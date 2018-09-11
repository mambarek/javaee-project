package com.it2go.employee.ui.controller;

import com.it2go.employee.entities.*;
import com.it2go.employee.persistence.IEmployeeRepository;
import com.it2go.employee.persistence.UserSession;
import com.it2go.framework.dao.*;
import com.it2go.framework.util.StringUtils;
import com.it2go.masterdata.Continent;
import lombok.Data;
import org.apache.commons.configuration2.io.FileLocatorUtils;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.Conversation;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ConversationScoped;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Named
@ConversationScoped
@Data
public class EditEmployeeController implements BaseViewController {

    public static final String VIEW_ID = "editEmployee";

    @Inject
    private Conversation conversation;

    private Part file;

    @Inject
    private IEmployeeRepository employeeRepository;

    @Inject
    private UserSession userSession;
    @Inject
    private WebFlowController webFlowController;

    private Map<String, Object> viewParams;

    private Long employeeId;
    private Employee model;
    private int currentTabPage = 1;

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

    public String initPage(){

        if(this.model == null) return "error";

        return null;
    }

    public void initEditEmployeeAction() throws EntityNotFoundException {
        //if (!FacesContext.getCurrentInstance().isPostback()) {
        final String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("hiddenCreateForm:edit_employee_id");
        if(StringUtils.exists(id))
            employeeId = Long.parseLong(id);

        final String create = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("hiddenCreateForm:create_employee");
        final String currentPage = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("hiddenCreateForm:currentTabPage");

        if(StringUtils.exists(currentPage)) {
            try {
                this.currentTabPage = Integer.parseInt(currentPage);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (employeeId != null){
            model = employeeRepository.findById((Long) employeeId);
           // if(model == null) return "error";

            if (model.getAddress() == null)
                model.setAddress(new Address());
        } else {
            if (model == null && "true".equals(create))
                createNewEmployee();
        }
        //}

        //return "/pages/employees/editor.xhtml?faces-redirect=true";
        //return "editEmployee?faces-redirect=true";
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

    public void saveEmployee() throws EntityConcurrentModificationException, EntityRemovedException, EntityNotValidException {

        System.out.println("## EditEmployeeController::saveEmployee model = " + model);

        if (!model.isValid()) throw new EntityNotValidException();

        Person loggedInUser = userSession.getTestUpdateUser();
        if (model.isNew())
            loggedInUser = userSession.getTestCreationUser();

        employeeRepository.persist(model, loggedInUser);
    }

    public void deleteEmployee() throws EntityNotPersistedException {
        System.out.println("## EditEmployeeController::deleteEmployee model = " + model);
        if (this.model == null || this.model.isNew())
            throw new EntityNotPersistedException();

        employeeRepository.remove(this.model);

    }

    public void cancel() {

        closeConversation();
    }

    public void closeConversation(){
        conversation.close();
    }

/*    public String redirectToList(){
        conversation.close();
        return "/pages/employees/jqGrid-table.xhtml?faces-redirect=true&page=" + this.currentTabPage;
    }*/

    private void resetView() {
/*        this.employeeId = null;
        if (viewParams != null)
            viewParams.remove("id");

        this.model = null;
        // reset all components
        resetComponent(FacesContext.getCurrentInstance().getViewRoot().getFacetsAndChildren());*/
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

    public void ajaxSaveAction(AjaxBehaviorEvent event) {

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

    public void testActionListener(){

        String s = "xxx";


    }

    public String ajaxTestAction(AjaxBehaviorEvent event) {

        final UIComponent component = event.getComponent();
        final String componentId = component.getId();
        final Object model = component.getAttributes().get("model");


        return null;
    }

    public String ajaxActionCheckProjectDuration(AjaxBehaviorEvent event) {

        final UIComponent component = event.getComponent();
        final String componentId = component.getId();
        final Object model = component.getAttributes().get("model");

        if(model == null) return null;

        Project project = (Project)model;
        final LocalDate begin = convertToLocalDate(project.getBegin());
        final LocalDate end = convertToLocalDate(project.getEnd());
        if(end != null){
            Period period = Period.between(begin, end);
            if(period.isNegative()){
                ((UIInput)component).setValid(false);
                String msg = "Datum stimmt doch nicht!";
                FacesContext.getCurrentInstance().addMessage(event.getComponent().getClientId(),
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));

            }
        }

        return null;
    }

    public LocalDate convertToLocalDate(java.util.Date dateToConvert) {
        if(dateToConvert == null) return null;

        /*if(dateToConvert instanceof java.sql.Date)
            return ((java.sql.Date)dateToConvert).toLocalDate();

        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();*/

        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }


    public String uploadFile() {
        if(file == null) return null;

        try {
            String contentType = file.getContentType();
            String filename = "file_name_NOT_FOUND";
            if(StringUtils.exists(file.getSubmittedFileName())){
                String[] split = org.apache.commons.lang3.StringUtils.split(file.getSubmittedFileName(), '\\');
                filename = split[split.length - 1];
            }

            InputStream inputStream = file.getInputStream();
            byte[] content = new byte[inputStream.available()];
            inputStream.read(content);

            File newFile = new File();
            newFile.setName(filename);
            newFile.setContentType(contentType);
            newFile.setContent(content);

            upload(newFile);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getFileNameFromPart(Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : partHeader.split(";")) {
            if (content.trim().startsWith("filename")) {
                String fileName = content.substring(content.indexOf('=') + 1)
                        .trim().replace("\"", "");
                return fileName;
            }
        }
        return null;
    }

    public void upload(File file) {
        if(file != null){
            this.model.addDocument(file);
        }
    }

    public void downloadFile(File file) throws IOException {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();

        response.reset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
        response.setContentType(file.getContentType()); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ServletContext#getMimeType() for auto-detection based on filename.
        response.setContentLength(file.getContent().length); // Set it with the file size. This header is optional. It will work if it's omitted, but the download progress will be unknown.
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\""); // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.

        OutputStream output = response.getOutputStream();
        // Now you can write the InputStream of the file to the above OutputStream the usual way.
        // ...
        output.write(file.getContent());
        output.flush();
        output.close();
        fc.responseComplete(); // Important! Otherwise JSF will attempt to render the response which obviously will fail since it's already written with a file and closed.
    }

    public void deleteFile(File file) {
        if(file != null){
            this.model.remoDocument(file);
        }
    }
}
