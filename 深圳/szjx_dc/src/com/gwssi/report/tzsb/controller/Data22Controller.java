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
import com.gwssi.report.tzsb.service.Data22Service;
import com.gwssi.report.tzsb.service.Data24Service;
import com.gwssi.report.tzsb.utils.QueryTsData22Utils;

/**
 * 特种设备统计
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("data22")
public class Data22Controller {

	@Autowired
	private Data22Service service;

	

	@ResponseBody
	@RequestMapping("sldw_code_value")
	public void getSheBeiStatus(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		HttpServletRequest request = req.getHttpRequest();
		resp.addTree("sldw", service.getSheBeiStatus());
	}
	
	
	
	
	

	// 通过区域查询
	@RequestMapping(value = "/queryByChangSuo")
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
		
		return service.queryListByChangSuo(map);
	}

	// 通过区域查询生成excel下载
	@RequestMapping(value = "/downExcelByChangSuo")
	@ResponseBody
	public void downExcelByXiaQu(HttpServletRequest req, HttpServletResponse response)
			throws SQLException, OptimusException {
		
		
		
		Map map = (Map) req.getSession().getAttribute("pam");
		String type = req.getParameter("type");
		
		List<Map> num = service.queryListByChangSuo(map);
		QueryTsData22Utils qu = new QueryTsData22Utils();
		String str = qu.excelByXiaQu(num);
		String filename = "设备所在场所的统计查询（按场所）.xls";
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

	
	
	
	
	
	
	
	
	// 通过类型查询
	@RequestMapping(value = "/queryByType")
	@ResponseBody
	public List<Map> queryByType(HttpServletRequest req, HttpServletResponse res) throws OptimusException {
		String regCode = req.getParameter("regCode");
		String adminbrancode = req.getParameter("adminbrancode");
		String sheBeiLeiBie = req.getParameter("sheBeiLeiBie");
		Map map = new HashMap();
		map.put("regCode", regCode);
		map.put("adminbrancode", adminbrancode);
		map.put("sheBeiLeiBie", sheBeiLeiBie);
		HttpSession session = req.getSession();
		req.getSession().setAttribute("pam", map);
		
		return service.queryByType(map);
	}

	// 通过类型查询生成excel下载
	@RequestMapping(value = "/downExcelByType")
	@ResponseBody
	public void downExcelByPingZhong(HttpServletRequest req, HttpServletResponse response)
			throws SQLException, OptimusException {
		
		Map map = (Map) req.getSession().getAttribute("pam");
		String type = req.getParameter("type");
		
		
		List<Map> num = service.queryByType(map);
		QueryTsData22Utils qu = new QueryTsData22Utils();
		String str = qu.ExcelByType(num);
		String filename = "设备所在场所的统计查询（设备类型）.xls";
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
