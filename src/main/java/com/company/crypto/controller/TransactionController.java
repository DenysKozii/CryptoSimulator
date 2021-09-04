package com.company.crypto.controller;

import com.company.crypto.dto.TransactionDto;
import com.company.crypto.service.AuthorizationService;
import com.company.crypto.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
@AllArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {
    private final AuthorizationService authorizationService;
    private final TransactionService transactionService;

    private final static String BUY = "buy";

    @GetMapping("/list")
    public String getTransactions(Model model) {
        List<TransactionDto> transactions = transactionService.getAllByUser();
        model.addAttribute("transactions", transactions);
        return "transactions";
    }


    @GetMapping("/order/{symbolVariable}")
    public String getOrderEditor(@PathVariable(required = false) String symbolVariable,
                                 @RequestParam(required = false) String symbol,
                                 Model model) {
        Double price;
        Double currentAsset;
        if (symbol != null) {
            price = transactionService.getPrice(symbol);
            currentAsset = transactionService.getCurrentAsset(symbol);
            model.addAttribute("symbol", symbol);
        } else {
            price = transactionService.getPrice(symbolVariable);
            currentAsset = transactionService.getCurrentAsset(symbolVariable);
            model.addAttribute("symbol", symbolVariable);
        }

        Double available = transactionService.getAvailable();

        model.addAttribute("price", price);
        model.addAttribute("available", available);
        model.addAttribute("currentAsset", currentAsset);
        return "orderEditor";
    }


    @PostMapping("/order/submit/{symbol}")
    public String submitOrder(@PathVariable String symbol,
                              @RequestParam String order,
                              @RequestParam Double amount) {
        if (BUY.equals(order))
            transactionService.buy(symbol, amount);
        else
            transactionService.sell(symbol, amount);
        return "redirect:/transaction/order/" + symbol;
    }


//    @GetMapping("/stop/{symbolVariable}")
//    public String getStopEditor(@PathVariable(required = false) String symbolVariable,
//                                @RequestParam(required = false) String symbol,
//                                Model model) {
//        Double price;
//        Double currentAsset;
//        if (symbol != null) {
//            price = transactionService.getPrice(symbol);
//            currentAsset = transactionService.getCurrentAsset(symbol);
//            model.addAttribute("symbol", symbol);
//        } else {
//            price = transactionService.getPrice(symbolVariable);
//            currentAsset = transactionService.getCurrentAsset(symbolVariable);
//            model.addAttribute("symbol", symbolVariable);
//        }
//
//        Double available = transactionService.getAvailable();
//
//        model.addAttribute("price", price);
//        model.addAttribute("available", available);
//        model.addAttribute("currentAsset", currentAsset);
//        return "stopEditor";
//    }
//
//    @PostMapping("/stop/submit/{symbol}")
//    public String submitStop(@PathVariable String symbol,
//                             @RequestParam String order,
//                             @RequestParam Double stop,
//                             @RequestParam Double amount) {
//        if (BUY.equals(order))
//            transactionService.buyStop(symbol, amount, stop);
//        else
//            transactionService.sellStop(symbol, amount, stop);
//        return "redirect:/transaction/stop/" + symbol;
//    }

}
