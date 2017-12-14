# sqlite工具集

## 查询构器
eg:
String query = SQLiteQueryBuilder.
      select("column1", "column2", "column3")
      .from("mytable")
      .build();

引用：https://github.com/alexfu/SQLiteQueryBuilder

## 语句执行器
      StatementExecutor.insert(sql);
      Map rowData = StatementExecutor.selectOne(sql);
