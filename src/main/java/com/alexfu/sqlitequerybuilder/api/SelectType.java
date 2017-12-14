package com.alexfu.sqlitequerybuilder.api;

import com.alexfu.sqlitequerybuilder.utils.ToolkitUtil;

public class SelectType implements Builder {

  private enum Type {
    DISTINCT, ALL
  }

  private Type type;
  private String[] fields;

  private SelectType(Type type, String... fields) {
    this.type = type;
    this.fields = fields;
  }

  public static SelectType DISTINCT(String... fields) {
    return new SelectType(Type.DISTINCT, fields);
  }

  public static SelectType ALL(String... fields) {
    return new SelectType(Type.ALL, fields);
  }

  @Override
  public String build() {
    return ToolkitUtil.join(" ", type.toString(), ToolkitUtil.join(",", fields));
  }
}
