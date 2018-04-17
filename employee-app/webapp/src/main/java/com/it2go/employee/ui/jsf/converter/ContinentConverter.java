package com.it2go.employee.ui.jsf.converter;

import com.it2go.masterdata.Continent;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.Objects;

@FacesConverter("ContinentConverter")
public class ContinentConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (Objects.isNull(value) || value.trim().length() == 0)
            return null;

        return Continent.getContinentWithCode(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (Objects.isNull(value))
            return "";

        Continent res = null;
        try {
            res = (Continent)value;
        } catch (Exception e) {
            return "";
        }
        return res.getName();
    }
}
