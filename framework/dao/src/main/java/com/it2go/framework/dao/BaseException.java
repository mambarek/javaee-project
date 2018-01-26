package com.it2go.framework.dao;

import lombok.Data;

@Data
public class BaseException extends Exception {

    private String errorCode;

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String errorCode, String message) {
        super(message);
    }

}
