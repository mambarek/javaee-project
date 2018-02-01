package com.it2go.employee.entities;

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


}
