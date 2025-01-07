package com.example.fundmatch.api.wrapper;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ApiResponse<T> {
    private T data;
    private String message;
    private int statusCode;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    private String path;
    private boolean success;
    private String traceId;

    public static <T> ApiResponse<T> success(T data, String path) {
        return ApiResponse.<T>builder()
                .data(data)
                .message("Opération réussie")
                .statusCode(200)
                .path(path)
                .success(true)
                .traceId(generateTraceId())
                .build();
    }

    public static <T> ApiResponse<T> error(String message, String path, int statusCode) {
        return ApiResponse.<T>builder()
                .message(message)
                .statusCode(statusCode)
                .path(path)
                .success(false)
                .traceId(generateTraceId())
                .build();
    }

    public static String generateTraceId() {
        return "trace-" + LocalDateTime.now();
    }
}
