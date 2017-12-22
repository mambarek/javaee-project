package com.it2go.employee.ui.jsf.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

@FacesConverter("com.it2go.employee.ui.jsf.converter.DoubleConverter")
public class DoubleConverter extends javax.faces.convert.DoubleConverter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value == null || value.trim().length() == 0)
            return null;

        return super.getAsObject(context, component, value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null)
            return "";

        return super.getAsString(context, component, value);
    }
}
