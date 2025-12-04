package com.SCdl.service;

import com.SCdl.model.Licitacao;
import com.SCdl.web.login.user.domain.User;
import com.SCdl.repositories.LicitacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class LicitacaoService {

    private final LicitacaoRepository licitacaoRepository;

    @Autowired
    public LicitacaoService(LicitacaoRepository licitacaoRepository) {
        this.licitacaoRepository = licitacaoRepository;
    }

    public Licitacao salvarNovaLicitacao(User usuarioLogado, MultipartFile arquivo) throws IOException {

        // 1. Validação do usuário
        if (usuarioLogado == null) {
            throw new RuntimeException("Nenhum usuário logado. Upload não permitido.");
        }

        // 2. Lê conteúdo do PDF
        byte[] conteudoPdf = arquivo.getBytes();

        // 3. Monta objeto Licitacao
        Licitacao novaLicitacao = new Licitacao();
        novaLicitacao.setIdUsuarioFk(usuarioLogado.getId());
        novaLicitacao.setNomeArquivo(arquivo.getOriginalFilename());
        novaLicitacao.setArquivoPdf(conteudoPdf);
        novaLicitacao.setDataEnvio(LocalDateTime.now()); // opcional, caso sua tabela use DATA_ENVIO

        // 4. Salva via repository JPA
        return licitacaoRepository.save(novaLicitacao);
    }
}
