function createEmployees_agGrid(selector, colNames, url, data){
    var gridOptions = {
        rowData: data,

        headerHeight: 44,
        rowHeight: 44,
        enableSorting: true,
        enableFilter: true,
        enableColResize: true,
        pagination: true,
        paginationPageSize: 10,
        paginationAutoPageSize: false,

        // define columns
        columnDefs: [
            // uses the default column properties
            {headerName: colNames['firstName'], field: 'firstName'},

            // overrides the default with a number filter
            {
                headerName: colNames['lastName'],
                field: 'lastName',
                cellStyle: function (params) {
                    if (params.value.includes('1')) {
                        //mark police cells as red
                        return {color: 'red', backgroundColor: 'green'};
                    } else {
                        return null;
                    }
                }
            },

            // overrides the default using a column type
            {headerName: colNames['salary'], field: 'salary', type: 'nonEditableColumn'},

            // overrides the default using a multiple column types
            {headerName: colNames['birthDate'], field: 'birthDate', type: ['dateColumn', 'nonEditableColumn']}
        ],

        // a default column definition with properties that get applied to every column
        defaultColDef: {
            // set every column width
            width: 150,
            // make every column editable
            editable: false,
            // make every column use 'text' filter by default
            filter: 'agTextColumnFilter'
        },

        // if we had column groups, we could provide default group items here
        defaultColGroupDef: {},

        // define a column type (you can define as many as you like)
        columnTypes: {
            "nonEditableColumn": {editable: false},
            //"dateColumn": {filter: 'agDateColumnFilter', filterParams: {comparator: myDateComparator}, suppressMenu:true}
            "dateColumn": {filter: 'agDateColumnFilter', suppressMenu:true}
        }
    };

    // lookup the container we want the Grid to use
    var eGridDiv = document.querySelector(selector);

    // create the grid passing in the div to use together with the columns data we want to use
    new agGrid.Grid(eGridDiv, gridOptions);

    xhrGet(url).then(function (data) {
        gridOptions.api.setRowData(data);
    })

}

function old() {

    // specify the columns
    var columnDefs = [
        {
            headerName: "#{employeeMsg['employee.table.col.firstName']}",
            field: "firstName",
            filterParams: {newRowsAction: 'keep'},
            checkboxSelection: function (params) {
                // we put checkbox on the name if we are not doing grouping
                return params.columnApi.getRowGroupColumns().length === 0;
            },
            headerCheckboxSelection: function (params) {
                // we put checkbox on the name if we are not doing grouping
                return params.columnApi.getRowGroupColumns().length === 0;
            }
        },
        {
            headerName: "#{employeeMsg['employee.table.col.lastName']}",
            field: "lastName", filter: "agTextColumnFilter",
            cellStyle: function (params) {
                if (params.value.includes('1')) {
                    //mark police cells as red
                    return {color: 'red', backgroundColor: 'green'};
                } else {
                    return null;
                }
            }
        },
        {headerName: "#{employeeMsg['employee.table.col.salary']}", field: "salary"},
        {headerName: "#{employeeMsg['employee.table.col.birthDate']}", field: "birthDate", filter: "agDateColumnFilter"}
    ];

    /*            var autoGroupColumnDef = {
                headerName: 'Group',
                width: 200,
                field: 'firstName',
                valueGetter: function(params) {
                if (params.node.group) {
                return params.node.key;
                } else {
                return params.data[params.colDef.field];
                }
                },
                headerCheckboxSelection: true,
                // headerCheckboxSelectionFilteredOnly: true,
                cellRenderer:'agGroupCellRenderer',
                cellRendererParams: {
                checkbox: true
                }
                };*/
    // let the grid know which columns and what data to use
    var gridOptions = {
        columnDefs: columnDefs,
        headerHeight: 44,
        rowHeight: 44,
        enableSorting: true,
        enableFilter: true,
        enableColResize: true,
        pagination: true,
        paginationPageSize: 10,
        paginationAutoPageSize: false,
        // autoGroupColumnDef: autoGroupColumnDef,
        //floatingFilter: true
    };

    // lookup the container we want the Grid to use
    var eGridDiv = document.querySelector('#myGrid');

    // create the grid passing in the div to use together with the columns data we want to use
    new agGrid.Grid(eGridDiv, gridOptions);

    xhrGet('http://localhost:8080/webapp/rest/EmployeeService/employee/viewitems').then(function (data) {
        gridOptions.api.setRowData(data);
    })
}