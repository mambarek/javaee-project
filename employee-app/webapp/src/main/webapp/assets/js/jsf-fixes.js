var viewStateMap = {};

function rescueViewState(){
    var map;
    var all = document.getElementsByTagName('form');
    for(var i=0; i<all.length; i++){
        var form = all[i];
        for (var j = 0; j < form.elements.length; j++) {
            var elem = form.elements[j];
            if (elem && elem.name == "javax.faces.ViewState") {
                //elem.id= 'j_id1:javax.faces.ViewState:' + i;
                viewStateMap[i] = elem;
            }
        }
    }
}

rescueViewState();

function restoreViewState(){
    var map;
    var all = document.getElementsByTagName('form');
    for(var i=0; i<all.length; i++){
        var form = all[i];
        var viewStateElem =  viewStateMap[i];
        for (var j = 0; j < form.elements.length; j++) {
            var elem = form.elements[j];
            if (elem && elem.name == "javax.faces.ViewState") {
                //elem.id= 'j_id1:javax.faces.ViewState:' + i;
               elem.id = viewStateElem.id;
               //elem.value = viewStateElem.value;
            }
        }
    }
}

jsf.ajax.addOnEvent(function(data) {
    if (data.status == "success") {
        var viewState = getViewState(data.responseXML);

        for (var i = 0; i < document.forms.length; i++) {
            var form = document.forms[i];

            if (form.method == "post" && !hasViewState(form)) {
                createViewState(form, viewState);
            }
        }

        //restoreViewState();
    }
});

function getViewState(responseXML) {
    var updates = responseXML.getElementsByTagName("update");

    for (var i = 0; i < updates.length; i++) {
        if (updates[i].getAttribute("id").match(/^([\w]+:)?javax\.faces\.ViewState(:[0-9]+)?$/)) {
            return updates[i].textContent || updates[i].innerText;
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

function repareViewState(){
    var all = document.getElementsByTagName('form');
    for(var i=0; i<all.length; i++){
        var form = all[i];
        for (var j = 0; j < form.elements.length; j++) {
            var elem = form.elements[j];
            if (elem && elem.name == "javax.faces.ViewState") {
                elem.id= 'j_id1:javax.faces.ViewState:' + i;
            }
        }
    }
}

