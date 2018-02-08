package com.it2go.employee.dao.cmt;


import com.it2go.employee.dao.DomainEntity;
import com.it2go.employee.entities.Employee;

import javax.enterprise.context.Dependent;

@DomainEntity(clazz = Employee.class)
public class EmployeeCmtDAO extends EntityCmtDAO<Employee> {

    @Override
    Class<Employee> getEntityClass() {
        return Employee.class;
    }
}
