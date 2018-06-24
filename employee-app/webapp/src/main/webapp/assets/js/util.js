function format(source, params) {
    //var regex = new RegExp("{-?[0-9]+}", "g");
    $.each(params,function (i, n) {
        source = source.replace(new RegExp("\\%" + i + "\\%", "g"), n);
    })
    return source;
}

function getJSON(url) {
    return new Promise(function(resolve, reject) {
        var xhr = new XMLHttpRequest();
        xhr.open('get', url, true);
        xhr.responseType = 'json';
        xhr.onload = function() {
            var status = xhr.status;
            if (status == 200) {
                resolve(xhr.response);
            } else {
                reject(status);
            }
        };
        xhr.send();
    });
}

function xhrGet(url) {

    var xhr, results, def;

    def = $.Deferred();
    xhr = new XMLHttpRequest();
    xhr.open('GET', url, true);

    xhr.onload = function (e) {

        if (this.status === 200) {

            results = JSON.parse(this.responseText);
            def.resolve(results);
        }
    };

    xhr.onerror = function (e) {
        def.reject(e);
    };

    xhr.send();

    return def;
}

function xhrPost(url) {

    var xhr, results, def;

    def = $.Deferred();
    xhr = new XMLHttpRequest();
    xhr.open('POST', url, true);
    xhr.setRequestHeader("Content-type", "application/json; charset=utf-8");

    xhr.onload = function (e) {

        if (this.status === 200) {

            results = JSON.parse(this.responseText);
            def.resolve(results);
        }
    };

    xhr.onerror = function (e) {
        def.reject(e);
    };

    xhr.send();

    return def;
}