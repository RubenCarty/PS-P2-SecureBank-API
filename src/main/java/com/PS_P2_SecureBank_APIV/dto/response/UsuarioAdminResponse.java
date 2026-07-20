package com.PS_P2_SecureBank_APIV.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioAdminResponse {

    private Long id;
    private String dni;
    private String nombres;
    private String apellidos;
    private String correo;
    private String celular;
    private String direccion;
    private String username;
    private String rol;
    private Boolean activo;
}