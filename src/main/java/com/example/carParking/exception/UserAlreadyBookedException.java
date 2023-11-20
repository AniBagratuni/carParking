package com.example.carParking.exception;

public class UserAlreadyBookedException extends RuntimeException {
    public UserAlreadyBookedException(String message) {
        super(message);
    }
}