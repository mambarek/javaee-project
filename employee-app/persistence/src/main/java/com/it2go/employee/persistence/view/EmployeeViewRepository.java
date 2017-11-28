package com.it2go.employee.persistence.view;


import com.it2go.employee.dao.cmt.view.EmployeeTableViewDAO;
import com.it2go.employee.dto.EmployeeTableItem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Stateless
public class EmployeeViewRepository {

    @Inject
    private EmployeeTableViewDAO employeeTableViewDAO;

    public List<EmployeeTableItem> getEmployeeItems() {
        return employeeTableViewDAO.getEmployeeItems();
    }

    public List<EmployeeTableItem> getEmployeeItems(Map<String, Object> params) {
        return employeeTableViewDAO.getEmployeeItems(params);
    }

    public List<EmployeeTableItem> testCriteria(){
        return employeeTableViewDAO.getItemsUsingCriteria(null);
    }
}
