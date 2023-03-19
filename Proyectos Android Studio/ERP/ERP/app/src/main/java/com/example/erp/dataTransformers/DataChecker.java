package com.example.erp.dataTransformers;

import android.widget.EditText;

public class DataChecker {

    public static boolean isBankNumber(String s) {
        if (s.length() != 8 && s.length() != 11) return false;

        for (char c : s.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) return false;
        }

        for (int i = 0; i < 4; i++) {
            if (!Character.isLetter(s.charAt(i))) return false;
        }

        for (int i = 4; i < 6; i++) {
            if (!Character.isLetter(s.charAt(i))) return false;
        }

        for (int i = 6; i < 8; i++) {
            if (!Character.isLetterOrDigit(s.charAt(i))) return false;
        }

        if (s.length() == 11 &&!Character.isLetter(s.charAt(8)))return false;

        return true;
    }

    public static boolean isEmpty(EditText editText){
        if(editText.getText().toString().trim().equals(""))return true;
        else return false;
    }

    public static boolean isDniValid(String dni) {
        boolean isValid = false;

        // Check if the length is correct
        if (dni.length() == 9) {
            // Check if the first 8 characters are numerical digits
            String digits = dni.substring(0, 8);
            if (digits.matches("[0-9]+")) {
                // Check if the last character is a valid letter
                String letter = dni.substring(8).toUpperCase();
                String validLetters = "TRWAGMYFPDXBNJZSQVHLCKE";
                int remainder = Integer.parseInt(digits) % 23;
                char calculatedLetter = validLetters.charAt(remainder);
                if (calculatedLetter == letter.charAt(0)) {
                    isValid = true;
                }
            }
        }

        return isValid;
    }

    public static boolean isEmail(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        } else {
            String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
            return str.matches(emailRegex);
        }
    }

}
