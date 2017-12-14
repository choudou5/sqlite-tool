package com.alexfu.sqlitequerybuilder.builder;

import com.alexfu.sqlitequerybuilder.utils.AssertUtil;
import com.alexfu.sqlitequerybuilder.utils.ToolkitUtil;

public class SelectGroupByBuilder extends SegmentBuilder {

  private SelectFromBuilder prefix;
  private String column;

  public SelectGroupByBuilder(SelectFromBuilder prefix, String column) {
    this.prefix = prefix;
    this.column = column;
  }

  public SelectHavingBuilder having(String condition) {
    AssertUtil.isNotNull(condition, "Condition cannot be null");
    return new SelectHavingBuilder(this, condition);
  }

  @Override
  public String build() {
    return ToolkitUtil.join(" ", prefix.build(), "GROUP BY", column);
  }
}
