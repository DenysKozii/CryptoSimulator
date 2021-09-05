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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Data
@Slf4j
@RequiredArgsConstructor
public class TradingServiceImpl implements TradingService {
    private final BinanceApiWebSocketClient webSocketClient;
    private final PriceRepository priceRepository;

    @Override
    public void startTrading() {
        log.info("Trading started");
        for (Symbols symbols: Symbols.values()) {
            webSocketClient.onCandlestickEvent(symbols.name().toLowerCase(), CandlestickInterval.ONE_MINUTE, this::saveResponse);
        }
    }

    @Override
    public void saveResponse(CandlestickEvent candlestickEvent) {
        String symbol = candlestickEvent.getSymbol();
        Double close = Double.parseDouble(candlestickEvent.getClose());

        Price price = priceRepository.findBySymbol(symbol)
                .orElse(new Price(symbol));
        price.setPrice(close);
        price.setMinimum(Symbols.valueOf(symbol).getMinimum());
        priceRepository.save(price);
    }
}