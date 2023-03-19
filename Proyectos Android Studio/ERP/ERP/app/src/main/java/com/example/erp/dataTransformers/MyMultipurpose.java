package com.example.erp.dataTransformers;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyMultipurpose {
    public static String getSystemDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return format.format(date);
    }

    public static String roundString(double number) {
        DecimalFormat format = new DecimalFormat("#.##");
        return format.format(number);
    }

    public static double round(double number){
        DecimalFormat format = new DecimalFormat("#.##");
        return Double.parseDouble(format.format(number));
    }

    //changes a number in this way: 1000.5 --> 1.000,5 to be more visual
    public static String format(double number) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        DecimalFormat format = new DecimalFormat("#,###.00", symbols);
        return format.format(number);
    }

    public static String capitalizeFirst(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        } else if (Character.isLetter(str.charAt(0))) {
            return Character.toUpperCase(str.charAt(0)) + str.substring(1);
        } else {
            return str;
        }
    }

}
