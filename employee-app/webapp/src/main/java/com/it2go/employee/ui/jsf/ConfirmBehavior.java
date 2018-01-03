package com.it2go.employee.ui.jsf;

import lombok.Data;

import javax.faces.application.ResourceDependency;
import javax.faces.component.behavior.ClientBehaviorBase;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.component.behavior.FacesBehavior;

@FacesBehavior("employee.behavior.Confirm")
//@ResourceDependency(name = "confirm.js",target = "head")
@ResourceDependency(name="jsf.js", library="javax.faces", target="head")
@Data
public class ConfirmBehavior extends ClientBehaviorBase {

    private String modalSelector;
    private String message;
    private boolean ajax=false;
    private String successJsFunction;

    @Override
    public String getScript(ClientBehaviorContext behaviorContext) {

        //return "return confirm('Are you sure ?');";
        //return "$('#myModal').modal(null);";
        String yesFunc = "yesFunction";
        if(this.ajax)
            yesFunc = "yesFunctionAjax";

        if(successJsFunction != null)
            yesFunc = successJsFunction;

        return " ShowConfirmYesNo(event,'"+ modalSelector +"',"+yesFunc+", null, '"+this.message+"');";
        //return " ShowConfirmYesNo(event);";
    }



}
