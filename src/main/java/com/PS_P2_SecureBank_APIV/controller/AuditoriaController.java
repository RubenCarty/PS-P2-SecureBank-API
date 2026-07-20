package com.PS_P2_SecureBank_APIV.controller;

import com.PS_P2_SecureBank_APIV.entity.Auditoria;
import com.PS_P2_SecureBank_APIV.repository.AuditoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/auditoria")
@RequiredArgsConstructor
public class AuditoriaController {

    private final AuditoriaRepository auditoriaRepository;

    @GetMapping
    public List<Auditoria> listarAuditoria() {
        return auditoriaRepository.findAll();
    }
}