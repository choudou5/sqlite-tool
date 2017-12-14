package com.alexfu.sqlitequerybuilder.builder.update;

import com.alexfu.sqlitequerybuilder.builder.SegmentBuilder;
import com.alexfu.sqlitequerybuilder.builder.insert.InsertIntoBuilder;
import com.alexfu.sqlitequerybuilder.utils.AssertUtil;

public class UpdateBuilder extends SegmentBuilder {

  public SetColumnsBuilder table(String table) {
    AssertUtil.isNotNull(table, "Table name cannot be null");
    return new SetColumnsBuilder(this, table);
  }

  @Override
  public String build() {
    return "UPDATE ";
  }
}
