package com.nhnacademy.gw1.parking;

import com.nhnacademy.gw1.parking.car.Car;
import com.nhnacademy.gw1.parking.coupon.Coupon;
import com.nhnacademy.gw1.parking.exception.NotExistCarException;
import com.nhnacademy.gw1.parking.fee.ParkingFeePolicy;

import static com.nhnacademy.gw1.parking.config.Code.NOT_FOUND;

public class ParkingLot {
    private ParkingSpace[] parkingSpaceList;
    private final static ParkingLot parkingLot = new ParkingLot();

    private ParkingLot() {
        parkingSpaceList = new ParkingSpace[25];
    }

    public static ParkingLot getParkingLot() {
        return parkingLot;
    }

    public ParkingSpace[] getParkingSpaceList() {
        return parkingSpaceList;
    }

    public int findEmptySpace() {
        for (int i = 0; i < parkingSpaceList.length; i++) {
            if (parkingSpaceList[i] == null) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    public void entrance(Car car) {
        Entrance entrance = Entrance.getInstance();
        entrance.scan(car);
    }

    public void exit(Car car, ParkingFeePolicy parkingFeePolicy, Coupon coupon) {
        boolean isExistCar = false;
        for (int i = 0; i < parkingSpaceList.length; i++) {
            isExistCar = isExistCar(car, isExistCar, i);
        }
        if (!isExistCar) {
            throw new NotExistCarException(car);
        }

        for (int i = 0; i < parkingSpaceList.length; i++) {
            extractExitCarSpace(car, i);
        }
        Exit exit = Exit.getInstance();
        exit.pay(car, parkingFeePolicy, coupon);
    }

    private boolean isExistCar(Car car, boolean isExistCar, int i) {
        if (parkingSpaceList[i] != null && parkingSpaceList[i].getCar().equals(car)) {
            isExistCar = true;
        }
        return isExistCar;
    }

    private void extractExitCarSpace(Car car, int i) {
        if (parkingSpaceList[i] != null && parkingSpaceList[i].getCar().equals(car)) {
            parkingSpaceList[i] = null;
        }
    }

    public void parkingSpaceClear() {
        this.parkingSpaceList = new ParkingSpace[25];
    }


}
