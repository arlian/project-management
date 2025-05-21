package com.example.project_management.exception;

import org.springframework.http.HttpStatus;

/**
 * Base class for all application‐specific exceptions that carry an HTTP status.
 */
public abstract class ApiException extends RuntimeException {
    private final HttpStatus status;

    public ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
