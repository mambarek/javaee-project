package com.it2go.framework.dao;

public class EntityRemovedException extends BaseException {

    public EntityRemovedException() {
        super(ExceptionCode.ENTITY_REMOVED);
    }
}
