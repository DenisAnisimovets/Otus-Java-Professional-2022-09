package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), resultSet -> {
            try {
                if(resultSet.next()) {
                    T model = entityClassMetaData.getConstructor().newInstance();

                    List<Field> fieldList = entityClassMetaData.getAllFields();
                    for (Field field : fieldList) {
                        field.setAccessible(true);
                        field.set(model, resultSet.getObject(field.getName()));
                    }

                    return model;
                } else {
                    throw new RuntimeException("Не найден объект по id");
                }

            } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("Произошла проблема в findById");
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(),
                resultSet -> {
                    var list = new ArrayList<T>();
                    try {
                        while (resultSet.next()) {
                            T model = entityClassMetaData.getConstructor().newInstance();

                            List<Field> fieldList = entityClassMetaData.getAllFields();
                            for (Field field : fieldList) {
                                field.setAccessible(true);
                                field.set(model, resultSet.getObject(field.getName()));
                            }

                            list.add(model);
                        }
                        return list;

                    } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException("Произошла проблема в findAll");
                    }
                }).orElseThrow(() -> new RuntimeException("Произошла проблема в findAll"));
    }

    @Override
    public long insert(Connection connection, T client) {

        List<Field> fieldList = entityClassMetaData.getFieldsWithoutId();
        List<Object> params = new ArrayList<>();
        try {
            for (Field field : fieldList) {
                field.setAccessible(true);
                params.add(field.get(client));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Произошла проблема в insert");
        }

        return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), params);
    }

    @Override
    public void update(Connection connection, T client) {
        List<Field> fieldList = entityClassMetaData.getFieldsWithoutId();
        List<Object> params = new ArrayList<>();
        try {
            for (Field field : fieldList) {
                field.setAccessible(true);
                params.add(field.get(client));
            }

            Field fieldId = entityClassMetaData.getIdField();
            fieldId.setAccessible(true);
            params.add(fieldId.get(client));
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Произошла проблема в update");
        }

        dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), params);
    }

    private Object apply(ResultSet resultSet) throws InvocationTargetException, InstantiationException {
        var list = new ArrayList<T>();
        try {
            while (resultSet.next()) {
                T model = entityClassMetaData.getConstructor().newInstance();

                List<Field> fieldList = entityClassMetaData.getAllFields();
                for (Field field : fieldList) {
                    field.setAccessible(true);
                    field.set(model, resultSet.getObject(field.getName()));
                }

                list.add(model);
            }

            return list;
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException("Произошла проблема в findAll");
        }
    }
}
