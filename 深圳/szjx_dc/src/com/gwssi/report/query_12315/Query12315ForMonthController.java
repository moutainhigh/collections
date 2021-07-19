package com.gwssi.report.query_12315;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.report.service.Query12315ForMonthService;



//FOR 459546999 李唐
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/quert12315ForMonthController")
public class Query12315ForMonthController {

	@Autowired
	private Query12315ForMonthService query;
	
	

	/**
	 * 消费者投诉处理情况表
	 */
	@RequestMapping("/queryDate")
	@ResponseBody
	public List<Map> getValue(HttpServletRequest req, HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		return query.getData(beginTime, endTime, 1, req);
	}

	/**
	 * 消费者投诉处理情况表下载
	 */
	@RequestMapping(value = "/downExcel")
	@ResponseBody
	public void download(HttpServletRequest req, HttpServletResponse response)
			throws SQLException, OptimusException {
		QueryUtilsForMonth quMoth = new QueryUtilsForMonth();
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String sb = quMoth.getBuffer(query.getData(beginTime, endTime, 2, req));
		String filename = "消费者投诉处理情况表(" + beginTime + "至" + endTime + ")_"+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+ ".xls";
		InputStream inputStream = getStringStream(sb);
		OutputStream outputStream = null;
		byte[] b = new byte[102400];
		int len = 0;
		try {
			outputStream = response.getOutputStream();
			response.setContentType("application/force-download");
			// filename = filename.substring(36, filename.length());
			response.addHeader("Content-Disposition", "attachment; filename="
					+ URLEncoder.encode(filename, "UTF-8"));
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
				ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(
						sInputString.getBytes("UTF-8"));
				return tInputStringStream;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
}
