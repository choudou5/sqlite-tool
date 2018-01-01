package com.alexfu.sqlitequerybuilder.builder;

import com.alexfu.sqlitequerybuilder.api.Builder;
import com.alexfu.sqlitequerybuilder.utils.ToolkitUtil;

public class SelectLimitBuilder extends SegmentBuilder {

  private Builder prefix;
  private int rows = -1;
  private int start = -1;

  public SelectLimitBuilder(Builder prefix, int rows) {
    this.prefix = prefix;
    this.rows = rows;
  }

  public SegmentBuilder start(int start) {
    this.start = start;
    return this;
  }

  @Override
  public String build() {
    String offsetString = null;
    if (start != -1) {
      offsetString = ToolkitUtil.join(" ", "OFFSET", String.valueOf(start));
    }

    String result = ToolkitUtil.join(" ", prefix.build(), "LIMIT", String.valueOf(rows));
    if (offsetString != null) {
      result = ToolkitUtil.join(" ", result, offsetString);
    }
    return result;
  }
}
