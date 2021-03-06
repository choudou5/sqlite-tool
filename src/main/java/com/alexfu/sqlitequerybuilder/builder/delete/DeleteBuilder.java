package com.alexfu.sqlitequerybuilder.builder.delete;

import com.alexfu.sqlitequerybuilder.builder.SegmentBuilder;
import com.alexfu.sqlitequerybuilder.utils.AssertUtil;
import com.alexfu.sqlitequerybuilder.utils.ToolkitUtil;

public class DeleteBuilder extends SegmentBuilder {
  public DeleteFromBuilder from(String table) {
    AssertUtil.isNotNull(table, "Table name cannot be null");
    return new DeleteFromBuilder(this, table);
  }

  @Override
  public String build() {
    return ToolkitUtil.join(" ", "DELETE");
  }
}
