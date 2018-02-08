package com.it2go.employee.ui.controller;

import com.it2go.employee.dto.EmployeeTableItem;
import com.it2go.employee.dto.EmployeesSearchTemplate;
import com.it2go.employee.entities.EmailAddress;
import com.it2go.employee.entities.Employee;
import com.it2go.employee.entities.Person;
import com.it2go.employee.persistence.IEmployeeRepository;
import com.it2go.employee.persistence.UserSession;
import com.it2go.employee.persistence.view.EmployeesViewRepository;
import com.it2go.framework.dao.EntityConcurrentModificationException;
import com.it2go.framework.dao.EntityRemovedException;
import lombok.Getter;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class EmployeeListController implements BaseViewController{

    @Inject
    private IEmployeeRepository employeeRepository;

    @Inject
    private EmployeesViewRepository employeesViewRepository;

    @Inject
    private UserSession userSession;

    @Inject
    private EditEmployeeController editEmployeeController;

    @Inject
    private WebFlowController webFlowController;

    private Employee model = new Employee();
    @Getter
    private EmployeesSearchTemplate employeesSearchTemplate = new EmployeesSearchTemplate();

    public List<Employee> getAllEmployees(){
       // System.out.println("### EmployeeListController findAll call!");
        final List<Employee> employees = employeeRepository.findAll();
       // System.out.println("EmployeeListController -- > employees found= " + employees.size());

        return employees;
    }

    public List<EmployeeTableItem> getEmplpoyees(){

        return employeesViewRepository.filterEmployees(employeesSearchTemplate);
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

    public void filterEmployees(){
        employeesSearchTemplate.setMaxResult(10);
        //employeesSearchTemplate.getEmployeeTableItem().setFirstName("9");
        //employeesViewRepository.filterEmployees(employeesSearchTemplate);
    }
}
