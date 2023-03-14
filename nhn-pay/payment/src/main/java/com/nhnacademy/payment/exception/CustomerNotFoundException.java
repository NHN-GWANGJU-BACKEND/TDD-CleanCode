package com.nhnacademy.payment.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(String customerId) {
        super("Not Found customer" + customerId);

    }

    public void CustomerNotFoundException() {

    }

}
