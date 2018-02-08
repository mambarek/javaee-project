package com.it2go.employee.ui.controller;

import com.it2go.employee.dto.EmployeeTableItem;
import com.it2go.employee.dto.EmployeesSearchTemplate;
import com.it2go.employee.persistence.view.EmployeesViewRepository;
import com.it2go.framework.util.StringUtils;
import lombok.Data;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Named
@ViewScoped
@Data
public class EmployeesGridController implements Serializable {

    private EmployeesSearchTemplate employeeSearchTemplate = new EmployeesSearchTemplate();

    @Inject
    private EmployeesViewRepository employeesViewRepository;

    private int pageNum = 0;
    private int pageSize = 10;


    public void filterEmployees(){
        employeeSearchTemplate.setMaxResult(10);
        employeeSearchTemplate.getEmployeeTableItem().setFirstName("9");
        employeesViewRepository.filterEmployees(employeeSearchTemplate);
    }

}
