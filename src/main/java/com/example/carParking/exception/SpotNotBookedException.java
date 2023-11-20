package com.example.carParking.exception;

public class SpotNotBookedException extends RuntimeException {
    public SpotNotBookedException(String message) {
        super(message);
    }
}
