package com.nhnacademy.gw1.parking.fee;

import com.nhnacademy.gw1.parking.car.Car;

import java.math.BigInteger;

public abstract class ParkingFeePolicy {
    abstract int calculateDatePerFee(Car car, int totalFee);

    abstract int underDayFee(Car car, int totalFee);

    public int calculateTotalFee(Car car) {
        int totalFee = 0;
        while (isOverBoundary(car, 0)) {
            totalFee = calculateDatePerFee(car, totalFee);
        }
        return totalFee;
    }

    public boolean isOverBoundary(Car car, int time) {
        if (car.getParkingTimeSec().compareTo(BigInteger.valueOf(0)) == 0) {
            return false;
        }
        return car.getParkingTimeSec().compareTo(BigInteger.valueOf(time)) >= 0;
    }
}
