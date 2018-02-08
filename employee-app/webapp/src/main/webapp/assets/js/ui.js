$( function() {
    /*define IconSelectManu class*/
    $.widget( "custom.iconselectmenu", $.ui.selectmenu, {
        _renderItem: function( ul, item ) {
            var li = $("<li/>");
            var wrapper = $("<div/>");

            if ( item.disabled ) {
                li.addClass( "ui-state-disabled" );
            }

            var labelSpan = $('<span style="'+item.element.attr('data-label-style')+'"/>');
            labelSpan.html(item.label);

            /*
                                                            if(item.element.attr( "data-label-style" )){
                                                                labelSpan.style = {item.element.attr( "data-label-style" )};
                                                            }
            */

            var iconSpan = $("<span/>");

            if(item.element.attr( "data-style" )){
                iconSpan.attr('style',item.element.attr( "data-style" ));
            }

            if( item.element.attr( "data-class" )){
                iconSpan.addClass("ui-icon");
                iconSpan.addClass( item.element.attr( "data-class" ))
            }

            iconSpan.appendTo( wrapper );
            labelSpan.appendTo( wrapper );


            return li.append( wrapper ).appendTo( ul );
        }
    });
} );


function testit(id){
    //var encodedId = "#" + encodeId("#{cc.clientId}:editableText");
    //console.info("-- encodedId: " + encodedId);
    $(id).iconselectmenu().iconselectmenu( "menuWidget").addClass( "ui-menu-icons avatar" );

}