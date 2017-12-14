package com.alexfu.sqlitequerybuilder.utils;

import java.sql.*;

/**
 * 数据库 JDBC工具类
 * @author xuhaowen
 * @create 2017-12-下午 1:36
 **/
public class SqliteJdbc {

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

}
