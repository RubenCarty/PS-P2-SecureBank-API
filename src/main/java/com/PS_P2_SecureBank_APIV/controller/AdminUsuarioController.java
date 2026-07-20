package com.PS_P2_SecureBank_APIV.controller;

import com.PS_P2_SecureBank_APIV.dto.request.ActualizarUsuarioAdminRequest;
import com.PS_P2_SecureBank_APIV.dto.request.ResetPasswordAdminRequest;
import com.PS_P2_SecureBank_APIV.dto.response.UsuarioAdminResponse;
import com.PS_P2_SecureBank_APIV.service.AdminUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/usuarios")
@RequiredArgsConstructor
public class AdminUsuarioController {

    private final AdminUsuarioService adminUsuarioService;

    @GetMapping("/dni/{dni}")
    public UsuarioAdminResponse buscarPorDni(@PathVariable String dni) {
        return adminUsuarioService.buscarPorDni(dni);
    }

    @PutMapping("/{id}/actualizar")
    public UsuarioAdminResponse actualizarUsuario(
            @PathVariable Long id,
            @RequestBody ActualizarUsuarioAdminRequest request,
            Authentication authentication) {

        return adminUsuarioService.actualizarUsuario(
                id,
                request,
                authentication.getName()
        );
    }

    @PutMapping("/{id}/reset-password")
    public String resetPassword(
            @PathVariable Long id,
            @RequestBody ResetPasswordAdminRequest request,
            Authentication authentication) {

        adminUsuarioService.resetPassword(
                id,
                request,
                authentication.getName()
        );

        return "Contraseña actualizada correctamente";
    }
}