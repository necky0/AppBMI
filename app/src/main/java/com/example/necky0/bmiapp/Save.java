package com.example.necky0.bmiapp;

import android.content.SharedPreferences;

public class Save {
    private final String MASS = "mass1";
    private final String HEIGHT = "height1";
    private final String UNITS = "units1";

    private SharedPreferences sp;
    private SharedPreferences.Editor spe;

    private String mass;
    private String height;
    private Boolean units;

    public Save(SharedPreferences sp) {
        this.sp = sp;
        spe = sp.edit();

        getVariables();

        spe.apply();
    }

    private void getVariables(){
        mass = sp.getString(MASS, "0");
        height = sp.getString(HEIGHT, "0");
        units = sp.getBoolean(UNITS, false);
    }

    public String getMass() {
        return mass;
    }

    public String getHeight() {
        return height;
    }

    public Boolean getUnits() {
        return units;
    }

    public void save(String mass, String height, Boolean units) {
        spe.putString(MASS, mass);
        spe.putString(HEIGHT, height);
        spe.putBoolean(UNITS, units);

        spe.apply();
    }
}
