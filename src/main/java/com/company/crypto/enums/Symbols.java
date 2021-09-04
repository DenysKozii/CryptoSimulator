package com.company.crypto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Symbols {
//    DOGEUSDT,
//    BTCUSDT,
//    ETCUSDT,
//    ETHUSDT,
//    ADAUSDT,
//    BNBUSDT,
//    SOLUSDT,
//    DOTUSDT,
//    UNIUSDT,
//    LTCUSDT,
//    LUNAUSDT,
//    MATICUSDT,
//    XLMUSDT,
//    ATOMUSDT,
//    TRXUSDT,
//    XMRUSDT,
//    XTZUSDT,
//    NEOUSDT,
//    KSMUSDT,
//    XRPUSDT

    BTCUSDT("Bitcoin BTC/USDT", 0.0002),
    XRPUSDT("Ripple XRP/USDT", 10.0),
    DOGEUSDT("Dogecoin DOGE/USDT", 10.0),
    ETCUSDT("Ethereum Classic ETC/USDT", 10.0);

    private final String value;

    private final Double minimum;
}
