package com.it2go.framework.dao;

public class EntityNotFoundException extends BaseException {

    public EntityNotFoundException() {
        super();
    }

    public EntityNotFoundException(String errorCode, String message) {
        super(errorCode, message);
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        String msg = super.getMessage();
        if(msg != null)
            return msg;

        return "Entity not found!";
    }
}
