package com.nhnacademy.gw1.rate;

import com.nhnacademy.gw1.Currency;
import com.nhnacademy.gw1.Money;

public interface ExchangeRate {
    void exchangeRateApply(Money money, Currency currency);
    Currency getCurrency();
}
