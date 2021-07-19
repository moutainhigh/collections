package com.gwssi.report.tzsb.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.report.tzsb.service.Data13Service;
import com.gwssi.report.tzsb.utils.QueryTsData13Utils;
import com.gwssi.report.tzsb.utils.QueryTsData17Utils;

@Controller
@RequestMapping("data13")
public class Data13Controller {

	@Autowired
	private Data13Service service;
	
	
	

	@ResponseBody
	@RequestMapping("sldw_code_value")
	public void getCode(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		HttpServletRequest request = req.getHttpRequest();
		resp.addTree("sldw", service.queryCodeValue());
	}
	
	
	

	@ResponseBody
	@RequestMapping("slType_code_value")
	public void slType_code_value(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		HttpServletRequest request = req.getHttpRequest();
		resp.addTree("slType", service.querySLTypeCodeValue());
	}
	
	
	
	
	
	@RequestMapping(value = "/queyShiYongDj")
	@ResponseBody
	public List<Map> queryByXiaQu(HttpServletRequest req, HttpServletResponse res) throws OptimusException {
		String sldw = req.getParameter("sldw");
		String slType = req.getParameter("slType");
		String spBegTime = req.getParameter("spBegTime");
		String spEndTime = req.getParameter("spEndTime");
		String slBegTime = req.getParameter("slBegTime");
		String slEndTime = req.getParameter("slEndTime");
		
	
		
		
		Map map = new HashMap();
		map.put("sldw", sldw);
		map.put("slType", slType);
		map.put("spBegTime", spBegTime);
		map.put("spEndTime", spEndTime);
		map.put("slBegTime", slBegTime);
		map.put("slEndTime", slEndTime);
		
		
		req.getSession().setAttribute("pam", map);
		
		return service.querySYdJ(map);
	}
	
	
	
	@RequestMapping(value = "/downExcelByZaiYong")
	@ResponseBody
	public void downExcelByXiaQu(HttpServletRequest req, HttpServletResponse response)
			throws SQLException, OptimusException {
		
		Map map = (Map) req.getSession().getAttribute("pam");
		
		
		List<Map> num = service.querySYdJ(map);
		QueryTsData13Utils qu = new QueryTsData13Utils();
		String str = qu.date13yZaiYong(num);
		String filename = "深圳市特种设备使用登记统计表.xls";
		InputStream inputStream = getStringStream(str);
		OutputStream outputStream = null;
		byte[] b = new byte[102400];
		int len = 0;
		try {
			outputStream = response.getOutputStream();
			response.setContentType("application/force-download");
			// filename = filename.substring(36, filename.length());
			response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
			// response.setContentLength();
			while ((len = inputStream.read(b)) != -1) {
				outputStream.write(b, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
					inputStream = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
					outputStream = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * 将一个字符串转化为输入流
	 */
	public static InputStream getStringStream(String sInputString) {
		if (sInputString != null && !sInputString.trim().equals("")) {
			try {
				ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(sInputString.getBytes("UTF-8"));
				return tInputStringStream;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
}
