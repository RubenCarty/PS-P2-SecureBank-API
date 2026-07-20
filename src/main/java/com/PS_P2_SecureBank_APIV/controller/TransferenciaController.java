package com.PS_P2_SecureBank_APIV.controller;

import com.PS_P2_SecureBank_APIV.dto.request.TransferenciaRequest;
import com.PS_P2_SecureBank_APIV.dto.response.TransferenciaResponse;
import com.PS_P2_SecureBank_APIV.service.TransferenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transferencias")
@RequiredArgsConstructor
public class TransferenciaController {

    private final TransferenciaService transferenciaService;

    @PostMapping
    public TransferenciaResponse transferir(
            @RequestBody TransferenciaRequest request,
            Authentication authentication) {

        return transferenciaService.realizarTransferencia(
                request,
                authentication.getName()
        );
    }
    @GetMapping("/mis-transferencias")
    public List<TransferenciaResponse> misTransferencias(
            Authentication authentication) {

        return transferenciaService
                .obtenerMisTransferencias(authentication.getName());
    }
}