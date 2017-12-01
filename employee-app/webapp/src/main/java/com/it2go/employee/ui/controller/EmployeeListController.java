package com.it2go.employee.ui.controller;

import com.it2go.employee.entities.Employee;
import com.it2go.employee.persistence.EmployeeRepository;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class EmployeeListController {

    @EJB
    EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees(){

        final List<Employee> employees = employeeRepository.findAll();
        System.out.println("EmployeeListController -- > employees found= " + employees.size());

        return employees;
    }
}
