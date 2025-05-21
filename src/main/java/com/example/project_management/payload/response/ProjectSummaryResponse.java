package com.example.project_management.payload.response;

import java.time.Instant;

/**
 * A DTO for listing projects with lead & creator names.
 */
public record ProjectSummaryResponse(
    Long id,
    String projectKey,
    String name,
    String description,
    Long leadId,
    String leadName,
    Long createdById,
    String createdByName,
    Instant createdAt
) {}