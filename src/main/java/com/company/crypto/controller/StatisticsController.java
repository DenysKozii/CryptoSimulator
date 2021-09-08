//package com.company.crypto.controller;
//
//import com.company.crypto.service.StatisticsService;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@AllArgsConstructor
//@Slf4j
//@RequestMapping("/statistics")
//public class StatisticsController {
//    private final StatisticsService statisticsService;
//
//    @GetMapping
//    public String getStatistics(Model model) {
//        log.info("Displayed statistic page");
//        Double pnl = statisticsService.calculatePNL();
//        model.addAttribute("PNL", pnl);
//        return "statistics";
//    }
//
//}
