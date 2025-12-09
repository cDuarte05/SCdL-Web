package com.SCdl.service;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.SCdl.model.Comparacao;
import com.SCdl.model.Licitacao;
import com.SCdl.model.Proposta;
import com.SCdl.repositories.ComparacaoRepository;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

@Service
public class AnaliseService {

    @Autowired
    private PropostaService propostaService;

    @Autowired
    private LicitacaoService licitacaoService;

    @Autowired
    private ComparacaoRepository comparacaoRepo;

    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(15))
            .build();


    /**
     * Fluxo principal:
     * 1) Salva Proposta
     * 2) Salva Licitação
     * 3) Extrai textos
     * 4) Envia para IA
     * 5) Salva Comparação
     */
    public Comparacao comparar(MultipartFile proposta, MultipartFile licitacao) throws Exception {

        // 1) Salva Proposta no banco
        Proposta prop = propostaService.salvar(proposta);

        // 2) Salva Licitação no banco
        Licitacao lic = licitacaoService.salvar(licitacao);

        // 3) Lê os textos para enviar à IA
        String textoProposta = lerPdf(proposta);
        String textoLicitacao = lerPdf(licitacao);

        // 4) Monta requisição IA
        String jsonInput = """
                {
                  "model": "deepseek/deepseek-r1-0528:free",
                  "messages": [
                    {"role": "system", "content": "Você deve comparar documentos de forma crítica e analítica"},
                    {"role": "user", "content": "Com base nessa licitação:
                """ + textoLicitacao + """
                        Realize uma análise da seguinte proposta:
                """ + textoProposta + """
                        Responda SOMENTE um JSON contendo:
                        {
                           'semelhancas': '...',
                           'diferencas': '...',
                           'nota': 0-100
                        }
                    "}
                  ],
                  "max_tokens": 2000,
                  "temperature": 0.7
                }
                """;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://openrouter.ai/api/v1/chat/completions"))
                .timeout(Duration.ofSeconds(45))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer SUA_CHAVE")
                .POST(HttpRequest.BodyPublishers.ofString(jsonInput))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new RuntimeException("Erro ao chamar IA: " + response.body());
        }

        // 5) Converte IA → objeto Comparacao
        Comparacao comparacao = converterParaComparacao(response.body());

        // 6) Preenche FKs e data
        comparacao.setIdLicitacaoFk(lic.getId());
        comparacao.setIdPropostaFk(prop.getId());
        comparacao.setDataComparacao(LocalDateTime.now());

        // 7) Salva no banco
        comparacaoRepo.save(comparacao);

        return comparacao;
    }



    // ---------------- UTILITÁRIOS ----------------

    private String lerPdf(MultipartFile file) throws IOException {
        try (PDDocument doc = PDDocument.load(file.getInputStream())) {
            return new PDFTextStripper().getText(doc);
        }
    }


    private Comparacao converterParaComparacao(String respostaJson) {

        Gson gson = new Gson();

        Map<?, ?> resposta = gson.fromJson(respostaJson, Map.class);

        var choices = (java.util.List<?>) resposta.get("choices");
        var choice = (LinkedTreeMap<?, ?>) choices.get(0);

        var message = (LinkedTreeMap<?, ?>) choice.get("message");
        String conteudo = message.get("content").toString();

        conteudo = conteudo
                .replace("```json", "")
                .replace("```", "")
                .trim();

        Map<?, ?> dados = gson.fromJson(conteudo, Map.class);

        Comparacao c = new Comparacao();
        c.setTxtSemelhanca((String) dados.get("semelhancas"));
        c.setTxtDiferenca((String) dados.get("diferencas"));
        c.setNota(((Double) dados.get("nota")).intValue());

        return c;
    }
}
