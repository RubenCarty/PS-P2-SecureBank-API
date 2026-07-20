package com.PS_P2_SecureBank_APIV.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping({"/", "/login"})
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/transferencia")
    public String transferencia() {
        return "transferencia";
    }

    @GetMapping("/historial")
    public String historial() {
        return "historial";
    }

    @GetMapping("/admin/auditoria")
    public String auditoria() {
        return "admin/auditoria";
    }
    @GetMapping("/admin/cuentas")
    public String adminCuentas() {
        return "admin/cuentas";
    }
    @GetMapping("/admin/usuarios")
    public String adminUsuarios() {
        return "admin/usuarios";
    }
}