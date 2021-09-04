package com.company.crypto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Symbols {
    DOGEUSDT("Dogecoin DOGE/USDT", 75.0, 630000.0),
    BTCUSDT("Bitcoin BTC/USDT", 0.0003, 50.0),
    ETHUSDT("Ethereum ETH/USDT", 0.006, 1500.0),
    ETCUSDT("Ethereum Classic ETC/USDT", 0.4, 3500.0),
    XRPUSDT("Ripple XRP/USDT", 20.0, 1000000.0),
    ADAUSDT("Cardano ADA/USDT", 10.0, 300000.0),
    BNBUSDT("Binance Coin BNB/USDT", 0.05, 3000.0),
    SOLUSDT("Solana SOL/USDT", 1.0, 6000.0),
    DOTUSDT("Polcadot DOT/USDT", 0.4, 18000.0),
    UNIUSDT("Uniswap UNI/USDT", 0.5, 25000.0),
    LTCUSDT("Litecoin LTC/USDT", 0.06, 5000.0),
    ATOMUSDT("Cosmos ATOM/USDT", 2.0, 16000.0),
    MATICUSDT("Polygon MATIC/USDT", 10.0, 500000.0),
    THETAUSDT("Theta THETA/USDT", 2.4, 16000.0),
    LINKUSDT("Chainlink LINK/USDT", 0.5, 7000.0),
    TRXUSDT("TRON TRX/USDT", 205.0, 3500000.0),
    XMRUSDT("Monero XMR/USDT", 0.1, 175.0),
    XTZUSDT("Tezos XTZ/USDT", 3.0, 25000.0),
    EOSUSDT("EOS.IO EOS/USDT", 3.0, 150000.0),
    VETUSDT("VeChain VET/USDT", 200.0, 1500000.0),
    ICPUSDT("Internet Computer ICP/USDT", 0.36, 2400.0);


    private final String value;

    private final Double minimum;
    private final Double maximum;
}
