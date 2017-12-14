package com.alexfu.sqlitequerybuilder.utils;

import com.alexfu.sqlitequerybuilder.api.StatementExecutor;
import com.alexfu.sqlitequerybuilder.builder.SelectOrderByBuilder;
import com.alexfu.sqlitequerybuilder.entity.ColumnEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Desc: 表列 工具类
 * User: xuhaowende
 * Time: 2017/12/14
 */
public class TableColumnUtil {

    public static void main(String[] args) {
        List<String> names = getColumnNameList(SelectOrderByBuilder.class);
        for (String name : names) {
            System.out.println(name);
        }
    }

    /**
     * 获得 所有列名
     * @param clasz
     * @param <T>
     * @return
     */
    public static <T> List<String> getColumnNameList(Class<T> clasz){
        AssertUtil.isNotNull(clasz, "clasz不能为空!");
        List<Field> methods = ReflectionUtils.getAllFields(clasz);
        List<String> columnNames = new ArrayList<>();
        for (Field field : methods) {
            columnNames.add(ToolkitUtil.toUnderScoreCase(field.getName()));
        }
        return columnNames;
    }

    /**
     * 获得 所有列名
     * @param table
     * @return
     */
    public static List<String> getColumnNameList(String table){
        AssertUtil.isNotNull(table, "table不能为空!");
        return StatementExecutor.selectFieldNames(table);
    }

    /**
     * 获得 所有列名
     * @param table
     * @return
     */
    public static List<ColumnEntity> getColumnList(String table){
        AssertUtil.isNotNull(table, "table不能为空!");
        return StatementExecutor.selectFieldEntitys(table);
    }


    /**
     * 获取 Sql字段列名
     * @return
     */
    public static String[] getSqlsFields(String table){
        List<String> list = TableColumnUtil.getColumnNameList(table);
        String[] fields = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            fields[i] = list.get(i);
        }
        return fields;
    }


    /**
     * 获取 字段值
     * @return
     */
    public static Object[] getValues(Object object){
        List<Field> fields = getValidField(object);
        Object[] values = new Object[fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            values[i] = ReflectionUtils.getField(fields.get(i), object);
        }
        return values;
    }

    /**
     * 获得 有效字段
     * @param object
     * @return
     */
    public static List<Field> getValidField(Object object){
        List<String> columnNameList = TableColumnUtil.getColumnNameList(getTable(object.getClass()));
        List<Field> fields = ReflectionUtils.getAllFields(object.getClass());
        List<Field> validFields = new ArrayList<>();
        for (String columnName : columnNameList) {
            for (Field field : fields) {
                if(columnName.equals(ToolkitUtil.toUnderScoreCase(field.getName()))){
                    validFields.add(field);
                    break;
                }
            }
        }
        return validFields;
    }

    /**
     * 获得 表名
     * @param clasz
     * @return
     */
    public static String getTable(Class clasz){
        return ToolkitUtil.toUnderScoreCase(clasz.getSimpleName());
    }


}
