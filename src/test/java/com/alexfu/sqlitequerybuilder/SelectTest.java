package com.alexfu.sqlitequerybuilder;

import com.alexfu.sqlitequerybuilder.api.SQLiteQueryBuilder;
import com.alexfu.sqlitequerybuilder.api.SelectType;
import com.alexfu.sqlitequerybuilder.builder.SelectWhereBuilder;
import com.alexfu.sqlitequerybuilder.utils.AssertUtil;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class SelectTest {
  @Test
  public void singleColumnSelectTest() {
    String query = SQLiteQueryBuilder
      .select("column")
      .from("mytable")
      .build();

    AssertUtil.assertEqual(query,"SELECT column FROM mytable");
  }

  @Test
  public void multiColumnSelectTest() {
    String query = SQLiteQueryBuilder.
      select("column1", "column2", "column3")
      .from("mytable")
      .build();

    AssertUtil.assertEqual(query,"SELECT column1,column2,column3 FROM mytable");
  }

  @Test
  public void starColumnSelectTest() {
    String query = SQLiteQueryBuilder
      .select("*")
      .from("mytable")
      .build();

    AssertUtil.assertEqual(query,"SELECT * FROM mytable");
  }


  @Test
  public void starColumnSelectWhereTest() {
    SelectWhereBuilder where = SQLiteQueryBuilder
            .select("*")
            .from("mytable").where("1==1");
    for (int i = 0; i < 3; i++) {
      where.and("k="+i);
    }
    System.out.println(where.build());

  }

  @Test
  public void selectWhereTest() {
    String query = SQLiteQueryBuilder
      .select("column")
      .from("mytable")
      .where("id = 1")
      .build();

    AssertUtil.assertEqual(query,"SELECT column FROM mytable WHERE id = 1");
  }

  @Test
  public void selectJoinTest() {
    String query = SQLiteQueryBuilder
      .select("*")
      .from("mytable")
      .join("secondtable")
      .on("mytable.id = secondtable.id")
      .where("id = 1")
      .build();

    AssertUtil.assertEqual(query,"SELECT * FROM mytable JOIN secondtable ON "
      + "mytable.id = secondtable.id "
      + "WHERE id = 1");
  }

  @Test
  public void selectLeftOuterJoinTest() {
    String query = SQLiteQueryBuilder
      .select("*")
      .from("mytable")
      .leftOuterJoin("secondtable")
      .on("mytable.id = secondtable.id")
      .where("id = 1")
      .build();

    AssertUtil.assertEqual(query,"SELECT * FROM mytable LEFT OUTER JOIN secondtable ON "
      + "mytable.id = secondtable.id WHERE id = 1");
  }

  @Test
  public void selectCrossJoinTest() {
    String query = SQLiteQueryBuilder
      .select("*")
      .from("mytable")
      .crossJoin("secondtable")
      .build();

    AssertUtil.assertEqual(query,"SELECT * FROM mytable CROSS JOIN secondtable");
  }

  @Test
  public void selectNaturalJoinTest() {
    String query = SQLiteQueryBuilder
      .select("*")
      .from("mytable")
      .naturalJoin("secondtable")
      .build();

    AssertUtil.assertEqual(query,"SELECT * FROM mytable NATURAL JOIN secondtable");
  }

  @Test
  public void selectNaturalLeftOuterJoinTest() {
    String query = SQLiteQueryBuilder
      .select("*")
      .from("mytable")
      .naturalLeftOuterJoin("secondtable")
      .build();

    AssertUtil.assertEqual(query,"SELECT * FROM mytable NATURAL LEFT OUTER JOIN secondtable");
  }

  @Test
  public void selectMultiJoinTest() {
    String query = SQLiteQueryBuilder
      .select("*")
      .from("mytable")
      .join("secondtable")
      .on("mytable.id = secondtable.id")
      .join("thirdtable")
      .on("secondtable.id = thirdtable.id")
      .where("id = 1")
      .build();

    AssertUtil.assertEqual(query,"SELECT * FROM mytable JOIN secondtable ON "
      + "mytable.id = secondtable.id "
      + "JOIN thirdtable ON secondtable.id = thirdtable.id WHERE id = 1");
  }

  @Test
  public void selectAndTest() {
    String query = SQLiteQueryBuilder
      .select("*")
      .from("mytable")
      .where("id = 1")
      .and("color = 'red'")
      .build();

    AssertUtil.assertEqual(query,"SELECT * FROM mytable WHERE id = 1 AND color = 'red'");
  }

  @Test
  public void selectAndAndTest() {
    String query = SQLiteQueryBuilder
      .select("*")
      .from("mytable")
      .where("id = 1")
      .and("color = 'red'")
      .and("color = 'blue'")
      .build();

    AssertUtil.assertEqual(query,"SELECT * FROM mytable WHERE id = 1 AND color = 'red' " +
      "AND color = 'blue'");
  }

  @Test
  public void selectWhereLimitTest() {
    String query = SQLiteQueryBuilder
      .select("*")
      .from("mytable")
      .where("id = 1")
      .rows(5)
      .build();

    AssertUtil.assertEqual(query,"SELECT * FROM mytable WHERE id = 1 LIMIT 5");
  }

  @Test
  public void selectLimitTest() {
    String query = SQLiteQueryBuilder
      .select("*")
      .from("mytable")
      .limit(5)
      .build();

    AssertUtil.assertEqual(query,"SELECT * FROM mytable LIMIT 5");
  }

  @Test
  public void selectOrderByTest() {
    Map<String, String> sorts = new LinkedHashMap<String, String>();
    sorts.put("a", "asc");
    String query = SQLiteQueryBuilder
      .select("*")
      .from("mytable")
      .orderBy(sorts)
//      .orderBy("rank")
      .build();

    AssertUtil.assertEqual(query,"SELECT * FROM mytable ORDER BY rank");
  }

  @Test
  public void selectGroupByTest() {
    String query = SQLiteQueryBuilder
      .select("*")
      .from("mytable")
      .groupBy("rank")
      .build();

    AssertUtil.assertEqual(query,"SELECT * FROM mytable GROUP BY rank");
  }

  @Test
  public void selectGroupByHavingOrderTest() {
    String query = SQLiteQueryBuilder
      .select("*")
      .from("mytable")
      .groupBy("rank")
      .having("age > 0")
      .orderBy("rank")
      .build();

    AssertUtil.assertEqual(query,"SELECT * FROM mytable GROUP BY rank HAVING age > 0 ORDER BY rank");
  }

  @Test
  public void selectGroupByHavingLimitTest() {
    String query = SQLiteQueryBuilder
      .select("*")
      .from("mytable")
      .groupBy("rank")
      .having("age > 0")
      .rows(3)
      .build();

    AssertUtil.assertEqual(query,"SELECT * FROM mytable GROUP BY rank HAVING age > 0 LIMIT 3");
  }

  @Test
  public void selectOrderByAdvancedTest() {
    String query = SQLiteQueryBuilder
      .select("*")
      .from("mytable")
      .where("id = 1")
      .orderBy("rank")
      .rows(10)
      .start(5)
      .build();

    AssertUtil.assertEqual(query,"SELECT * FROM mytable WHERE id = 1 ORDER BY rank DESC LIMIT 10 "
      + "OFFSET 5");
  }

  @Test
  public void selectDistinctTest() {
    String query = SQLiteQueryBuilder
      .select(SelectType.DISTINCT("one", "two", "three"))
      .from("mytable")
      .build();

    AssertUtil.assertEqual(query,"SELECT DISTINCT one,two,three FROM mytable");
  }

  @Test
  public void selectAllTest() {
    String query = SQLiteQueryBuilder
      .select(SelectType.ALL("one", "two", "three"))
      .from("mytable")
      .build();

    AssertUtil.assertEqual(query,"SELECT ALL one,two,three FROM mytable");
  }

  @Test
  public void selectOrderByAsc() {
    String query = SQLiteQueryBuilder
      .select("*")
      .from("mytable")
      .orderBy("age")
      .build();

    AssertUtil.assertEqual(query,"SELECT * FROM mytable ORDER BY age ASC");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullArgs() {
    SQLiteQueryBuilder
      .select("*")
      .from(null)
      .build();
  }
}
