package com.ellekay.lucie.diabetesms;

import java.util.Date;

/**
 * Created by lucie on 7/28/2017.
 */

public class UserInformation {
    public String name;
    public String dateOfBirth;
    public String gender;
    public String height;
    public String weight;
    public String location;

    public UserInformation(){

    }

    public UserInformation(String name, String dateOfBirth, String gender, String height, String weight, String location) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.location = location;
    }
}
