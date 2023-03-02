package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private final EntityClassMetaData entityClassMetaData;
    private final String fieldsList;
    private final String fieldsListWithoutId;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
        List<Field> fieldList = entityClassMetaData.getAllFields();
        List<Field> fieldListWithoutId = entityClassMetaData.getFieldsWithoutId();
        this.fieldsList = fieldList.stream().map(field->field.getName()).collect(Collectors.joining(","));
        this.fieldsListWithoutId = fieldListWithoutId.stream().map(field->field.getName()).collect(Collectors.joining(","));
    }

    @Override
    public String getSelectAllSql() {
        return "select " + fieldsList + " from " + entityClassMetaData.getName();
    }

    @Override
    public String getSelectByIdSql() {
        return "select " + fieldsList + " from " + entityClassMetaData.getName() + " where " +
                entityClassMetaData.getIdField().getName() + " = ?";
    }

    @Override
    public String getInsertSql() {
        return "insert into " + entityClassMetaData.getName() + "(" + fieldsListWithoutId + ") values (" +
                fieldsListWithoutId.replaceAll("[^,]", "").replaceAll(",", "?, ").concat("?") + ")";
    }

    @Override
    public String getUpdateSql() {
        List<Field> fieldList = entityClassMetaData.getFieldsWithoutId();
        return "update " + entityClassMetaData.getName() + " set " +
                fieldList.stream().map(it -> it.getName()).collect(Collectors.joining(" = ?, ")).concat(" = ?") +
                " where " + entityClassMetaData.getIdField().getName() + " = ?";
    }
}
