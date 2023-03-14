package com.nhnacademy.payment.discount;

import com.nhnacademy.payment.discount.Discountable;

public class VariableRate implements Discountable {
    private int discountRate = 10;

    @Override
    public long getDiscountAmt(long originAmt) {
        return originAmt - (originAmt * discountRate / 100);
    }
}
