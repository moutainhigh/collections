package com.gwssi.report.fenpai;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import com.gwssi.report.service.QueryFenPaiService;

/**
 * 分派数
 * @author lokn
 * 2018/02/09
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/queryFenPai")
public class FenPaiController {
	
	@Autowired
	private QueryFenPaiService service;
	
	private FenPaiUtil util = new FenPaiUtil();
	private static SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMddHHmmss");
	
	/**
	 * 获取登记部门信息
	 * @param req
	 * @param resp
	 * @return
	 * @throws OptimusException 
	 */
	@RequestMapping("/regCode")
	@ResponseBody
	public List<Map> getRegCode(HttpServletRequest req, HttpServletResponse resp) throws OptimusException {
		List<Map> list = service.fingRegCode();
		return list;
	}
	
	/**
	 * 查询
	 * 	  --登记部门分派数年度月份对比统计报表
	 * @param req
	 * @param resp
	 * @return list
	 * @throws OptimusException 
	 */
	
	@RequestMapping("/registDept")
	@ResponseBody
	public Map<String, List<Map>> getRegistDeptForList(HttpServletRequest req, HttpServletResponse resp) throws OptimusException {
		String beginTime = req.getParameter("begintime");
		String endTime = req.getParameter("endtime");
		String inftype = req.getParameter("inftype");
		if (StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime)) {
			return null;
		}
		
		Map<String, List<Map>> map = service.findRegistDeptForList(beginTime, endTime, inftype, 1, req);
		return map;
	}
	
	/**
	 * 下载
	 * 	  --登记部门分派数年度月份对比统计报表
	 * @param req
	 * @param resp
	 * @throws OptimusException 
	 */
	@RequestMapping("/registDeptDown")
	public void downRegistDept(HttpServletRequest req, HttpServletResponse resp) throws OptimusException {
		String beginTime = req.getParameter("begintime");
		String endTime = req.getParameter("endtime");
		String inftype = req.getParameter("inftype");
		if (StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime)) {
			return;
		}
		Map<String, List<Map>> map = service.findRegistDeptForList(beginTime, endTime, inftype, 2, req);
		if (map == null || map.size() == 0) {
			return;
		}
		String fileName = "登记部门分派数年度月份对比统计报表_" + beginTime + "至" + endTime +"_"
				+ SDF.format(new Date()) + ".xls";
		
		String downStr = util.downReport(map.get("month"), map.get("query"), "登记部门");
		this.dowmFile(fileName, downStr, resp);
	}
	
	/**
	 * 查询
	 * 	  --登记人分派数年度月份对比统计报表
	 * @param req
	 * @param resp
	 * @return
	 * @throws OptimusException 
	 */
	@RequestMapping("/registrant")
	@ResponseBody
	public Map<String, List<Map>> getRegistrant(HttpServletRequest req, HttpServletResponse resp) throws OptimusException {
		String beginTime = req.getParameter("begintime");
		String endTime = req.getParameter("endtime");
		String inftype = req.getParameter("inftype");
		String regCode = req.getParameter("regcode");
		if (StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime)) {
			return null;
		}
		Map<String, List<Map>> map = service.findRegistrantForMap(beginTime, endTime, inftype, regCode, 1, req);
		return map;
	}
	
	/**
	 * 下载
	 * 	  --登记人分派数年度月份对比统计报表
	 * @param req
	 * @param resp
	 * @throws OptimusException 
	 */
	@RequestMapping("/registrantDown")
	public void downRegistrant(HttpServletRequest req, HttpServletResponse resp) throws OptimusException {
		String beginTime = req.getParameter("begintime");
		String endTime = req.getParameter("endtime");
		String inftype = req.getParameter("inftype");
		String regCode = req.getParameter("regcode");
		if (StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime)) {
			return;
		}
		Map<String, List<Map>> map = service.findRegistrantForMap(beginTime, endTime, inftype, regCode, 2, req);
		if (map == null || map.size() == 0) {
			return;
		}
		String downStr = util.downReport(map.get("month"), map.get("query"), "登记人");
		String fileName = "登记人分派数年度月份对比统计报表_" + beginTime + "至" + endTime + "_"
				+ SDF.format(new Date()) + ".xls";
		this.dowmFile(fileName, downStr, resp);
	}
	
	/**
	 * 查询
	 * 	  --承办部门分派数年度月份对比统计报表
	 * @param req
	 * @param resp
	 * @return
	 * @throws OptimusException 
	 */
	@RequestMapping("/handleDept")
	@ResponseBody
	public Map<String, List<Map>> getHandleDept(HttpServletRequest req, HttpServletResponse resp) throws OptimusException {
		String beginTime = req.getParameter("begintime");
		String endTime = req.getParameter("endtime");
		String inftype = req.getParameter("inftype");
		
		if (StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime)) {
			return null;
		}
		Map<String, List<Map>> map = service.findHandleDeptForMap(beginTime, endTime, inftype, 1, req);
		return map;
	}
	
	/**
	 * 下载
	 * 	  --承办部门分派数年度月份对比统计报表
	 * @param req
	 * @param resp
	 * @throws OptimusException 
	 */
	@RequestMapping("/handleDeptDown")
	public void dowHandleDept(HttpServletRequest req, HttpServletResponse resp) throws OptimusException {
		String beginTime = req.getParameter("begintime");
		String endTime = req.getParameter("endtime");
		String inftype = req.getParameter("inftype");
		
		if (StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime)) {
			return;
		}
		
		Map<String, List<Map>> map = service.findHandleDeptForMap(beginTime, endTime, inftype, 2, req);
		if (map == null || map.size() == 0) {
			return;
		}
		String downStr = util.downReport(map.get("month"), map.get("query"), "承办部门");
		String fileName = "承办部门分派数年度月份对比统计报表_" + beginTime + "至" + endTime + "_"
				+ SDF.format(new Date()) + ".xls";
		this.dowmFile(fileName, downStr, resp);
	}
	
	/**
	 * 查询
	 * 	  --承办人分派数年度月份对比统计报表
	 * @param req
	 * @param resp
	 * @return
	 * @throws OptimusException 
	 */
	@RequestMapping("/handler")
	@ResponseBody
	public Map<String, List<Map>> getHandler(HttpServletRequest req, HttpServletResponse resp) throws OptimusException {
		String beginTime = req.getParameter("begintime");
		String endTime = req.getParameter("endtime");
		String inftype = req.getParameter("inftype");
		String regCode = req.getParameter("regcode");
		if (StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime)) {
			return null;
		}
		Map<String, List<Map>> map = service.findHandlerForMap(beginTime, endTime, inftype,regCode, 1, req);
		return map;
	}
	
	/**
	 * 下载
	 * 	  --承办人分派数年度月份对比统计报表
	 * @param req
	 * @param resp
	 * @throws OptimusException 
	 */
	@RequestMapping("/handlerDown")
	public void downHandler(HttpServletRequest req, HttpServletResponse resp) throws OptimusException {
		String beginTime = req.getParameter("begintime");
		String endTime = req.getParameter("endtime");
		String inftype = req.getParameter("inftype");
		String regCode = req.getParameter("regcode");
		if (StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime)) {
			return;
		}
		Map<String, List<Map>> map = service.findHandlerForMap(beginTime, endTime, inftype,regCode, 2, req);
		String downStr = util.downReport(map.get("month"), map.get("query"), "承办人");
		String fileName = "承办人分派数年度月份对比统计报表_" + beginTime + "至" + endTime + "_"
				+ SDF.format(new Date()) + ".xls";
		this.dowmFile(fileName, downStr, resp);
	}
	
	/**
	 *  查询
	 * 	  --来源方式分派数年度月份对比统计报表
	 * @param req
	 * @param resp
	 * @return
	 * @throws OptimusException 
	 */
	@RequestMapping("/sourceWay")
	@ResponseBody
	public Map<String, List<Map>> getSourceWay(HttpServletRequest req, HttpServletResponse resp) throws OptimusException {
		String beginTime = req.getParameter("begintime");
		String endTime = req.getParameter("endtime");
		String inftype = req.getParameter("inftype");
		if (StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime)) {
			return null;
		}
		Map<String, List<Map>> map = service.findSourceWayForMap(beginTime, endTime, inftype, 1, req);
		return map;
	}
	
	/**
	 * 下载
	 * 	  --来源方式分派数年度月份对比统计报表 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/sourceWayDown")
	public void downSourceWay(HttpServletRequest req, HttpServletResponse resp) throws OptimusException {
		String beginTime = req.getParameter("begintime");
		String endTime = req.getParameter("endtime");
		String inftype = req.getParameter("inftype");
		if (StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime)) {
			return;
		}
		Map<String, List<Map>> map = service.findSourceWayForMap(beginTime, endTime, inftype, 2, req);
		String downStr = util.downReport(map.get("month"), map.get("query"), "来源方式");
		String fileName = "来源方式分派数年度月份对比统计报表_" + beginTime + "至" + endTime + "_"
				+ SDF.format(new Date()) + ".xls";
		this.dowmFile(fileName, downStr, resp);
	}
	
	/**
	 * 查询
	 * 	  --接收方式分派数年度月份对比统计报表 
	 * @param req
	 * @param resp
	 * @return
	 * @throws OptimusException 
	 */
	@RequestMapping("/receiveMode")
	@ResponseBody
	public Map<String, List<Map>> getReceviceMode(HttpServletRequest req, HttpServletResponse resp) throws OptimusException {
		String beginTime = req.getParameter("begintime");
		String endTime = req.getParameter("endtime");
		String inftype = req.getParameter("inftype");
		if (StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime)) {
			return null;
		}
		Map<String, List<Map>> map = service.findReceviceModeForMap(beginTime, endTime, inftype, 1, req);
		return map;
	}
	
	/**
	 * 下载
	 * 	  --接收方式分派数年度月份对比统计报表 
	 * @param req
	 * @param resp
	 * @throws OptimusException 
	 */
	@RequestMapping("/receiveModeDown")
	public void downReceviceMode(HttpServletRequest req, HttpServletResponse resp) throws OptimusException {
		String beginTime = req.getParameter("begintime");
		String endTime = req.getParameter("endtime");
		String inftype = req.getParameter("inftype");
		if (StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime)) {
			return;
		}
		Map<String, List<Map>> map = service.findReceviceModeForMap(beginTime, endTime, inftype, 2, req);
		String fileName = "接收方式分派数年度月份对比统计报表_" + beginTime + "至" + endTime + "_"
				+ SDF.format(new Date()) + ".xls";
		String downStr = util.downReport(map.get("month"), map.get("query"), "接受方式");
		this.dowmFile(fileName, downStr, resp);
	}
	
	/**
	 * 查询
	 * 	  --行业类型分派数年度月份对比统计报表 
	 * @param req
	 * @param resp
	 * @return
	 * @throws OptimusException 
	 */
	@RequestMapping("/industryType")
	@ResponseBody
	public Map<String, List<Map>> getIndustryType(HttpServletRequest req, HttpServletResponse resp) throws OptimusException {
		String beginTime = req.getParameter("begintime");
		String endTime = req.getParameter("endtime");
		String inftype = req.getParameter("inftype");
		if (StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime)) {
			return null;
		}
		Map<String, List<Map>> map = service.findIndustryTypeForMap(beginTime, endTime, inftype, 1, req);
		return map;
	}
	
	/**
	 * 下载
	 * 	  --行业类型分派数年度月份对比统计报表 
	 * @param req
	 * @param resp
	 * @throws OptimusException 
	 */
	@RequestMapping("/industryTypeDown")
	public void downIndustryType(HttpServletRequest req, HttpServletResponse resp) throws OptimusException {
		String beginTime = req.getParameter("begintime");
		String endTime = req.getParameter("endtime");
		String inftype = req.getParameter("inftype");
		if (StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime)) {
			return;
		}
		Map<String, List<Map>> map = service.findIndustryTypeForMap(beginTime, endTime, inftype, 2, req);
		String downStr = util.downReport(map.get("month"), map.get("query"), "行业类型");
		String fileName = "行业类型分派数年度月份对比统计报表_" + beginTime + "至" + endTime + "_"
				+ SDF.format(new Date()) + ".xls";
		this.dowmFile(fileName, downStr, resp);
	}
	
	/**
	 * 查询
	 * 	  --问题性质分派数年度月份对比统计报表 
	 * @param req
	 * @param resp
	 * @return
	 * @throws OptimusException 
	 */
	@RequestMapping("/issueNature")
	@ResponseBody
	public Map<String, List<Map>> getIssueNature(HttpServletRequest req, HttpServletResponse resp) throws OptimusException {
		String beginTime = req.getParameter("begintime");
		String endTime = req.getParameter("endtime");
		String inftype = req.getParameter("inftype");
		if (StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime)) {
			return null;
		}
		Map<String, List<Map>> map = service.findIssueNatureForMap(beginTime, endTime, inftype, 1, req);
		return map;
	}
	
	/**
	 * 下载
	 * 	  --问题性质分派数年度月份对比统计报表 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/issueNatureDown")
	public void downIssueNature(HttpServletRequest req, HttpServletResponse resp) throws OptimusException {
		String beginTime = req.getParameter("begintime");
		String endTime = req.getParameter("endtime");
		String inftype = req.getParameter("inftype");
		if (StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime)) {
			return;
		}
		Map<String, List<Map>> map = service.findIssueNatureForMap(beginTime, endTime, inftype, 2, req);
		String downStr = util.downReport(map.get("month"), map.get("query"), "问题性质");
		String fileName = "问题性质分派数年度月份对比统计报表_" + beginTime + "至" + endTime + "_"
				+ SDF.format(new Date()) + ".xls";
		this.dowmFile(fileName, downStr, resp);
	}
	
	/**
	 * 查询
	 * 	  --所属部门分派数年度月份对比统计报表 
	 * @param req
	 * @param resp
	 * @return
	 * @throws OptimusException 
	 */
	@RequestMapping("/suoShuBuMen")
	@ResponseBody
	public Map<String, List<Map>> suoShuBuMen(HttpServletRequest req, HttpServletResponse resp) throws OptimusException {
		String beginTime = req.getParameter("begintime");
		String endTime = req.getParameter("endtime");
		String inftype = req.getParameter("inftype");
		if (StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime)) {
			return null;
		}
		Map<String, List<Map>> map = service.suoShuBuMen(beginTime, endTime, inftype, 1, req);
		return map;
	}
	
	/**
	 * 下载
	 * 	  --所属部门分派数年度月份对比统计报表  
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/downSuoShuBuMen")
	public void downSuoShuBuMen(HttpServletRequest req, HttpServletResponse resp) throws OptimusException {
		String beginTime = req.getParameter("begintime");
		String endTime = req.getParameter("endtime");
		String inftype = req.getParameter("inftype");
		if (StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime)) {
			return;
		}
		Map<String, List<Map>> map = service.suoShuBuMen(beginTime, endTime, inftype, 2, req);
		String downStr = util.getExcelFile(map, "所属部门");
		String fileName = "所属部门分派数年度月份对比统计报表_" + beginTime + "至" + endTime + "_"
				+ SDF.format(new Date()) + ".xls";
		this.dowmFile(fileName, downStr, resp);
	}
	
	
	/**
	 * 查询
	 * 	  --业务范围分派数年度月份对比统计报表 
	 * @param req
	 * @param resp
	 * @return
	 * @throws OptimusException 
	 */
	@RequestMapping("/yeWuFanWei")
	@ResponseBody
	public Map<String, List<Map>> yeWuFanWei(HttpServletRequest req, HttpServletResponse resp) throws OptimusException {
		String beginTime = req.getParameter("begintime");
		String endTime = req.getParameter("endtime");
		String inftype = req.getParameter("inftype");
		if (StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime)) {
			return null;
		}
		Map<String, List<Map>> map = service.yeWuFanWei(beginTime, endTime, inftype, 1, req);
		return map;
	}
	
	
	/**
	 * 下载
	 * 	  --业务范围分派数年度月份对比统计报表  
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/downYeWuFanWei")
	public void downYeWuFanWei(HttpServletRequest req, HttpServletResponse resp) throws OptimusException {
		String beginTime = req.getParameter("begintime");
		String endTime = req.getParameter("endtime");
		String inftype = req.getParameter("inftype");
		if (StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime)) {
			return;
		}
		Map<String, List<Map>> map = service.yeWuFanWei(beginTime, endTime, inftype, 2, req);
		String downStr = util.getExcelFile(map, "业务范围");
		String fileName = "业务范围分派数年度月份对比统计报表_" + beginTime + "至" + endTime + "_"
				+ SDF.format(new Date()) + ".xls";
		this.dowmFile(fileName, downStr, resp);
	}
	
	/**
	 * 查询
	 * 	  --涉及客体分派数年度月份对比统计报表 
	 * @param req
	 * @param resp
	 * @return
	 * @throws OptimusException 
	 */
	@RequestMapping("/sheJiKeTi")
	@ResponseBody
	public Map<String, List<List<Map>>> sheJiKeTi(HttpServletRequest req, HttpServletResponse resp) throws OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		String shejiketi = req.getParameter("shejiketi");
		if (StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime)) {
			return null;
		}
		return service.sheJiKeTi(beginTime, endTime, infotype,shejiketi, 1,req);
	}
	
	/**
	 * 下载
	 * 	  --涉及客体分派数年度月份对比统计报表  
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/downShejiketi")
	public void downShejiketi(HttpServletRequest req, HttpServletResponse resp) throws OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infotype = req.getParameter("infotype");
		String shejiketi = req.getParameter("shejiketi");
		if (StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime)) {
			return;
		}
		Map<String, List<List<Map>>> num = service.sheJiKeTi(beginTime, endTime, infotype,shejiketi, 2,req);
		String str = util.downSheJiKeTi(num);
		String fileName = "涉及客体分派数年度月份对比统计报表_" + beginTime + "至" + endTime + "_"
				+ SDF.format(new Date()) + ".xls";
		this.dowmFile(fileName, str, resp);
	}
	
	/**
	 * 下载
	 * @param fileName 文件名
	 * @param downStr  输出信息
	 * @param req
	 * @param resp
	 */
	public void dowmFile(String fileName, String downStr, HttpServletResponse resp) {
		InputStream input = this.getInputStreamForStr(downStr);
		byte[] b = new byte[102400];
		int len = -1;
		OutputStream output = null;
		try {
			output = resp.getOutputStream();
			resp.setContentType("application/force-download");
			resp.setHeader("Content-Disposition", "attachment; filename="
					+ URLEncoder.encode(fileName, "UTF-8"));
			while((len = input.read(b)) != -1) {
				output.write(b, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
					output = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (input != null) {
				try {
					input.close();
					input = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} 
		
	}
	
	/**
	 * 将字符串转换为输入流
	 * @param inputStreamStr
	 * @return
	 */
	public InputStream getInputStreamForStr(String inputStreamStr) {
		if (StringUtils.isNotEmpty(inputStreamStr)) {
			
			try {
				ByteArrayInputStream inputStream = new ByteArrayInputStream(inputStreamStr.getBytes("UTF-8"));
				return inputStream;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;	
	}
	
}