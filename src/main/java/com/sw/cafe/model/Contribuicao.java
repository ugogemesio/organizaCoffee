package com.sw.cafe.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "contribuicao")
public class Contribuicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private LocalDate data;
    private boolean confirmada = false; // Definindo valor padrão

    @ManyToOne
    @JoinColumn(name = "colaborador_id")
    private Colaborador colaborador;

    // Construtores, getters e setters

    public Contribuicao() {
    }

    public Contribuicao(long id, String nome, LocalDate data, Colaborador colaborador, boolean confirmada) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.colaborador = colaborador;
        this.confirmada = confirmada;
    }

    // Getters e setters...

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public boolean isConfirmada() {
        return confirmada;
    }

    public void setConfirmada(boolean confirmada) {
        this.confirmada = confirmada;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }
}
