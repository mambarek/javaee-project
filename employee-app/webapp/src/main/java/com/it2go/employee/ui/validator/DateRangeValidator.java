package com.it2go.employee.ui.validator;

import com.it2go.employee.entities.Project;
import lombok.Data;

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
@Data
public class DateRangeValidator implements Validator {

    private UIInput beginInput;
    private UIInput endInput;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        //final Object model = component.getAttributes().get("model");
        final ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        final Object model = elContext.getELResolver().getValue(elContext,null,"project");
        //final String logicalName = (String)component.getAttributes().get("logicalName");
        final String validatorBegin = (String)component.getNamingContainer().getAttributes().get("DateRangeValidator_BEGIN");
        final String validatorEnd = (String)component.getNamingContainer().getAttributes().get("DateRangeValidator_END");

        final String beginFieldLabel = (String)component.getNamingContainer().getAttributes().get("beginFieldLabel");
        final String endFieldLabel = (String)component.getNamingContainer().getAttributes().get("endFieldLabel");



        if(model == null) return;

        Project project = (Project)model;
        LocalDate begin = convertToLocalDate(project.getBegin());
        LocalDate end = convertToLocalDate(project.getEnd());

        String beginLabel = "Beginn";
        String endLabel = "Ende";

        if("true".equals(validatorBegin)) {
            beginLabel = (String)component.getAttributes().get("label");
            endLabel = endFieldLabel;
            begin = convertToLocalDate((Date)value);
        }

        if("true".equals(validatorEnd)) {
            endLabel = (String)component.getAttributes().get("label");
            beginLabel = beginFieldLabel;
            end = convertToLocalDate((Date)value);
        }

        if(end != null){
            Period period = Period.between(begin, end);
            if(period.isNegative()){
                //((UIInput)component).setValid(false);
                String msgText = beginLabel + " darf nicht nach " + endLabel;
                FacesMessage msg =
                        new FacesMessage(msgText, msgText);
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
