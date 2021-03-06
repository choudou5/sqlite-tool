package com.alexfu.sqlitequerybuilder.builder.delete;

import com.alexfu.sqlitequerybuilder.builder.SegmentBuilder;
import com.alexfu.sqlitequerybuilder.utils.AssertUtil;
import com.alexfu.sqlitequerybuilder.utils.ToolkitUtil;

public class DeleteFromBuilder extends SegmentBuilder {

  private DeleteBuilder prefix;
  private String[] tables;

  public DeleteFromBuilder(DeleteBuilder prefix, String... tables) {
    this.prefix = prefix;
    this.tables = tables;
  }

  public DeleteWhereBuilder where(String condition) {
    AssertUtil.isNotNull(condition, "Condition cannot be null");
    return new DeleteWhereBuilder(this, condition);
  }

  @Override
  public String build() {
    return ToolkitUtil.join(" ", prefix.build(), "FROM", ToolkitUtil.join(",", tables));
  }
}
