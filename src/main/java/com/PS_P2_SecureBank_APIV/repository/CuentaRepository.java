package com.PS_P2_SecureBank_APIV.repository;

import com.PS_P2_SecureBank_APIV.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);

    List<Cuenta> findByUsuarioId(Long usuarioId);

    boolean existsByNumeroCuenta(String numeroCuenta);
}