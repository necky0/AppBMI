package com.example.necky0.bmiapp;

public class BMICount {
    public static double polishUnits(double mass, double height) {
        double result;
        if (mass > 1 && mass < 500 && height > 0.3 && height <= 3) {
            result = mass / (height * height);
        } else {
            result = 0.0;
        }
        return result;
    }

    public static double englishUnits(double mass, double height) {
        double result;
        if (mass > 2 && mass < 1000 && height > 4 && height <= 120) {
            result = mass / (height * height)*703;
        } else {
            result = 0.0;
        }
        return result;
    }


    public static String toString(double number) {
        return String.valueOf((int)(number*100)/100.0);
    }
}
