package com.example.project_management.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

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