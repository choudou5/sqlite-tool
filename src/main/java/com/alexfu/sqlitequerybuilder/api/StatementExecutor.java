package com.alexfu.sqlitequerybuilder.api;

import com.alexfu.sqlitequerybuilder.utils.SqliteJdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 语句执行器
 * @author xuhaowen
 * @create 2017-12-下午 1:21
 **/
public class StatementExecutor<T> {


    public static int selectCount(String sql){
        Connection conn =  SqliteJdbcUtils.getConnection();
        Statement st = SqliteJdbcUtils.getStatement(conn);
        ResultSet rs = null;
        try {
            rs = st.executeQuery(sql);
            return getCount(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            SqliteJdbcUtils.close(conn, st, rs);
        }
        return 0;
    }

    public static Map selectOne(String sql){
        Connection conn =  SqliteJdbcUtils.getConnection();
        Statement st = SqliteJdbcUtils.getStatement(conn);
        ResultSet rs = null;
        try {
            rs = st.executeQuery(sql);
            return getRow(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            SqliteJdbcUtils.close(conn, st, rs);
        }
        return null;
    }

    public static List<Map> selectList(String sql){
        Connection conn =  SqliteJdbcUtils.getConnection();
        Statement st = SqliteJdbcUtils.getStatement(conn);
        ResultSet rs = null;
        try {
            rs = st.executeQuery(sql);
            return getRows(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            SqliteJdbcUtils.close(conn, st, rs);
        }
        return null;
    }

    public static long insert(String sql){
        return execute(sql, true);
    }

    public static long batchInsert(String ... sql){
        Connection conn =  SqliteJdbcUtils.getConnection();
        return executeBatch(conn, true, sql);
    }

    public static long update(String sql){
        return execute(sql, true);
    }

    public static long delete(String sql){
        return execute(sql, true);
    }

    private static long execute(String sql, boolean commit){
        Connection conn =  SqliteJdbcUtils.getConnection();
        return execute(conn, sql, commit);
    }

    private static long execute(Connection conn, String sql, boolean commit){
        Statement st = null;
        try {
            conn.setAutoCommit(false);
            st = SqliteJdbcUtils.getStatement(conn);
            st.executeUpdate(sql);
            if(commit)
                conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            SqliteJdbcUtils.close(conn, st);
        }
        return 0;
    }

    private static Map getRowData(ResultSet rs, int columnCount, ArrayList<String> columns, ArrayList<String> types) throws SQLException {
        Map<String, Object> map = new LinkedHashMap<>();
        for (int i = 0; i < columnCount; i++) {
            if (types.get(i).equals("text")) {
                map.put(columns.get(i), rs.getString(columns.get(i)));
            }
            else if (types.get(i).equals("float")) {
                map.put(columns.get(i), rs.getDouble(columns.get(i)));
            }
            else if (types.get(i).equals("integer")) {
                map.put(columns.get(i), rs.getInt(columns.get(i)));
            }
            else if (types.get(i).equals("blob")) {
                map.put(columns.get(i), rs.getBlob(columns.get(i)));
            }
            else if (types.get(i).equals("null")) {
                map.put(columns.get(i), null);
            }
        }
        return map;
    }


    public static long executeBatch(Connection conn, boolean commit, String ... sqls){
        Statement st = null;
        try {
            conn.setAutoCommit(false);
            st = SqliteJdbcUtils.getStatement(conn);
            for (String sql : sqls) {
                st.addBatch(sql);
            }
            st.executeBatch();
            if(commit)
                conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            SqliteJdbcUtils.close(conn, st);
        }
        return 0;
    }

    private static int getCount(ResultSet rs) throws SQLException {
        int count = 0;
        while (rs.next()) {
            count = rs.getInt(1);
        }
        return count;
    }

    private static Map getRow(ResultSet rs) throws SQLException{
        List<Map> list = getRows(rs);
        return list!=null?list.get(0):null;
    }

    private static List<Map> getRows(ResultSet rs) throws SQLException{
        List<Map> list = new ArrayList<>();
        try {
            int columnCount = rs.getMetaData().getColumnCount();
            ArrayList<String> columns = new ArrayList<String>();
            ArrayList<String> types = new ArrayList<String>();
            for (int i = 1; i <= columnCount; i++) {
                columns.add(rs.getMetaData().getColumnName(i));
                types.add(rs.getMetaData().getColumnTypeName(i));
            }
            while (rs.next()) {
                list.add(getRowData(rs, columnCount, columns, types));
            }
        } catch (SQLException e) {
            throw e;
        }
        return list;
    }
}
