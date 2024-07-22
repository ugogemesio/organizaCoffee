// package com.sw.cafe.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;
// import com.sw.cafe.model.Contribuicao;
// import com.sw.cafe.model.Colaborador;
// import com.sw.cafe.service.ContribuicaoService;
// import com.sw.cafe.service.ColaboradorService;

// import java.time.LocalDate;
// import java.util.List;

// @RestController
// @RequestMapping("/api/contribuicoes")
// public class ContribuicaoController {

//     @Autowired
//     private ContribuicaoService contribuicaoService;

//     @Autowired
//     private ColaboradorService colaboradorService;

//     @PostMapping
//     public Contribuicao criarContribuicao(@RequestParam String nome, @RequestParam Long colaboradorId, @RequestParam String data) {
//         Colaborador colaborador = colaboradorService.acharColaborador(colaboradorId);
//         LocalDate dataFormatada = LocalDate.parse(data);
//         return contribuicaoService.criarContribuicao(nome, colaborador, dataFormatada);
//     }

//     // Outros endpoints
// }
