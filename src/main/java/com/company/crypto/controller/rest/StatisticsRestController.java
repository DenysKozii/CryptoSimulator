package com.company.crypto.controller.rest;

import com.company.crypto.service.AuthorizationService;
import com.company.crypto.service.StatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/statistics")
public class StatisticsRestController {
    private final AuthorizationService authorizationService;
    private final StatisticsService statisticsService;

    @GetMapping
    public String getStatistics(Model model) {
        Double pnl = statisticsService.calculatePNL();
        model.addAttribute("PNL", pnl);
        return "statistics";
    }

}
