package com.sw.cafe.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sw.cafe.model.Contribuicao;

@Repository
public interface ContribuicaoRepository extends CrudRepository<Contribuicao, Long> {

    @Query(value = "SELECT * FROM contribuicao WHERE data = ?1", nativeQuery = true)
    List<Contribuicao> findByData(LocalDate data);

    @Query(value = "SELECT * FROM contribuicao WHERE data = ?1 AND nome = ?2", nativeQuery = true)
    Contribuicao findByDataAndNome(LocalDate data, String nome);

    @Query(value = "SELECT * FROM contribuicao WHERE data = ?1 AND colaborador_id = ?2", nativeQuery = true)
    List<Contribuicao> findByDataAndColaboradorId(LocalDate data, Long colaboradorId);
}
