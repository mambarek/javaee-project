$(document).ready(function(){
    //initSessionTimeoutTimer();
    // reset the session timer after ajax call. this is a user activity
    jsf.ajax.addOnEvent(function (data) {
        if(data.status == 'success'){
            //console.info("-- jsf.ajax.addOnEvent() call -- so resetSessionTimeOutTimer()");
            resetSessionTimeOutTimer();
        }
    })
});

var employee_i18n = null;
var sessionTimeOutSec = 1800;
var sessionTimeoutCounterInterval = null;
var sessionTimeoutInterval = null;
var counter = null;

function i18nInit(i18n){
    employee_i18n = i18n;
}

function setSessionTimeOutSec(sec){
    sessionTimeOutSec = sec;
}

function setDocumentTitel(title){
    document.title = title;
}

function extendSession() {
    //reload page
    //location.reload();
    // extend the session sending an empty ajax
    console.info("--extendSession() call",$('.refreshSession'));
    $('.refreshSession').click();
    //window.location.replace(window.location.href);
    //initSessionTimeOutTimer();
}

function resetSession() {
    //reload page
    location.reload();
    //window.location.replace(window.location.href);
    initSessionTimeOutTimer();
}

function initSessionTimeOutTimer(){

    resetSessionTimeOutTimer();

    // restart session timer, not on login mask
/*
    if($("#sub-navi").children().length > 0){
        resetSessionTimeOutTimer();
    }
*/
}

function resetSessionTimeOutTimer(){

    if (sessionTimeoutCounterInterval){
        clearTimeout(sessionTimeoutCounterInterval);
    }

    if (sessionTimeoutInterval){
        clearTimeout(sessionTimeoutInterval);
    }

    if(counter) clearInterval(counter);

    var timeout = null;
    if(sessionTimeOutSec)
     timeout = sessionTimeOutSec * 1000 - 30000; // 3 sec puffer???

    if(!timeout) return;

    sessionTimeoutCounterInterval = setTimeout('showSessionTimeOutCounter()', timeout - 10000); // - 10 sek for countdown
    // 10 sec countdown
    sessionTimeoutInterval = setTimeout('showSessionTimeOutInfo()', timeout);
}

function showSessionTimeOutCounter(){
    var count = 10;
    var s = '<span style="font-weight: bold" id="timer">'+count+'</span>';
    var options = {
        title: employee_i18n['employee.overlay.session.timout.timer.titel'],
        message: format(employee_i18n['employee.overlay.session.timout.timer.message'],[s]),
        showOnlyRightBtn: true,
        rightBtnLabel: employee_i18n['employee.overlay.session.timout.timer.button.text'],
        rightBtnFuncName: "extendSession"
    };

    overlay.showConfirm2BtnDialog(options);

    counter = setInterval(function (args) {
        count--;
        document.getElementById("timer").innerHTML = count;
    }, 1000);
}

function showSessionTimeOutInfo(){
    if(counter) clearInterval(counter);
    var options = {
        title: employee_i18n['employee.overlay.session.timout.info.titel'],
        message: employee_i18n['employee.overlay.session.timout.info.message'],
        showOnlyRightBtn: true,
        rightBtnLabel: employee_i18n['employee.overlay.session.timout.info.button.text'],
        rightBtnFuncName: "resetSession"
    };

    overlay.showConfirm2BtnDialog(options);
}



/*-----------------------------------------------------------------------------*/



function makeAjax(element, event, execute, render, oneventFunc, onerrorFunc){

    event.preventDefault();
    event.stopPropagation();
    //alert("makeAjax call");
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

function validateInputStyle3(component){

    var valid = component.attr("data-valid");
    var inputContainer = component.closest(".inputContainer");
    var disabled = component.attr("disabled");
   // console.info("validateInputStyle component", component, " valid: " + valid);

    // reset highlighting
    //component.parent().removeClass('has-danger has-success');
    inputContainer.removeClass('has-danger has-success');

    var target = component;
/*    var isSelect = component.is("select");
    var isRadio = target.attr("data-isRadio");
    if(isRadio == "true"){
        component.find('radio').forEach(function(radio){
            var radioValid = radio.attr("data-valid");
            if(radioValid ==)
        })
    }

    if(component.attr('type') == "radio" ) {
        target = component.closest(".input-group");
        // add border
        target.addClass("form-control");
    }*/

    // dropdown: HTML select are display none
    // the jqueryui select menu generates a span with
    // select content. so highlight the span instead of
    // the select
    if(component.is("select")) {
        target = component.siblings('span');
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

function validateInput(data){
    if(data.status == "success"){
        var input = $('#' + encodeId(data.source.id));
        validateInputStyle(input, true);
    }
}

function validateInputStyle(component, hightlight_valid_input){

    var valid = component.attr("data-valid");
    var inputContainer = component.closest(".inputContainer");
    var disabled = component.attr("disabled");
    // console.info("validateInputStyle component", component, " valid: " + valid);

    var target = component;

    var type = component.attr("data-component-type");
    if(type === "comboBox"){
        target = component.siblings('.custom-combobox');
    }
    // dropdown: HTML select are display none
    // the jqueryui select menu generates a span with
    // select content. so highlight the span instead of
    // the select
    if(component.is("select")) {
        target = component.siblings('span');
        if(target.length == 0) {
            target = component.siblings('.custom-combobox');

        }
        else
        // add border
        target.removeClass("form-control").addClass("form-control");
    }

    target.removeClass("is-valid is-invalid");

    // when disabled so no highlighting
    if(disabled && disabled != false) return;

    var rowForm = component.closest("[data-component-name='rowForm']");
    var countrySelect = component.closest("[data-component-name='countrySelect']");

    var isComposedInput = component.closest("[data-component-name='composedInput-input']").length > 0;
    var composedInput = component.closest("[data-component-name='composedInput']");
    var showErrorContainer = valid == "false";
    if(composedInput.length > 0) {
        showErrorContainer = composedInput.attr('data-valid') == "false";
        composedInput.find('input').each(function(){
            var ch_valid = $(this).attr("data-valid");
            if (ch_valid == "false") {
                $(this).removeClass("is-invalid").addClass('is-invalid');
                //target.siblings('.invalid-feedback').show();
                if($(this).attr('type') == "radio") {
                    // all radios should have the same color valid/invalid
                    $('input[name="'+encodeId($(this).attr('name'))+'"').removeClass('is-invalid').addClass('is-invalid');
                }
            }else {
                if(hightlight_valid_input) {
                    $(this).removeClass("is-valid").addClass('is-valid');
                    //target.siblings('.invalid-feedback').hide();
                    if ($(this).attr('type') == "radio") {
                        // all radios should have the same color valid/invalid
                        $('input[name="' + encodeId($(this).attr('name')) + '"').removeClass('is-invalid').addClass('is-valid');
                    }
                }
            }
        })
    }

    if (valid == "false") {
        target.removeClass('is-valid').addClass('is-invalid');
        //target.siblings('.invalid-feedback').show();
        if(component.attr('type') == "radio") {
            // all radios should have the same color valid/invalid
            $('input[name="'+encodeId(component.attr('name'))+'"').removeClass('is-valid').addClass('is-invalid');
        }
    }else {
        if(hightlight_valid_input) {
            target.removeClass('is-invalid').addClass('is-valid');
            //target.siblings('.invalid-feedback').hide();
            if (component.attr('type') == "radio") {
                // all radios should have the same color valid/invalid
                $('input[name="' + encodeId(component.attr('name')) + '"').removeClass('is-invalid').addClass('is-valid');
            }
        }
    }

    if (showErrorContainer) {

        if(countrySelect.length > 0)
            countrySelect.find('.invalid-feedback').show();

        if(rowForm.length > 0)
            inputContainer.find('.invalid-feedback').show();

        if(type === "comboBox")
            inputContainer.find('.invalid-feedback').show();

        if(composedInput.length > 0)
            composedInput.find(".error-container.collapse").show();
    }else {

        if(countrySelect.length > 0)
            countrySelect.find('.invalid-feedback').hide();

        if(rowForm.length > 0)
            inputContainer.find('.invalid-feedback').hide();

        if(type === "comboBox")
            inputContainer.find('.invalid-feedback').hide();

        if(composedInput.length > 0)
            composedInput.find(".error-container.collapse").hide();
    }
}

function validateElementWithId(selector){
    //var element = $("#" + id.replace(new RegExp(':', 'g'),"\\:"));
    //var allInputFields = form.filter('input[type=text], select');
    $(selector).find("input[type=text], input[type=radio], select, textarea")
        .each(function(){validateInputStyle($(this))});
}

function validateElement(elem){
    var element = $("#" + elem.id.replace(new RegExp(':', 'g'),"\\:"));
    //var allInputFields = form.filter('input[type=text], select');
    element.find("input[type=text], input[type=radio], select, textarea").each(function(){validateInputStyle($(this))});
}

function validateForm(form){
    //var allInputFields = form.filter('input[type=text], select');
    //form-control
    //form.find(".form-control, input[type=radio]").each(function(){
    form.find("input[type=text], input[type=radio], select, textarea").each(function(){
        validateInputStyle($(this), false)
    });

    //form.find("radio")
    //form.addClass("was-validated");
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

function handleAjax(data) {}
function handleAjax_old(data) {
    var status = data.status;
    var componentId = encodeId(data.source.id);

    var input = $("#" + componentId);
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

            validateInputStyle(input);
            var inputContainer = input.closest(".inputContainer")[0];
            refreshAllEventListener(encodeId(inputContainer.id));

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
            break;

        case "complete":
            // This is invoked right after AJAX response is returned.
            break;

        case "success":
            // This is invoked right after successful processing of AJAX response and update of HTML DOM.
            validateForm(form);

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

                overlay.showConfirm2BtnDialog(dialogOptions);

                break;
            }
    }
}

function validateTab(data) {

    $('ul[role=tablist] a[role=tab]').each(function(index){

            var form = $($(this).attr('href'));
            // submit the form to validate it
            form.find(".tabSubmit").click();
        }
    );

}

function checkTabValidationAndConfirmSave(data) {

            var noError = true;
            // This is invoked right after successful processing of AJAX response and update of HTML DOM.
            $('ul[role=tablist] a[role=tab]').each(function(index){

                    var form = $($(this).attr('href'));
                    if(hasError(form)) {
                        noError = false;
                    }
                }
            );

            if(noError) {
                var dialogOptions = {
                    title: employee_i18n['employee.overlay.saveData'],
                    message: employee_i18n['employee.overlay.saveDataQuestion'],
                    leftBtnLabel: employee_i18n['employee.overlay.save'],
                    rightBtnLabel: employee_i18n['employee.overlay.cancel'],
                    leftBtnFuncName: "clickSave",
                    leftBtnDismiss: false,
                    rightBtnFunc: null
                };

                overlay.showConfirm2BtnDialog(dialogOptions);
            }


}

function clickTabSave() {
    //console.info('##### clickSave call');
    $('.saveConfirmed').click();
}

function refreshAllEventListener(rootId){
    if(isIE()) {
        refreshEventListener(rootId, "click");
        refreshEventListener(rootId, "change");
    }
}

function musterRefreshEventListener(rootId){
    var form = $('#' + rootId);
    // form.find("input[type=text], input[type=radio], select").each(function(){
    form.find('[onclick]').each(function(){
        if(this.removeEventListeners)
            this.removeEventListeners('click',this.onclick, true);

        var onclickFunc = this.getAttribute('onclick');

        if(onclickFunc && onclickFunc != null && !this.onclick) {
            console.info("## refreshEventListener " + this.id + " onclickFunc[",onclickFunc,"] this.onclick[",this.onclick,"]" );
            this.onclick = new Function(onclickFunc);
            this.addEventListener('click', this.onclick);
        }
    });
}

/**
 * f:ajax generates an onclick in the submit button. by clicking the first time the form is submitted and the form is rerendered after Ajax is finish.
 * the onclick is in new generated html but in IE there is no Eventlistener registred for click you can see this analysing the button properties in IE DevTools.
 * so the solution is to add manually the Eventlistener after an ajax call for new rendered elments. ist not easy but this works.
 * i add manually every click function for new rendered elements. It works. now i have no trouble with reloading page
 * **/
function refreshEventListener(rootId, eventName){
    var form = $('#' + rootId);
    var oneventName = 'on' + eventName;

    // form.find("input[type=text], input[type=radio], select").each(function(){
    form.find('['+oneventName+']').each(function(){
        if(this.removeEventListeners)
            this.removeEventListeners(eventName,this[oneventName], true);

        var eventFunc = this.getAttribute(oneventName);

        if(eventFunc && !this[oneventName]) {
            console.info("## refreshEventListener " + this.id + " eventFunc[",eventFunc,"] this[oneventName][",this[oneventName],"]" );
            this[oneventName] = new Function(eventFunc);
            this.addEventListener(eventName, this[oneventName]);
        }

    });
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

function saveSuccess(){
    //var currentPage = $('#grid').getGridParam('page');
    //window.location.href = "jqGrid-table.xhtml";
    $('.saveSuccess').click();
}

function deleteSuccess(){
    //var currentPage = $('#grid').getGridParam('page');
    //window.location.href = "jqGrid-table.xhtml";
    $('.deleteSuccess').click();
}

function handleAjaxSaveEvent(data){
    var status = data.status;
    //var encodedId = encodeId(data.source.id);
    //var form = $("#" + encodedId).closest("form");
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
                    rightBtnFuncName: "saveSuccess", // redirect explicite to list
                    rightBtnLabel: employee_i18n['employee.overlay.ok']
                };
            }
            overlay.showConfirm2BtnDialog(options);
            break;
    }
}



function handleAjaxDeleteEvent(data){
    var status = data.status;
/*    var encodedId = encodeId(data.source.id);
    var form = $("#" + encodedId).closest("form");*/
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
                    rightBtnFuncName: "deleteSuccess", // redirect explicite to list
                    rightBtnLabel: employee_i18n['employee.overlay.ok']
                }
            }

            overlay.showConfirm2BtnDialog(options);
            break;
    }
}

function hasError(element){
    var res = false;
    element.find(".form-control, .custom-control-input").each(function(){
        var target = $(this);
        var valid = target.attr("data-valid");
        if($(this)[0].type == "radio") {
            target = $(this).closest(".input-group");
        }
        var error = target.hasClass('form-control-danger');
        if(valid == "false" || error){
        //if(valid == "false"){
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
    //console.info('##### clickSave call');
    $('.saveConfirmed').click();
}

function clickDelete() {
    $('.deleteConfirmed').click();
}

/**
 * detect IE
 * returns version of IE or false, if browser is not Internet Explorer
 */
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

function jqui_select() {

}