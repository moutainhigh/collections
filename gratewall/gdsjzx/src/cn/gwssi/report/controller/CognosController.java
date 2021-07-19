package cn.gwssi.report.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gwssi.quartz.inter.JobServer;
import cn.gwssi.report.model.TCognosReportBO;
import cn.gwssi.report.service.CognosService;
import cn.gwssi.report.service.RunCognos;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.util.BeanUtil;

@Controller
@RequestMapping("/cognosController")
public class CognosController implements JobServer {
	private static Logger log = Logger.getLogger(CognosController.class);

	@Autowired
	private CognosService cognos;

	// 读取cognos图表数据
	@ResponseBody
	@RequestMapping("/readCognoxXML")
	public List readCognoxXML(OptimusRequest req, OptimusResponse resp) throws Exception {

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
		// //ws.sdk_m_visit("业务办理",
		// "content/folder[@name='report']/report[@name='业务分析']", "admin",
		// "123456", "GdSjzxProvider", htParams)
		// System.out.println(reportValue);
		//
		// List xmlList=this.readXML(reportValue);
		// return xmlList;
		// return l;
		return null;
	}

	// 读取cognos图表数据
	@ResponseBody
	@RequestMapping("/readCognoxQY")
	public List readCognox(OptimusRequest req, OptimusResponse resp) throws Exception {

		//
		// String reportPath=req.getHttpRequest().getParameter("reportPath");
		// String reportName=req.getParameter("report");
		//
		// HashMap map=new HashMap();
		// map.put("area", "001");
		// map.put("measure", "001001");
		// map.put("edDt","2015-12-07");
		// map.put("stDt","2015-12-07");
		//// ws.sdk_m_visit("业务办理",
		// "content/folder[@name='report']/report[@name='业务分析']", "admin",
		// "123456", "GdSjzxProvider", htParams)
		//// /content/folder[@name='report']/folder[@name='市场主体']/folder[@name='new']/report[@name='企业设立登记_按产业分析(日)']
		//// String reportValue =cognos.sdk_m_visit(null, reportPath, "admin",
		// "123456", "GdSjzxProvider", map);
		// System.out.println(reportValue);
		//
		//
		// List xmlList=this.readXML(reportValue);
		// return xmlList;
		return null;
	}

	/**
	 * 解析xml
	 * 
	 * @param xml
	 * @return returnlist 0 value 1 x 轴 2维度
	 * @throws DocumentException
	 */
	public List readXML(String xml) throws DocumentException {

		List returnlist = new ArrayList();

		Document document = DocumentHelper.parseText(xml); // 解析xml
		Element rootel = (Element) document.selectSingleNode("/dataset");
		Element crossel = rootel.element("crosstab");
		List<Element> ElList = crossel.elements();

		for (Element el : ElList) {
			String elname = el.getName();
			if ("corner".equals(elname)) {
			}
			// 维度
			if ("columns".equals(elname)) {
				List<Element> list = el.elements("colEdge");
				for (Element e : list) {
					List<Element> elList2 = e.elements("caption");
					int coledge = elList2.size();
					// 初始化数组长度
					String cols[] = new String[coledge];
					for (int i = 0; i < coledge; i++) {
						Element ex = elList2.get(i);
						cols[i] = ex.getText();
					}
					System.out.println(cols.toString());
					returnlist.add(cols);
				}
			}

			// x抽度量及份分类
			if ("values".equals(elname)) {
				// 值
				List<Element> list = el.elements("value");
				int valuesSize = list.size();
				String values[] = new String[valuesSize];// 值
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
				String rowEdge[] = new String[rowsize];// 维度
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

	// @ResponseBody
	// @RequestMapping("/readCognoxIni")
	// public List readCognoxIni(OptimusRequest req, OptimusResponse resp)
	// throws Exception {
	// String types=req.getParameter("types");
	// String[] ts=types.split(",");
	// List<String> userList = Arrays.asList(ts);
	// List list=cognos.iniSearch(userList);
	// return list;
	//
	// }

	public static void main(String[] args) throws Exception {
		// CognosImplDao congnos=new CognosImplDao();
		//
		// HashMap map=new HashMap();
		// map.put("term", "2016-08-03");
		// map.put("unit", "440200");
		// System.out.println(Date.class.newInstance().getMonth());
		//// String reportValue =congnos.sdk_m_visit("内资1表",
		// "/content/folder[@name='report']/folder[@name='工商制式报表']/report[@name='外资1表']",
		// "admin", "123456", "GdSjzxProvider", map);

	}

	/*
	 * 季报、年报、半年报需要修改
	 * 
	 * @see cn.gwssi.quartz.inter.JobServer#job(java.lang.String)
	 */
	@RequestMapping("/runCognos")
	public String job(String paramers) throws Exception {
		// cognos报表参数报表日期，年报：YYYY-MM-01（半年报：YYYY-06-01）;季报：YYYY-MM-02;月报：YYYY-MM-03
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String dataStr = df.format(new Date()).substring(0, 7);
		String year = dataStr.substring(0, 4);
		String mouth = dataStr.substring(5, 7);
		// String mouth="10";

		String reportParamters = "";
		String bqgb = "";
		List<Map> list = new ArrayList<Map>();
		switch (paramers) {
		case "1":
			bqgb = "03";
			dataStr = dataStr + "-" + bqgb;
			reportParamters = mouth + "-" + bqgb;

			break;
		case "2": // 季报
			bqgb = "02";
			reportParamters = mouth + "-" + bqgb;
			break;
		case "3": // 半年报
			bqgb = "06-01";
			reportParamters = year + "-" + bqgb;
			break;
		case "4": // 年报
			bqgb = "01";
			reportParamters = year + "-" + bqgb;
			break;

		default:
			break;
		}
		list = cognos.getAllMouthReports(year, reportParamters);
		this.runCognosReport(list, year, mouth, reportParamters);
		return null;
	}

	/*
	 * paramers= 1月报 =2季报 =3半年报 =4年报 dataStr 2016-08 自动刷新数据库存在的报表
	 * 
	 * @see cn.gwssi.quartz.inter.JobServer#job(java.lang.String)
	 */
	@RequestMapping("/runCognosByDate")
	public String runCognosByDate(String dataStr, String type) throws Exception {
		// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd
		// HH:mm:ss");//设置日期格式
		String year = dataStr.substring(0, 4);
		String mouth = dataStr.substring(5, 7);
		String reportParamters = "";
		String bqgb = "";
		List<Map> list = new ArrayList<Map>();
		switch (type) {
		case "1":
			bqgb = "03";
			dataStr = dataStr + "-" + bqgb;
			reportParamters = mouth + "-" + bqgb;

			break;
		case "2": // 季报
			bqgb = "02";
			reportParamters = mouth + "-" + bqgb;
			break;
		case "3": // 半年报
			bqgb = "06-01";
			reportParamters = year + "-" + bqgb;
			break;
		case "4": // 年报
			bqgb = "01";
			reportParamters = year + "-" + bqgb;
			break;

		default:
			break;
		}
		// cognos.deleteALlByDate(year, reportParamters);
		list = cognos.getAllMouthReports(year, reportParamters);
		this.runCognosReport(list, year, mouth, reportParamters);

		return null;
	}

	public void runCognosReport(List<Map> list, String year, String mouth, String reportParamters) throws Exception {
		ExecutorService ex = Executors.newFixedThreadPool(5);//

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
			System.out.println("共" + list.size() + "张报表需要生成；" + "正在生成第" + i + "张");
			RunCognos cognosx = new RunCognos(bo);
			ex.submit(cognosx);
			// cognosx.call();
		}
	}

	@RequestMapping("/readCognos")
	@ResponseBody
	public String readReport(String id) throws SQLException {
		System.out.println(id);
		Map map = cognos.queryCognosReport(id);
		System.out.println(map);
		String value = (String) map.get("res");
		return value;
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

	/**
	 * 下载文件
	 * 
	 * @param id
	 *            appid
	 * @param response
	 * @throws SQLException
	 */
	@RequestMapping(value = "/downReport")
	public void download(String id, HttpServletResponse response) throws SQLException {

		Map map = cognos.queryCognosReport(id);
		String regcode = (String) map.get("regcode");
		String year = (String) map.get("year");
		String mouth = (String) map.get("mouth");
		String reportname = (String) map.get("reportname");
		String value = (String) map.get("res");
		String filename = regcode + "-" + year + "年" + mouth + "-" + reportname + ".xls";
		InputStream inputStream = getStringStream(value);
		OutputStream outputStream = null;
		byte[] b = new byte[1024];
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

	@RequestMapping(value = "/saveReport")
	@ResponseBody
	public String saveReport(String id, String report) throws SQLException, OptimusException {
		System.out.println("id===" + id + ":report=" + report);
		if (report != null) {
			String hearder = "<html  xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:x=\"urn:schemas-microsoft-com:office:excel\" xmlns=\"http://www.w3.org/TR/REC-html40\"><head>";
			report = report.trim();
			report = hearder + report;
			report = report.replace("contenteditable=\"true\"", " ");
		}
		cognos.updateReport(id, report);

		return "保存成功！";
	}

	@RequestMapping("/queryReport")
	public void queryReport(OptimusRequest req, OptimusResponse res) throws OptimusException {
		Map<String, String> params = req.getForm("formpanel");// 获取参数
		System.out.println("paramss==" + params);
		String reportname = null, regcode = null, year = null, bgq = null;
		if (params != null) {
			reportname = params.get("reportname");
			System.out.println(reportname.length());
			if (reportname.trim() == ""||reportname.length()==0) {
				reportname = "内资1表";
			}
			regcode = params.get("regcode");
			year = params.get("year");
			bgq = params.get("bgq");
		}

		List list = cognos.queryReport(reportname, regcode, year, bgq);
		res.addGrid("reportListGrid", list, null);
	}

	@RequestMapping("/queryWz")
	public void queryWzReport(OptimusRequest req, OptimusResponse res) throws OptimusException {
		Map<String, String> params = req.getForm("formpanel");// 获取参数
		System.out.println("paramss==" + params);
		String reportname = null, regcode = null, year = null, bgq = null;
		if (params != null) {
			reportname = params.get("reportname");
			//start
			if(reportname.trim()==""||reportname.length()==0){
				reportname="外资1表";
		    }
			//end
			regcode = params.get("regcode");
			year = params.get("year");
			bgq = params.get("bgq");
		}

		List list = cognos.queryReport(reportname, regcode, year, bgq);
		res.addGrid("reportListGrid", list, null);
	}

	@RequestMapping("/queryGt")
	public void queryGtReport(OptimusRequest req, OptimusResponse res) throws OptimusException{
		Map<String,String> params = req.getForm("formpanel");//获取参数
		System.out.println("paramss=="+params);
		String reportname = null,regcode = null,year = null,bgq = null;
		if(params!=null){
			reportname=params.get("reportname");
			if(reportname.trim()==""||reportname.length()==0){
					reportname="个体1表";
			}
			regcode=params.get("regcode");
			year=params.get("year");
			bgq=params.get("bgq");
		}
		
		List list=cognos.queryReport(reportname,regcode,year,bgq);
		res.addGrid("reportListGrid", list, null);
	}

	@RequestMapping("/queryZh")
	public void queryZhReport(OptimusRequest req, OptimusResponse res) throws OptimusException {
		Map<String, String> params = req.getForm("formpanel");// 获取参数
		System.out.println("paramss==" + params);
		String reportname = null, regcode = null, year = null, bgq = null;
		if (params != null) {
			reportname = params.get("reportname");
			if (reportname.trim() == ""||reportname.length()==0) {
				reportname = "综合1表";
			}
			regcode = params.get("regcode");
			year = params.get("year");
			bgq = params.get("bgq");
		}

		List list = cognos.queryReport(reportname, regcode, year, bgq);
		res.addGrid("reportListGrid", list, null);
	}

	@RequestMapping("/queryNh")
	public void queryNhReport(OptimusRequest req, OptimusResponse res) throws OptimusException {
		Map<String, String> params = req.getForm("formpanel");// 获取参数
		System.out.println("paramss==" + params);
		String reportname = null, regcode = null, year = null, bgq = null;
		if (params != null) {
			reportname = params.get("reportname");
			if (reportname.trim() == ""||reportname.length()==0) {
					reportname = "农合1表";
				}
			regcode = params.get("regcode");
			year = params.get("year");
			bgq = params.get("bgq");
		}

		List list = cognos.queryReport(reportname, regcode, year, bgq);
		res.addGrid("reportListGrid", list, null);
	}

	// @RequestMapping("/test")
	// public void test(OptimusRequest req, HttpServletResponse response) throws
	// OptimusException, SQLException, IOException, WriteException{
	// Map map=cognos.queryCognosReport1();
	// byte[] b= new byte[1024];
	// int len = 0;
	//
	// String value=(String) map.get("res");
	// InputStream inputStream = getStringStream(value);
	// OutputStream outputStream = response.getOutputStream();
	// response.setContentType("application/msexcel");
	//// response.addHeader("Content-Disposition","attachment; filename=" +
	// URLEncoder.encode("xx.xls", "UTF-8"));
	// // response.setContentLength();
	// while((len = inputStream.read(b)) != -1){
	// outputStream.write(b, 0, len);
	// }
	// //创建工作薄
	// WritableWorkbook workbook = Workbook.createWorkbook(outputStream);
	// //把创建的内容写入到输出流中，并关闭输出流
	// workbook.write();
	// workbook.close();
	// outputStream.close();
	//
	// }
}
