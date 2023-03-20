package com.example.erp.dataTransformers;

import android.widget.EditText;

public class DataChecker {

    public static boolean isBankNumber(String input) {
        // First, remove any white spaces from the input string
        input = input.replaceAll("\\s", "");

        // Check if the input string has between 8 and 30 alphanumeric characters
        if (input.matches("[A-Za-z0-9]{8,30}")) {
            // Assuming the first two characters are the country code, we can extract them
            String countryCode = input.substring(0, 2);

            // Use a switch statement to apply country-specific validation rules
            switch (countryCode.toUpperCase()) {
                case "ES": // Spain: First character must be a "0" or "1"
                    if (input.charAt(2) == '0' || input.charAt(2) == '1') {
                        return true;
                    }
                    break;
                case "US": // United States: No specific validation rules for now
                    return true;
                case "GB": // United Kingdom: No specific validation rules for now
                    return true;
                // Add additional cases for other countries as needed
                default:
                    return false;
            }
        }
        // If the input string does not meet the above conditions, return false
        return false;
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
