package com.alexfu.sqlitequerybuilder.utils;

public class ToolkitUtil {

  public static String join(CharSequence delimiter, String... array) {
    return join(delimiter, (Object[]) array);
  }

  public static String join(CharSequence delimiter, Object... array) {
    StringBuilder sb = new StringBuilder();

    for (int i = 0, size = array.length; i < size; i++) {
      if (i > 0) {
        sb.append(delimiter);
      }
      sb.append(array[i]);
    }
    return sb.toString();
  }

  public static boolean isEmpty(String str) {
    return str == null || str.length() == 0;
  }

  public static boolean isNotEmpty(String str) {
    return !isEmpty(str);
  }

  public static boolean isEmpty(Object[] array) {
    return array == null || array.length == 0;
  }

  public static boolean isEmpty(long[] array) {
    return array == null || array.length == 0;
  }

  public static boolean isEmpty(int[] array) {
    return array == null || array.length == 0;
  }

  public static boolean isEmpty(short[] array) {
    return array == null || array.length == 0;
  }

  public static boolean isEmpty(char[] array) {
    return array == null || array.length == 0;
  }

  public static boolean isEmpty(byte[] array) {
    return array == null || array.length == 0;
  }

  public static boolean isEmpty(double[] array) {
    return array == null || array.length == 0;
  }

  public static boolean isEmpty(float[] array) {
    return array == null || array.length == 0;
  }

  public static boolean isEmpty(boolean[] array) {
    return array == null || array.length == 0;
  }

  public static boolean isNotEmpty(Object[] array) {
    return array != null && array.length != 0;
  }

  public static boolean isNotEmpty(long[] array) {
    return array != null && array.length != 0;
  }

  public static boolean isNotEmpty(int[] array) {
    return array != null && array.length != 0;
  }

  public static boolean isNotEmpty(short[] array) {
    return array != null && array.length != 0;
  }

  public static boolean isNotEmpty(char[] array) {
    return array != null && array.length != 0;
  }

  public static boolean isNotEmpty(byte[] array) {
    return array != null && array.length != 0;
  }

  public static boolean isNotEmpty(double[] array) {
    return array != null && array.length != 0;
  }

  public static boolean isNotEmpty(float[] array) {
    return array != null && array.length != 0;
  }

  public static boolean isNotEmpty(boolean[] array) {
    return array != null && array.length != 0;
  }


  public static boolean isBlank(String str) {
    int strLen;
    if (str == null || (strLen = str.length()) == 0) {
      return true;
    }
    for (int i = 0; i < strLen; i++) {
      if ((Character.isWhitespace(str.charAt(i)) == false)) {
        return false;
      }
    }
    return true;
  }

  public static boolean isNotBlank(String str) {
    return !isBlank(str);
  }


  public static boolean contains(String str, String searchStr) {
    if (str == null || searchStr == null) {
      return false;
    }
    return str.indexOf(searchStr) >= 0;
  }

  public static boolean contains(Object[] array, Object objectToFind) {
    return indexOf(array, objectToFind) != -1;
  }

  public static int indexOf(Object[] array, Object objectToFind) {
    return indexOf(array, objectToFind, 0);
  }

  public static int indexOf(Object[] array, Object objectToFind, int startIndex) {
    if(array == null) {
      return -1;
    } else {
      if(startIndex < 0) {
        startIndex = 0;
      }

      int i;
      if(objectToFind == null) {
        for(i = startIndex; i < array.length; ++i) {
          if(array[i] == null) {
            return i;
          }
        }
      } else if(array.getClass().getComponentType().isInstance(objectToFind)) {
        for(i = startIndex; i < array.length; ++i) {
          if(objectToFind.equals(array[i])) {
            return i;
          }
        }
      }

      return -1;
    }
  }

  public static boolean containsAny(String str, String searchChars) {
    if (searchChars == null) {
      return false;
    }
    return containsAny(str, searchChars.toCharArray());
  }

  public static boolean containsAny(String str, char[] searchChars) {
    if (isEmpty(str) || isEmpty(searchChars)) {
      return false;
    }
    int csLength = str.length();
    int searchLength = searchChars.length;
    int csLast = csLength - 1;
    int searchLast = searchLength - 1;
    for (int i = 0; i < csLength; i++) {
      char ch = str.charAt(i);
      for (int j = 0; j < searchLength; j++) {
        if (searchChars[j] == ch) {
          if (isHighSurrogate(ch)) {
            if (j == searchLast) {
              // missing low surrogate, fine, like String.indexOf(String)
              return true;
            }
            if (i < csLast && searchChars[j + 1] == str.charAt(i + 1)) {
              return true;
            }
          } else {
            // ch is in the Basic Multilingual Plane
            return true;
          }
        }
      }
    }
    return false;
  }

  static boolean isHighSurrogate(char ch) {
    return ('\uD800' <= ch && '\uDBFF' >= ch);
  }
}
