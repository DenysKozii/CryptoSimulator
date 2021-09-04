package com.company.crypto.service;

import com.binance.api.client.domain.event.CandlestickEvent;

import java.util.List;

public interface TradingService {

    void startTrading();

    void saveResponse(CandlestickEvent candlestickEvent);

}
