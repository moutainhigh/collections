package com.gwssi.report.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.util.BeanUtil;
import com.gwssi.report.auth.ReportAuthFilter;
import com.gwssi.report.auth.SimpleReportInfo;
import com.gwssi.report.auth.UserDepartmentUtils;
import com.gwssi.report.balance.BalanceInfo;
import com.gwssi.report.balance.api.BalanceClient;
import com.gwssi.report.balance.api.ReportModel;
import com.gwssi.report.model.HomeData;
import com.gwssi.report.model.TCognosReportBO;
import com.gwssi.report.service.CognosService;
import com.gwssi.report.service.RunCognos;
import com.gwssi.report.util.LogOperation;

/*import cn.gwssi.quartz.inter.JobServer;
 import cn.gwssi.report.model.TCognosReportBO;
 import cn.gwssi.report.service.CognosService;
 import cn.gwssi.report.service.RunCognos;

 import cn.gwssi.report.service.CognosService;*/
/**
 * @UPDATE ???????????????????????? USER_REGION_KEY ??? -1 ??????(??????????????????)???
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/cognosController")
public class CognosController {
	private static Logger log = Logger.getLogger(CognosController.class);
	LogOperation logop=new LogOperation();
	@Autowired
	private CognosService cognos;
	
	// ??????cognos????????????
	@ResponseBody
	@RequestMapping("/readCognoxXML")
	public List readCognoxXML(OptimusRequest req, OptimusResponse resp)
			throws Exception {

		// String reportPath=req.getHttpRequest().getParameter("reportPath");
		// String reportName=req.getParameter("report");
		// String industryco=req.getParameter("industry");
		// String measure=req.getParameter("measure");
		// String stDt=req.getParameter("stDt");
		// String edDt=req.getParameter("edDt");
		// String stMon=req.getParameter("stMon");
		// String edMon=req.getParameter("edMon");
		// String area=req.getParameter("area");
		//
		// HashMap map=new HashMap();
		// if(area!=null){
		// map.put("area", area);
		// }
		// if(industryco!=null){
		// map.put("industryco", industryco);
		// }
		// if(measure!=null){
		// map.put("measure", measure);
		// }
		// /*if(stDt!=null || edDt!=null){
		// map.put("edDt",stDt);
		// map.put("stDt",edDt);
		// }else if(stMon!=null||edMon!=null){
		// map.put("stMon",stMon);
		// map.put("edMon",edMon);
		// }*/
		// String reportValue =cognos.sdk_m_visit(null, reportPath, "admin",
		// "123456", "GdSjzxProvider", map);
		// //ws.sdk_m_visit("????????????",
		// "content/folder[@name='report']/report[@name='????????????']", "admin",
		// "123456", "GdSjzxProvider", htParams)
		// System.out.println(reportValue);
		//
		// List xmlList=this.readXML(reportValue);
		// return xmlList;
		// return l;
		return null;
	}

	// ??????cognos????????????
	@ResponseBody
	@RequestMapping("/readCognoxQY")
	public List readCognox(OptimusRequest req, OptimusResponse resp)
			throws Exception {

		//
		// String reportPath=req.getHttpRequest().getParameter("reportPath");
		// String reportName=req.getParameter("report");
		//
		// HashMap map=new HashMap();
		// map.put("area", "001");
		// map.put("measure", "001001");
		// map.put("edDt","2015-12-07");
		// map.put("stDt","2015-12-07");
		// // ws.sdk_m_visit("????????????",
		// "content/folder[@name='report']/report[@name='????????????']", "admin",
		// "123456", "GdSjzxProvider", htParams)
		// //
		// /content/folder[@name='report']/folder[@name='????????????']/folder[@name='new']/report[@name='??????????????????_???????????????(???)']
		// // String reportValue =cognos.sdk_m_visit(null, reportPath, "admin",
		// "123456", "GdSjzxProvider", map);
		// System.out.println(reportValue);
		//
		//
		// List xmlList=this.readXML(reportValue);
		// return xmlList;
		return null;
	}

	/**
	 * ??????xml
	 * 
	 * @param xml
	 * @return returnlist 0 value 1 x ??? 2??????
	 * @throws DocumentException
	 */
	
	public List readXML(String xml) throws DocumentException {

		List returnlist = new ArrayList();

		Document document = DocumentHelper.parseText(xml); // ??????xml
		Element rootel = (Element) document.selectSingleNode("/dataset");
		Element crossel = rootel.element("crosstab");
		List<Element> ElList = crossel.elements();

		for (Element el : ElList) {
			String elname = el.getName();
			if ("corner".equals(elname)) {
			}
			// ??????
			if ("columns".equals(elname)) {
				List<Element> list = el.elements("colEdge");
				for (Element e : list) {
					List<Element> elList2 = e.elements("caption");
					int coledge = elList2.size();
					// ?????????????????????
					String cols[] = new String[coledge];
					for (int i = 0; i < coledge; i++) {
						Element ex = elList2.get(i);
						cols[i] = ex.getText();
					}
					System.out.println(cols.toString());
					returnlist.add(cols);
				}
			}

			// x?????????????????????
			if ("values".equals(elname)) {
				// ???
				List<Element> list = el.elements("value");
				int valuesSize = list.size();
				String values[] = new String[valuesSize];// ???
				for (int i = 0; i < valuesSize; i++) {
					Element e = list.get(i);
					values[i] = e.getText();
				}
				System.out.println(values.toString());
				returnlist.add(values);
			}

			if ("rows".equals(elname)) {
				List<Element> rowlist = el.elements("rowEdge");
				int rowsize = rowlist.size();
				String rowEdge[] = new String[rowsize];// ??????
				for (int i = 0; i < rowsize; i++) {
					Element erow = rowlist.get(i);
					List<Element> elList2 = (List) erow.elements("caption");
					for (Element ecaption : elList2) {
						rowEdge[i] = ecaption.getText();
					}
				}
				returnlist.add(rowEdge);
				System.out.println(rowEdge.toString());
			}
		}
		document.clone();
		System.out.println(returnlist);
		return returnlist;
	}

	/*
	 * ???????????????????????????????????????
	 * 
	 * @see cn.gwssi.quartz.inter.JobServer#job(java.lang.String)
	 */
	@RequestMapping("/runCognos")
	public String job(String paramers) throws Exception {
		// cognos????????????????????????????????????YYYY-MM-01???????????????YYYY-06-01???;?????????YYYY-MM-02;?????????YYYY-MM-03
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ??????????????????
		String dataStr = df.format(new Date()).substring(0, 7);
		String year = dataStr.substring(0, 4);
		String mouth = dataStr.substring(5, 7);
		String reportParamters = "";
		String bqgb = "";
		List<Map> list = new ArrayList<Map>();
		
		/*
		 * switch (paramers) { case "1": bqgb="03"; dataStr=dataStr+"-"+bqgb;
		 * reportParamters=mouth+"-"+bqgb; break; case "2": //?????? bqgb="02";
		 * reportParamters=mouth+"-"+bqgb; break; case "3": //????????? bqgb="06-01";
		 * reportParamters=year+"-"+bqgb; break; case "4": //?????? bqgb="01";
		 * reportParamters=year+"-"+bqgb; break;
		 * 
		 * default: break; }
		 */
		list = cognos.getAllMouthReports(year, reportParamters);
		this.runCognosReport(list, year, mouth, reportParamters);
		return null;
	}

	/*
	 * paramers= 1?????? =2?????? =3????????? =4?????? dataStr 2016-08 ????????????????????????????????????
	 * 
	 * @see cn.gwssi.quartz.inter.JobServer#job(java.lang.String)
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/runCognosByDate")
	public String runCognosByDate(String dataStr, String type) throws Exception {
		// SimpleDateFormat df = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//??????????????????
		String year = dataStr.substring(0, 4);
		String mouth = dataStr.substring(5, 7);
		String reportParamters = "";
		String bqgb = "";
		List<Map> list = new ArrayList<Map>();
		/*
		 * switch (type) { case "1": bqgb="03"; dataStr=dataStr+"-"+bqgb;
		 * reportParamters=mouth+"-"+bqgb;
		 * 
		 * break; case "2": //?????? bqgb="02"; reportParamters=mouth+"-"+bqgb;
		 * break; case "3": //????????? bqgb="06-01"; reportParamters=year+"-"+bqgb;
		 * break; case "4": //?????? bqgb="01"; reportParamters=year+"-"+bqgb;
		 * break;
		 * 
		 * default: break; }
		 */
		// cognos.deleteALlByDate(year, reportParamters);
		list = cognos.getAllMouthReports(year, reportParamters);
		this.runCognosReport(list, year, mouth, reportParamters);

		return null;
	}

	@SuppressWarnings("unchecked")
	public void runCognosReport(List<Map> list, String year, String mouth,
			String reportParamters) throws InstantiationException,
			IllegalAccessException, InterruptedException, ExecutionException,
			OptimusException {
		ExecutorService pool = Executors.newFixedThreadPool(5);
		for (int i = 0; i < list.size(); i++) {
			HashMap mapHelper = new HashMap();
			Map map = list.get(i);
			TCognosReportBO bo = new TCognosReportBO();
			BeanUtil.mapToBean(map, bo);
			bo.setReportparamters(reportParamters);
			bo.setMouth(mouth);
			bo.setYear(year);
			mapHelper.put("term", year + "-" + reportParamters);
			mapHelper.put("unit", bo.getRegcode());
			System.out.println(mapHelper.toString());
			bo.setMapHelper(mapHelper);
			RunCognos cognosx = new RunCognos(bo);
			pool.submit(cognosx);
		}
	}

	// ????????????????????? @UPDATE ????????????
	@RequestMapping("/readCognos")
	@ResponseBody
	public String readReport(HttpServletRequest req, String id)
			throws SQLException, OptimusException {
		String region = (String) req.getSession().getAttribute(
				ReportAuthFilter.USER_REGION_KEY);
		Map map = cognos.queryCognosReport(id, region);
		// System.out.println(map);
		if (map == null)
			return "";
		if (!"001".equals(region)) {
			final String basicRegion = (String) req.getSession().getAttribute(
					ReportAuthFilter.USER_BASIC_REGION_KEY);
			final Set<SimpleReportInfo> sets = UserDepartmentUtils
					.getInstance().getReportNames(basicRegion);
//			if (sets == null
//					|| sets.size() == 0
//					|| !sets.contains(new SimpleReportInfo((String) map
//							.get("reportname"), (String) map.get("reporttype"))))
//				return "????????????";
			if ("1".equals(map.get("isvalid"))) {
				return "???????????????!";
			}
		}
		logop.logInfo(id,"????????????", req);
		String value = (String) map.get("reportcontext");
		return value;
	}

	// ?????????????????????
	@SuppressWarnings("unchecked")
	@RequestMapping("/readAllCognos")
	@ResponseBody
	public void readAllReport(OptimusRequest req, OptimusResponse res,
			String id, String regcode, String reportName, String params,
			String month, String year) throws SQLException, OptimusException {
		List<Map> list = cognos.queryCognosReports(id, regcode, reportName,
				params, month, year);
		res.addGrid("reportListGrid", list, null);
	}

	/**
	 * ????????????????????????????????????
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
	 * ???????????? @UPDATE ????????????
	 * 
	 * @param id
	 *            appid
	 * @param response
	 * @throws SQLException
	 * @throws OptimusException
	 */
	@RequestMapping(value = "/downReport")
	public void download(String id, HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String region = (String) req.getSession().getAttribute(
				ReportAuthFilter.USER_REGION_KEY);//????????????????????????
		Map map = cognos.queryCognosReport(id, region);
		// ????????????
		if (map == null)
			return;
		String reporttype = (String) map.get("reporttype");
		String regcode = (String) map.get("regcode");
		String year = (String) map.get("year");
		String mouth = (String) map.get("mouth");
		String reportname = (String) map.get("reportname");
		String value = (String) map.get("reportcontext");
		String filename = this.getReg(regcode) +"_"+ reportname + "_" +
							year + "???" + mouth + "???"+ ".xls";
		if (!"001".equals(region)) {
			final String basicRegion = (String) req.getSession().getAttribute(
					ReportAuthFilter.USER_BASIC_REGION_KEY);//????????????
			final Set<SimpleReportInfo> sets = UserDepartmentUtils
					.getInstance().getReportNames(basicRegion);
			if (sets == null
					|| sets.size() == 0
					|| !sets.contains(new SimpleReportInfo(reportname,
							reporttype)))
				return;
		}
		InputStream inputStream = getStringStream(value);
		OutputStream outputStream = null;
		byte[] b = new byte[1024];
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
			logop.logInfo(id,"????????????", req);//????????????
		}
	}

	@RequestMapping(value = "/saveReport")
	@ResponseBody
	public String saveReport(String id, String report, HttpServletRequest req)
			throws SQLException, OptimusException {
		String region = (String) req.getSession().getAttribute(
				ReportAuthFilter.USER_REGION_KEY);
		if ("1".equals(region)) {
			return "????????????";
		}
		Map map = cognos.queryCognosReport(id, region);
		if (map == null)
			return "";
		if (!"1".equals(region)) {
			final String basicRegion = (String) req.getSession().getAttribute(
					ReportAuthFilter.USER_BASIC_REGION_KEY);
			final Set<SimpleReportInfo> sets = UserDepartmentUtils
					.getInstance().getReportNames(basicRegion);
			if (sets == null
					|| sets.size() == 0
					|| !sets.contains(new SimpleReportInfo((String) map
							.get("reportname"), (String) map.get("reporttype"))))
				return "????????????";
		}
		System.out.println("id===" + id + ":report=" + report);
		if (report != null) {
			String hearder = "<html  xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:x=\"urn:schemas-microsoft-com:office:excel\" xmlns=\"http://www.w3.org/TR/REC-html40\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">";
			report = report.trim();
			report = hearder + report;
			report = report.replace("contenteditable=\"true\"", " ");
		}

		cognos.updateReport(id, report);
		logop.logInfo(id,"????????????",req);//????????????
		return "???????????????";
	}
	
	@SuppressWarnings("unused")
	@RequestMapping("/queryReport_change")
	public void queryReport_change(OptimusRequest req, OptimusResponse res)
			throws OptimusException{
		//????????????
				String bumen=(String) req.getHttpRequest().getSession().getAttribute("USER_BASIC_REGION_CODE");
				//????????????
				String quyu=(String)req.getHttpRequest().getSession().getAttribute("USER_REGION_CODE");
				//????????????
				String juese=(String)req.getHttpRequest().getSession().getAttribute("USER_DOOR_ROLE_CODE");
				
				Map<String, String> params = req.getForm("formpanel");// ??????????????????
				System.out.println("params=====" + params);
				String  isvalid    = null, //????????????????????????
					    reportname = null, //????????????
						regcode    = null, //??????
						year       = null, //??????
						bgq        = null, //?????????
						reporttype = null; //????????????
				String region = (String) req.getHttpRequest().getSession()
						.getAttribute(ReportAuthFilter.USER_REGION_KEY);//??????????????????
				String pRegCode = params.get("regcode");//??????????????????????????????
				if (region == null)//??????session???????????????????????????
					return;
				
//				if (!"1".equals(region)) { // ????????????&????????????????????????
//					pRegCode = pRegCode == null || "".equals(pRegCode) ? region
//							: pRegCode;
//					if (!region.equals(pRegCode)) {
//						res.addGrid("reportListGrid", null, null);
//						return;
//					}
//				}//??????????????????????????????
				if ("001".equals(quyu)) {
					pRegCode = pRegCode == null || "".equals(pRegCode) ? null
							: pRegCode;
				}else{
					pRegCode=region;
					}
				
				if (params != null) {
					reportname = params.get("reportname");//????????????
					reporttype = params.get("reporttype");
					regcode =pRegCode;
					year = params.get("year");
					bgq = params.get("bgq");
				}
						
				Set<SimpleReportInfo> filters = null;
//				if (!"001".equals(region)) {
//					final String basicRegion = (String) req.getHttpRequest()
//							.getSession()
//							.getAttribute(ReportAuthFilter.USER_BASIC_REGION_KEY);//??????????????????
//					filters = UserDepartmentUtils.getInstance().getReportNames(
//							basicRegion);//???????????????????????????
//						if (filters == null || filters.size() == 0) {
//							// ??????????????????????????????
//							res.addGrid("reportListGrid", null, null);
//							return;
//						}
//					}
				
				List list = cognos.queryReport(reportname, regcode, year, bgq,
						reporttype, filters);
				// ????????????????????????s
				res.addGrid("reportListGrid", list, null);
	}
	// ??????????????????
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping("/queryReport")
	public void queryReport(OptimusRequest req, OptimusResponse res)
			throws OptimusException {
		//????????????
		String bumen=(String) req.getHttpRequest().getSession().getAttribute("USER_BASIC_REGION_CODE");
		//????????????
		String quyu=(String)req.getHttpRequest().getSession().getAttribute("USER_REGION_CODE");
		//????????????
		String juese=(String)req.getHttpRequest().getSession().getAttribute("USER_DOOR_ROLE_CODE");
		//???????????????????????????
//		Set<SimpleReportInfo> reportNames = UserDepartmentUtils.getInstance().getReportNames(bumen==null?"lqbz":bumen);
//		String reportNames1="";
//		if (reportNames==null||reportNames.size()==0) {
//			reportNames1="";
//		}else{
//			for (SimpleReportInfo simpleReportInfo : reportNames) {
//				reportNames1+=simpleReportInfo.reportName;
//			}
//		}
//		req.getHttpRequest().getSession().setAttribute("reportNames",reportNames1);
		
		Map<String, String> params = req.getForm("formpanel");// ??????????????????
		System.out.println("params=====" + params);
		String  isvalid    = null, //????????????????????????
			    reportname = null, //????????????
				regcode    = null, //??????
				year       = null, //??????
				bgq        = null, //?????????
				reporttype = null; //????????????
		String region = (String) req.getHttpRequest().getSession()
				.getAttribute(ReportAuthFilter.USER_REGION_KEY);//??????????????????
		String pRegCode = params.get("regcode");//??????????????????????????????
		if (region == null)//??????session???????????????????????????
			return;
		
//		if (!"1".equals(region)) { // ????????????&????????????????????????
//			pRegCode = pRegCode == null || "".equals(pRegCode) ? region
//					: pRegCode;
//			if (!region.equals(pRegCode)) {
//				res.addGrid("reportListGrid", null, null);
//				return;
//			}
//		}//??????????????????????????????
		if ("001".equals(quyu)||"1".equals(juese)) {
			pRegCode = pRegCode == null || "".equals(pRegCode) ? null
					: pRegCode;
		}else{
			if (pRegCode == null || "".equals(pRegCode)) {
				req.getHttpRequest().getSession().setAttribute("fenjuzonghe","1");
				pRegCode="0";
			}else{
				pRegCode=region;
				}
			}
		
		if ("2".equals(juese)) { //?????????
			if ((params.get("regcode")!=null||!"".equals(params.get("regcode")))&&!"001".equals(region)&&"001".equals(params.get("regcode"))) {
				res.addGrid("reportListGrid", null, null);
				return;
			}
		}
//		if ("1".equals(juese)) { //?????????
//			if ((params.get("regcode")!=null||!"".equals(params.get("regcode")))&&!"001".equals(region)&&"001".equals(params.get("regcode"))) {//??????????????????
//					res.addGrid("reportListGrid", null, null);
//					return;
//			}
//		}
			
		if ("3".equals(juese)) { //?????????
			if ((params.get("regcode")!=null||!"".equals(params.get("regcode")))&&!"001".equals(region)&&"001".equals(params.get("regcode"))) {//??????????????????
					res.addGrid("reportListGrid", null, null);
					return;
					}
				}
		if (params != null) {
			reportname = params.get("reportname");//????????????
			reporttype = params.get("reporttype");
			regcode =pRegCode;
			year = params.get("year");
			bgq = params.get("bgq");
		}
				
		Set<SimpleReportInfo> filters = null;
		if (!"001".equals(region)) {
			if ("1".equals(juese)) {
				
			}else if("2".equals(juese)){
			final String basicRegion = (String) req.getHttpRequest()
					.getSession()
					.getAttribute(ReportAuthFilter.USER_BASIC_REGION_KEY);//??????????????????
			filters = UserDepartmentUtils.getInstance().getReportNames(
					basicRegion);//???????????????????????????
				if (filters == null || filters.size() == 0) {
					// ??????????????????????????????
					res.addGrid("reportListGrid", null, null);
					return;
				}
			}
		}
		List list = cognos.queryReport(reportname, regcode, year, bgq,
				reporttype, filters);
		// ????????????????????????s
		
		res.addGrid("reportListGrid", list, null);
	}

	// ???????????????????????? @TODO
	@SuppressWarnings("rawtypes")
	@RequestMapping("/queryBalanceInfo")
	@ResponseBody
	public String queryBalanceInfo(String reportId) throws OptimusException {
		if (StringUtils.isBlank(reportId))
			return "";
		@SuppressWarnings("rawtypes")
		Map reportInfo = null;
		try {
			reportInfo = cognos.queryReportInfo(reportId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (reportInfo == null || reportInfo.size() == 0)
			return "";
		List<Map> infos = cognos.queryBalanceInfo(
				(String) reportInfo.get("reportname"),
				(String) reportInfo.get("reporttype"));
		return infos == null || infos.size() == 0 ? "-1" : JSON
				.toJSONString(infos.get(0));
	}

	// ??????????????????
	@SuppressWarnings("rawtypes")
	@RequestMapping("/checkoutReport")
	@ResponseBody
	public String checkoutReport(String reportId, HttpServletResponse response)
			throws OptimusException {
		System.out.println(reportId);
		@SuppressWarnings("rawtypes")
		Map reportInfo = null;
		try {
			reportInfo = cognos.queryReportInfo(reportId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (reportInfo == null)
			return "-2";
		final String reportName = (String) reportInfo.get("reportname");
		List<Map> infos = cognos.queryBalanceInfo(reportName,
				(String) reportInfo.get("reporttype"));
		for (Map map : infos) {
			System.out.println("?????????????????????"+map.get("columnbalance"));
			System.out.println("????????????????????????"+map.get("gridbalance"));
			System.out.println("?????????????????????"+map.get("rowbalance"));
			System.out.println("?????????????????????"+map.get("tablebalance"));
		}
		if (infos == null || infos.size() == 0)
			return "-1";
		final BalanceInfo balance = new BalanceInfo();
		balance.setReportId(reportId);
		balance.setRowBalance((String) infos.get(0).get("rowbalance"));
		balance.setColumnBalance((String) infos.get(0).get("columnbalance"));
		balance.setGridBalance((String) infos.get(0).get("gridbalance"));
		balance.setTableBalance((String) infos.get(0).get("tablebalance"));
		final StringBuilder result = new StringBuilder(4096);
		// BalanceUtils.checkBalance(result, reportContext, balance);
		// balance check V2.0 Balan
		final TCognosReportBO queryInfo = new TCognosReportBO();
		queryInfo.setId(reportId);
		final ReportModel reportModel = cognos.getReport(queryInfo);
		final BalanceClient client = new BalanceClient(reportModel, cognos);
		client.rowCheck(balance, result);
		client.columnCheck(balance, result);
		client.gridCheck(balance, result);
		client.tableCheck(balance, result);
		OutputStreamWriter outputStream = null;
		PrintWriter pw = null;
		try {
			outputStream=new OutputStreamWriter(response.getOutputStream(), "utf-8");
			pw = new PrintWriter(outputStream);
			response.setContentType("application/force-download");
			// filename = filename.substring(36, filename.length());
			response.addHeader("Content-Disposition", "attachment; filename="
					+ URLEncoder.encode(reportName + "-??????.txt", "UTF-8"));
			// response.setContentLength();
			pw.write(result.toString().replace("??????", "??????\r\n"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
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
//		return result.toString().replace("??????", "??????\r\n");
		return "";
	}

	@RequestMapping("/changeValid")
	@ResponseBody
	public String changeValid(String id, String isValid, HttpServletRequest req)
			throws OptimusException {
		String region = (String) req.getSession().getAttribute(
				ReportAuthFilter.USER_REGION_KEY);
//		if (!"001".equals(region)) {
//			return "????????????!";
//		}
		if (id == null || isValid == null) {
			return "????????????!";
		}
		isValid = isValid.trim();
		if (!"1".equals(isValid) && !"0".equals(isValid)) {
			return "????????????!";
		}
		int r = cognos.updateValid(id, isValid);
		return r == 0 ? "????????????:?????????????????????!" : "????????????!";
	}

	// @RequestMapping("/testReport")
	// @ResponseBody
	// public String testReport(String reportId) throws OptimusException {
	// System.out.println(reportId);
	// TCognosReportBO bo = new TCognosReportBO();
	// bo.setId(reportId);
	// cognos.getReport(bo);
	// return "";
	// }
	@RequestMapping("/getRequestx")
	@ResponseBody
	public String removeSessionx( HttpServletRequest req)throws OptimusException{
		System.out.println("??????request???="+req.getAttribute("xiugai"));
		return (String)req.getAttribute("xiugai");
	}
	
	@RequestMapping("/removeRequestx")
	@ResponseBody
	public String removeRequestx( HttpServletRequest req)throws OptimusException{
		System.out.println("??????request???");
		req.getSession().removeAttribute("xiugai");
		return (String)req.getSession().getAttribute("xiugai");
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getHomePage")
	@ResponseBody
	public Map<String,List<Map>> getHomePage( HttpServletRequest req)throws OptimusException{
		Map map=new HashMap();
		map.put("mapvalue", HomeData.dataList);
		map.put("nums", HomeData.datalist2);
		 return map;
	}
	
	
	public String getReg(String regcode){
		
		if (regcode.equals("001")) {
			return "?????????";
		}else if (regcode.equals("440303")) {
			return "??????????????????";
		}else if (regcode.equals("440304")) {
			return "??????????????????";
		}else if(regcode.equals("440305")) {
			return "??????????????????";
		}else if(regcode.equals("440306")) {
			return "??????????????????";
		}else if(regcode.equals("440307")) {
			return "??????????????????";
		}else if(regcode.equals("440308")) {
			return "??????????????????";
		}else if(regcode.equals("440309")) {
			return "?????????????????????";
		}else if(regcode.equals("440310")) {
			return "?????????????????????";
		}else if(regcode.equals("440342")) {
			return "?????????????????????";
		}else if(regcode.equals("440343")) {
			return "?????????????????????";
		}else {
			return "?????????????????????";
		}
	}
	
	
	public String getIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("x-forwarded-for");  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}  
	public static void main(String[] args) {
		// List<BalanceInfo> list = new ArrayList<BalanceInfo>();
		// BalanceInfo info = new BalanceInfo();
		// info.setColumnBalance("1>=2;4>=5+6+8+9");
		// info.setRowBalance("1==3");
		// list.add(info);
		// System.out.println(JSONArray.toJSONString(list));
		
	}

}
