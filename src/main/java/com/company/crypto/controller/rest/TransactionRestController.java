package com.company.crypto.controller.rest;

import com.company.crypto.dto.OrderInfoDto;
import com.company.crypto.dto.TransactionDto;
import com.company.crypto.enums.Action;
import com.company.crypto.enums.Symbols;
import com.company.crypto.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public List<TransactionDto> getUserTransactions(@RequestParam(required = false) String username) {
        return transactionService.getAllByUser(username);
    }

    @GetMapping("/order")
    public OrderInfoDto getOrderInfo(@RequestParam(defaultValue = BTCUSDT) Symbols symbol) {
        return transactionService.getOrderInfo(symbol.getValue());
    }

    @PostMapping("/order/submit")
    public boolean submitOrder(@RequestParam Symbols symbol,
                               @RequestParam Action order,
                               @RequestParam(required = false) Double usdt,
                               @RequestParam(required = false) Double amount) {
        if (Action.BUY.equals(order))
            return transactionService.buy(symbol.getValue(), usdt, amount);
        else
            return transactionService.sell(symbol.getValue(), usdt, amount);
    }

    @PostMapping("/stop/submit")
    public boolean submitStop(@RequestParam String symbol,
                              @RequestParam Action order,
                              @RequestParam(required = false) Double usdt,
                              @RequestParam(required = false) Double amount) {
        if (Action.BUY.equals(order))
            return transactionService.buy(symbol, usdt, amount);
        else
            return transactionService.sell(symbol, usdt, amount);
    }

}
