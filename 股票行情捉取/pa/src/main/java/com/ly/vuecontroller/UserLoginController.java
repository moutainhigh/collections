package com.ly.vuecontroller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ly.dao.UserDao;
import com.ly.placeholder.DESUtils;
import com.ly.pojo.User;

import redis.RedisDao;

//vue 操作cookie
//https://www.jb51.net/article/119692.htm
@RestController
@RequestMapping(value = "api")
public class UserLoginController {
	private static int recordeTimeOut = 60*10*3;// 10秒 10，600*3秒，共1800秒，30分钟
	
	@Autowired
	private UserDao userDao;
	
	
	@Autowired
	private RedisDao redisDao;

	@RequestMapping(value = "/dologin", method = RequestMethod.POST)
	public Map login(User user2, HttpServletResponse response) {
		Map<String, String> msg = new HashMap<String, String>();
		Map<String, String> paraMap = new HashMap<String, String>();
		String username = user2.getName();
		String password = user2.getPwd();
		if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)) {
			int count = 0;
			paraMap.put("name", username);
			User user = userDao.getUserByParam(paraMap);
			if (user != null) {
				if (user.getLimitTime() != null) {
					count = Integer.valueOf(user.getLimitTime());
				}
				if (count >= 7) {
					count = count - 1;
					user.setLimitTime(count + "");
					if (user.getPwd().equals(DESUtils.getEncryptString(password))) {
						
						user.setLimitTime("10");
						msg.put("code", "登录成功");
						msg.put("token",DESUtils.getEncryptString(user.getName()));
						redisDao.add(user.getName(), recordeTimeOut, user);
					}else{
						msg.put("code", "用户名或密码不正确");
					}
				} else {
					msg.put("code", "用户名或密码输入错误次数过多，请稍后再试");
				}
				userDao.save(user);
			}else{
				msg.put("code", "用户不存在");
			}

		} else {
			msg.put("code", "用户名或密码不能为空");
		}
		return msg;
	}
}
