package com.ly.vuecontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ly.dao.impl.TokenKeyDao;
import com.ly.dao.impl.UserDao;
import com.ly.placeholder.DESUtils;
import com.ly.pojo.TokenKey;
import com.ly.pojo.User;

//vue 操作cookie
//https://www.jb51.net/article/119692.htm
@RestController
@RequestMapping(value = "api")
public class DoLoginController {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private TokenKeyDao tokenKeyDao;
	
	//@RequestMapping(value = "/dologin", method = RequestMethod.POST)
	@RequestMapping(value = "/dologin")
	public Map login(String name,String pwd, HttpServletResponse response,HttpSession session) {
		Map<String, String> msg = new HashMap<String, String>();
		
		String username = name.trim();
		String password = pwd.trim();
		User user = null;
		if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)) {
			int count = 0;
			List<User>  users = userDao.find("from User where name=?", username);
			if(users!=null&&users.size()>0){
				user = users.get(0);
				if (user.getLimitTime() != null) {
					count = Integer.valueOf(user.getLimitTime());
				}
				if (count >= 7) {
					count = count - 1;
					user.setLimitTime(count + "");
					if (user.getPwd().equals(DESUtils.getEncryptString(password))) {
						user.setLimitTime("10");
						msg.put("code", "登录成功");
						String token = DESUtils.getEncryptString(user.getName());
						msg.put("token",token);
						TokenKey key = new TokenKey();
						key.setId(token);
						key.setKeyStr(token+"_hao123");
						tokenKeyDao.update(key);
					}else{
						msg.put("code", "用户名或密码不正确");
					}
				} else {
					msg.put("code", "用户名或密码输入错误次数过多，请稍后再试");
				}
				userDao.update(user);
			}else{
				msg.put("code", "用户不存在");
			}

		} else {
			msg.put("code", "用户名或密码不能为空");
		}
		return msg;
	}
}
