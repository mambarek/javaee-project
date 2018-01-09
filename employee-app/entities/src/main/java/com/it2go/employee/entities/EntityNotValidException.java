package com.it2go.employee.entities;

public class EntityNotValidException extends Exception {
    @Override
    public String getMessage() {
        return "Entity not valid!";
    }
}
