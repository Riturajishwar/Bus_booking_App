package com.busticket_booking.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookingId;

    private String seatFrom;
    private String seatTo;
    private String status;
    private long totalCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="book_bus_id")
    private Bus bus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="book_customer_id")
    private Customer customer;
}
