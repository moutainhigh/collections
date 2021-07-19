package com.gwssi.common.util;

import java.util.ArrayList;
import java.util.List;

/**
* ���ڴ���String �ָ�
*@author lifx,zuojy
*/
public class StringTokenizer {
  //���б�
  protected List listField = null;
  //��ȡλ��ָ��
  protected int intPointer = 0;

  /**
   * ���ƣ�
   * ���ܣ�
   * ���������
   * ���ز�����
   */
  public StringTokenizer(String fields)  throws Exception {
    try {
      //ʹ��ȱʡ�ķָ�����ʼ�����б�
      init(fields, "&&");
    } catch(Exception e) {
      throw e;
    }
  }

  /**
   * ���ƣ�
   * ���ܣ�
   * ���������
   * ���ز�����
   */
  public StringTokenizer(String fields, String delimiter)  throws Exception {
    try {
      //ʹ��ָ���ķָ�����ʼ�����б�
      init(fields, delimiter);
    } catch(Exception e) {
      throw e;
    }
  }

  /**
   * ���ƣ�
   * ���ܣ�
   * ���������
   * ���ز�����
   */
  public void init(String fields, String delimiter)  throws Exception {
    try {
      //�����ʼ���ַ�Ϊ�գ����д�����
      if (fields == null) {
        //������
      }

      //��ʼ�����б�
      listField = (List)new ArrayList();

      //�����ʼ���ַ�Ϊ���ַ��������п�ֵ����
      if (fields.length() == 0) {
        //���ƴ���
		return;
      }

      //��������ԭʼ�ַ���
      String strField = fields + delimiter;

      //���������б�
      while(strField.indexOf(delimiter) >= 0) {
        //��ȡ��ʼ�����ַ��еĵ�һ��������
        listField.add(strField.substring(0, strField.indexOf(delimiter)));
        //����ȡ���������ݴӳ�ʼ�����ַ�����ɾ��
        strField = strField.substring(strField.indexOf(delimiter) + delimiter.length());
      }
    } catch(Exception e) {
      throw e;
    }
  }

  /**
   * ���ƣ�
   * ���ܣ�
   * ���������
   * ���ز�����
   */
  public boolean hasMoreField()  throws Exception {
    try {
      //���б��л�����Ԫ�أ�����true
      if (listField.size() > intPointer) {
        return true;
      //���б���û��Ԫ�أ�����false
      } else {
        return false;
      }
    } catch(Exception e) {
      throw e;
    }
  }

  /**
   * ���ƣ�
   * ���ܣ�
   * ���������
   * ���ز�����
   */
  public String nextField()  throws Exception {
    try {
      //��ȡ��ǰ������
      String strField = (String)listField.get(intPointer);

      //��ȡ��ָ�����
      intPointer++;

      //����������
      return strField;
    } catch(Exception e) {
      throw e;
    }
  }

  /**
   * ���ƣ�
   * ���ܣ�
   * ���������
   * ���ز�����
   */
  public int size()  throws Exception {
    try {
      //�б�����򷵻��б���
      if (listField != null) {
        return listField.size();
      //�б����ڣ�����-1
      } else {
        return -1;
      }
    } catch(Exception e) {
      throw e;
    }
  }

  /**
   * ���ƣ�
   * ���ܣ�
   * ���������
   * ���ز�����
   */
  public static List getFields(String fields)  throws Exception {
    try {
      //���ؽ��
      return getFields(fields, "&&");
    } catch(Exception e) {
      throw e;
    }
  }

  /**
   * ���ƣ�
   * ���ܣ�
   * ���������
   * ���ز�����
   */
  public static List getFields(String fields, String delimiter)  throws Exception {
    try {
      //�����ʼ���ַ�Ϊ�գ����д�����
      if (fields == null) {
        //������
      }

      //���÷���ֵ
      ArrayList vtField = new ArrayList();

      //��������ԭʼ�ַ���
      String strField = fields + delimiter;

      //���������б�
      while(strField.indexOf(delimiter) >= 0) {
        //��ȡ��ʼ�����ַ��еĵ�һ��������
        vtField.add(strField.substring(0, strField.indexOf(delimiter)));
        //����ȡ���������ݴӳ�ʼ�����ַ�����ɾ��
        strField = strField.substring(strField.indexOf(delimiter) + delimiter.length());
      }

      //���ؽ��
      return vtField.subList(0, vtField.size());
    } catch(Exception e) {
      throw e;
    }
  }

}
