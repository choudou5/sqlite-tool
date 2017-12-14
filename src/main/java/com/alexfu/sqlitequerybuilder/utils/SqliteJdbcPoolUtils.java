package com.alexfu.sqlitequerybuilder.utils;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据库 JDBC工具类
 * @author xuhaowen
 * @create 2017-12-下午 1:36
 **/
public class SqliteJdbcPoolUtils extends SqliteJdbc {

    /**
     * 数据源
     */
    private BasicDataSource dataSource;
    public BasicDataSource getDataSource() {
        return dataSource;
    }
    public void setDataSource(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static void main(String[] args) {
        //1、Spring方式获取 连接
//        SqliteJdbcPoolUtils databaseUtil = (SqliteJdbcPoolUtils)SpringContentHolder.getBean("sqliteJdbcPoolUtils");
//        Connection conn = databaseUtil.getConnection();

        //2、单例模式获取
        Connection conn = getConnectionBySingleton();
    }

    /**
     * 获得连接 （单例模式）
     * @return
     */
    public static Connection getConnectionBySingleton(){
        return SqliteJdbcPoolUtils.getInstance().getConnection();
    }

    public Connection getConnection(){
        Connection conn = null;
        try {
            AssertUtil.isNotNull(dataSource, "数据源为空!");
             conn = dataSource.getConnection();
        } catch ( Exception e ) {
            e.printStackTrace();
            throw new BizException("SqliteJdbcPoolUtils类getConnection()失败.");
        }
        return conn;
    }


    private SqliteJdbcPoolUtils(){}

    /**
     * 内部类，用于实现lzay机制
     */
    private static class SingletonHolder {
        /** 单例变量  */
        private static SqliteJdbcPoolUtils instance = new SqliteJdbcPoolUtils();
    }

    /**
     * 获取单例对象实例
     * @return 单例对象
     */
    private static SqliteJdbcPoolUtils getInstance(){
        return(SingletonHolder.instance);
    }
}
