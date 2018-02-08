package com.it2go.masterdata;

import org.junit.Test;

import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;

public class ContinentTest {

    @Test
    public void testContinent(){
        List<Country> all =  Continent.ASIA.getCountries(Locale.CHINESE);
        System.out.println("Test");
        Continent.ASIA.getCountries(Locale.GERMAN).forEach(System.out::println);
    }

    @Test
    public void testCountry(){
        Country country = new Country("AS","Samoa");
        Locale l = new Locale(country.getCode());
        System.out.println("l.getDisplayCountry() = " + l.getDisplayCountry());;
        System.out.println("l.getDisplayCountry(de) = " + l.getDisplayCountry(Locale.GERMANY));
    }
}