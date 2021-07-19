package com.gwssi.common.util;

/**
 * <p>Title: 国家统计数据项目-发布库管理</p>
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
     * 将字符串数组连接起来.使用指定的连接字符.
     * @param sb String[]   String字符串数组
     * @param splitChar String  连接字符
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
