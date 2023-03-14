package com.nhnacademy.gw1.symbol;

import com.nhnacademy.gw1.Currency;

public class SymbolUSD implements Symbol{
    private final String symbol = "$";
    private final Currency currency = Currency.USD;

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }
}
