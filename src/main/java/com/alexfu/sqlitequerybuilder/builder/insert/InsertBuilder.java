package com.alexfu.sqlitequerybuilder.builder.insert;

import com.alexfu.sqlitequerybuilder.builder.SegmentBuilder;
import com.alexfu.sqlitequerybuilder.utils.AssertUtil;

public class InsertBuilder extends SegmentBuilder {
  public InsertIntoBuilder into(String table) {
    AssertUtil.isNotNull(table, "Table name cannot be null");
    return new InsertIntoBuilder(this, table);
  }

  @Override
  public String build() {
    return "INSERT INTO";
  }
}
