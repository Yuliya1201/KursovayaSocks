package com.example.kursovayasocks.exception;


public class SocksNotFoundException extends Exception {
    public SocksNotFoundException(String message) {
        super(message);
    }

    public SocksNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
