package com.nhnacademy.gw1.parking.coupon;

public class oneHourCoupon implements Coupon {
    private final int DISCOUNT = 3600;

    public int getDiscount() {
        return DISCOUNT;
    }
}
