package com.PS_P2_SecureBank_APIV.repository;

import com.PS_P2_SecureBank_APIV.entity.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {

    Optional<Transferencia> findByNumeroOperacion(String numeroOperacion);

    boolean existsByNumeroOperacion(String numeroOperacion);

    List<Transferencia> findByCuentaOrigenUsuarioUsernameOrCuentaDestinoUsuarioUsername(
            String usernameOrigen,
            String usernameDestino
    );
}