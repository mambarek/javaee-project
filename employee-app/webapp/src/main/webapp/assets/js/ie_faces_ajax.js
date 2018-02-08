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

function refreshInputEventListener(_input, eventName) {
    var input = _input;//$('#' + encodeId(_input.id)).get(0);
    var oneventName = 'on' + eventName;
    if(input.removeEventListeners)
        input.removeEventListeners(eventName,input[oneventName], true);

    var eventFunc = input.getAttribute(oneventName);

    if(eventFunc && !input[oneventName]) {
        console.info("## refreshEventListener " + input.id + " eventFunc[",eventFunc,"] this[oneventName][",input[oneventName],"]" );
        input[oneventName] = new Function(eventFunc);
        input.addEventListener(eventName, input[oneventName]);
    }
}

/**
 * f:ajax generates an onclick in the submit button. by clicking the first time the form is submitted and the form is rerendered after Ajax is finish.
 * the onclick is in new generated html but in IE there is no Eventlistener registered for click you can see this analysing the button properties in IE DevTools.
 * so the solution is to add manually the Eventlistener after an ajax call for new rendered elments. ist not easy but this works.
 * i add manually every click function for new rendered elements. It works. now i have no trouble with reloading page
 * **/
function refreshRootEventListener(rootId, eventName){
    var form = $('#' + rootId);
    var oneventName = 'on' + eventName;

    // form.find("input[type=text], input[type=radio], select").each(function(){
    form.find('['+oneventName+']').each(function(index, input){
        if(input.removeEventListeners)
            input.removeEventListeners(eventName,input[oneventName], true);

        var eventFunc = input.getAttribute(oneventName);

        if(eventFunc && !input[oneventName]) {
            console.info("## refreshEventListener " + input.id + " eventFunc[",eventFunc,"] this[oneventName][",input[oneventName],"]" );
            input[oneventName] = new Function(eventFunc);
            input.addEventListener(eventName, input[oneventName]);
        }

    });
}

function refreshEventListener(root, eventName) {

    //var form = $('#' + rootId);
    var oneventName = 'on' + eventName;
    //console.info("-- refreshEventListener fired", rootId, form,oneventName);
    // form.find("input[type=text], input[type=radio], select").each(function(){
    root.find('[' + oneventName + ']').each(function (index, input) {
        //refreshInputEventListener(input, eventName);
        if(input.removeEventListeners)
            input.removeEventListeners(eventName,input[oneventName], true);

        var eventFunc = input.getAttribute(oneventName);

        if(eventFunc && !input[oneventName]) {
            console.info("## refreshEventListener " + input.id + " eventFunc[",eventFunc,"] this[oneventName][",input[oneventName],"]" );
            input[oneventName] = new Function(eventFunc);
            input.addEventListener(eventName, input[oneventName]);
        }
    });
}

function refreshAllInputEventListener(input) {
    //console.info("++ refreshAllEventListener fire", rootId, event);
    if (isIE()) {
        refreshInputEventListener(input, "click");
        refreshInputEventListener(input, "change");
        refreshInputEventListener(input, "blur");
    }
}

function refreshAllEventListener(root) {
    //console.info("++ refreshAllEventListener fire", rootId, event);
    if (isIE()) {
        refreshEventListener(root, "click");
        refreshEventListener(root, "change");
        refreshEventListener(root, "blur");
    }
}

function refreshAllRootEventListener(rootId) {
    //console.info("++ refreshAllEventListener fire", rootId, event);
    if (isIE()) {
        refreshRootEventListener(rootId, "click");
        refreshRootEventListener(rootId, "change");
        refreshRootEventListener(rootId, "blur");
    }
}

function handleAjax(data) {
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

            //var valid = $("#" + componentId).attr("data-valid");
            //console.info("checkInputStyle success jquery valid: " + valid);

            /*validateInputStyle(input);
            var inputContainer = input.closest(".inputContainer")[0];*/

            validateFormInput(input);
            //var inputContainer = $(input.closest("form")[0]);
            var inputContainer = input.closest("form")[0];
            //var inputContainer = $(input.closest("form"));

            if(inputContainer) {
                //var t1 = $(inputContainer);
                //var t2 = $('#' + encodeId(inputContainer.id));
                refreshAllRootEventListener(encodeId(inputContainer.id));
                //refreshAllEventListener($(inputContainer));
                //refreshAllEventListener($('#' + encodeId(inputContainer.id)));
            }
            else
                refreshAllInputEventListener(data.source);

            break;
    }
}