package com.alexfu.sqlitequerybuilder.builder;

import com.alexfu.sqlitequerybuilder.utils.StrUtils;

public class SelectOrderTermBuilder extends SegmentBuilder {

  public enum Term {
    ASC, DESC
  }

  private SelectOrderByBuilder prefix;
  private Term term;

  public SelectOrderTermBuilder(SelectOrderByBuilder prefix, Term term) {
    this.prefix = prefix;
    this.term = term;
  }

  public SelectLimitBuilder limit(int limit) {
    return new SelectLimitBuilder(this, limit);
  }

  @Override
  public String build() {
    return StrUtils.join(" ", prefix.build(), term.toString());
  }
}
