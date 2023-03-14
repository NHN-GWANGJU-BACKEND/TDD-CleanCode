package com.nhnacademy.gw1.parking;

import com.nhnacademy.gw1.parking.car.Car;
import com.nhnacademy.gw1.parking.car.ElectricCar;
import com.nhnacademy.gw1.parking.car.LightCar;
import com.nhnacademy.gw1.parking.car.MiddleCar;
import com.nhnacademy.gw1.parking.exception.CannotExitException;
import com.nhnacademy.gw1.parking.exception.NotExistCarException;
import com.nhnacademy.gw1.parking.fee.FeeV1;
import com.nhnacademy.gw1.parking.fee.FeeV2;
import com.nhnacademy.gw1.parking.fee.ParkingFeePolicy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigInteger;


class ExitTest {
    private ParkingSystem parkingSystem;
    private ParkingLot parkingLot;
    private User user;

    @BeforeEach
    void setup() {
        parkingSystem = ParkingSystem.getParkingSystem();
        parkingLot = parkingSystem.getParkingLot();
        parkingLot.parkingSpaceClear();
        user = new User(false);
    }

    @ParameterizedTest
    @CsvSource({
            "1800,19000",
            "1801,18500",
            "3000,18000",
            "3660,17000",
            "21600,10000"
    })
    @DisplayName("돈을 내고 나가는 테스트(요금표1 버전)")
    void enough_money_pay_fee_version1(int timeSec, int expect) {
        BigInteger parkingTime = new BigInteger(String.valueOf(timeSec));
        Car car = new MiddleCar(user, parkingTime);
        ParkingFeePolicy parkingFeePolicy = new FeeV1();

        parkingLot.entrance(car);

        parkingLot.exit(car, parkingFeePolicy, null);

        Assertions.assertThat(car.getUser().getWallet().getMoney()).isEqualTo(expect);
        Assertions.assertThat(parkingLot.getParkingSpaceList()[0]).isNull();
    }

    @ParameterizedTest
    @CsvSource({
            "1800,20000",
            "1801,19000",
            "3600,19000",
            "3601,18500",
            "14400,10000",
            "20400,5000"
    })
    @DisplayName("돈을 내고 나가는 테스트(요금표2 버전)")
    void enough_money_pay_fee_version2(int timeSec, int expect) {
        BigInteger parkingTime = new BigInteger(String.valueOf(timeSec));
        Car car = new ElectricCar(user, parkingTime);
        ParkingFeePolicy parkingFeePolicy = new FeeV2();

        parkingLot.entrance(car);

        parkingLot.exit(car, parkingFeePolicy, null);

        Assertions.assertThat(car.getUser().getWallet().getMoney()).isEqualTo(expect);
        Assertions.assertThat(parkingLot.getParkingSpaceList()[0]).isNull();
    }

    @Test
    @DisplayName("돈이 없어 나가지 못하는 테스트")
    void no_money_cannot_exit() {
        BigInteger parkingTime = new BigInteger(String.valueOf(10000000));
        Car car = new MiddleCar(user, parkingTime);
        ParkingFeePolicy parkingFeePolicy = new FeeV1();

        parkingLot.entrance(car);

        Assertions.assertThatThrownBy(() -> parkingLot.exit(car, parkingFeePolicy, null))
                .isInstanceOf(CannotExitException.class)
                .hasMessageContaining("can't exit");
    }


    @Test
    @DisplayName("차가 나간 후 가장 빠르게 도착하는 주차공간을 다음차에 제공")
    void exit_car_found_empty_space() {
        BigInteger parkingTime = new BigInteger(String.valueOf(1801));
        Car car = new MiddleCar(user, parkingTime);
        ParkingFeePolicy parkingFeePolicy = new FeeV1();

        for (int i = 0; i < 10; i++) {
            if (i == 5) {
                parkingLot.entrance(car);
            }
            parkingLot.entrance(new MiddleCar(user, parkingTime));
        }

        parkingLot.exit(car, parkingFeePolicy, null);

        Assertions.assertThat(parkingLot.findEmptySpace()).isEqualTo(5);
    }

    @Test
    @DisplayName("경차 할인 적용 테스트")
    void light_car_discount_fee() {
        BigInteger parkingTime = new BigInteger(String.valueOf(20400));
        Car car = new LightCar(user, parkingTime);
        ParkingFeePolicy parkingFeePolicy = new FeeV2();

        parkingLot.entrance(car);

        parkingLot.exit(car, parkingFeePolicy, null);

        Assertions.assertThat(car.getUser().getWallet().getMoney()).isEqualTo(12500);
        Assertions.assertThat(parkingLot.getParkingSpaceList()[0]).isNull();
    }

    @Test
    @DisplayName("페이코 유저 V2최대 가격 15000에 10퍼 할인적용")
    void payco_user_discount_fee() {
        BigInteger parkingTime = new BigInteger(String.valueOf(20400));
        User user = new User(true);
        Car car = new ElectricCar(user, parkingTime);
        ParkingFeePolicy parkingFeePolicy = new FeeV2();

        parkingLot.entrance(car);

        parkingLot.exit(car, parkingFeePolicy, null);

        Assertions.assertThat(car.getUser().getWallet().getMoney()).isEqualTo(6500);
        Assertions.assertThat(parkingLot.getParkingSpaceList()[0]).isNull();
    }

    @Test
    @DisplayName("존재하지 않는 차를 exit할 경우 테스트")
    void no_exist_car() {
        BigInteger parkingTime = new BigInteger(String.valueOf(1000));
        Car car = new MiddleCar(user, parkingTime);
        Car noExistCar = new MiddleCar(user, parkingTime);
        ParkingFeePolicy parkingFeePolicy = new FeeV1();

        parkingLot.entrance(car);

        Assertions.assertThatThrownBy(() -> parkingLot.exit(noExistCar, parkingFeePolicy, null))
                .isInstanceOf(NotExistCarException.class)
                .hasMessageContaining("not exist car in ParkingLot");
    }
}