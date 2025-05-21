package com.example.project_management.exception;

import org.springframework.http.HttpStatus;

public class InvalidOperationException extends ApiException {

    public InvalidOperationException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
    
}
