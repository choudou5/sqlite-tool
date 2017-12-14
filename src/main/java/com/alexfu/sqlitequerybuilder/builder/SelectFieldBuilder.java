package com.alexfu.sqlitequerybuilder.builder;

import com.alexfu.sqlitequerybuilder.utils.ToolkitUtil;

public class SelectFieldBuilder extends SelectBuilder {

  private String[] fields;

  public SelectFieldBuilder(String... fields) {
    this.fields = fields;
  }

  @Override
  public String build() {
    return ToolkitUtil.join(" ", "SELECT", ToolkitUtil.join(",", fields));
  }
}
