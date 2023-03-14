package com.nhnacademy.gw1.parking;

import com.nhnacademy.gw1.parking.car.Car;
import com.nhnacademy.gw1.parking.car.MiddleCar;
import com.nhnacademy.gw1.parking.coupon.Coupon;
import com.nhnacademy.gw1.parking.coupon.oneHourCoupon;
import com.nhnacademy.gw1.parking.coupon.twoHourCoupon;
import com.nhnacademy.gw1.parking.exception.NotHaveCouponException;
import com.nhnacademy.gw1.parking.fee.FeeV2;
import com.nhnacademy.gw1.parking.fee.ParkingFeePolicy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class CouponTest {

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

    @Test
    @DisplayName("class명으로 쿠폰 이름 가져오는 테스트")
    void getSimpleName() {
        Exit exit = Exit.getInstance();
        Coupon coupon = new twoHourCoupon();
        assertThat(exit.getCouponName(coupon)).isEqualTo("twoHourCoupon");
    }

    @Test
    @DisplayName("쿠폰이 없는데 쿠폰을 사용하겠다고 할 경우 테스트")
    void user_not_have_coupon() {
        BigInteger parkingTime = new BigInteger(String.valueOf(20400));
        Car car = new MiddleCar(user, parkingTime);
        ParkingFeePolicy parkingFeePolicy = new FeeV2();
        Coupon coupon = new twoHourCoupon();

        parkingLot.entrance(car);

        Assertions.assertThatThrownBy(() -> parkingLot.exit(car, parkingFeePolicy, coupon))
                .isInstanceOf(NotHaveCouponException.class)
                .hasMessageContaining("user not have target coupon");
    }


    @ParameterizedTest
    @CsvSource({"7200,20000", "9001,19000", "27600,5000"})
    @DisplayName("2시간 쿠폰 적용시 할인되는지 테스트")
    void user_have_twoHour_coupon(int timeSec, int expectMoney) {
        BigInteger parkingTime = new BigInteger(String.valueOf(timeSec));
        Coupon coupon = new twoHourCoupon();
        user.getCouponList().add(coupon);
        Car car = new MiddleCar(user, parkingTime);
        ParkingFeePolicy parkingFeePolicy = new FeeV2();

        parkingLot.entrance(car);

        parkingLot.exit(car, parkingFeePolicy, coupon);

        assertThat(car.getUser().getWallet().getMoney()).isEqualTo(expectMoney);
    }

    @ParameterizedTest
    @CsvSource({"3600,20000", "6401,19000", "24000,5000"})
    @DisplayName("1시간 쿠폰 적용시 할인되는지 테스트")
    void user_have_oneHour_coupon(int timeSec, int expectMoney) {
        BigInteger parkingTime = new BigInteger(String.valueOf(timeSec));
        Coupon coupon = new oneHourCoupon();
        user.getCouponList().add(coupon);
        Car car = new MiddleCar(user, parkingTime);
        ParkingFeePolicy parkingFeePolicy = new FeeV2();

        parkingLot.entrance(car);

        parkingLot.exit(car, parkingFeePolicy, coupon);

        assertThat(car.getUser().getWallet().getMoney()).isEqualTo(expectMoney);
    }

}
