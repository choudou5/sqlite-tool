package com.alexfu.sqlitequerybuilder.utils;

import java.sql.*;

/**
 * 数据库
 * @author xuhaowen
 * @create 2017-12-下午 1:36
 **/
public class SqliteJdbcUtils {

    static{
        try {
            Class.forName("org.sqlite.JDBC");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:test.db");
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

    public static void main( String args[] ){

    }
}
