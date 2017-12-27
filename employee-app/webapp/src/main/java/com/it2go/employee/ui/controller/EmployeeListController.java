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
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@SessionScoped
public class EmployeeListController implements BaseViewController{

    @Inject
    IEmployeeRepository employeeRepository;

    @Inject
    UserSession userSession;

    @Inject
    EditEmployeeController editEmployeeController;

    @Inject
    WebFlowController webFlowController;

    private Employee model = new Employee();

    public List<Employee> getAllEmployees(){
       // System.out.println("### EmployeeListController findAll call!");
        final List<Employee> employees = employeeRepository.findAll();
       // System.out.println("EmployeeListController -- > employees found= " + employees.size());

        return employees;
    }

    public String saveEmployee() throws EntityConcurrentModificationException, EntityRemovedException {
        if(model.getFirstName() != null && model.getLastName() != null) {
            System.out.println("## model = " + model);
            Person loggedInUser = userSession.getTestUpdateUser();
            if(model.isNew())
                loggedInUser = userSession.getTestCreationUser();

            employeeRepository.persist(model, loggedInUser);

            this.model = new Employee();
        }

        return "employeeList?faces-redirect=true";
    }

    public String editEmployee(Long id){

/*        final Employee employee = employeeRepository.findById(id);
        System.out.println("editEmployee ## employee = " + employee);
        this.model = employee;*/
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("id",id);
        webFlowController.putViewParams(EditEmployeeController.VIEW_ID, paramsMap);

        return "employeeList?faces-redirect=true";
    }

    public String editEmployeeOnNewPage(Long id){

/*        final Employee employee = employeeRepository.findById(id);
        System.out.println("editEmployeeOnNewPage ## employee = " + employee);
        editEmployeeController.setModel(employee);*/

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("id",id);
        webFlowController.putViewParams(EditEmployeeController.VIEW_ID, paramsMap);

        return "editEmployee?faces-redirect=true";
    }

    public Employee getModel() {
        return model;
    }

    public void setModel(Employee model) {
        this.model = model;
    }


    @Override
    public String getViewId() {
        return null;
    }

    @Override
    public String getPage() {
        return null;
    }
}
