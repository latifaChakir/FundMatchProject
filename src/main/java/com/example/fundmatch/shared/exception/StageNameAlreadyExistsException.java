package com.example.fundmatch.shared.exception;

public class StageNameAlreadyExistsException extends RuntimeException {
    public StageNameAlreadyExistsException(String message) {
        super(message);
    }
}
