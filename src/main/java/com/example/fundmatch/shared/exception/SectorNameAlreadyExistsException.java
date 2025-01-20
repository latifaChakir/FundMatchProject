package com.example.fundmatch.shared.exception;

public class SectorNameAlreadyExistsException extends RuntimeException {
    public SectorNameAlreadyExistsException(String message) {
        super(message);
    }
}
