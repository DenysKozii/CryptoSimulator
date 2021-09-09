package com.company.crypto.utils;

import com.company.crypto.service.TradingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Trade {
    private final TradingService tradingService;

    @EventListener(ApplicationReadyEvent.class)
    public void firstInit() {
        tradingService.startTrading();
    }
}
