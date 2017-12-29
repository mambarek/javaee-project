package com.it2go.employee.ui.jsf;

import javax.faces.component.behavior.ClientBehaviorBase;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.component.behavior.FacesBehavior;

@FacesBehavior("employee.behavior.Confirm")
public class ConfirmBehavior extends ClientBehaviorBase {

    @Override
    public String getScript(ClientBehaviorContext behaviorContext) {

        //return "return confirm('Are you sure ?');";
        //return "$('#myModal').modal(null);";
        return " ShowConfirmYesNo();";
    }
}
