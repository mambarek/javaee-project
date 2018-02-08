package com.it2go.employee.ui.jsf;

import lombok.Data;

import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import java.util.Locale;
import java.util.ResourceBundle;

@Data
@FacesComponent(value = "Row")
public class Row extends UIInputComponent implements NamingContainer {

    private boolean visible = true;
    private UIInput input;
    private UIInput hiddenInput;

    public String getDisplayValue() {

        final Converter inputConverter = input.getConverter();

        if (inputConverter == null)
            return input.getValue() != null ? input.getValue().toString() : "";

        return inputConverter.getAsString(FacesContext.getCurrentInstance(), input, input.getValue());
    }

    public String getPlaceHolder() {

        String placeHolder = (String) this.getAttributes().get("placeholder");
        if ((placeHolder == null || placeHolder.isEmpty()) && (boolean) this.getAttributes().get("isRequired")) {
            placeHolder = "Bitte angeben";
            //final String s = getResourceBundleMap().get("employee.app.mustfiled.placeholder");
            final String s = getLocalizedMessage(FacesContext.getCurrentInstance().getViewRoot().getLocale(), "employee.app.mustfiled.placeholder");
            if(s != null)
                placeHolder = s;
        }

        return placeHolder;
    }

    public String getLocalizedMessage(Locale locale, String key) {
        ResourceBundle bundle = ResourceBundle.getBundle("com/it2go/EmployeeApp", locale);

        return bundle.getString(key);
    }
}


