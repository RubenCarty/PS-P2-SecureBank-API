package com.PS_P2_SecureBank_APIV.dto;

import lombok.Data;

@Data
public class RegistroUsuarioDTO {

    private String nombres;
    private String apellidos;
    private String dni;
    private String correo;
    private String celular;
    private String direccion;
    private String username;
    private String password;
}