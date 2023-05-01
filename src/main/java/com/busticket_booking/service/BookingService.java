package com.busticket_booking.service;

import com.busticket_booking.payload.BookingDto;

import java.util.List;

public interface BookingService {
    void bookBusTicket(BookingDto bookingDto, long busId, long customerId);

   List< BookingDto> findAllBooking();

    void deleteBookingById(long bookingId);

    BookingDto findBookingById(long id);
}
