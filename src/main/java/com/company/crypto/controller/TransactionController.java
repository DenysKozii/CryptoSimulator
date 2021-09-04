package com.company.crypto.controller;

import com.company.crypto.dto.QuestionDto;
import com.company.crypto.service.AuthorizationService;
import com.company.crypto.service.QuestionService;
import com.company.crypto.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {
    private final AuthorizationService authorizationService;
    private final TransactionService transactionService;


    @GetMapping("/order")
    public String getOrderEditor(@RequestParam(defaultValue = "BTCUSDT") String symbol, Model model) {
        Double price = transactionService.getPrice(symbol);
        Double available = transactionService.getAvailable();
        Double currentAsset = transactionService.getCurrentAsset(symbol);
        model.addAttribute("price", price);
        model.addAttribute("available", available);
        model.addAttribute("currentAsset", currentAsset);
        return "orderEditor";
    }

    @GetMapping("/stop")
    public String getStopEditor(@RequestParam(defaultValue = "BTCUSDT") String symbol, Model model) {
        return "stopEditor";
    }

    @PostMapping("/order/post")
    public String postOrder(@RequestParam Long orderId,
                              @RequestParam String title,
                              @RequestParam String context,
                              @RequestParam Double answer,
                              @RequestParam(value = "imageQuestion", required = false) MultipartFile imageQuestion,
                              @RequestParam(value = "imageAnswer", required = false) MultipartFile imageAnswer) throws IOException {
//        transactionService.create(orderId, title, context, answer, imageQuestion, imageAnswer);
        return "redirect:/transaction/order";
    }

    @PostMapping("/stop/post")
    public String postStop(
                              @RequestParam Long orderId,
                              @RequestParam String title,
                              @RequestParam String context,
                              @RequestParam Double answer,
                              @RequestParam(value = "imageQuestion", required = false) MultipartFile imageQuestion,
                              @RequestParam(value = "imageAnswer", required = false) MultipartFile imageAnswer) throws IOException {
//        transactionService.create(orderId, title, context, answer, imageQuestion, imageAnswer);
        return "redirect:/transaction/stop";
    }

}
