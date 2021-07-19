package com.gwssi.report.foodstuff;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.gwssi.report.service.QueryFoodStuffServer;

@Controller
@RequestMapping("/queryFoodstuff")
public class QueryFoodstuffController {
	@Autowired
	private QueryFoodStuffServer query;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 各辖区持有效《餐饮服务许可证》、《食品流通许可证》主体分类统计表
	 */
	@RequestMapping(value = "/queryShiPinCanYinXuKe")
	@ResponseBody
	public List<Map> queryShiPinCanYinXuKe(HttpServletRequest req,
			HttpServletResponse res) {
		String beginTime = req.getParameter("begintime");
		return query.queryShiPinCanYinXuKe(beginTime, 1, req);
	}

	/**
	 * 下载各辖区持有效《餐饮服务许可证》、《食品流通许可证》主体分类统计表
	 */
	@RequestMapping(value = "/downExcelShiPinCanYinXuKe")
	@ResponseBody
	public void downExcelShiPinCanYinXuKe(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String beginTime = req.getParameter("begintime");
		List<Map> num = query.queryShiPinCanYinXuKe(beginTime, 2, req);
		FoodstuffUtils qu = new FoodstuffUtils();
		String str = qu.downExcelShiPinCanYinXuKe(num);
		String filename = "各辖区持有效《餐饮服务许可证》、《食品流通许可证》主体分类统计表(" + beginTime
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
	 * 各辖区持有效《食品经营许可证》主体分类统计表
	 */
	@RequestMapping(value = "/queryShiPinXuKe")
	@ResponseBody
	public List<Map> queryShiPinXuKe(HttpServletRequest req,
			HttpServletResponse res) {
		String beginTime = req.getParameter("begintime");
		return query.queryShiPinXuKe(beginTime, 1, req);
	}
	
	/**
	 * 下载各辖区持有效《食品经营许可证》主体分类统计表
	 */
	@RequestMapping(value = "/downExcelShiPinXuKe")
	@ResponseBody
	public void downExcelShiPinXuKe(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String beginTime = req.getParameter("begintime");
		List<Map> num = query.queryShiPinXuKe(beginTime, 2, req);
		FoodstuffUtils qu = new FoodstuffUtils();
		String str = qu.queryShiPinXuKe(num);
		String filename = "各辖区持有效《食品经营许可证》主体分类统计表(" + beginTime
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
	
	//downExcelShiPinZhunRu
	/**
	 * 食药准入××××年××月份业务工作完成情况报表（食品）
	 */
	@RequestMapping(value = "/queryShiPinZhunRu")
	@ResponseBody
	public Map queryShiPinZhunRu(HttpServletRequest req,
			HttpServletResponse res) {
		String beginTime = req.getParameter("begintime");
		String endTime = req.getParameter("endtime");
		return query.queryShiPinZhunRu(beginTime,endTime, 1, req);
	}
	
	/**
	 * 下载食药准入××××年××月份业务工作完成情况报表（食品）
	 */
	@RequestMapping(value = "/downExcelShiPinZhunRu")
	@ResponseBody
	public void downExcelShiPinZhunRu(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String beginTime = req.getParameter("begintime");
		String endTime = req.getParameter("endtime");
		//List<Map> num = query.queryShiPinZhunRu(beginTime,endTime, 2, req);
		
		Map map =  query.queryShiPinZhunRu(beginTime,endTime, 2, req);
		
		
		
	/*	retMap.put("shengChangList", shengChangList);
		retMap.put("jingYingList", jingYingList);*/
		
		FoodstuffUtils qu = new FoodstuffUtils();
		String str = qu.downExcelShiPinZhunRu(map,beginTime);
		String filename = "食药准入"+beginTime.substring(0,4)+"年"+beginTime.substring(5,7)+"月份业务工作完成情况报表（食品）(" + beginTime
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
	 * 食品审查
	 */
	@RequestMapping(value = "/queryShiPinShenCha")
	@ResponseBody
	public List<Map> queryShiPinShenCha(HttpServletRequest req,
			HttpServletResponse res) {
		String beginTime = req.getParameter("begintime");
		String endTime = req.getParameter("endtime");
		return query.queryShiPinShenCha(beginTime, endTime,1, req);
	}
	
	
	/**
	 * 下载食品审查
	 */
	@RequestMapping(value = "/downExcelShiPinShenCha")
	@ResponseBody
	public void downExcelShiPinShenCha(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String beginTime = req.getParameter("begintime");
		String endTime = req.getParameter("endtime");
		List<Map> num = query.queryShiPinShenCha(beginTime,endTime, 2, req);
		FoodstuffUtils qu = new FoodstuffUtils();
		String str = qu.downExcelShiPinShenCha(num,beginTime);
		String filename = "食品审查"+"(" + beginTime+"至"+endTime
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
