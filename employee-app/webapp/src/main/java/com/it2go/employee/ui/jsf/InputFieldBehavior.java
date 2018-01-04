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

        String handleAjax = "function(data){\n"+
                "                    var status = data.status;\n" +
                "\n" +
                "                    switch (status) {\n" +
                "                        case 'begin':\n" +
                "                            // This is the start of the AJAX request.\n" +
                "                            //document.getElementsByTagName('body')[0].className = 'loading';\n" +
                "                               console.info('handleAjaxCall begin id: ' + data.source.id);\n" +
                "                            break;\n" +
                "\n" +
                "                        case 'complete':\n" +
                "                            // This is invoked right after AJAX response is returned.\n" +
                "                            break;\n" +
                "\n" +
                "                        case 'success':\n" +
                "                            // This is invoked right after successful processing of AJAX response and update of HTML DOM.\n" +
                "                            //document.getElementsByTagName('body')[0].className = '';\n"+
                                                setStyle +
                "                            break;"+
                "                   }"+
                "               }";


        //String js = "jsf.ajax.request(this,event,{execute:'@this',render:'@form',onevent: "+handleAjax+", onerror: function(){alert('onError call');}});return false;";

        //String js = "jsf.ajax.request(this,event,{execute:'@this',render:'@form',onevent: checkInputStyle, onerror: function(){alert('onError call');}});return false;";
        String s = "$(\"#"+id.replace(":","\\:")+"\").attr(\"data-valid\")";
        String js = "console.info(\"Component valid: \" + "+s+")";

        return js;
    }

    public String getScript_old(ClientBehaviorContext behaviorContext) {

        String id = behaviorContext.getComponent().getClientId();
        String parentId = behaviorContext.getComponent().getParent().getClientId();

        String setStyle = "$('#"+parentId.replace(":","\\\\:")+"').toggleClass('has-success');" +
                "$('#"+id.replace(":","\\\\:")+"').toggleClass('form-control-success');";

        String handleAjax = "function(data){\n"+
                "                    var status = data.status;\n" +
                "\n" +
                "                    switch (status) {\n" +
                "                        case 'begin':\n" +
                "                            // This is the start of the AJAX request.\n" +
                "                            //document.getElementsByTagName('body')[0].className = 'loading';\n" +
                "                               console.info('handleAjaxCall begin id: ' + data.source.id);\n" +
                "                            break;\n" +
                "\n" +
                "                        case 'complete':\n" +
                "                            // This is invoked right after AJAX response is returned.\n" +
                "                            break;\n" +
                "\n" +
                "                        case 'success':\n" +
                "                            // This is invoked right after successful processing of AJAX response and update of HTML DOM.\n" +
                "                            //document.getElementsByTagName('body')[0].className = '';\n"+
                setStyle +
                "                            break;"+
                "                   }"+
                "               }";


        //String js = "jsf.ajax.request(this,event,{execute:'@this',render:'@form',onevent: "+handleAjax+", onerror: function(){alert('onError call');}});return false;";

        String js = "jsf.ajax.request(this,event,{execute:'@this',render:'@form',onevent: checkInputStyle, onerror: function(){alert('onError call');}});return false;";

        return js;
    }

}
