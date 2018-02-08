package com.it2go.employee.ui.jsf;

import com.it2go.framework.util.StringUtils;
import lombok.Data;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import java.util.Locale;
import java.util.ResourceBundle;

@Data
@FacesComponent("Row")
public class Row extends UINamingContainer {

    private UIInput input;
    private UIInput hiddenInput;

    public String getDisplayValue() {

        if(input instanceof HtmlSelectBooleanCheckbox){
            final Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
            final String yes = getLocalizedMessage(locale, "employee.app.boolean.yes");
            final String no = getLocalizedMessage(locale, "employee.app.boolean.no");
            final boolean selected = (boolean)input.getValue();

            if(selected) return yes;

            return no;
        }

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
            final Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
            final String s = getLocalizedMessage(locale, "employee.app.mustfield.placeholder");
            if(StringUtils.exists(s))
                placeHolder = s;
        }

        return placeHolder;
    }

    private String getLocalizedMessage(Locale locale, String key) {
        ResourceBundle bundle = ResourceBundle.getBundle("com/it2go/EmployeeApp", locale);

        return bundle.getString(key);
    }

    public String cssHideClass(){

        if((boolean)this.getAttributes().get("readOnly")) return "d-none";

        return "";
    }


}
