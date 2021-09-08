package com.company.crypto.controller.rest;

import com.company.crypto.entity.User;
import com.company.crypto.service.StatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/statistics")
public class StatisticsRestController {

    private final StatisticsService statisticsService;

    @GetMapping
    public Double calculatePNL(@AuthenticationPrincipal User user) {
        return statisticsService.calculatePNL(user.getUsername());
    }

}
