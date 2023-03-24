package com.example.erp.dataTransformers;

import com.example.erp.dataBaseObjects.ProductSale;
import com.example.erp.dataBaseObjects.Sale;
import com.example.erp.dataBaseObjects.Supply;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MyMultipurpose {
    public static String getSystemDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return format.format(date);
    }

    public static String separateFromDate(String s, boolean day){
        SimpleDateFormat f1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat f2;
        if(day)f2= new SimpleDateFormat("dd/MM/yyyy");
        else f2= new SimpleDateFormat("HH:mm:ss");

        Date date;
        String toret;
        try {
            date=f1.parse(s);
            toret=f2.format(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return toret;
    }

    public static String getDateWithDay(String dateTime) {
        String dateWithDay = "";
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", new Locale("es", "ES"));

        try {
            // Parse the date string into a Date object
            Date date = dateTimeFormat.parse(dateTime);
            // Use a Calendar instance to get the day of the week for the date
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            String dayOfWeek = dayFormat.format(calendar.getTime());
            // Combine the day of the week and date strings
            dateWithDay = dayOfWeek + ", " + dateTime;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return dateWithDay;
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

    public static double getTotalFromSale(Sale s){
        double toret=0;
        for(ProductSale ps :s.getLines()){
            toret+=(ps.getAmount()*ps.getIndPrice());
        }
        return toret;
    }

    public static String deformat(String money) {
        if(DataChecker.correctDouble(money))return money;
        else{
            String toret="";
            for(int i = 0; i < money.length(); i++){
                if(money.charAt(i)!='.')toret+=money.charAt(i);
            }

            String toret2="";
            for(int i=0; i<toret.length();i++){
                if(toret.charAt(i)!=',')toret2+=toret.charAt(i);
                else toret2+='.';
            }
            return toret2;
        }
    }

    public static int getTotalProductsFromSupply(Supply supply){
        int toret=0;
        for(ProductSale ps : supply.getLines()){
            toret+=ps.getAmount();
        }
        return toret;
    }
}
