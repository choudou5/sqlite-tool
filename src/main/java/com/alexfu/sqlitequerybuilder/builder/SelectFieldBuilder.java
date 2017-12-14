package com.alexfu.sqlitequerybuilder.builder;

import com.alexfu.sqlitequerybuilder.utils.StrUtils;

public class SelectFieldBuilder extends SelectBuilder {

  private String[] fields;

  public SelectFieldBuilder(String... fields) {
    this.fields = fields;
  }

  @Override
  public String build() {
    return StrUtils.join(" ", "SELECT", StrUtils.join(",", fields));
  }
}
