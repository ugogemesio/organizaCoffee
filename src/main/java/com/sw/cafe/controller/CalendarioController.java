package com.sw.cafe.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sw.cafe.model.Colaborador;
import com.sw.cafe.model.Contribuicao;
import com.sw.cafe.service.CalendarioService;
import com.sw.cafe.service.ColaboradorService;
import com.sw.cafe.service.ContribuicaoService;

@Controller
public class CalendarioController {

    private static final Logger logger = LoggerFactory.getLogger(CalendarioController.class);

    private final CalendarioService calendarioService;
    private final ContribuicaoService contribuicaoService;
    private final ColaboradorService colaboradorService;

    public CalendarioController(CalendarioService calendarioService, ContribuicaoService contribuicaoService,
            ColaboradorService colaboradorService) {
        this.calendarioService = calendarioService;
        this.contribuicaoService = contribuicaoService;
        this.colaboradorService = colaboradorService;
    }

    @GetMapping("/calendar")
    public String showCalendar(Model model, Authentication authentication) {
        if (authentication != null) {
            String cpf = authentication.getName();
            Colaborador colaborador = colaboradorService.achaPorCpf(cpf);
            Contribuicao contribuicao = new Contribuicao();

            LocalDate today = LocalDate.now();
            List<Contribuicao> contribuicoes = contribuicaoService.findByDataAndColaboradorId(today,
                    colaborador.getId());
            model.addAttribute("contribuicoes", contribuicoes);
        }

        YearMonth currentYearMonth = YearMonth.now();
        String yearMonth = currentYearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        return getCalendar(yearMonth, model);
    }

    @GetMapping("/calendar/{yearMonth}")
    public String getCalendar(@PathVariable String yearMonth, Model model) {
        YearMonth ym = YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-MM"));
        List<LocalDateTime> days = calendarioService.generateMonthCalendar(ym.getYear(), ym.getMonthValue());
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

}
