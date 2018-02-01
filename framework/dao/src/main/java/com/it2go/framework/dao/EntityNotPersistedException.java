package com.it2go.framework.dao;

public class EntityNotPersistedException extends BaseException {

    public EntityNotPersistedException() {
        super(ExceptionCode.ENTITY_NOT_PERSISTED);
    }


}
