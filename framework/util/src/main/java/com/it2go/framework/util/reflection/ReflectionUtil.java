package com.it2go.framework.util.reflection;

import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.Set;

public class ReflectionUtil {

    static public <T> T getAttributeValue(Object object, String attributeName) {
        final Set<Field> allFields = ReflectionUtils.getAllFields(object.getClass(), f -> f.getName().equals(attributeName));

        final Optional<Field> first = allFields.stream().findFirst();

        return (T) first.map(field -> {
            field.setAccessible(true);
            try {
                return field.get(object);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }).orElse(null);
    }

    static <V> void setAttributeValue(Object object, String attributeName, V value) {
        final Set<Field> allFields = ReflectionUtils.getAllFields(object.getClass(), field -> field.getName().equals(attributeName));
        allFields.stream().findFirst().ifPresent(field -> {
            field.setAccessible(true);
            try {
                field.set(object, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }
}
