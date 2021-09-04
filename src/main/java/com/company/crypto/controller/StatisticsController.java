package com.company.crypto.controller;

import com.company.crypto.dto.TransactionDto;
import com.company.crypto.service.AuthorizationService;
import com.company.crypto.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/statistics")
public class StatisticsController {
    private final AuthorizationService authorizationService;
    private final TransactionService transactionService;

    @GetMapping
    public String getTransactions(Model model) {
        return "statistics";
    }

}
