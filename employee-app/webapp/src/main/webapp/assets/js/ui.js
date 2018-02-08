$(function () {
    /*define IconSelectManu class*/
    $.widget("custom.iconselectmenu", $.ui.selectmenu, {
        _renderItem: function (ul, item) {
            var li = $("<li/>");
            var wrapper = $("<div/>");

            /*            if ( item.disabled ) {
                            li.addClass( "ui-state-disabled" );
                        }*/

            var iconSpan = $("<span/>");

            if (item.element.attr("data-icon-style")) {
                iconSpan.attr('style', item.element.attr("data-style"));
            }

            if (item.element.attr("data-icon-class")) {
                iconSpan.addClass("ui-icon");
                iconSpan.addClass(item.element.attr("data-icon-class"))
            }

            var labelSpan = $('<span/>');
            if (item.element.attr('data-label-style')) {
                labelSpan.attr('style', item.element.attr("data-label-style"));
            }

            labelSpan.html(item.label);

            iconSpan.appendTo(wrapper);
            labelSpan.appendTo(wrapper);


            return li.append(wrapper).appendTo(ul);
        }
    });

    $.widget("custom.selectonemenu", $.ui.selectmenu, {
        clientId: null,

        _renderItem: function (ul, item) {
            if (this.clientId && item) {
                console.info("+++ ClientId: " + this.cllientId);
                var _id = encodeId("j_idt35:employeeForm:j_idt82:editableText-li-" + item.index);
                console.info("#+#+ _d: " + _id);
                var _li = $(_id);
                var li = $("<li/>");
                li.innerHtml = _li.innerHTML;
                return li.appendTo(ul);
            }
            else {
                li = $("<li/>");
                var wrapper = $("<div/>");

                /*            if ( item.disabled ) {
                                li.addClass( "ui-state-disabled" );
                            }*/

                var iconSpan = $("<span/>");

                if (item.element.attr("data-icon-style")) {
                    iconSpan.attr('style', item.element.attr("data-style"));
                }

                if (item.element.attr("data-icon-class")) {
                    iconSpan.addClass("ui-icon");
                    iconSpan.addClass(item.element.attr("data-icon-class"))
                }

                var labelSpan = $('<span style="color:red;"/>');
                if (item.element.attr('data-label-style')) {
                    labelSpan.attr('style', item.element.attr("data-label-style"));
                }

                labelSpan.html(item.label);

                iconSpan.appendTo(wrapper);
                labelSpan.appendTo(wrapper);


                li.append(wrapper).appendTo(ul);
            }

            return li;
        }
    });

    $.widget("custom.selectonemenu2", $.ui.selectmenu, {
        clientId: null,

        _renderItem: function (ul, item) {
            console.info("+++ ClientId: " + this.cllientId);
            var _id = "#" + encodeId("j_idt35:employeeForm:j_idt82-li-" + item.index);
            console.info("#+#+ _d: " + _id);
            var _li = $(_id);
            var li = $("<li/>");
            li.html(_li.html());
            return li.appendTo(ul);
        }
    });
});


function testit(id) {
    //var encodedId = "#" + encodeId("#{cc.clientId}:editableText");
    //console.info("-- encodedId: " + encodedId);
    $(id).iconselectmenu().iconselectmenu("menuWidget").addClass("ui-menu-icons avatar");

}