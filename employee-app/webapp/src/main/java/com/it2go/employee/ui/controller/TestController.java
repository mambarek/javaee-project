package com.it2go.employee.ui.controller;

import com.it2go.masterdata.Continent;
import lombok.Data;

import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;

@Data
@Named
@ViewScoped
public class TestController implements Serializable {

    private String firstName;
    private String lastName;

    private Date date1;
    private Date date2;
    private Date date3;

    private Continent continent;
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

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    public Date getDate3() {
        return date3;
    }

    public void setDate3(Date date3) {
        this.date3 = date3;
    }
}
