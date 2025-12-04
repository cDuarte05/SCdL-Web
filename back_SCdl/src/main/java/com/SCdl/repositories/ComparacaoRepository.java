package com.SCdl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SCdl.model.Comparacao;
import com.SCdl.model.ComparacaoId;

public interface ComparacaoRepository extends JpaRepository<Comparacao, ComparacaoId> {
}

// esse JpaRepository serve para fazer operações de CRUD no banco de dados para
// a entidade Comparacao, usando ComparacaoId como chave primária composta.
