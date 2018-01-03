package com.it2go.employee.ui.jsf;

import lombok.Data;

import javax.faces.application.ResourceDependency;
import javax.faces.component.UIInput;
import javax.faces.component.behavior.ClientBehaviorBase;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.component.behavior.FacesBehavior;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputText;

@FacesBehavior("employee.behavior.InputFieldBehavior")
@ResourceDependency(name="jsf.js", library="javax.faces", target="head")
@Data
public class InputFieldBehavior extends ClientBehaviorBase {

    @Override
    public String getScript(ClientBehaviorContext behaviorContext) {

        String id = behaviorContext.getComponent().getClientId();
        String parentId = behaviorContext.getComponent().getParent().getClientId();

        String setStyle = "$('#"+parentId.replace(":","\\\\:")+"').toggleClass('has-success');" +
                "$('#"+id.replace(":","\\\\:")+"').toggleClass('form-control-success');";

        String js = "jsf.ajax.request(this,event,{execute:'@this',render:'@form',onevent: function(){"+setStyle+"}"+
                ",onerror: function(){alert('onError call');}});return false;";

        return js;
    }
}
