package com.nhnacademy.gw1;

import com.nhnacademy.gw1.exception.TypeSameException;
import com.nhnacademy.gw1.rate.ExchangeRate;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Bank {
    private List<ExchangeRate> exchangeRateList;

    public Bank(List<ExchangeRate> exchangeRateList) {
        this.exchangeRateList = exchangeRateList;
    }

    public ExchangeFee exchangeCurrency(Money money, Currency currency) {
        if (currency.equals(money.getCurrency())) {
            throw new TypeSameException(money.getCurrency(), currency);
        } else {
            ExchangeFee exchangeFee = new ExchangeFee(money);
            exchangeMoney(money, currency);
            log.info(exchangeFee.toString());
            return exchangeFee;
        }
    }

    private void exchangeMoney(Money money, Currency currency) {
        exchangeRateApply(money, currency);
        money.setCurrencyAndSymbol(currency);
    }

    public void exchangeRateApply(Money money, Currency currency) {
        for (ExchangeRate exchangeRate : exchangeRateList) {
            findSuitableRate(money, currency, exchangeRate);
        }
    }

    private void findSuitableRate(Money money, Currency currency, ExchangeRate exchangeRate) {
        if (exchangeRate.getCurrency().equals(money.getCurrency())) {
            exchangeRate.exchangeRateApply(money, currency);
        }
    }
}
