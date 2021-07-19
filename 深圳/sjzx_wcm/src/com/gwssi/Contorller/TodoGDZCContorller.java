package com.gwssi.Contorller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwssi.AppConstants;
import com.gwssi.Service.PortalGDZCTodoService;
import com.gwssi.util.ErrorUtil;
import com.gwssi.util.UserUtil;
import com.trs.dev4.jdk16.servlet24.ResponseUtil;

/**
 * 获取门户固定资产待办。
 * 
 */
public class TodoGDZCContorller extends BaseContorller {
	
	private static final Logger logger = LoggerFactory.getLogger(TodoGDZCContorller.class);
	
	protected static PortalGDZCTodoService portalTodoService = new PortalGDZCTodoService();
	
	/* (non-Javadoc)
	 * @see com.gwssi.Contorller.BaseContorller#handle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void handle(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
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
		
		if(StringUtils.isBlank(userId)){
			jsonResponseText = ErrorUtil.getErrorResponse("Current userId is empty.");
			ResponseUtil.response(response, jsonResponseText);
			return ;
		}
		// 测试用
		if("CHANGRUAN@SZAIC".equals(userId) || "CHANGRUAN1@SZAIC".equals(userId)){
			// userId = "LINQY1@SZAIC";
			// userId = "LINQY1@SZAIC";
			//userId = "LINAN@SZAIC";//@szaic.gov.cn
			userId = "JIANGHC@SZAIC";//@szaic.gov.cn
		}
		String username = UserUtil.getUserName(userId);
		try{
			
			if("Pma".equalsIgnoreCase(method)){
				jsonResponseText = portalTodoService.callPermanentAssetsService(userId,username);
				logger.info("Todo result>>>>>>>>>>>>>>>>" + jsonResponseText);
				logger.info("============================================userId " + userId + "==============   >>> " +username );
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
