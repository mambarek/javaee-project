package com.it2go.employee.services.rest;


import com.it2go.employee.dto.EmployeeTableItem;
import com.it2go.employee.dto.EmployeesSearchTemplate;
import com.it2go.employee.entities.Employee;
import com.it2go.employee.persistence.IEmployeeRepository;
import com.it2go.employee.persistence.UserSession;
import com.it2go.employee.persistence.view.EmployeesViewRepository;
import com.it2go.framework.dao.EntityConcurrentModificationException;
import com.it2go.framework.dao.EntityNotFoundException;
import com.it2go.framework.dao.EntityRemovedException;

import javax.inject.Inject;
import javax.ws.rs.Path;
import java.util.List;

@Path("/EmployeeService")
public class EmployeeResourceImpl implements EmployeeResource {

    @Inject
    private IEmployeeRepository employeeRepository;

    @Inject
    private EmployeesViewRepository employeesViewRepository;

    @Inject
    private UserSession userSession;

    @Override
    public String ping(){
        return "<< Hello from EmployeeResource! >> ----------------------------------------";
    }

    @Override
    public Employee save(final Employee employee) {
        System.out.println("EmployeeResourceImpl :: save employee = " + employee);
        Employee persistedEmpl = null;
        try {
            persistedEmpl = employeeRepository.persist(employee, userSession.getTestCreationUser());
        } catch (EntityConcurrentModificationException | EntityRemovedException e) {
            e.printStackTrace();
        }

        return persistedEmpl;
    }

    @Override
    public Employee findById(final Long id) {
        System.out.println("### EmployeeResourceImpl findById call!");
        Employee foundEntity = null;
        try {
            foundEntity = employeeRepository.findById(id);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        return foundEntity;
    }

    @Override
    public List<Employee> findAll() {
        System.out.println("### EmployeeResourceImpl findAll call!");
        return employeeRepository.findAll();
    }


    public List<EmployeeTableItem> findAllEmployeeItems() {
        return employeesViewRepository.getEmployeeItems();
    }

    @Override
    public List<EmployeeTableItem> findAllEmployeeItems(int rows, int offset, String sidx, String sord) {
        EmployeesSearchTemplate searchTemplate = new EmployeesSearchTemplate();
        searchTemplate.setMaxResult(rows);
        searchTemplate.setOffset(offset);
        searchTemplate.setOrderBy(sidx);
        searchTemplate.setOrderDirection(sord);

        return employeesViewRepository.filterEmployees(searchTemplate);
    }

    @Override
    public List<EmployeeTableItem> findAllEmployeeItems(int rows, int offset, String sidx, String sord, String searchField, String searchString, String searchOper) {
        EmployeesSearchTemplate searchTemplate = new EmployeesSearchTemplate();
        searchTemplate.setMaxResult(rows);
        searchTemplate.setOffset(offset);
        searchTemplate.setOrderBy(sidx);
        searchTemplate.setOrderDirection(sord);

        return employeesViewRepository.filterEmployees(searchTemplate);
    }
}
