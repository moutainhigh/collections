package com.gwssi.ebaic.apply.security.user.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.ebaic.apply.security.user.service.UserLoginService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.util.JSON;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.HttpSessionUtil;
import com.gwssi.rodimus.util.ParamUtil;

@Controller
@RequestMapping("/security/auth/user")
public class UserLoginController {

	@Autowired
	UserLoginService userLoginService;
	/**
	 * index.html
	 *  user：登录名/身份证号
		password：密码
		vercode : 验证码
		type : ent - 企业用户；空或者 person，个人用户；admin-后台账户
		smscode : 短信验证码（企业用户必填）
	 * @param request
	 * @param response
	 * @throws OptimusException 
	 * @throws IOException 
	 */
	@RequestMapping("/login")
	public void login(OptimusRequest request,OptimusResponse response) throws IOException, OptimusException{
		/**1、验证码校验**/
		Object session_verifyCode=HttpSessionUtil.getSession().getAttribute("verify_code");
		String vercode = ParamUtil.get("vercode",false);
		//验证码不区分大小写,将页面输入的验证码转成大写再与session中大写的验证码进行比较
		vercode = (vercode==null)?"":vercode;
		vercode=vercode.toUpperCase();
		if(session_verifyCode==null){
			throw new EBaicException("验证码错误，请刷新页面后重试");
		}
		if(!session_verifyCode.equals(vercode)){
			response.addAttr("result", "验证码错误");
			return ;
		}
		
		String user = ParamUtil.get("user");
		String password = ParamUtil.get("password");			
		//用户校验
		String result = userLoginService.personLogin(user, password);
		// 登录成功，跳转页面
		//response.getHttpResponse().sendRedirect("");
		response.addAttr("result", result);

	}
	/**
	 * 当前登录用户状态。
	 * 
	 * @param request
	 * @param response
	 * @throws OptimusException
	 * @throws IOException
	 * @throws ServletException 
	 */
	@RequestMapping("/loginStatus")
	public void status(OptimusRequest request,OptimusResponse response) throws OptimusException, IOException, ServletException{
		Map<String,Object> state = userLoginService.getLoginState();
		response.addResponseBody(JSON.toJSON(state));
	}
	
	/**
	 * 当前登录用户状态。
	 * 
	 * @param request
	 * @param response
	 * @throws OptimusException
	 * @throws IOException
	 * @throws ServletException 
	 */
	@RequestMapping("/checkCerNo")
	public void checkCerNo(OptimusRequest request,OptimusResponse response) throws OptimusException, IOException, ServletException{
		Map<String,Object> state = userLoginService.getLoginState();
		response.addResponseBody(JSON.toJSON(state));
	}
	
	@RequestMapping("/logout")
	public void logout(OptimusRequest request,OptimusResponse response) throws OptimusException, IOException, ServletException{
		HttpSession session = HttpSessionUtil.getSession();
		session.removeAttribute(OptimusAuthManager.USER);
	}
	
	
	/**
	 * 当前用户如果没有验证身份证的话 需要填写身份证进行验证 
	 * yzh
	 * @param request
	 * @param response 
	 * @throws OptimusException 
	 */
	@RequestMapping("/verifyCerNoAgain")
	public void verifyCerNoAgain(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String cerNo = request.getParameter("cerNo").trim();
		String loginName = request.getParameter("loginName").trim();
		//String gid = ParamUtil.get("gid").trim();
		//准备返回变量
		Map<String,Object> map = new HashMap<String,Object>();
		if("".equals(cerNo)){
			map.put("result", "2");
		}
		String sql = "SELECT COUNT(1) FROM T_PT_YH WHERE CER_NO = ?";
		
		Map<String, Object> resultMap = DaoUtil.getInstance().queryForRow(sql, cerNo);
		BigDecimal count = (BigDecimal) resultMap.get("count(1)");
		int r = count.compareTo(BigDecimal.ZERO);
		if(r==1){//数据库中有该身份证
			map.put("result","2");
		}else{//数据库没有该身份证
			//String userId = HttpSessionUtil.getCurrentUserId();
			userLoginService.alterCheckState(loginName,cerNo);//如果改身份证没有被验证过 则改变该用户的身份证核查状态以及将身份证存库
			map.put("result","1");
		}
		//System.err.println(JSON.toJSON(map));
		response.addResponseBody(JSON.toJSON(map));
		//return map;
	}
}
