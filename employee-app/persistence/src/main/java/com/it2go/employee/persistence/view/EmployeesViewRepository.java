package com.it2go.employee.persistence.view;


import com.it2go.employee.dao.cmt.view.EmployeeTableViewDAO;
import com.it2go.employee.dto.EmployeeTableItem;
import com.it2go.employee.dto.EmployeesSearchTemplate;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Stateless
public class EmployeesViewRepository {

    @Inject
    private EmployeeTableViewDAO employeeTableViewDAO;

    public List<EmployeeTableItem> getEmployeeItems() {
        return employeeTableViewDAO.getEmployeeItems();
    }

    public List<EmployeeTableItem> getEmployeeItems(Map<String, Object> params) {
        return employeeTableViewDAO.getEmployeeItems(params);
    }

    public List<EmployeeTableItem> filterEmployees(EmployeesSearchTemplate employeesSearchTemplate){
        return employeeTableViewDAO.filterEmployees(employeesSearchTemplate);
    }
}
