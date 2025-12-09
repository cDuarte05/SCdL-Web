package com.SCdl.service;

import com.SCdl.model.Proposta;
import com.SCdl.repositories.PropostaRepository;
import com.SCdl.web.login.user.domain.User;
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

    // ✔ método com usuário logado
    public Proposta salvarNovaProposta(User usuarioLogado, MultipartFile arquivo) throws IOException {

        if (usuarioLogado == null) {
            throw new RuntimeException("Nenhum usuário logado. Upload não permitido.");
        }

        Proposta nova = new Proposta();
        nova.setIdUsuarioFk(usuarioLogado.getId());
        nova.setNomeArquivo(arquivo.getOriginalFilename());
        nova.setArquivoPdf(arquivo.getBytes());
        nova.setDataEnvio(LocalDateTime.now());

        return propostaRepository.save(nova);
    }

    // ✔ método usado pelo AnaliseService
    public Proposta salvar(MultipartFile arquivo) throws IOException {

        if (arquivo == null || arquivo.isEmpty()) {
            throw new IllegalArgumentException("Arquivo inválido.");
        }

        Proposta nova = new Proposta();
        nova.setNomeArquivo(arquivo.getOriginalFilename());
        nova.setArquivoPdf(arquivo.getBytes());
        nova.setDataEnvio(LocalDateTime.now());
        nova.setIdUsuarioFk(null);

        return propostaRepository.save(nova);
    }
}
