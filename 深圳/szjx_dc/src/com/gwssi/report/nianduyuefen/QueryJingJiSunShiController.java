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
import com.gwssi.report.service.QueryJingJiSunShiService;

@SuppressWarnings({ "rawtypes" })
@Controller
@RequestMapping("/queryJingJiSunShi")
public class QueryJingJiSunShiController {
	@Autowired
	private QueryJingJiSunShiService query;
	private QueryJingJiSunShiUtils qu = new QueryJingJiSunShiUtils();
	private final static SimpleDateFormat SDF = new SimpleDateFormat(
			"yyyyMMddHHmmss");
	/**
	 * 登记部门挽回经济损失总额年度月份对比表
	 */
	@RequestMapping("/dengJiBuMen")
	@ResponseBody
	public Map<String, List<Map>> dengJiBuMen(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		return query.dengJiBuMen(beginTime, endTime, infotype, 1, req);
	}
	
	/**
	 * 下载 --登记部门挽回经济损失总额年度月份对比表
	 */
	@RequestMapping("/downDengJiBuMen")
	@ResponseBody
	public void downDengJiBuMen(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		Map<String, List<Map>> num = query.dengJiBuMen(beginTime, endTime,
				infotype, 2, req);
		if (num == null || num.size() == 0) {
			return;
		}
		String str = qu.getExcelFile(num, "登记部门");
		String filename = "登记部门挽回经济损失总额年度月份对比表" + "(" + beginTime + "至" + endTime
				+ ")_" + this.getTimeString() + ".xls";
		this.down(response, filename, str);
	}
	
	/**
	 * 登记人挽回经济损失总额年度月份对比表
	 */
	@RequestMapping("/dengJiRen")
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
	 * 下载 --登记人挽回经济损失总额年度月份对比表
	 */
	@RequestMapping("/downDengJiRen")
	@ResponseBody
	public void downDengJiRen(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		String regcode = req.getParameter("regcode");
		Map<String, List<Map>> num = query.dengJiRen(beginTime, endTime, infotype,regcode, 2,
				req);
		if (num == null || num.size() == 0) {
			return;
		}
		String str = qu.getExcelFile(num, "登记人");
		String filename = "登记人挽回经济损失总额年度月份对比表" + "(" + beginTime + "至" + endTime
				+ ")_" + this.getTimeString() + ".xls";
		this.down(response, filename, str);
	}
	
	/**
	 * 承办部门挽回经济损失总额年度月份对比表
	 */
	@RequestMapping("/chengBanBuMen")
	@ResponseBody
	public Map<String, List<Map>> chengBanBuMen(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		return query.chengBanBuMen(beginTime, endTime, infotype, 1, req);
	}
	
	/**
	 * 下载 --承办部门挽回经济损失总额年度月份对比表
	 */
	@RequestMapping("/downChengBanBuMen")
	@ResponseBody
	public void downChengBanBuMen(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		Map<String, List<Map>> num = query.chengBanBuMen(beginTime, endTime,
				infotype, 2, req);
		if (num == null || num.size() == 0) {
			return;
		}
		String str = qu.getExcelFile(num, "承办部门");
		String filename = "承办部门挽回经济损失总额年度月份对比表" + "(" + beginTime + "至" + endTime
				+ ")_" + this.getTimeString() + ".xls";
		this.down(response, filename, str);
	}
	
	/**
	 * 承办人挽回经济损失总额年度月份对比表
	 */
	@RequestMapping("/chengBanRen")
	@ResponseBody
	public Map<String, List<Map>> chengBanRen(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		String regcode = req.getParameter("regcode");
		return query.chengBanRen(beginTime, endTime, infotype,regcode, 1,
				req);
	}
	
	/**
	 * 下载 --承办人挽回经济损失总额年度月份对比表
	 */
	@RequestMapping("/downChengBanRen")
	@ResponseBody
	public void downChengBanRen(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		String regcode = req.getParameter("regcode");
		Map<String, List<Map>> num = query.chengBanRen(beginTime, endTime, infotype,regcode, 2,
				req);
		if (num == null || num.size() == 0) {
			return;
		}
		String str = qu.getExcelFile(num, "承办人");
		String filename = "承办人挽回经济损失总额年度月份对比表" + "(" + beginTime + "至" + endTime
				+ ")_" + this.getTimeString() + ".xls";
		this.down(response, filename, str);
	}
	
	/**
	 * 来源方式挽回经济损失总额年度月份对比表
	 */
	@RequestMapping("/laiYuanFangShi")
	@ResponseBody
	public Map<String, List<Map>> laiYuanFangShi(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		return query.laiYuanFangShi(beginTime, endTime, infotype, 1, req);
	}
	
	/**
	 * 下载 --来源方式挽回经济损失总额年度月份对比表
	 */
	@RequestMapping("/downLaiYuanFangShi")
	@ResponseBody
	public void downLaiYuanFangShi(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		Map<String, List<Map>> num = query.laiYuanFangShi(beginTime, endTime,
				infotype, 2, req);
		if (num == null || num.size() == 0) {
			return;
		}
		String str = qu.getExcelFile(num, "来源方式");
		String filename = "来源方式挽回经济损失总额年度月份对比表" + "(" + beginTime + "至" + endTime
				+ ")_" + this.getTimeString() + ".xls";
		this.down(response, filename, str);
	}
	
	/**
	 * 接收方式挽回经济损失总额年度月份对比表
	 */
	@RequestMapping("/jieShouFangShi")
	@ResponseBody
	public Map<String, List<Map>> jieShouFangShi(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		return query.jieShouFangShi(beginTime, endTime, infotype, 1, req);
	}
	
	/**
	 * 下载 --接收方式挽回经济损失总额年度月份对比表
	 */
	@RequestMapping("/downJieShouFangShi")
	@ResponseBody
	public void downJieShouFangShi(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		Map<String, List<Map>> num = query.jieShouFangShi(beginTime, endTime,
				infotype, 2, req);
		if (num == null || num.size() == 0) {
			return;
		}
		String str = qu.getExcelFile(num, "接收方式");
		String filename = "接收方式挽回经济损失总额年度月份对比表" + "(" + beginTime + "至" + endTime
				+ ")_" + this.getTimeString() + ".xls";
		this.down(response, filename, str);
	}
	
	/**
	 * 行业类型挽回经济损失总额年度月份对比表
	 */
	@RequestMapping("/hangYeLeiXing")
	@ResponseBody
	public Map<String, List<Map>> hangYeLeiXing(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		return query.hangYeLeiXing(beginTime, endTime, infotype, 1, req);
	}
	
	/**
	 * 下载 --行业类型挽回经济损失总额年度月份对比表
	 */
	@RequestMapping("/downHangYeLeiXing")
	@ResponseBody
	public void downHangYeLeiXing(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		Map<String, List<Map>> num = query.hangYeLeiXing(beginTime, endTime,
				infotype, 2, req);
		if (num == null || num.size() == 0) {
			return;
		}
		String str = qu.getExcelFile(num, "行业类型");
		String filename = "行业类型挽回经济损失总额年度月份对比表" + "(" + beginTime + "至" + endTime
				+ ")_" + this.getTimeString() + ".xls";
		this.down(response, filename, str);
	}
	
	/**
	 * 问题性质挽回经济损失总额年度月份对比表
	 */
	@RequestMapping("/wenTiXingZhi")
	@ResponseBody
	public Map<String, List<Map>> wenTiXingZhi(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		return query.wenTiXingZhi(beginTime, endTime, infotype, 1, req);
	}
	
	/**
	 * 下载 --问题性质挽回经济损失总额年度月份对比表
	 */
	@RequestMapping("/downWenTiXingZhi")
	@ResponseBody
	public void downWenTiXingZhi(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		Map<String, List<Map>> num = query.wenTiXingZhi(beginTime, endTime,
				infotype, 2, req);
		if (num == null || num.size() == 0) {
			return;
		}
		String str = qu.getExcelFile(num, "问题性质");
		String filename = "问题性质挽回经济损失总额年度月份对比表" + "(" + beginTime + "至" + endTime
				+ ")_" + this.getTimeString() + ".xls";
		this.down(response, filename, str);
	}
	
	/**
	 * 业务范围挽回经济损失总额年度月份对比表
	 */
	@RequestMapping("/yeWuFanWei")
	@ResponseBody
	public Map<String, List<Map>> yeWuFanWei(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		return query.yeWuFanWei(beginTime, endTime, infotype, 1, req);
	}
	
	/**
	 * 下载 --业务范围挽回经济损失总额年度月份对比表
	 */
	@RequestMapping("/downYeWuFanWeis")
	@ResponseBody
	public void downYeWuFanWeis(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		Map<String, List<Map>> num = query.yeWuFanWei(beginTime, endTime,
				infotype, 2, req);
		if (num == null || num.size() == 0) {
			return;
		}
		String str = qu.getExcelFile(num, "业务范围");
		String filename = "业务范围挽回经济损失总额年度月份对比表" + "(" + beginTime + "至" + endTime
				+ ")_" + this.getTimeString() + ".xls";
		this.down(response, filename, str);
	}
	
	/**
	 * 所属部门挽回经济损失总额年度月份对比表
	 */
	@RequestMapping("/suoShuBuMen")
	@ResponseBody
	public Map<String, List<Map>> suoShuBuMen(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		return query.suoShuBuMen(beginTime, endTime, infotype, 1, req);
	}
	
	/**
	 * 下载 --所属部门挽回经济损失总额年度月份对比表
	 */
	@RequestMapping("/downSuoShuBuMen")
	@ResponseBody
	public void downSuoShuBuMen(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		Map<String, List<Map>> num = query.suoShuBuMen(beginTime, endTime,
				infotype, 2, req);
		if (num == null || num.size() == 0) {
			return;
		}
		String str = qu.getExcelFile(num, "所属部门");
		String filename = "所属部门挽回经济损失总额年度月份对比表" + "(" + beginTime + "至" + endTime
				+ ")_" + this.getTimeString() + ".xls";
		this.down(response, filename, str);
	}
	
	
	/**
	 * 涉及客体挽回经济损失总额年度月份对比统计表
	 */
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
	 * 下载 --涉及客体挽回经济损失总额年度月份对比统计表
	 */
	@RequestMapping("/downShejiketi")
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
		String filename = "涉及客体挽回经济损失总额年度月份对比统计表" + "(" + beginTime + "至" + endTime + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	
	/**
	 * @param response
	 * @param filename
	 * @param sb
	 *            下载函数
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
	public String getTimeString() {
		return SDF.format(new Date());
	}
}
