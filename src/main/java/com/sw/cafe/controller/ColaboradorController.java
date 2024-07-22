package com.sw.cafe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sw.cafe.model.Colaborador;
import com.sw.cafe.service.ColaboradorService;

@RestController
@RequestMapping("/colaborador")
public class ColaboradorController {

    private static final Logger logger = LoggerFactory.getLogger(ColaboradorController.class);

    @Autowired
    private ColaboradorService colaboradorService;

    @GetMapping("/id/{id}")
    public Colaborador getColaboradorId(@PathVariable Long id) {
        return colaboradorService.achaPorId(id);
    }

    @GetMapping("/cpf/{cpf}")
    public Colaborador getColaboradorCpf(@PathVariable String cpf) {
        return colaboradorService.achaPorCpf(cpf);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String criaColaborador(@RequestBody Colaborador colaborador) {
        try {
            logger.info("Criando colaborador: Nome = {}, CPF = {}", colaborador.getNome(), colaborador.getCpf());
            colaboradorService.criarColaborador(colaborador.getNome(), colaborador.getCpf(), colaborador.getSenha());
            return "Criado colaborador " + colaborador.getNome();
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao criar colaborador: {}", e.getMessage());
            return "Erro ao criar colaborador: " + e.getMessage();
        }
    }

    @PutMapping
    public String atualizaColaborador(@RequestBody Colaborador colaborador) {
        logger.info("Atualizando colaborador: Nome = {}, CPF = {}", colaborador.getNome(), colaborador.getCpf());
        colaboradorService.atualizarColaborador(colaborador);
        return "Atualizado colaborador " + colaborador.getNome();
    }

    @DeleteMapping("/id/{id}")
    public String deletaColaborador(@PathVariable Long id) {
        logger.info("Deletando colaborador com ID = {}", id);
        colaboradorService.deletarColaborador(id);
        return "Removido colaborador com ID = " + id;
    }
}
