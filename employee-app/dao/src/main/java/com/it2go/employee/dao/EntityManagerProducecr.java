package com.it2go.employee.dao;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EntityManagerProducecr {

    @PersistenceContext(unitName = "h2-hibernate")
    private EntityManager defaultEntityManager;

    @Produces
    public EntityManager getEntityManager(){
        return defaultEntityManager;
    }
}
