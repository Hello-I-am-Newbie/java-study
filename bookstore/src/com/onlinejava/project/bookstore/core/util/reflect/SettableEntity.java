package com.onlinejava.project.bookstore.core.util.reflect;

import com.onlinejava.project.bookstore.core.util.StringUtils;
import com.onlinejava.project.bookstore.application.domain.entity.Grade;
import com.onlinejava.project.bookstore.application.domain.entity.Entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Supplier;

import static com.onlinejava.project.bookstore.core.util.StringUtils.*;
import static com.onlinejava.project.bookstore.core.util.reflect.ReflectionUtils.*;

public class SettableEntity<T extends Entity> {
    private final T model;
    private final Map<String, Method> methods;

    public SettableEntity(T model) {
        this.model = model;
        this.methods = getMethodsMap(model.getClass());
    }
    public SettableEntity(Class<T> clazz) {
        this.model = newInstance(clazz);
        this.methods = getMethodsMap(model.getClass());
    }

    public void set(String fieldName, String value) {
        String setterName = "set" + toCapitalize(fieldName.trim());
        Method setter = methods.get(setterName);
        if (setter == null) {
            System.out.printf("Setter method of [%s] is not found. [value:%s]\n", fieldName, value);
            return;
        }
        set(setter, value);
    }

    public void set(Method setter, String value) {
        if (setter == null) {
            System.out.printf("Setter method is null. [value:%s]\n", value);
            return;
        }
        if (setter.getParameterCount() != 1) {
            System.out.printf("Setter method [%s] has invalid number of parameters. [#params:%d]\n", setter.getName(), setter.getParameterCount());
            return;
        }

        Type type = setter.getParameters()[0].getType();
        invoke(setter, valueConverter(type, value.trim()));
    }

    private Object valueConverter(Type type, String value) {
        if (type == int.class) {
            return Integer.valueOf(value);

        } else if (type == boolean.class) {
            return Boolean.valueOf(value);

        } else if (type == Grade.class) {
            return Grade.valueOf(value);

        } else if (type == String.class) {
            return value;

        } else {
            throw new IllegalArgumentException(
                    String.format("%s can not be assigned to type [%s] in %s class",
                            value, type.getTypeName(), model.getClass().getName())
            );
        }
    }

    private Object invoke(Method setter, Object value) {

        try {
            return setter.invoke(model, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }

    public T getOrElse(Supplier<T> supplier) {
        if (model == null) {
            return supplier.get();
        }
        return model;
    }

    public T getOrThrow() {
        if (model == null) {
            throw new RuntimeException("Model is null");
        }
        return model;
    }

    public T getObject() {
        return model;
    }
}
