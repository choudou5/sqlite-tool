package com.alexfu.sqlitequerybuilder;

import com.alexfu.sqlitequerybuilder.api.SQLiteQueryBuilder;
import com.alexfu.sqlitequerybuilder.api.StatementExecutor;
import com.alexfu.sqlitequerybuilder.utils.AssertUtil;
import com.alexfu.sqlitequerybuilder.utils.DateUtils;
import org.junit.Test;

import java.util.Calendar;
import java.util.Map;

public class InsertTest {

  @Test
  public void insertWithGoodValues() throws Exception {
    Calendar timestamp = Calendar.getInstance();
    String sql = SQLiteQueryBuilder.insert()
      .into("people")
      .columns("id", "name", "timestamp")
      .values(1, "John", Calendar.getInstance())
      .build();

    String iso8601 = DateUtils.iso8601().format(timestamp.getTime());
    String result = "INSERT INTO people (id,name,timestamp) values (1,'John','" + iso8601 + "')";
    AssertUtil.assertEqual(sql, result);

    StatementExecutor.insert(sql);
    Map rowData = StatementExecutor.selectOne(sql);
  }

  @Test(expected = IllegalArgumentException.class)
  public void insertWithNullColumns() {
    SQLiteQueryBuilder.insert()
      .into("people")
      .columns(null)
      .values(1, "John", Calendar.getInstance())
      .build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void insertWithNullValues() {
    SQLiteQueryBuilder.insert()
      .into("people")
      .columns("id", "name", "timestamp")
      .values(null)
      .build();
  }
}
