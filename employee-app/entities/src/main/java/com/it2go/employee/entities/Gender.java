package com.it2go.employee.entities;

import java.util.Locale;
import java.util.ResourceBundle;

public enum Gender {
    MALE(1,"Male"),
    FEMALE(2,"Female");

    private final int id;
    private final String name;

    Gender(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public static String getLocalizedNameFor(Gender gender, Locale locale){
        String resLabel;

        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("com/it2go/gender/gender", locale);
            resLabel = resourceBundle.getString(gender.toString());
        }catch (Exception e){
            e.printStackTrace();
            resLabel = gender.name;
        }

        return resLabel;
    }

    public boolean isMale(){
        return this.equals(MALE);
    }

    public boolean isFemale(){
        return this.equals(FEMALE);
    }
}
