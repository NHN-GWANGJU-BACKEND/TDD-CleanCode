package com.nhnacademy.gw1.rate;

import com.nhnacademy.gw1.Currency;
import com.nhnacademy.gw1.Money;

public class CurrencyUSD implements ExchangeRate {
    private final Currency currency = Currency.USD;
    private final double USD_KRW = 1000;
    private final double USD_EUR = 0.67;


    public Currency getCurrency() {
        return currency;
    }

    @Override
    public void exchangeRateApply(Money money, Currency currency) {
        if (currency.equals(Currency.KRW)) {
            money.setBalance(exchangeRateDecimalRound(money, USD_KRW));
        } else if (currency.equals(Currency.EUR)) {
            money.setBalance(exchangeRateDecimalRound(money, USD_EUR));
        }
    }

    private double exchangeRateDecimalRound(Money money, double exchangeRate) {
        return Math.round(money.getBalance() * 100) / 100.0 * exchangeRate;
    }
}
