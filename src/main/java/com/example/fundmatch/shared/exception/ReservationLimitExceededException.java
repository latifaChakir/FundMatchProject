package com.example.fundmatch.shared.exception;

public class ReservationLimitExceededException extends RuntimeException {
    public ReservationLimitExceededException(String message) {
        super(message);
    }
}
