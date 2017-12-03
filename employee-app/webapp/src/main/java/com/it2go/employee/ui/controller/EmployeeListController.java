package com.it2go.employee.ui.controller;

import com.it2go.employee.entities.Employee;
import com.it2go.employee.persistence.EmployeeRepository;
import com.it2go.employee.persistence.IEmployeeRepository;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class EmployeeListController implements Serializable{

    @Inject
    IEmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees(){
        System.out.println("### EmployeeListController findAll call!");
        final List<Employee> employees = employeeRepository.findAll();
        System.out.println("EmployeeListController -- > employees found= " + employees.size());

        return employees;
    }
}
