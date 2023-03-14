package com.nhnacademy.gw1.exception;

public class InvalidMoneyException extends RuntimeException {
    public InvalidMoneyException(double originBalance, double targetBalance) {
        super("invalid money \n this money :" + originBalance + " target money : " + targetBalance);
    }
}
