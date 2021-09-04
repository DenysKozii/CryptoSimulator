package com.company.crypto.controller.rest;

import com.company.crypto.dto.OrderInfoDto;
import com.company.crypto.dto.TransactionDto;
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
    public List<TransactionDto> getTransactions() {
        return transactionService.getAllByUser();
    }

    @GetMapping("/order")
    public OrderInfoDto getOrderInfo(@RequestParam(defaultValue = BTCUSDT) String symbol) {
        return transactionService.getOrderInfo(symbol);
    }

    @PostMapping("/order/submit/{symbol}")
    public boolean submitOrder(@PathVariable String symbol,
                               @RequestParam String order,
                               @RequestParam Double usdt,
                               @RequestParam Double amount) {
        if (BUY.equals(order))
            return transactionService.buy(symbol, usdt, amount);
        else
            return transactionService.sell(symbol, usdt, amount);
    }

}
