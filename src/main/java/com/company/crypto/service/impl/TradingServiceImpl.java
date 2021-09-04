package com.company.crypto.service.impl;

import com.binance.api.client.BinanceApiWebSocketClient;
import com.binance.api.client.domain.event.CandlestickEvent;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.company.crypto.entity.Price;
import com.company.crypto.enums.Symbols;
import com.company.crypto.repository.PriceRepository;
import com.company.crypto.service.TradingService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor
public class TradingServiceImpl implements TradingService {
    private final BinanceApiWebSocketClient webSocketClient;
    private final PriceRepository priceRepository;
    private final Double START_USDT = 1000.0;
    private final Double TAX = 0.00075;

    private Double USDT = START_USDT;
    private Double totalUsdt = START_USDT;
    private Double amount = 0.0;
    private Double firstClose = 0.0;
    private Double lastClose = 0.0;
    private Double value = 0.0;
    private Double close = 0.0;

    private String SYMBOL = "DOGEUSDT";


//    @Override
//    public void trade(WaveDto wave, boolean simulate, boolean logTransactions) {
//        wave.setClose(Double.valueOf(wave.getCandlestickEvent().getClose()));
//        if (firstClose == 0.0)
//            firstClose = wave.getClose();
//        lastClose = wave.getClose();
//
//
//        double decisionRate = rate(wave);
//        decision(decisionRate, wave, simulate);
//    }

    @Override
    public void startTrading() {
        for (Symbols symbols: Symbols.values()) {
            webSocketClient.onCandlestickEvent(symbols.name().toLowerCase(), CandlestickInterval.ONE_MINUTE, this::writeResponse);
        }
    }

    @Override
    public void writeResponse(CandlestickEvent candlestickEvent) {
        String symbol = candlestickEvent.getSymbol();
        double close = Double.parseDouble(candlestickEvent.getClose());

        Price price = priceRepository.findBySymbol(symbol)
                .orElse(new Price(symbol));
        price.setPrice(close);
        price.setMinimum(Symbols.valueOf(symbol).getMinimum());
        priceRepository.save(price);
    }

//    @Override
//    public void buy(double decisionRate, WaveDto wave, boolean simulate) {
//        double close = Double.parseDouble(wave.getCandlestickEvent().getClose());
//        double delta = -USDT * decisionRate;
//        double deltaAmount = -delta / close;
//        totalUsdt = USDT + amount * close;
//        if (deltaAmount > 10) {
//            USDT += delta + delta * TAX;
//            amount += deltaAmount;
//            totalUsdt = USDT + amount * close;
//            writeAction(wave, simulate);
//        }
//    }
//
//    @Override
//    public void sell(double decisionRate, WaveDto wave, boolean simulate) {
//        double close = Double.parseDouble(wave.getCandlestickEvent().getClose());
//        double delta = -decisionRate * amount;
//        totalUsdt = USDT + amount * close;
//        if (delta > 10) {
//            USDT += delta * close - delta * close * TAX;
//            amount -= delta;
//            totalUsdt = USDT + amount * close;
//            writeAction(wave, simulate);
//        }
//    }
//
//    @Override
//    public void writeAction(WaveDto wave, boolean simulate) {
//        LocalTime time = LocalTime.now();
//        Double passiveUSDT = START_USDT * (lastClose / firstClose);
//
//        if (simulate)
//            System.out.printf("%s:time = %s, price = %s, total usdt = %s, passive usdt = %s%n",
//                    wave.getAction(), wave.getCandlestickEvent().getQuoteAssetVolume(), wave.getClose(), totalUsdt, passiveUSDT);
////        return;
//        else
//            System.out.printf("%s:time = %s, price = %s, total usdt = %s, passive usdt = %s%n",
//                    wave.getAction(), time, wave.getClose(), totalUsdt, passiveUSDT);
//    }
}