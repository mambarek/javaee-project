package com.it2go.framework.dao;

public class EntityNotFoundException extends BaseException {

    public EntityNotFoundException() {
        super(ExceptionCode.ENTITY_NOT_FOUND);
    }

}
