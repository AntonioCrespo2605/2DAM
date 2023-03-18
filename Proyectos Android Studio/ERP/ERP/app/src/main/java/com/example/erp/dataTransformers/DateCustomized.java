package com.example.erp.dataTransformers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateCustomized {
    public static String getSystemDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return format.format(date);
    }
}
