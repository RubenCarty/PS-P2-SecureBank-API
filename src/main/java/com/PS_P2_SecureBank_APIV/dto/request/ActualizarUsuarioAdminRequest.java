package com.PS_P2_SecureBank_APIV.dto.request;

import lombok.Data;

@Data
public class ActualizarUsuarioAdminRequest {

    private String correo;
    private String celular;
    private String direccion;
}