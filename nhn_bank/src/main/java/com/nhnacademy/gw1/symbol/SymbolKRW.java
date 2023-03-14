package com.nhnacademy.gw1.symbol;

import com.nhnacademy.gw1.Currency;

public class SymbolKRW implements Symbol{
    private final String symbol = "원";
    private final Currency currency = Currency.KRW;
    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public Currency getCurrency() {
        return currency;
    }
}
