package com.it2go.employee.ui.controller;

import javax.inject.Named;

@Named
public class TestController {

    private String firstName;
    private String lastName;

    public String save(){
        System.out.println("-- TestController save() call --");
        return null;
    }

    public String cancel(){
        System.out.println("-- TestController cancel() call --");
        return null;
    }

    public String delete(){
        System.out.println("-- TestController delete() call --");
        return null;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
