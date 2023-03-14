package com.nhnacademy.payment.domain;

import java.time.LocalDateTime;

public class Receipt {

    private LocalDateTime payTime;
    private String customerId;
    private long amount;
    private double customerShoppingPoint;
    private long pointDiscountAmount;

    public Receipt(String customerId, long amount, double customerShoppingPoint, long pointDiscountAmount) {
        this.payTime = LocalDateTime.now();
        this.customerId = customerId;
        this.amount = amount;
        this.customerShoppingPoint = customerShoppingPoint;
        this.pointDiscountAmount = pointDiscountAmount;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "payTime=" + payTime +
                ", customerId='" + customerId + '\'' +
                ", amount=" + amount +
                ", customerShoppingPoint=" + customerShoppingPoint +
                ", pointDiscountAmount=" + pointDiscountAmount +
                '}';
    }
}
