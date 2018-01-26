package com.it2go.framework.dao;

public class EntityNotPersistedException extends BaseException {

    public EntityNotPersistedException() {
        super();
    }

    public EntityNotPersistedException(String errorCode, String message) {
        super(errorCode, message);
    }

    public EntityNotPersistedException(String message) {
        super(message);
    }


    @Override
    public String getMessage() {
        return "Entity not persisted!";
    }
}
