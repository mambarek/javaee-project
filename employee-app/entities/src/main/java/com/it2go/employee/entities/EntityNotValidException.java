package com.it2go.employee.entities;


import com.it2go.framework.dao.BaseException;
import com.it2go.framework.dao.ExceptionCode;

public class EntityNotValidException extends BaseException {

    public EntityNotValidException() {
        super(ExceptionCode.ENTITY_NOT_VALID);
    }

}
