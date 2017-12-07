package com.it2go.employee.ui.controller;

import com.it2go.employee.entities.EmailAddress;
import com.it2go.employee.entities.Employee;
import com.it2go.employee.entities.Person;
import com.it2go.employee.persistence.IEmployeeRepository;
import com.it2go.employee.persistence.UserSession;
import com.it2go.framework.dao.EntityConcurrentModificationException;
import com.it2go.framework.dao.EntityNotPersistedException;
import com.it2go.framework.dao.EntityRemovedException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;

@Named
@RequestScoped
public class EditEmployeeController implements BaseViewController{

    public static final String VIEW_ID = "editEmployee";
    //@ManagedProperty("id")
    Long employeeId;

    @Inject
    IEmployeeRepository employeeRepository;

    @Inject
    UserSession userSession;
    @Inject
    WebFlowController webFlowController;

    private Map<String, Object> viewParams;

    private Employee model;
    private EmailAddress modelEmail;

    public EditEmployeeController() {
        System.out.println(">> EditEmployeeController::Constructor!");

    }

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

    @PostConstruct
    public void initView(){

        viewParams = webFlowController.getViewParams(VIEW_ID);
        Object id = null;
        if(viewParams != null)
            id = viewParams.get("id");

        if(id != null){
            model = employeeRepository.findById((Long)id);
        }
        else
            model = new Employee();

        System.out.println("-- EditEmployeeController::initView id = " + id);
        modelEmail = new EmailAddress();
/*        final Employee employee = employeeRepository.findById(employeeId);
        System.out.println("## EditEmployeeController::initView employee = " + employee);
        model = employee;*/
    }

    public String saveEmployee() throws EntityConcurrentModificationException, EntityRemovedException {

        if(model.getFirstName() != null && model.getLastName() != null) {
            if(this.modelEmail.getEmail() != null && this.modelEmail.getEmail().length() > 0)
                this.model.getEmails().add(this.modelEmail);
            System.out.println("## EditEmployeeController::saveEmployee model = " + model);
            Person loggedInUser = userSession.getTestUpdateUser();
            if(model.isNew())
                loggedInUser = userSession.getTestCreationUser();

            employeeRepository.persist(model, loggedInUser);

            // reset the view
            this.resetView();
        }

        return "employeeList?faces-redirect=true";
    }

    public String deleteEmployee() throws EntityNotPersistedException {
        if(this.model != null && !this.model.isNew())
            employeeRepository.remove(this.model);

        this.resetView();

        return "employeeList?faces-redirect=true";
    }

    public String cancel(){
        // reset the view
        this.resetView();

        return "employeeList?faces-redirect=true";
    }

    private void resetView(){
        if(viewParams != null)
            viewParams.remove("id");
        this.model = new Employee();
        this.modelEmail = new EmailAddress();
    }

    @Override
    public String getViewId() {
        return "editEmployee";
    }

    @Override
    public String getPage() {
        return "editEmployee.xhtml";
    }
}
