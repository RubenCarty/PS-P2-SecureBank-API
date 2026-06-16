package com.PS_P2_SecureBank_APIV.service;


import com.PS_P2_SecureBank_APIV.dto.auth.RegisterRequest;
import com.PS_P2_SecureBank_APIV.dto.request.LoginRequest;
import com.PS_P2_SecureBank_APIV.dto.response.LoginResponse;
import com.PS_P2_SecureBank_APIV.entity.Rol;
import com.PS_P2_SecureBank_APIV.entity.Usuario;
import com.PS_P2_SecureBank_APIV.enums.RolNombre;
import com.PS_P2_SecureBank_APIV.repository.RolRepository;
import com.PS_P2_SecureBank_APIV.repository.UsuarioRepository;
import com.PS_P2_SecureBank_APIV.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String token =
                jwtService.generateToken(request.getUsername());

        return new LoginResponse(token);
    }
    public Usuario registrar(RegisterRequest request) {

        if (usuarioRepository.existsByDni(request.getDni())) {
            throw new RuntimeException("El DNI ya está registrado");
        }

        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("El username ya está registrado");
        }

        Rol rolCliente = rolRepository.findByNombre(RolNombre.ROLE_CLIENTE)
                .orElseThrow(() -> new RuntimeException("Rol CLIENTE no encontrado"));

        Usuario usuario = Usuario.builder()
                .dni(request.getDni())
                .nombres(request.getNombres())
                .apellidos(request.getApellidos())
                .correo(request.getCorreo())
                .celular(request.getCelular())
                .direccion(request.getDireccion())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .activo(true)
                .fechaRegistro(LocalDateTime.now())
                .rol(rolCliente)
                .build();

        return usuarioRepository.save(usuario);
    }
}