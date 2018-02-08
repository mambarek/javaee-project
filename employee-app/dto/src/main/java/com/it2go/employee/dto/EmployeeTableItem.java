package com.it2go.employee.dto;

import lombok.Data;

import java.util.Date;

@Data
public class EmployeeTableItem {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private Double salary;
    private String createdByName;
    private Date createdAt;

    public EmployeeTableItem() {
    }

    public EmployeeTableItem(Long id, String firstName, String lastName, Date createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
    }

    public EmployeeTableItem(Long id, String firstName, String lastName, Date birthDate, Double salary, String createdByName, Date createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.salary = salary;
        this.createdByName = createdByName;
        this.createdAt = createdAt;
    }

    public EmployeeTableItem(Long id, String firstName, String lastName, String createdByName, Date createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdByName = createdByName;
        this.createdAt = createdAt;
    }

    public String toString(){
        return String.format("EmployeeItem >> employeeId: %s firtName: %s lastName: %s crreatedBy: %s created at: %s", id,firstName, lastName, createdByName, createdAt);
    }
}
