package com.PS_P2_SecureBank_APIV.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CuentaResponse {

    private Long id;

    private String numeroCuenta;

    private String numeroTarjetaDebito;

    private String tipoCuenta;

    private String estado;

    private BigDecimal saldo;
}