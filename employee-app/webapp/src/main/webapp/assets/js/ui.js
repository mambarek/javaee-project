/********************** popover ***********************************/
function initPopover(selector, yourOptions){
    // Assigning defaults
    if (typeof yourOptions === 'undefined') {
        yourOptions = {};
    }

    var options = $.extend({
        showOnMobile: false,
        title: "Information Card",
        content: "Set your content",
        html: true,
        animation: true,
        container: 'body',
        trigger: 'hover ', //click | hover | focus | manual
        //fallbackPlacement: 'flip',
        placement: 'right',
        boundary: 'viewport'
    }, yourOptions);

    // mobile
    if(!options.showOnMobile && window.innerWidth <= 800)
        return;

    $(selector).popover(options);
}
/********************** selectOneMenu ***********************************/
function initSelectOneMenu(targetWidgetId, itemIdPrefix, decorated, showPopover) {
    $.widget( "custom.selectOneMenu", $.ui.selectmenu, {
        _renderItem: function( ul, item ) {
            var _id = '#' + itemIdPrefix + item.index;
            //console.info("#+#+ _d: " + _id);
            var _li = $(_id);

            var content = item.label;
            if(_li.find('.item-details').length > 0){
                content = _li.find('.item-details').html();
            }

            var li = $("<li/>");
            var div = $("<div>");
            //div.attr('title', item.label);
            //div.tooltip( {"position": {my: "right+150 center", at: "right center" } });

            // popover not for mobile
            if(showPopover && window.innerWidth > 800){
                div.popover({
                    title: item.label,
                    //content: _li.find('.item-details').first().html(),
                    content: content,
                    html: true,
                    animation: true,
                    container: 'body',
                    trigger: 'hover ', //click | hover | focus | manual
                    //fallbackPlacement: 'flip',
                    placement: 'right',
                    boundary: 'viewport'
                });
            }

            li.append(div);
            div.html(_li.html());
            //div.tooltip({placement:"left"});
            return li.appendTo( ul );
        },

        _renderButtonItem: function( item ) {
            var buttonItem = $( "<div>", {
                "class": "ui-selectmenu-text"
            });

            var _id = '#' + itemIdPrefix + item.index;
            //console.info("#+#+ _d: " + _id);
            var _li = $(_id);

            var content = item.label;
            if(_li.find('.item-details').length > 0){
                content = _li.find('.item-details').html();
            }

            if(showPopover){
                buttonItem.popover({
                    title: "Information Card",
                    content: content,
                    html: true,
                    animation: true,
                    container: 'body',
                    trigger: 'hover ', //click | hover | focus | manual
                    //fallbackPlacement: 'flip',
                    placement: 'bottom',
                    boundary: 'viewport'
                })
            }

            if(decorated){
                // set the span content to the selected item content
                buttonItem.html(_li.html());
            }
            else
                buttonItem.text(item.label);
            //this._setText( buttonItem, _li.html() );
            return buttonItem;
        }

    });

    //var encodedId = "#" + encodeId("#{cc.clientId}:editableText");
    //console.info("**&#45;&#45; encodedId: " + encodedId);
    // apply the menu
    $("#" + targetWidgetId).selectOneMenu({
        // bei collision at the bottom of browser show menu on the opposite (top)
        position:{collision: "flip"},
        // trigger onchange for select and fire ajax
        change: function( event, ui ) {$("#" + targetWidgetId).trigger( "change" );}
    }).selectOneMenu( "menuWidget" )
        .addClass("select-overflow"); // set max height for scrolling
}
/********************** Combobox ***********************************/
function initComboBox(targetWidgetId, itemIdPrefix){
    $.widget( "custom.combobox", {
        _create: function() {
            this.wrapper = $( "<div>" )
                .addClass( "custom-combobox" )
                .insertAfter( this.element );

            this.element.hide();
            this._createAutocomplete();
            //this._createDisplay();
            this._createShowAllButton();

        },

        _createDisplay: function(){
            var _this = this;
            this.display = $("<span>").hide();
            this.display.appendTo( this.wrapper )
                .addClass( "custom-combobox-input ui-widget ui-widget-content ui-state-default ui-corner-left" )
                .autocomplete({
                    delay: 0,
                    minLength: 0,
                    source: $.proxy( this, "_source" )

                });
            this.display.on("click",function(){
                _this.display.hide();
                _this.input.show();
                _this.input.focus();
            })
        },

        _createAutocomplete: function() {
            var selected = this.element.children( ":selected" ),
                value = selected.val() ? selected.text() : "";
            //console.info("## selected:",selected);
            this.input = $( "<input>" )
                .appendTo( this.wrapper )
                .val( value )
                .attr( "title", "" )
                .addClass( "custom-combobox-input ui-widget ui-widget-content ui-state-default ui-corner-left" )
                .autocomplete({
                    delay: 0,
                    minLength: 0,
                    source: $.proxy( this, "_source" ),
                    // bei collision at the bottom of browser show menu on the oposite (top)
                    position:{collision: "flip"}

                });

            this.input.data("uiAutocomplete")._renderItem = function( ul, item ) {
                var _id = '#' + itemIdPrefix + item.option.index;
                //console.info("#+#+ _d: " + _id, item);
                var _li = $(_id);

                var li = $("<li/>");
                var div = $("<div>");
                li.append(div);
                div.html(_li.html());
                return li.appendTo( ul );
            }

            this._on( this.input, {
                autocompleteselect: function( event, ui ) {
                    ui.item.option.selected = true;
                    this._trigger( "select", event, {
                        item: ui.item.option
                    });

                    this.element.trigger("change");
                    var _id = '#' + itemIdPrefix + ui.item.option.index;
                    //console.info("#+#+ _d: " + _id, ui.item);
                    var _li = $(_id);

                    /*this.display.html(_li.html());
                    this.display.show();
                    this.input.hide();*/

                },

                autocompletechange: "_removeIfInvalid"
            });
        },

        _createShowAllButton: function() {
            var input = this.input,
                wasOpen = false;

            $( "<a>" )
                .attr( "tabIndex", -1 )
                .attr( "title", "Show All Items" )
                //.tooltip()
                .appendTo( this.wrapper )
                .button({
                    icons: {
                        primary: "ui-icon-triangle-1-s"
                    },
                    text: false
                })
                .removeClass( "ui-corner-all" )
                .addClass( "ui-corner-right ui-button-icon ui-combobox-button custom-combobox-button" )
                .on( "mousedown", function() {
                    wasOpen = input.autocomplete( "widget" ).is( ":visible" );
                })
                .on( "click", function() {
                    input.trigger( "focus" );

                    // Close if already visible
                    if ( wasOpen ) {
                        return;
                    }

                    // Pass empty string as value to search for, displaying all results
                    input.autocomplete( "search", "" );
                });
        },



        _source: function( request, response ) {
            var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
            response( this.element.children( "option" ).map(function() {
                var text = $( this ).text();
                if ( this.value && ( !request.term || matcher.test(text) ) )
                return {
                    label: text,
                    value: text,
                    option: this
                };
            }) );
        },

        _removeIfInvalid: function( event, ui ) {

            // Selected an item, nothing to do
            if ( ui.item ) {
                return;
            }

            // Search for a match (case-insensitive)
            var value = this.input.val(),
                valueLowerCase = value.toLowerCase(),
                valid = false;
            this.element.children( "option" ).each(function() {
                if ( $( this ).text().toLowerCase() === valueLowerCase ) {
                    this.selected = valid = true;
                    return false;
                }
            });

            // Found a match, nothing to do
            if ( valid ) {
                return;
            }

            // Remove invalid value
            this.input
                .val( "" )
                .attr( "title", value + " didn't match any item" )
                //.tooltip( "open" );
            this.element.val( "" );
/*            this._delay(function() {
                this.input.tooltip( "close" ).attr( "title", "" );
            }, 2500 );*/
            this.input.autocomplete( "instance" ).term = "";
        },

        _destroy: function() {
            this.wrapper.remove();
            this.element.show();
        }});

    $( "#" + targetWidgetId).combobox({

    });
}

function checkTabValidation(selector){
    $(selector).find('a').on('click', function (e) {
        // stop event propagation
        e.preventDefault();
        e.stopPropagation();

        // reset selection
        $(selector).find('a').attr('data-last-clicked',false);
        // set last clicked tab
        $(this).attr('data-last-clicked',true);
        // validate active tab
        var selectedTab = $(selector).find('a[aria-selected=true]');
        var form = $(selectedTab.attr('href'));
        // submit the form to validate it on the server side using jsf
        form.find("input[type=submit]").click();
    })
}

function checkValidationAndSelectTab(data){
    var status = data.status;
    checkValidation(data);
    if(status == "success"){

        var selectedTab = $('ul[role=tablist] a[aria-selected=true]');
        var lastClicked = $('ul[role=tablist] a[data-last-clicked=true]');
        var form = $(selectedTab.attr('href'));

        if(hasError(form)) {
            $(selectedTab).tab('show');
        }
        else {
            $(lastClicked).tab('show');
            //form = $(lastClicked.attr('href'));
            // submit the form to validate it on the server side using jsf
            //form.find("input[type=submit]").click();
        }
    }
}

function hightlightMustFields(selector){
    $('[data-required="true"]').each(function(){

        if(this.type == "radio"){
            if($('[name="'+this.name+'"]').filter('[checked]').length == 0){
                $('[name="'+this.name+'"]').each(function(){
                    $(this).removeClass("is-invalid").addClass('is-invalid');
                })
            }

            return;
        }

        // dropdown: HTML select are display none
        // the jqueryui select menu generates a span with
        // select content. so highlight the span instead of
        // the select
        if($(this).is("select")) {
            var target = $(this).siblings('span');
            // add border
            if(!this.value)
                target.removeClass("form-control is-invalid").addClass("form-control is-invalid");

            return;
        }

        if(!this.value){
            $(this).removeClass("is-invalid").addClass('is-invalid');
        }

    })
}