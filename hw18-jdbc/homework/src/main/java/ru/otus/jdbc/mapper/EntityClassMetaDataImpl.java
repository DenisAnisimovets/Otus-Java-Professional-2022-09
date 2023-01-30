package ru.otus.jdbc.mapper;

import ru.otus.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData{

    private final Class<T> entityClass;
    public EntityClassMetaDataImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public String getName() {
        return entityClass.getSimpleName();
    }

    @Override
    public Constructor getConstructor() {
        try {
            return entityClass.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Field getIdField() {
        return Arrays.stream(entityClass.getDeclaredFields()).filter(it -> it.isAnnotationPresent(Id.class)).findFirst().get();
    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.stream(entityClass.getDeclaredFields()).toList();
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return Arrays.stream(entityClass.getDeclaredFields()).filter(it -> !it.isAnnotationPresent(Id.class)).toList();
    }
}
