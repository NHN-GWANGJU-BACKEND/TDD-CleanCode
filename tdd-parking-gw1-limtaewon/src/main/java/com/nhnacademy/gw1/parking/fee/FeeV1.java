package com.nhnacademy.gw1.parking.fee;

import com.nhnacademy.gw1.parking.car.Car;

import java.math.BigInteger;

public class FeeV1 extends ParkingFeePolicy {
    private final int DAY_FEE = 10000;
    private final int INIT_FEE = 1000;
    private final int TEN_MINUTE_FEE = 500;
    private final int DAY = 86400;
    private final int LIMIT_TIME = 12600;
    private final int THIRTY_MINUTE = 1800;
    private final int TEN_MINUTE = 600;

    @Override
    int calculateDatePerFee(Car car, int totalFee) {
        if (isOverBoundary(car, LIMIT_TIME)) {
            car.updateParkingTime(BigInteger.valueOf(DAY));
            totalFee += DAY_FEE;
        } else {
            totalFee = underDayFee(car, totalFee);
        }
        return totalFee;
    }

    @Override
    int underDayFee(Car car, int totalFee) {
        car.updateParkingTime(BigInteger.valueOf(THIRTY_MINUTE));
        totalFee += INIT_FEE;

        while (isOverBoundary(car, 0)) {
            car.updateParkingTime(BigInteger.valueOf(TEN_MINUTE));
            totalFee += TEN_MINUTE_FEE;
        }
        return totalFee;
    }
}
