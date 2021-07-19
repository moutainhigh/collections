package com.gwssi.common.util;

import java.util.ArrayList;
import java.util.List;

/**
* 用于处理String 分隔
*@author lifx,zuojy
*/
public class StringTokenizer {
  //域列表
  protected List listField = null;
  //读取位置指针
  protected int intPointer = 0;

  /**
   * 名称：
   * 功能：
   * 输入参数：
   * 返回参数：
   */
  public StringTokenizer(String fields)  throws Exception {
    try {
      //使用缺省的分隔符初始化域列表
      init(fields, "&&");
    } catch(Exception e) {
      throw e;
    }
  }

  /**
   * 名称：
   * 功能：
   * 输入参数：
   * 返回参数：
   */
  public StringTokenizer(String fields, String delimiter)  throws Exception {
    try {
      //使用指定的分隔符初始化域列表
      init(fields, delimiter);
    } catch(Exception e) {
      throw e;
    }
  }

  /**
   * 名称：
   * 功能：
   * 输入参数：
   * 返回参数：
   */
  public void init(String fields, String delimiter)  throws Exception {
    try {
      //如果初始化字符为空，进行错误处理
      if (fields == null) {
        //错误处理
      }

      //初始化域列表
      listField = (List)new ArrayList();

      //如果初始化字符为空字符串，进行空值处理
      if (fields.length() == 0) {
        //控制处理
		return;
      }

      //构建处理原始字符串
      String strField = fields + delimiter;

      //解析出域列表
      while(strField.indexOf(delimiter) >= 0) {
        //读取初始化域字符中的第一个域内容
        listField.add(strField.substring(0, strField.indexOf(delimiter)));
        //将读取过的域内容从初始化域字符串中删除
        strField = strField.substring(strField.indexOf(delimiter) + delimiter.length());
      }
    } catch(Exception e) {
      throw e;
    }
  }

  /**
   * 名称：
   * 功能：
   * 输入参数：
   * 返回参数：
   */
  public boolean hasMoreField()  throws Exception {
    try {
      //域列表中还存在元素，返回true
      if (listField.size() > intPointer) {
        return true;
      //域列表中没有元素，返回false
      } else {
        return false;
      }
    } catch(Exception e) {
      throw e;
    }
  }

  /**
   * 名称：
   * 功能：
   * 输入参数：
   * 返回参数：
   */
  public String nextField()  throws Exception {
    try {
      //读取当前域内容
      String strField = (String)listField.get(intPointer);

      //读取域指针后移
      intPointer++;

      //返回域内容
      return strField;
    } catch(Exception e) {
      throw e;
    }
  }

  /**
   * 名称：
   * 功能：
   * 输入参数：
   * 返回参数：
   */
  public int size()  throws Exception {
    try {
      //列表存在则返回列表长度
      if (listField != null) {
        return listField.size();
      //列表不存在，返回-1
      } else {
        return -1;
      }
    } catch(Exception e) {
      throw e;
    }
  }

  /**
   * 名称：
   * 功能：
   * 输入参数：
   * 返回参数：
   */
  public static List getFields(String fields)  throws Exception {
    try {
      //返回结果
      return getFields(fields, "&&");
    } catch(Exception e) {
      throw e;
    }
  }

  /**
   * 名称：
   * 功能：
   * 输入参数：
   * 返回参数：
   */
  public static List getFields(String fields, String delimiter)  throws Exception {
    try {
      //如果初始化字符为空，进行错误处理
      if (fields == null) {
        //错误处理
      }

      //设置返回值
      ArrayList vtField = new ArrayList();

      //构建处理原始字符串
      String strField = fields + delimiter;

      //解析出域列表
      while(strField.indexOf(delimiter) >= 0) {
        //读取初始化域字符中的第一个域内容
        vtField.add(strField.substring(0, strField.indexOf(delimiter)));
        //将读取过的域内容从初始化域字符串中删除
        strField = strField.substring(strField.indexOf(delimiter) + delimiter.length());
      }

      //返回结果
      return vtField.subList(0, vtField.size());
    } catch(Exception e) {
      throw e;
    }
  }

}
