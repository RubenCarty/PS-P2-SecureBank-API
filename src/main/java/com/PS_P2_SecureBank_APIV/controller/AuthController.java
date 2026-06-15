package com.PS_P2_SecureBank_APIV.controller;


import com.PS_P2_SecureBank_APIV.dto.auth.RegisterRequest;
import com.PS_P2_SecureBank_APIV.dto.request.LoginRequest;
import com.PS_P2_SecureBank_APIV.dto.response.LoginResponse;
import com.PS_P2_SecureBank_APIV.entity.Usuario;
import com.PS_P2_SecureBank_APIV.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody RegisterRequest request) {
        Usuario usuario = authService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}