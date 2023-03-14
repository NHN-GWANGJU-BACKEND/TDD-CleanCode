package com.nhnacademy.payment;

import com.nhnacademy.payment.alert.*;
import com.nhnacademy.payment.discount.DiscountCode;
import com.nhnacademy.payment.discount.Discountable;
import com.nhnacademy.payment.discount.DiscounterFactory;
import com.nhnacademy.payment.discount.SimpleDiscounterFactory;
import com.nhnacademy.payment.domain.Customer;
import com.nhnacademy.payment.exception.CustomerNotFoundException;
import com.nhnacademy.payment.exception.NegativePaymentAmountException;
import com.nhnacademy.payment.repository.CustomerRepository;
import com.nhnacademy.payment.sevice.PaymentService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PaymentServiceTest {

    PaymentService service;
    CustomerRepository repository;
    Network network;
    List<PaymentAlert> paymentAlerts;

    @BeforeEach
    void setUp() {
        repository = mock(CustomerRepository.class);
        network = mock(Network.class);
        service = new PaymentService(repository,network);

        paymentAlerts = Arrays.asList(
                new SmsNotice(),
                new AppPush(),
                new Email()
            );
    }

    @Test
    void pay_notFoundCustomer_thenThrowCustomerNotFoundException() {
        long amount = 10_100L;
        String customerId = "testId";

        when(repository.findById(customerId)).thenReturn(null);

        Assertions.assertThatThrownBy(() -> service.pay(amount, customerId,null))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessageContaining("Not Found customer", customerId);
    }

    @Test
    void pay_FoundCustomer() {
        long amount = 10_100L;
        String customerId = "testId";

        Customer customer = new Customer(customerId);

        when(repository.findById(customerId)).thenReturn(customer);

        Assertions.assertThat(customer).isEqualTo(repository.findById(customerId));
    }

    @Test
    void negativeAmount_thenThrowNegativePaymentAmountException() {
        long amount = -1;
        String customerId = "testId";

        Customer customer = new Customer(customerId);

        when(repository.findById(customerId)).thenReturn(customer);

        Assertions.assertThatThrownBy(() -> service.pay(amount, customerId,null))
                .isInstanceOf(NegativePaymentAmountException.class)
                .hasMessageContaining("amount is negative", amount);
    }

    @Test
    void customerPoint_stack() {
        long amount = 1000L;
        String customerId = "testId";
        Customer customer = new Customer(customerId);

        when(repository.findById(customerId)).thenReturn(customer);

        service.pay(amount, customerId,null);

        Assertions.assertThat(customer.getNhnPoint()).isEqualTo(100);
    }

    @Test
    void success_send_smsMessage() {
        long amount = 1000L;
        String customerId = "testId";
        Customer customer = new Customer(customerId);

        when(repository.findById(customerId)).thenReturn(customer);
        when(network.isConnect()).thenReturn(true);

        service.pay(amount, customerId,null);

        Assertions.assertThat(paymentAlerts.get(0).sendMessage(network.isConnect())).contains("success");
    }

    @Test
    void failed_send_smsMessage() {
        long amount = 1000L;
        String customerId = "testId";
        Customer customer = new Customer(customerId);

        when(repository.findById(customerId)).thenReturn(customer);
        when(network.isConnect()).thenReturn(false);

        service.pay(amount, customerId,null);

        Assertions.assertThat(paymentAlerts.get(0).sendMessage(network.isConnect())).contains("failed");
    }

    @Test
    void payment_success_result_receipt() {
        long amount = 1000L;
        String customerId = "testId";
        Customer customer = new Customer(customerId);
        when(repository.findById(customerId)).thenReturn(customer);

        Assertions.assertThat(service.pay(amount, customerId,null)).isNotNull();
    }

    @Test
    void can_use_shoppingPoint() {
        long amount = 1000L;
        String customerId = "testId";
        Customer customer = new Customer(customerId);
        when(repository.findById(customerId)).thenReturn(customer);

        service.pay(amount, customerId,null);

        customer.setPointUsed(true);
        service.pay(amount, customerId,null);

        Assertions.assertThat(customer.getNhnPoint()).isEqualTo(90);
    }

    @Test
    void cannot_use_shoppingPoint() {
        long amount = 1000L;
        String customerId = "testId";
        Customer customer = new Customer(customerId);
        when(repository.findById(customerId)).thenReturn(customer);

        service.pay(amount, customerId,null);

        customer.setPointUsed(false);
        service.pay(amount, customerId,null);

        Assertions.assertThat(customer.getNhnPoint()).isEqualTo(200);
    }

    @Test
    void success_send_smsMessageAndAppPushAndEmail() {
        long amount = 1000L;
        String customerId = "testId";
        Customer customer = new Customer(customerId);

        when(repository.findById(customerId)).thenReturn(customer);
        when(network.isConnect()).thenReturn(true);

        service.pay(amount, customerId,null);

        for (PaymentAlert alert : paymentAlerts) {
            Assertions.assertThat(alert.sendMessage(network.isConnect())).contains("success");
        }
    }

    @Test
    void failed_send_smsMessageAndAppPushAndEmail() {
        long amount = 1000L;
        String customerId = "testId";
        Customer customer = new Customer(customerId);

        when(repository.findById(customerId)).thenReturn(customer);
        when(network.isConnect()).thenReturn(false);

        service.pay(amount, customerId,null);

        for (PaymentAlert alert : paymentAlerts) {
            Assertions.assertThat(alert.sendMessage(network.isConnect())).contains("failed");
        }
    }

    @Test
    void fixedRate_discount_apply(){
        long amount = 10000L;
        String customerId = "testId";
        Customer customer = new Customer(customerId);
        DiscounterFactory DiscounterFactory = new SimpleDiscounterFactory();
        Discountable discountable = DiscounterFactory.getDiscounter(DiscountCode.FIXED_RATE);

        when(repository.findById(customerId)).thenReturn(customer);
        when(network.isConnect()).thenReturn(true);

        service.pay(amount, customerId, DiscountCode.FIXED_RATE);

        Assertions.assertThat(discountable.getDiscountAmt(amount)).isEqualTo(9000L);
    }

    @Test
    void variable_discount_apply(){
        long amount = 10000L;
        String customerId = "testId";
        Customer customer = new Customer(customerId);
        DiscounterFactory DiscounterFactory = new SimpleDiscounterFactory();
        Discountable discountable = DiscounterFactory.getDiscounter(DiscountCode.VARIABLE_RATE);

        when(repository.findById(customerId)).thenReturn(customer);
        when(network.isConnect()).thenReturn(false);

        service.pay(amount, customerId,DiscountCode.VARIABLE_RATE);

        Assertions.assertThat(discountable.getDiscountAmt(amount)).isEqualTo(9000L);
    }
}