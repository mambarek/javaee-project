package com.it2go.employee.dto.search;

import lombok.Data;

import java.util.List;

@Data
public class Group {

    private GroupOperation groupOp;
    private List<Rule> rules;
    private List<Group> groups;
}
