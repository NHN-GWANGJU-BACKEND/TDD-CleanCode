package com.nhnacademy.gw1.parking.exception;

public class LargeParkingException extends RuntimeException {
    public LargeParkingException() {
        super("large car can't parking");
    }
}
