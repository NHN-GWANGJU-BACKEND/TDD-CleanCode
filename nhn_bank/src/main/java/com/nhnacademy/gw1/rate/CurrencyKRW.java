package com.nhnacademy.gw1.rate;

import com.nhnacademy.gw1.Currency;
import com.nhnacademy.gw1.Money;

public class CurrencyKRW implements ExchangeRate{
    private final Currency currency = Currency.KRW;

    public Currency getCurrency() {
        return currency;
    }
    private final int KRW_USD = 1000;
    private final int KRW_EUR = 1500;

    @Override
    public void exchangeRateApply(Money money, Currency currency) {
        if(currency.equals(Currency.USD)) {
            money.setBalance(exchangeRateDecimalRound(money,KRW_USD));
        }else if(currency.equals(Currency.EUR)){
            money.setBalance(exchangeRateDecimalRound(money,KRW_EUR));
        }
    }

    private double exchangeRateDecimalRound(Money money,int exchangeRate) {
        return Math.round((money.getBalance() / exchangeRate) * 100) / 100.0;
    }
}
