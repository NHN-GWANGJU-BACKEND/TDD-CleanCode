package com.nhnacademy.payment.discount;

public interface Discountable {

    Discountable NONE = new Discountable() {
        @Override
        public long getDiscountAmt(long originAmt) {
            return 0;
        }
    };

    long getDiscountAmt(long originAmt);
}
