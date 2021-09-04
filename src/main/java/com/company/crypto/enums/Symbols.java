package com.company.crypto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Symbols {
    BTCUSDT("Bitcoin BTC/USDT", 0.0002),
    XRPUSDT("Ripple XRP/USDT", 10.0),
    DOGEUSDT("Dogecoin DOGE/USDT", 10.0),
    ETCUSDT("Ethereum Classic ETC/USDT", 10.0);

    private final String value;

    private final Double minimum;
}
