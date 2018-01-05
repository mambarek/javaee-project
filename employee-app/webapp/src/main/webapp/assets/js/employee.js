function ConfirmYesNo(modalSelector, message) {
    var dfd = jQuery.Deferred();
    var confirm = $(modalSelector);
    confirm.find('.modal-message').html(message);
    confirm.modal('show');
    // $('#myModalLabel').html(title);
    // $('#myModalText').html(msg);
    $('#modal-ok').off('click').click(function () {
        confirm.modal('hide');
        dfd.resolve(1);
        return 1;
    });
    $('#modal-cancel').off('click').click(function () {
        confirm.modal('hide');
        dfd.reject("cancel");
        return 0;
    });
    return dfd.promise();
}

var yesFunction = function () {
    console.info("-- yesFunction() call!");
    $('#inputForm').submit()
};

var yesFunctionAjax = function (event) {
    console.info("-- yesFunctionAjax() call");
    //$('#inputForm').submit()
    jsf.ajax.request(this, event, {render: '@form', execute: '@form'})
};

function ShowConfirmYesNo(event, modalSelector, yesFunc, noFunc, message) {
    // stop the event and trigger asynchron promise
    event.preventDefault();
    //event.stopPropagation();
    var promise = ConfirmYesNo(modalSelector, message);
    promise.then(yesFunc
        , function (reason) {
            console.info("-- ShowConfirmYesNo() no succes reason:", reason);
        })
    console.info("-- ShowConfirmYesNo() Event: ", event);
}

function makeAjax(element, event, execute, render){

    return jsf.ajax.request(element, event,{execute:execute,render:'render'});
}

function checkInputStyle(data){
    var status = data.status;
    var compenentId = data.source.id.replace(new RegExp(':', 'g'),"\\:");

    //var component = $("'#"+compenentId+"'");
    // j_idt23:employeeForm:testAttr
    //var component = $('j_idt23\\:employeeForm\\:testAttr');

    switch (status) {
        case "begin":
            // This is the start of the AJAX request.
            //document.getElementsByTagName('body')[0].className = 'loading';
            console.info("handleAjaxCall begin");
            break;

        case "complete":
            // This is invoked right after AJAX response is returned.
            console.info("handleAjaxCall complete");
            break;

        case "success":
            // This is invoked right after successful processing of AJAX response and update of HTML DOM.
            //document.getElementsByTagName('body')[0].className = '';
            console.info("checkInputStyle success dom valid:" + data.source.getAttribute("data-valid"), data.source);
            var valid = $("\"#"+compenentId+"\"").attr("data-valid");
            console.info("checkInputStyle success jquery valid: " + valid );
            break;
    }
}

function encodeId(id) {
    return id.replace(new RegExp(':', 'g'), "\\:").toString();
}

function validateInputStyle(component){

    var valid = component.attr("data-valid");
    console.info("validateInputStyle component", component, " valid: " + valid);

    component.parent().removeClass('has-danger has-success');
    component.removeClass("form-control-danger form-control-success")
    if (valid == "false") {
        component.parent().addClass('has-danger');
        component.addClass('form-control-danger');
        component.siblings('.form-control-feedback').show();
    }
    else {
        component.parent().addClass('has-success');
        component.addClass('form-control-success');
        component.siblings('.form-control-feedback').hide();
    }
}

function validateForm(form){
    //var allInputFields = form.filter('input[type=text], select');
    form.find("input[type=text], select").each(function(){validateInputStyle($(this))});
}


function handleAjax(data) {
    var status = data.status;
    var compenentId = data.source.id.replace(new RegExp(':', 'g'), "\\:");
    var c1 = $("#j_idt23\\:employeeForm\\:firstName");
    var id = "j_idt23\\:employeeForm\\:firstName";
    var c111 = $("#" + id);
    var encodedId = encodeId(data.source.id);
    var c21 = $("#" + encodedId);


    var input = $("#" + compenentId);
    switch (status) {
        case "begin":
            // This is the start of the AJAX request.
            //document.getElementsByTagName('body')[0].className = 'loading';
            console.info("handleAjaxCall begin");
            break;

        case "complete":
            // This is invoked right after AJAX response is returned.
            break;

        case "success":
            // This is invoked right after successful processing of AJAX response and update of HTML DOM.
            //document.getElementsByTagName('body')[0].className = '';
            console.info("ComponentId: " + data.source.id);
            console.info("Component valid: " + $("#j_idt23\\:employeeForm\\:firstName").attr("data-valid"));
            console.info("checkInputStyle success valid:" + data.source.getAttribute("data-valid"), data.source);
            var valid = $("#" + compenentId).attr("data-valid");
            console.info("checkInputStyle success jquery valid: " + valid);

            validateInputStyle(input);

            break;
    }
}

function checkValidation(data){
    var status = data.status;
    var encodedId = encodeId(data.source.id);
    var form = $("#" + encodedId).closest("form");
    switch (status) {
        case "begin":
            // This is the start of the AJAX request.
            //document.getElementsByTagName('body')[0].className = 'loading';
            console.info("checkValidation begin");
            break;

        case "complete":
            // This is invoked right after AJAX response is returned.
            break;

        case "success":
            // This is invoked right after successful processing of AJAX response and update of HTML DOM.
            //document.getElementsByTagName('body')[0].className = '';

            validateForm(form);
            $('#saveHiddenForm\\:saveOk').click();
            break;
    }
}