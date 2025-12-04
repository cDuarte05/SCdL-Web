package com.SCdl.repositories;
import com.SCdl.model.Licitacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LicitacaoRepository extends JpaRepository<Licitacao, Integer> {

    List<Licitacao> findByUsuarioId(int idUsuario);

}
// esse JpaRepository serve para fazer operações de CRUD no banco de dados para
// a entidade Licitacao, usando Integer como chave primária.