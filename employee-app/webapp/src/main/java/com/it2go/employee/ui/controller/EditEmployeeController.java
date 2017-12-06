package com.it2go.employee.ui.controller;

import com.it2go.employee.entities.EmailAddress;
import com.it2go.employee.entities.Employee;
import com.it2go.employee.entities.Person;
import com.it2go.employee.persistence.IEmployeeRepository;
import com.it2go.employee.persistence.UserSession;
import com.it2go.framework.dao.EntityConcurrentModificationException;
import com.it2go.framework.dao.EntityRemovedException;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.flow.FlowScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class EditEmployeeController implements Serializable{

    //@ManagedProperty("id")
    Long employeeId;

    @Inject
    IEmployeeRepository employeeRepository;

    @Inject
    UserSession userSession;

    private Employee model = new Employee();
    private EmailAddress modelEmail = new EmailAddress();

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Employee getModel() {
        return model;
    }

    public void setModel(Employee model) {
        this.model = model;
    }

    public EmailAddress getModelEmail() {
        return modelEmail;
    }

    public void setModelEmail(EmailAddress modelEmail) {
        this.modelEmail = modelEmail;
    }

    public void initView(){

        final Employee employee = employeeRepository.findById(employeeId);
        System.out.println("## editEmployeeOnNewPage ## employee = " + employee);
        model = employee;
    }

    public String saveEmployee() throws EntityConcurrentModificationException, EntityRemovedException {
        if(model.getFirstName() != null && model.getLastName() != null) {
            if(this.modelEmail.getEmail() != null && this.modelEmail.getEmail().length() > 0)
                this.model.getEmails().add(this.modelEmail);
            System.out.println("## editEmployeeOnNewPage model = " + model);
            Person loggedInUser = userSession.getTestUpdateUser();
            if(model.isNew())
                loggedInUser = userSession.getTestCreationUser();

            employeeRepository.persist(model, loggedInUser);

            this.model = new Employee();
            this.modelEmail = new EmailAddress();
        }

        return "faces-test?faces-redirect=true";
    }
}
