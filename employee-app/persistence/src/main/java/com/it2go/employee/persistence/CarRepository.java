package com.it2go.employee.persistence;

import com.it2go.employee.dao.DomainEntity;
import com.it2go.employee.entities.Car;
import com.it2go.employee.entities.Gender;
import com.it2go.employee.entities.Person;
import com.it2go.framework.dao.EntityConcurrentModificationException;
import com.it2go.framework.dao.EntityNotFoundException;
import com.it2go.framework.dao.EntityNotPersistedException;
import com.it2go.framework.dao.EntityRemovedException;
import com.it2go.framework.dao.IEntityDAO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class CarRepository implements ICarRepository {

    @Inject
    @DomainEntity(clazz = Car.class)
    private IEntityDAO<Car> carDAO;

    @Override
    public Car persist(Car entity, Person user) throws EntityConcurrentModificationException, EntityRemovedException {
        return carDAO.save(entity);
    }

    @Override
    public Car remove(Car entity) throws EntityNotPersistedException {
        return carDAO.delete(entity);
    }

    @Override
    public void refresh(Car entity) {
        carDAO.refresh(entity);
    }

    @Override
    public <Long> Car removeById(Long id) throws EntityNotFoundException {
        return carDAO.deleteByIdentityKey(id);
    }

    @Override
    public <K> Car findById(K id) throws EntityNotFoundException {
        return carDAO.getByIdentityKey(id);
    }

    @Override
    public List<Car> findAll() {
        return carDAO.getAll();
    }

    @Override
    public int executeUpdate(String query) {
        return carDAO.executeUpdate(query);
    }
}
