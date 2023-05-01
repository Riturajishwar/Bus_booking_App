package com.busticket_booking.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long busNo;
    private String busName;
    private String busFrom;
    private String routeTo;
    private String BusType;
    private Date departure;
    private Date arrival;
    private long totalSeats;
    private long availSeats;
    private long fare;
    @OneToMany(mappedBy = "bus",cascade = CascadeType.ALL)
private List<Booking> booking=new ArrayList<>();

}
