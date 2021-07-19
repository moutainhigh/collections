package com.gwssi.application.home.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.application.home.service.OAService;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;


@Controller
@RequestMapping("/home")
public class OAController {
	
	@Autowired
	private OAService oaService;
	
	private static final String ZERO_FLAG = "0";
	
	private static Logger logger = Logger.getLogger(HomeController.class);
	
	@RequestMapping(value = "getOAwaitByjsonp")
	@ResponseBody
	public String getOAwait( HttpServletRequest request, String callback)
	        throws Exception {
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);

		
		
		
	    String result =  "{'waitno':'"+oaService.dogetWaitOaNO(user.getUserId())+"'}";
	    //加上返回参数
	    result=callback+"("+result+")";
	   return result;
	}
	@RequestMapping(value = "getCWwaitByjsonp")
	@ResponseBody
	public String getCWwait( HttpServletRequest request, String callback)
	        throws Exception {
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
	    String result =  "{'waitno':'"+oaService.dogetWaitCWNO(user.getUserId())+"'}";
	    //加上返回参数
	    result=callback+"("+result+")";
	   return result;
	}
	/***
	 * 登记许可待办(浪潮)
	 * @param request
	 * @param callback
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getDJXKwaitByjsonp")
	@ResponseBody
	public Map<String,String> getDJXKwait( HttpServletRequest request)
			throws Exception {
		HttpSession session = WebContext.getHttpSession();
		Map<String,String> returnMap = new HashMap<String,String>();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		if(user==null&&user.getUserId()==null){
			returnMap.put("djxk_wait", ZERO_FLAG);
			return returnMap;
		}
		logger.debug("DJXK userId is >>>>>>>>>>>>>>>>>>>>>>>>>>"+user.getUserId());
		String result =String.valueOf(oaService.dogetWaitDJXKNO(user.getUserId()));
		returnMap.put("djxk_wait", result);
		//加上返回参数
		return returnMap;
	}
	/***
	 * WCM待审批
	 * @param request
	 * @param callback
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getPendingWaitByjson")
	@ResponseBody
	public Map<String,String> getPendingWait( HttpServletRequest request)
			throws Exception {
		HttpSession session = WebContext.getHttpSession();
		Map<String,String> returnMap = new HashMap<String,String>();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		if(user==null&&user.getUserId()==null){
			returnMap.put("wcm_wait", ZERO_FLAG);
			return returnMap;
		}
		logger.debug("Pending userId is >>>>>>>>>>>>>>>>>>>>>>>>>>"+user.getUserId());
		String result =String.valueOf(oaService.dogetWaitPending(user.getUserId()));
		returnMap.put("wcm_wait", result);
		//加上返回参数
		return returnMap;
	}
	
	
}
