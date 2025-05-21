package com.example.project_management.exception;

import org.springframework.http.HttpStatus;

public class UsernameAlreadyExistsException extends ApiException {
    public UsernameAlreadyExistsException(String username) {
        super(HttpStatus.CONFLICT, "Username already taken: " + username);
    }
}
