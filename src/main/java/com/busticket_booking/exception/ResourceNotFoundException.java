package com.busticket_booking.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String msg) {

        super(msg);
    }
}
