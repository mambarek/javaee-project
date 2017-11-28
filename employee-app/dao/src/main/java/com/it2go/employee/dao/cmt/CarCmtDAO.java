package com.it2go.employee.dao.cmt;


import com.it2go.employee.dao.DomainEntity;
import com.it2go.employee.entities.Car;

@DomainEntity(clazz = Car.class)
public class CarCmtDAO extends EntityCmtDAO<Car> {
    @Override
    Class<Car> getEntityClass() {
        return Car.class;
    }
}
