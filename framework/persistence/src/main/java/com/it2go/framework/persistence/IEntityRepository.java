package com.it2go.framework.persistence;

import com.it2go.framework.dao.EntityConcurrentModificationException;
import com.it2go.framework.dao.EntityNotFoundException;
import com.it2go.framework.dao.EntityNotPersistedException;
import com.it2go.framework.dao.EntityRemovedException;
import com.it2go.framework.entities.IAbstractEntity;

import java.io.Serializable;
import java.util.List;

public interface IEntityRepository<T extends IAbstractEntity, P extends IAbstractEntity > extends Serializable{

    public T persist(T entity, P user) throws EntityConcurrentModificationException, EntityRemovedException;
    public T remove(T entity) throws EntityNotPersistedException;
    public void refresh(T entity);
    public <K> T removeById(K id) throws EntityNotFoundException;
    public <K> T findById(K id) throws EntityNotFoundException;
    public List<T> findAll();
    public int executeUpdate(final String query);
}
