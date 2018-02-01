package com.it2go.framework.dao;

public class EntityConcurrentModificationException extends BaseException {

    public EntityConcurrentModificationException() {
        super(ExceptionCode.CONCURRENT_MODIFICATION);
    }
}
