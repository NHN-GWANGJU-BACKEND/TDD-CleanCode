package com.nhnacademy.gw1.parking.exception;

public class CannotExitException extends RuntimeException {
    public CannotExitException(double originBalance, double targetBalance) {
        super("invalid money \n " +
                "this money :" + originBalance + " target money : " + targetBalance
                + "\n can't exit ParkingLot!!!");
    }
}
