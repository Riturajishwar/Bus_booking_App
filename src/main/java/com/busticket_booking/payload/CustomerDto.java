package com.busticket_booking.payload;

import lombok.Data;

@Data
public class CustomerDto {
    private int cusId;
    private String firstName;
    private String lastName;
    private String address;
    private String mobile;
}
