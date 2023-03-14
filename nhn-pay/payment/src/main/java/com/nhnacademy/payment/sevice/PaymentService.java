package com.nhnacademy.payment.sevice;

import com.nhnacademy.payment.alert.*;
import com.nhnacademy.payment.domain.Customer;
import com.nhnacademy.payment.repository.CustomerRepository;
import com.nhnacademy.payment.discount.DiscountCode;
import com.nhnacademy.payment.discount.Discountable;
import com.nhnacademy.payment.discount.DiscounterFactory;
import com.nhnacademy.payment.discount.SimpleDiscounterFactory;
import com.nhnacademy.payment.domain.Receipt;
import com.nhnacademy.payment.exception.CustomerNotFoundException;
import com.nhnacademy.payment.exception.NegativePaymentAmountException;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class PaymentService {

    private static int ACCRUAL_RATE = 10;
    private final CustomerRepository repository;
    private final Network network;
    private final SmsNotice smsNotice;

    public PaymentService(CustomerRepository customerRepository, Network network) {
        this.repository = customerRepository;
        this.network = network;
        this.smsNotice = new SmsNotice();
    }

    public Receipt pay(long amount, String customerId, DiscountCode discountCode) {
        DiscounterFactory discounterFactory = new SimpleDiscounterFactory();

        long originAmt = amount;

        Customer customer = repository.findById(customerId);

        if (customer == null) {
            throw new CustomerNotFoundException(customerId);
        }

        if (originAmt < 0) {
            throw new NegativePaymentAmountException(amount);
        }

        Discountable discountPolicy = discounterFactory.getDiscounter(discountCode);
        originAmt -= discountPolicy.getDiscountAmt(originAmt);

        long pointDiscountAmount = originAmt;

        if (checkCustomerUsingPoint(customer)) {
            pointDiscountAmount -= customer.getNhnPoint();
            customer.clearNhnPoint();
        }

        double customerShoppingPoint = (pointDiscountAmount / ACCRUAL_RATE);
        customer.setNhnPoint(customerShoppingPoint);

        List<PaymentAlert> paymentAlertList = getPaymentAlertList();
        paymentAlertList.stream()
                .forEach(paymentAlert -> paymentAlert.sendMessage(network.isConnect()));

        Receipt receipt = new Receipt(customerId,amount,customerShoppingPoint, pointDiscountAmount);
        log.info("receipt = {}", receipt);
        return receipt;
    }

    private boolean checkCustomerUsingPoint(Customer customer) {
        return customer.getNhnPoint() > 0 && customer.isPointUsed();
    }

    private List<PaymentAlert> getPaymentAlertList(){
        return Arrays.asList(
                new SmsNotice(),
                new AppPush(),
                new Email()
        );
    }

}
