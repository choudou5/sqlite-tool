package com.alexfu.sqlitequerybuilder;

import com.alexfu.sqlitequerybuilder.api.SQLiteQueryBuilder;
import com.alexfu.sqlitequerybuilder.utils.AssertUtil;
import org.junit.Test;

/**
 *
 */
public class UpdateTest {
  @Test
  public void testUpdate() {
    String sql = SQLiteQueryBuilder
      .update()
      .table("my_table")
      .set("name='xxx'")
      .where("id = 1")
      .build();

    AssertUtil.assertEqual(sql,"UPDATE my_table SET name='xxx'  WHERE id = 1");
  }

  @Test
  public void testUpdate2() {
    String sql = SQLiteQueryBuilder
            .update()
            .table("my_table")
            .set("name='xxx'")
            .set("age=11")
            .where("id = 1")
            .build();

    AssertUtil.assertEqual(sql,"UPDATE my_table SET name='xxx', age=11  WHERE id = 1");
  }
}
