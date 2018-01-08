package com.alexfu.sqlitequerybuilder.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Desc:
 * User: xuhaowende
 * Time: 2018/1/1
 */
public class SqliteSqlUtils {

    public static String warpSetValue(Object object){
        if(object instanceof Date){
            return "'"+DateUtils.toDateTime((Date)object)+"'";
        }else{
            return "'"+object.toString()+"'";
        }
    }

    public static void main(String[] args) throws IOException {
        List<String> sqlLines = getTestFileContentLines("project.sql");
        String sql = mysqlToSqlite(sqlLines);
        System.out.println(sql);
    }


    public static String toSqliteSql(String type, List<String> sqlLines){
        String result = null;
        switch (type){
            case "mysql":
                result = mysqlToSqlite(sqlLines);
                break;
        }
        return result;
    }

    /**
     * mysql转sqlite
     * @param sqlLines
     * @return
     */
    private static String mysqlToSqlite(List<String> sqlLines){
        StringBuilder sb = new StringBuilder(256);
        boolean existKey = false;
        for (String line : sqlLines) {
            line = line.toLowerCase();
            int dex = line.indexOf("comment '");//评论
            int end = line.indexOf("engine=");//结尾
            int key = line.indexOf("key ");//索引
            if(dex != -1){
                sb.append(line.substring(0, dex));
                if(line.endsWith(",")){
                    sb.append(",");
                }
                sb.append("\r\n");
            }else if(end != -1){
                if(existKey)
                    sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(line.substring(0, end));
                sb.append(";\r\n");
            }else if(key != -1){
                existKey = true;
                continue;
            }else{
                sb.append(line+"\r\n");
            }
        }
        return sb.toString();
    }


    private static List<String> getTestFileContentLines(String fileBasePath) throws IOException {
        String classes = SqliteSqlUtils.class.getResource("/").getPath();
        String testClasses = new File(classes).getParentFile().getPath();
        File file = new File(testClasses+"\\test-classes\\"+fileBasePath);
        FileReader fr = new FileReader(file);
        BufferedReader bf = new BufferedReader(fr);
        List<String> sqlLines = new ArrayList<>();
        String line = null;
        while((line = bf.readLine()) != null){
            sqlLines.add(line);
        }
        bf.close();
        fr.close();
        return sqlLines;
    }

}
