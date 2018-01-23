package com.it2go.employee.services;


import com.it2go.employee.entities.Employee;
import com.it2go.framework.dao.EntityConcurrentModificationException;
import com.it2go.framework.dao.EntityNotFoundException;
import com.it2go.framework.dao.EntityRemovedException;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface EmployeeRepositoryService {

    @WebMethod
    public Employee save(Employee employee) throws EntityConcurrentModificationException, EntityRemovedException;

    @WebMethod
    public Employee getEmployeeById(Long id) throws EntityNotFoundException;

    @WebMethod
    public List<Employee> findAll();
}
