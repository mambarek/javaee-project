package com.it2go.employee.dto.search;

import com.it2go.employee.dto.EmployeeTableItem;
import lombok.Data;

import java.util.List;

/*
{
  "total": "1",
  "page": "1",
  "records": "2",
  "rows" : [
    {"id" :"1", "name": "Bob", "phone": "232-532-6268", "addres":"address 1"},
    {"id" :"2", "name": "Jeff", "phone": "365-267-8325", "addres":"address 2"}
  ]
}
 */
@Data
public class SearchResult {

    private int total;
    private int page;
    private int records;
    private List<EmployeeTableItem> rows;
    private Object userdata;
}
