package com.gwssi.ebaic.apply.entaccount.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.gwssi.ebaic.apply.entaccount.service.EntAuthService;
import com.gwssi.ebaic.apply.util.SessionConst;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.rodimus.util.HttpSessionUtil;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.rodimus.util.StringUtil;

/**
 * 
 * @author qiaozy
 */
@Controller
@RequestMapping("/apply/entAuth")
public class EntAuthController {
	@Autowired
	EntAuthService entAuthService;
	
	/**
	 * 是否电子营业执照，只有电子营业执照，才可以执行企业认证服务。
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/isECert")
	public void isECert(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String regNo = ParamUtil.get("regNo");
		Map<String, Object> ret = entAuthService.isECert(regNo);
		response.addResponseBody(JSON.toJSONString(ret, true));
	}
	
	/**
	 * 根据企业名称获取服务认证码和触发认证时间
	 * @param request
	 * @param response
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/getServerCode")
	public void getServerCode(OptimusRequest request,OptimusResponse response) throws OptimusException{
		//String entName = ParamUtil.get("entName");
		String regNo = ParamUtil.get("regNo");
		Map<String, Object> authMap = entAuthService.getServerCode(regNo);
		HttpSession session = HttpSessionUtil.getSession();
		String entName = StringUtil.safe2String(authMap.get("entName"));
		session.setAttribute("authEntName", entName);
		authMap.remove("entName");
		response.addResponseBody(JSON.toJSONString(authMap, true));
		//response.addAttr("authMap",authMap);
	}
	/**
	 * 根据企业认证码验证企业
	 * @param request
	 * @param response
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/checkEntAuth")
	public void checkEntAuth(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String authCode = ParamUtil.get("authCode");
		String serverCode = ParamUtil.get("serverCode");
		HttpSession session = HttpSessionUtil.getSession();
		String authEntName = (String)session.getAttribute(SessionConst.AUTH_ENT_NAME);
		String flag = entAuthService.checkEntAuth(authCode,serverCode);
		entAuthService.updateState(flag, authCode);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("authEntName", authEntName);
		map.put("authFlag", flag);
		response.addResponseBody(JSON.toJSONString(map, true));
		//response.addAttr("result",map);
	}
}
