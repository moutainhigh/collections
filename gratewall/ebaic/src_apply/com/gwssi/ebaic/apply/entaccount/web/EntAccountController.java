package com.gwssi.ebaic.apply.entaccount.web;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.ebaic.apply.entaccount.service.EntAccountService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.rodimus.util.HttpSessionUtil;

/**
 * 
 * @author qiaozy
 */
@Controller
@RequestMapping("/apply/entAccHome")
public class EntAccountController {
	@Autowired
	EntAccountService entAccountService;
	/**
	 * 获取当天的企业认证记录信息
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/getEntValidateRecord")
	public void getEntValidateRecord(OptimusRequest request,OptimusResponse response) throws OptimusException{
		List<Map<String, Object>> list = entAccountService.queryEntValidateRecord();
		response.addAttr("result",list);
	}
	
	/**
	 * 从session获取mobile 判断是法人账户登陆还是管理用用户登陆
	 * yzh
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/checkLepAccount")
	public void checkLepAccount(OptimusRequest request,OptimusResponse response) throws OptimusException{
		HttpSession session = HttpSessionUtil.getSession();
		String isEntAccountFlag = (String) session.getAttribute("isEntAccount");
		response.addResponseBody(isEntAccountFlag);
	}
}
