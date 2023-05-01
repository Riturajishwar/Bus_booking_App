package com.busticket_booking.controller;

import com.busticket_booking.payload.CustomerDto;
import com.busticket_booking.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cus")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto){

        CustomerDto customer = customerService.createCustomer(customerDto);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<CustomerDto>> findAllCustomer(){

        List<CustomerDto> allCustomer = customerService.findAllCustomer();

        return new ResponseEntity<>(allCustomer,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomerDto> findCustomerById(@PathVariable("id")long id){

        CustomerDto customerById = customerService.findCustomerById(id);

      return new ResponseEntity<>(customerById,HttpStatus.OK);
    }
    @PutMapping("/{id}/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomerDto> updateCustomerById(@PathVariable("id")long id,@RequestBody CustomerDto customerDto){

        CustomerDto customerDto1 = customerService.updateCustomerById(id, customerDto);

        return new ResponseEntity<>(customerDto1,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteCustomerById(@PathVariable("id")long id){

        customerService.deleteCustomerById(id);
        return new ResponseEntity<>("Customer deleted ! "+id,HttpStatus.OK);

    }

}
