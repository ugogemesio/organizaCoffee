package com.sw.cafe.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sw.cafe.model.Contribuicao;
import com.sw.cafe.repository.ContribuicaoRepository;

@Service
public class ContribuicaoService {

    private static final Logger logger = LoggerFactory.getLogger(Contribuicao.class);

    @Autowired
    private ContribuicaoRepository contribuicaoRepository;

    public List<Contribuicao> findByData(LocalDate data) {
        return contribuicaoRepository.findByData(data);
    }

    public List<Contribuicao> findByDataAndColaboradorId(LocalDate data, Long colaboradorId) {
        return contribuicaoRepository.findByDataAndColaboradorId(data, colaboradorId);
    }

    public List<Contribuicao> findByColaboradorCpf(String cpf) {
        return contribuicaoRepository.findByColaboradorCpf(cpf);
    }

    public void deleteContribuicaoById(Long id) {
        contribuicaoRepository.deleteContribuicaoById(id);
    }

    public void addContribuicao(Contribuicao contribuicao) {
        logger.info("Verificando existência de contribuição para data: {} e nome: {}", contribuicao.getData(),
                contribuicao.getNome());
        Contribuicao existente = contribuicaoRepository.findByDataAndNome(contribuicao.getData(),
                contribuicao.getNome());
        if (existente != null) {
            logger.error("Contribuição já existe para esta data e nome.");
            throw new IllegalArgumentException("Já existe uma contribuição com este nome neste dia");
        }
        contribuicao.setConfirmada(false);
        contribuicaoRepository.save(contribuicao);
        logger.info("Contribuição salva com sucesso: {}", contribuicao);
    }

    public Contribuicao findById(Long id) {
        return contribuicaoRepository.findById(id).orElse(null);
    }

    public void save(Contribuicao contribuicao) {
        contribuicaoRepository.save(contribuicao);
    }

    public void deleteById(Long id) {
        contribuicaoRepository.deleteById(id);
    }
}
