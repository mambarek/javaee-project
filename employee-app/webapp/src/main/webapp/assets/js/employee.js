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
    if(event)
        event.preventDefault();

    //event.stopPropagation();
    var promise = ConfirmYesNo(modalSelector, message);
    promise.then(yesFunc
        , function (reason) {
            console.info("-- ShowConfirmYesNo() no succes reason:", reason);
        })
    console.info("-- ShowConfirmYesNo() Event: ", event);
}

/**    new     */
//function showConfirmationDialog(element, event, execute, render, oneventFunc, onerrorFunc){
function showConfirmationDialog(element, event, yesFunc){
    //var promise = ConfirmYesNo(modalSelector, message);
    var ajax = makeAjax(element, event, '@form', '@form', ShowConfirmYesNo(null,'#myModal',yesFunc,null, "Message...."), null);
}


function makeAjax(element, event, execute, render, oneventFunc, onerrorFunc){

    return jsf.ajax.request(element, event,{execute:execute,render:render, onevent: oneventFunc, onerror: onerrorFunc});
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
    var inputContainer = component.closest("#inputContainer");
    var disabled = component.attr("disabled");
   // console.info("validateInputStyle component", component, " valid: " + valid);

    // reset highlighting
    //component.parent().removeClass('has-danger has-success');
    inputContainer.removeClass('has-danger has-success');

    var target = component;
    if(component[0].type == "radio") {
        target = component.closest(".input-group");
        // add border
        target.addClass("form-control");
    }

    target.removeClass("form-control-danger form-control-success")

    // when disabled so no highlighting
    if(disabled && disabled != false) return;

    if (valid == "false") {
        inputContainer.addClass('has-danger');
        //component.parent().addClass('has-danger');
        target.addClass('form-control-danger');
        target.siblings('.form-control-feedback').show();
    }
    else {
        inputContainer.addClass('has-success');
        //component.parent().addClass('has-success');
        target.addClass('form-control-success');
        target.siblings('.form-control-feedback').hide();
    }
}

function validateElementWithId(id){
    var element = $("#" + id.replace(new RegExp(':', 'g'),"\\:"));
    //var allInputFields = form.filter('input[type=text], select');
    element.find("input[type=text], select").each(function(){validateInputStyle($(this))});
}

function validateElement(elem){
    var element = $("#" + elem.id.replace(new RegExp(':', 'g'),"\\:"));
    //var allInputFields = form.filter('input[type=text], select');
    element.find("input[type=text], select").each(function(){validateInputStyle($(this))});
}

function validateForm(form){
    //var allInputFields = form.filter('input[type=text], select');
    form.find("input[type=text], input[type=radio], select").each(function(){

        validateInputStyle($(this))
    });
}

function validateWidget(widget){
    widget.children('.widget');
    //var allInputFields = form.filter('input[type=text], select');
    widget.find("input[type=text], input[type=radio], select").each(function(){
        var _this = $(this);
        if(!_this.hasClass('.inputWidget'))
            validateInputStyle(_this)
    });
}

function prepaireView(selector){
    $(selector).find("input[type=text], select").each(function(){
        this.startValue = this.value;
    });
}

function handleAjax(data) {
    var status = data.status;
    var componentId = data.source.id.replace(new RegExp(':', 'g'), "\\:");
    var c1 = $("#j_idt23\\:employeeForm\\:firstName");
    var id = "j_idt23\\:employeeForm\\:firstName";
    var c111 = $("#" + id);
    var encodedId = encodeId(data.source.id);
    var c21 = $("#" + encodedId);


    var input = $("#" + componentId);
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
            var valid = $("#" + componentId).attr("data-valid");
            console.info("checkInputStyle success jquery valid: " + valid);

            validateInputStyle(input);

            //var date = $(data.source.getParent()).hasClass('date');
            //if(date) initDatepicker();
            break;
    }
}

function initDatepicker(elment){
    $(elment).datepicker({
        language: 'de',
        autoClose: true,
        todayHighlight: true,
        format: 'dd.mm.yyyy'
    })
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
            //$('#saveHiddenForm\\:saveOk').click();
            break;
    }
}

function checkValidationAndConfirmSave(data) {
    var status = data.status;
    var encodedId = encodeId(data.source.id);
    var button = $('#'+encodedId);
    var form = button.closest("form");
    var saveFunc = button.attr("data-saveFunc");
    switch (status) {
        case "begin":
            // This is the start of the AJAX request.
            break;

        case "complete":
            // This is invoked right after AJAX response is returned.
            break;

        case "success":
            // This is invoked right after successful processing of AJAX response and update of HTML DOM.

            validateForm(form);
            var error = hasError(form);
            if (!error) {
                var dialogOptions = {
                    title: employee_i18n['employee.overlay.saveData'],
                    message: employee_i18n['employee.overlay.saveDataQuestion'],
                    leftBtnLabel: employee_i18n['employee.overlay.save'],
                    rightBtnLabel: employee_i18n['employee.overlay.cancel'],
                    leftBtnFuncName: saveFunc,
                    leftBtnDismiss: false,
                    rightBtnFunc: null
                };

                //overlay.showConfirm2BtnDialog(dialogOptions);
                overlay.showSpinner('Die daten werden gespeichert');
                setTimeout(
                clickSave, 3000);
                break;
            }
    }
}

function confirmDelete(data) {
    var status = data.status;
    var encodedId = encodeId(data.source.id);
    var button = $('#'+encodedId);
    var form = button.closest("form");
    var deleteFunc = button.attr("data-deleteFunc");
    switch (status) {
        case "begin":
            // This is the start of the AJAX request.
            break;

        case "complete":
            // This is invoked right after AJAX response is returned.
            break;

        case "success":
            // This is invoked right after successful processing of AJAX response and update of HTML DOM.
            var dialogOptions = {
                title: employee_i18n['employee.overlay.deleteData'],
                message: employee_i18n['employee.overlay.deleteDataQuestion'],
                leftBtnLabel:employee_i18n['employee.overlay.delete'],
                rightBtnLabel: employee_i18n['employee.overlay.cancel'],
                leftBtnFuncName: deleteFunc,
                leftBtnDismiss: false,
                rightBtnFunc: null
            };

            overlay.showConfirm2BtnDialog(dialogOptions);
            break;
    }
}

function handleAjaxSaveEvent(data){
    var status = data.status;
    var encodedId = encodeId(data.source.id);
    var form = $("#" + encodedId).closest("form");
    var minLiveTime = 3000; // 3s

    switch (status) {
        case "begin":
            // This is the start of the AJAX request.
            overlay.showSpinner(employee_i18n['employee.overlay.saveDataWaitMessage']);
            break;

        case "complete":
            // This is invoked right after AJAX response is returned.
            break;

        case "success":
            // This is invoked right after successful processing of AJAX response and update of HTML DOM.

            // the overlay should appears min for one second
            var startTime = overlay.startTime;
            var now = new Date().getTime();
            var diff = now - startTime;
            while(diff < minLiveTime ) {
                now = new Date().getTime();
                diff = now - startTime;
            }

            var messages = $('.employeeFormErrorList');
            var options;
            if (messages && messages.children().length > 0) {
                messages.css('display','block');
                options = {
                    title: employee_i18n['employee.overlay.error'],
                    message: employee_i18n['employee.overlay.savingError'],
                    showOnlyRightBtn: true,
                    rightBtnLabel: employee_i18n['employee.overlay.ok']
                };
            }
            else {
                options = {
                    title: employee_i18n['employee.overlay.info'],
                    message: employee_i18n['employee.overlay.savingSuccess'],
                    showOnlyRightBtn: true,
                    rightBtnLabel: employee_i18n['employee.overlay.ok']
                };
            }
            overlay.showConfirm2BtnDialog(options);
            break;
    }
}

function handleAjaxDeleteEvent(data){
    var status = data.status;
    var encodedId = encodeId(data.source.id);
    var form = $("#" + encodedId).closest("form");
    var minLiveTime = 3000; // 3s

    switch (status) {
        case "begin":
            // This is the start of the AJAX request.
            overlay.showSpinner(employee_i18n['employee.overlay.deleteDataWaitMessage']);
            break;

        case "complete":
            // This is invoked right after AJAX response is returned.
            break;

        case "success":
            // This is invoked right after successful processing of AJAX response and update of HTML DOM.

            // the overlay should appears min for one second
            var startTime = overlay.startTime;
            var now = new Date().getTime();
            var diff = now - startTime;
            while(diff < minLiveTime ) {
                now = new Date().getTime();
                diff = now - startTime;
            }

            var messages = $('.employeeFormErrorList');
            var options;
            if (messages && messages.children().length > 0) {
                messages.css('display','block');
                options = {
                    title: employee_i18n['employee.overlay.error'],
                    message: employee_i18n['employee.overlay.deletingError'],
                    showOnlyRightBtn: true,
                    rightBtnLabel: employee_i18n['employee.overlay.ok']
                }
            }
            else {
                options = {
                    title: employee_i18n['employee.overlay.info'],
                    message: employee_i18n['employee.overlay.deletingSuccess'],
                    showOnlyRightBtn: true,
                    rightBtnLabel: employee_i18n['employee.overlay.ok']
                }
            }

            overlay.showConfirm2BtnDialog(options);
            break;
    }
}

function hasError(element){
    var res = false;
    element.find("input[type=text], input[type=radio], select").each(function(){
        var target = $(this);
        if($(this)[0].type == "radio") {
            target = $(this).closest(".input-group");
        }
        var erro = target.hasClass('form-control-danger');
        if(erro){
            res = true;
            return false;}
         }
    );

    return res;
}

function overlayYesNo(modalSelector,message) {
    var dfd = jQuery.Deferred();
    var confirm = $(modalSelector);
    confirm.find('.modal-message').html(message);
    confirm.modal('show');
    // $('#myModalLabel').html(title);
    // $('#myModalText').html(msg);
    $('#overlay-modal-ok').off('click').click(function () {
        confirm.modal('hide');
        dfd.resolve(1);
        return 1;
    });
    $('#overlay-modal-cancel').off('click').click(function () {
        confirm.modal('hide');
        dfd.reject("cancel");
        return 0;
    });
    return dfd.promise();
}

function showOverlay(data){

    var status = data.status;
    if(status == "success") {

        var message = "Overlay -- Ihre daten werden gespeichert...";
        var promise = overlayYesNo("#overlayModal", message);
        promise.then(function () {
                alert("Ihre Daten wurden erfolgreich gespeichert!");
            }
            , function (reason) {
                alert("Ihre Daten konnten nicht gepeichert werden. FEHLER: 00WWQQ");
            });
    }
}

function saveAndshowOverlay(){

        clickSave();
        var message = "Overlay -- Ihre daten werden gespeichert...";
        var promise = overlayYesNo("#overlayModal", message);
        promise.then(function () {

                alert("Ihre Daten wurden erfolgreich gespeichert!");
            }
            , function (reason) {
                alert("Ihre Daten konnten nicht gepeichert werden. FEHLER: 00WWQQ");
            });

}


function checkFormForError(form){
    var res = true;
    form.find("input[type=text], select").each(function(){
                var hasError = $(this).hasClass('form-control-danger');
               if(hasError) {
                   res = false;
                   return false;
               }
            }
        );

    return res;
}

function validateEnclosingFormWithId(elementId){
    var form = $("#" + encodedId.replace(new RegExp(':', 'g'),"\\:")).closest("form");
    validateForm(form);
}

function validateEnclosingForm(elem){
    var form = $("#" + elem.id.replace(new RegExp(':', 'g'),"\\:")).closest("form");
    validateForm(form);
}



function showOverlay(message, callback){
    var dfd = jQuery.Deferred();
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

function clickSave() {
    console.info('##### clickSave call');
    $('.saveConfirmed').click();
}

function clickDelete() {
    $('.deleteConfirmed').click();
}