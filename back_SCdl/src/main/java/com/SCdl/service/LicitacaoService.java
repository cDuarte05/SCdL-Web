package com.SCdl.service;

import com.SCdl.model.Licitacao;
import com.SCdl.repositories.LicitacaoRepository;
import com.SCdl.web.login.user.domain.User;
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

    // ✔ método usado no upload normal (com usuário logado)
    public Licitacao salvarNovaLicitacao(User usuarioLogado, MultipartFile arquivo) throws IOException {

        if (usuarioLogado == null) {
            throw new RuntimeException("Nenhum usuário logado. Upload não permitido.");
        }

        Licitacao nova = new Licitacao();
        nova.setIdUsuarioFk(usuarioLogado.getId());
        nova.setNomeArquivo(arquivo.getOriginalFilename());
        nova.setArquivoPdf(arquivo.getBytes());
        nova.setDataEnvio(LocalDateTime.now());

        return licitacaoRepository.save(nova);
    }

    // ✔ método usado pelo AnaliseService (sem usuário, só para salvar pdf)
    public Licitacao salvar(MultipartFile arquivo) throws IOException {

        if (arquivo == null || arquivo.isEmpty()) {
            throw new IllegalArgumentException("Arquivo inválido.");
        }

        Licitacao nova = new Licitacao();
        nova.setNomeArquivo(arquivo.getOriginalFilename());
        nova.setArquivoPdf(arquivo.getBytes());
        nova.setDataEnvio(LocalDateTime.now());
        nova.setIdUsuarioFk(null); // banco deve permitir

        return licitacaoRepository.save(nova);
    }
}
