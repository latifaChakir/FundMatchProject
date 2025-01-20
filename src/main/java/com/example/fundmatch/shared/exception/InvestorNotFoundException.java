package com.example.fundmatch.shared.exception;

public class InvestorNotFoundException extends RuntimeException {
    public InvestorNotFoundException(String message) {
        super(message);
    }
}
