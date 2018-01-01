package com.alexfu.sqlitequerybuilder.utils;

import java.util.Date;

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
}
