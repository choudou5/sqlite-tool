package com.alexfu.sqlitequerybuilder.utils;

import com.alexfu.sqlitequerybuilder.builder.SelectOrderByBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
        AssertUtil.isNotNull(clasz, "类不能为空!");
        List<Field> methods = ReflectionUtils.getAllFields(clasz);
        List<String> columnNames = new ArrayList<>();
        for (Field field : methods) {
            columnNames.add(ToolkitUtil.toUnderScoreCase(field.getName()));
        }
        return columnNames;
    }

}
