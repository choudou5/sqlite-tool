package com.alexfu.sqlitequerybuilder.builder;

import com.alexfu.sqlitequerybuilder.api.SelectType;
import com.alexfu.sqlitequerybuilder.utils.ToolkitUtil;

public class SelectTypeBuilder extends SelectBuilder {

  private SelectType type;

  public SelectTypeBuilder(SelectType type) {
    this.type = type;
  }

  @Override
  public String build() {
    return ToolkitUtil.join(" ", "SELECT", type.build());
  }
}
