package com.gwssi.ebaic.apply.entaccount.web;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.ebaic.apply.entaccount.service.EntConfirmEntryService;
import com.gwssi.ebaic.domain.SysmgrIdentityBO;
import com.gwssi.ebaic.mobile.impl.LoginServiceImpl;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.sms.SmsVerCodeUtil;
import com.gwssi.rodimus.util.HttpSessionUtil;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.rodimus.util.StringUtil;

/**
 * 企业账号登录（业务确认）
 * 
 * @author 祥乾 刘
 *
 */
@Controller
@RequestMapping("/apply/entConfrimEntry")
public class EntConfirmEntryController {
	@Autowired
	LoginServiceImpl loginServiceImpl;
	@Autowired
	EntConfirmEntryService entConfirmEntryService;
	
	/**
	 * 企业账号登录
	 * 
	 * @param requeest
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/login")
	public void entConfirmEntry(OptimusRequest requeest,OptimusResponse response){
		String loginWay = ParamUtil.get("loginWay",true);
		//判断企业账户登录方式，indi-身份验证信息登录 ;ca-CA证书登录
		if("indi".equals(loginWay)){
			String regNo = ParamUtil.get("regNo",true);
			String mobile = ParamUtil.get("mobile",true);
			String mobileVaildateCode = ParamUtil.get("mobileVaildateCode",true);
			//查询企业联系人电话（身份认证时留的电话）
			String encMobile =  entConfirmEntryService.queryEntConfirmMobile(regNo);
			if(!mobile.equals(encMobile)) {
	            throw new EBaicException("企业联系人手机号输入错误，请重新输入。");
	        }
			//企业用户-业务确认入口
			Map<String, String> ret = loginServiceImpl.entEntry("", regNo, mobile, mobileVaildateCode, "1");
			if(ret==null || ret.isEmpty()){
				throw new EBaicException("未找到企业认证信息，请核对信息后重试。");
			}
			
			String identityId = ret.get("identityId");
			String flag = ret.get("flag");
			//只有身份认证通过后将认证信息放入session
			if("1".equals(flag) || "0".equals(flag)){
				SysmgrIdentityBO user = entConfirmEntryService.querySysmgrIdentityBO(identityId);
				HttpSession session = HttpSessionUtil.getSession();
				session.setAttribute(OptimusAuthManager.USER, user);
				session.setAttribute(OptimusAuthManager.LOGIN_USER_TYPE, OptimusAuthManager.LOGIN_USER_TYPE_ENT);
			}else{
				throw new EBaicException("您需要先进行实名认证才能登录。请通过手机客户端进行实名身份认证。");
			}
			try {
				response.addAttr("result","success");
			} catch (OptimusException e) {
				throw new EBaicException(e.getMessage());
			}
		}else if("ca".equals(loginWay)){
			//TODO CA证书登录
		}else{
			throw new EBaicException("请选择登录方式。");
		}
		
		
	}
	
	/**
	 * 获取企业账号登录验证码
	 * 
	 * @param requeest
	 * @param response
	 */
	@RequestMapping("/getConfirmCode")
	public void getEntConfirmVerCode(OptimusRequest requeest,OptimusResponse response){
		String regNo = ParamUtil.get("regNo",true);
		String mobile = ParamUtil.get("mobile",true);
		String encMobile =  entConfirmEntryService.queryEntConfirmMobile(regNo);
		if(!mobile.equals(encMobile)) {
		    throw new EBaicException(String.format("企业联系人电话不正确，请输入原预留的%s，如原号码已经不可用，请联系系统管理员。",StringUtil.maskMobile(encMobile)));
		}
		SmsVerCodeUtil.send(mobile);
		try {
			response.addAttr("result","success");
		} catch (OptimusException e) {
			throw new EBaicException(e.getMessage());
		}
	}
}
