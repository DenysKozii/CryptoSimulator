package com.company.crypto.config;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiWebSocketClient;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
public class BinanceApiWebSocketClientConfig {
    @Bean
    public BinanceApiWebSocketClient webSocketClient(){
        return BinanceApiClientFactory.newInstance().newWebSocketClient();
    }
}
