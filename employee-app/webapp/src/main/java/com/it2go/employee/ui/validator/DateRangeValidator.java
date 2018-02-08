package com.it2go.employee.ui.validator;

import com.it2go.employee.entities.Project;

import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

@FacesValidator("DateRangeValidator")
public class DateRangeValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        //final Object model = component.getAttributes().get("model");
        final ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        final Object model = elContext.getELResolver().getValue(elContext,null,"project");
        final String logicalName = (String)component.getAttributes().get("logicalName");

        if(model == null) return;

        Project project = (Project)model;
        LocalDate begin = convertToLocalDate(project.getBegin());
        LocalDate end = convertToLocalDate(project.getEnd());

        if("DateRangeValidator.BEGIN".equals(logicalName))
            begin = convertToLocalDate((Date)value);

        if("DateRangeValidator.END".equals(logicalName))
            end = convertToLocalDate((Date)value);

        if(end != null){
            Period period = Period.between(begin, end);
            if(period.isNegative()){
                //((UIInput)component).setValid(false);

                FacesMessage msg =
                        new FacesMessage("Datum stimmt doch nicht!",
                                "Datum stimmt doch nicht bitte beginn und ende kontrollieren!");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);

                throw new ValidatorException(msg);
            }
        }
    }

    public LocalDate convertToLocalDate(java.util.Date dateToConvert) {
        if(dateToConvert == null) return null;

        /*if(dateToConvert instanceof java.sql.Date)
            return ((java.sql.Date)dateToConvert).toLocalDate();

        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();*/

        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }
}
