/**
 * Module for displaying "Waiting for..." dialog using Bootstrap
 *
 * @author Eugene Maslovich <ehpc@em42.ru>
 */

var progressDialog = progressDialog || (function ($) {
    'use strict';

    // Creating modal dialog's DOM
    var $dialog = $(
        '<div class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true" style="padding-top:15%; overflow-y:visible;">' +
        '<div class="modal-dialog modal-m">' +
        '<div class="modal-content">' +
        '<div class="modal-header"><h3 style="margin:0;"></h3></div>' +
        '<div class="modal-body">' +
        '<div class="progress progress-striped active" style="margin-bottom:0;"><div class="progress-bar" style="width: 100%"></div></div>' +
        '</div>' +
        '</div></div></div>');

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
                message = 'Loading';
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
            $dialog.find('h3').text(message);
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

var confirm2BtnDialog = confirm2BtnDialog || (function ($) {
    'use strict';

    // Creating modal dialog's DOM
    var $dialog = $(
        '<div class="modal fade confirm2BtnDialog" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true" style="padding-top:15%; overflow-y:visible;">' +
            '<div class="modal-dialog modal-m">' +
                '<div class="modal-content">' +
                    '<div class="modal-header">'+
                        '<h5 class="modal-title">Title</h5>' +
                    '</div>'+
                    '<div class="modal-body">' +
                        '<p class="modal-message">Give your message here!</p>' +
                    '</div>' +
                    '<div class="modal-footer">'+
                        '<button id="modal-btn-left" type="button" class="btn btn-primary" data-dismiss="modal" >Left</button>'+
                        '<button id="modal-btn-right" type="button" class="btn btn-secondary" data-dismiss="modal" >Right</button>'+
                    '</div>'+
                '</div>'+
            '</div>'+
        '</div>');

    return {
        /**
         * Opens our dialog
         * @param message Custom message
         * @param options Custom options:
         * 				  options.dialogSize - bootstrap postfix for dialog size, e.g. "sm", "m";
         * 				  options.progressType - bootstrap postfix for progress bar type, e.g. "success", "warning".
         */
        show: function (options) {
            // Assigning defaults
            if (typeof options === 'undefined') {
                options = {};
            }

            var defaultOptions = {
                dialogSize: 'm',
                title: 'Title',
                message: 'Give your message here!',
                leftBtnLabel:'Left',
                rightBtnLabel: 'Right',
                leftBtnFunc: null,
                rightBtnFunc: null,
                onHide: null // This callback runs after the dialog was hidden
            }

            var settings = $.extend(defaultOptions, options);

            // Configuring dialog
            $dialog.find('.modal-dialog').attr('class', 'modal-dialog').addClass('modal-' + settings.dialogSize);

            $dialog.find('.modal-title').text(settings.title);
            $dialog.find('.modal-message').text(settings.message);
            $dialog.find('#modal-btn-left').html(settings.leftBtnLabel);
            $dialog.find('#modal-btn-right').html(settings.rightBtnLabel);

            $dialog.find('#modal-btn-left').off('click').click(settings.leftBtnFunc);
            $dialog.find('#modal-btn-right').off('click').click(settings.rightBtnFunc);


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

/**
 * Module for displaying "Waiting for..." dialog using Bootstrap
 *
 * @author Eugene Maslovich <ehpc@em42.ru>
 */

var waitingDialog =  (function ($) {
    'use strict';

    // Creating modal dialog's DOM
    var $dialog = $(
        '<div class="modal waitingDialog" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true" style="padding-top:15%; overflow-y:visible;">' +
            '<div class="modal-dialog modal-m viewport-centred">' +
                '<div class="modal-content" style="border:0px;background-color: inherit;text-align: center">' +
                    /*        '<div class="modal-header"><h3 style="margin:0;"></h3></div>'*/
                        '<div class="modal-body viewport-centred">' +
                            '<div class="overlay-white">'+
                            //'<div class="progress progress-striped active" style="margin-bottom:0;"><div class="progress-bar" style="width: 100%"></div></div>' +

                            '<span class="overlay-text">Loading...</span><br/><br/>'+
                            /*'<i class="fa fa-spinner fa-spin fa-4x fa-fw"></i>'+*/
                            /*'<i class="fa fa-circle-o-notch fa-spin fa-4x fa-fw"></i>'+*/

                            /*'<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i>'+*/

                            /*'<i class="fa fa-cog fa-spin fa-3x fa-fw"></i>'+*/

                            /*'<i class="fa fa-spinner fa-pulse fa-3x fa-fw"></i>'+*/

                            /*'<span class="sr-only">Loading...</span>'+*/
                            '<div class="loader_color"></div>'+
                        '</div>' +
                    '</div>' +
                '</div>'+
            '</div>'+
        '</div>'
    );

    return {
        dialog: $dialog,
        startTime: null,
        minLiveTime:5,   // 3s
        visible: false,
        /**
         * Opens our dialog
         * @param message Custom message
         * @param options Custom options:
         * 				  options.dialogSize - bootstrap postfix for dialog size, e.g. "sm", "m";
         * 				  options.progressType - bootstrap postfix for progress bar type, e.g. "success", "warning".
         */
        show: function (message, options) {
            var dfd = jQuery.Deferred();
            // Assigning defaults
            if (typeof options === 'undefined') {
                options = {};
            }
            if (typeof message === 'undefined') {
                message = 'Ihre daten werden Loading...';
            }
            var settings = $.extend({
                dialogSize: 'm',
                progressType: '',
                onHide: null, // This callback runs after the dialog was hidden,
            }, options);

            // Configuring dialog
            /*            $dialog.find('.modal-dialog').attr('class', 'modal-dialog').addClass('modal-' + settings.dialogSize);
                        $dialog.find('.progress-bar').attr('class', 'progress-bar');
                        if (settings.progressType) {
                            $dialog.find('.progress-bar').addClass('progress-bar-' + settings.progressType);
                        }*/
            this.dialog.find('.overlay-text').text(message);
            // Adding callbacks
            if (typeof settings.onHide === 'function') {
                this.dialog.off('hidden.bs.modal').on('hidden.bs.modal', function (e) {
                    settings.onHide.call($dialog);
                });
            }

            // Opening dialog
            this.startTime = new Date().getTime();

            var _visible = this.visible;
            this.dialog.off('shown.bs.modal').on('shown.bs.modal', function (e) {
                _visible = true;
            });

            this.dialog.modal();

            while(!_visible){
                // wait until visible
            };

            this.visible = _visible;
            dfd.resolve();

            return dfd.promise();
        },
        /**
         * Closes dialog
         */
        hide: function () {
            var dfd = jQuery.Deferred();
            var _dialog = this.dialog;
            // the overlay should appears min for one second
            var now = new Date().getTime();
            var diff = now - this.startTime;
            while(diff/1000 < this.minLiveTime ) {
                now = new Date().getTime();
                diff = now - this.startTime;
            }

            var _visible = this.visible;
            this.dialog.off('hidden.bs.modal').on('hidden.bs.modal', function (e) {
                _visible = false;
            });

            this.dialog.modal('hide');

            this.visible = _visible;
            dfd.resolve();

            return dfd.promise();
        }
    };

})(jQuery);

/**** Overlay **/

var overlay =  overlay || (function ($) {

    'use strict';

    // Creating modal dialog's DOM
    var $dialog = $(
        '<div class="modal waitingDialog" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true" style="padding-top:15%; overflow-y:visible;"></div>'
    );

    return {
        dialog: $dialog,
        startTime: null,
        minLiveTime:3,   // 3s
        visible: false,

        showContent: function (content) {
            var dfd = jQuery.Deferred();
            if(this.visible) {
                this.dialog.html(content);

                // set focus when ching content
                if(this.dialog.find('#modal-btn-left').length > 0)
                    this.dialog.find('#modal-btn-left').focus();
                else
                    this.dialog.find('#modal-btn-right').focus();

                dfd.resolve();

                return dfd.promise();
            }

            // Opening dialog
            this.startTime = new Date().getTime();

            var _this = this;
            this.dialog.off('shown.bs.modal').on('shown.bs.modal', function (e) {
                _this.visible = true;
                if(_this.dialog.find('#modal-btn-left').length > 0)
                    _this.dialog.find('#modal-btn-left').focus();
                else
                    _this.dialog.find('#modal-btn-right').focus();
            });

            this.dialog.off('hidden.bs.modal').on('hidden.bs.modal', function (e) {
                _this.visible = false;
            });

            this.dialog.html(content);
            // show the dialog
            this.dialog.modal();


            while(!this.visible){
                // wait until visible
            }

            dfd.resolve();

            return dfd.promise();
        },

        /**
         * Opens our dialog
         * @param message Custom message
         * @param options Custom options:
         * 				  options.dialogSize - bootstrap postfix for dialog size, e.g. "sm", "m";
         * 				  options.progressType - bootstrap postfix for progress bar type, e.g. "success", "warning".
         */
        show: function (message) {
            return this.showSpinner(message);
        },

        showSpinner: function (message) {

            this.startTime = new Date().getTime();

            if (typeof message === 'undefined') {
                message = 'Loading...';
            }

            var spinnerContent =
                '<div class="modal-dialog modal-m viewport-centred">' +
                '<div class="modal-content" style="border: 0px;background-color: inherit">' +
                '<div class="modal-header" style="border: 0px"><span style="color:white">'+ message +'</span></div>'+
                '<div class="modal-body">' +
                '<div class="overlay-white">'+

                /*'<i class="fa fa-spinner fa-spin fa-4x fa-fw"></i>'+*/
                '<i class="fa fa-circle-o-notch fa-spin fa-4x fa-fw"></i>'+

                /*'<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i>'+*/

                /*'<i class="fa fa-cog fa-spin fa-3x fa-fw"></i>'+*/

                /*'<i class="fa fa-spinner fa-pulse fa-3x fa-fw"></i>'+*/

                /*'<span class="sr-only">Loading...</span>'+*/
                /*'<div class="loader_color"></div>'+*/
                '</div>' +
                '</div>' +
                '</div>'+
                '</div>';

            return this.showContent(spinnerContent);
        },

        showConfirm2BtnDialog: function(options){
            // Assigning defaults
            if (typeof options === 'undefined') {
                options = {};
            }

            var defaultOptions = {
                dialogSize: 'm',
                title: 'Title',
                message: 'Give your message here!',
                leftBtnLabel:'Left',
                rightBtnLabel: 'Right',
                leftBtnFuncName: null,
                rightBtnFuncName: null,
                showOnlyRightBtn: false,
                rightBtnDismiss: true,
                leftBtnDismiss: true
            }

            var settings = $.extend(defaultOptions, options);

            var dialogContent = '<div class="modal-dialog modal-m modal-'+settings.dialogSize+'">' +
                '<div class="modal-content">' +
                '<div class="modal-header popup-header">'+
                '<div class="modal-title">'+settings.title+'</div>' +
                '</div>'+
                '<div class="modal-body">' +
                '<p class="modal-message">'+settings.message+'</p>' +
                '</div>' +
                '<div class="modal-footer">';

            if(!settings.showOnlyRightBtn) {
                if(settings.leftBtnDismiss)
                    dialogContent += '<button id="modal-btn-left" type="button" class="btn btn-primary" data-dismiss="modal" onclick="'+settings.leftBtnFuncName+'">' + settings.leftBtnLabel+'</button>';
                else
                    dialogContent += '<button id="modal-btn-left" type="button" class="btn btn-primary" onclick="'+settings.leftBtnFuncName+'" >' + settings.leftBtnLabel+'</button>';
            }

            var btnClass = "btn btn-secondary";
            if(settings.showOnlyRightBtn) btnClass = "btn btn-primary";

            if(settings.rightBtnDismiss)
                dialogContent +=
                    '<button id="modal-btn-right" type="button" class="'+btnClass+'" data-dismiss="modal" onclick="'+settings.rightBtnFunc+'" >' + settings.rightBtnLabel+'</button>';
            else
                dialogContent +=
                    '<button id="modal-btn-right" type="button" class="'+btnClass+'" onclick="'+settings.rightBtnFunc+'"  > ' + settings.rightBtnLabel+'</button>';

            dialogContent +=
                '</div>'+
                '</div>'+
                '</div>';


            return this.showContent(dialogContent);
        },

        /**
         * Closes dialog
         */
        hide: function () {
            var dfd = jQuery.Deferred();
            var _dialog = this.dialog;
            // the overlay should appears min for one second
            var now = new Date().getTime();
            var diff = now - this.startTime;
            while(diff/1000 < this.minLiveTime ) {
                now = new Date().getTime();
                diff = now - this.startTime;
            }

            this.dialog.modal('hide');


            dfd.resolve();

            return dfd.promise();
        }
    };

})(jQuery);