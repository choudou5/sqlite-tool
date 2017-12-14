# sqlite工具集

## 查询构器
      String sql = SQLiteQueryBuilder.
      select("column1", "column2", "column3")
      .from("mytable")
      .build();

引用：https://github.com/alexfu/SQLiteQueryBuilder

## 语句执行器
      //插入
      StatementExecutor.insert(sql);
      //查询
      Map rowData = StatementExecutor.selectOne(sql);
      ......
