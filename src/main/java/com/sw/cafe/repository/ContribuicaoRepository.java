package com.sw.cafe.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sw.cafe.model.Contribuicao;

import jakarta.transaction.Transactional;

@Repository
public interface ContribuicaoRepository extends CrudRepository<Contribuicao, Long> {

    @Query(value = "SELECT * FROM contribuicao", nativeQuery = true)
    List<Contribuicao> findAll();

    @Query(value = "SELECT * FROM contribuicao WHERE data = ?1", nativeQuery = true)
    List<Contribuicao> findByData(LocalDate data);

    @Query(value = "SELECT * FROM contribuicao WHERE data = ?1 AND nome = ?2", nativeQuery = true)
    Contribuicao findByDataAndNome(LocalDate data, String nome);

    @Query(value = "SELECT * FROM contribuicao WHERE data = ?1 AND colaborador_id = ?2", nativeQuery = true)
    List<Contribuicao> findByDataAndColaboradorId(LocalDate data, Long colaboradorId);

    @Query(value = "SELECT * FROM contribuicao WHERE id = :id", nativeQuery = true)
    Optional<Contribuicao> findById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE contribuicao SET confirmada = :confirmada WHERE id = :id", nativeQuery = true)
    void updateContribuicao(@Param("id") Long id, @Param("confirmada") boolean confirmada);

    @Query(value = "SELECT * FROM contribuicao WHERE colaborador_cpf = :cpf", nativeQuery = true)
    List<Contribuicao> findByColaboradorCpf(@Param("cpf") String cpf);

    @Transactional
    @Modifying
    @Query(value = "DELETE  FROM contribuicao WHERE id = :id", nativeQuery = true)
    void deleteContribuicaoById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM contribuicao WHERE colaborador_id = :id", nativeQuery = true)
    List<Contribuicao>  findByColadboradorId(@Param("id") Long id);

}
