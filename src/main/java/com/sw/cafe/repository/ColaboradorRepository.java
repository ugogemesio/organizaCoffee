package com.sw.cafe.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sw.cafe.model.Colaborador;

import jakarta.transaction.Transactional;

public interface ColaboradorRepository extends CrudRepository<Colaborador, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO colaborador (nome, cpf, senha) VALUES (?1, ?2, ?3)", nativeQuery = true)
    void insertColaborador(String nome, String cpf, String senha);

    @Transactional
    @Query(value = "SELECT * FROM colaborador WHERE cpf = ?1", nativeQuery = true)
    Colaborador findByCpf(String cpf);
}
