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

## maven配置
    //1、将sqlite-tool 项目clone到本地工程
    //2、安装到本地私服-->执行maven命令：mvn clean -U compile install -Dmaven.test.skip=true
    //3、集成依赖到maven项目 pom.xml
    <dependency>
        <groupId>com.choudoufu.tool</groupId>
        <artifactId>choudoufu.tool.sqlite</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>