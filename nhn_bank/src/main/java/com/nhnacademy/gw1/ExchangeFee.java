package com.nhnacademy.gw1;

import com.nhnacademy.gw1.symbol.Symbol;

public class ExchangeFee {
    private double COMMISSION_RATE = 5;
    private double fee;
    private String symbol;

    public double getFee() { return fee; }

    public ExchangeFee(Money money) {
        this.fee = money.getBalance() * (COMMISSION_RATE/100);
        this.symbol = money.getSymbol();
    }

    @Override
    public String toString() {
        return "ExchangeFee{" +
                "COMMISSION_RATE=" + COMMISSION_RATE +
                "%, fee=" + fee + symbol +
                '}';
    }
}
