package com.example.project_management.payload;

import lombok.*;      // make sure you have these imports
import java.time.Instant;

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
