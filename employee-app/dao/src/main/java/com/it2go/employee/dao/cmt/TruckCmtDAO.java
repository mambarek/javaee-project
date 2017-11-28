package com.it2go.employee.dao.cmt;


import com.it2go.employee.dao.DomainEntity;
import com.it2go.employee.entities.Truck;

@DomainEntity(clazz = Truck.class)
public class TruckCmtDAO extends EntityCmtDAO<Truck> {
    @Override
    Class<Truck> getEntityClass() {
        return Truck.class;
    }
}
