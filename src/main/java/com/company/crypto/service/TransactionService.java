package com.company.crypto.service;

import com.company.crypto.dto.OrderInfoDto;
import com.company.crypto.dto.TransactionDto;

import java.util.List;

public interface TransactionService {

    boolean buy(String symbol, Double usdt, Double amount);

    boolean sell(String symbol, Double usdt, Double amount);

    boolean buyStop(String symbol, Double amount, Double stop);

    boolean sellStop(String symbol, Double amount, Double stop);

    List<TransactionDto> getAllByUser();

    OrderInfoDto getOrderInfo(String symbol);
}
