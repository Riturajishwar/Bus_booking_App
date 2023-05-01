package com.busticket_booking.service;


import com.busticket_booking.payload.LoginDto;
import com.busticket_booking.payload.SignUpDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(SignUpDto registerDto);

}
