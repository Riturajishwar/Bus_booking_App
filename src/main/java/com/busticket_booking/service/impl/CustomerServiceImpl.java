package com.busticket_booking.service.impl;

import com.busticket_booking.entities.Bus;
import com.busticket_booking.entities.Customer;
import com.busticket_booking.exception.ResourceNotFoundException;
import com.busticket_booking.payload.BusDto;
import com.busticket_booking.payload.CustomerDto;
import com.busticket_booking.repositories.CustomerRepository;
import com.busticket_booking.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private ModelMapper mapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper mapper) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {

        Customer customer = mapToEntity(customerDto);
        Customer saved = customerRepository.save(customer);
return mapToDto(saved);
    }

    @Override
    public List<CustomerDto> findAllCustomer() {

        List<Customer> allCustomer = customerRepository.findAll();

        List<CustomerDto> collect = allCustomer.stream().map(i -> mapToDto(i)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public CustomerDto findCustomerById(long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " ));

        return mapToDto(customer);
    }

    @Override
    public CustomerDto updateCustomerById(long id, CustomerDto customerDto) {

        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found ! " + id));

       customer.setFirstName(customerDto.getFirstName());
       customer.setMobile(customerDto.getMobile());
       customer.setLastName(customerDto.getLastName());
       customer.setAddress(customerDto.getAddress());
        Customer savedCus = customerRepository.save(customer);

        return mapToDto(savedCus) ;
    }

    @Override
    public void deleteCustomerById(long id) {

        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found with id "));

        customerRepository.deleteById(id);
    }

    Customer mapToEntity(CustomerDto customerDto) {
        Customer customer = mapper.map(customerDto, Customer.class);

        return customer;
    }

    CustomerDto mapToDto(Customer customer) {
        CustomerDto customerDto = mapper.map(customer, CustomerDto.class);

        return customerDto;

    }
}
