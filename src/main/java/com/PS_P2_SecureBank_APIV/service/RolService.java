package com.PS_P2_SecureBank_APIV.service;

import com.PS_P2_SecureBank_APIV.entity.Rol;
import com.PS_P2_SecureBank_APIV.enums.RolNombre;
import com.PS_P2_SecureBank_APIV.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolService {

    private final RolRepository rolRepository;

    public Optional<Rol> buscarPorNombre(RolNombre nombre) {
        return rolRepository.findByNombre(nombre);
    }

    public Rol guardar(Rol rol) {
        return rolRepository.save(rol);
    }
}