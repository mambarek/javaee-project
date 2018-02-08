package com.it2go.employee.persistence;

import com.it2go.employee.dao.DomainEntity;
import com.it2go.employee.entities.Employee;
import com.it2go.employee.entities.Person;
import com.it2go.framework.dao.EntityConcurrentModificationException;
import com.it2go.framework.dao.EntityNotFoundException;
import com.it2go.framework.dao.EntityNotPersistedException;
import com.it2go.framework.dao.EntityRemovedException;
import com.it2go.framework.dao.IEntityDAO;
import com.it2go.masterdata.Gender;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Stateless
public class EmployeeRepository implements IEmployeeRepository {

    @Inject
    @DomainEntity(clazz = Employee.class)
    private IEntityDAO<Employee> employeeDAO;

    @Override
    public List<Employee> findByFirstName(String firstName) {
        String query = "SELECT employee FROM Employee employee WHERE employee.firstName = ?1";

        return employeeDAO.getByQuery(query, firstName);
    }

    @Override
    public List<Employee> findByLastName(String lastName) {

        String query = "SELECT employee FROM Employee employee WHERE employee.lastName = ?1";

        return employeeDAO.getByQuery(query, lastName);
    }

    @Override
    public List<Employee> findByFullName(String firstName, String lastName) {
        String query = "SELECT employee FROM Employee employee WHERE employee.firstName = ?1 and employee.lastName = ?2";

        return employeeDAO.getByQuery(query, firstName, lastName);
    }

    @Override
    public List<Employee> findByBirthDate(LocalDate birthDate) {
        //String query = "SELECT employee FROM Employee employee WHERE employee.birthDate = :birthDate";
        String query = "SELECT employee FROM Employee employee WHERE employee.birthDate = ?1";

        return employeeDAO.getByQuery(query, new Date());
    }


    @Override
    public Employee persist(Employee employee, Person user) throws EntityConcurrentModificationException, EntityRemovedException {
        Objects.requireNonNull(employee);

        if (employee.isNew()) {
            employee.setCreatedBy(user);
        } else {
            employee.setUpdatedBy(user);
        }
        // check children
        if (employee.getChildren() != null && employee.getChildren().size() > 0) {
            for (Person child : employee.getChildren()) {
                if (child.isNew()) {
                    child.setCreatedBy(user);
                    child.setCreatedAt(new Date());
                }
            }
        }
        return this.employeeDAO.save(employee);
    }

    @Override
    public Employee remove(Employee entity) throws EntityNotPersistedException {
        return employeeDAO.delete(entity);
    }

    @Override
    public void refresh(Employee entity) {
        employeeDAO.refresh(entity);
    }

    @Override
    public <Long> Employee removeById(Long id) throws EntityNotFoundException {
        return employeeDAO.deleteByIdentityKey(id);
    }

    @Override
    public <Long> Employee findById(Long id) throws EntityNotFoundException {
        return this.employeeDAO.getByIdentityKey(id);
    }

    @Override
    public List<Employee> findAll() {
        return employeeDAO.getAll();
    }

    @Override
    public int executeUpdate(String query) {
        return employeeDAO.executeUpdate(query);
    }

    @Override
    public List<Employee> findByGender(Gender gender) {
        String query = "SELECT e from Employee e WHERE e.gender = ?1";

        return employeeDAO.getByQuery(query, gender);
    }

    public Date getLastUpdate(Employee employee) {
        return employeeDAO.getLastUpdateTime(employee);
    }
}
