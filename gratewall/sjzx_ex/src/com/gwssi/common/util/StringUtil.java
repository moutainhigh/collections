package com.gwssi.common.util;


import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;


/**
 * 处理HTML,String常用辅助处理类
 * @author lifx
 */
public class StringUtil
{
	public static void main(String[] args) throws ParseException{
//		String temp = "22.3";
//		System.out.println(flushMoney(temp,4));
//		temp = "22.34";
//		System.out.println(flushMoney(temp,4));
//		temp = "22.500";
//		System.out.println(flushMoney(temp,4));
//		temp = "22.00";
//		System.out.println(flushMoney(temp,4));
		
//		NumberFormat format = NumberFormat.getPercentInstance();
//		double totalCzje = 10000.0d;
//		format.setMinimumFractionDigits(2);
//		format.setGroupingUsed( false ); 
//		System.out.println(format.format(1111.444));
		System.out.println(formatDateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));
	}
	
	/**
	 * 统一处理金额小数点对齐的方法。
	 * @param content
	 * @return
	 */
	public static String flushMoney(String money,int count)
	{
		String tmp = "";
		if(money==null||"".equals(money)){
			return money;
		}else{
			Double d = new Double(money);
			NumberFormat format = NumberFormat.getNumberInstance();
			tmp = format.format(d);
		}
		int flushNum = 0;
		if(tmp.indexOf(".")<0){
			flushNum = count;
		}else{
			flushNum = count-(tmp.length()-tmp.indexOf("."));
		}
		
		for(int i=0;i<=flushNum;i++){
			tmp+=" ";
		}
		return tmp;
	}	
	
	public static String toPercent(String str)
	{
		String tmp = "";
		if (str == null || "".equals(str.trim())) {
			return str;
		}
		if (str.indexOf("%") == -1) {
			try {
				Double d = new Double(str);
				NumberFormat format = NumberFormat.getNumberInstance();
				format.setMaximumFractionDigits(5);
				tmp = format.format(d.doubleValue());
				if (tmp.trim().equals("0"))
					return "";
				else
					return tmp + "%";
			} catch (Exception e) {
				return "";
			}
		} else {
			return str;
		}
	}

	public static String toPercent(String str, int plus)
	{
		String tmp = "";
		if (str == null || "".equals(str.trim())) {
			return str;
		}
		if (str.indexOf("%") == -1) {
			try {
				Double d = new Double(str);
				NumberFormat format = NumberFormat.getNumberInstance();
				tmp = format.format(d.doubleValue() * plus);
				format.setMaximumFractionDigits(5);
				tmp = format.format(d.doubleValue() * plus);
				if (tmp.trim().equals("0"))
					return "";
				else
					return tmp + "%";
			} catch (Exception e) {
				return "";
			}

		} else {
			return tmp;
		}
	}
	/**
	 * 统一处理字符编码转换，在页面格式处理的帮助。
	 * @param content
	 * @return
	 */
	public static String encode(String content)
	{
		StringBuffer tmpStr = new StringBuffer();
		for (int i = 0; i < content.length(); i++)
		{
			if (content.charAt(i) == '<')
			{
				tmpStr.append("&lt");
			} else if (content.charAt(i) == '>'){
				tmpStr.append("&gt");
			} else if (content.charAt(i) == '\n'){
				tmpStr.append("<br>");
			} else if (content.charAt(i) == ' '){
				tmpStr.append("&nbsp;");
			} else {
				tmpStr.append(content.charAt(i));
			}
		}
		return tmpStr.toString();
	}
    
    /**
     * 统一处理字符编码转换，在页面格式处理的帮助。
     * @param content
     * @return
     */
    public static String encodeHTML(String content)
    {

        StringBuffer tmpStr = new StringBuffer();
        for (int i = 0; i < content.length(); i++)
        {
            if (content.charAt(i) == '<')
            {
                tmpStr.append("&lt");
            } else if (content.charAt(i) == '>'){
                tmpStr.append("&gt");
            } else if (content.charAt(i) == '\n'){
                tmpStr.append("<br>");
            } else if (content.charAt(i) == ' '){
                tmpStr.append("&nbsp;");
            } else {
                tmpStr.append(content.charAt(i));
            }
        } 
        String temp=replace(tmpStr.toString(),"\\n", "<br>");
        System.out.println("@@@@@@@@@@@@"+temp);        
        return temp;
    } 
    
    /**
     * 统一处理字符编码转换，转换数据库换行字符。
     * @param content
     * @return
     */
    public static String encodeZXBJ(String content)
    {
        StringBuffer tmpStr = new StringBuffer();
        for (int i = 0; i < content.length(); i++)
        {
            if (content.charAt(i) == '\r') {

            } else if (content.charAt(i) == '\n') {
                tmpStr.append("\\n");
            } else {
                tmpStr.append(content.charAt(i));
            }
        }
        return tmpStr.toString();
    }
    
    /**
     * 统一处理字符编码转换，转换代码字符串换行字符。
     * @param content
     * @return
     */
    public static String encodeZXBJCode(String content)
    {
        StringBuffer tmpStr = new StringBuffer();
        for (int i = 0; i < content.length(); i++)
        {
            if (content.charAt(i) == '\r') {

            } else if (content.charAt(i) == '\n') {
                tmpStr.append("\\n");
            } else if (content.charAt(i) == '\'') {
                tmpStr.append("\\\'");
            } else if (content.charAt(i) == '\"') {
                tmpStr.append("\\\"");
            } else {
                tmpStr.append(content.charAt(i));
            }
        }
        return tmpStr.toString();
    }
    
    /**
     * 统一处理字符编码转换，转换数据库回车、单双引号的问题。
     * @param content
     * @return
     */
    public static String encodeSJJY(String content)
    {
        StringBuffer tmpStr = new StringBuffer();
        for (int i = 0; i < content.length(); i++)
        {
            if (content.charAt(i) == '\r') {

            } else if (content.charAt(i) == '\n') {
                tmpStr.append("\\n");
            } else if (content.charAt(i) == '\'') {
                tmpStr.append("\\\'");
            } else {
                tmpStr.append(content.charAt(i));
            }
        }
        return tmpStr.toString();
    }    
    
	/**
	 * String 变量的内容替换方法，比较常用。
	 * @param content 需要替换String对象
	 * @param oldStr 将要被替换的原始内容
	 * @param newStr 替换成的新内容
	 * @return
	 */
	public static String replace(String content, String oldStr, String newStr){
		StringBuffer tmpStr = new StringBuffer();
		int fromIndex = 0, position = 0, i = 0;
		position = content.indexOf(oldStr, fromIndex);
		while (position != -1){
			for (i = fromIndex; i < position; i++)
			{
				tmpStr.append(content.charAt(i));
			}
			tmpStr.append(newStr);
			fromIndex = position + oldStr.length();
			position = content.indexOf(oldStr, fromIndex);
		}
		for (i = fromIndex; i < content.length(); i++){
			tmpStr.append(content.charAt(i));
		}
		return tmpStr.toString();
	}
    
    public static String getHttpContext(String strUrl,String strRoot){
        String temp = "";
        if (strRoot != null && strUrl != null){
            String strHttp = strUrl.substring(0,strUrl.substring(7).indexOf("/")+7);
            String strURI = strUrl.substring(strHttp.length());
            if (strRoot.equals("")||strRoot.equals("/")){
                temp = strHttp;
            }else if (strURI.indexOf(strRoot) != -1 && strURI.lastIndexOf("/") > 0){
                temp = strHttp+strURI.substring(0,strURI.indexOf(strRoot)+strRoot.length());
            }
        }
        
        return temp;
    }
  
	/**
	 * 将string转化成Map
	 * @param str 格式以,号分割多个数据 以=号分割key和value
	 * @return
	 * @throws Exception
	 */
  public static Map stringToMap(String str)throws Exception{
		Map map = new HashMap();
		
		if (str != null && !str.equals("")){
			for (StringTokenizer stk = new StringTokenizer(str,",");stk.hasMoreField();){
				String element = stk.nextField();
				if (!element.equals("") && element.indexOf("=") !=-1){
					int index = element.indexOf("=");
					String values = element.substring(index+1);
					map.put(element.substring(0,element.indexOf("=")),values);
				}
			}
		}
		
		return map;
	}
  
  	/**
  	 * 将string[](key=value)转化成Map 格式
  	 * @param str
  	 * @return
  	 * @throws Exception
  	 */
  	public static Map arrayToMap(String[] strs)throws Exception{
  		Map map = new HashMap();
		
		if (strs != null && !strs.equals("")){
			for (int i = 0 ; i < strs.length ; i ++){
				String element = strs[i];
				if (!element.equals("") && element.indexOf("=") !=-1){
					int index = element.indexOf("=");
					String values = element.substring(index+1);
					map.put(element.substring(0,element.indexOf("=")),values);
				}else{
					map.put(element,null);
				}
			}
		}
		
		return map;
	}
  	
    /***
     * 功能:String转化成汉字GBK
     */
    public static String unicodeToGB(String strIn) {
    	try {
    		if (strIn == null || strIn.equals(""))
    			return strIn;
    		String strOut;
    		strOut = new String(strIn.getBytes(), "GBK");
    		return strOut;
    	}catch (Exception e) {
    		return strIn;
    	}
    }
    // 将null值转换成空字符串
    public static String nullToEmpty(String strIn) {
    	return strIn == null?"":strIn;
    }
    
    public static String format2Date(String from,String fromPattern, String toPattern) throws ParseException{
    	SimpleDateFormat df = new SimpleDateFormat(fromPattern);
    	Date d = df.parse(from);
		df.applyPattern(toPattern);
		
		return df.format(d);    	
    }
    
    public static String formatDateToString(java.util.Date date,String toPattern){
    	SimpleDateFormat df = new SimpleDateFormat(toPattern);
    	
    	return df.format(date);
    }
    
    public static String formatDouble(double d){
    	NumberFormat format = NumberFormat.getNumberInstance();
    	format.setGroupingUsed( false ); 
    	return format.format(d);
    }
    
    public static int intValue(String from){
    	if(from == null || from.trim().equals("")){
    		return 0;
    	}else{
    		return new Integer(from).intValue();
    	}
    }
   
    /** 
     * 判断是否是xml结构 
     */  
    public static boolean isXML(String value) {  
        try {  
            DocumentHelper.parseText(value);  
        } catch (DocumentException e) { 
        	e.printStackTrace();
            return false;  
        }  
        return true;  
    }  
}