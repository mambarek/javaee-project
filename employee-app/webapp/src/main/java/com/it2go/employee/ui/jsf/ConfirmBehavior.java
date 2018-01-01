package com.it2go.employee.ui.jsf;

import javax.faces.application.ResourceDependency;
import javax.faces.component.behavior.ClientBehaviorBase;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.component.behavior.FacesBehavior;

@FacesBehavior("employee.behavior.Confirm")
//@ResourceDependency(name = "confirm.js",target = "head")
@ResourceDependency(name="jsf.js", library="javax.faces", target="head")
public class ConfirmBehavior extends ClientBehaviorBase {

    private String message;
    private boolean ajax=false;

    @Override
    public String getScript(ClientBehaviorContext behaviorContext) {

        //return "return confirm('Are you sure ?');";
        //return "$('#myModal').modal(null);";
        String yesFunc = "yesFunction";
        if(this.ajax)
            yesFunc = "yesFunctionAjax";

        return " ShowConfirmYesNo(event, "+yesFunc+", null, '"+this.message+"');";
        //return " ShowConfirmYesNo(event);";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isAjax() {
        return ajax;
    }

    public void setAjax(boolean ajax) {
        this.ajax = ajax;
    }
}
