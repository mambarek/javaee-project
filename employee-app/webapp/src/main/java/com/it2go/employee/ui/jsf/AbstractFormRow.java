package com.it2go.employee.ui.jsf;

import lombok.Data;

import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.Arrays;

@Data
@FacesComponent(value = "AbstractFormRow")
public class AbstractFormRow extends UIInputComponent implements NamingContainer {

    final static String START_VALUE = "startValue";

    private boolean visible = true;
    private UIInput input;
    private UIInput hiddenInput;

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
        if (input != null) {
/*        Converter converter = (Converter) getAttributes().get("converter");
        if (converter != null) {
            input.setConverter(converter);
        }*/
            input.setConverter(this.getConverter());

            boolean readOnly = (boolean) this.getAttributes().get("readOnly");

            Arrays.stream(input.getValidators()).forEach(input::removeValidator);
            if (!readOnly)
                Arrays.stream(this.getValidators()).forEach(input::addValidator);
            else
                input.setRendered(false);
        }
        if (visible)
            super.encodeBegin(context);
    }

    public String getDisplayValue() {

        if (this.getConverter() == null)
            return getValue() != null ? getValue().toString() : "";

        return this.getConverter().getAsString(FacesContext.getCurrentInstance(), input, getValue());
    }

    public String getPlaceHolder() {

        String placeHolder = (String) this.getAttributes().get("placeholder");
        if ((placeHolder == null || placeHolder.isEmpty()) && (boolean) this.getAttributes().get("isRequired"))
            placeHolder = "Bitte angeben";

        return placeHolder;
    }

    public String getRequired(){
        if((boolean) this.getAttributes().get("isRequired"))
            return "required";

        return "";
    }

    @Override
    public void resetValue() {
        super.resetValue();
        if(this.input != null)
            this.input.resetValue();

        if(this.hiddenInput != null)
            this.hiddenInput.resetValue();
    }

    public Object getStartValue() {
        return this.getStateHelper().get(START_VALUE);
    }

    public void setStartValue(Object startValue) {
        this.getStateHelper().put(START_VALUE, startValue);
    }
}
