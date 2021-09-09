package com.company.crypto.service;

import com.company.crypto.dto.OrderInfoDto;
import com.company.crypto.dto.TransactionDto;
import com.company.crypto.enums.Symbols;

import java.util.List;

public interface TransactionService {

    boolean buy(String username, String symbol, Double usdt, Double amount);

    boolean sell(String username, String symbol, Double usdt, Double amount);

    boolean buyStop(String username, String symbol, Double amount, Double stop);

    boolean sellStop(String username, String symbol, Double amount, Double stop);

    OrderInfoDto getOrderInfo(String username, String symbol);

    List<TransactionDto> getAllByUser(String username, String currentUsername);
}
