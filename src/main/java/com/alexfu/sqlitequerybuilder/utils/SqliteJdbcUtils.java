package com.alexfu.sqlitequerybuilder.utils;

import java.sql.*;

/**
 * 数据库 JDBC工具类
 * @author xuhaowen
 * @create 2017-12-下午 1:36
 **/
public class SqliteJdbcUtils extends SqliteJdbc {

    private static String dbPath = null;
    static{
        try {
            Class.forName("org.sqlite.JDBC");
        } catch ( Exception e ) {
            throw new BizException("系统找不到org.sqlite.JDBC类");
        }
    }

    public static void init(String dbPath){
        SqliteJdbcUtils.dbPath = dbPath;
    }

    public static Connection getConnection(){
        Connection conn = null;
        try {
            AssertUtil.isNotNull(dbPath, "系统未调用SqliteJdbcUtils类 init()");
            conn = DriverManager.getConnection("jdbc:sqlite:"+dbPath);
        } catch ( Exception e ) {
            e.printStackTrace();
            throw new BizException("SqliteJdbcUtils类getConnection()失败.");
        }
        return conn;
    }

}
