
function editEmployee(cellvalue, options, rowObject){
    var currentPage = $('#grid').getGridParam('page');
    window.location.href = 'editor.xhtml?id='+cellvalue + "&page=" + currentPage;
}

function editButton(cellvalue, options, rowObject){

    return '<a onclick="editEmployee('+cellvalue+')" ><i class="fas fa-pencil-alt" aria-hidden="true"/></a>';
}

function yeditButton(cellvalue, options, rowObject){
    var currentPage = $('#'+ options.grid).getGridParam('page')
    return '<a onclick="editEmployee(cellvalue)" href="editor.xhtml?id='+cellvalue+'"&page="+ currentPage +" ><i class="fas fa-pencil-alt" aria-hidden="true"/></a>';
}

function xeditButton(cellvalue, options, rowObject){
    return '<a href="?id='+cellvalue+'#test" style="margin-left: 20px;" onclick="mojarra.ab(this,event,\'click\',\'@this\',\'content\');return false"><i class="fas fa-pencil-alt" aria-hidden="true"/></a>';
}

function createEmployeesGrid(selector, colModel, data, rowsPerPage, locale){
    var pagerId = "#gridPager"; // muss als param übergebne
    $(selector).jqGrid({
        regional: locale,
        datatype: "local",
        data: data,
        colModel: colModel,
        //altRows : true,
        rownumbers : false,
        colMenu : true,
        menubar: true,
        //viewrecords : true,
        hoverrows : true,
        height: '100%',
        rowNum: rowsPerPage,
        records: 100,
        caption : 'Employees',
        sortable: true,
        //altRows: true, This does not work in boostrarap
        // altclass: '....'
        pager: "#gridPager"
        // set table stripped class in table style in bootsrap

    });

    $(selector).jqGrid('navGrid',pagerId, {
        search: true, // show search button on the toolbar
        add: true,
        edit: false,
        del: false,
        refresh: true
    });


    $(selector).jqGrid('menubarAdd',  [
        {
            id : 'das',
            //cloasoncall : true,
            title : 'Sort by Category',
            click : function ( event) {
                $(selector).jqGrid('sortGrid','CategoryName');
            }
        },
        {
            divider : true,
        },
        {
            id : 'was',
            //cloasoncall : true,
            title : 'Toggle Visibility',
            click : function ( event) {
                var state = (this.p.gridstate === 'visible') ? 'hidden' : 'visible';
                $(selector).jqGrid('setGridState',state);
            }
        },
        {
            id : 'show_hide_col',
            //cloasoncall : true,
            title : 'Show/Hide column',
            click : function ( event) {
                $(selector).jqGrid('columnChooser');

            }
        }
    ]);

    // workaround set width to 100%
    var newWidth = $(selector).closest(".ui-jqgrid").parent().width();
    $(selector).jqGrid("setGridWidth", newWidth, true);

    $('td#add_grid.ui-pg-button').off('click').on('click', function(e){

            e.preventDefault();
            e.stopPropagation();
            //alert("I was clicked");
            var currentPage = $('#grid').getGridParam('page');
            window.location.href = 'editor.xhtml?page=' + currentPage;
        }
    )

}

/* cached items grid*/
function createCachedEmployeesGrid(selector, url, colModel, rowsPerPage, currentPage, locale){
    var pagerId = "#gridPager"; // muss als param übergebne
    var data = {};
    var totalRows = 100;
    var totalPages = Math.ceil(totalRows/rowsPerPage);
    $(selector).jqGrid({
        regional: locale,
        datatype: function(postdata) {
            var thegrid = this;
            var currPage = $(this).getGridParam('page');
            //if(currPage == 0) currPage = 1;

            var minPage = currPage - 1;
            if(minPage < 1) minPage = 1;
            var maxPage = currPage + 1;
            if(maxPage > totalPages) maxPage = totalPages;
            var rows = (maxPage - minPage + 1)*rowsPerPage;

            postdata.rows = rows;
            //postdata.page = (minPage-1);
            postdata.offset = (minPage-1)*rowsPerPage

            if(data[currPage]) {
                thegrid.addJSONData(data[currPage]);
            }
            else{
                data = {};
                $.ajax({
                    url: url,
                    data:postdata,
                    dataType:"json",
                    complete: function(jsonData,stat){
                        if(stat=="success") {
                            if(minPage == currPage){
                                data[minPage] = data[currPage] = jsonData.responseJSON.slice(0,rowsPerPage);
                                data[maxPage] = jsonData.responseJSON.slice(rowsPerPage, 2*rowsPerPage);
                            }
                            else{
                                if(currPage == totalPages){
                                    data[minPage] = jsonData.responseJSON.slice(0,rowsPerPage);
                                    data[currPage] = data[maxPage] = jsonData.responseJSON.slice(rowsPerPage, 2*rowsPerPage);
                                }
                                else{
                                    data[minPage] = jsonData.responseJSON.slice(0,rowsPerPage);
                                    data[currPage] = jsonData.responseJSON.slice(rowsPerPage ,2*rowsPerPage);
                                    data[maxPage] = jsonData.responseJSON.slice(2*rowsPerPage, 3*rowsPerPage);
                                }
                            }

                            thegrid.addJSONData(data[currPage]);
                        }
                    }
                })
            }

            if(!data[maxPage]){
                data = {};
                $.ajax({
                    url: url,
                    data:postdata,
                    dataType:"json",
                    complete: function(jsonData,stat){
                        if(stat=="success") {
                            if(minPage == currPage){
                                data[minPage] = data[currPage] = jsonData.responseJSON.slice(0,rowsPerPage);
                                data[maxPage] = jsonData.responseJSON.slice(rowsPerPage, 2*rowsPerPage);
                            }
                            else{
                                if(currPage == totalPages){
                                    data[minPage] = jsonData.responseJSON.slice(0,rowsPerPage);
                                    data[currPage] = data[maxPage] = jsonData.responseJSON.slice(rowsPerPage, 2*rowsPerPage);
                                }
                                else{
                                    data[minPage] = jsonData.responseJSON.slice(0,rowsPerPage);
                                    data[currPage] = jsonData.responseJSON.slice(rowsPerPage, 2*rowsPerPage);
                                    data[maxPage] = jsonData.responseJSON.slice(2*rowsPerPage, 3*rowsPerPage);
                                }
                            }
                        }
                    }
                })
            }




        },
        jsonReader: {
            repeatitems: false,
            id: "id",
            root: function (obj) { return obj; },
            //page: function (obj) { return 3; },
            //total: function (obj) { return rowsPerPage; }, // total page number
            //records: function (obj) { return totalPages; } // total records number
            total: function (obj) { return totalPages; }, // total page number
            records: function (obj) { return totalRows; } // total records number

        },
        colModel: colModel,
        //altRows : true,
        rownumbers : false,
        colMenu : true,
        menubar: true,
        viewrecords : true,
        hoverrows : true,
        height: '100%',
        rowNum: rowsPerPage,
        records: 100,
        caption : 'Employees',
        sortable: true,
        //altRows: true, This does not work in boostrarap
        // altclass: '....'
        pager: "#gridPager",
        // set table stripped class in table style in bootsrap
        //page: currentPage

    });

    $(selector).jqGrid('navGrid',pagerId, {
        search: true, // show search button on the toolbar
        add: true,
        edit: false,
        del: false,
        refresh: true
    });


    $(selector).jqGrid('menubarAdd',  [
        {
            id : 'das',
            //cloasoncall : true,
            title : 'Sort by Category',
            click : function ( event) {
                $(selector).jqGrid('sortGrid','CategoryName');
            }
        },
        {
            divider : true,
        },
        {
            id : 'was',
            //cloasoncall : true,
            title : 'Toggle Visibility',
            click : function ( event) {
                var state = (this.p.gridstate === 'visible') ? 'hidden' : 'visible';
                $(selector).jqGrid('setGridState',state);
            }
        },
        {
            id : 'show_hide_col',
            //cloasoncall : true,
            title : 'Show/Hide column',
            click : function ( event) {
                $(selector).jqGrid('columnChooser');

            }
        }
    ]);

    // workaround set width to 100%
    var newWidth = $(selector).closest(".ui-jqgrid").parent().width();
    $(selector).jqGrid("setGridWidth", newWidth, true);

    $('td#add_grid.ui-pg-button').off('click').on('click', function(e){

            e.preventDefault();
            e.stopPropagation();
            //alert("I was clicked");
            var currentPage = $('#grid').getGridParam('page');
            window.location.href = 'editor.xhtml?page=' + currentPage;
        }
    )

}

function createTheGrid(selector, colModel, url, rowsPerPage, selectedPage, locale){

    xhrGet(url).then(function(data) {

        createEmployeesGrid(selector, colModel, data, rowsPerPage, locale);

        $('#grid').jqGrid('setGridParam', {page: selectedPage});

        //refresh grid
        $('#grid').trigger( 'reloadGrid' );

    });
}

function createCachedGrid(selector, colModel, url, rowsPerPage, selectedPage, locale){

        createCachedEmployeesGrid(selector, url, colModel, rowsPerPage, locale)

        $('#grid').jqGrid('setGridParam', {page: selectedPage});

        //refresh grid
        $('#grid').trigger( 'reloadGrid' );

}
