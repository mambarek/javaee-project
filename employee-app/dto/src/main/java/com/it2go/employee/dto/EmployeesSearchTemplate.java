package com.it2go.employee.dto;

import com.it2go.employee.dto.search.Group;
import lombok.Data;

import java.util.List;

@Data
public class EmployeesSearchTemplate {

    private EmployeeTableItem employeeTableItem = new EmployeeTableItem();
    private String orderBy;
    private String orderDirection = "asc";
    private int maxResult = -1;
    private int offset = 0;
    private String searchField;
    private String searchString;
    private String searchOper;
    private Group filters;
}
