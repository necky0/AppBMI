package com.example.necky0.bmiapp;

class BMICount {
    private static final double NO_VALID_RESULT = 0;
    private static final double KG_MIN = 1;
    private static final double KG_MAX = 500;
    private static final double M_MIN = 0.3;
    private static final double M_MAX = 3;
    private static final double LB_MIN = 2;
    private static final double LB_MAX = 1000;
    private static final double IN_MIN = 12;
    private static final double IN_MAX = 120;
    private static final double FACTOR_ENGLISH_UNITS = 703;
    private static final double BMI_RESULT_MIN = 18.5;
    private static final double BMI_RESULT_MAX = 25;

    static double polishUnits(double mass, double height) {
        double result;
        if (mass > KG_MIN && mass < KG_MAX && height > M_MIN && height <= M_MAX) {
            result = mass / (height * height);
        } else {
            result = NO_VALID_RESULT;
        }
        return result;
    }

    static double englishUnits(double mass, double height) {
        double result;
        if (mass > LB_MIN && mass < LB_MAX && height > IN_MIN && height <= IN_MAX) {
            result = mass / (height * height)*FACTOR_ENGLISH_UNITS;
        } else {
            result = NO_VALID_RESULT;
        }
        return result;
    }

    static double count(double mass, double height, boolean isEnglishUnit) {
        if (isEnglishUnit) return BMICount.englishUnits(mass, height);
        else return BMICount.polishUnits(mass, height);
    }

    static BMIClassification getBMIClassification(double result) {
        if (result == NO_VALID_RESULT) {
            return BMIClassification.NONE;
        }else if (result < BMI_RESULT_MIN) {
            return BMIClassification.TOO_LOW;
        } else if ( result < BMI_RESULT_MAX) {
            return BMIClassification.OPTIMAL;
        } else {
            return BMIClassification.TOO_HIGH;
        }
    }

    static boolean validResult(double result){
        return result != NO_VALID_RESULT;
    }
}

enum BMIClassification {
    TOO_LOW, OPTIMAL, TOO_HIGH, NONE
}
