package com.nhnacademy.gw1.exception;

import com.nhnacademy.gw1.Currency;

public class TypeSameException extends RuntimeException {
    public TypeSameException(Currency origin, Currency target) {
        super("same exchange currency type\n this : " + origin + "target : " + target);
    }
}
