package com.busticket_booking.payload;

import lombok.Data;

@Data
public class BookingDto {

    private long bookingId;

    private String seatFrom;
    private String seatTo;
    private String status;
    private long totalCost;
}
