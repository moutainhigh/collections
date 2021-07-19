package com.gwssi.ebaic.apply.security.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.ebaic.apply.security.user.service.UserRegistService;

/**
 * 个人用户注册。
 */
@Controller
@RequestMapping("/security/auth/regist")
public class UserRegistController {
	
	@Autowired
	private UserRegistService userRegistService;
	
	/**
	 * 登录名是否已经存在。
	 * 
	 * @param login_name
	 * @return
	 */
	@RequestMapping("/checkUser")
	@ResponseBody
	public String checkUser(String loginName){
		boolean isExist=userRegistService.checkUser(loginName);
		if(isExist){
			return "has";
		}
		return "none";
	}
	
	
	/**
	 * 检查移动电话码是否注册过
	 * yzh
	 * @param mobile
	 * @return
	 */
	@RequestMapping("/checkMobile")
	@ResponseBody
	public String checkMobile(String mobile){
		boolean isExist = userRegistService.checkMobile(mobile);
		if(isExist){
			return "has";
		}
		return "none";
	}
}
