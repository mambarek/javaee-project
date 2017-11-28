package com.it2go.employee.persistence;


import com.it2go.employee.entities.Gender;
import com.it2go.employee.entities.Person;
import com.it2go.framework.persistence.IEntityRepository;

import java.time.LocalDate;
import java.util.List;

public interface IPersonRepository extends IDomainEntityRepository<Person> {

    public List<Person> findByFirstName(String name);
    public List<Person> findByLastName(String name);
    public List<Person> findByFullName(String firstName, String lastName);
    public List<Person> findByBirthDate(LocalDate birthDate);
    public List<Person> findByGender(Gender gender);

    public List<Person> testCriteriaAPI();
}
