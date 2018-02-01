package com.it2go.framework.dao;

public enum ExceptionCode {

    ENTITY_NOT_VALID("1000","Entity not valid"),
    ENTITY_NOT_PERSISTED("1001","Entity not persisted"),
    CONCURRENT_MODIFICATION("1002","Entity was modified by another process, pleas reload it"),
    ENTITY_REMOVED("1003","Entity was removed"),
    ENTITY_NOT_FOUND("1004","Entity not found");

    private final String code;
    private final String message;

    ExceptionCode(String code, String msg){
        this.code = code;
        this.message = msg;
    }

    public String getCode(){
        return this.code;
    }

    public String getMessage(){
        return this.message;
    }
}
