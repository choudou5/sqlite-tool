package com.alexfu.sqlitequerybuilder.builder;

import com.alexfu.sqlitequerybuilder.api.ObjectType;
import com.alexfu.sqlitequerybuilder.utils.AssertUtil;
import com.alexfu.sqlitequerybuilder.utils.ToolkitUtil;

/**
 * @author Steven Wu
 */
public class DropSegmentBuilder extends SegmentBuilder {

  private boolean ifExists;
  private ObjectType type;
  private String name;

  private DropSegmentBuilder(ObjectType type, String name) {
    this.type = type;
    this.name = name;
  }

  public DropSegmentBuilder() {
  }

  public DropSegmentBuilder table(String table) {
    AssertUtil.isNotNull(table, "Table name cannot be null");
    return new DropSegmentBuilder(ObjectType.TABLE, table);
  }

  public DropSegmentBuilder view(String view) {
    AssertUtil.isNotNull(view, "View name cannot be null");
    return new DropSegmentBuilder(ObjectType.VIEW, view);
  }

  public DropSegmentBuilder index(String index) {
    AssertUtil.isNotNull(index, "Index name cannot be null");
    return new DropSegmentBuilder(ObjectType.INDEX, index);
  }

  public DropSegmentBuilder trigger(String trigger) {
    AssertUtil.isNotNull(trigger, "Trigger name cannot be null");
    return new DropSegmentBuilder(ObjectType.TRIGGER, trigger);
  }

  public SegmentBuilder ifExists() {
    ifExists = true;
    return this;
  }

  @Override
  public String build() {
    String statement = "DROP " + type + (ifExists ? " IF EXISTS" : "");
    return ToolkitUtil.join(" ", statement, name);
  }
  
}
