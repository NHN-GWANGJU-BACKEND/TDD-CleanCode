package com.nhnacademy.payment.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer {

    private String id;
    private double nhnPoint;
    private boolean isPointUsed;

    public Customer(String id) {
        this.id = id;
        this.nhnPoint = 0;
        this.isPointUsed = false;
    }

    public void setNhnPoint(double point) {
        this.nhnPoint += point;
    }

    public boolean isPointUsed() {
        return isPointUsed;
    }

    public void setPointUsed(boolean pointUsed) {
        isPointUsed = pointUsed;
    }

    public void clearNhnPoint() {
        this.nhnPoint = 0;
    }
}
