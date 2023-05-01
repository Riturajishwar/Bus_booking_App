package com.busticket_booking.payload;

import lombok.Data;

import java.util.Date;

@Data
public class BusDto {

    private Long busNo;
    private String busName;
    private String busFrom;
    private String routeTo;
    private String busType;
    private Date departure;
    private Date arrival;
    private long totalSeats;
    private long availSeats;
    private long fare;
}
