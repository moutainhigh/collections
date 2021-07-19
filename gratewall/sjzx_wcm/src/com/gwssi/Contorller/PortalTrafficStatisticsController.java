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
import com.gwssi.Service.PortalTrafficStatisticsService;
import com.gwssi.util.ErrorUtil;
import com.trs.dev4.jdk16.servlet24.ResponseUtil;

//�Ż�����ͳ��  1��ҳ��������2��ҳ���������ܼ�
public class PortalTrafficStatisticsController extends BaseContorller {
	
	private static PortalTrafficStatisticsService portalTrafficStatisticsService = new PortalTrafficStatisticsService();
	
	private static Logger logger = Logger.getLogger(PortalTrafficStatisticsController.class);
	
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
		//��ȡҳ����������
		if("getCommentCount".equals(method)){
			getCommentCount(request,response);
			return ;
		}
		//���ҳ������---ÿ�ε��ҳ�棬���������1
		if("addHitsCount".equals(method)){
			addHitsCount(request,response);
			return ;
		}
		//��ȡҳ������
		if("getHitsCount".equals(method)){
			getHitsCount(request,response);
			return ;
		}
		
		
		String responseText = ErrorUtil.getErrorResponse("unsupported method.");
		ResponseUtil.response(response, responseText);
	}
	

	/**
	 * ��ȡҳ��������
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getCommentCount(HttpServletRequest request, HttpServletResponse response) throws IOException{
		List<Map<String, Object>> commentCountList = portalTrafficStatisticsService.getCommentCount(request);
		String commentCount=null;
		if (commentCountList !=null && !commentCountList.isEmpty()) {
			commentCount = JSON.toJSONString(commentCountList, true);
		}else{
			Map<String,Object> listItem=new HashMap<String,Object>();
			listItem.put("COMMENTCOUNT", 0);
			commentCountList.add(listItem);
			commentCount = JSON.toJSONString(commentCountList, true);
		}
		logger.info("��ȡҳ����������>>>>>>>>>>>>>>>>>>>>>>\n"+commentCount);
		ResponseUtil.response(response, commentCount);
	}
	
	/**
	 * ���ҳ������---ÿ�ε��ҳ�棬���������1
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void addHitsCount(HttpServletRequest request, HttpServletResponse response) throws IOException{
		 portalTrafficStatisticsService.addHitsCount(request);
		ResponseUtil.response(response, "1");
	}
	
	/**
	 * ��ȡҳ������
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getHitsCount(HttpServletRequest request, HttpServletResponse response) throws IOException{
		int hitsCountNow = portalTrafficStatisticsService.getHitsCount(request);
		String hitsCount = JSON.toJSONString(hitsCountNow, true);  
		logger.info("��ȡҳ��������>>>>>>>>>>>>>>>>>>>>>>\n"+hitsCount);
		ResponseUtil.response(response, hitsCount);
	}
	

}
