package com.example.project_management.payload.response;

import java.time.Instant;

public record CreateProjectResponse(
    Long id,
    String projectKey,
    String name,
    String description,
    Long leadId,
    Long createdById,
    Instant createdAt
) {}