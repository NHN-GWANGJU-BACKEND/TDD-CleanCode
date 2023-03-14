package com.nhnacademy.gw1.parking;

import com.nhnacademy.gw1.parking.car.Car;
import com.nhnacademy.gw1.parking.car.LargeCar;
import com.nhnacademy.gw1.parking.car.MiddleCar;
import com.nhnacademy.gw1.parking.exception.LargeParkingException;
import com.nhnacademy.gw1.parking.exception.NoEmptySpaceException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

class EntranceTest {
    private ParkingSystem parkingSystem;
    private ParkingLot parkingLot;
    private BigInteger parkingTime;


    @BeforeEach
    void setup() {
        parkingSystem = ParkingSystem.getParkingSystem();
        parkingLot = parkingSystem.getParkingLot();
        parkingLot.parkingSpaceClear();
        parkingTime = new BigInteger(String.valueOf(10));
    }

    @Test
    @DisplayName("차가 들어오고 비어있는 주차자리로 찾아가는지 테스트")
    void entrance_car() {
        User a1user = new User(false);
        Car a1car = new MiddleCar(a1user, parkingTime);

        parkingLot.entrance(a1car);
        assertThat(parkingLot.getParkingSpaceList()[0].getCode()).isEqualTo("A - 1");
        assertThat(parkingLot.getParkingSpaceList()[0].getCar()).isEqualTo(a1car);

        User a2user = new User(false);
        Car a2car = new MiddleCar(a2user, parkingTime);

        parkingLot.entrance(a2car);
        assertThat(parkingLot.getParkingSpaceList()[1].getCode()).isEqualTo("A - 2");
        assertThat(parkingLot.getParkingSpaceList()[1].getCar()).isEqualTo(a2car);
    }

    @Test
    void no_empty_space() {
        for (int i = 0; i < 25; i++) {
            parkingLot.entrance(new MiddleCar(new User(false), parkingTime));
        }

        Assertions.assertThatThrownBy(() -> parkingLot.entrance(new MiddleCar(new User(false), parkingTime)))
                .isInstanceOf(NoEmptySpaceException.class)
                .hasMessageContaining("is not empty");
    }

    @Test
    void cannot_park_large_car() {
        User user = new User(false);
        Car car = new LargeCar(user, parkingTime);

        Assertions.assertThatThrownBy(() -> parkingLot.entrance(car))
                .isInstanceOf(LargeParkingException.class)
                .hasMessageContaining("large car can't parking");
    }
}