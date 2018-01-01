package com.alexfu.sqlitequerybuilder.builder;

import com.alexfu.sqlitequerybuilder.api.Builder;
import com.alexfu.sqlitequerybuilder.utils.AssertUtil;
import com.alexfu.sqlitequerybuilder.utils.ToolkitUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.alexfu.sqlitequerybuilder.utils.ToolkitUtil.join;

public class SelectWhereBuilder extends SegmentBuilder {


  private Builder prefix;
  private StringBuilder conditions = new StringBuilder(36);

  public SelectWhereBuilder(Builder prefix, String condition) {
    conditions.append(condition);
    this.prefix = prefix;
  }

  public enum CondType{
    AND,
    OR
  }

  public SelectWhereBuilder and(String condition) {
    AssertUtil.isNotNull(condition, "Condition cannot be null");
    conditions.append(" AND "+condition);
    return this;
  }

  public SelectWhereBuilder or(String condition) {
    AssertUtil.isNotNull(condition, "Condition cannot be null");
    conditions.append(" OR "+condition);
    return this;
  }

  public SelectWhereBuilder cond(CondType type, String field, String symbol, String value) {
    AssertUtil.isNotNull(type, "type cannot be null");
    AssertUtil.isNotNull(field, "field cannot be null");
    AssertUtil.isNotNull(symbol, "symbol cannot be null");
    AssertUtil.isNotNull(value, "value cannot be null");
    conditions.append(" "+type.name()+" "+field+" "+symbol+" "+value);
    return this;
  }

  public SelectLimitBuilder rows(int rows) {
    return new SelectLimitBuilder(this, rows);
  }

  public SelectOrderByBuilder orderBy(String column) {
    return orderBy(column, SelectOrderByBuilder.OrderType.DESC);
  }

  public SelectOrderByBuilder orderBy(String column, SelectOrderByBuilder.OrderType type) {
    return new SelectOrderByBuilder(this, column, type);
  }

  public SelectOrderByBuilder orderBy(Map<String, String> sorts) {
    return new SelectOrderByBuilder(this, sorts);
  }


  @Override
  public String build() {
    return join(" ", prefix.build(), "WHERE", conditions.toString());
  }
}
