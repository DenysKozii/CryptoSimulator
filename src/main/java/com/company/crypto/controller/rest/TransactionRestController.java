package com.company.crypto.controller.rest;

import com.company.crypto.dto.OrderDto;
import com.company.crypto.dto.OrderInfoDto;
import com.company.crypto.dto.TransactionDto;
import com.company.crypto.dto.UserRequest;
import com.company.crypto.enums.Action;
import com.company.crypto.enums.Symbols;
import com.company.crypto.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/transaction")
public class TransactionRestController {
    private final TransactionService transactionService;

    private final static String BUY = "buy";
    private final static String BTCUSDT = "BTCUSDT";

    @GetMapping("/list")
    public List<TransactionDto> getUserTransactions(@RequestBody UserRequest user) {
        return transactionService.getAllByUser(user.getUsername());
    }

    @GetMapping("/order")
    public OrderInfoDto getOrderInfo(@RequestParam(defaultValue = BTCUSDT) Symbols symbol) {
        return transactionService.getOrderInfo(symbol.getValue());
    }

    @PostMapping("/order/submit")
    public boolean submitOrder(@Valid OrderDto order) {
        if (Action.BUY.equals(order.getOrder()))
            return transactionService.buy(order.getSymbol().getValue(), order.getUsdt(), order.getAmount());
        else
            return transactionService.sell(order.getSymbol().getValue(), order.getUsdt(), order.getAmount());
    }

    @PostMapping("/stop/submit")
    public boolean submitStop(@Valid OrderDto order) {
        if (Action.BUY.equals(order.getOrder()))
            return transactionService.buy(order.getSymbol().getValue(), order.getUsdt(), order.getAmount());
        else
            return transactionService.sell(order.getSymbol().getValue(), order.getUsdt(), order.getAmount());
    }

}
