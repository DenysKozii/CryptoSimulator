package com.company.crypto.service;

import com.binance.api.client.domain.event.CandlestickEvent;

import java.util.List;

public interface TradingService {

//    void buy(double decisionRate, WaveDto wave, boolean simulate);
//
//    void sell(double decisionRate, WaveDto wave, boolean simulate);
//
//    void writeAction(WaveDto wave, boolean simulate);
//
//    void decision(double decisionRate, WaveDto wave, boolean simulate);
//
//    double rate(WaveDto wave);
//
//    void trade(WaveDto wave, boolean simulate, boolean logTransactions);

    void startTrading();

    void writeResponse(CandlestickEvent candlestickEvent);

//    List<String> readFilenames();

}
