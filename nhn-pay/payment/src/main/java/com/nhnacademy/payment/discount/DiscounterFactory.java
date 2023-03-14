package com.nhnacademy.payment.discount;

public interface DiscounterFactory {
    Discountable getDiscounter(DiscountCode discountCode);
}

