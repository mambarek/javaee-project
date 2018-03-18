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
    private Continent country;

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


}
