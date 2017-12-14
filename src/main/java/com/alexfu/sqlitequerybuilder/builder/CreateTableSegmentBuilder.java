package com.alexfu.sqlitequerybuilder.builder;

import com.alexfu.sqlitequerybuilder.api.Column;
import com.alexfu.sqlitequerybuilder.utils.AssertUtil;
import com.alexfu.sqlitequerybuilder.utils.ToolkitUtil;

import java.util.ArrayList;
import java.util.List;

public class CreateTableSegmentBuilder extends SegmentBuilder {

  private boolean temp;
  private boolean ifNotExists;
  private String name;
  private final List<Column> definitions = new ArrayList<Column>();

  public CreateTableSegmentBuilder() {}

  public CreateTableSegmentBuilder temp() {
    temp = true;
    return this;
  }

  public CreateTableSegmentBuilder ifNotExists() {
    ifNotExists = true;
    return this;
  }

  public CreateTableSegmentBuilder column(Column column) {
    AssertUtil.isNotNull(column, "A non-null column is required.");
    definitions.add(column);
    return this;
  }

  public CreateTableSegmentBuilder table(String name) {
    AssertUtil.isNotEmpty(name, "Table name can not be empty.");
    this.name = name;
    return this;
  }

  @Override
  public String build() {
    String head = "CREATE "
      + (temp ? "TEMP " : "")
      + "TABLE"
      + (ifNotExists ? " IF NOT EXISTS" : "");

    String tail = name + "(" + ToolkitUtil.join(",", definitions.toArray()) + ")";

    return ToolkitUtil.join(" ", head, tail);
  }

}
