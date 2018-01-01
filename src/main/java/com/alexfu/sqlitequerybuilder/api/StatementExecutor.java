package com.alexfu.sqlitequerybuilder.api;

import com.alexfu.sqlitequerybuilder.entity.ColumnEntity;
import com.alexfu.sqlitequerybuilder.utils.SqliteJdbcUtils;
import com.alexfu.sqlitequerybuilder.utils.ToolkitUtil;

import java.sql.*;
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

    public static List<String> selectFieldNames(String table){
        Connection conn =  SqliteJdbcUtils.getConnection();
        Statement st = SqliteJdbcUtils.getStatement(conn);
        ResultSet rs = null;
        try {
            rs = st.executeQuery("SELECT * FROM "+table+" limit 1");
            return getColumnNames(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            SqliteJdbcUtils.close(conn, st, rs);
        }
        return null;
    }

    public static List<ColumnEntity> selectFieldEntitys(String table){
        Connection conn =  SqliteJdbcUtils.getConnection();
        Statement st = SqliteJdbcUtils.getStatement(conn);
        ResultSet rs = null;
        try {
            rs = st.executeQuery("SELECT * FROM "+table+" limit 1");
            return getColumns(table, rs, conn.getMetaData());
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            SqliteJdbcUtils.close(conn, st, rs);
        }
        return null;
    }


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
        return ToolkitUtil.isNotEmpty(list)?list.get(0):null;
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

    private static List<String> getColumnNames(ResultSet rs) throws SQLException{
        List<String> list = new ArrayList<>();
        ResultSetMetaData rsmd = rs.getMetaData(); //获取字段名
        if(rsmd != null){
            int count  = rsmd.getColumnCount();
            for(int i=1;i<=count;i++){
                list.add(rsmd.getColumnName(i));
            }
        }
        return list;
    }

    private static List<ColumnEntity> getColumns(String table, ResultSet rs, DatabaseMetaData meta) throws SQLException{
        List<ColumnEntity> list = new ArrayList<>();
        List<String> pkNames = new ArrayList<>(5);
        //得到主键集合
        ResultSet primaryKey = meta.getPrimaryKeys(null, null, table);
        while (primaryKey.next()) {
            pkNames.add(primaryKey.getString("COLUMN_NAME"));
        }
        primaryKey.close();

        ResultSetMetaData rsmd = rs.getMetaData(); //获取字段名
        if(rsmd != null){
            int count  = rsmd.getColumnCount();
            for(int i=1;i<=count;i++){
                ColumnEntity column = new ColumnEntity();
                column.setColumnName(rsmd.getColumnName(i))
                        .setColumnLabel(rsmd.getColumnLabel(i))
                        .setColumnTypeName(rsmd.getColumnTypeName(i))
                        .setIsAutoIncrement(rsmd.isAutoIncrement(i))
                        .setIsNullable(rsmd.isNullable(i)==1);
                if(pkNames.contains(column.getColumnName()))
                    column.setIsPk(true);
                list.add(column);
            }
        }
        return list;
    }


}
