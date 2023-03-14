package com.nhnacademy.gw1.parking;

import com.nhnacademy.gw1.parking.car.Car;
import com.nhnacademy.gw1.parking.car.ElectricCar;
import com.nhnacademy.gw1.parking.fee.FeeV1;
import com.nhnacademy.gw1.parking.fee.FeeV2;
import com.nhnacademy.gw1.parking.fee.ParkingFeePolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

class ParkingFeePolicyTest {
    private Car car;

    @BeforeEach
    void setup() {
        BigInteger parkingTime = new BigInteger(String.valueOf(10));
        User user = new User(false);
        car = new ElectricCar(user, parkingTime);
    }

    @Test
    @DisplayName("요금표 1에 대한 가격 테스트(주차시간 10초)")
    void calculate_version_feeV1() {
        FeeV1 feeV1 = new FeeV1();
        assertThat(feeV1.calculateTotalFee(car)).isEqualTo(1000);
    }

    @Test
    @DisplayName("요금표 2에 대한 가격 테스트(주차시간 10초)")
    void calculate_version_feeV2() {
        FeeV2 feeV2 = new FeeV2();
        assertThat(feeV2.calculateTotalFee(car)).isEqualTo(0);
    }

    @Test
    @DisplayName("차 주차시간 10초에 대한 boolean 테스트")
    void underDayFee() {
        ParkingFeePolicy feeV1AndV2 = new FeeV1();

        assertThat(feeV1AndV2.isOverBoundary(car, 0)).isTrue();
        assertThat(feeV1AndV2.isOverBoundary(car, 15)).isFalse();
        assertThat(feeV1AndV2.isOverBoundary(car, 10)).isTrue();
    }

}