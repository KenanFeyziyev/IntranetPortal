package com.intranet.portal.controller;

import com.intranet.portal.dto.auth.AuthResponse;
import com.intranet.portal.dto.auth.LoginRequest;
import com.intranet.portal.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        var user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        String role = user.getAuthorities()
                .stream()
                .findFirst()
                .get()
                .getAuthority(); // məsələn ROLE_ADMIN

        String token = jwtService.generateToken(request.email(), role);

        return ResponseEntity.ok(new AuthResponse(token));
    }
}