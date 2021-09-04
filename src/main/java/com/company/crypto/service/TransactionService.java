package com.company.crypto.service;

import com.binance.api.client.domain.event.CandlestickEvent;

public interface TransactionService {

    Double getAvailable();

    Double getCurrentAsset(String symbol);

    Double getPrice(String symbol);
}
