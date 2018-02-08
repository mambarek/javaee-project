package com.it2go.employee.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@Data
@XmlRootElement
public class EmployeeTableItem implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private Double salary;
    private boolean traveling;
    private boolean weekendWork;
    private String createdByName;
    private Date createdAt;

    public EmployeeTableItem() {
    }

    public EmployeeTableItem(Long id, String firstName, String lastName, Date birthDate, Double salary, boolean traveling, boolean weekendWork, String createdByName, Date createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.salary = salary;
        this.traveling = traveling;
        this.weekendWork = weekendWork;
        this.createdByName = createdByName;
        this.createdAt = createdAt;
    }

    public String toString(){
        return String.format("EmployeeItem >> employeeId: %s firtName: %s lastName: %s crreatedBy: %s created at: %s", id,firstName, lastName, createdByName, createdAt);
    }
}
