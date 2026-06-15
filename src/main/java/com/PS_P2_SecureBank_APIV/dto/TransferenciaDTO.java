package com.PS_P2_SecureBank_APIV.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferenciaDTO {

    private Long cuentaOrigenId;
    private Long cuentaDestinoId;
    private BigDecimal monto;
    private String descripcion;
}
