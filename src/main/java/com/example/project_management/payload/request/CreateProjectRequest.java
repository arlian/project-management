package com.example.project_management.payload.request;

import jakarta.validation.constraints.NotBlank;

public record CreateProjectRequest(
    @NotBlank
    String projectKey,

    @NotBlank
    String name,

    String description
) {}