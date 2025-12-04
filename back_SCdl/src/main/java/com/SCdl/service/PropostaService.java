package com.SCdl.service;

import com.SCdl.model.Proposta;
import com.SCdl.web.login.user.domain.User;
import com.SCdl.repositories.PropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class PropostaService {

    private final PropostaRepository propostaRepository;

    @Autowired
    public PropostaService(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    /**
     * Salva uma nova proposta enviada pelo usuário.
     *
     * @param usuarioLogado usuário autenticado (pode ser null — será validado)
     * @param arquivo MultipartFile enviado pelo cliente
     * @return Proposta salva (com id preenchido)
     * @throws IOException se houver erro na leitura dos bytes do arquivo
     */
    public Proposta salvarNovaProposta(User usuarioLogado, MultipartFile arquivo) throws IOException {

        // valida usuário
        if (usuarioLogado == null) {
            throw new RuntimeException("Nenhum usuário logado. Upload não permitido.");
        }

        // valida arquivo
        if (arquivo == null || arquivo.isEmpty()) {
            throw new IllegalArgumentException("Arquivo inválido ou não informado.");
        }

        // lê bytes do PDF
        byte[] conteudoPdf = arquivo.getBytes();

        // monta entidade
        Proposta novaProposta = new Proposta();
        // ATENÇÃO: alinhar tipos entre User.id e Proposta.idUsuarioFk.
        // Aqui seguimos o padrão do LicitacaoService: usar o id do User diretamente.
        novaProposta.setIdUsuarioFk(usuarioLogado.getId());
        novaProposta.setNomeArquivo(arquivo.getOriginalFilename());
        novaProposta.setArquivoPdf(conteudoPdf);
        novaProposta.setDataEnvio(LocalDateTime.now()); // opcional se DB preencher automaticamente

        // salva via repository JPA
        return propostaRepository.save(novaProposta);
    }
}
