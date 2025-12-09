package com.SCdl.model;

import java.io.Serializable;
import java.util.Objects;

public class ComparacaoId implements Serializable {

    private Integer idLicitacaoFk;
    private Integer idPropostaFk;

    public ComparacaoId() {
    }

    public ComparacaoId(int idLicitacaoFk, int idPropostaFk) {
        this.idLicitacaoFk = idLicitacaoFk;
        this.idPropostaFk = idPropostaFk;
    }

    public int getIdLicitacaoFk() {
        return idLicitacaoFk;
    }

    public void setIdLicitacaoFk(int idLicitacaoFk) {
        this.idLicitacaoFk = idLicitacaoFk;
    }

    public int getIdPropostaFk() {
        return idPropostaFk;
    }

    public void setIdPropostaFk(int idPropostaFk) {
        this.idPropostaFk = idPropostaFk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComparacaoId that = (ComparacaoId) o;
        return idLicitacaoFk == that.idLicitacaoFk &&
               idPropostaFk == that.idPropostaFk;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLicitacaoFk, idPropostaFk);
    }
}
