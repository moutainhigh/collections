/**
 * tzdesk系统平台
 * tzexam
 * com.tz.web
 * BaseController.java
 * 创建人:xuchengfei 
 * 时间：2015年11月14日-上午12:14:31 
 * 2015潭州教育公司-版权所有
 */
package com.tz.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tz.model.User;
import com.tz.util.TzConstant;

/**
 * 
 * BaseController
 * 创建人:xuchengfei 
 * 时间：2015年11月14日-上午12:14:31 
 * @version 1.0.0
 * 
 */
@Controller
public class BaseController {

	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 获取用户ID
	 * com.tz.web 
	 * 方法名：getUserId
	 * 创建人：xuchengfei 
	 * 手机号码:15074816437
	 * 时间：2015年11月14日-上午12:15:16 
	 * @return 
	 * 返回类型：Integer
	 * @exception 
	 * @since  1.0.0
	 */
	protected Integer getUserId(){
		Integer userId = (Integer) request.getSession().getAttribute(TzConstant.SESSION_USER_ID);
		return userId;
	}
	
	/**
	 * 获取用户信息
	 * com.tz.web 
	 * 方法名：getUser
	 * 创建人：xuchengfei 
	 * 手机号码:15074816437
	 * 时间：2015年11月14日-上午12:15:52 
	 * @return 
	 * 返回类型：User
	 * @exception 
	 * @since  1.0.0
	 */
	protected User getUser(){
		User user = (User) request.getSession().getAttribute(TzConstant.SESSION_USER);
		return user;
	}
}
