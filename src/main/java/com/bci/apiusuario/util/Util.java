package com.bci.apiusuario.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Util {
    public static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static String convertirDateToString(Date fecha) {
        return dateFormat.format(fecha);
    }
}
