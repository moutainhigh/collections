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
import com.gwssi.report.query_12315.QueryUtils;
import com.gwssi.report.tzsb.service.Data24Service;
import com.gwssi.report.tzsb.utils.QueryTsData24Utils;

/**
 * 特种设备统计
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("data24")
public class Data24Controller {

	@Autowired
	private Data24Service service;


	// 通过区域查询
	@RequestMapping(value = "/queryByXiaQu")
	@ResponseBody
	public List<Map> queryByXiaQu(HttpServletRequest req, HttpServletResponse res) throws OptimusException {
		String regCode = req.getParameter("regCode");
		String adminbrancode = req.getParameter("adminbrancode");
		String sheBeiLeiBie = req.getParameter("sheBeiLeiBie");
		Map map = new HashMap();
		map.put("regCode", regCode);
		map.put("adminbrancode", adminbrancode);
		map.put("sheBeiLeiBie", sheBeiLeiBie);
		HttpSession session = req.getSession();
		req.getSession().setAttribute("pam", map);
		
		return service.getListByQuYu(map);
	}

	// 通过区域查询生成excel下载
	@RequestMapping(value = "/downExcelByXiaQu")
	@ResponseBody
	public void downExcelByXiaQu(HttpServletRequest req, HttpServletResponse response)
			throws SQLException, OptimusException {
		
		
		Map map = (Map) req.getSession().getAttribute("pam");
		String type = req.getParameter("type");
		
		List<Map> num = service.getListByQuYu(map);
		QueryTsData24Utils qu = new QueryTsData24Utils();
		String str = qu.excelByXiaQu(num);
		System.out.println(str);
		String filename = "特种设备统计（区域）.xls";
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

	// 通过品种查询
	@RequestMapping(value = "/queryByPingZhong")
	@ResponseBody
	public List<Map> queryByPingZhong(HttpServletRequest req, HttpServletResponse res) throws OptimusException {
		String regCode = req.getParameter("regCode");
		String adminbrancode = req.getParameter("adminbrancode");
		String sheBeiLeiBie = req.getParameter("sheBeiLeiBie");
		Map map = new HashMap();
		map.put("regCode", regCode);
		map.put("adminbrancode", adminbrancode);
		map.put("sheBeiLeiBie", sheBeiLeiBie);
		
		
		HttpSession session = req.getSession();
		req.getSession().setAttribute("pam", map);
		
		return service.getListByPinZhong(map);
	}

	// 通过品种查询生成excel下载
	@RequestMapping(value = "/downExcelByPingZhong")
	@ResponseBody
	public void downExcelByPingZhong(HttpServletRequest req, HttpServletResponse response)
			throws SQLException, OptimusException {
		
		
		Map map = (Map) req.getSession().getAttribute("pam");
		String type = req.getParameter("type");
		
		List<Map> num = service.getListByPinZhong(map);
		QueryTsData24Utils qu = new QueryTsData24Utils();
		String str = qu.ExcelByPingZhong(num);
		String filename = "特种设备统计（品种）.xls";
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
