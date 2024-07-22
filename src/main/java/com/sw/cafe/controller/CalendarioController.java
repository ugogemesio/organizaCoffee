package com.sw.cafe.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sw.cafe.model.Colaborador;
import com.sw.cafe.model.Contribuicao;
import com.sw.cafe.service.CalendarioService;
import com.sw.cafe.service.ColaboradorService;
import com.sw.cafe.service.ContribuicaoService;

@Controller
public class CalendarioController {

    private final CalendarioService calendarioService;
    private final ContribuicaoService contribuicaoService;
    private final ColaboradorService colaboradorService;

    public CalendarioController(CalendarioService calendarioService, ContribuicaoService contribuicaoService, ColaboradorService colaboradorService) {
        this.calendarioService = calendarioService;
        this.contribuicaoService = contribuicaoService;
        this.colaboradorService = colaboradorService;
    }

    @GetMapping("/calendar")
    public String showCalendar(Model model) {
        YearMonth currentYearMonth = YearMonth.now();
        String yearMonth = currentYearMonth.getYear() + "-" + String.format("%02d", currentYearMonth.getMonthValue());
        return getCalendar(yearMonth, model);
    }

    @GetMapping("/calendar/{yearMonth}")
    public String getCalendar(@PathVariable String yearMonth, Model model) {
        String[] parts = yearMonth.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        List<LocalDateTime> days = calendarioService.generateMonthCalendar(year, month);
        model.addAttribute("days", days);
        model.addAttribute("currentMonth", yearMonth);
        model.addAttribute("yearMonth", yearMonth);
        return "calendar";
    }

    @GetMapping("/calendar/day/{day}")
    public String showDay(@PathVariable("day") String day, Model model) {
        LocalDate date = LocalDate.parse(day, DateTimeFormatter.ISO_DATE);
        List<Contribuicao> contribuicoes = contribuicaoService.findByData(date);
        model.addAttribute("day", date);
        model.addAttribute("contribuicoes", contribuicoes);
        return "dayDetails";
    }

    @PostMapping("/calendar/day/{day}/contribute")
    public String addContribuicao(@PathVariable("day") String day, Contribuicao contribuicao, RedirectAttributes redirectAttributes) {
        LocalDate date = LocalDate.parse(day, DateTimeFormatter.ISO_DATE);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String cpf = authentication.getName(); // O CPF é usado como nome de usuário
        Colaborador colaborador = colaboradorService.achaPorCpf(cpf);
        contribuicao.setData(date);
        contribuicao.setColaborador(colaborador);
        try {
            contribuicaoService.addContribuicao(contribuicao);
        } catch (IllegalArgumentException e) {
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
