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