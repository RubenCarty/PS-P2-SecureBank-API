package com.PS_P2_SecureBank_APIV.dto.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;
    private String password;
}