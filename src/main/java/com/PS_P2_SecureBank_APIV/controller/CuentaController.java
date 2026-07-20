package com.PS_P2_SecureBank_APIV.controller;

import com.PS_P2_SecureBank_APIV.dto.request.CrearCuentaRequest;
import com.PS_P2_SecureBank_APIV.dto.response.CuentaResponse;
import com.PS_P2_SecureBank_APIV.entity.Usuario;
import com.PS_P2_SecureBank_APIV.repository.UsuarioRepository;
import com.PS_P2_SecureBank_APIV.service.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/crear")
    public CuentaResponse crearCuenta(
            Authentication authentication,
            @RequestBody CrearCuentaRequest request) {

        String username = authentication.getName();

        Usuario usuario = usuarioRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

        return cuentaService.crearCuenta(
                usuario.getId(),
                request
        );
    }

    @GetMapping("/mis-cuentas")
    public List<CuentaResponse> misCuentas(Authentication authentication) {

        String username = authentication.getName();

        Usuario usuario = usuarioRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

        return cuentaService.obtenerCuentas(usuario.getId());
    }
}