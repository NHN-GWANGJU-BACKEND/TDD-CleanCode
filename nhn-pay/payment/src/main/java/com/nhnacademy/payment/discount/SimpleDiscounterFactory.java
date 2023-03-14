package com.nhnacademy.payment.discount;

public class SimpleDiscounterFactory implements DiscounterFactory {
    @Override
    public Discountable getDiscounter(DiscountCode discountCode) {
        if (DiscountCode.FIXED_RATE.equals(discountCode)) {
            return new FixedRate();
        } else if (DiscountCode.VARIABLE_RATE.equals(discountCode)) {
            return new VariableRate();
        } else {
            return Discountable.NONE;
        }
    }
}
