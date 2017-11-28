package com.it2go.framework.dao;

public class EntityNotFoundException extends Exception {
    @Override
    public String getMessage() {
        return "Entity not found!";
    }
}
