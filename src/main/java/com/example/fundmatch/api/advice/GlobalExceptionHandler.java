package com.example.fundmatch.api.advice;

import com.example.fundmatch.api.wrapper.ApiResponse;
import com.example.fundmatch.shared.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //OK
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        ApiResponse<Map<String, String>> response = ApiResponse .<Map<String, String>>builder()
                .message("Validation failed")
                .path("/api/validation-error")
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .data(errors)
                .success(false)
                .timestamp(LocalDateTime.now())
                .traceId(ApiResponse.generateTraceId())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StageNameAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<String>> handleStageNameAlreadyExistsException(StageNameAlreadyExistsException ex) {
        ApiResponse<String> response = ApiResponse.error(
                ex.getMessage(),
                "/api/stages",
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(StageNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleStageNotFoundException(StageNotFoundException ex) {
        ApiResponse<String> response = ApiResponse.error(
                ex.getMessage(),
                "/api/stages",
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AccountDisabledException.class)
    public ResponseEntity<ApiResponse<String>> handleAccountDisabledException(AccountDisabledException ex) {
        ApiResponse<String> response = ApiResponse.error(
                ex.getMessage(),
                "/api/users",
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        ApiResponse<String> response = ApiResponse.error(
                ex.getMessage(),
                "/api/users",
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiResponse<String> response = ApiResponse.error(
                ex.getMessage(),
                "/api/startups",
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidToken.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidToken(InvalidToken ex) {
        ApiResponse<String> response = ApiResponse.error(
                ex.getMessage(),
                "/api/auth/reset-token",
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<ApiResponse<String>> handleEmailAlreadyInUseException(EmailAlreadyInUseException ex) {
        ApiResponse<String> response = ApiResponse.error(
                ex.getMessage(),
                "/api/users",
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleEventNotFoundException(EventNotFoundException ex) {
        ApiResponse<String> response = ApiResponse.error(
                ex.getMessage(),
                "/api/users",
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvestorNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleInvestorNotFoundException(InvestorNotFoundException ex) {
        ApiResponse<String> response = ApiResponse.error(
                ex.getMessage(),
                "/api/investors",
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvestorOrganizationAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<String>> handleInvestorOrganizationAlreadyExistsException(InvestorOrganizationAlreadyExistsException ex) {
        ApiResponse<String> response = ApiResponse.error(
                ex.getMessage(),
                "/api/investors",
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleProjectNotFoundException(ProjectNotFoundException ex) {
        ApiResponse<String> response = ApiResponse.error(
                ex.getMessage(),
                "/api/projects",
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ApiResponse<String>> handlePaymentException(PaymentException ex) {
        ApiResponse<String> response = ApiResponse.error(
                ex.getMessage(),
                "/api/reservations",
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReservationLimitExceededException.class)
    public ResponseEntity<ApiResponse<String>> handleReservationLimitExceededException(ReservationLimitExceededException ex) {
        ApiResponse<String> response = ApiResponse.error(
                ex.getMessage(),
                "/api/reservations",
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RoleNameAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<String>> handleRoleNameAlreadyExistsException(RoleNameAlreadyExistsException ex) {
        ApiResponse<String> response = ApiResponse.error(
                ex.getMessage(),
                "/api/roles",
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SectorNameAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<String>> handleSectorNameAlreadyExistsException(SectorNameAlreadyExistsException ex) {
        ApiResponse<String> response = ApiResponse.error(
                ex.getMessage(),
                "/api/sectors",
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(SectorNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleSectorNotFoundException(SectorNotFoundException ex) {
        ApiResponse<String> response = ApiResponse.error(
                ex.getMessage(),
                "/api/sectors",
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StageNameAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<String>> handleStageNameAlreadyExistsException(SectorNotFoundException ex) {
        ApiResponse<String> response = ApiResponse.error(
                ex.getMessage(),
                "/api/sectors",
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(StartupNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleStartupNotFoundException(StartupNotFoundException ex) {
        ApiResponse<String> response = ApiResponse.error(
                ex.getMessage(),
                "/api/startups",
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
