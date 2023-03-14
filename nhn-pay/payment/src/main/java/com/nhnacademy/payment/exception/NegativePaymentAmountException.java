package com.nhnacademy.payment.exception;

public class NegativePaymentAmountException extends RuntimeException {
    public NegativePaymentAmountException() {
    }

    public NegativePaymentAmountException(long amount) {
        super("amount is negative " + amount);
    }
}
