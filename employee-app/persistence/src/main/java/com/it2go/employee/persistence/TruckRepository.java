package com.it2go.employee.persistence;

import com.it2go.employee.dao.DomainEntity;
import com.it2go.employee.entities.Gender;
import com.it2go.employee.entities.Person;
import com.it2go.employee.entities.Truck;
import com.it2go.framework.dao.EntityConcurrentModificationException;
import com.it2go.framework.dao.EntityNotFoundException;
import com.it2go.framework.dao.EntityNotPersistedException;
import com.it2go.framework.dao.EntityRemovedException;
import com.it2go.framework.dao.IEntityDAO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class TruckRepository implements ITruckRepository {

    @Inject
    @DomainEntity(clazz = Truck.class)
    private IEntityDAO<Truck> truckDAO;

    @Override
    public Truck persist(Truck entity, Person user) throws EntityConcurrentModificationException, EntityRemovedException {
        return truckDAO.save(entity);
    }

    @Override
    public Truck remove(Truck entity) throws EntityNotPersistedException {
        return truckDAO.delete(entity);
    }

    @Override
    public <Long> Truck removeById(Long id) throws EntityNotFoundException {
        return truckDAO.deleteByIdentityKey(id);
    }

    @Override
    public <K> Truck findById(K id) {
        return truckDAO.getByIdentityKey(id);
    }

    @Override
    public List<Truck> findAll() {
        return truckDAO.getAll();
    }

    @Override
    public int executeUpdate(String query) {
        return truckDAO.executeUpdate(query);
    }
}
