package com.PS_P2_SecureBank_APIV.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TransferenciaResponse {

    private String numeroOperacion;

    private String cuentaOrigen;

    private String cuentaDestino;

    private BigDecimal monto;

    private String descripcion;

    private String estado;

    private LocalDateTime fechaTransferencia;
}