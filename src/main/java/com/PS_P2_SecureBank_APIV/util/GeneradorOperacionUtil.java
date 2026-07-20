package com.PS_P2_SecureBank_APIV.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class GeneradorOperacionUtil {

    private static final Random RANDOM = new Random();

    public static String generarNumeroOperacion() {

        String fecha = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        int aleatorio = 1000 + RANDOM.nextInt(9000);

        return "TRX" + fecha + aleatorio;
    }
}