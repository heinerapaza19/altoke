package edu.pe.upeu.delivery.controller;

import edu.pe.upeu.delivery.DTOs.AuthResponse;
import edu.pe.upeu.delivery.DTOs.LoginRequest;
import edu.pe.upeu.delivery.DTOs.RegisterRequest;
import edu.pe.upeu.delivery.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // ===================== REGISTRO =====================
    @PostMapping("/registro")
    public ResponseEntity<AuthResponse> registrar(@RequestBody RegisterRequest request) {
        AuthResponse response = authService.registrar(request);
        return ResponseEntity.ok(response);
    }

    // ===================== LOGIN =====================
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    // ===================== LOGOUT =====================
    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout(
            @RequestHeader(value = "Authorization", required = false) String token) {

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        return ResponseEntity.ok(authService.logout(token));
    }

    // ===================== VALIDATE TOKEN =====================
    @GetMapping("/validate")
    public ResponseEntity<AuthResponse> validateToken(
            @RequestHeader(value = "Authorization", required = false) String token) {

        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(401)
                    .body(AuthResponse.builder().mensaje("Token no proporcionado").build());
        }

        return ResponseEntity.ok(
                AuthResponse.builder().mensaje("Token válido").build()
        );
    }
}
