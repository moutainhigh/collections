package com.gwssi.report.shenziqiye;

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

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.report.query_12315.QueryUtils;
import com.gwssi.report.shenziqiye.service.ShengZiQiYeHangYeService;


@Controller
@RequestMapping("/szhy")
public class ShenZiQiYeHangYeController {
	@Autowired
	private ShengZiQiYeHangYeService query;
	SimpleDateFormat myFmt1=new SimpleDateFormat("yyyy-MM-dd");
	Date now=new Date();
	
	@RequestMapping("/hangye")
	@ResponseBody
	public List<Map> getValue(HttpServletRequest req, HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		
		if(StringUtils.isEmpty(endTime)) {
			endTime = myFmt1.format(now);
		}
		
		
		if(StringUtils.isEmpty(beginTime)) {
			beginTime = myFmt1.format(now);
		}
		
		return query.query(beginTime, endTime, req);
	}
	
	
	@RequestMapping(value = "/downHangYe")
	@ResponseBody
	public void downQianHaiZongLiang(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		List<Map> num = query.query(beginTime, endTime, req);
		HangYeUtil qu = new HangYeUtil();
		String str = qu.hangYeUtils(num);
		String filename = "深资背景企业行业分布(" + beginTime + "到" + endTime + ")_"
				+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
				+ ".xls";
		InputStream inputStream = getStringStream(str);
		OutputStream outputStream = null;
		byte[] b = new byte[102400];
		int len = 0;
		try {
			outputStream = response.getOutputStream();
			response.setContentType("application/force-download");
			response.addHeader("Content-Disposition", "attachment; filename="
					+ URLEncoder.encode(filename, "UTF-8"));
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
