package com.PS_P2_SecureBank_APIV.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AbonoRequest {

    private String numeroCuenta;
    private BigDecimal monto;
}