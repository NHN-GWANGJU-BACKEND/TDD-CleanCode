package com.nhnacademy.gw1.parking;

import com.nhnacademy.gw1.parking.car.Car;
import com.nhnacademy.gw1.parking.car.LightCar;
import com.nhnacademy.gw1.parking.coupon.Coupon;
import com.nhnacademy.gw1.parking.exception.NotHaveCouponException;
import com.nhnacademy.gw1.parking.fee.ParkingFeePolicy;

import java.math.BigInteger;

import static com.nhnacademy.gw1.parking.config.Code.PAYCO_DISCOUNT;

public class Exit {
    private static Exit exit = new Exit();

    private Exit() {
    }

    public static Exit getInstance() {
        return exit;
    }

    public void pay(Car car, ParkingFeePolicy parkingFeePolicy, Coupon targetCoupon) {
        applyCoupon(car, targetCoupon);
        int totalFee = parkingFeePolicy.calculateTotalFee(car);
        totalFee = lightCarDiscount(car, totalFee);
        totalFee = paycoDiscount(car, totalFee);
        car.getUser().getWallet().subMoney(new Money(totalFee));
    }

    private void applyCoupon(Car car, Coupon targetCoupon) {
        if (targetCoupon == null) return;
        boolean isApply = false;
        for (Coupon coupon : car.getUser().getCouponList()) {
            isApply = settingDiscountHour(car, targetCoupon, coupon);
            if(isApply) break;
        }
        if (!isApply) {
            throw new NotHaveCouponException(targetCoupon);
        }
    }

    private boolean settingDiscountHour(Car car, Coupon targetCoupon, Coupon coupon) {
        if (getCouponName(coupon).equals(getCouponName(targetCoupon))) {
            car.updateParkingTime(BigInteger.valueOf(coupon.getDiscount()));
            return true;
        }
        return false;
    }

    public String getCouponName(Coupon coupon) {
        return coupon.getClass().getSimpleName();
    }

    private int paycoDiscount(Car car, int totalFee) {
        if (car.getUser().isPayco()) {
            totalFee -= (int) (totalFee * PAYCO_DISCOUNT);
        }
        return totalFee;
    }

    private int lightCarDiscount(Car car, int totalFee) {
        if (car instanceof LightCar) {
            totalFee /= 2;
        }
        return totalFee;
    }
}
