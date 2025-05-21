package com.example.project_management.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFound extends ApiException {

    public ResourceNotFound(String resource, Long id) {
        super(HttpStatus.NOT_FOUND, resource + " with id " + id + " not found");
    }
}
