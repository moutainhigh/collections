package com.gwssi.report.shangshi;

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
import com.gwssi.report.query_12315.QueryUtils;
import com.gwssi.report.service.ShangShiZhuTiService;
@Controller
@RequestMapping("/shangShiZhuTi")
public class ShangShiZhuTiController {
	
		@Autowired
		private ShangShiZhuTiService query;
		/**
		 * 全系统全流程网上商事登记业务办理情况表
		 */
		@SuppressWarnings("rawtypes")
		@RequestMapping("/dengjiBanJieInfo")
		@ResponseBody
		public Map<String, Map> dengjiBanJieInfo(HttpServletRequest req,
				HttpServletResponse res) {
			String endTime = req.getParameter("endtime");
			String beginTime = req.getParameter("begintime");
			return query.dengjiBanJieInfo(beginTime, endTime, 1, req);
		}
		/**
		 *  全系统全流程网上商事登记业务办理情况表下载
		 */
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/dengjiBanJieInfoDown")
		@ResponseBody
		public void dengjiBanJieInfoDown(HttpServletRequest req,
				HttpServletResponse response) throws SQLException, OptimusException {
			String endTime = req.getParameter("endtime");
			String beginTime = req.getParameter("begintime");
			Map<String,Map> num = query.dengjiBanJieInfo(beginTime, endTime, 2, req);
			DengjiBanJieUtil qu = new DengjiBanJieUtil();
			String str = qu.dengjiBanJieInfoDown(num);
			String filename = "全系统全流程网上商事登记业务办理情况表(" + beginTime + "至" + endTime + ")_"
					+ this.getTime()
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
		
		//dengjiExceedInfo
		
		/**
		 * 全流程网上商事登记业务超期办理情况表
		 */
		@SuppressWarnings("rawtypes")
		@RequestMapping("/dengjiExceedInfo")
		@ResponseBody
		public List<Map> dengjiExceedInfo(HttpServletRequest req,
				HttpServletResponse res) {
			String endTime = req.getParameter("endtime");
			String beginTime = req.getParameter("begintime");
			return query.dengjiExceedInfo(beginTime, endTime, 1, req);
		}
		
		/**
		 * 下载 --全流程网上商事登记业务超期办理情况表
		 */
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/dengjiExceedInfoDown")
		@ResponseBody
		public void downQianHaiGangZiSl(HttpServletRequest req,
				HttpServletResponse response) throws SQLException, OptimusException {
			String endTime = req.getParameter("endtime");
			String beginTime = req.getParameter("begintime");
			List<Map>  num = query.dengjiExceedInfo( beginTime,endTime, 2, req);
			DengjiBanJieUtil qu = new DengjiBanJieUtil();
			String str = qu.qianHaiGangZiSl(num);
			String filename = "全流程网上商事登记业务超期办理情况表("+beginTime+"到"+endTime+")_"
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
		public String getTime(){
			return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		}
}
