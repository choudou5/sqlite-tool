package com.alexfu.sqlitequerybuilder.builder;

import com.alexfu.sqlitequerybuilder.utils.AssertUtil;

public abstract class SelectBuilder extends SegmentBuilder {
  public SelectFromBuilder from(String table) {
    AssertUtil.isNotNull(table, "Table cannot be null");
    return new SelectFromBuilder(this, table);
  }
}
