package com.nhnacademy.gw1;

import com.nhnacademy.gw1.exception.InvalidMoneyException;
import com.nhnacademy.gw1.exception.TypeNotSameException;
import com.nhnacademy.gw1.symbol.Symbol;

import java.util.List;

public class Money {
    private double balance;
    private Currency currency;
    private String currencySymbol;
    private final List<Symbol> symbolList;

    public Money(String money, List<Symbol> symbolList) {
        this.symbolList = symbolList;
        settingTypeAndMoney(money);
    }

    public double getBalance() {
        return balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getSymbol() {
        return currencySymbol;
    }

    public void setCurrencyAndSymbol(Currency currency) {
        for (Symbol symbol : symbolList) {
            updateSymbol(currency, symbol);
        }
        this.currency = currency;
    }

    private void updateSymbol(Currency currency, Symbol symbol) {
        if (symbol.getCurrency().equals(currency)) {
            this.currencySymbol = symbol.getSymbol();
        }
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


    public void add(Money money) {
        if (money.currency != this.currency) {
            throw new TypeNotSameException(this.currency, money.currency);
        }
        addInvalidMoney(money);
        this.balance += money.balance;
    }

    private void addInvalidMoney(Money money) {
        if (this.balance + money.balance < 0) {
            throw new InvalidMoneyException(this.balance, money.getBalance());
        }

    }

    public void subtract(Money money) {
        if (money.currency != this.currency) {
            throw new TypeNotSameException(this.currency, money.currency);
        }
        subInvalidMoney(money);
        this.balance -= money.balance;
    }

    private void subInvalidMoney(Money money) {
        if (this.balance - money.balance < 0) {
            throw new InvalidMoneyException(this.balance, money.getBalance());
        }
    }

    public void settingTypeAndMoney(String originMoney) {
        currencySymbol = originMoney.substring(originMoney.length() - 1);
        this.balance = Double.parseDouble(originMoney.substring(0, originMoney.length() - 1));

        for (Symbol symbol : symbolList) {
            setCurrency(currencySymbol, symbol);
        }
    }

    private void setCurrency(String type, Symbol symbol) {
        if (type.equals(symbol.getSymbol())) {
            this.currency = symbol.getCurrency();
        }
    }

    public boolean equals(Money money) {
        if (this == money) return true;
        if (money == null || getClass() != money.getClass()) return false;
        return Double.compare(money.balance, balance) == 0 && currency == money.currency;
    }

    @Override
    public String toString() {
        if (currency == Currency.KRW) {
            return String.format("%.0f%s", balance, currencySymbol);
        } else {
            return String.format("%.2f%s", balance, currencySymbol);
        }
    }
}
