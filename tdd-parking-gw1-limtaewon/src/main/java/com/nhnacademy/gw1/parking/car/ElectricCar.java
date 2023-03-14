package com.nhnacademy.gw1.parking.car;

import com.nhnacademy.gw1.parking.User;

import java.math.BigInteger;

public class ElectricCar extends Car {
    public ElectricCar(User user, BigInteger parkingTimeSec) {
        super(user, parkingTimeSec);
    }
}
