package com.PS_P2_SecureBank_APIV.dto.auth;

import lombok.Data;

@Data
public class RegisterRequest {

    private String dni;
    private String nombres;
    private String apellidos;
    private String correo;
    private String celular;
    private String direccion;
    private String username;
    private String password;

}