package com.PS_P2_SecureBank_APIV.service;

import com.PS_P2_SecureBank_APIV.dto.request.TransferenciaRequest;
import com.PS_P2_SecureBank_APIV.dto.response.TransferenciaResponse;
import com.PS_P2_SecureBank_APIV.entity.Cuenta;
import com.PS_P2_SecureBank_APIV.entity.Transferencia;
import com.PS_P2_SecureBank_APIV.enums.EstadoTransferencia;
import com.PS_P2_SecureBank_APIV.repository.CuentaRepository;
import com.PS_P2_SecureBank_APIV.repository.TransferenciaRepository;
import com.PS_P2_SecureBank_APIV.util.GeneradorOperacionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferenciaService {

    private final TransferenciaRepository transferenciaRepository;
    private final CuentaRepository cuentaRepository;
    private final AuditoriaService auditoriaService;
    private final RateLimitService rateLimitService;

    @Transactional
    public TransferenciaResponse realizarTransferencia(
            TransferenciaRequest request,
            String username) {

        try {
            if (!rateLimitService.permitirTransferencia(username)) {
                throw new RuntimeException("Límite de transferencias excedido. Máximo 5 por minuto.");
            }
            Cuenta origen = cuentaRepository
                    .findByNumeroCuenta(request.getNumeroCuentaOrigen())
                    .orElseThrow(() ->
                            new RuntimeException("Cuenta origen no existe"));

            Cuenta destino = cuentaRepository
                    .findByNumeroCuenta(request.getNumeroCuentaDestino())
                    .orElseThrow(() ->
                            new RuntimeException("Cuenta destino no existe"));

            if (!origen.getUsuario().getUsername().equals(username)) {
                throw new RuntimeException("La cuenta origen no pertenece al usuario");
            }

            if (origen.getNumeroCuenta().equals(destino.getNumeroCuenta())) {
                throw new RuntimeException("No puede transferirse a la misma cuenta");
            }

            if (request.getMonto() == null || request.getMonto().signum() <= 0) {
                throw new RuntimeException("Monto inválido");
            }
            if (request.getMonto().compareTo(new BigDecimal("5000")) > 0) {
                throw new RuntimeException(
                        "Transferencia supera el monto máximo permitido (S/ 5000)");
            }

            if (origen.getSaldo().compareTo(request.getMonto()) < 0) {
                throw new RuntimeException("Saldo insuficiente");
            }

            origen.setSaldo(origen.getSaldo().subtract(request.getMonto()));
            destino.setSaldo(destino.getSaldo().add(request.getMonto()));

            cuentaRepository.save(origen);
            cuentaRepository.save(destino);

            String numeroOperacion = GeneradorOperacionUtil.generarNumeroOperacion();

            Transferencia transferencia = Transferencia.builder()
                    .numeroOperacion(numeroOperacion)
                    .cuentaOrigen(origen)
                    .cuentaDestino(destino)
                    .monto(request.getMonto())
                    .descripcion(request.getDescripcion())
                    .estado(EstadoTransferencia.EXITOSA)
                    .fechaTransferencia(LocalDateTime.now())
                    .build();

            transferenciaRepository.save(transferencia);

            auditoriaService.registrarEvento( //auditoria de tranferencias exitosa
                    username,
                    "TRANSFERENCIA_EXITOSA",
                    "Operación " + numeroOperacion
                            + " | Origen: " + origen.getNumeroCuenta()
                            + " | Destino: " + destino.getNumeroCuenta()
                            + " | Monto: " + request.getMonto(),
                    "127.0.0.1",
                    "TRANSFERENCIAS",
                    true
            );

            return TransferenciaResponse.builder()
                    .numeroOperacion(transferencia.getNumeroOperacion())
                    .cuentaOrigen(origen.getNumeroCuenta())
                    .cuentaDestino(destino.getNumeroCuenta())
                    .monto(transferencia.getMonto())
                    .descripcion(transferencia.getDescripcion())
                    .estado(transferencia.getEstado().name())
                    .fechaTransferencia(transferencia.getFechaTransferencia())
                    .build();

        } catch (RuntimeException e) {

            auditoriaService.registrarEvento(
                    username,
                    "TRANSFERENCIA_RECHAZADA",
                    e.getMessage(),
                    "127.0.0.1",
                    "TRANSFERENCIAS",
                    false
            );

            throw e;
        }
    }

    public List<TransferenciaResponse> obtenerMisTransferencias(String username) {
        return transferenciaRepository
                .findByCuentaOrigenUsuarioUsernameOrCuentaDestinoUsuarioUsername(username, username)
                .stream()
                .map(t -> TransferenciaResponse.builder()
                        .numeroOperacion(t.getNumeroOperacion())
                        .cuentaOrigen(t.getCuentaOrigen().getNumeroCuenta())
                        .cuentaDestino(t.getCuentaDestino().getNumeroCuenta())
                        .monto(t.getMonto())
                        .descripcion(t.getDescripcion())
                        .estado(t.getEstado().name())
                        .fechaTransferencia(t.getFechaTransferencia())
                        .build())
                .toList();
    }
}