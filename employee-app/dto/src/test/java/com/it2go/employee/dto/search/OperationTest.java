package com.it2go.employee.dto.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;


public class OperationTest {

    @Test
    public void test1(){

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            final Group group = objectMapper.readValue("{\n" +
                    "   \"groupOp\": \"OR\",\n" +
                    "   \"rules\": [],\n" +
                    "   \"groups\": [\n" +
                    "     {\n" +
                    "       \"groupOp\":\"AND\",\n" +
                    "       \"rules\": [\n" +
                    "         {\"field\":\"OrderDate\",\"op\":\"gt\",\"data\":\"2001-01-01\"},\n" +
                    "         {\"field\":\"OrderDate\",\"op\":\"lt\",\"data\":\"2013-01-01\"}\n" +
                    "       ],\n" +
                    "       \"groups\":[]\n" +
                    "     },{\n" +
                    "       \"groupOp\":\"AND\",\n" +
                    "       \"rules\":[\n" +
                    "         {\"field\":\"Freight\",\"op\":\"lt\",\"data\":\"10\"},\n" +
                    "         {\"field\":\"CustomerID\",\"op\":\"bw\",\"data\":\"W\"}\n" +
                    "       ],\n" +
                    "       \"groups\":[]\n" +
                    "     }\n" +
                    "   ]\n" +
                    " }", Group.class);
            System.out.println("group = " + group);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}