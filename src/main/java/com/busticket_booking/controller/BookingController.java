package com.busticket_booking.controller;

import com.busticket_booking.payload.BookingDto;
import com.busticket_booking.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private BookingService bookingService;

    public BookingController(BookingService bookingService) {


        this.bookingService = bookingService;
    }
    @PostMapping("/{busId}/{cusId}")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> bookTicket(@RequestBody BookingDto bookingDto, @PathVariable("busId")long busId,@PathVariable("cusId")long cusId ){

        bookingService.bookBusTicket(bookingDto,busId,cusId);

        return new ResponseEntity<>("Ticket Booked", HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<BookingDto>>findAllBooking(){

        List<BookingDto> allBooking = bookingService.findAllBooking();

        return new ResponseEntity<>(allBooking, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<BookingDto> findBookingById(@PathVariable("id")long id){

        BookingDto bookingById = bookingService.findBookingById(id);

        return new ResponseEntity<>(bookingById,HttpStatus.OK);
    }

    @DeleteMapping("/{bookingId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteBookingById(@PathVariable("bookingId")long bookingId){

        bookingService.deleteBookingById(bookingId);
        return new ResponseEntity<>("Booking Deleted "+bookingId,HttpStatus.OK);
    }
}