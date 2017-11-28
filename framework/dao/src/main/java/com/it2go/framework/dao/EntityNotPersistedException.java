package com.it2go.framework.dao;

public class EntityNotPersistedException extends Exception {
    @Override
    public String getMessage() {
        return "Entity not persisted!";
    }
}
