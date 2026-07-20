package com.PS_P2_SecureBank_APIV.service;

import com.PS_P2_SecureBank_APIV.dto.request.CrearCuentaRequest;
import com.PS_P2_SecureBank_APIV.dto.response.CuentaResponse;
import com.PS_P2_SecureBank_APIV.entity.Cuenta;
import com.PS_P2_SecureBank_APIV.entity.Usuario;
import com.PS_P2_SecureBank_APIV.enums.EstadoCuenta;
import com.PS_P2_SecureBank_APIV.repository.CuentaRepository;
import com.PS_P2_SecureBank_APIV.repository.UsuarioRepository;
import com.PS_P2_SecureBank_APIV.util.GeneradorCuentaUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final UsuarioRepository usuarioRepository;

    public CuentaResponse crearCuenta(
            Long usuarioId,
            CrearCuentaRequest request) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

        String numeroCuenta;

        do {
            numeroCuenta = GeneradorCuentaUtil.generarNumeroCuenta();
        } while (cuentaRepository.existsByNumeroCuenta(numeroCuenta));

        Cuenta cuenta = Cuenta.builder()
                .numeroCuenta(numeroCuenta)
                .tipoCuenta(request.getTipoCuenta())
                .estado(EstadoCuenta.ACTIVA)
                .saldo(BigDecimal.ZERO)
                .fechaCreacion(LocalDateTime.now())
                .usuario(usuario)
                .build();

        cuentaRepository.save(cuenta);

        return CuentaResponse.builder()
                .id(cuenta.getId())
                .numeroCuenta(cuenta.getNumeroCuenta())
                .tipoCuenta(cuenta.getTipoCuenta().name())
                .estado(cuenta.getEstado().name())
                .saldo(cuenta.getSaldo())
                .build();
    }

    public List<CuentaResponse> obtenerCuentas(Long usuarioId) {

        return cuentaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(cuenta -> CuentaResponse.builder()
                        .id(cuenta.getId())
                        .numeroCuenta(cuenta.getNumeroCuenta())
                        .numeroTarjetaDebito(cuenta.getNumeroTarjetaDebito())
                        .tipoCuenta(cuenta.getTipoCuenta().name())
                        .estado(cuenta.getEstado().name())
                        .saldo(cuenta.getSaldo())
                        .build()
                )
                .toList();
    }
}