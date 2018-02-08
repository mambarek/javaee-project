package com.it2go.employee.ui.controller;


import com.it2go.employee.entities.ApplicationUser;
import com.it2go.employee.entities.Person;
import lombok.Data;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;

@SessionScoped
@Named
@Data
public class LoginController implements Serializable {

    private ApplicationUser loggedInUser;

    public boolean isUserLoggedIn() {

        Principal userPrincipal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();

        return userPrincipal != null;
    }

    public void logout(){

        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        try {
            request.logout();
            this.loggedInUser = null;
        } catch (ServletException e) {
            e.printStackTrace();
        }

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath()+"/index.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
