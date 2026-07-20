package com.PS_P2_SecureBank_APIV.service;

import com.PS_P2_SecureBank_APIV.dto.request.AbonoRequest;
import com.PS_P2_SecureBank_APIV.dto.response.CuentaResponse;
import com.PS_P2_SecureBank_APIV.entity.Cuenta;
import com.PS_P2_SecureBank_APIV.enums.EstadoCuenta;
import com.PS_P2_SecureBank_APIV.repository.CuentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.PS_P2_SecureBank_APIV.dto.response.CuentaAdminResponse;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AdminCuentaService {

    private final CuentaRepository cuentaRepository;
    private final AuditoriaService auditoriaService;

    @Transactional
    public CuentaResponse abonarCuenta(AbonoRequest request, String adminUsername) {

        if (request.getMonto() == null || request.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("El monto del abono debe ser mayor a cero");
        }

        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(request.getNumeroCuenta())
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        if (cuenta.getEstado() != EstadoCuenta.ACTIVA) {
            throw new RuntimeException("No se puede abonar a una cuenta bloqueada o cerrada");
        }

        cuenta.setSaldo(cuenta.getSaldo().add(request.getMonto()));

        Cuenta cuentaActualizada = cuentaRepository.save(cuenta);

        auditoriaService.registrarEvento(
                adminUsername,
                "ABONO_ADMIN",
                "Abono de S/ " + request.getMonto()
                        + " a la cuenta " + cuenta.getNumeroCuenta(),
                "127.0.0.1",
                "ADMIN_CUENTAS",
                true
        );

        return CuentaResponse.builder()
                .id(cuentaActualizada.getId())
                .numeroCuenta(cuentaActualizada.getNumeroCuenta())
                .numeroTarjetaDebito(cuentaActualizada.getNumeroTarjetaDebito())
                .tipoCuenta(cuentaActualizada.getTipoCuenta().name())
                .estado(cuentaActualizada.getEstado().name())
                .saldo(cuentaActualizada.getSaldo())
                .build();
    }

    @Transactional
    public CuentaResponse bloquearCuenta(String numeroCuenta, String adminUsername) {

        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        cuenta.setEstado(EstadoCuenta.BLOQUEADA);

        Cuenta cuentaActualizada = cuentaRepository.save(cuenta);

        auditoriaService.registrarEvento(
                adminUsername,
                "CUENTA_BLOQUEADA",
                "Cuenta bloqueada: " + numeroCuenta,
                "127.0.0.1",
                "ADMIN_CUENTAS",
                true
        );

        return CuentaResponse.builder()
                .id(cuentaActualizada.getId())
                .numeroCuenta(cuentaActualizada.getNumeroCuenta())
                .numeroTarjetaDebito(cuentaActualizada.getNumeroTarjetaDebito())
                .tipoCuenta(cuentaActualizada.getTipoCuenta().name())
                .estado(cuentaActualizada.getEstado().name())
                .saldo(cuentaActualizada.getSaldo())
                .build();
    }

    @Transactional
    public CuentaResponse activarCuenta(String numeroCuenta, String adminUsername) {

        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        cuenta.setEstado(EstadoCuenta.ACTIVA);

        Cuenta cuentaActualizada = cuentaRepository.save(cuenta);

        auditoriaService.registrarEvento(
                adminUsername,
                "CUENTA_ACTIVADA",
                "Cuenta activada: " + numeroCuenta,
                "127.0.0.1",
                "ADMIN_CUENTAS",
                true
        );

        return CuentaResponse.builder()
                .id(cuentaActualizada.getId())
                .numeroCuenta(cuentaActualizada.getNumeroCuenta())
                .numeroTarjetaDebito(cuentaActualizada.getNumeroTarjetaDebito())
                .tipoCuenta(cuentaActualizada.getTipoCuenta().name())
                .estado(cuentaActualizada.getEstado().name())
                .saldo(cuentaActualizada.getSaldo())
                .build();
    }
    public CuentaAdminResponse buscarCuentaAdmin(String numeroCuenta) {

        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        return CuentaAdminResponse.builder()
                .numeroCuenta(cuenta.getNumeroCuenta())
                .titular(cuenta.getUsuario().getNombres() + " " + cuenta.getUsuario().getApellidos())
                .dni(cuenta.getUsuario().getDni())
                .tipoCuenta(cuenta.getTipoCuenta().name())
                .estado(cuenta.getEstado().name())
                .saldo(cuenta.getSaldo())
                .build();
    }
}