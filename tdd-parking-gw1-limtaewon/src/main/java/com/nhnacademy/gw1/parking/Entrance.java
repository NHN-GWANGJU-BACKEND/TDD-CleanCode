package com.nhnacademy.gw1.parking;

import com.nhnacademy.gw1.parking.car.Car;
import com.nhnacademy.gw1.parking.car.LargeCar;
import com.nhnacademy.gw1.parking.exception.LargeParkingException;
import com.nhnacademy.gw1.parking.exception.NoEmptySpaceException;

import static com.nhnacademy.gw1.parking.config.Code.NOT_FOUND;

public class Entrance {

    private static Entrance entrance = new Entrance();

    private Entrance() {
    }

    public static Entrance getInstance() {
        return entrance;
    }

    public void scan(Car car) {
        ParkingSystem parkingSystem = ParkingSystem.getParkingSystem();
        ParkingLot parkingLot = parkingSystem.getParkingLot();
        int emptySpace = parkingLot.findEmptySpace();

        if (car instanceof LargeCar) {
            throw new LargeParkingException();
        }

        if (emptySpace == NOT_FOUND) {
            throw new NoEmptySpaceException();
        }

        parkingCar(car, parkingSystem, parkingLot, emptySpace);
    }

    private void parkingCar(Car car, ParkingSystem parkingSystem, ParkingLot parkingLot, int emptySpace) {
        String spaceCode = "A - " + (emptySpace + 1);
        parkingLot.getParkingSpaceList()[emptySpace] = new ParkingSpace(spaceCode, car);
    }
}
