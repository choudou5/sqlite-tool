package com.alexfu.sqlitequerybuilder.builder;

import com.alexfu.sqlitequerybuilder.utils.AssertUtil;
import com.alexfu.sqlitequerybuilder.utils.ToolkitUtil;

import java.util.Map;

public class SelectFromBuilder extends SegmentBuilder {

  private SelectBuilder prefix;
  private String[] tables;

  public SelectFromBuilder(SelectBuilder prefix, String... tables) {
    this.prefix = prefix;
    this.tables = tables;
  }

  public SelectWhereBuilder where(String condition) {
    AssertUtil.isNotNull(condition, "Condition cannot be null");
    return new SelectWhereBuilder(this, condition);
  }

  public SelectJoinBuilder join(String table) {
    AssertUtil.isNotNull(table, "Table cannot be null");
    return new SelectJoinBuilder(this, table, JoinType.JOIN);
  }

  public SelectJoinBuilder leftOuterJoin(String table) {
    AssertUtil.isNotNull(table, "Table cannot be null");
    return new SelectJoinBuilder(this, table, JoinType.LEFT_OUTER_JOIN);
  }

  public SelectJoinBuilder crossJoin(String table) {
    AssertUtil.isNotNull(table, "Table cannot be null");
    return new SelectJoinBuilder(this, table, JoinType.CROSS_JOIN);
  }

  public SelectJoinBuilder naturalJoin(String table) {
    AssertUtil.isNotNull(table, "Table cannot be null");
    return new SelectJoinBuilder(this, table, JoinType.NATURAL_JOIN);
  }

  public SelectJoinBuilder naturalLeftOuterJoin(String table) {
    AssertUtil.isNotNull(table, "Table cannot be null");
    return new SelectJoinBuilder(this, table, JoinType.NATURAL_LEFT_OUTER_JOIN);
  }

  public SelectOrderByBuilder orderBy(String column) {
    AssertUtil.isNotNull(column, "Column cannot be null");
    return new SelectOrderByBuilder(this, column);
  }

  public SelectOrderByBuilder orderBy(Map<String, String> sorts) {
    return new SelectOrderByBuilder(this, sorts);
  }


  public SelectGroupByBuilder groupBy(String column) {
    AssertUtil.isNotNull(column, "Column cannot be null");
    return new SelectGroupByBuilder(this, column);
  }

  public SelectLimitBuilder limit(int limit) {
    return new SelectLimitBuilder(this, limit);
  }

  @Override
  public String build() {
    return ToolkitUtil.join(" ", prefix.build(), "FROM", ToolkitUtil.join(",", tables));
  }
}
