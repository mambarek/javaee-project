package com.it2go.masterdata;

import com.it2go.employee.entities.Employee_;
import com.it2go.employee.entities.Person;
import org.junit.Test;

import javax.persistence.metamodel.SingularAttribute;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;

public class ContinentTest {

    @Test
    public void testCountry(){
        Country country = new Country("AS","Samoa");
        Locale l = new Locale(country.getCode());
        System.out.println("l.getDisplayCountry() = " + l.getDisplayCountry());;
        System.out.println("l.getDisplayCountry(de) = " + l.getDisplayCountry(Locale.GERMANY));

        //Country.getAllCountries(Locale.GERMAN).forEach(System.out::println);

        Continent continent = Continent.getContinentContainingCountry("de");
        System.out.println("continent = " + continent);
    }

    @Test
    public void test2Continent(){
        List<Country> all =  Continent.ASIA.getCountries(Locale.CHINESE);
        System.out.println("Test");
        Continent.ASIA.getCountries(Locale.GERMAN).forEach(System.out::println);
        /*final Continent europe = Continent.getContinentWithName(Continent.EUROPE.getName());
        System.out.println(europe);*/
    }

    @Test
    public void testGenerated(){
        final SingularAttribute<Person, String> firstName = Employee_.firstName;
        System.out.println("firstName = " + firstName);
    }
}