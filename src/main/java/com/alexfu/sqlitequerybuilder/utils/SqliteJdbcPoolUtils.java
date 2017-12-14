package com.alexfu.sqlitequerybuilder.utils;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据库
 * @author xuhaowen
 * @create 2017-12-下午 1:36
 **/
public class SqliteJdbcPoolUtils {

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
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        return conn;
    }

    public static Statement getStatement(Connection conn){
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stmt;
    }

    public static void close(Connection conn, Statement st){
        close(conn, st, null);
    }

    public static void close(Connection conn, Statement st, ResultSet rs){
        try {
            if(rs != null) rs.close();
            if(st != null) st.close();
            if(conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
