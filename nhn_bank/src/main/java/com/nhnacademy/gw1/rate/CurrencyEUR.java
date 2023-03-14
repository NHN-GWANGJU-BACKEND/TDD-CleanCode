package com.nhnacademy.gw1.rate;

import com.nhnacademy.gw1.Currency;
import com.nhnacademy.gw1.Money;

public class CurrencyEUR implements ExchangeRate{
    private final Currency currency = Currency.EUR;

    public Currency getCurrency() {
        return currency;
    }

    private final double EUR_KRW = 1500;
    private final double EUR_USD = 1.5;

    @Override
    public void exchangeRateApply(Money money, Currency currency) {
        if(currency.equals(Currency.KRW)) {
            money.setBalance(exchangeRateDecimalRound(money,EUR_KRW));
        }else if(currency.equals(Currency.USD)){
            money.setBalance(exchangeRateDecimalRound(money,EUR_USD));
        }
    }

    private double exchangeRateDecimalRound(Money money, double exchangeRate) {
        return Math.round((money.getBalance() / 100) * 100) * exchangeRate;
    }
}
