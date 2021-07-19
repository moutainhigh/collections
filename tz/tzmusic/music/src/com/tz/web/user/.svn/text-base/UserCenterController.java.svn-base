package com.tz.web.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * 用户中心类,这个类里面的方法都必须要登陆
 * UserCenterController
 * 创建人:keke
 * 时间：2015年3月24日-下午9:15:29 
 * @version 1.0.0
 *
 */
@Controller
@RequestMapping("/center")
public class UserCenterController {
	
	
	@RequestMapping("/index")
	public String index(){
		System.out.println("=================我是以用户中心方法!!!!!");
		return "center/index";///WEB-INF/pages/center/index.jsp
	}
	
	
	@RequestMapping("/add")
	public String add(){
		System.out.println("=================添加用户!!!!!");
		return "center/index";///WEB-INF/pages/center/index.jsp
	}
}
