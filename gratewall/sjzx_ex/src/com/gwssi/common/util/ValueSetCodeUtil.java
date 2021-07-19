package com.gwssi.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.resource.PublicResource;
import cn.gwssi.common.dao.resource.code.CodeMap;

public class ValueSetCodeUtil
{

	CodeMap		codeMap	= PublicResource.getCodeFactory();

	DateFormat	df		= new SimpleDateFormat("yyyy-MM-dd");

	DateFormat	df1		= new SimpleDateFormat("yyyy年MM月dd日");

	/**
	 * 根据传入值和代码集 返回中文解释
	 * 
	 * @param context
	 * @param valueCode
	 * @param value
	 * @return
	 * @throws TxnException
	 */
	public String getCodeStr(TxnContext context, String valueCode, String value)
			throws TxnException
	{
		if (value == null) {
			return "";
		}
		return codeMap.getCodeDesc(context, valueCode, value) == null ? value
				: codeMap.getCodeDesc(context, valueCode, value);
	}

	/**
	 * 根据传入日期字符串转化为固定格式的字符串
	 * 
	 * @param datestr
	 * @return
	 * @throws ParseException
	 */
	public String getDateFormatStn(String datestr)
	{
		if (StringUtils.isBlank(datestr)) {
			return "";
		}

		try {
			return df1.format(df.parse(datestr));
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
	}

	public String getDateFormatStnByPtn(String datestr, String ptn)
			throws ParseException
	{
		if (datestr == null) {
			return "";
		}
		df1 = new SimpleDateFormat(ptn);
		return df1.format(df.parse(datestr));
	}

	/**
	 * 实现JDK1.5的contains方法
	 * 
	 * @param str_1
	 * @param str_2
	 * @return
	 */
	public static boolean kcontains(String str_1, String str_2)
	{
		boolean b_result = false;
		char[] stra_1 = str_1.toCharArray();
		char[] stra_2 = str_2.toCharArray();
		int i_index = 0;
		int i_length = stra_2.length;
		int i_end = stra_1.length - i_length;
		while (i_index <= i_end) {
			int i_temp = i_index;
			int i_TrueFlag = 0;
			for (int i = 0; i < i_length; i++) {
				if (stra_2[i] == stra_1[i_temp]) {
					i_TrueFlag++;
				} else {
					break;
				}
				if (i_TrueFlag == i_length)
					return true;
				i_temp++;
			}
			i_index++;
		}
		return b_result;
	}

	public static String getParamContent(String paramText)
	{
		String temp = paramText.substring(0,
				paramText.lastIndexOf("<font color=red>"));
		return temp.substring(temp.lastIndexOf("</font>") + 7).trim();
	}

	/**
	 * 返回参数显示部分 string[0]为除去参数部分 去除括号
	 * 
	 * @param paramText
	 * @return
	 */
	public static String[] getParamContentArray(String paramText)
	{
		String temp = paramText.substring(0,
				paramText.lastIndexOf("<font color=red>"));
		String param = temp.substring(temp.lastIndexOf("</font>") + 7).trim();
		return new String[] {
				paramText.substring(0,
						paramText.indexOf(param) + param.length()),
				paramText.substring(paramText.indexOf(param) + param.length()) };
	}

	/**
	 * 获取property中的值 根据key
	 * @param fileName
	 * @param key
	 * @return
	 */
	public static String getPropertiesByKey(String fileName, String key)
	{
		ResourceBundle code = ResourceBundle.getBundle(fileName);
		String return_code = "";
		try {
			return_code = code.getString(key);
		} catch (Exception e) {
			return_code = key;
		}
		return return_code;
	}

}
