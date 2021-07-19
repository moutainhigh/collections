package com.gwssi.ebaic.apply.security.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.ebaic.apply.security.user.service.FindEntAccountPwdService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.rodimus.util.ParamUtil;
/**
 * 找回密码 。
 * @author qiaozy
 */
@Controller
@RequestMapping("/security/auth/findEntAccountPwd")
public class FindEntAccountPwdController {


	@Autowired
	private FindEntAccountPwdService findPwdService;
	/**
	 * 根据企业名称 注册号或统一信用码 法定代表人姓名 法定代表人证件号校验企业账号，并返回移动电话
	 * @param request
	 * @param response
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/checkEntAccount")
	public void checkEntAccount(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String entName = ParamUtil.get("entName");
		String regNo = ParamUtil.get("regNo");
		String legName = ParamUtil.get("legName");
		String regCerNo = ParamUtil.get("regCerNo");
		String mobile = findPwdService.queryMobile(entName, regNo, legName, regCerNo);
		response.addAttr("mobile",mobile);
	}
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
		String entAccName = ParamUtil.get("entAccName");
		String mobile = ParamUtil.get("mobile");
		String verCode = ParamUtil.get("verCode");
		String pwd = ParamUtil.get("pwd");
		findPwdService.save(entAccName, mobile, verCode, pwd);
		response.addAttr("result","success");
	}
}
