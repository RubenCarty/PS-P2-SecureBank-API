package com.PS_P2_SecureBank_APIV.util;

import java.util.Random;

public class GeneradorCuentaUtil {

    private static final Random RANDOM = new Random();

    public static String generarNumeroCuenta() {

        StringBuilder sb = new StringBuilder("104");

        for (int i = 0; i < 10; i++) {
            sb.append(RANDOM.nextInt(10));
        }

        return sb.toString();
    }

}