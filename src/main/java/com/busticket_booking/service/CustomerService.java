package com.busticket_booking.service;

import com.busticket_booking.payload.CustomerDto;

import java.util.List;

public interface CustomerService {
    CustomerDto createCustomer(CustomerDto customerDto);

   List<CustomerDto> findAllCustomer();

    CustomerDto findCustomerById(long id);

    CustomerDto updateCustomerById(long id, CustomerDto customerDto);

    void deleteCustomerById(long id);
}
