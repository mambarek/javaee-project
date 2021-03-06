package com.it2go.employee.services;

import com.it2go.employee.entities.Employee;
import com.it2go.employee.persistence.IEmployeeRepository;
import com.it2go.employee.persistence.UserSession;
import com.it2go.framework.dao.EntityConcurrentModificationException;
import com.it2go.framework.dao.EntityNotFoundException;
import com.it2go.framework.dao.EntityRemovedException;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.jws.WebService;
import java.util.List;

@WebService(endpointInterface = "com.it2go.employee.services.EmployeeRepositoryService",
        serviceName = "EmployeeRepositoryService")
public class EmployeeRepositoryServiceImpl implements EmployeeRepositoryService {

    @Inject
    private IEmployeeRepository employeeRepository;

    @Inject
    private UserSession userSession;

    public Employee save(Employee employee) throws EntityConcurrentModificationException, EntityRemovedException {
        final Employee persistedEmpl = employeeRepository.persist(employee, userSession.getTestCreationUser());

        System.out.println("persist.getId().getClass() = " + persistedEmpl.getId().getClass());
        return persistedEmpl;
    }

    public Employee getEmployeeById(Long id) throws EntityNotFoundException {
        return employeeRepository.findById(id);
//        return null;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
}
