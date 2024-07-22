package com.sw.cafe.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sw.cafe.model.Contribuicao;
import com.sw.cafe.repository.ContribuicaoRepository;

@Service
public class ContribuicaoService {

    @Autowired
    private ContribuicaoRepository contribuicaoRepository;

    public List<Contribuicao> findByData(LocalDate data) {
        return contribuicaoRepository.findByData(data);
    }

    public void addContribuicao(Contribuicao contribuicao) {
        Contribuicao existente = contribuicaoRepository.findByDataAndColaboradorIdAndNome(
                contribuicao.getData(), contribuicao.getColaborador().getId(), contribuicao.getNome());
        if (existente != null) {
            throw new IllegalArgumentException("Colaborador já fez uma contribuição com este nome neste dia");
        }
        contribuicaoRepository.insertContribuicao(contribuicao.getNome(), contribuicao.getData(), contribuicao.getColaborador().getId());
    }
}
