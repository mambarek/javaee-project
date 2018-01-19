package com.it2go.employee.ui.controller;

import com.it2go.employee.entities.EmailAddress;
import com.it2go.employee.entities.Employee;
import com.it2go.employee.entities.EntityNotValidException;
import com.it2go.employee.entities.Person;
import com.it2go.employee.persistence.IEmployeeRepository;
import com.it2go.employee.persistence.UserSession;
import com.it2go.framework.dao.EntityConcurrentModificationException;
import com.it2go.framework.dao.EntityNotPersistedException;
import com.it2go.framework.dao.EntityRemovedException;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

@Named
@ViewScoped
public class EditEmployeeController implements BaseViewController{

    public static final String VIEW_ID = "editEmployee";

    @Inject
    private IEmployeeRepository employeeRepository;

    @Inject
    private UserSession userSession;
    @Inject
    private WebFlowController webFlowController;

    private Map<String, Object> viewParams;

    private Employee model;

    public EditEmployeeController() {
        System.out.println(">> EditEmployeeController::Constructor!");
    }

    public Employee getModel() {
        return model;
    }

    public void setModel(Employee model) {
        this.model = model;
    }

    //@PostConstruct
    public void initView(){

        System.out.println("-- EditEmployeeController::initView before model: " + model);
        viewParams = webFlowController.getViewParams(VIEW_ID);
        Object id = null;
        if(viewParams != null)
            id = viewParams.get("id");

        if(id != null){
            model = employeeRepository.findById((Long)id);
        }
        else
            model = new Employee();

        System.out.println("-- EditEmployeeController::initView id = " + id + " model: " + model);

/*        final Employee employee = employeeRepository.findById(employeeId);
        System.out.println("## EditEmployeeController::initView employee = " + employee);
        model = employee;*/
    }

    public String editEmployee(Long id){
        if(id != null){
            model = employeeRepository.findById((Long)id);
        }

        return null;
    }

    public void editEmployeeAjax(Long id){
        if(id != null){
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("id",id);
            webFlowController.putViewParams(VIEW_ID, paramsMap);
            model = employeeRepository.findById((Long)id);
        }
        else
            model = new Employee();
    }

    public void saveEmployee() throws EntityConcurrentModificationException, EntityRemovedException, EntityNotValidException {

        if(model.isValid()) {
            System.out.println("## EditEmployeeController::saveEmployee model = " + model);
            Person loggedInUser = userSession.getTestUpdateUser();
            if(model.isNew())
                loggedInUser = userSession.getTestCreationUser();

            employeeRepository.persist(model, loggedInUser);

            // reset the view
            this.resetView();
        }
        else{
            throw new EntityNotValidException();
        }

        //return "employeeList?faces-redirect=true";
    }

    public void deleteEmployee(ActionEvent event) throws EntityNotPersistedException {
        System.out.println("## EditEmployeeController::deleteEmployee model = " + model);
        if(this.model != null && !this.model.isNew())
            employeeRepository.remove(this.model);

        this.resetView();

       // return "employeeList?faces-redirect=true";
    }

    public void cancel(){
        // reset the view
        this.resetView();

       // return "employeeList?faces-redirect=true";
    }

    private void resetView(){
        if(viewParams != null)
            viewParams.remove("id");

        this.model = null;
    }

    public String addNewEmail(){
        System.out.println("*** EditController::addNewEmail before emails size " + this.model.getEmails().size());
        this.model.addEmail(new EmailAddress());
        System.out.println("*** EditController::addNewEmail after emails size " + this.model.getEmails().size());

        return "employeeList?faces-redirect=true";
    }

    @Override
    public String getViewId() {
        return "editEmployee";
    }

    @Override
    public String getPage() {
        return "editEmployee.xhtml";
    }

    public void onChange(ActionEvent actionEvent){
        final String clientId = actionEvent.getComponent().getClientId();
        String compId = clientId +":"+ actionEvent.getComponent().getId();
        compId = compId.replace(":","\\:");
    }
}
