package com.alexfu.sqlitequerybuilder.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
  private static DateFormat ISO8601;
  private static DateFormat DATE_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public static DateFormat iso8601() {
    if (ISO8601 == null) {
      ISO8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
    }
    return ISO8601;
  }

  public static String toDateTime(Date date) {
     return DATE_TIME.format(date);
  }

}
