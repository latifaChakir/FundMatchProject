package com.example.fundmatch.shared.exception;

public class StartupNotFoundException extends RuntimeException {
    public StartupNotFoundException(String message) {
        super(message);
    }
}
