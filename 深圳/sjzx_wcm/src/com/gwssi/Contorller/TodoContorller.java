package com.gwssi.Contorller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwssi.AppConstants;
import com.gwssi.Service.PortalTodoService;
import com.gwssi.util.ErrorUtil;
import com.gwssi.util.UserUtil;
import com.trs.dev4.jdk16.servlet24.ResponseUtil;

/**
 * 获取门户待办。
 * 
 */


public class TodoContorller extends BaseContorller {
	
	private static final Logger logger = LoggerFactory.getLogger(TodoContorller.class);
	
	protected static PortalTodoService portalTodoService = new PortalTodoService();
	
	/* (non-Javadoc)
	 * @see com.gwssi.Contorller.BaseContorller#handle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void handle(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		logger.debug("Into the to-do control layer>>>>>>>>>>>>>>>>");
		
		String method = request.getParameter(AppConstants.REQUEST_PARAM_NAME_METHOD);
		logger.debug("method is message>>>>>>>>>>>>>>>>"+method);
		if(StringUtils.isBlank(method)){
			String responseText = ErrorUtil.getErrorResponse("Parameter method is required.");
			ResponseUtil.response(response, responseText);
			return ;
		}
		
		String jsonResponseText = "{}";
		
		String userId = UserUtil.getCurrentUserId(request);
		System.out.println("得到当前待办用户的ID ==============>>>>>>> 时间===》" + new Date().toLocaleString() + "    " + userId);
		if(StringUtils.isBlank(userId)){
			jsonResponseText = ErrorUtil.getErrorResponse("Current userId is empty.");
			ResponseUtil.response(response, jsonResponseText);
			return ;
		}
		// 测试用
		if("CHANGRUAN@SZAIC".equals(userId) || "CHANGRUAN1@SZAIC".equals(userId)){
			userId = "JIANGHC@SZAIC";
			//userId = "LINAN@SZAIC";
		}
		
		logger.info("=========================********************系统待办数据处理开始**************************===========================");
		logger.info("待办处理的用户的ID=============》" + new Date().toLocaleString() + "=============" + userId );
		
		try{
			if("Caiwu".equalsIgnoreCase(method)){
				jsonResponseText = portalTodoService.callCaiWuService(userId);
				logger.info("Todo result>>>>>>>>>>>>>>>>" + jsonResponseText);
				ResponseUtil.response(response, jsonResponseText);
				return ;
			}
			if("Sms".equalsIgnoreCase(method)){
				jsonResponseText = portalTodoService.callSmsService(userId);
				logger.info("Todo result>>>>>>>>>>>>>>>>" + jsonResponseText);
				ResponseUtil.response(response, jsonResponseText);
				return ;
			}
			if("Case".equalsIgnoreCase(method)){
				jsonResponseText = portalTodoService.callCaseService(userId);
				logger.info("Todo result>>>>>>>>>>>>>>>>" + jsonResponseText);
				ResponseUtil.response(response, jsonResponseText);
				return ;
			}
			if("Ms".equalsIgnoreCase(method)){
				jsonResponseText = portalTodoService.callMsService(userId);
				logger.info("Todo result>>>>>>>>>>>>>>>>" + jsonResponseText);
				ResponseUtil.response(response, jsonResponseText);
				return ;
			}
			if("Oa".equalsIgnoreCase(method)){
				jsonResponseText = portalTodoService.callOaService(userId);
				logger.info("Todo result>>>>>>>>>>>>>>>>" + jsonResponseText);
				ResponseUtil.response(response, jsonResponseText);
				return ;
			}
			if("GIAP".equalsIgnoreCase(method)){
				jsonResponseText = portalTodoService.callGiapService(userId);
				logger.info("Todo result>>>>>>>>>>>>>>>>" + jsonResponseText);
				ResponseUtil.response(response, jsonResponseText);
				return ;
			}
			if("Wcm".equalsIgnoreCase(method)){
				String id=StringUtils.replace(StringUtils.lowerCase(userId), "@", "_");
				jsonResponseText = portalTodoService.callWcmService(id);
				logger.info("Todo result>>>>>>>>>>>>>>>>" + jsonResponseText);
				ResponseUtil.response(response, jsonResponseText);
				return ;
			}
			
			if("GetWaitNo".equalsIgnoreCase(method)){
				jsonResponseText = portalTodoService.CallNoByDataBase(userId,request);
				logger.info("Todo result>>>>>>>>>>>>>>>>" + jsonResponseText);
				ResponseUtil.response(response, jsonResponseText);
				return ;
			}
			
			//合同管理系统
			if("cbmsys".equalsIgnoreCase(method)){
				logger.info("合同系统>>>>>>>>>>>>>>>>" + userId);
				jsonResponseText = portalTodoService.callCBMService(userId);
				logger.info("cbm合同系统  Todo result>>>>>>>>>>>>>>>>" + jsonResponseText);
				ResponseUtil.response(response, jsonResponseText);
				return ;
			}
			//jsonResponseText = ErrorUtil.getErrorResponse("Method " + method + " is not supported. ");
			//ResponseUtil.response(response, jsonResponseText);
			
			
			//exchange邮件系统
			if("exchangeCount".equalsIgnoreCase(method)){
				logger.info("===============================进入exchangeToDo的webservices接口开始========================================");
				logger.info("exchange邮件系统待办处理开始>>>>>>>>>>>>>>>>" + userId);
				jsonResponseText = portalTodoService.getEmailCounts(userId);
				logger.info("exchange邮件系统  Todo result>>>>>>>>>>>>>>>>" + jsonResponseText);
				logger.info("=================================进入exchangeToDo的webservices接口结束======================================");
				ResponseUtil.response(response, jsonResponseText);
				return ;
			}
			jsonResponseText = ErrorUtil.getErrorResponse("Method " + method + " is not supported. ");
			ResponseUtil.response(response, jsonResponseText);
			
		}catch(Throwable e){
			e.printStackTrace();
			jsonResponseText = ErrorUtil.getErrorResponse(e.getMessage());
			ResponseUtil.response(response, jsonResponseText);
			return ;
		}
	}
}
