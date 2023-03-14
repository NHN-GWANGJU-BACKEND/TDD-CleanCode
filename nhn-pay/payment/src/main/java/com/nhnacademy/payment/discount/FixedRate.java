package com.nhnacademy.payment.discount;

import com.nhnacademy.payment.discount.Discountable;

public class FixedRate implements Discountable {
    private long discountAmt = 1000L;

    @Override
    public long getDiscountAmt(long originAmt) {
        if(originAmt<discountAmt){
            return originAmt;
        }
        return originAmt-discountAmt;
    }
}
