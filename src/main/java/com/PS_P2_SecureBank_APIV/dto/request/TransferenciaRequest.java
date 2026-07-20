package com.PS_P2_SecureBank_APIV.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferenciaRequest {

    private String numeroCuentaOrigen;

    private String numeroCuentaDestino;

    private BigDecimal monto;

    private String descripcion;
}