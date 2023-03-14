package com.nhnacademy.gw1.parking.exception;

import com.nhnacademy.gw1.parking.coupon.Coupon;

public class NotHaveCouponException extends RuntimeException {
    public NotHaveCouponException(Coupon coupon) {
        super("user not have target coupon : " + coupon.getClass().getSimpleName());
    }
}
