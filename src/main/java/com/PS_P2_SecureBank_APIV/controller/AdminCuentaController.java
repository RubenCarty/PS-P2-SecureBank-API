package com.PS_P2_SecureBank_APIV.controller;

import com.PS_P2_SecureBank_APIV.dto.request.AbonoRequest;
import com.PS_P2_SecureBank_APIV.dto.response.CuentaResponse;
import com.PS_P2_SecureBank_APIV.service.AdminCuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.PS_P2_SecureBank_APIV.dto.response.CuentaAdminResponse;

@RestController
@RequestMapping("/api/admin/cuentas")
@RequiredArgsConstructor
public class AdminCuentaController {

    private final AdminCuentaService adminCuentaService;

    @PostMapping("/abonar")
    public CuentaResponse abonar(
            @RequestBody AbonoRequest request,
            Authentication authentication) {

        return adminCuentaService.abonarCuenta(request, authentication.getName());
    }

    @PutMapping("/{numeroCuenta}/bloquear")
    public CuentaResponse bloquear(
            @PathVariable String numeroCuenta,
            Authentication authentication) {

        return adminCuentaService.bloquearCuenta(numeroCuenta, authentication.getName());
    }

    @PutMapping("/{numeroCuenta}/activar")
    public CuentaResponse activar(
            @PathVariable String numeroCuenta,
            Authentication authentication) {

        return adminCuentaService.activarCuenta(numeroCuenta, authentication.getName());
    }
    @GetMapping("/{numeroCuenta}")
    public CuentaAdminResponse buscarCuenta(
            @PathVariable String numeroCuenta) {

        return adminCuentaService.buscarCuentaAdmin(numeroCuenta);
    }
}