// Fix jsf IE Ajax

function isIE() {
    var ua = window.navigator.userAgent;

    // Test values; Uncomment to check result …

    // IE 10
    // ua = 'Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)';

    // IE 11
    // ua = 'Mozilla/5.0 (Windows NT 6.3; Trident/7.0; rv:11.0) like Gecko';

    // Edge 12 (Spartan)
    // ua = 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36 Edge/12.0';

    // Edge 13
    // ua = 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586';

    var msie = ua.indexOf('MSIE ');
    if (msie > 0) {
        // IE 10 or older => return version number
        return parseInt(ua.substring(msie + 5, ua.indexOf('.', msie)), 10);
    }

    var trident = ua.indexOf('Trident/');
    if (trident > 0) {
        // IE 11 => return version number
        var rv = ua.indexOf('rv:');
        return parseInt(ua.substring(rv + 3, ua.indexOf('.', rv)), 10);
    }

    var edge = ua.indexOf('Edge/');
    if (edge > 0) {
        // Edge (IE 12+) => return version number
        return parseInt(ua.substring(edge + 5, ua.indexOf('.', edge)), 10);
    }

    // other browser
    return false;
}

function encodeId(id) {
    return id.replace(new RegExp(':', 'g'), "\\:").toString();
}

var _eventHandler = {};

// initHandler in documentReady aufrufen um den dom evnt zu intialisieren
function initHandler(eventName){
    var oneventName = 'on' + eventName;
    $("form").each(function(i, form){
        $(form).find('['+oneventName+']').each(function(index, input){
            var handlerIndex  = form.id + "_" + index + "_" + eventName;
            var eventFunc = input.getAttribute(oneventName);
            if(eventFunc)
                _eventHandler[handlerIndex] = new Function(eventFunc);
        })
    })
}

/**
 * f:ajax generates an onclick in the submit button. by clicking the first time the form is submitted and the form is rerendered after Ajax is finish.
 * the onclick is in new generated html but in IE there is no Eventlistener registered for click you can see this analysing the button properties in IE DevTools.
 * so the solution is to add manually the Eventlistener after an ajax call for new rendered elments. ist not easy but this works.
 * i add manually every click function for new rendered elements. It works. now i have no trouble with reloading page
 * **/
function refreshFormEventListener(form, eventName){

    var oneventName = 'on' + eventName;

    form.find('['+oneventName+']').each(function(index, input){

        var handlerIndex  = form.id + "_" + index + "_" + eventName;
        var eventFunc = input.getAttribute(oneventName);
        var handler = _eventHandler[handlerIndex];

        if(eventFunc){
            // if no handler so create one
            if(!handler){
                handler = new Function(eventFunc);
                _eventHandler[handlerIndex] = handler;
            }

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

function refreshRootEventListener(rootId, eventName){
    var root = $('#' + encodeId(rootId));
    var oneventName = 'on' + eventName;

    refreshFormEventListener(root, eventName);

}

function refreshAllRootEventListener(rootId) {
    console.info("++ refreshAllRootEventListener fired", rootId, event);
    if (isIE()) {
        refreshRootEventListener(rootId, "click");
        refreshRootEventListener(rootId, "change");
        refreshRootEventListener(rootId, "blur");
    }
}

function handleAjax(data, containerId) {

    var status = data.status;
    var input = $(data.source);
    // the input coming from ajax may be not bound in the dom
    // so try to get the real input with its id, may be w user another attribute
    // for searching but id should be in all inputs
    if(data.source.id){
        var componentId = encodeId(data.source.id);
        input = $("#" + componentId);
    }

    switch (status) {
        case "begin":
            // This is the start of the AJAX request.
            //console.info("handleAjaxCall begin");
            break;

        case "complete":
            // This is invoked right after AJAX response is returned.
            break;

        case "success":
            // This is invoked right after successful processing of AJAX response and update of HTML DOM.
            console.info("$$ handleAjax success fired!");
            validateFormInput(input);
            var _containerId = containerId;
            if(!_containerId) {
                var form = input.closest("form")[0];
                if(form)
                    _containerId = form.id;
            }

            if(_containerId)
                refreshAllRootEventListener(encodeId(_containerId));

            break;
    }
}