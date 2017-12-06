package com.it2go.employee.dao;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

public class EntityManagerProducecr {

    @PersistenceContext(unitName = "EmployeeDS")
    private EntityManager defaultEntityManager;

    @Produces
    public EntityManager getEntityManager(){
        return defaultEntityManager;
    }
}
