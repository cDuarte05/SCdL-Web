package com.SCdl.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Comparacao")
@IdClass(ComparacaoId.class)
public class Comparacao {

    @Id
    @Column(name = "id_licitacao_fk")
    private Integer idLicitacaoFk;

    @Id
    @Column(name = "id_proposta_fk")
    private Integer idPropostaFk;

    @Column(name = "txt_semelhanca", columnDefinition = "TEXT")
    private String txtSemelhanca;

    @Column(name = "txt_diferenca", columnDefinition = "TEXT")
    private String txtDiferenca;

    private int nota;

    @Column(name = "data_comparacao")
    private LocalDateTime dataComparacao = LocalDateTime.now();


    // GETTERS E SETTERS -----------------------


    public Integer getIdLicitacaoFk() {
        return idLicitacaoFk;
    }

    public void setIdLicitacaoFk(Integer idLicitacaoFk) {
        this.idLicitacaoFk = idLicitacaoFk;
    }

    public Integer getIdPropostaFk() {
        return idPropostaFk;
    }

    public void setIdPropostaFk(Integer idPropostaFk) {
        this.idPropostaFk = idPropostaFk;
    }

    public String getTxtSemelhanca() {
        return txtSemelhanca;
    }

    public void setTxtSemelhanca(String txtSemelhanca) {
        this.txtSemelhanca = txtSemelhanca;
    }

    public String getTxtDiferenca() {
        return txtDiferenca;
    }

    public void setTxtDiferenca(String txtDiferenca) {
        this.txtDiferenca = txtDiferenca;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public LocalDateTime getDataComparacao() {
        return dataComparacao;
    }

    public void setDataComparacao(LocalDateTime dataComparacao) {
        this.dataComparacao = dataComparacao;
    }
}
