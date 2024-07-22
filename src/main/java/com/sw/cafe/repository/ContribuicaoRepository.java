package com.sw.cafe.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sw.cafe.model.Contribuicao;

import jakarta.transaction.Transactional;

@Repository
public interface ContribuicaoRepository extends CrudRepository<Contribuicao, Long> {

    @Query(value = "SELECT * FROM contribuicao WHERE data = ?1", nativeQuery = true)
    List<Contribuicao> findByData(LocalDate data);

    @Query(value = "SELECT * FROM contribuicao WHERE data = ?1 AND colaborador_id = ?2 AND nome = ?3", nativeQuery = true)
    Contribuicao findByDataAndColaboradorIdAndNome(LocalDate data, Long colaboradorId, String nome);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO contribuicao (nome, data, colaborador_id) VALUES (?1, ?2, ?3)", nativeQuery = true)
    void insertContribuicao(String nome, LocalDate data, Long colaboradorId);
}
