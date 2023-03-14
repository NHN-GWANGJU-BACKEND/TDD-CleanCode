package com.nhnacademy.gw1.parking;

import com.nhnacademy.gw1.parking.car.MiddleCar;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

class ParkingLotTest {
    private ParkingLot parkingLot;

    @BeforeEach
    void setup() {
        parkingLot = ParkingLot.getParkingLot();
        parkingLot.parkingSpaceClear();
    }

    @Test
    void get_parking_space_list() {
        Assertions.assertThat(parkingLot.getParkingSpaceList().length).isEqualTo(25);
    }

    @Test
    void find_empty_space() {
        for (int i = 0; i < 23; i++) {
            parkingLot.getParkingSpaceList()[i] = new ParkingSpace("A-1", new MiddleCar(new User(false), BigInteger.valueOf(1)));
        }
        Assertions.assertThat(parkingLot.findEmptySpace()).isEqualTo(23);
    }

    @Test
    void not_find_empty_space() {
        for (int i = 0; i < 25; i++) {
            parkingLot.getParkingSpaceList()[i] = new ParkingSpace("A-1", new MiddleCar(new User(false), BigInteger.valueOf(1)));
        }
        Assertions.assertThat(parkingLot.findEmptySpace()).isEqualTo(404);
    }
}