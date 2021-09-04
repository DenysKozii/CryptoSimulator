package com.company.crypto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Symbols {
    DOGEUSDT("Dogecoin DOGE/USDT"),
    BTCUSDT("Bitcoin BTC/USDT"),
    ETCUSDT("Ethereum Classic ETC/USDT"),
    XRPUSDT("Ripple XRP/USDT");

    private final String value;
}
