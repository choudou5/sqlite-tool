package com.alexfu.sqlitequerybuilder.builder;

import com.alexfu.sqlitequerybuilder.utils.AssertUtil;
import com.alexfu.sqlitequerybuilder.utils.ToolkitUtil;

public class SelectHavingBuilder extends SegmentBuilder {
  private SelectGroupByBuilder prefix;
  private String condition;

  public SelectHavingBuilder(SelectGroupByBuilder prefix, String condition) {
    this.prefix = prefix;
    this.condition = condition;
  }

  public SelectOrderByBuilder orderBy(String column) {
    AssertUtil.isNotNull(column, "Column cannot be null");
    return orderBy(column, SelectOrderByBuilder.OrderType.DESC);
  }

  public SelectOrderByBuilder orderBy(String column, SelectOrderByBuilder.OrderType type) {
    AssertUtil.isNotNull(column, "Column cannot be null");
    return new SelectOrderByBuilder(this, column, type);
  }

  public SelectLimitBuilder rows(int rows) {
    return new SelectLimitBuilder(this, rows);
  }

  @Override
  public String build() {
    return ToolkitUtil.join(" ", prefix.build(), "HAVING", condition);
  }
}
