package com.company.crypto.service;

import com.binance.api.client.domain.event.CandlestickEvent;
import com.company.crypto.dto.TransactionDto;

import java.util.List;

public interface TransactionService {

    Double getAvailable();

    Double getCurrentAsset(String symbol);

    Double getPrice(String symbol);

    boolean buy(String symbol, Double amount);

    boolean sell(String symbol, Double amount);

    boolean buyStop(String symbol, Double amount, Double stop);

    boolean sellStop(String symbol, Double amount, Double stop);

    List<TransactionDto> getAllByUser();
}
