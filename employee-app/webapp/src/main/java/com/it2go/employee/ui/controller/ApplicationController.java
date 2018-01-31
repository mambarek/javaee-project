package com.it2go.employee.ui.controller;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Named
@ApplicationScoped
public class ApplicationController {

    public String getTranslations(){
        final StringBuilder builder = new StringBuilder();
        //builder.append("{");

        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("com/it2go/EmployeeApp", locale);

        builder.append("'locale':'"+locale.toString()+"',");

        bundle.keySet().forEach(key -> {
            String value = bundle.getString(key);
            builder.append("'"+key+"'");
            builder.append(":'");
            builder.append(value);
            builder.append("', ");
        });

        //builder.append("}");
        String res = builder.toString();
        // remove the last quote
        if(res.endsWith(",")) res.substring(res.length());

        return res;
    }
}
