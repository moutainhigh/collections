package com.tz.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tz.dao.UserDao;
import com.tz.model.TzMusicUser;
import com.tz.util.TmStringUtils;
import com.tz.util.ip.TmIpUtil;

@Controller
public class LoginController {

	@Autowired
	private UserDao userDao;

	/**
	 * 调整到登陆页面 方法名：login 创建人：xuchengfei 时间：2015年3月17日-下午10:49:23
	 * 
	 * @return String
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	/**
	 * 登陆请求验证 方法名：logined 创建人：xuchengfei 时间：2015年3月17日-下午10:50:08
	 * 
	 * @return String
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/logined/{account}/{password}", method = RequestMethod.POST)
	public TzMusicUser logined(@PathVariable("account") String account,@PathVariable("password") String password,HttpServletRequest request) {
		if(TmStringUtils.isNotEmpty(account) && TmStringUtils.isNotEmpty(password)){
			// 根据账号和密码判断是否存在
			TzMusicUser user = userDao.getMusicUser(account, TmStringUtils.md5Base64(password));
			if (user != null) {
				//将登陆的用户信息保存在session中
				String address =  TmIpUtil.ipLocation(request);//获取当前的ip地址
				user = getSessionUser(user);
				user.setAddress(address);
				request.getSession().setAttribute("user", user);
				request.getSession().setAttribute("address", address);
				return user;
			} else {
				return null;
			}
		}else{
			return null;
		}
	}
	
	/**
	 * 前台日期，
	 * userid
	 * 方法名：getSessionUser
	 * 创建人：xuchengfei 时间：2015年3月24日-下午11:51:16 
	 * @param user
	 * @return TzMusicUser
	 * @exception 
	 * @since  1.0.0
	 */
	public TzMusicUser getSessionUser(TzMusicUser user){
		TzMusicUser musicUser = new TzMusicUser();
		musicUser.setId(user.getId());
		musicUser.setAccount(user.getAccount());
		musicUser.setUsername(user.getUsername());
		musicUser.setHeaderPic(user.getHeaderPic());
		return musicUser;
	}
	
	@RequestMapping("/logout")
	@ResponseBody
	public String logout(HttpServletRequest request){
		request.getSession().invalidate();
		return "success";
	}
}
