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
import com.gwssi.report.tzsb.service.Data14Service;
import com.gwssi.report.tzsb.utils.QueryTsData13Utils;
import com.gwssi.report.tzsb.utils.QueryTsData14Utils;

@Controller
@RequestMapping("data14")
public class Data14Controller {

	@Autowired
	private Data14Service service;


	
	
	
	
	
	@ResponseBody
	@RequestMapping("tjxm_code_value")
	public void getCode(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		HttpServletRequest request = req.getHttpRequest();
		resp.addTree("sldw", service.tjxm_code_value());
	}
	
	
	

	@ResponseBody
	@RequestMapping("sggzlx_code_value")
	public void slType_code_value(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		HttpServletRequest request = req.getHttpRequest();
		resp.addTree("slType", service.sggzlx_code_value());
	}
	
	
	
	
	@RequestMapping(value = "/queryByDw")
	@ResponseBody
	public List<Map> queryByDw(HttpServletRequest req, HttpServletResponse res) throws OptimusException {
		String sldw = req.getParameter("tjxm");
		String slType = req.getParameter("sggzlx");
		String spBegTime = req.getParameter("spBegTime");
		String spEndTime = req.getParameter("spEndTime");

		Map map = new HashMap();
		map.put("tjxm", sldw);
		map.put("sggzlx", slType);
		map.put("spBegTime", spBegTime);
		map.put("spEndTime", spEndTime);

		req.getSession().setAttribute("pam", map);

		return service.queryByDw(map);
	}

	@RequestMapping(value = "/queryByXiaQu")
	@ResponseBody
	public List<Map> queryByXiaQu(HttpServletRequest req, HttpServletResponse res) throws OptimusException {
		String sldw = req.getParameter("tjxm");
		String slType = req.getParameter("sggzlx");
		String spBegTime = req.getParameter("spBegTime");
		String spEndTime = req.getParameter("spEndTime");

		Map map = new HashMap();
		map.put("tjxm", sldw);
		map.put("sggzlx", slType);
		map.put("spBegTime", spBegTime);
		map.put("spEndTime", spEndTime);

		req.getSession().setAttribute("pam", map);
		return service.queryByXiaQu(map);
	}

	@RequestMapping(value = "/downExcelByDw")
	@ResponseBody
	public void downExcelByDw(HttpServletRequest req, HttpServletResponse response)
			throws SQLException, OptimusException {

		Map map = (Map) req.getSession().getAttribute("pam");

		List<Map> num = service.queryByDw(map);
		QueryTsData14Utils qu = new QueryTsData14Utils();
		String str = qu.downExcelByDw(num);
		String filename = "特种设备施工告知受理情况统计查询（按受理单位）.xls";
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

	@RequestMapping(value = "/downExcelByXiaQu")
	@ResponseBody
	public void downExcelByXiaQu(HttpServletRequest req, HttpServletResponse response)
			throws SQLException, OptimusException {

		Map map = (Map) req.getSession().getAttribute("pam");

		List<Map> num = service.queryByXiaQu(map);
		QueryTsData14Utils qu = new QueryTsData14Utils();
		String str = qu.date14ByXiaQu(num);
		String filename = "特种设备施工告知受理情况统计查询(按辖区).xls";
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
