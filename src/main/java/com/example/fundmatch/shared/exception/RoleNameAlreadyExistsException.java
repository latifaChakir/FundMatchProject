package com.example.fundmatch.shared.exception;

public class RoleNameAlreadyExistsException extends RuntimeException {
  public RoleNameAlreadyExistsException(String message) {
    super(message);
  }
}
