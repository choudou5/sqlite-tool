package com.alexfu.sqlitequerybuilder.builder;

import com.alexfu.sqlitequerybuilder.api.Builder;
import com.alexfu.sqlitequerybuilder.utils.ToolkitUtil;

import java.util.Map;

public class SelectOrderByBuilder extends SegmentBuilder {

  private Builder prefix;
  private StringBuilder orderStr = new StringBuilder();

  public enum OrderType{
    ASC,
    DESC
  }

  public SelectOrderByBuilder(Builder prefix, String column) {
    this(prefix, column, OrderType.DESC);
  }

  public SelectOrderByBuilder(Builder prefix, String column, OrderType type) {
    this.prefix = prefix;
    this.orderStr.append(column+" "+ type.name());
  }

  public SelectOrderByBuilder(Builder prefix, Map<String, String> sorts) {
    this.prefix = prefix;
    if(sorts != null && sorts.size() > 0){
      int i = 0;
      for (String column : sorts.keySet()) {
        this.orderStr.append((i>0?", ":"")+column+" "+ sorts.get(column));
        i++;
      }
    }
  }

  public SelectOrderByBuilder asc(String column) {
    this.orderStr.append(", "+column+" "+ OrderType.ASC.name());
    return this;
  }

  public SelectOrderByBuilder desc(String column) {
    this.orderStr.append(", "+column+" "+ OrderType.DESC.name());
    return this;
  }

  public SelectLimitBuilder rows(int rows) {
    return new SelectLimitBuilder(this, rows);
  }



  @Override
  public String build() {
    return ToolkitUtil.join(" ", prefix.build(), "ORDER BY", orderStr.toString());
  }
}
