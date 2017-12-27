package com.it2go.employee.ui.jsf;

import com.it2go.employee.entities.EmailAddress;

import javax.annotation.PostConstruct;
import javax.faces.component.FacesComponent;
import java.util.List;

@FacesComponent("com.it2go.employee.ui.jsf.EmailListComponent")
public class EmailListComponent extends UICompositeComponent {

    private List<EmailAddress> emailList;

    public void init(){
        emailList = (List<EmailAddress>)this.getAttributes().get("emailList");
         if(emailList == null)
             System.out.println("*** EmailListComponent init emailList is NULL ");
         else
            System.out.println("*** EmailListComponent init emailList: " + emailList.size());
    }

    public void addNewEmail(){
        System.out.println("*** EmailListComponent addNewEmail() call !!!");
        //emailList = (List<EmailAddress>)this.getAttributes().get("emailList");
        EmailAddress emailAddress = new EmailAddress();
        emailList.add(emailAddress);
    }

    public void removeEmail(int index){
        System.out.println("*** EmailListComponent removeEmail() call !!!");
        emailList.remove(index);
    }
}
