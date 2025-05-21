package com.example.project_management.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project_management.payload.request.LoginRequest;
import com.example.project_management.payload.request.SignUpRequest;
import com.example.project_management.payload.response.LoginResponse;
import com.example.project_management.security.JwtUtil;
import com.example.project_management.security.MyUserDetails;
import com.example.project_management.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest req) {
        // 1) Authenticate (this calls your CustomUserDetailsService → returns MyUserDetails)
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username(), req.password()));
        SecurityContextHolder.getContext().setAuthentication(auth);

        // 2) Cast principal back to MyUserDetails to get id/name/roles
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();

        // 3) Generate a JWT with all of your custom claims (id, name, roles…)
        String token = jwtUtil.generateToken(user);

        // 4) Return it
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignUpRequest req) {
        authService.registerUser(req);
        return ResponseEntity.ok("User registered successfully");
    }
}
