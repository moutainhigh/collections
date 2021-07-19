package com.gwssi.common.util;

/**
 * <p>Title: ����ͳ��������Ŀ-���������</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007.8</p>
 *
 * <p>Company: gwssi</p>
 *
 * @author chenzw
 * @version 1.0
 */
public class StringConvertUtil {
    public StringConvertUtil() {
    }

    /**
     * ���ַ���������������.ʹ��ָ���������ַ�.
     * @param sb String[]   String�ַ�������
     * @param splitChar String  �����ַ�
     * @return String
     */
    static public String join(String[] str, String splitChar){
        if (str == null || splitChar == null){
            return null;
        }
        if (str.length == 0) return "";

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length; i++){
            if (i > 0){
                sb.append(splitChar);
            }
            sb.append(str[i]);
        }
        return sb.toString();
    }

}
