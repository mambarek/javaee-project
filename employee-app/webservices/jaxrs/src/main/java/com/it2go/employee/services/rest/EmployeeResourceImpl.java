package com.it2go.employee.services.rest;


import com.it2go.employee.dto.EmployeeTableItem;
import com.it2go.employee.dto.EmployeesSearchTemplate;
import com.it2go.employee.dto.search.SearchResult;
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

/*
    @Path annotation must be on the implementation
    If the annotation is on the Interface Weld can't instantiate
    the injected beans
 */
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
    public SearchResult findAllEmployeeItems(final EmployeesSearchTemplate searchTemplate) {

        final List<EmployeeTableItem> employeeTableItems = employeesViewRepository.filterEmployees(searchTemplate);
        final Long countEmployees = employeesViewRepository.countEmployees(searchTemplate);

        SearchResult result = new SearchResult();
        result.setRows(employeeTableItems);
        //result.setPage(1);
        result.setRecords(countEmployees);
        //result.setTotal(10);

        return result;
    }
}
