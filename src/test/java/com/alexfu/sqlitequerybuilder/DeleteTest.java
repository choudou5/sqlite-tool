package com.alexfu.sqlitequerybuilder;

import com.alexfu.sqlitequerybuilder.api.SQLiteQueryBuilder;
import com.alexfu.sqlitequerybuilder.utils.AssertUtil;
import org.junit.Test;

/**
 * Created by alexfu on 7/7/15.
 */
public class DeleteTest {
  @Test
  public void testDeleteAll() {
    String sql = SQLiteQueryBuilder
      .delete()
      .from("my_table")
      .build();

  }

  @Test
  public void testDeleteWhere() {
    String sql = SQLiteQueryBuilder
      .delete()
      .from("my_table")
      .where("id = 1")
      .build();

    AssertUtil.assertEqual(sql,"DELETE FROM my_table WHERE id = 1");
  }
}
