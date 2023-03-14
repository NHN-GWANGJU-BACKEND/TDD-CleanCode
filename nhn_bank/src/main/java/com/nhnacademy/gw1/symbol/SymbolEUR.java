package com.nhnacademy.gw1.symbol;

import com.nhnacademy.gw1.Currency;

public class SymbolEUR implements Symbol{
    private final String symbol = "€";
    private final Currency currency = Currency.EUR;

    @Override
    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }
}
