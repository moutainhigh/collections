package com.gwssi.Contorller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class WcmController extends BaseContorller {
	
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
		
		if("getLeader".equalsIgnoreCase(method)){
			getLeader(request,response);
			return;
		}
		
		
		
		String responseText = ErrorUtil.getErrorResponse("unsupported method.");
		ResponseUtil.response(response, responseText);
	}
	
	/**
	 * 获取当前人员领导
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void getLeader(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Map<String,Object>> getList= portalHomeService.getLearder(request);
		String leadersJSonString = JSON.toJSONString(getList, true);  
		logger.info("获取领导清单：>>>>>>>>>>>>>>>>>>>>>>\n"+leadersJSonString);
		ResponseUtil.response(response, leadersJSonString);
		
	}
	
	/**
	 * 判断当前图片是否符合该像素
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void picReader(HttpServletRequest request, HttpServletResponse response) throws IOException {

		File file = new File("E:\\test.jpg");
		FileChannel fc = null;
		if(file.exists() && file.isFile()){
		try {
		FileInputStream fs = new FileInputStream(file);
		fc = fs.getChannel();
		System.out.println(fc.size() + "-----fc.size()");
		} catch (FileNotFoundException e) {
		e.printStackTrace();
		} catch (IOException e) {
		e.printStackTrace();
		}
		}
		System.out.println(file.length() + "-----file.length  B");
		System.out.println(file.length() * 1024 + "-----file.length  kb");
		BufferedImage bi = null;
		try {
		bi = ImageIO.read(file);
		} catch (IOException e) {
		e.printStackTrace();
		}

		int width = bi.getWidth();
		int height = bi.getHeight();

		System.out.println("宽：像素-----" + width + "高：像素"  + height);

		
		
	}
}
