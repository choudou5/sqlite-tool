package com.alexfu.sqlitequerybuilder;

import com.alexfu.sqlitequerybuilder.utils.ToolkitUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StrUtilsTest {
  @Test
  public void joinsStringsWithCommaDelimeter() {
    String[] strings = {"One", "Two", "Three", "Four", "Five"};

    String expected = "One,Two,Three,Four,Five";
    String result = ToolkitUtil.join(",", strings);
    assertEquals(expected, result);
  }
}
