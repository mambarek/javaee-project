package com.it2go.employee.dto.search;

import lombok.Data;

@Data
public class Rule {

    private String field;
    private Operation op;
    private String data;
}
