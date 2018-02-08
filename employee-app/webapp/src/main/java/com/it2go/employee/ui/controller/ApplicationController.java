package com.it2go.employee.ui.controller;

import com.it2go.employee.entities.Gender;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Named
@ApplicationScoped
public class ApplicationController implements Serializable{

    public String getTranslations(){
        final StringBuilder builder = new StringBuilder();

        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("com/it2go/EmployeeApp", locale);

        builder.append("'locale':'").append(locale.toString()).append("',");

        bundle.keySet().forEach(key -> {
            String value = bundle.getString(key);
            builder.append("'").append(key).append("'");
            builder.append(":'");
            builder.append(value);
            builder.append("', ");
        });

        return  builder.toString();
    }

    public long getSessionTimeoutPeriod() {

        return FacesContext.getCurrentInstance().getExternalContext().getSessionMaxInactiveInterval();
    }

    public String getLocalizedNameFor(Object dataObject){
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

        if(dataObject instanceof Gender){
            String resLabel;
            Gender gender = (Gender)dataObject;
            try {
                ResourceBundle resourceBundle = ResourceBundle.getBundle("com/it2go/gender/gender", locale);
                resLabel = resourceBundle.getString(gender.toString());
            }catch (Exception e){
                e.printStackTrace();
                resLabel = gender.getName();
            }

            return resLabel;
        }

        return "";
    }
}
