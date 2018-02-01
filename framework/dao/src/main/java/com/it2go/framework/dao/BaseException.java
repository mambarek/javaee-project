package com.it2go.framework.dao;

import java.util.Locale;
import java.util.ResourceBundle;

public class BaseException extends Exception {

    private final ExceptionCode exceptionCode;

    public BaseException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public String getMessage() {
        return exceptionCode.getMessage();
    }

    public String getLocalizedMessage(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("com/it2go/exception/ExceptionMessages", locale);

        return bundle.getString(this.exceptionCode.getCode());
    }
}
