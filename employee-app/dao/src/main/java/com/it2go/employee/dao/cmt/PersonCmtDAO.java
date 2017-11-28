package com.it2go.employee.dao.cmt;


import com.it2go.employee.dao.DomainEntity;
import com.it2go.employee.entities.Person;

@DomainEntity(clazz = Person.class)
public class PersonCmtDAO extends EntityCmtDAO<Person> {
    @Override
    Class<Person> getEntityClass() {
        return Person.class;
    }
}
