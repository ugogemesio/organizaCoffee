package com.sw.cafe.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sw.cafe.model.Colaborador;
import com.sw.cafe.model.Contribuicao;
import com.sw.cafe.service.ColaboradorService;
import com.sw.cafe.service.ContribuicaoService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ContribuicaoController {

    private static final Logger logger = LoggerFactory.getLogger(ContribuicaoController.class);

    private final ContribuicaoService contribuicaoService;
    private final ColaboradorService colaboradorService;

    public ContribuicaoController(ContribuicaoService contribuicaoService, ColaboradorService colaboradorService) {
        this.contribuicaoService = contribuicaoService;
        this.colaboradorService = colaboradorService;
    }

    @GetMapping("/minhas-contribuicoes")
    public String minhasContribuicoes(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String cpf = authentication.getName(); // Assuming the CPF is the username
        Colaborador a = colaboradorService.achaPorCpf(cpf);

        List<Contribuicao> contribuicoes = contribuicaoService.findByColabId(a.getId());
        model.addAttribute("contribuicoes", contribuicoes);
        return "minhasContribuicoes";
    }

    @PostMapping("/contribuicoes/deletar/{id}")
    public void deletarContribuicao(@PathVariable("id") long id, HttpServletResponse response) {
        System.out.println(id);
        try {
            contribuicaoService.deleteContribuicaoById(id);
            response.sendRedirect("/minhas-contribuicoes");
        } catch (Exception e) {
            try {
                response.sendRedirect("/error"); // Redirecionar para uma página de erro, se necessário
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
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

    @PostMapping("/contribuicoes/atualizar")
    public ResponseEntity<String> updateContribuicao(@RequestParam("id") Long id,
            @RequestParam("confirmada") boolean confirmada) {
        try {
            contribuicaoService.confirmaContribuicao(id, confirmada);
            return ResponseEntity.ok("Contribuição atualizada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar a contribuição.");
        }
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred: " + e.getMessage());
        return "redirect:/calendar";
    }
}
