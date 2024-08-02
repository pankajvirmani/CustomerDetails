package com.allica.customerInfo;

import com.allica.customerInfo.model.Customer;
import com.allica.customerInfo.repository.CustomerRepository;
import com.allica.customerInfo.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CustomerInfoApplicationTests {


	@Mock
	private CustomerRepository customerRepository;

	@Autowired
	CustomerServiceImpl customerService;

	private static final Logger log = LoggerFactory.getLogger(CustomerInfoApplicationTests.class);

	@Test
	void testSaveCustomer_Success() {
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customer.setDateOfBirth(LocalDate.of(2021,12,30));

		when(customerRepository.save(any(Customer.class))).thenReturn(customer);

		Customer savedCustomer = customerService.saveCustomer(customer);

		assertNotNull(savedCustomer);
		assertEquals("John", savedCustomer.getFirstName());
		assertEquals("Doe", savedCustomer.getLastName());
	}

	@Test
	void testSaveCustomer_ValidationFailure() {
		Customer customer = new Customer();
		customer.setFirstName(null);
		customer.setLastName(null);
		customer.setDateOfBirth(null);


		Exception thrownException = assertThrows(RuntimeException.class, () -> {
			customerService.saveCustomer(customer);
		});

		assertEquals("Received null input. Enter valid customer details ", thrownException.getMessage());
	}

	@Test
	void testGetAllCustomers() {
		List<Customer> customers = new ArrayList<>();
		Customer customer = new Customer();
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customer.setDateOfBirth(LocalDate.of(2021,11,30));
		customers.add(customer);

		when(customerRepository.findAll()).thenReturn(customers);

		List<Customer> result = customerService.getAllCustomers();

		assertNotNull(result);
	}

	@Test
	void testGetCustomerById_NotFound() {
		Long id = 1L;

		when(customerRepository.findById(id)).thenReturn(Optional.empty());

		Optional<Customer> result = customerService.getCustomerById(id);

		assertFalse(result.isPresent());
	}
}


