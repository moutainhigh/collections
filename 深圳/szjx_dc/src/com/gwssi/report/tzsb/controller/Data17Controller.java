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
import com.gwssi.report.tzsb.service.Data17Service;
import com.gwssi.report.tzsb.service.Data18Service;
import com.gwssi.report.tzsb.utils.QueryTsData17Utils;

//
@Controller
@RequestMapping("data17")
public class Data17Controller {

	@Autowired
	private Data17Service service;

	// 在用设备统计
	@RequestMapping(value = "/queryListByZaiYong")
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
		
		
		return service.queryListZaiYong(map);
	}

	// 通过使用单位统计
	@RequestMapping(value = "/queryShiYongDanWei")
	@ResponseBody
	public List<Map> queryShiYongDanWei(HttpServletRequest req, HttpServletResponse res) throws OptimusException {
		String regCode = req.getParameter("regCode");
		String adminbrancode = req.getParameter("adminbrancode");
		String sheBeiLeiBie = req.getParameter("sheBeiLeiBie");
		Map map = new HashMap();
		map.put("regCode", regCode);
		map.put("adminbrancode", adminbrancode);
		map.put("sheBeiLeiBie", sheBeiLeiBie);
		HttpSession session = req.getSession();
		req.getSession().setAttribute("pam", map);
		
		return service.queryListByDanWei(map);
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

	// 通过区域查询生成excel下载
	@RequestMapping(value = "/downExcelByZaiYong")
	@ResponseBody
	public void downExcelByXiaQu(HttpServletRequest req, HttpServletResponse response)
			throws SQLException, OptimusException {
		
		
		Map map = (Map) req.getSession().getAttribute("pam");
		String type = req.getParameter("type");
		
		
		List<Map> num = service.queryListZaiYong(map);
		QueryTsData17Utils qu = new QueryTsData17Utils();
		String str = qu.date17ByZaiYong(num);
		String filename = "设备所在场所的统计查询（按区域）.xls";
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

	// 通过单位查询生成excel下载
	@RequestMapping(value = "/downExcelByDangWei")
	@ResponseBody
	public void downExcelByDanwei(HttpServletRequest req, HttpServletResponse response)
			throws SQLException, OptimusException {
		
		
		Map map = (Map) req.getSession().getAttribute("pam");
		String type = req.getParameter("type");
		
		
		List<Map> num = service.queryListByDanWei(map);
		QueryTsData17Utils qu = new QueryTsData17Utils();
		String str = qu.date17ByDanWei(num);
		String filename = "设备所在场所的统计查询（按使用单位）.xls";
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
}
