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

//门户流量统计  1：页面点击量。2：页面评论数总计
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
		//获取页面评论总数
		if("getCommentCount".equals(method)){
			getCommentCount(request,response);
			return ;
		}
		//添加页面点击量---每次点击页面，点击量增加1
		if("addHitsCount".equals(method)){
			addHitsCount(request,response);
			return ;
		}
		//获取页面点击量
		if("getHitsCount".equals(method)){
			getHitsCount(request,response);
			return ;
		}
		
		
		String responseText = ErrorUtil.getErrorResponse("unsupported method.");
		ResponseUtil.response(response, responseText);
	}
	

	/**
	 * 获取页面评论数
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
		logger.info("获取页面评论数：>>>>>>>>>>>>>>>>>>>>>>\n"+commentCount);
		ResponseUtil.response(response, commentCount);
	}
	
	/**
	 * 添加页面点击量---每次点击页面，点击量增加1
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void addHitsCount(HttpServletRequest request, HttpServletResponse response) throws IOException{
		 portalTrafficStatisticsService.addHitsCount(request);
		ResponseUtil.response(response, "1");
	}
	
	/**
	 * 获取页面点击量
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getHitsCount(HttpServletRequest request, HttpServletResponse response) throws IOException{
		int hitsCountNow = portalTrafficStatisticsService.getHitsCount(request);
		String hitsCount = JSON.toJSONString(hitsCountNow, true);  
		logger.info("获取页面点击量：>>>>>>>>>>>>>>>>>>>>>>\n"+hitsCount);
		ResponseUtil.response(response, hitsCount);
	}
	

}
