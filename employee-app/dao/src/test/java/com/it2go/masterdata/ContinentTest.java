package com.it2go.masterdata;

import org.junit.Test;

import static org.junit.Assert.*;

public class ContinentTest {

    @Test
    public void testContinent(){
        Continent.ASIA.getCountries("de").forEach(System.out::println);
    }
}