package com.alexfu.sqlitequerybuilder.builder.update;

import com.alexfu.sqlitequerybuilder.api.Builder;
import com.alexfu.sqlitequerybuilder.builder.SegmentBuilder;
import com.alexfu.sqlitequerybuilder.builder.SelectAndBuilder;
import com.alexfu.sqlitequerybuilder.builder.SelectLimitBuilder;
import com.alexfu.sqlitequerybuilder.builder.SelectOrderByBuilder;
import com.alexfu.sqlitequerybuilder.utils.AssertUtil;

import static com.alexfu.sqlitequerybuilder.utils.ToolkitUtil.join;

public class UpdateWhereBuilder extends SegmentBuilder {

  private Builder prefix;
  private String condition;

  public UpdateWhereBuilder(Builder prefix, String condition) {
    this.condition = condition;
    this.prefix = prefix;
  }

  @Override
  public String build() {
    return join(" ", prefix.build(), " WHERE", condition);
  }
}
