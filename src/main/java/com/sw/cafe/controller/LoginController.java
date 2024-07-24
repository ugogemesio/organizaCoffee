package com.sw.cafe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sw.cafe.model.Colaborador;
import com.sw.cafe.service.ColaboradorService;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private ColaboradorService colaboradorService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> loga(@RequestBody Colaborador colaborador) {
        try {
            logger.info("Logando colaborador: Nome = {}, CPF = {}", colaborador.getNome(), colaborador.getCpf());
            colaboradorService.criarColaborador(colaborador.getNome(), colaborador.getCpf(), colaborador.getSenha());
            return ResponseEntity.ok("Logado " + colaborador.getNome());
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao logar colaborador: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Erro ao logar colaborador: " + e.getMessage());
        }
    }

    @GetMapping("/login")
    public String home() {
        return "loginForm";
    }

    @GetMapping("/")
    public String index() {
        return "loginForm";
    }

    @GetMapping("/register")
    public String registra() {
        return "registraForm";
    }
}
