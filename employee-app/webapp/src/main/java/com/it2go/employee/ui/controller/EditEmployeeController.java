package com.it2go.employee.ui.controller;

import com.it2go.employee.entities.*;
import com.it2go.employee.persistence.IEmployeeRepository;
import com.it2go.employee.persistence.UserSession;
import com.it2go.framework.dao.BaseException;
import com.it2go.framework.dao.EntityConcurrentModificationException;
import com.it2go.framework.dao.EntityNotFoundException;
import com.it2go.framework.dao.EntityNotPersistedException;
import com.it2go.framework.dao.EntityRemovedException;
import com.it2go.masterdata.Continent;
import lombok.Data;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Named
@SessionScoped
@Data
public class EditEmployeeController implements BaseViewController {

    public static final String VIEW_ID = "editEmployee";

    @Inject
    private IEmployeeRepository employeeRepository;

    @Inject
    private UserSession userSession;
    @Inject
    private WebFlowController webFlowController;

    private Map<String, Object> viewParams;

    private Long employeeId;
    private Employee model;
    private int currentTabPage;

    // this must be moved to a country view select
    // contains continents and countries
    private Continent selectedContinent;

    public EditEmployeeController() {
        System.out.println(">> EditEmployeeController::Constructor!");
    }

    public Employee getModel() {
        return model;
    }

    public void setModel(Employee model) {
        this.model = model;
    }

    public void createNewEmployee() {
        model = new Employee();
        model.setAddress(new Address());
    }

    public String editEmployee(Long id) throws EntityNotFoundException {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            if (id != null) {
                model = employeeRepository.findById((Long) id);
                if (model.getAddress() == null)
                    model.setAddress(new Address());
            } else {
                if (model == null)
                    createNewEmployee();
            }
        }

        return null;
    }

    public String editEmployee() throws EntityNotFoundException {
        if(this.model != null && this.model.getId() != null && this.model.getId().equals(this.employeeId))
            return null;

        return editEmployee(this.employeeId);
    }

    public void editEmployeeAjax(Long id) throws EntityNotFoundException {
        if (id != null) {
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("id", id);
            webFlowController.putViewParams(VIEW_ID, paramsMap);
            model = employeeRepository.findById((Long) id);
            if(model.getAddress() == null)
                model.setAddress(new Address());
        }
    }

    public String saveEmployee() throws EntityConcurrentModificationException, EntityRemovedException, EntityNotValidException {

        System.out.println("## EditEmployeeController::saveEmployee model = " + model);

        if (!model.isValid()) throw new EntityNotValidException();

        Person loggedInUser = userSession.getTestUpdateUser();
        if (model.isNew())
            loggedInUser = userSession.getTestCreationUser();

        employeeRepository.persist(model, loggedInUser);

        // reset the view
        this.resetView();

        return "/pages/employees/jqGrid-table.xhtml?faces-redirect=true&page=" + this.currentTabPage;
    }

    public String deleteEmployee() throws EntityNotPersistedException {
        System.out.println("## EditEmployeeController::deleteEmployee model = " + model);
        if (this.model == null || this.model.isNew())
            throw new EntityNotPersistedException();

        employeeRepository.remove(this.model);

        this.resetView();

        // return "employeeList?faces-redirect=true";
        return "/pages/employees/jqGrid-table.xhtml?faces-redirect=true&page=" + this.currentTabPage;
    }

    public String cancel() {
        // reset the view
        this.resetView();

        // return "employeeList?faces-redirect=true";
        //return "/pages/employees/jqGrid-table.xhtml?faces-redirect=true";

        return "/pages/employees/jqGrid-table.xhtml?faces-redirect=true&page=" + this.currentTabPage;
    }

    private void resetView() {
        this.employeeId = null;
        if (viewParams != null)
            viewParams.remove("id");

        this.model = null;
        // reset all components
        resetComponent(FacesContext.getCurrentInstance().getViewRoot().getFacetsAndChildren());
    }

    private void resetComponent(Iterator<UIComponent> componentIterator){
        while(componentIterator.hasNext()) {
            UIComponent component = componentIterator.next();
            if(component instanceof UIInput){
                UIInput input = (UIInput)component;
                input.resetValue();
            }
            else{
                resetComponent(component.getFacetsAndChildren());
            }
        }
    }

    public String addNewEmail() {
        System.out.println("*** EditController::addNewEmail before emails size " + this.model.getEmails().size());
        this.model.addEmail(new EmailAddress());
        System.out.println("*** EditController::addNewEmail after emails size " + this.model.getEmails().size());

        return "employeeList?faces-redirect=true";
    }

    public String addProject(){
        this.model.addProject(new Project());

        return null;
    }

    public String removeProject(Project project){
        this.model.removeProject(project);

        return null;
    }

    @Override
    public String getViewId() {
        return "editEmployee";
    }

    @Override
    public String getPage() {
        return "editEmployee.xhtml";
    }

    public void onChange(ActionEvent actionEvent) {
        final String clientId = actionEvent.getComponent().getClientId();
        String compId = clientId + ":" + actionEvent.getComponent().getId();
        compId = compId.replace(":", "\\:");
    }


    /*** Ajax events handling ***/

    public String ajaxSaveAction(AjaxBehaviorEvent event) {

        final UIComponent component = event.getComponent();
        final String componentId = component.getId();

        try {
            return this.saveEmployee();
        } catch (BaseException e) {
            this.handleBaseEcption(e, event.getComponent().getParent().getClientId());
            e.printStackTrace();
        }

        return null;
    }

    public String ajaxDeleteAction(AjaxBehaviorEvent event) {

        final UIComponent component = event.getComponent();
        final String componentId = component.getId();

        try {
            return this.deleteEmployee();
        } catch (BaseException e) {
            this.handleBaseEcption(e, event.getComponent().getParent().getClientId());
            e.printStackTrace();
        }

        return null;
    }

    private void handleBaseEcption(BaseException e, String clientId) {
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        String msg = e.getLocalizedMessage(locale);
        FacesContext.getCurrentInstance().addMessage(clientId,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));

    }

    public Continent getSelectedContinent() {
        return selectedContinent;
    }

    public void setSelectedContinent(Continent selectedContinent) {
        this.selectedContinent = selectedContinent;
    }

    public FacesMessage.Severity getSeverityError(){
        return FacesMessage.SEVERITY_ERROR;
    }

    public String checkProjectDuration(Project project){

        if(project == null) return null;

        final Date begin = project.getBegin();
        final Date end = project.getEnd();


        return null;
    }

    public String testActionListener(){

        String s = "xxx";


        return null;
    }
}
