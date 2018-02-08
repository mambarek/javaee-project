$(document).ready(function(){

    jsf.ajax.addOnEvent(function(data) {
        if (data.status == "success") {
            var viewState = getViewState(data.responseXML);

            for (var i = 0; i < document.forms.length; i++) {
                var form = document.forms[i];

                if (form.method == "post" && !hasViewState(form)) {
                    createViewState(form, viewState);
                }
            }

            if(isIE()) {
                var rootNodeId = getUpdatedRootNodeId(data.responseXML);
                //var input = $('#' + encodeId(data.source.id));
                //validateInputStyle(input);
                //var form = input.closest("form")[0];
                var container = $("#" + encodeId(rootNodeId));
                refreshFormEventListener(container, "click");
                refreshFormEventListener(container, "change");
                refreshFormEventListener(container, "blur")
            }
        }
    });
});

function refreshContainerEvents(containerId){
    if(isIE()){
        var container = $('#' + encodeId(containerId));
        refreshFormEventListener(container, "click");
        refreshFormEventListener(container, "change");
        refreshFormEventListener(container, "blur")
    }
}

/**
 * f:ajax generates an onclick in the submit button. by clicking the first time the form is submitted and the form is rerendered after Ajax is finish.
 * the onclick is in new generated html but in IE there is no Eventlistener registered for click you can see this analysing the button properties in IE DevTools.
 * so the solution is to add manually the Eventlistener after an ajax call for new rendered elments. ist not easy but this works.
 * i add manually every click function for new rendered elements. It works. now i have no trouble with reloading page
 * **/
function refreshFormEventListener(form, eventName){

    var oneventName = 'on' + eventName;

    $(form).find('['+oneventName+']').each(function(index, input){

        var handlerIndex  = form.id + "_" + index + "_" + eventName;
        var eventFunc = input.getAttribute(oneventName);

        if(eventFunc){
            // create a function
            var handler = new Function(eventFunc);

            // check if the input have an event handler
            if(!input[oneventName]) {
                //console.info("## refreshEventListener " + handlerIndex + " eventFunc[",eventFunc,"] this[oneventName][",input[oneventName],"]" );
                //input.removeEventListener(eventName,handler);
                // this is not working on IE
                //input.addEventListener(eventName, handler, false);
                // works  fine
                //input[oneventName] = handler;
                // use better jquery
                $(input).off(eventName).on(eventName,handler);
            }
        }
    });
}

function getViewState(responseXML) {
    var updates = responseXML.getElementsByTagName("update");

    for (var i = 0; i < updates.length; i++) {
        if (updates[i].getAttribute("id").match(/^([\w]+:)?javax\.faces\.ViewState(:[0-9]+)?$/)) {
            return updates[i].textContent || updates[i].innerText;
        }
    }

    return null;
}

function getUpdatedRootNodeId(responseXML) {
    var updates = responseXML.getElementsByTagName("update");

    for (var i = 0; i < updates.length; i++) {
        if (!updates[i].getAttribute("id").match(/^([\w]+:)?javax\.faces\.ViewState(:[0-9]+)?$/)) {
            return updates[i].getAttribute("id");
        }
    }

    return null;
}

function hasViewState(form) {
    for (var i = 0; i < form.elements.length; i++) {
        if (form.elements[i].name == "javax.faces.ViewState") {
            return true;
        }
    }

    return false;
}

function createViewState(form, viewState) {
    var hidden;

    try {
        hidden = document.createElement("<input name='javax.faces.ViewState'>"); // IE6-8.
    } catch(e) {
        hidden = document.createElement("input");
        hidden.setAttribute("name", "javax.faces.ViewState");
    }

    hidden.setAttribute("type", "hidden");
    hidden.setAttribute("value", viewState);
    hidden.setAttribute("autocomplete", "off");
    form.appendChild(hidden);
}