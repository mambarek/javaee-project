package com.it2go.employee.ui.jsf;

import lombok.Data;
import sun.reflect.Reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Data
public class SelectItemWrapper<T> {
    private T value;
    private String localizedLabel;

    public SelectItemWrapper(T value) {
        this.value = value;
    }

    public String getHtml(){
        return "<i class=\"fas fa-male\"/>  %s";
    }

    public Object getMethodValue(String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = value.getClass();
        final Method method = c.getDeclaredMethod(methodName);
        return method.invoke(value);
    }
}
