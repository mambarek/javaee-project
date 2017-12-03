package com.it2go.employee.services.rest;


import com.it2go.employee.entities.Employee;
import com.it2go.employee.persistence.IEmployeeRepository;
import com.it2go.employee.persistence.UserSession;
import com.it2go.framework.dao.EntityConcurrentModificationException;
import com.it2go.framework.dao.EntityRemovedException;

import javax.inject.Inject;
import javax.ws.rs.Path;
import java.util.List;

@Path("/EmployeeService")
public class EmployeeResourceImpl implements EmployeeResource {

    @Inject
    IEmployeeRepository employeeRepository;

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
        } catch (EntityConcurrentModificationException e) {
            e.printStackTrace();
        } catch (EntityRemovedException e) {
            e.printStackTrace();
        }

        return persistedEmpl;
    }

    @Override
    public Employee findById(final Long id){
        System.out.println("### EmployeeResourceImpl findById call!");
        return employeeRepository.findById(id);
    }

    @Override
    public List<Employee> findAll() {
        System.out.println("### EmployeeResourceImpl findAll call!");
        return employeeRepository.findAll();
    }

}
