package com.it2go.employee.persistence;


import com.it2go.employee.entities.Person;
import com.it2go.employee.persistence.IPersonRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@Stateless
public class UserSession implements Serializable{

    @Inject
    private IPersonRepository personRepository;

    /*
    @Inject
    @DomainEntity(clazz = Person.class)
    private IEntityDAO<Person> personDAO;
    */
    public Person getLoggedInUser(){
        final Person user;

        final List<Person> byFullName = personRepository.findByFullName("Admin", "man");
        if(byFullName.size() > 0)
            user = byFullName.get(0);
        else
            user = null;

        return user;
    }

    public Person getTestCreationUser(){
        final Person user;


        String query = "SELECT p FROM Person p WHERE p.firstName = 'Admin' and p.lastName = 'man'";

        //final List<Person> byFullName = personDAO.getByQuery(query);
        final List<Person> byFullName = personRepository.findByFullName("Admin", "man");

        if(byFullName.size() > 0)
            user = byFullName.get(0);
        else
            user = null;

        return user;
    }

    public Person getTestUpdateUser(){
        final Person user;
        //PersonUmtDAO personUmtDAO = new PersonUmtDAO();
        String query = "SELECT p FROM Person p WHERE p.firstName = 'Update' and p.lastName = 'man'";
        //final List<Person> byFullName = personDAO.getByQuery(query);
        final List<Person> byFullName = personRepository.findByFullName("Update", "man");
        if(byFullName.size() > 0)
            user = byFullName.get(0);
        else
            user = null;

        return user;
    }
}
