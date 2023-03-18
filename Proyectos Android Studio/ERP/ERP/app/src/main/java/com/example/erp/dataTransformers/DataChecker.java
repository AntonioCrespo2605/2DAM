package com.example.erp.dataTransformers;

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
}
