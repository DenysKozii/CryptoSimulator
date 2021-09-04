package com.company.crypto.controller;

import com.company.crypto.service.AuthorizationService;
import com.company.crypto.service.StatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;

@Controller
@AllArgsConstructor
@RequestMapping("/statistics")
public class StatisticsController {
    private final AuthorizationService authorizationService;
    private final StatisticsService statisticsService;

    @GetMapping
    public String getStatistics(Model model) {
        Double pnl = statisticsService.calculatePNL();
        model.addAttribute("PNL", pnl);
        return "statistics";
    }

}
