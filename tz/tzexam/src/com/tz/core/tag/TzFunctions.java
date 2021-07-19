/**
 * Project Name:tzexam
 * File Name:TzFunctions.java
 * Package Name:com.tz.core.tag
 * Date:2015年11月1日上午12:06:09
 * Copyright (c) 2015, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.tz.core.tag;

import com.tz.util.date.TmDateUtil;

/**
 * ClassName:TzFunctions <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年11月1日 上午12:06:09 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class TzFunctions {

	/**
	 * 
	 * 自定日期格式化
	 * @author Administrator
	 * @param date
	 * @return
	 * @since JDK 1.6
	 */
	public static String dateFormate(String date){
		return TmDateUtil.getTimeFormat(date);
	}
	
}

