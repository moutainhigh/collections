package com.gwssi.report.tzsb.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.report.tzsb.service.Data18Service;
import com.gwssi.report.tzsb.utils.QueryTsData17Utils;
import com.gwssi.report.tzsb.utils.QueryTsData18Utils;



//
@Controller
@RequestMapping("data18")
public class Data18Controller {
	
	@Autowired
	private Data18Service service;
	

	// 通过区域查询
	@RequestMapping(value = "/queryListByDingJianLv")
	@ResponseBody
	public List<Map> queryByXiaQu(HttpServletRequest req, HttpServletResponse res) throws OptimusException {
		String regCode = req.getParameter("regCode");
		String adminbrancode = req.getParameter("adminbrancode");
		String sheBeiLeiBie = req.getParameter("sheBeiLeiBie");
		String jianGuanSuo = req.getParameter("jianGuanSuo");
		
		
		Map map = new HashMap();
		
		
		map.put("regCode", regCode);//注册局
		map.put("adminbrancode", adminbrancode);//监管所
		//map.put("sheBeiLeiBie", sheBeiLeiBie);
		map.put("jianguasuo", jianGuanSuo);
		
		
		System.out.println(map);
		req.getSession().setAttribute("pam", map);
		
		
		return service.queryListByDingJianLv(map);
	}
	
	
	
	@RequestMapping(value = "/downExcelByDingJianLv")
	@ResponseBody
	public void downExcelByXiaQu(HttpServletRequest req, HttpServletResponse response)
			throws SQLException, OptimusException, UnsupportedEncodingException {
		
		
		Map map = (Map) req.getSession().getAttribute("pam");
		String type = req.getParameter("type");
		
		
		List<Map> num = service.queryListByDingJianLv(map);
		QueryTsData18Utils qu = new QueryTsData18Utils();
		String str = qu.date18ByDingDian(num,type,map);
		String filename = "特种设备定检率统计.xls";
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
