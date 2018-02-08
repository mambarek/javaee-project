package com.it2go.employee.dto.search;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Operation {

    // [{ oper:'eq', text:'equal'},
    // { oper:'ne', text:'not equal'},
    // { oper:'lt', text:'less'},
    // { oper:'le', text:'less or equal'},
    // { oper:'gt', text:'greater'},
    // { oper:'ge', text:'greater or equal'},
    // { oper:'bw', text:'begins with'},
    // { oper:'bn', text:'does not begin with'},
    // { oper:'in', text:'is in'},
    // { oper:'ni', text:'is not in'},
    // { oper:'ew', text:'ends with'},
    // { oper:'en', text:'does not end with'},
    // { oper:'cn', text:'contains'},
    // { oper:'nc', text:'does not contain'},
    // { oper:'nu', text:'is null'},
    // { oper:'nn', text:'is not null'},
    // {oper:'bt', text:'between'}]

    EQUAL("eq","equal"),
    NOT_EQUAL("ne","not equal"),
    LESS("lt","less"),
    LESS_OR_EQUAL("le","less or equal"),
    GREATHER("gt","greater"),
    GREATHER_OR_EQUAL("ge","greater or equal"),
    BEGINS_WITH("bw","begins with"),
    NOT_BEGIN_WITH("bn","does not begin with"),
    NOT_IN("ni","is not in"),
    ENDS_WITH("ew","ends with"),
    NOT_ENDS_WITH("en","does not end with"),
    CONTAINS("cn","contains"),
    NOT_CONTAINS("nc","does not contain"),
    NULL("nu","is null"),
    NOT_NULL("nn","is not null"),
    BETWEEN("bt","between");

    private final String id;
    private final String name;

    Operation(String _id){
        id = _id;
        name = "";
    };

    Operation(String _id, String _name){
        id = _id;
        name = _name;
    }

    @JsonValue
    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }
}
