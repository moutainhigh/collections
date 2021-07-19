package com.gwssi.util;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.gwssi.AppConstants;

/**
 * 用户工具类。
 * 
 * @author chaihaowei
 */
public class UserUtil {
	
	/**
	 * 根据UserID的获取用户姓名。
	 * 
	 * @param userID
	 * @return
	 */
	public static String getUserName(String userID) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from V_HR_USERS t where t.USER_ID =?");
		List<Map<String,Object>> list=	SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_YYJC, sql.toString(), userID);
		if(list!=null && !list.isEmpty()){
			return (String) list.get(0).get("USER_NAME");
		}else{
			return null;	
		}
	}
	/**
	 * 获得当前登录用户ID。
	 * 
	 * @param request
	 * @return
	 */
	public static String getCurrentUserId(HttpServletRequest request){
		String ret = "";
	 	HttpSession session = request.getSession();
	 	if(session!=null){
	 		ret = (String)session.getAttribute(AppConstants.SESSION_KEY_USER_ID);
	 	}
		if(StringUtils.isBlank(ret)){
			ret = "";
		}
		return ret;
	 }
}
