function format(source, params) {
    //var regex = new RegExp("{-?[0-9]+}", "g");
    $.each(params,function (i, n) {
        source = source.replace(new RegExp("\\%" + i + "\\%", "g"), n);
    })
    return source;
}