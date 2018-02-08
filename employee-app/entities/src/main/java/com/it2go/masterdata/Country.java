package com.it2go.masterdata;

import java.text.Collator;
import java.util.Locale;

public class Country implements Comparable<Country> {
    private String code;
    private String name;

    public Country(String code, String name) {
        super();
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int compareTo(Country o) {
        return Collator.getInstance(Locale.GERMANY).compare(name, o.name);
//        return this.name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return "["+code+"] " + name;
    }
}
