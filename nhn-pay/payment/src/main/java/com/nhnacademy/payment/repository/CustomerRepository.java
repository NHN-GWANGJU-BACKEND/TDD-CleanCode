package com.nhnacademy.payment.repository;

import com.nhnacademy.payment.domain.Customer;

public interface CustomerRepository {

    public Customer findById(String customerId);
}
