package com.allica.customerInfo.service;

import com.allica.customerInfo.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    public Customer saveCustomer(Customer customer);

    public List<Customer> getAllCustomers();

    public Optional<Customer> getCustomerById(Long id);
}
