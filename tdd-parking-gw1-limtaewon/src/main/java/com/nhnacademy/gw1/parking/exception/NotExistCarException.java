package com.nhnacademy.gw1.parking.exception;

import com.nhnacademy.gw1.parking.car.Car;

public class NotExistCarException extends RuntimeException {
    public NotExistCarException(Car car) {
        super("not exist car in ParkingLot - carCode : " + car.getNumber());
    }
}
