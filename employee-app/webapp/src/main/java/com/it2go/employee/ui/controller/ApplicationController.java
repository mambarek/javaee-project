package com.it2go.employee.ui.controller;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

@Named
@ApplicationScoped
public class ApplicationController implements Serializable {

    public String getTranslations() {
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

        return builder.toString();
    }

    public long getSessionTimeoutPeriod() {

        return FacesContext.getCurrentInstance().getExternalContext().getSessionMaxInactiveInterval();
    }


}
