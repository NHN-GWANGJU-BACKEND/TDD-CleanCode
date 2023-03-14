package com.nhnacademy.gw1.parking.exception;

public class NoEmptySpaceException extends RuntimeException {
    public NoEmptySpaceException() {
        super("parking space is not empty");
    }
}
