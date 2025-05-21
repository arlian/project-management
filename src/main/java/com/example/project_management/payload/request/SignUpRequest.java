package com.example.project_management.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
public class SignUpRequest {
    @NotBlank
    String username;

    @NotBlank
    @ToString.Exclude
    String password;

    @NotBlank
    String name;
}