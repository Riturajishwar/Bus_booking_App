package com.busticket_booking.repositories;

import com.busticket_booking.entities.Booking;
import com.busticket_booking.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {

   List<Booking> findByCustomer(Customer customer);
}
