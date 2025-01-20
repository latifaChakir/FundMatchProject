package com.example.fundmatch.shared.exception;

public class InvestorOrganizationAlreadyExistsException extends RuntimeException {
    public InvestorOrganizationAlreadyExistsException(String message) {
        super(message);
    }
}
