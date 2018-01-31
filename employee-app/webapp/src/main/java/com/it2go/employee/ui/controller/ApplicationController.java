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

        // build tree map
        Map<String, Object> map;

        bundle.keySet().forEach(key -> {
            String[] names = key.split(".");
            Arrays.stream(names).forEach(name ->{
                //map.containsKey(name)
            });

            String value = bundle.getString(key);
            builder.append(key.replace(".","_"));
            builder.append(":\"");
            builder.append(value);
            builder.append("\" ");
        });

        //builder.append("}");

        return builder.toString();
    }
}
