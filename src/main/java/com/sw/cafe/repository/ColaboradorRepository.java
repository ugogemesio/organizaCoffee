package com.sw.cafe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sw.cafe.model.Colaborador;
import com.sw.cafe.model.Contribuicao;

import jakarta.transaction.Transactional;

public interface ColaboradorRepository extends CrudRepository<Colaborador, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO colaborador (nome, cpf, senha) VALUES (?1, ?2, ?3)", nativeQuery = true)
    void insertColaborador(String nome, String cpf, String senha);

    @Transactional
    @Query(value = "SELECT * FROM colaborador WHERE cpf = ?1", nativeQuery = true)
    Colaborador findByCpf(String cpf);

    @Transactional
    @Query(value="SELECT * FROM contribuicao WHERE colaborador_id = ?1", nativeQuery=true)
    List<Contribuicao> listaContribuicao(Long colaboradorId);
}
