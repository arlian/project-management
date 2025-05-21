package com.example.project_management.exception;

import org.springframework.http.HttpStatus;

public class CredentialIncorrectException extends ApiException {
    public CredentialIncorrectException() {
        super(HttpStatus.UNAUTHORIZED, "Username or Password incorret");
    }
}
