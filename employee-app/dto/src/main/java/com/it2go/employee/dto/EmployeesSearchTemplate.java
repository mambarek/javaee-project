package com.it2go.employee.dto;

import lombok.Data;

@Data
public class EmployeesSearchTemplate {

    private EmployeeTableItem employeeTableItem = new EmployeeTableItem();
    private String orderBy;
    private String orderDirection = "asc";
    private int maxResult = -1;
    private int offset = 0;
}
