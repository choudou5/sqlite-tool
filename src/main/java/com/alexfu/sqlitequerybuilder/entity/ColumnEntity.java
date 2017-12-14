package com.alexfu.sqlitequerybuilder.entity;

import java.io.Serializable;

/**
 * Desc:
 * User: xuhaowende
 * Time: 2017/12/14
 */
public class ColumnEntity implements Serializable{

    private String columnLabel;
    private String columnName;
    private String columnTypeName;
    private boolean isNullable = true;
    private boolean isPk = false;
    private boolean isAutoIncrement = false;


    public String getColumnLabel() {
        return columnLabel;
    }

    public ColumnEntity setColumnLabel(String columnLabel) {
        this.columnLabel = columnLabel;
        return this;
    }

    public String getColumnName() {
        return columnName;
    }

    public ColumnEntity setColumnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    public String getColumnTypeName() {
        return columnTypeName;
    }

    public ColumnEntity setColumnTypeName(String columnTypeName) {
        this.columnTypeName = columnTypeName;
        return this;
    }

    public boolean getIsNullable() {
        return isNullable;
    }

    public ColumnEntity setIsNullable(boolean nullable) {
        isNullable = nullable;
        return this;
    }

    public boolean getIsPk() {
        return isPk;
    }

    public ColumnEntity setIsPk(boolean pk) {
        isPk = pk;
        return this;
    }

    public boolean getIsAutoIncrement() {
        return isAutoIncrement;
    }

    public ColumnEntity setIsAutoIncrement(boolean autoIncrement) {
        isAutoIncrement = autoIncrement;
        return this;
    }
}
