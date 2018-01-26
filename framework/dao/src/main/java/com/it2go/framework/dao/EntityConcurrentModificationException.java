package com.it2go.framework.dao;

public class EntityConcurrentModificationException extends BaseException {
    @Override
    public String getMessage() {
        return "Entity was modified by another process, pleas reload it!";
    }
}
