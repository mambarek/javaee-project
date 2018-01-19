package com.it2go.employee.ui.jsf;

import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.validator.LengthValidator;
import javax.faces.validator.Validator;
import java.io.IOException;

@FacesComponent("xtestInput")
public class TestInput extends UIInput implements NamingContainer {

    private UIInput text;

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
        //text.setValue(this.getValue());
        //text.addValidator();

        super.encodeBegin(context);
    }

    @Override
    public void encodeEnd(FacesContext context) throws IOException {
        super.encodeEnd(context);
        final LengthValidator lengthValidator = new LengthValidator();
        lengthValidator.setMaximum(50);
        lengthValidator.setMinimum(5);
      //  text.addValidator(lengthValidator);
    }

    public UIInput getText() {
        return text;
    }

    public void setText(UIInput text) {
        this.text = text;
    }

    @Override
    public Object getSubmittedValue() {
        return text.getSubmittedValue();
    }
}
