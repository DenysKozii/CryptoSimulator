package com.company.crypto.controller.rest;

import com.company.crypto.dto.OrderDto;
import com.company.crypto.dto.OrderInfoDto;
import com.company.crypto.dto.TransactionDto;
import com.company.crypto.dto.UserRequest;
import com.company.crypto.entity.User;
import com.company.crypto.enums.Action;
import com.company.crypto.enums.Symbols;
import com.company.crypto.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/transaction")
public class TransactionRestController {
    private final TransactionService transactionService;

    private final static String BTCUSDT = "BTCUSDT";

    @PostMapping("/list")
    public List<TransactionDto> getUserTransactions(@RequestBody UserRequest userRequest,
                                                    @AuthenticationPrincipal User user) {
        return transactionService.getAllByUser(userRequest.getUsername(), user.getUsername());
    }

    @GetMapping("/order")
    public OrderInfoDto getOrderInfo(@RequestParam String symbol,
                                     @AuthenticationPrincipal User user) {
        try {
            return transactionService.getOrderInfo(user.getUsername(), symbol);
        } catch (Exception e){
            return transactionService.getOrderInfo(user.getUsername(), BTCUSDT);
        }
    }

    @PostMapping("/order/submit")
    public boolean submitOrder(@RequestBody OrderDto order,
                               @AuthenticationPrincipal User user) {
        if (Action.BUY.equals(order.getOrder()))
            return transactionService.buy(user.getUsername(),order.getSymbol().name(), order.getUsdt(), order.getAmount());
        else
            return transactionService.sell(user.getUsername(),order.getSymbol().name(), order.getUsdt(), order.getAmount());
    }

    @PostMapping("/stop/submit")
    public boolean submitStop(@RequestBody OrderDto order,
                              @AuthenticationPrincipal User user) {
        if (Action.BUY.equals(order.getOrder()))
            return transactionService.buy(user.getUsername(),order.getSymbol().name(), order.getUsdt(), order.getAmount());
        else
            return transactionService.sell(user.getUsername(),order.getSymbol().name(), order.getUsdt(), order.getAmount());
    }

}
