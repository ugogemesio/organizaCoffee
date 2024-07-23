package com.sw.cafe.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sw.cafe.model.Colaborador;
import com.sw.cafe.model.Contribuicao;
import com.sw.cafe.service.ColaboradorService;
import com.sw.cafe.service.ContribuicaoService;

@Controller
public class ContribuicaoController {

    private static final Logger logger = LoggerFactory.getLogger(ContribuicaoController.class);

    private final ContribuicaoService contribuicaoService;
    private final ColaboradorService colaboradorService;

    public ContribuicaoController(ContribuicaoService contribuicaoService, ColaboradorService colaboradorService) {
        this.contribuicaoService = contribuicaoService;
        this.colaboradorService = colaboradorService;
    }

    @PostMapping("/contribuicoes/adicionar/{day}")
    public String addContribuicao(@PathVariable("day") String day, Contribuicao contribuicao,
            RedirectAttributes redirectAttributes, Authentication authentication) {
        LocalDate date = LocalDate.parse(day, DateTimeFormatter.ISO_DATE);
        String cpf = authentication.getName(); // O CPF é usado como nome de usuário
        logger.info("CPF do usuário autenticado: {}", cpf);

        Colaborador colaborador = colaboradorService.achaPorCpf(cpf);
        if (colaborador == null) {
            logger.error("Colaborador não encontrado para o CPF: {}", cpf);
            redirectAttributes.addFlashAttribute("errorMessage", "Colaborador não encontrado.");
            return "redirect:/calendar/day/" + day;
        }

        contribuicao.setData(date);
        contribuicao.setColaborador(colaborador);
        logger.info("Tentando adicionar contribuição: Nome = {}, Data = {}, Colaborador ID = {}",
                contribuicao.getNome(), contribuicao.getData(), colaborador.getId());

        try {
            contribuicaoService.addContribuicao(contribuicao);
            logger.info("Contribuição adicionada com sucesso");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao adicionar contribuição: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/calendar/day/" + day;
        }
        return "redirect:/calendar/day/" + day;
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred: " + e.getMessage());
        return "redirect:/calendar";
    }
}
