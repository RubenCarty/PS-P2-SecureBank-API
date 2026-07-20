package com.PS_P2_SecureBank_APIV.config;

import com.PS_P2_SecureBank_APIV.entity.Rol;
import com.PS_P2_SecureBank_APIV.enums.RolNombre;
import com.PS_P2_SecureBank_APIV.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;

    @Override
    public void run(String... args) {

        crearRolSiNoExiste(RolNombre.ROLE_ADMIN);
        crearRolSiNoExiste(RolNombre.ROLE_CLIENTE);
        crearRolSiNoExiste(RolNombre.ROLE_CAJERO);
        crearRolSiNoExiste(RolNombre.ROLE_SUPERVISOR);
    }

    private void crearRolSiNoExiste(RolNombre rolNombre) {
        rolRepository.findByNombre(rolNombre)
                .orElseGet(() ->
                        rolRepository.save(
                                Rol.builder()
                                        .nombre(rolNombre)
                                        .build()
                        )
                );
    }
}