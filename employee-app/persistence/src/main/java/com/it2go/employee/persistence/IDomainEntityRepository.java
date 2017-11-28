package com.it2go.employee.persistence;


import com.it2go.employee.entities.DomainEntity;
import com.it2go.employee.entities.Person;
import com.it2go.framework.persistence.IEntityRepository;

public interface IDomainEntityRepository<T extends DomainEntity> extends IEntityRepository<T, Person> {

}
