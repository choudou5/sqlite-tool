package com.alexfu.sqlitequerybuilder.api;

import com.alexfu.sqlitequerybuilder.utils.ToolkitUtil;

public class Column implements Builder {

  private final String name;
  private final ColumnType type;
  private final ColumnConstraint constraint;
  private final String defaultValue;

  public Column(String name, ColumnType type) {
    this(name, type, null);
  }

  public Column(String name, ColumnType type, ColumnConstraint constraint) {
    this(name, type, constraint, null);
  }

  public Column(String name, ColumnType type, ColumnConstraint constraint, String defaultValue) {
    this.name = name;
    this.type = type;
    this.constraint = constraint;
    this.defaultValue = defaultValue;
  }

  @Override
  public String build() {
    String result = ToolkitUtil.join(" ", name, type.toString());
    if (constraint != null) {
      result = ToolkitUtil.join(" ", result, constraint.toString());
    }
    if (defaultValue != null) {
      result = ToolkitUtil.join(" ", result, "DEFAULT", defaultValue);
    }
    return result;
  }

  @Override
  public String toString() {
    return build();
  }
}
