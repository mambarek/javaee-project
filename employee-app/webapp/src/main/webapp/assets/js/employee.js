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
    console.info("validateInputStyle component", component, " valid: " + valid);

    // reset hightlighting
    //component.parent().removeClass('has-danger has-success');
    inputContainer.removeClass('has-danger has-success');
    component.removeClass("form-control-danger form-control-success")

    if (valid == "false") {
        inputContainer.addClass('has-danger');
        //component.parent().addClass('has-danger');
        component.addClass('form-control-danger');
        component.siblings('.form-control-feedback').show();
    }
    else {
        inputContainer.addClass('has-success');
        //component.parent().addClass('has-success');
        component.addClass('form-control-success');
        component.siblings('.form-control-feedback').hide();
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
    form.find("input[type=text], select").each(function(){validateInputStyle($(this))});

}

function prepaireView(selector){
    $(selector).find("input[type=text], select").each(function(){
        this.startValue = this.value;
    });
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
            //$('#saveHiddenForm\\:saveOk').click();
            break;
    }
}

function handlaAjaxSaveEvent(data){
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
            var error = hasError(form);
            if(!error) {
                //ShowConfirmYesNo(null, "#myModal", processShowOverlay, null, "Änderungen speichern?");
                var promise = ConfirmYesNo("#myModal", "Änderungen speichern?");
/*                promise.then(function(){

                        // clickSave(); // save data
                        showOverlayOnly();
                    }
                    , function (reason) {
                        console.info("-- ShowConfirmYesNo() no succes reason:", reason);
                    })*/
            }
            //$('#saveHiddenForm\\:saveOk').click();
            break;
    }

}

function hasError(element){
    var res = false;
    element.find("input[type=text], select").each(function(){
        var erro = $(this).hasClass('form-control-danger');
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

function saveAndshowOverlayOnly(){

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

function processShowOverlay() {
    //$('#saveHiddenForm\\:saveOk').click();
    //TODO HIER SELECTOR MUSS DANN IRGEDWIE ÜBERGEBEN und nicht fest verdrahtet
    $('.showOverlay').click();
}

function clickSave() {
    //$('#saveHiddenForm\\:saveOk').click();
    //TODO HIER SELECTOR MUSS DANN IRGEDWIE ÜBERGEBEN und nicht fest verdrahtet
    $('.saveOk').click();
}

function processSaveAction(){

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

/**
 * Module for displaying "Waiting for..." dialog using Bootstrap
 *
 * @author Eugene Maslovich <ehpc@em42.ru>
 */

var waitingDialog = waitingDialog || (function ($) {
    'use strict';

    // Creating modal dialog's DOM
    var $dialog = $(
        '<div class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true" style="padding-top:15%; overflow-y:visible;">' +
//        '<div class="modal-dialog modal-m viewport-centred">' +
//        '<div class="modal-content">' +
/*        '<div class="modal-header"><h3 style="margin:0;"></h3></div>' +
        '<div class="modal-body">' +*/
'<div class="viewport-centred overlay-white">'+
        //'<div class="progress progress-striped active" style="margin-bottom:0;"><div class="progress-bar" style="width: 100%"></div></div>' +

        //'<i class="fa fa-spinner fa-spin fa-3x fa-fw"></i>'+
        '<span class="overlay-text">Loading...</span><br/><br/>'+
        '<i class="fa fa-circle-o-notch fa-spin fa-3x fa-fw"></i>'+
/*
        '<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i>'+

        '<i class="fa fa-cog fa-spin fa-3x fa-fw"></i>'+

        '<i class="fa fa-spinner fa-pulse fa-3x fa-fw"></i>'+

        '<span class="sr-only">Loading...</span>'+
        '<div class="loader_multi"></div>'+*/
/*
        '</div>' +
        '</div>'+
*/
        '</div>'+

        '</div>'
    );

    return {
        /**
         * Opens our dialog
         * @param message Custom message
         * @param options Custom options:
         * 				  options.dialogSize - bootstrap postfix for dialog size, e.g. "sm", "m";
         * 				  options.progressType - bootstrap postfix for progress bar type, e.g. "success", "warning".
         */
        show: function (message, options) {
            // Assigning defaults
            if (typeof options === 'undefined') {
                options = {};
            }
            if (typeof message === 'undefined') {
                message = 'Loading...';
            }
            var settings = $.extend({
                dialogSize: 'm',
                progressType: '',
                onHide: null // This callback runs after the dialog was hidden
            }, options);

            // Configuring dialog
            $dialog.find('.modal-dialog').attr('class', 'modal-dialog').addClass('modal-' + settings.dialogSize);
            $dialog.find('.progress-bar').attr('class', 'progress-bar');
            if (settings.progressType) {
                $dialog.find('.progress-bar').addClass('progress-bar-' + settings.progressType);
            }
            $dialog.find('overlay-text').text(message);
            // Adding callbacks
            if (typeof settings.onHide === 'function') {
                $dialog.off('hidden.bs.modal').on('hidden.bs.modal', function (e) {
                    settings.onHide.call($dialog);
                });
            }
            // Opening dialog
            $dialog.modal();
        },
        /**
         * Closes dialog
         */
        hide: function () {
            $dialog.modal('hide');
        }
    };

})(jQuery);

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