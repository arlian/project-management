package com.example.project_management.config;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.project_management.exception.ApiException;
import com.example.project_management.payload.response.ApiError;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class GlobalExceptionHandler {

    // 1) Validation errors (400 Bad Request)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(
            MethodArgumentNotValidException ex,
            HttpServletRequest req) {

        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.joining("; "));

        log.error(msg, ex);

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiError body = ApiError.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(msg)
                .path(req.getRequestURI())
                .build();

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthException(
            AuthenticationException ex,
            HttpServletRequest request) {
        
        log.error("Auth Error", ex);
        ApiError body = new ApiError(
                Instant.now(),
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                "Username or Password inccorrect",
                request.getRequestURI()
        );
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(body);
    }

    // 2) All of your domain exceptions (any HTTP status)
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiError> handleApiException(
            ApiException ex,
            HttpServletRequest request) {

        log.warn("API exception: {}", ex.getMessage());
        ApiError body = buildApiError(
                ex.getStatus(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(ex.getStatus()).body(body);
    }

    // 3) Fallback for everything else (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAllUncaught(
            Exception ex,
            HttpServletRequest request) {

        log.error("Unhandled exception caught: ", ex);
        ApiError body = buildApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred. Please try again later.",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    // --- helper to build the ApiError payload ---
    private ApiError buildApiError(HttpStatus status, String message, String path) {
        return ApiError.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(path)
                .build();
    }
}
