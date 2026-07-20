package com.PS_P2_SecureBank_APIV.service;

import com.PS_P2_SecureBank_APIV.entity.Auditoria;
import com.PS_P2_SecureBank_APIV.repository.AuditoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarEvento(
            String usuario,
            String accion,
            String descripcion,
            String ipAddress,
            String modulo,
            Boolean exitoso) {

        Auditoria auditoria = Auditoria.builder()
                .usuario(usuario)
                .accion(accion)
                .descripcion(descripcion)
                .ipAddress(ipAddress)
                .fechaEvento(LocalDateTime.now())
                .modulo(modulo)
                .exitoso(exitoso)
                .build();

        auditoriaRepository.save(auditoria);
    }
}