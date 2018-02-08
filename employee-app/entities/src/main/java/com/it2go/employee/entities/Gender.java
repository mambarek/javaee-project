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

    public String getLocalizedName(Locale locale){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("com/it2go/gender/gender",locale);

        return resourceBundle.getString(this.toString());
    }
}
