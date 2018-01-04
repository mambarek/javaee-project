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