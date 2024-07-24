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

    @Query(value = "DELETE FROM contribuicao WHERE id = :id", nativeQuery = true)
    void deleteById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO contribuicao (nome, data, confirmada, colaborador_id) VALUES (:nome, :data, :confirmada, :colaborador_id)", nativeQuery = true)
    void saveContribuicao(@Param("nome") String nome, @Param("data") LocalDate data,
            @Param("confirmada") boolean confirmada, @Param("colaborador_id") Long colaboradorId);

    @Query(value = "UPDATE contribuicao SET nome = :nome, data = :data, confirmada = :confirmada, colaborador_id = :colaborador_id WHERE id = :id", nativeQuery = true)
    void updateContribuicao(@Param("id") Long id, @Param("nome") String nome, @Param("data") LocalDate data,
            @Param("confirmada") boolean confirmada, @Param("colaborador_id") Long colaboradorId);

    @Query(value = "SELECT * FROM contribuicao WHERE colaborador_cpf = :cpf", nativeQuery = true)
    List<Contribuicao> findByColaboradorCpf(String cpf);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM contribuicao WHERE id = :id", nativeQuery = true)
    void deleteContribuicaoById(Long id);

}
