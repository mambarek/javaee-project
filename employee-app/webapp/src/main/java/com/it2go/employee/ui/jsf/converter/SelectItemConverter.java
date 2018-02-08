package com.it2go.employee.ui.jsf.converter;

import com.it2go.employee.ui.jsf.SelectItemWrapper;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("com.it2go.employee.ui.jsf.converter.SelectItemConverter")
public class SelectItemConverter implements Converter{

    //private final Object value;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return "<i class=\"fas fa-male\"/>  " + ((SelectItemWrapper)value).getLocalizedLabel();
    }
}
