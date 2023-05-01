package com.busticket_booking.exception;

import org.springframework.http.HttpStatus;

public class BusAPIException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public BusAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public BusAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
