package com.nhnacademy.gw1.parking.car;

import com.nhnacademy.gw1.parking.User;
import com.nhnacademy.gw1.parking.config.Code;

import java.math.BigInteger;
import java.util.Objects;

public abstract class Car {
    private final long number;
    private final User user;
    private BigInteger parkingTimeSec;

    public Car(User user, BigInteger parkingTimeSec) {
        this.number = Code.carCode++;
        this.user = user;
        this.parkingTimeSec = parkingTimeSec;
    }

    public long getNumber() {
        return number;
    }

    public BigInteger getParkingTimeSec() {
        return parkingTimeSec;
    }

    public void updateParkingTime(BigInteger parkingTimeSec) {
        preventMinusTimeSec(parkingTimeSec);
    }

    private void preventMinusTimeSec(BigInteger parkingTimeSec) {
        if (this.parkingTimeSec.compareTo(parkingTimeSec) < 0) {
            this.parkingTimeSec = BigInteger.valueOf(0);
        } else {
            this.parkingTimeSec = this.parkingTimeSec.subtract(parkingTimeSec);
        }
    }

    public User getUser() {
        return user;
    }


    public boolean equals(Car car) {
        if (this == car) return true;
        if (car == null || getClass() != car.getClass()) return false;
        return number == car.number && Objects.equals(user, car.user) && Objects.equals(parkingTimeSec, car.parkingTimeSec);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, user, parkingTimeSec);
    }
}
