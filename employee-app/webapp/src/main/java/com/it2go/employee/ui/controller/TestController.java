package com.it2go.employee.ui.controller;

import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class TestController implements Serializable {

    private String firstName;
    private String lastName;

    public String save() {
        System.out.println("-- TestController save() call --");
        return null;
    }

    public String cancel() {
        System.out.println("-- TestController cancel() call --");
        return null;
    }

    public String delete() {
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
