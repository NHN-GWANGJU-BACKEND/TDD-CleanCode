package com.nhnacademy.gw1.exception;

import com.nhnacademy.gw1.Currency;

public class TypeNotSameException extends RuntimeException {
    public TypeNotSameException(Currency currency, Currency target) {
        super("not same currency - \n origin : " + currency + "target : " + target);
    }
}
