package com.nhnacademy.gw1.parking;

import com.nhnacademy.gw1.parking.config.Code;
import com.nhnacademy.gw1.parking.coupon.Coupon;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final long userId;
    private Money wallet;
    private final boolean isPayco;
    private List<Coupon> couponList;

    public User(boolean isPayco) {
        this.userId = Code.userId++;
        this.wallet = new Money(20000);
        this.isPayco = isPayco;
        this.couponList = new ArrayList<>();
    }

    public Money getWallet() {
        return wallet;
    }

    public boolean isPayco() {
        return isPayco;
    }

    public List<Coupon> getCouponList() {
        return couponList;
    }
}
