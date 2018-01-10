package com.it2go.employee.ui.jsf;

import com.it2go.employee.entities.EmailAddress;
import com.it2go.framework.util.reflection.ReflectionUtil;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;

import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@FacesComponent("com.it2go.employee.ui.jsf.EmailListComponent")
public class EmailListComponent extends UIInputComponent implements NamingContainer{

   // private Object model;
  //  private String listAttributeName;
    private List<UIInput> emailList;

    public void addNewEmail(){
        System.out.println("*** EmailListComponent addNewEmail() call !!!");
        //emailList = (List<EmailAddress>)this.getAttributes().get("emailList");
        EmailAddress emailAddress = new EmailAddress();
        this.getEmailList().add(emailAddress);
    }

    public void ajaxAddNewEmail(AjaxBehaviorEvent event){
        System.out.println("*** EmailListComponent ajaxAddNewEmail() call !!!");
        //emailList = (List<EmailAddress>)this.getAttributes().get("emailList");
        EmailAddress emailAddress = new EmailAddress();
        this.getEmailList().add(emailAddress);
    }

    public void removeEmail(int index){
        System.out.println("*** EmailListComponent removeEmail() index: " +index);
        this.getEmailList().remove(index);
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        //model = this.getAttributes().get("model");
        //listAttributeName = (String)this.getAttributes().get("listAttributeName");

        this.setEmailList(new ArrayList<>());
        //final List<EmailAddress> attributeValue = ReflectionUtil.getAttributeValue(model, listAttributeName);
        final List<EmailAddress> attributeValue = (List<EmailAddress>) this.getValue();
        this.getEmailList().addAll(attributeValue);

        super.encodeBegin(context);
    }

    @Override
    public Object getSubmittedValue() {
        return this.getEmailList();
    }

    public List<EmailAddress> getEmailList() {
        return (List<EmailAddress>) this.getStateHelper().get("emailList");
    }

    public void setEmailList(List<EmailAddress> emailList) {
        this.getStateHelper().put("emailList", emailList);
    }
}
