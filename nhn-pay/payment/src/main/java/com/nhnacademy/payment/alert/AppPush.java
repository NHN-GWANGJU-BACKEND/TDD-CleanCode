package com.nhnacademy.payment.alert;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppPush implements PaymentAlert {
    public String sendMessage(boolean isNetwork) {
        if(!isNetwork) {
            return "failed App push";
        }
        return "success App push";
    }
}
