package com.busticket_booking.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cusId;
    private String firstName;
    private String lastName;
    private String address;
    private String mobile;


    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Booking> booking = new ArrayList<>();
}
