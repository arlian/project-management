package com.example.project_management.payload.response;

import java.time.Instant;      // make sure you have these imports

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder            // <— this generates ApiError.builder()
public class ApiError {
    private Instant timestamp;
    private int     status;
    private String  error;
    private String  message;
    private String  path;
}
