package com.it2go.employee.ui.controller;

import com.it2go.employee.entities.*;
import com.it2go.employee.persistence.IEmployeeRepository;
import com.it2go.employee.persistence.UserSession;
import com.it2go.employee.ui.jsf.SelectItemWrapper;
import com.it2go.framework.dao.BaseException;
import com.it2go.framework.dao.EntityConcurrentModificationException;
import com.it2go.framework.dao.EntityNotFoundException;
import com.it2go.framework.dao.EntityNotPersistedException;
import com.it2go.framework.dao.EntityRemovedException;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
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
    public void initView() throws EntityNotFoundException {

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

    public void createNewEmployee(){
        model = new Employee();
    }

    public String editEmployee(Long id) throws EntityNotFoundException {
        if(id != null){
            model = employeeRepository.findById((Long)id);
        }

        return null;
    }

    public void editEmployeeAjax(Long id) throws EntityNotFoundException {
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

        System.out.println("## EditEmployeeController::saveEmployee model = " + model);

        if(!model.isValid()) throw new EntityNotValidException();

        Person loggedInUser = userSession.getTestUpdateUser();
        if(model.isNew())
            loggedInUser = userSession.getTestCreationUser();

        employeeRepository.persist(model, loggedInUser);

        // reset the view
        this.resetView();

    }

    public void deleteEmployee() throws EntityNotPersistedException {
        System.out.println("## EditEmployeeController::deleteEmployee model = " + model);
        if(this.model == null || this.model.isNew())
            throw new EntityNotPersistedException();

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


    /*** Ajax events handling ***/

    public void ajaxSaveAction(AjaxBehaviorEvent event)  {

        final UIComponent component = event.getComponent();
        final String componentId = component.getId();

        try {
            this.saveEmployee();
        } catch (BaseException e) {
            this.handleBaseEcption(e, event.getComponent().getParent().getClientId());
            e.printStackTrace();
        }
    }

    public void ajaxDeleteAction(AjaxBehaviorEvent event) {

        final UIComponent component = event.getComponent();
        final String componentId = component.getId();

        try {
            this.deleteEmployee();
        } catch (BaseException e) {
            this.handleBaseEcption(e, event.getComponent().getParent().getClientId());
            e.printStackTrace();
        }
    }

    private void handleBaseEcption(BaseException e, String clientId){
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        String msg = e.getLocalizedMessage(locale);
        FacesContext.getCurrentInstance().addMessage(clientId,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));

    }

    public SelectItemWrapper[] getGenderList(){

        SelectItemWrapper[] resArray = new SelectItemWrapper[Gender.values().length];
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

        // add null item for select
/*        SelectItemWrapper<Gender> nullValue = new SelectItemWrapper<>(null);
        nullValue.setLocalizedLabel("-- Bitte wählen Sie --");
        resArray[0] = nullValue;*/

        int i=0;
        for (Gender gender:Gender.values()) {
            SelectItemWrapper<Gender> selectItemWrapper = new SelectItemWrapper<>(gender);
            selectItemWrapper.setLocalizedLabel(Gender.getLocalizedNameFor(gender,locale));
            resArray[i] = selectItemWrapper;
            i++;
        }

        return resArray;
    }

    public SelectItem[] getGenderItems(){
        SelectItem[] res = new SelectItem[2];
        res[0] = new SelectItem(Gender.MALE, Gender.MALE.getName());
        res[1] = new SelectItem(Gender.FEMALE, Gender.FEMALE.getName());

        return res;
    }
}
