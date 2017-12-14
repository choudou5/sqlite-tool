package com.alexfu.sqlitequerybuilder;

import com.alexfu.sqlitequerybuilder.utils.AssertUtil;
import org.junit.Assert;
import org.junit.Test;


import com.alexfu.sqlitequerybuilder.api.SQLiteQueryBuilder;

public class DropTest {

  @Test
  public final void testDropTable() {
    // Arrange and Act
    String statement = SQLiteQueryBuilder
      .drop()
      .table("myTable")
      .ifExists()
      .toString();

    // Assert
    AssertUtil.assertEqual(statement, "DROP TABLE IF EXISTS myTable");
  }

  @Test
  public final void testDropTableNoIfExists() {
    // Arrange and Act
    String statement = SQLiteQueryBuilder
      .drop()
      .table("myTable")
      .toString();

    // Assert
    AssertUtil.assertEqual(statement,"DROP TABLE myTable");
  }

  @Test
  public final void testDropView() {
    // Arrange and Act
    String statement = SQLiteQueryBuilder
      .drop()
      .view("myView")
      .ifExists()
      .toString();

    // Assert
    AssertUtil.assertEqual(statement,"DROP VIEW IF EXISTS myView");
  }

  @Test
  public final void testDropViewNoIfExists() {
    // Arrange and Act
    String statement = SQLiteQueryBuilder
      .drop()
      .view("myView")
      .toString();

    // Assert
    AssertUtil.assertEqual(statement,"DROP VIEW myView");
  }

  @Test
  public final void testDropIndex() {
    // Arrange and Act
    String statement = SQLiteQueryBuilder
      .drop()
      .index("myIndex")
      .ifExists()
      .toString();

    // Assert
    AssertUtil.assertEqual(statement,"DROP INDEX IF EXISTS myIndex");
  }

  @Test
  public final void testDropIndexNoIfExists() {
    // Arrange and Act
    String statement = SQLiteQueryBuilder
      .drop()
      .index("myIndex")
      .toString();

    // Assert
    AssertUtil.assertEqual(statement,"DROP INDEX myIndex");
  }

  @Test
  public final void testDropTrigger() {
    // Arrange and Act
    String statement = SQLiteQueryBuilder
      .drop()
      .trigger("myTrigger")
      .ifExists()
      .toString();

    // Assert
    AssertUtil.assertEqual(statement,"DROP TRIGGER IF EXISTS myTrigger");
  }

  @Test
  public final void testDropTriggerNoIfExists() {
    // Arrange and Act
    String statement = SQLiteQueryBuilder
      .drop()
      .trigger("myTrigger")
      .toString();

    // Assert
    AssertUtil.assertEqual(statement,"DROP TRIGGER myTrigger");
  }
}
