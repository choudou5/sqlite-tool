package com.alexfu.sqlitequerybuilder.api;

import com.alexfu.sqlitequerybuilder.entity.ColumnEntity;
import com.alexfu.sqlitequerybuilder.utils.BizException;
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

    public static List<String> selectFieldNames(String table) throws Exception{
        Connection conn =  SqliteJdbcUtils.getConnection();
        Statement st = SqliteJdbcUtils.getStatement(conn);
        ResultSet rs = null;
        try {
            rs = st.executeQuery("SELECT * FROM "+table+" limit 1");
            return getColumnNames(rs);
        } catch (SQLException e) {
            throw e;
        }finally {
            SqliteJdbcUtils.close(conn, st, rs);
        }
    }

    public static List<ColumnEntity> selectFieldEntitys(String table) throws Exception{
        Connection conn =  SqliteJdbcUtils.getConnection();
        Statement st = SqliteJdbcUtils.getStatement(conn);
        ResultSet rs = null;
        try {
            rs = st.executeQuery("SELECT * FROM "+table+" limit 1");
            return getColumns(table, rs, conn.getMetaData());
        } catch (SQLException e) {
            throw e;
        }finally {
            SqliteJdbcUtils.close(conn, st, rs);
        }
    }


    public static int selectCount(String sql) throws Exception{
        Connection conn =  SqliteJdbcUtils.getConnection();
        Statement st = SqliteJdbcUtils.getStatement(conn);
        ResultSet rs = null;
        try {
            rs = st.executeQuery(sql);
            return getCount(rs);
        } catch (SQLException e) {
            throw e;
        }finally {
            SqliteJdbcUtils.close(conn, st, rs);
        }
    }

    public static Map selectOne(String sql) throws Exception{
        Connection conn =  SqliteJdbcUtils.getConnection();
        Statement st = SqliteJdbcUtils.getStatement(conn);
        ResultSet rs = null;
        try {
            rs = st.executeQuery(sql);
            return getRow(rs);
        } catch (SQLException e) {
            throw e;
        }finally {
            SqliteJdbcUtils.close(conn, st, rs);
        }
    }

    public static List<Map> selectList(String sql) throws Exception{
        Connection conn =  SqliteJdbcUtils.getConnection();
        Statement st = SqliteJdbcUtils.getStatement(conn);
        ResultSet rs = null;
        try {
            rs = st.executeQuery(sql);
            return getRows(rs);
        } catch (SQLException e) {
            throw e;
        }finally {
            SqliteJdbcUtils.close(conn, st, rs);
        }
    }

    public static long insert(String sql) throws Exception{
        return execute(sql, true);
    }

    public static long batchInsert(String ... sql) throws Exception{
        Connection conn =  SqliteJdbcUtils.getConnection();
        return executeBatch(conn, true, sql);
    }

    public static long update(String sql) throws Exception{
        return execute(sql, true);
    }

    public static long delete(String sql) throws Exception{
        return execute(sql, true);
    }

    private static long execute(String sql, boolean commit) throws Exception{
        Connection conn =  SqliteJdbcUtils.getConnection();
        return execute(conn, sql, commit);
    }

    private static long execute(Connection conn, String sql, boolean commit) throws Exception{
        Statement st = null;
        try {
            conn.setAutoCommit(false);
            st = SqliteJdbcUtils.getStatement(conn);
            st.executeUpdate(sql);
            if(commit)
                conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
                throw e;
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            SqliteJdbcUtils.close(conn, st);
        }
        return 0;
    }

    private static Map getRowData(ResultSet rs, int columnCount, ArrayList<String> columns, ArrayList<String> types) throws Exception {
        Map<String, Object> map = new LinkedHashMap<>();
        String type = null;
        for (int i = 0; i < columnCount; i++) {
            type = types.get(i).trim().toUpperCase();
            if (type.equals("CHAR") || type.equals("VARCHAR") || type.equals("TEXT")) {
                map.put(columns.get(i), rs.getString(columns.get(i)));
            }
            else if (type.equals("INT") || type.equals("INTEGER") || type.equals("TINYINT")) {
                map.put(columns.get(i), rs.getInt(columns.get(i)));
            }
            else if (type.equals("FLOAT")) {
                map.put(columns.get(i), rs.getFloat(columns.get(i)));
            }
            else if (type.equals("BIGINT")) {
                map.put(columns.get(i), rs.getLong(columns.get(i)));
            }
            else if (type.equals("DOUBLE")) {
                map.put(columns.get(i), rs.getDouble(columns.get(i)));
            }
            else if (type.equals("NUMERIC") || type.equals("DECIMAL")) {
                map.put(columns.get(i), rs.getBigDecimal(columns.get(i)));
            }
            else if (type.equals("BLOB")) {
                map.put(columns.get(i), rs.getBlob(columns.get(i)));
            }
            else if (type.equals("DATETIME") || type.equals("DATE")) {
                map.put(columns.get(i), rs.getDate(columns.get(i)));
            }
            else if (type.equals("BOOLEAN")) {
                map.put(columns.get(i), rs.getBoolean(columns.get(i)));
            }
            else{
                throw new BizException("不支持 "+type+" 类型数据");
            }
        }
        return map;
    }


    public static long executeBatch(Connection conn, boolean commit, String ... sqls) throws Exception{
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
            try {
                conn.rollback();
                throw e;
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            SqliteJdbcUtils.close(conn, st);
        }
        return 0;
    }

    private static int getCount(ResultSet rs) throws Exception {
        int count = 0;
        while (rs.next()) {
            count = rs.getInt(1);
        }
        return count;
    }

    private static Map getRow(ResultSet rs) throws Exception{
        List<Map> list = getRows(rs);
        return ToolkitUtil.isNotEmpty(list)?list.get(0):null;
    }

    private static List<Map> getRows(ResultSet rs) throws Exception{
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

    private static List<ColumnEntity> getColumns(String table, ResultSet rs, DatabaseMetaData meta) throws Exception{
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
