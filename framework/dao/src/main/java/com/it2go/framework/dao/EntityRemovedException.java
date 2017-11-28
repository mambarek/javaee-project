package com.it2go.framework.dao;

public class EntityRemovedException extends Exception {
    @Override
    public String getMessage() {
        return "Entity was removed!";
    }
}
