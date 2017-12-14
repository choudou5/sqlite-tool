package com.alexfu.sqlitequerybuilder.builder.update;

import com.alexfu.sqlitequerybuilder.api.Builder;
import com.alexfu.sqlitequerybuilder.builder.SegmentBuilder;
import com.alexfu.sqlitequerybuilder.builder.delete.DeleteWhereBuilder;
import com.alexfu.sqlitequerybuilder.builder.insert.InsertValuesBuilder;
import com.alexfu.sqlitequerybuilder.utils.AssertUtil;
import com.alexfu.sqlitequerybuilder.utils.ToolkitUtil;

import java.util.ArrayList;
import java.util.List;

public class SetColumnsBuilder extends SegmentBuilder {

  private List<String> setItems = new ArrayList<>();

  private final SegmentBuilder predicate;
  private String table;

  public SetColumnsBuilder(SegmentBuilder predicate, String table) {
    this.predicate = predicate;
    this.table = table;
  }

  public SetColumnsBuilder sets(String ... setItems) {
    AssertUtil.isNotNull(setItems, "setItems cannot be null");
    for (String setItem : setItems) {
      this.setItems.add(setItem);
    }
    return this;
  }

  public SetColumnsBuilder set(String setItem) {
    AssertUtil.isNotNull(setItem, "setItem cannot be null");
    setItems.add(setItem);
    return this;
  }

  public UpdateWhereBuilder where(String condition) {
    AssertUtil.isNotNull(condition, "Condition cannot be null");
    return new UpdateWhereBuilder(this, condition);
  }

  @Override
  public String build() {
    return predicate.build() + table +" SET " + ToolkitUtil.joinList(", ", setItems);
  }
}
