package com.ellekay.lucie.diabetesms;

/**
 * Created by lucie on 8/3/2017.
 */

public class Readings {
    public String date;
    public String timeOfDay;
    public Integer glucoReading;
    public String action;
    public String medication;
    public String notes;


    public Readings(){

    }


    public Readings(String date, String timeOfDay, Integer glucoReading, String action, String medication, String notes) {
        this.date = date;
        this.timeOfDay = timeOfDay;
        this.glucoReading = glucoReading;
        this.action = action;
        this.medication = medication;
        this.notes = notes;
    }
}
