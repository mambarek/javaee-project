
/********************** selectOneMenu ***********************************/
function initSelectOneMenu(targetWidgetId, itemIdPrefix, decorated) {
    $.widget( "custom.selectOneMenu", $.ui.selectmenu, {
        _renderItem: function( ul, item ) {
            var _id = '#' + itemIdPrefix + item.index;
            //console.info("#+#+ _d: " + _id);
            var _li = $(_id);

            var li = $("<li/>");
            var div = $("<div>");
            li.append(div);
            div.html(_li.html());
            return li.appendTo( ul );
        },

        _renderButtonItem: function( item ) {
            var buttonItem = $( "<span>", {
                "class": "ui-selectmenu-text"
            });

            if(decorated){
                var _id = '#' + itemIdPrefix + item.index;
                var _li = $(_id);
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



        _source: function( request, response ) { console.info("-source call");
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