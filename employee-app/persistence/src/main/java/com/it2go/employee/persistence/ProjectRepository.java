package com.it2go.employee.persistence;

import com.it2go.employee.dao.DomainEntity;
import com.it2go.employee.dao.cmt.PersonCmtDAO;
import com.it2go.employee.entities.Gender;
import com.it2go.employee.entities.Person;
import com.it2go.employee.entities.Project;
import com.it2go.framework.dao.EntityConcurrentModificationException;
import com.it2go.framework.dao.EntityNotFoundException;
import com.it2go.framework.dao.EntityNotPersistedException;
import com.it2go.framework.dao.EntityRemovedException;
import com.it2go.framework.dao.IEntityDAO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

@Stateless
public class ProjectRepository implements IProjectRepository {

    @Inject
    @DomainEntity(clazz = Project.class)
    private IEntityDAO<Project> projectDAO;

    @Override
    public Project persist(Project project, Person user) throws EntityConcurrentModificationException, EntityRemovedException {
        return projectDAO.save(project);
    }

    @Override
    public Project remove(Project entity) throws EntityNotPersistedException {
        return projectDAO.delete(entity);
    }

    @Override
    public void refresh(Project entity) {
        projectDAO.refresh(entity);
    }

    @Override
    public <Long> Project removeById(Long id) throws EntityNotFoundException {
        return projectDAO.deleteByIdentityKey(id);
    }

    @Override
    public <Long> Project findById(Long id) throws EntityNotFoundException {
        return projectDAO.getByIdentityKey(id);
    }

    @Override
    public List<Project> findAll() {
        return projectDAO.getAll();
    }

    @Override
    public int executeUpdate(String query) {
        return projectDAO.executeUpdate(query);
    }

    @Override
    public List<Project> findByName(String name) {
        final String query = "SELECT p FROM Project p WHERE p.name = ?1";

        return projectDAO.getByQuery(query, name);
    }

    @Override
    public List<Project> findByBeginDate(LocalDate beginDate) {
        final String query = "SELECT p FROM Project p WHERE p.beginDate = ?1";

        return projectDAO.getByQuery(query, beginDate);

    }


}
