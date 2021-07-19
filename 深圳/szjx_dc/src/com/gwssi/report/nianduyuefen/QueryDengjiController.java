package com.gwssi.report.nianduyuefen;

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
import com.gwssi.report.service.QueryDengJiService;

@Controller
@RequestMapping("/queryDengJi")
public class QueryDengjiController {
	@Autowired
	private QueryDengJiService query;
	private QueryDengjiUtils qu = new QueryDengjiUtils();
	private final static SimpleDateFormat SDF=new SimpleDateFormat("yyyyMMddHHmmss");
	/**
	 * 登记部门登记量年度月份对比表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/dengJiBuMen")
	@ResponseBody
	public Map<String, List<Map>> dengJiBuMen(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		return query.dengJiBuMen(beginTime, endTime, infotype, 1,
				req);
	}
	
	/**
	 * 下载 --登记部门登记量年度月份对比表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/downDengJiBuMen")
	@ResponseBody
	public void downDengJiBuMen(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		Map<String, List<Map>> num = query.dengJiBuMen(beginTime, endTime, infotype,2, req);
		if (num==null||num.size()==0) {
			return;
		}
		String str = qu.getExcelFile(num,"登记部门");
		String filename = "登记部门登记量年度月份对比表" + "(" + beginTime + "至" + endTime + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	/**
	 * 登记人登记数年度月份对比统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/dengJiRen")
	@ResponseBody
	public Map<String, List<Map>> dengJiRen(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		String regcode = req.getParameter("regcode");
		return query.dengJiRen(beginTime, endTime, infotype,regcode, 1,
				req);
	}
	
	/**
	 * 下载 --登记人登记数年度月份对比统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/downDengJiRen")
	@ResponseBody
	public void downDengJiRen(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		String regcode = req.getParameter("regcode");
		Map<String, List<Map>> num = query.dengJiRen(beginTime, endTime, infotype,regcode,2, req);
		if (num==null||num.size()==0) {
			return;
		}
		String str = qu.getExcelFile(num,"登记人");
		String filename = "登记人登记数年度月份对比统计表" + "(" + beginTime + "至" + endTime + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	/**
	 * 来源方式登记量年度月份对比统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/laiYuan")
	@ResponseBody
	public Map<String, List<Map>> laiYuan(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		return query.laiYuan(beginTime, endTime, infotype, 1,
				req);
	}
	
	/**
	 * 下载 --来源方式登记量年度月份对比统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/downLaiYuan")
	@ResponseBody
	public void downLaiYuan(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		Map<String, List<Map>> num = query.laiYuan(beginTime, endTime, infotype,2, req);
		if (num==null||num.size()==0) {
			return;
		}
		String str =qu.getExcelFile(num,"来源方式");
		String filename = "来源方式登记量年度月份对比统计表" + "(" + beginTime + "至" + endTime + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	
	/**
	 * 接收方式登记量年度月份对比统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/jieShou")
	@ResponseBody
	public Map<String, List<Map>> jieShou(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		return query.jieShou(beginTime, endTime, infotype, 1,
				req);
	}
	
	
	/**
	 * 下载 --接收方式登记量年度月份对比统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/downJieShou")
	@ResponseBody
	public void downJieShou(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		Map<String, List<Map>> num = query.jieShou(beginTime, endTime, infotype,2, req);
		if (num==null||num.size()==0) {
			return;
		}
		String str = qu.getExcelFile(num,"接收方式");
		String filename = "接收方式登记量年度月份对比统计表" + "(" + beginTime + "至" + endTime + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	
	/**
	 * 行业类型登记量年度月份对比统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/hangYe")
	@ResponseBody
	public Map<String, List<Map>> hangYe(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		return query.hangYe(beginTime, endTime, infotype, 1,
				req);
	}
	
	
	/**
	 * 下载 --行业类型登记量年度月份对比统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/downHangYe")
	@ResponseBody
	public void downHangYe(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		Map<String, List<Map>> num = query.hangYe(beginTime, endTime, infotype,2, req);
		if (num==null||num.size()==0) {
			return;
		}
		String str = qu.getExcelFile(num,"行业类型");
		String filename = "行业类型登记量年度月份对比统计表" + "(" + beginTime + "至" + endTime + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	
	/**
	 * 问题性质登记量年度月份对比统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/wenTi")
	@ResponseBody
	public Map<String, List<Map>> wenTi(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		return query.wenTi(beginTime, endTime, infotype, 1,
				req);
	}
	
	/**
	 * 下载 --问题性质登记量年度月份对比统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/downWenTi")
	@ResponseBody
	public void downWenTi(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		Map<String, List<Map>> num = query.wenTi(beginTime, endTime, infotype,2, req);
		if (num==null||num.size()==0) {
			return;
		}
		String str = qu.getExcelFile(num,"问题性质");
		String filename = "问题性质登记量年度月份对比统计表" + "(" + beginTime + "至" + endTime + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	/**
	 * 业务范围登记量年度月份对比统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/yeWu")
	@ResponseBody
	public Map<String, List<Map>> yeWu(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		return query.yeWu(beginTime, endTime, infotype, 1,
				req);
	}
	
	/**
	 * 下载 --业务范围登记量年度月份对比统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/downYeWu")
	@ResponseBody
	public void downYeWu(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		Map<String, List<Map>> num = query.yeWu(beginTime, endTime, infotype,2, req);
		if (num==null||num.size()==0) {
			return;
		}
		String str = qu.getExcelFile(num,"业务范围");
		String filename = "业务范围登记量年度月份对比统计表" + "(" + beginTime + "至" + endTime + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	
	/**
	 * 所属部门登记量年度月份对比统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/suoShuBuMen")
	@ResponseBody
	public Map<String, List<Map>> suoShuBuMen(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		return query.suoShuBuMen(beginTime, endTime, infotype, 1,
				req);
	}
	
	/**
	 * 下载 --所属部门登记量年度月份对比统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/downSuoShuBuMen")
	@ResponseBody
	public void downSuoShuBuMen(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		Map<String, List<Map>> num = query.suoShuBuMen(beginTime, endTime, infotype,2, req);
		if (num==null||num.size()==0) {
			return;
		}
		String str = qu.getExcelFile(num,"所属部门");
		String filename = "所属部门登记量年度月份对比统计表" + "(" + beginTime + "至" + endTime + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	/**
	 * 涉及客体登记量年度月份对比统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/sheJiKeTi")
	@ResponseBody
	public Map<String, List<List<Map>>> sheJiKeTi(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		String shejiketi = req.getParameter("shejiketi");
		return query.sheJiKeTi(beginTime, endTime, infotype,shejiketi, 1,
				req);
	}
	
	/**
	 * 下载 --涉及客体登记量年度月份对比统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/downSheJiKeTi")
	@ResponseBody
	public void downSheJiKeTi(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		String shejiketi = req.getParameter("shejiketi");
		Map<String, List<List<Map>>> num = query.sheJiKeTi(beginTime, endTime, infotype,shejiketi,2, req);
		if (num==null||num.size()==0) {
			return;
		}
		String str = qu.downSheJiKeTi(num);
		String filename = "涉及客体登记量年度月份对比统计表" + "(" + beginTime + "至" + endTime + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	
	
	/**
	 * @param response
	 * @param filename
	 * @param sb
	 * 下载函数
	 */
	public void down(HttpServletResponse response, String filename, String sb) {
		InputStream inputStream = getStringStream(sb.toString());
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
	
	
	
	/**
	 * 获取一个精确到秒的时间字符串
	 */
	public String getTimeString(){
		return SDF.format(new Date());
	}
}
