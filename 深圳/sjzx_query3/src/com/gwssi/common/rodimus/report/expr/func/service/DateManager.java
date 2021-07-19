package com.gwssi.common.rodimus.report.expr.func.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 
 * <h2>表达式数据源：日期相关</h2>
 * 
 * <p>所有方法必须以get开头。</p>
 * 
 * 
 * @author liuhailong
 */
public class DateManager {
	
	protected final static DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * @return 当天日期字符串，格式为yyyy-MM-dd。
	 * @author liuhailong
	 */
	public String getToday(){
		Date date = new Date();
		String ret = sdf.format(date);
		return ret;
	}
}
