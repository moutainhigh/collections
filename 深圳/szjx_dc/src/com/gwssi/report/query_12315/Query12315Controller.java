package com.gwssi.report.query_12315;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.report.excelmodel.JiChaBiao01;
import com.gwssi.report.excelmodel.JiaGeJuBaoXinXi;
import com.gwssi.report.service.Query12315Service;

@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/quert12315Controller")
public class Query12315Controller {
	@Autowired
	private Query12315Service query;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
		QueryUtils qu = new QueryUtils();
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String sb = qu.getBuffer(query.getData(beginTime, endTime, 2, req));
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
	 * 话务员登记量报表
	 */
	@RequestMapping(value = "/queryYuWuDj")
	@ResponseBody
	public List<Map> queryYuWuNum(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String regcode = req.getParameter("regcode");
		return query.getYeWuNum(beginTime, endTime, 1, req, regcode);
	}

	/**
	 * 话务员登记量报表下载
	 */
	@RequestMapping(value = "/downExcelHuaWuDj")
	@ResponseBody
	public void downloadYeWu(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String regcode = req.getParameter("regcode");
		List<Map> num = query.getYeWuNum(beginTime, endTime, 2, req, regcode);
		QueryUtils qu = new QueryUtils();
		String str = qu.getHuaWuNum(num);
		String filename = "话务员登记量报表(" + beginTime + "至" + endTime + ")_"
				+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
				+ ".xls";
		InputStream inputStream = getStringStream(str);
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
	 * 话务员登记提交量统计报表
	 */
	@RequestMapping(value = "/queryYuWuTj")
	@ResponseBody
	public List<Map> queryYuWuTj(HttpServletRequest req, HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String regcode = req.getParameter("regcode");
		return query.getYeWuTj(beginTime, endTime, 1,regcode, req);
	}

	/**
	 * 话务员登记提交量统计报表下载
	 */
	@RequestMapping(value = "/downExcelHuaWuTj")
	@ResponseBody
	public void downloadYeWuTj(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String regcode = req.getParameter("regcode");
		List<Map> num = query.getYeWuTj(beginTime, endTime, 2,regcode, req);
		QueryUtils qu = new QueryUtils();
		String str = qu.getHuaWuTj(num);
		String filename = "话务员登记提交量统计报表(" + beginTime + "至" + endTime + ")_"
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
	 * 登记量统计报表（按接收类型分）
	 */
	@RequestMapping(value = "/queryTongJiByType")
	@ResponseBody
	public List<Map> queryTongJiByType(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String regcode = req.getParameter("regcode");
		return query.getYeWuTongjiByType(beginTime, endTime, 1, req, regcode);
	}

	/**
	 * 下载登记量统计报表（按接收类型分）
	 */
	@RequestMapping(value = "/downExcelTongJiByType")
	@ResponseBody
	public void downloadTongJiByType(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String regcode = req.getParameter("regcode");
		List<Map> num = query.getYeWuTongjiByType(beginTime, endTime, 2, req,
				regcode);
		QueryUtils qu = new QueryUtils();
		String str = qu.getTongJiByType(num);
		String filename = "登记量统计报表（按接收类型分）(" + beginTime + "至" + endTime + ")_"
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
	 * 登记量统计报表（按承办部门分）
	 */
	@RequestMapping(value = "/queryTongJiByDept")
	@ResponseBody
	public List<Map> queryTongJiByDept(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String regcode = req.getParameter("regcode");
		return query.getYeWuTongjiByDept(beginTime, endTime, 1,regcode, req);
	}

	/**
	 * 下载登记量统计报表（按承办部门分）
	 */
	@RequestMapping(value = "/downExcelTongJiByDept")
	@ResponseBody
	public void downloadTongJiByDept(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String regcode = req.getParameter("regcode");
		List<Map> num = query.getYeWuTongjiByDept(beginTime, endTime, 2,regcode, req);
		QueryUtils qu = new QueryUtils();
		String str = qu.getTongJiByDept(num);
		String filename = "登记量统计报表（按承办部门分）(" + beginTime + "至" + endTime + ")_"
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
	 * 话务员登记热点投诉人件统计报表
	 */
	@RequestMapping(value = "/queryYuWuHot")
	@ResponseBody
	public List<Map> queryTongJiByHot(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String regcode = req.getParameter("regcode");
		return query.getYeWuHot(beginTime, endTime, 1, req, regcode);
	}

	/**
	 * 下载 --话务员登记热点投诉人件统计报表
	 */
	@RequestMapping(value = "/downExcelHuaWuHot")
	@ResponseBody
	public void downloadHuaWuHot(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String regcode = req.getParameter("regcode");
		List<Map> num = query.getYeWuHot(beginTime, endTime, 2, req, regcode);
		QueryUtils qu = new QueryUtils();
		String str = qu.getHuaWuHot(num);
		String filename = "务员登记热点投诉人件统计报表(" + beginTime + "至" + endTime + ")_"
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
	 * 热点投诉人咨询举报投诉件统计报表
	 */
	@RequestMapping(value = "/queryHotMan")
	@ResponseBody
	public List<Map> queryHotMan(HttpServletRequest req, HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String checkType = req.getParameter("checktype");
		String regcode = req.getParameter("regcode");
		if (checkType == null || checkType.length() == 0) {
			checkType = "1,2";
		}
		return query.getHotMan(beginTime, endTime, checkType, 1,regcode, req);
	}

	/**
	 * 下载 --热点投诉人咨询举报投诉件统计报表
	 */
	@RequestMapping(value = "/downExcelHotMan")
	@ResponseBody
	public void downloadHotMan(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String checkType = req.getParameter("checktype");
		String regcode = req.getParameter("regcode");
		if (checkType == null || checkType.length() == 0) {
			checkType = "1,2";
		}
		List<Map> num = query.getHotMan(beginTime, endTime, checkType, 2,regcode, req);
		QueryUtils qu = new QueryUtils();
		String str = qu.getHotMan(num);
		String filename = null;
		if (checkType.equals("1")) {
			filename = "热点投诉人咨询举报投诉件统计报表(市场监管投诉)(" + beginTime + "至" + endTime
					+ ")_"
					+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
					+ ".xls";
		} else if (checkType.equals("2")) {
			filename = "热点投诉人咨询举报投诉件统计报表(举报)(" + beginTime + "至" + endTime
					+ ")_"
					+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
					+ ".xls";
		} else {
			filename = "热点投诉人咨询举报投诉件统计报表(" + beginTime + "至" + endTime + ")_"
					+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
					+ ".xls";
		}
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
	 * 热点投诉人举报投诉撤诉统计报表
	 */
	@RequestMapping(value = "/queryHotManChe")
	@ResponseBody
	public List<Map> queryHotManChe(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String regcode = req.getParameter("regcode");
		return query.getHotManChe(beginTime, endTime, 1, req, regcode);
	}

	/**
	 * 下载 --热点投诉人举报投诉撤诉统计报表
	 */
	@RequestMapping(value = "/downExcelHotManChe")
	@ResponseBody
	public void downloadHotManChe(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String regcode = req.getParameter("regcode");
		List<Map> num = query.getHotManChe(beginTime, endTime, 2, req, regcode);
		QueryUtils qu = new QueryUtils();
		String str = qu.getHotManChe(num);
		String filename = "热点投诉人举报投诉撤诉统计报表(" + beginTime + "至" + endTime + ")_"
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
	 * 登记信息涉及金额统计表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryDjMoney")
	@ResponseBody
	public List<List<Map>> queryDjMoney(HttpServletRequest req,
			HttpServletResponse res) {
		//String endTime = req.getParameter("endtime");
		//String beginTime = req.getParameter("begintime");
		Map map=new HashMap();
		map.put("begintime",req.getParameter("begintime"));
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("shejiketi",req.getParameter("shejiketi"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("laiyuanfangshi",req.getParameter("laiyuanfangshi"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("shijianjibie",req.getParameter("shijianjibie"));
		map.put("renyuanshenfen",req.getParameter("renyuanshenfen"));
		map.put("zhutileixing",req.getParameter("zhutileixing"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("qiyeleixing",req.getParameter("qiyeleixing"));
		map.put("wangzhanleixing",req.getParameter("wangzhanleixing"));
		map.put("guanjianzi",req.getParameter("guanjianzi"));
		return query.getDjMoney(map, 1, req);
	}

	/**
	 * 下载 --登记信息涉及金额统计表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downExcelDjMoneyDown")
	@ResponseBody
	public void downloadDjMoneyDown(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		Map map=new HashMap();
		map.put("begintime",req.getParameter("begintime"));
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("shejiketi",req.getParameter("shejiketi"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("laiyuanfangshi",req.getParameter("laiyuanfangshi"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("shijianjibie",req.getParameter("shijianjibie"));
		map.put("renyuanshenfen",req.getParameter("renyuanshenfen"));
		map.put("zhutileixing",req.getParameter("zhutileixing"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("qiyeleixing",req.getParameter("qiyeleixing"));
		map.put("wangzhanleixing",req.getParameter("wangzhanleixing"));
		map.put("guanjianzi",req.getParameter("guanjianzi"));
		List<List<Map>> num = query.getDjMoney(map, 2, req);
		QueryUtils qu = new QueryUtils();
		String str = qu.getDjMoney(num);
		String filename = "登记信息涉及金额统计表(" + req.getParameter("begintime") + "至" + req.getParameter("endtime") + ")_"
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
	 * 消委会登记情况表
	 */
	@RequestMapping(value = "/queryXwh")
	@ResponseBody
	public List<Map> queryXwh(HttpServletRequest req, HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		return query.getXwh(beginTime, endTime, 1, req);
	}

	/**
	 * 下载 --消委会登记情况表
	 */
	@RequestMapping(value = "/downExcelXwh")
	@ResponseBody
	public void downloadXwh(HttpServletRequest req, HttpServletResponse response)
			throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		List<Map> num = query.getXwh(beginTime, endTime, 2, req);
		QueryUtils qu = new QueryUtils();
		String str = qu.getXwh(num);
		String filename = "消委会登记情况表(" + beginTime + "至" + endTime + ")_"
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
	 * 消委会登记信息涉及金额统计表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryXwhMoney")
	@ResponseBody
	public List<Map> queryXwhMoney(HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap();
		map.put("begintime",req.getParameter("begintime"));
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("laiyuanfangshi",req.getParameter("laiyuanfangshi"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("shijianjibie",req.getParameter("shijianjibie"));
		map.put("renyuanshenfen",req.getParameter("renyuanshenfen"));
		map.put("zhutileixing",req.getParameter("zhutileixing"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("qiyeleixing",req.getParameter("qiyeleixing"));
		map.put("wangzhanleixing",req.getParameter("wangzhanleixing"));
		map.put("guanjianzi",req.getParameter("guanjianzi"));
		return query.getXwhMoney(map, 1, req);
	}

	/**
	 * 下载 --消委会登记信息涉及金额统计表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downExcelXwhMoney")
	@ResponseBody
	public void downloadXwhMoney(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		Map map=new HashMap();
		map.put("begintime",req.getParameter("begintime"));
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("laiyuanfangshi",req.getParameter("laiyuanfangshi"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("shijianjibie",req.getParameter("shijianjibie"));
		map.put("renyuanshenfen",req.getParameter("renyuanshenfen"));
		map.put("zhutileixing",req.getParameter("zhutileixing"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("qiyeleixing",req.getParameter("qiyeleixing"));
		map.put("wangzhanleixing",req.getParameter("wangzhanleixing"));
		map.put("guanjianzi",req.getParameter("guanjianzi"));
		List<Map> num = query.getXwhMoney(map, 2, req);
		QueryUtils qu = new QueryUtils();
		String str = qu.getXwhMoney(num);
		String filename = "消委会登记信息涉及金额统计表(" + req.getParameter("begintime") + "至" + req.getParameter("endtime") + ")_"
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
	 * 20xx年 xx月消费者投诉举报办结情况一览表
	 */
	@RequestMapping(value = "/touSuBanJieTj")
	@ResponseBody
	public List<Map> touSuBanJieTj(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String regionCheck = req.getParameter("regioncheck");
		return query.touSuBanJieTj(beginTime, endTime, regionCheck, 1, req);
	}

	/**
	 * 下载 --20xx年 xx月消费者投诉举报办结情况一览表
	 */
	@RequestMapping(value = "/downExcelTouSuBanJie")
	@ResponseBody
	public void downExcelTouSuBanJie(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String regionCheck = req.getParameter("regioncheck");
		List<Map> num = query.touSuBanJieTj(beginTime, endTime, regionCheck, 2,
				req);
		QueryUtils qu = new QueryUtils();
		String str = qu.touSuBanJieTj(num, beginTime, endTime);
		String filename = "消费者投诉举报办结情况一览表" + "(" + beginTime + "至" + endTime
				+ ")_"
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
	 * 各辖区投诉/举报热点排名一览表
	 */
	@RequestMapping(value = "/touSuReDianTj")
	@ResponseBody
	public List<List<Map>> touSuReDianTj(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String regionCheck = req.getParameter("regioncheck");
		return query.touSuReDianTj(beginTime, endTime, regionCheck, 1, req);
	}

	/**
	 * 下载 --各辖区投诉/举报热点排名一览表
	 */
	@RequestMapping(value = "/downExcelTouSuReDian")
	@ResponseBody
	public void downExcelTouSuReDain(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String regionCheck = req.getParameter("regioncheck");
		List<List<Map>> num = query.touSuReDianTj(beginTime, endTime,
				regionCheck, 2, req);
		QueryUtils qu = new QueryUtils();
		String str = qu.touSuReDianTj(num);
		String filename = null;
		if (regionCheck != null && regionCheck.length() != 0
				&& "2".endsWith(regionCheck)) {
			filename = "各辖区投诉举报热点排名一览表（科所）" + "(" + beginTime + "至" + endTime
					+ ")_"
					+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
					+ ".xls";
		} else {
			filename = "各辖区投诉举报热点排名一览表" + "(" + beginTime + "至" + endTime
					+ ")_"
					+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
					+ ".xls";
		}

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
	 * 投诉举报各辖区异动提示表
	 */
	@RequestMapping(value = "/touSuYiDong")
	@ResponseBody
	public List<Map> touSuYiDong(HttpServletRequest req, HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String hBeginTime = req.getParameter("hbegintime");
		String hEndTime = req.getParameter("hendtime");
		String checkType = req.getParameter("checktype");
		String regionCheck = req.getParameter("regioncheck");
		return query.touSuYiDong(beginTime, endTime, hBeginTime, hEndTime,
				checkType, regionCheck, 1, req);
	}

	/**
	 * 下载 --投诉举报各辖区异动提示表
	 */
	@RequestMapping(value = "/downExcelTouSuYiDong")
	@ResponseBody
	public void downExcelTouSuYiDong(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String hBeginTime = req.getParameter("hbegintime");
		String hEndTime = req.getParameter("hendtime");
		String checkType = req.getParameter("checktype");
		String regionCheck = req.getParameter("regioncheck");
		String types = null;
		String regions = null;
		if (checkType != null && checkType.length() != 0
				&& "1".equals(checkType)) {
			types = "投诉";
		} else if (checkType != null && checkType.length() != 0
				&& "2".equals(checkType)) {
			types = "举报";
		} else {
			types = "投诉举报";
		}
		List<Map> num = query.touSuYiDong(beginTime, endTime, hBeginTime,
				hEndTime, checkType, regionCheck, 2, req);
		QueryUtils qu = new QueryUtils();
		String str = null;
		if (regionCheck != null && regionCheck.length() != 0
				&& "2".equals(regionCheck)) {
			str = qu.touSuReYiDongKeSuo(num, types);
			regions = "(科/所)";
		} else {
			str = qu.touSuReYiDong(num, types);
			regions = "(区/局)";
		}

		String filename = types + "各辖区异动提示表" + regions + "(" + beginTime + "至"
				+ endTime + ")_"
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
	 * 投诉举报行业异动提示表
	 */
	@RequestMapping(value = "/touSuHangYe")
	@ResponseBody
	public List<Map> touSuHangYe(HttpServletRequest req, HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String hBeginTime = req.getParameter("hbegintime");
		String hEndTime = req.getParameter("hendtime");
		String checkType = req.getParameter("checktype");
		return query.touSuHangYe(beginTime, endTime, hBeginTime, hEndTime,
				checkType, 1, req);
	}

	/**
	 * 下载 --投诉举报行业异动提示表
	 */
	@RequestMapping(value = "/downExcelTouSuHangYe")
	@ResponseBody
	public void downExcelTouSuHangYe(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String hBeginTime = req.getParameter("hbegintime");
		String hEndTime = req.getParameter("hendtime");
		String checkType = req.getParameter("checktype");
		String types = null;
		if (checkType != null && checkType.length() != 0
				&& "1".equals(checkType)) {
			types = "投诉";
		} else if (checkType != null && checkType.length() != 0
				&& "2".equals(checkType)) {
			types = "举报";
		} else {
			types = "投诉举报";
		}
		List<Map> num = query.touSuHangYe(beginTime, endTime, hBeginTime,
				hEndTime, checkType, 2, req);
		QueryUtils qu = new QueryUtils();
		String str = qu.touSuReHangYe(num, types);
		String filename = types + "行业异动提示表" + "(" + beginTime + "至" + endTime
				+ ")_"
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
	 * 投诉举报问题类别异动提示表
	 */
	@RequestMapping(value = "/touSuLeiBie")
	@ResponseBody
	public List<Map> touSuLeiBie(HttpServletRequest req, HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String hBeginTime = req.getParameter("hbegintime");
		String hEndTime = req.getParameter("hendtime");
		String checkType = req.getParameter("checktype");
		return query.touSuLeiBie(beginTime, endTime, hBeginTime, hEndTime,
				checkType, 1, req);
	}

	/**
	 * 下载 --投诉举报问题类别异动提示表
	 */
	@RequestMapping(value = "/downExcelTouSuLeiBie")
	@ResponseBody
	public void downExcelTouSuLeiBie(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String hBeginTime = req.getParameter("hbegintime");
		String hEndTime = req.getParameter("hendtime");
		String checkType = req.getParameter("checktype");
		String types = null;
		if (checkType != null && checkType.length() != 0
				&& "1".equals(checkType)) {
			types = "投诉";
		} else if (checkType != null && checkType.length() != 0
				&& "2".equals(checkType)) {
			types = "举报";
		} else {
			types = "投诉举报";
		}
		List<Map> num = query.touSuLeiBie(beginTime, endTime, hBeginTime,
				hEndTime, checkType, 2, req);
		QueryUtils qu = new QueryUtils();
		String str = qu.touSuReLieBie(num, types);
		String filename = types + "问题类别异动提示表" + "(" + beginTime + "至" + endTime
				+ ")_"
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
	 * 各领域咨询业务数量统计表
	 */
	@RequestMapping(value = "/lingYuZiXun")
	@ResponseBody
	public List<Map> lingYuZiXun(HttpServletRequest req, HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		return query.lingYuZiXun(beginTime, endTime, 1, req);
	}

	/**
	 * 下载 --各领域咨询业务数量统计表
	 */
	@RequestMapping(value = "/downExcelLingYuZiXun")
	@ResponseBody
	public void downExcelLingYuZiXun(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		List<Map> num = query.lingYuZiXun(beginTime, endTime, 2, req);
		QueryUtils qu = new QueryUtils();
		String str = qu.lingYuZiXun(num);
		String filename = "各领域咨询业务数量统计表" + "(" + beginTime + "至" + endTime
				+ ")_"
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

	// lingYuTouSu
	/**
	 * 各领域投诉业务数量统计表
	 */
	@RequestMapping(value = "/lingYuTouSu")
	@ResponseBody
	public List<Map> lingYuTouSu(HttpServletRequest req, HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		return query.lingYuTouSu(beginTime, endTime, 1, req);
	}

	/**
	 * 下载 --各领域投诉业务数量统计表
	 */
	@RequestMapping(value = "/downExcelLingYuTouSu")
	@ResponseBody
	public void downExcelLingYuTouSu(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		List<Map> num = query.lingYuTouSu(beginTime, endTime, 2, req);
		QueryUtils qu = new QueryUtils();
		String str = qu.lingYuTouSu(num);
		String filename = "各领域投诉业务数量统计表" + "(" + beginTime + "至" + endTime
				+ ")_"
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
	 * 各领域举报业务数量统计表
	 */
	@RequestMapping(value = "/lingYuJuBao")
	@ResponseBody
	public List<Map> lingYuJuBao(HttpServletRequest req, HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		return query.lingYuJuBao(beginTime, endTime, 1, req);
	}

	/**
	 * 下载 --各领域投诉业务数量统计表
	 */
	@RequestMapping(value = "/downExcelLingYuJuBao")
	@ResponseBody
	public void downExcelLingYuJuBao(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		List<Map> num = query.lingYuJuBao(beginTime, endTime, 2, req);
		QueryUtils qu = new QueryUtils();
		String str = qu.lingYuJuBao(num);
		String filename = "各领域举报业务数量统计表" + "(" + beginTime + "至" + endTime
				+ ")_"
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
	 * 举报、投诉热点产品品牌或企业统计表
	 */
	@RequestMapping(value = "/juBaoTouSuPinPai")
	@ResponseBody
	public List<Map> juBaoTouSuPinPai(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		return query.juBaoTouSuPinPai(beginTime, endTime, 1, req);
	}

	/**
	 * 下载 --举报、投诉热点产品品牌或企业统计表
	 */
	@RequestMapping(value = "/downExcelJuBaoTouSuPinPai")
	@ResponseBody
	public void downExcelJuBaoTouSuPinPai(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		List<Map> num = query.juBaoTouSuPinPai(beginTime, endTime, 2, req);
		QueryUtils qu = new QueryUtils();
		String str = qu.juBaoTouSuPinPai(num);
		String filename = "举报、投诉热点产品品牌或企业统计表" + "(" + beginTime + "至" + endTime
				+ ")_"
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
	 * 12365投诉处置工作效率与工作效果分析表
	 */
	@RequestMapping(value = "/BanLiXiaoLv")
	@ResponseBody
	public List<Map> BanLiXiaoLv(HttpServletRequest req, HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		return query.BanLiXiaoLv(beginTime, endTime, 1, req);
	}

	/**
	 * 下载 --12365投诉处置工作效率与工作效果分析表
	 */
	@RequestMapping(value = "/downExcelBanLiXiaoLv")
	@ResponseBody
	public void downExcelBanLiXiaoLv(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		List<Map> num = query.BanLiXiaoLv(beginTime, endTime, 2, req);
		QueryUtils qu = new QueryUtils();
		String str = qu.BanLiXiaoLv(num);
		String filename = "12365投诉处置工作效率与工作效果分析表" + "(" + beginTime + "至"
				+ endTime + ")_"
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
	 * 职业打假人
	 */
	@RequestMapping(value = "/DaJiaRen")
	@ResponseBody
	public List<Map> DaJiaRen(HttpServletRequest req, HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		return query.DaJiaRen(beginTime, endTime, 1, req);
	}

	/**
	 * 下载 --职业打假人
	 */
	@RequestMapping(value = "/downExcelDaJiaRen")
	@ResponseBody
	public void downExcelDaJiaRen(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		List<Map> num = query.DaJiaRen(beginTime, endTime, 2, req);
		QueryUtils qu = new QueryUtils();
		String str = qu.DaJiaRen(num);
		String filename = "职业打假人" + "(" + beginTime + "至" + endTime + ")_"
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
	 * 价格投诉举报信息采集表1
	 */
	@RequestMapping(value = "/jiaGeJuBao")
	@ResponseBody
	public Map<String, Map> jiaGeJuBao(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String tBeginTime = req.getParameter("tbegintime");
		String tEndTime = req.getParameter("tendtime");
		return query.JiaGeJuBao(beginTime, endTime, tBeginTime, tEndTime, 1,
				req);
	}

	/**
	 * 下载 --价格投诉举报信息采集表1
	 */
	@RequestMapping(value = "/downExcelJiaGeJuBao")
	@ResponseBody
	public void downExcelJiaGeJuBao(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		JiaGeJuBaoXinXi jgjbxx = new JiaGeJuBaoXinXi();
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String tBeginTime = req.getParameter("tbegintime");
		String tEndTime = req.getParameter("tendtime");
		Map<String, Map> num = query.JiaGeJuBao(beginTime, endTime, tBeginTime,
				tEndTime, 2, req);
		HSSFWorkbook workBook = jgjbxx.getHSSFWorkbook(req, num);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		String fileName = "价格投诉举报信息采集表1" + "(" + beginTime + "至" + endTime
				+ ")_"
				+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
				+ ".xls";
		try {
			workBook.write(os);
			byte[] bytes = os.toByteArray();
			response.reset();
			response.setContentType("application/force-download");
			response.setHeader("Content-disposition", "attachment;filename= "
					+ URLEncoder.encode(fileName, "UTF-8"));
			response.getOutputStream().write(bytes);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 价格投诉举报信息采集表2
	 */
	@RequestMapping(value = "/jiaGeJuBao2")
	@ResponseBody
	public Map<String, List<Map>> jiaGeJuBao2(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String tEndTime = req.getParameter("tendtime");
		String beginTime = req.getParameter("begintime");
		String tBeginTime = req.getParameter("tbegintime");
		return query.JiaGeJuBao2(beginTime, endTime, tBeginTime, tEndTime, 1,
				req);
	}

	/**
	 * 下载 --价格投诉举报信息采集表2
	 */
	@RequestMapping(value = "/downExceljiaGeJuBao2")
	@ResponseBody
	public void downExceljiaGeJuBao2(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String tEndTime = req.getParameter("tendtime");
		String tBeginTime = req.getParameter("tbegintime");
		String type = req.getParameter("type");
		String name = "";
		if ("2".equals(type)) {
			name = "(根据服务类型)";
		} else if ("3".equals(type)) {
			name = "(根据违法行为种类)";
		} else {
			name = "";
		}
		Map<String, List<Map>> num = query.JiaGeJuBao2(beginTime, endTime,
				tBeginTime, tEndTime, 2, req);
		QueryUtils qu = new QueryUtils();
		String str = qu.downExceljiaGeJuBao2(num, type);
		String filename = "价格投诉举报信息采集表2" + name + "(" + beginTime + "至"
				+ endTime + ")_"
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
	 * 价格投诉举报情况日报
	 */
	@RequestMapping(value = "/jiaGeTouSuRiBao")
	@ResponseBody
	public Map<String, List<Map>> jiaGeTouSuRiBao(HttpServletRequest req,
			HttpServletResponse res) {
		String beginTime = req.getParameter("begintime");
		if (beginTime == null || beginTime.length() == 0) {
			beginTime = sdf.format(new Date()).substring(0, 10);
		}
		return query.jiaGeTouSuRiBao(beginTime, 1, req);
	}

	/**
	 * 下载 --价格投诉举报情况日报
	 */
	@RequestMapping(value = "/downExcelJiaGeRiBao")
	@ResponseBody
	public void downExcelJiaGeRiBao(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String beginTime = req.getParameter("begintime");
		if (beginTime == null || beginTime.length() == 0) {
			beginTime = sdf.format(new Date()).substring(0, 10);
		}
		Map<String, List<Map>> num = query.jiaGeTouSuRiBao(beginTime, 2, req);
		QueryUtils qu = new QueryUtils();
		String str = qu.downExcelJiaGeRiBao(num);
		String filename = "价格投诉举报情况日报" + "(" + beginTime + ")_"
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
	 * 价格投诉举报行业统计表
	 */
	@RequestMapping(value = "/hangYeLeiXingTj")
	@ResponseBody
	public List<Map> hangYeLeiXingTj(HttpServletRequest req,
			HttpServletResponse res) {
		String beginTime = req.getParameter("begintime");
		String endTime = req.getParameter("endtime");
		String tBeginTime = req.getParameter("tbegintime");
		String tEndTime = req.getParameter("tendtime");
		String hBeginTime = req.getParameter("hbegintime");
		String hEndTime = req.getParameter("hendtime");
		return query.hangYeLeiXingTjX(beginTime, endTime, tBeginTime, tEndTime,
				hBeginTime, hEndTime, 1, req);
	}

	/**
	 * 下载 --价格投诉举报行业统计表
	 */
	@RequestMapping(value = "/downExcelHangYeLeiXingTj")
	@ResponseBody
	public void downExcelHangYeLeiXingTj(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String tBeginTime = req.getParameter("tbegintime");
		String tEndTime = req.getParameter("tendtime");
		String hBeginTime = req.getParameter("hbegintime");
		String hEndTime = req.getParameter("hendtime");
		List<Map> num = query.hangYeLeiXingTj(beginTime, endTime, tBeginTime,
				tEndTime, hBeginTime, hEndTime, 2, req);
		QueryUtils qu = new QueryUtils();
		String str = qu.downExcelHangYeLeiXingTj(num);
		String filename = "价格投诉举报行业统计表" + "(" + beginTime + "至" + endTime
				+ ")_"
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
	 * 下载 --价格投诉举报行业统计表详情
	 */
	@RequestMapping(value = "/downExcelHangYeLeiXingTjX")
	@ResponseBody
	public void downExcelHangYeLeiXingTjX(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String tBeginTime = req.getParameter("tbegintime");
		String tEndTime = req.getParameter("tendtime");
		String hBeginTime = req.getParameter("hbegintime");
		String hEndTime = req.getParameter("hendtime");
		List<Map> num = query.hangYeLeiXingTjX(beginTime, endTime, tBeginTime,
				tEndTime, hBeginTime, hEndTime, 2, req);
		QueryUtils qu = new QueryUtils();
		String str = qu.downExcelHangYeLeiXingTjX(num);
		String filename = "价格投诉举报行业统计表详情" + "(" + beginTime + "至" + endTime
				+ ")_"
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
	 * 12358投诉举报统计表
	 */
	@RequestMapping(value = "/shenSuJuBao_12358")
	@ResponseBody
	public List<Map> shenSuJuBao_12358(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		return query.shenSuJuBao_12358(beginTime, endTime, 1, req);
	}

	/**
	 * 下载 --12358投诉举报统计表
	 */
	@RequestMapping(value = "/downExcel12358ShenSu")
	@ResponseBody
	public void downExcel12358ShenSu(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		List<Map> num = query.shenSuJuBao_12358(beginTime, endTime, 2, req);
		QueryUtils qu = new QueryUtils();
		String str = qu.downExcel12358ShenSu(num);
		String filename = "12358投诉举报统计表" + "(" + beginTime + "至" + endTime
				+ ")_"
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
	 * 累计入驻前海商务秘书公司且在营业执照上标注实际经营场所的企业区域分布
	 */
	@RequestMapping(value = "/qianHaiLeiJi")
	@ResponseBody
	public List<Map> qianHaiLeiJi(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		return query.qianHaiLeiJi(endTime, 1, req);
	}

	/**
	 * 下载 --累计入驻前海商务秘书公司且在营业执照上标注实际经营场所的企业区域分布
	 */
	@RequestMapping(value = "/downQianHaiLeiJi")
	@ResponseBody
	public void downQianHaiLeiJi(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		List<Map> num = query.qianHaiLeiJi(endTime, 2, req);
		QueryUtils qu = new QueryUtils();
		String str = qu.qianHaiLeiJi(num);
		String filename = "累计入驻前海商务秘书公司且在营业执照上标注实际经营场所的企业区域分布(截止到" + endTime
				+ ")_"
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
	 * 表2 前海片区港资背景企业行业分布
	 */
	@RequestMapping(value = "/qianHaiGangZi")
	@ResponseBody
	public List<Map> qianHaiGangZi(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		return query.qianHaiGangZi(beginTime, endTime, 1, req);
	}

	/**
	 * 下载 --表2 前海片区港资背景企业行业分布
	 */
	@RequestMapping(value = "/downQianHaiGangZi")
	@ResponseBody
	public void downQianHaiGangZi(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		List<Map> num = query.qianHaiGangZi(beginTime, endTime, 2, req);
		QueryUtils qu = new QueryUtils();
		String str = qu.qianHaiGangZi(num);
		String filename = "前海片区港资背景企业行业分布(" + beginTime + "到" + endTime + ")_"
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
	 * 表3 港资背景企业数量
	 */
	@RequestMapping(value = "/qianHaiGangZiSl")
	@ResponseBody
	public List<Map> qianHaiGangZiSl(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		return query.qianHaiGangZiSl(beginTime, endTime, 1, req);
	}

	/**
	 * 下载 --表3 港资背景企业数量
	 */
	@RequestMapping(value = "/downQianHaiGangZiSl")
	@ResponseBody
	public void downQianHaiGangZiSl(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		List<Map> num = query.qianHaiGangZiSl(beginTime, endTime, 2, req);
		QueryUtils qu = new QueryUtils();
		String str = qu.qianHaiGangZiSl(num);
		String filename = "前海片区港资背景企业数量(" + beginTime + "到" + endTime + ")_"
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
	 * 表4 前海片区总量企业行业分布
	 */
	@RequestMapping(value = "/qianHaiZongLiang")
	@ResponseBody
	public List<Map> qianHaiZongLiang(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		return query.qianHaiZongLiang(beginTime, endTime, 1, req);
	}

	/**
	 * 下载 --表4 前海片区总量企业行业分布
	 */
	@RequestMapping(value = "/downQianHaiZongLiang")
	@ResponseBody
	public void downQianHaiZongLiang(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		List<Map> num = query.qianHaiZongLiang(beginTime, endTime, 2, req);
		QueryUtils qu = new QueryUtils();
		String str = qu.downQianHaiZongLiang(num);
		String filename = "前海片区总量企业行业分布(" + beginTime + "到" + endTime + ")_"
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
	 * 表5 前海蛇口新增数量统计
	 */
	@RequestMapping(value = "/qianHaiXinZeng")
	@ResponseBody
	public List<Map> qianHaiXinZeng(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		return query.qianHaiXinZeng(beginTime, endTime, 1, req);
	}

	/**
	 * 下载 --表5 前海蛇口新增数量统计
	 */
	@RequestMapping(value = "/downQianHaiXinZeng")
	@ResponseBody
	public void downQianHaiXinZeng(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		List<Map> num = query.qianHaiXinZeng(beginTime, endTime, 2, req);
		QueryUtils qu = new QueryUtils();
		String str = qu.downQianHaiXinZeng(num);
		String filename = "前海蛇口新增数量统计(" + beginTime + "到" + endTime + ")_"
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
	 * 稽查1表
	 */
	@RequestMapping(value = "/jiChaBiao01")
	@ResponseBody
	public Map<String, Map<String, Map<String, String>>> jiChaBiao01(
			HttpServletRequest req, HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		return query.jiChaBiao01(beginTime, endTime, 1, req);
	}

	/**
	 * 稽查1表 下载
	 */
	@RequestMapping(value = "/downExcelJiChaBiao01")
	@ResponseBody
	public void downExcelJiChaBiao01(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		JiChaBiao01 jgjbxx = new JiChaBiao01();
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		Map<String, Map<String, Map<String, String>>> num = query.jiChaBiao01(
				beginTime, endTime, 2, req);
		HSSFWorkbook workBook = jgjbxx.getHSSFWorkbook(req, endTime, num);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		String fileName = "稽查1表" + "(" + beginTime + "至" + endTime + ")_"
				+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
				+ ".xls";
		try {
			workBook.write(os);
			byte[] bytes = os.toByteArray();
			response.reset();
			response.setContentType("application/force-download");
			response.setHeader("Content-disposition", "attachment;filename= "
					+ URLEncoder.encode(fileName, "UTF-8"));
			response.getOutputStream().write(bytes);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 表5 前海企业住所情况统计
	 */
	@RequestMapping(value = "/qianHaiRuZhu")
	@ResponseBody
	public List<Map> qianHaiRuZhu(HttpServletRequest req,
			HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		return query.qianHaiRuZhu(beginTime, endTime, 1, req);
	}

	/**
	 * 下载 --表6 前海企业住所情况统计
	 */
	@RequestMapping(value = "/downQianHaiRuZhu")
	@ResponseBody
	public void downQianHaiRuZhu(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		List<Map> num = query.qianHaiRuZhu(beginTime, endTime, 2, req);
		QueryUtils qu = new QueryUtils();
		String str = qu.qianHaiRuZhu(num);
		String filename = "前海企业住所情况统计(" + beginTime + "到" + endTime + ")_"
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
	 * 实时获取所有12315消保信息件登记部门
	 */
	@RequestMapping(value = "/getRegCode")
	@ResponseBody
	public List<Map> getRegCode(HttpServletRequest req, HttpServletResponse res) {
		return query.getRegCode();
	}

	/**
	 * 实时获取所有12315消保信息件处理部门
	 */
	@RequestMapping(value = "/getHandepcode")
	@ResponseBody
	public List<Map> getHandepcode(HttpServletRequest req, HttpServletResponse res) {
		return query.getHandepcode();
	}
	
	/**
	 * 获取人力资源数据
	 */
	@RequestMapping(value = "/getRenLiZiYuan")
	@ResponseBody
	public List<Map> getRenLiZiYuan(HttpServletRequest req, HttpServletResponse res) {
		return query.getRenLiZiYuan();
	}
	
	/**
	 * 获取电子商务监管与服务分析数据
	 */
	@RequestMapping(value = "/getDianZiShangWu")
	@ResponseBody
	public List<Map> getDianZiShangWu(HttpServletRequest req, HttpServletResponse res) {
		return query.getDianZiShangWu();
	}
	/**
	 * 获取跨行业分析数据
	 */
	@RequestMapping(value = "/getKuaHangYe")
	@ResponseBody
	public List<Map> getKuaHangYe(HttpServletRequest req, HttpServletResponse res) {
		return query.getKuaHangYe();
	}
	
	/**
	 * 获取市场规范主题分析数据
	 */
	@RequestMapping(value = "/shiChangGuiFan")
	@ResponseBody
	public List<Map> shiChangGuiFan(HttpServletRequest req, HttpServletResponse res) {
		return query.shiChangGuiFan();
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
