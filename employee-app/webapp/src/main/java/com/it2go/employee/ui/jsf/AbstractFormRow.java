package com.it2go.employee.ui.jsf;

import lombok.Data;

import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import java.io.IOException;
import java.util.Arrays;

@Data
@FacesComponent(value = "AbstractFormRow")
public class AbstractFormRow extends UIInput implements NamingContainer {

    private boolean visible = true;
    private UIInput input;
    private HtmlInputText test;

    /**
     * Returns the component family of {@link UINamingContainer}.
     * (that's just required by composite component)
     */
    @Override
    public String getFamily() {
        return UINamingContainer.COMPONENT_FAMILY;
    }
    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        Converter converter = (Converter) getAttributes().get("converter");
        if (converter != null) {
            input.setConverter(converter);
        }

        Arrays.stream(this.getValidators()).forEach(input::addValidator);

        if(visible)
            super.encodeBegin(context);
    }

    @Override
    public void encodeEnd(FacesContext context) throws IOException {
        if(visible)
            super.encodeEnd(context);
    }

    @Override
    public Object getSubmittedValue() {
        return input.getSubmittedValue();
    }

    @Override
    protected Object getConvertedValue(FacesContext context, Object newSubmittedValue) throws ConverterException {
        //return super.getConvertedValue(context, newSubmittedValue);
        Converter converter = (Converter) getAttributes().get("converter");
        if (converter != null) {
            return converter.getAsObject(context, this, (String) newSubmittedValue);
        } else {
            return newSubmittedValue;
        }


    }
}
