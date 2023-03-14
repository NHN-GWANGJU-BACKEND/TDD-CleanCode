package com.nhnacademy.payment.alert;

import com.nhnacademy.payment.alert.PaymentAlert;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Email implements PaymentAlert {
    public String sendMessage(boolean isNetwork) {
        if(!isNetwork) {
            return "failed send Email";
        }

         return "success send Email";
    }
}
