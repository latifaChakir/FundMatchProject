package com.example.fundmatch.api.advice;

import com.example.fundmatch.api.wrapper.ApiResponse;
import com.example.fundmatch.shared.exception.InvalidCredentialsException;
import com.example.fundmatch.shared.exception.ResourceNotFoundException;
import com.example.fundmatch.shared.exception.StageNameAlreadyExistsException;
import com.example.fundmatch.shared.exception.StageNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGenericException(Exception ex) {
        ApiResponse<String> response = ApiResponse.error("Une erreur interne est survenue", "/api/generic-error", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
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
                .data(errors)  // Définir les données directement dans le builder
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

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiResponse<String> response = ApiResponse.error(
                ex.getMessage(),
                "/api/startups",
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
