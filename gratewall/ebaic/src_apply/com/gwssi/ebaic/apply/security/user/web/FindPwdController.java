package com.gwssi.ebaic.apply.security.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.ebaic.apply.security.user.service.FindPwdService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.rodimus.util.ParamUtil;
/**
 * 找回密码 。
 * @author qiaozy
 */
@Controller
@RequestMapping("/security/auth/findPwd")
public class FindPwdController {


	@Autowired
	private FindPwdService findPwdService;
	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/checkMob")
	public void checkMob(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String loginName = ParamUtil.get("loginName");
		String mobile = ParamUtil.get("mobile");
		findPwdService.checkMobile(loginName, mobile);
		response.addAttr("result","success");
	}

	/**
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/save")
	public void save(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String loginName = ParamUtil.get("loginName");
		String mobile = ParamUtil.get("mobile");
		String verCode = ParamUtil.get("verCode");
		String pwd = ParamUtil.get("pwd");
		findPwdService.save(loginName, mobile, verCode, pwd);
		response.addAttr("result","success");
	}
}
