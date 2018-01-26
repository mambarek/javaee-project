package com.it2go.framework.dao;

public class EntityRemovedException extends BaseException {

    public EntityRemovedException() {
        super();
    }

    public EntityRemovedException(String message) {
        super(message);
    }

    public EntityRemovedException(String errorCode, String message) {
        super(errorCode, message);
    }

    @Override
    public String getMessage() {
        return "Entity was removed!";
    }
}
