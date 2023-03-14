package com.nhnacademy.gw1.parking.coupon;

public class twoHourCoupon implements Coupon {
    private final int DISCOUNT = 7200;

    public int getDiscount() {
        return DISCOUNT;
    }
}
