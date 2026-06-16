package com.PS_P2_SecureBank_APIV.repository;

import com.PS_P2_SecureBank_APIV.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findByCorreo(String correo);

    Optional<Usuario> findByDni(String dni);

    boolean existsByUsername(String username);

    boolean existsByCorreo(String correo);

    boolean existsByDni(String dni);

}