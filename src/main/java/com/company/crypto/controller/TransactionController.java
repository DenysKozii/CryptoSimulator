package com.company.crypto.controller;

import com.company.crypto.service.AuthorizationService;
import com.company.crypto.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@AllArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {
    private final AuthorizationService authorizationService;
    private final TransactionService transactionService;

    private final static String BUY = "buy";

    @GetMapping("/order/{symbol}")
    public String getOrderEditor(@PathVariable String symbol, Model model) {
        Double price = transactionService.getPrice(symbol);
        Double available = transactionService.getAvailable();
        Double currentAsset = transactionService.getCurrentAsset(symbol);
        model.addAttribute("price", price);
        model.addAttribute("available", available);
        model.addAttribute("currentAsset", currentAsset);
        return "orderEditor";
    }

    @GetMapping("/stop/{symbol}")
    public String getStopEditor(@PathVariable String symbol, Model model) {
        Double price = transactionService.getPrice(symbol);
        Double available = transactionService.getAvailable();
        Double currentAsset = transactionService.getCurrentAsset(symbol);
        model.addAttribute("price", price);
        model.addAttribute("available", available);
        model.addAttribute("currentAsset", currentAsset);
        return "stopEditor";
    }

    @PostMapping("/order/submit")
    public String buyOrder(@RequestParam String symbol, @RequestParam String order, @RequestParam Double amount) {
        if (BUY.equals(order))
            transactionService.buy(symbol, amount);
        else
            transactionService.sell(symbol, amount);
        return "redirect:/transaction/order/" + symbol;
    }

    @PostMapping("/order/sell")
    public String sellOrder(@RequestParam String symbol, @RequestParam Double amount) {
        transactionService.sell(symbol, amount);
        return "redirect:/transaction/order/" + symbol;
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
