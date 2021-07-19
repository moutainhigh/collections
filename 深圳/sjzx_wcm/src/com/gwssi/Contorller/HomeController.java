package com.gwssi.Contorller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.gwssi.AppConstants;
import com.gwssi.Service.PortalHomeService;
import com.gwssi.util.ErrorUtil;
import com.trs.dev4.jdk16.servlet24.ResponseUtil;

public class HomeController extends BaseContorller {
	
	private static PortalHomeService portalHomeService = new PortalHomeService();
	
	private static Logger logger = Logger.getLogger(PortalHomeService.class);
	
	
	/* (non-Javadoc)
	 * @see com.gwssi.Contorller.BaseContorller#handle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getParameter(AppConstants.REQUEST_PARAM_NAME_METHOD);
		if(StringUtils.isBlank(method)){
			String responseText = ErrorUtil.getErrorResponse("Parameter method is required.");
			ResponseUtil.response(response, responseText);
			return ;
		}
		
		if("getAppList".equals(method)){
			getSysList(request,response);
			return ;
		}
		
		if("getUser".equals(method)){
			getUser(request,response);
			return ;
		}
		
		if("getLeader".equalsIgnoreCase(method)){
			getLeader(request,response);
			return;
		}
		
		if("dualFar".equalsIgnoreCase(method))
		{
			dualFar(request,response);
			return;
		}
		
		
		String responseText = ErrorUtil.getErrorResponse("unsupported method.");
		ResponseUtil.response(response, responseText);
	}
	


	/**
	 * ��ȡϵͳ�б�
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getSysList(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//List<Map<String, Object>> appList = portalHomeService.getAppList(request);
		List<Map<String, Object>> appList = portalHomeService.getAppListNew(request);
		String appListJSonString = JSON.toJSONString(appList, true);  
		logger.info("��ȡϵͳ�嵥��>>>>>>>>>>>>>>>>>>>>>>\n"+appListJSonString);
		ResponseUtil.response(response, appListJSonString);
	}
	
	
	/**
	 *  ɾ���������ղؼ�
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void dualFar(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String dual=request.getParameter("dual");
		if(StringUtils.equalsIgnoreCase(dual, "add")){
			Map<String, String> p1 = portalHomeService.insertFarOne(request);
			String appListJSonString = JSON.toJSONString(p1, true);  
			ResponseUtil.response(response, appListJSonString);
		}else if(StringUtils.equalsIgnoreCase(dual, "delete")){
			Map<String, String> p1 = portalHomeService.deleteFarOne(request);

			String appListJSonString = JSON.toJSONString(p1, true);  
			ResponseUtil.response(response, appListJSonString);
		}else{
			String responseText = ErrorUtil.getErrorResponse("unsupported method.");
			ResponseUtil.response(response, responseText);
			return;
		}
		

	}
	
	/**
	 * ��ȡ��ǰ�û�
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getUser(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		Map<String,String> userMap = new HashMap<String,String>();
		
		HttpSession session = request.getSession();
		if(session!=null){
			String userId = (String)session.getAttribute(AppConstants.SESSION_KEY_USER_ID);
			String userName =(String)session.getAttribute(AppConstants.SESSION_KEY_USER_NAME);
			
			if(StringUtils.isBlank(userId)){
				userId = "";
			}
			if(StringUtils.isBlank(userName)){
				userName = "";
			}
			
			userMap.put(AppConstants.SESSION_KEY_USER_ID, userId);
			userMap.put(AppConstants.SESSION_KEY_USER_NAME, userName);
		}
		
		//���δ��¼���᷵�ؿն���
		String userJSonString = JSON.toJSONString(userMap, true);
		logger.info("��ȡ�û���Ϣ��>>>>>>>>>>>>>>>>>>>>>>"+userJSonString);
		ResponseUtil.response(response, userJSonString);
	}

//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>����ΪWCM���õ���>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>	
	/**
	 * ��ȡ��ǰ��Ա�쵼
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void getLeader(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.debug("��ǰ��Ա�쵼" + request);
		List<Map<String,Object>> getList= portalHomeService.getLearder(request);
		String leadersJSonString = JSON.toJSONString(getList, true);  
		logger.info("��ȡ�쵼�嵥��>>>>>>>>>>>>>>>>>>>>>>\n"+leadersJSonString);
		System.out.println("����ȡ�쵼�嵥" + leadersJSonString);
		ResponseUtil.response(response, leadersJSonString);
		
	}
	

}
