package com.allica.customerInfo.service.impl;

import com.allica.customerInfo.model.Customer;
import com.allica.customerInfo.repository.CustomerRepository;
import com.allica.customerInfo.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);
    @Autowired
    private CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer) {
        validateCustomer(customer);
        log.info("[CustomerServiceImpl][saveCustomer] Saving customer: {}", customer);
        return customerRepository.save(customer);
    }

    private void validateCustomer(Customer customer) {
        if(ObjectUtils.isEmpty(customer.getFirstName()) && ObjectUtils.isEmpty(customer.getLastName()) & ObjectUtils.isEmpty(customer.getDateOfBirth())){
            log.info("[CustomerServiceImpl][validateCustomer] Validation failed due to invalid input");
            throw new RuntimeException("Received null input. Enter valid customer details ");
        }
    }

    public List<Customer> getAllCustomers() {
        log.info("[CustomerServiceImpl][getAllCustomers] Fetching all customers");
        List<Customer> customers=new ArrayList<>();
         customerRepository.findAll().forEach(customer ->customers.add(customer));
        return customers;
    }

    public Optional<Customer> getCustomerById(Long id) {
        log.info("[CustomerServiceImpl][getCustomerById] Fetching customer with ID: {}", id);
        return customerRepository.findById(id);
    }
}
