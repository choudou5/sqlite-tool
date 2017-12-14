package com.alexfu.sqlitequerybuilder;

import com.alexfu.sqlitequerybuilder.utils.AssertUtil;
import com.alexfu.sqlitequerybuilder.utils.ToolkitUtil;
import org.junit.Test;


public class StrUtilsTest {
  @Test
  public void joinsStringsWithCommaDelimeter() {
    String[] strings = {"One", "Two", "Three", "Four", "Five"};

    String expected = "One,Two,Three,Four,Five";
    String result = ToolkitUtil.join(",", strings);
    AssertUtil.assertEqual(expected, result);
  }
}
