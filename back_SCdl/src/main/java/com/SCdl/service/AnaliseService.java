package com.SCdl.service;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.io.IOException;
import java.net.URI;

import java.time.Duration;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.SCdl.model.Comparacao;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

@Service
public class AnaliseService {

    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(15))
            .build();

    public Comparacao comparar(MultipartFile proposta, MultipartFile licitacao) throws Exception {

        String textoProposta = lerPdf(proposta);
        String textoLicitacao = lerPdf(licitacao);

        String jsonInputString = """
                {
                  "model": "deepseek/deepseek-r1-0528:free",
                  "messages": [
                    {"role": "system", "content": "Você tem como objetivo comparar documentos de maneira crítica e analítica"},
                    {"role": "user", "content": "Com base nessa licitação:
                                         """
                + textoLicitacao + """
                        Realize uma análise da seguinte proposta:
                        """ + textoProposta + """
                                                 Responda SOMENTE um JSON com:
                                                   semelhancas
                                                   diferencas
                                                   nota "}
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
                .POST(HttpRequest.BodyPublishers.ofString(jsonInputString))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new RuntimeException("Erro ao chamar a API: " + response.body());
        }

        return converterParaComparacao(response.body());
    }

    private String lerPdf(MultipartFile file) throws IOException {
        try (PDDocument doc = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(doc);
        }
    }

    private Comparacao converterParaComparacao(String respostaJson) {

        Gson gson = new Gson();

        Map<?, ?> resposta = gson.fromJson(respostaJson, Map.class);

        var choices = (java.util.List<?>) resposta.get("choices");
        var choice = (LinkedTreeMap<?, ?>) choices.get(0);

        var message = (LinkedTreeMap<?, ?>) choice.get("message");

        String conteudo = message.get("content").toString();

        // Remove ```json e ```
        conteudo = conteudo.replace("```json", "").replace("```", "").trim();

        Map<?, ?> dados = gson.fromJson(conteudo, Map.class);

        Comparacao c = new Comparacao();
        c.setTxtSemelhanca((String) dados.get("semelhancas"));
        c.setTxtDiferenca((String) dados.get("diferencas"));
        c.setNota(((Double) dados.get("nota")).intValue());

        return c;
    }
}
