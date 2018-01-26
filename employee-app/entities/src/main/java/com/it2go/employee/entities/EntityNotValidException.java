package com.it2go.employee.entities;

import com.it2go.framework.dao.BaseException;

public class EntityNotValidException extends BaseException {

    public EntityNotValidException() {
        super();
    }

    public EntityNotValidException(String message) {
        super(message);
    }

    public EntityNotValidException(String errorCode, String message) {
        super(errorCode, message);
    }

    @Override
    public String getMessage() {
        return "Entity not valid!";
    }
}
