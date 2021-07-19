package com.ly.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ly.common.SysResultCode;
import com.ly.dao.UserDao;
import com.ly.placeholder.DESUtils;
import com.ly.pojo.User;

import net.sf.json.JSONObject;

//https://bbs.csdn.net/topics/390995192
//https://www.cnblogs.com/black-spike/p/7813238.html
@Controller
public class UserController {

	@Autowired
	private UserDao userDao;

	@ResponseBody
	@RequestMapping("/loginCheck")
	public Boolean loginCheck(HttpServletRequest request, HttpServletResponse response) {
		String username = (String) request.getSession().getAttribute("username");
		if (username == null) {
			response.setHeader("sessionstatus", "timeout");
			return false;
		}
		return true;
	}

	// 表单验证,注意方法与上面不同
	@RequestMapping(value = "/login")
	@ResponseBody
	public Map login(User user2, HttpSession session, HttpServletRequest request) {
		String fromUrl = (String) session.getAttribute("fromPath");
		Map paraMap = new HashMap();
		String username = user2.getName();
		String password = user2.getPwd();
		int count = 0;

		// 由于重点在SpringMVC,此处模拟从数据库取出数据进行表单验证
		if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)) {
			paraMap.put("name", username);
			// paraMap.put("pwd", password);
			User user = userDao.getUserByParam(paraMap);
			if(user!=null){
				if (user.getLimitTime() != null) {
					count = Integer.valueOf(user.getLimitTime());
				}
				if (count >= 7) {
					count = count - 1;
					user.setLimitTime(count + "");
					
				}
				if (user != null && user.getPwd().equals(DESUtils.getEncryptString(password))) {
					if (count >= 7) {
						session.setAttribute("username", user.getName());
						// 进入登录主页
						// map.put("code", "登录成功");
						SysResultCode.codeStatus1();
						SysResultCode.codeStatus00("fromUrl",fromUrl);
						user.setLimitTime("10");
						//map.put("from", fromUrl);
					} else {
						// map.put("msg", "输入错误次数过多，请过一段时间再试");
						SysResultCode.codeStatus2();
					}
				} else {
					//用户名或密码错误
					SysResultCode.codeStatus3();
				}
				userDao.save(user);
			}else{
				SysResultCode.codeStatus8();
			}
			
		} else {
			// request.setAttribute("msg", "用户名或密码错误");
			// map.put("msg", "用户输入信息不能为空");
			SysResultCode.codeStatu4();
			// 返回到登录页面
		}
		return SysResultCode.getMap();
	}

	@RequestMapping("/logout")
	@ResponseBody
	public void logout(HttpSession session, HttpServletResponse response) throws IOException {
		// 清除session的数据
		session.invalidate();
		JSONObject jsonObject = JSONObject.fromObject(SysResultCode.codeStatu5());
		PrintWriter writer = response.getWriter();
		writer.print(jsonObject);
		writer.close();
		response.flushBuffer();
	}
}
