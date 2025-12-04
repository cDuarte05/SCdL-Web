package com.SCdl.service;

import com.SCdl.model.Comparacao;
import com.SCdl.model.ComparacaoId;
import com.SCdl.model.Licitacao;
import com.SCdl.model.Proposta;
import com.SCdl.repositories.ComparacaoRepository;
import com.SCdl.controller.APIConnection; // ou onde estiver a sua APIConnection
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComparacaoService {

    private final ComparacaoRepository comparacaoRepository;

    @Autowired
    public ComparacaoService(ComparacaoRepository comparacaoRepository) {
        this.comparacaoRepository = comparacaoRepository;
    }
    
    @Transactional
    public Comparacao realizarComparacao(Proposta proposta, Licitacao licitacao) {
        // chama a API externa que compara os documentos
        Comparacao temp = APIConnection.compararDocumentos(proposta, licitacao);

        if (temp == null) {
            throw new IllegalStateException("Comparação retornou nulo — erro no processamento externo.");
        }

        // extrai os valores (como você tinha antes) — útil para validação/transformação/log
        String semelhancaSimulada = temp.getTxtSemelhanca();
        String diferencaSimulada   = temp.getTxtDiferenca();
        int notaSimulada           = temp.getNota();

        // (Exemplo de validação simples)
        if (notaSimulada < 0 || notaSimulada > 100) {
            throw new IllegalArgumentException("Nota da comparação inválida: " + notaSimulada);
        }

        // monta o objeto a ser salvo (use os setters conforme sua entidade)
        Comparacao resultado = new Comparacao();
        resultado.setIdLicitacaoFk(licitacao.getId());
        resultado.setIdPropostaFk(proposta.getId());
        resultado.setTxtSemelhanca(semelhancaSimulada);
        resultado.setTxtDiferenca(diferencaSimulada);
        resultado.setNota(notaSimulada);


        // salva via repository (substitui o antigo DAO)
        // como sua entidade usa chave composta, save funcionará normalmente
        return comparacaoRepository.save(resultado);
    }
}
