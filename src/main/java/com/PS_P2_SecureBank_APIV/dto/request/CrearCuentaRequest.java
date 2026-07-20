package com.PS_P2_SecureBank_APIV.dto.request;

import com.PS_P2_SecureBank_APIV.enums.TipoCuenta;
import lombok.Data;

@Data
public class CrearCuentaRequest {

    private TipoCuenta tipoCuenta;
}