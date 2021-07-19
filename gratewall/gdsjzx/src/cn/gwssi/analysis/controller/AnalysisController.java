package cn.gwssi.analysis.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gwssi.analysis.service.AnalysisService;
import cn.gwssi.resource.DateUtil;
import cn.gwssi.resource.FreemarkerUtil;
import cn.gwssi.resource.SortUtil;

import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

@Controller
@RequestMapping("/analysis")
public class AnalysisController {

	public static Logger log = Logger.getLogger(AnalysisController.class);

	@Autowired
	private AnalysisService analysisService;

	@RequestMapping("/entestbreg")
	@ResponseBody
	public List findByEntEstbReg(OptimusRequest req, OptimusResponse res)
			throws OptimusException {
		// String dlm = req.getParameter("dlm").trim();
		// String mm = req.getParameter("mm").trim();
		Map rootNode = new HashMap();
		List funcList = analysisService.findByEntEstbReg(rootNode);
		// Map rootNode = new HashMap();
		return funcList;// funcList;
	}

	@RequestMapping("/entestbregDrillDwon")
	@ResponseBody
	public List drillDwonFindByEntEstbReg(OptimusRequest req,
			OptimusResponse res) throws OptimusException {
		// String dlm = req.getParameter("dlm").trim();
		// String mm = req.getParameter("mm").trim();
		Map rootNode = new HashMap();
		List funcList = analysisService.findByEntEstbReg(rootNode);
		// Map rootNode = new HashMap();
		return funcList;// funcList;
	}

	// 初始化业务类型---受理类型
	@RequestMapping("/iniBusinessAnalystsType")
	@ResponseBody
	public List iniBusinessAnalystsType(OptimusRequest req, OptimusResponse res)
			throws OptimusException {
		String drillType = req.getParameter("drillType");
		Map map = new HashMap();
		if (drillType != null) {
			map.put("drillType", drillType);
		}
		List funcList = analysisService.iniBusinessAnalystsType(map);
		return funcList;// funcList;
	}

	// 初始化业务类型---区域类型
	@RequestMapping("/iniBusinessAnalystsArea")
	@ResponseBody
	public List iniBusinessAnalystsArea(OptimusRequest req, OptimusResponse res)
			throws OptimusException {

		String drillType = req.getParameter("drillType");

		Map map = new HashMap();
		if (drillType != null) {
			map.put("drillType", drillType);
		}

		List funcList = analysisService.iniBusinessAnalystsArea(map);
		return funcList;// funcList;
	}

	// 初始化业务类型---业务类型
	@RequestMapping("/iniBusinessAnalystsBusiness")
	@ResponseBody
	public List iniBusinessAnalystsBusiness(OptimusRequest req,
			OptimusResponse res) throws OptimusException {
		String fxType = req.getParameter("fxType");
		String drillType = req.getParameter("drillType");
		if (fxType != null) {
			if ("qsgsfx".equals(fxType)) {
				List list = analysisService.iniBusinessAnalystsQsgsfx();
				return list;
			}
		}
		if (drillType != null) {
			List funcList = analysisService
					.iniBusinessAnalystsBusiness(drillType);
			return funcList;
		}
		List funcList = analysisService.iniBusinessAnalystsBusiness(null);
		return funcList;// funcList;
	}

	/*
	 * // 初始化市场主体变更---组织形式和产业分析
	 * 
	 * @RequestMapping("/iniZzxsIndustrycoBusiness")
	 * 
	 * @ResponseBody public List iniZzxsIndustrycoBusiness(OptimusRequest req,
	 * OptimusResponse res) throws OptimusException {
	 * 
	 * String fxType = req.getParameter("fxType");
	 * 
	 * if (fxType != null) { if ("qsgsfx".equals(fxType)) { List list =
	 * analysisService.iniBusinessAnalystsQsgsfx(); return list; } if
	 * ("zzhcy".equals(fxType)) { List list =
	 * analysisService.iniZzxsCyAnalystsfx(); return list; }
	 * 
	 * } return funcList;// funcList; }
	 */

	// 业务分析——按业务量业务类型
	@RequestMapping("/businessAnalystsType")
	@ResponseBody
	public List businessAnalystsType(OptimusRequest req, OptimusResponse res)
			throws OptimusException {
		// selType regType startdt enddt accepttype businessCount
		String selType = req.getParameter("selType");
		String selArea = req.getParameter("selArea");
		String regType = req.getParameter("regType");
		String startdt = req.getParameter("startdt");
		// String enddt = req.getParameter("enddt");
		String accepttype = req.getParameter("accepttype");
		String businessCount = req.getParameter("businessCount");
		String level = req.getParameter("level");
		Map params = new HashMap();
		if (regType != null) {
			params.put("regType", regType);
		}

		if (selType != null) {
			params.put("selType", selType);
		}

		if (startdt != null) {
			params.put("startdt", startdt);
		}

		if (businessCount != null) {
			params.put("businessCount", businessCount);
		}
		if (selArea != null) {
			params.put("selArea", selArea);
		}
		if (accepttype != null) {
			params.put("accepttype", accepttype);
		}
		if (level != null) {
			params.put("level", level);
		}
		List funcList = analysisService.businessAnalystsType(params);
		return funcList;
	}

	// 业务分析--按各级地市-or-按区域分析
	@RequestMapping("/businessAnalystsSum")
	@ResponseBody
	public List businessAnalystsSum(OptimusRequest req, OptimusResponse res)
			throws OptimusException {
		// selType startdt enddt regorg businessCount
		String selType = req.getParameter("selType");
		String startdt = req.getParameter("startdt");
		String enddt = req.getParameter("enddt");
		String businessCount = req.getParameter("businessCount");
		String drillType = req.getParameter("drillType");
		Map params = new HashMap();

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
		// 初始化和传参
		if (startdt != null) {
			params.put("startdt", startdt);
		} else {
			params.put("startdt", dateString);
		}
		if (enddt != null) {
			params.put("enddt", enddt);
		} else {
			params.put("enddt", dateString);
		}
		if (selType != null) {
			params.put("selType", selType);
		}
		if (businessCount != null) {
			params.put("businessCount", businessCount);
		}
		if (drillType != null) {
			params.put("drillType", drillType);
		}
		List funcList = analysisService.businessAnalystsSum(params);
		return funcList;
	}

	// 下钻--业务分析--按业务量业务类型 --按区域分析
	@RequestMapping("/drillDownBusinessAnalystsSum")
	@ResponseBody
	public List drillDownBusinessAnalystsSum(OptimusRequest req,
			OptimusResponse res) throws OptimusException, ParseException {
		String startdt = req.getParameter("showstarttime");
		String enddt = req.getParameter("showendtime");
		String selType = req.getParameter("selType");
		String businessCount = req.getParameter("businessCount");
		// 区域类型
		String drillType = req.getParameter("drillType");
		Map params = new HashMap();
		// 初始化和传参
		if (startdt != null) {
			params.put("startdt", getSpecifiedDayBefore(startdt));
		}
		if (enddt != null) {
			params.put("enddt", getSpecifiedDayAfter(enddt));
		}
		if (drillType != null) {
			params.put("regorgcode", drillType);
		}
		if (businessCount != null) {
			params.put("businessCount", businessCount);
		}
		if (selType != null) {
			params.put("selType", selType);
		}

		List funcList = analysisService.drillDownBusinessAnalystsSum(params);
		return funcList;
	}

	// 下钻--业务分析--按业务量业务类型 --按区域分析
	@RequestMapping("/drillDownBusinessAnalystsType")
	@ResponseBody
	public List drillDownBusinessAnalystsType(OptimusRequest req,
			OptimusResponse res) throws OptimusException, ParseException {
		// a.transdt and a.transdt and b.parentcode and a.MEASURE selType
		// regType
		String startdt = req.getParameter("showstarttime");
		String enddt = req.getParameter("showendtime");
		String selType = req.getParameter("selType");
		String businessCount = req.getParameter("businessCount");
		// 区域类型
		String bussisType = req.getParameter("bussisType");
		Map params = new HashMap();
		// 初始化和传参
		if (startdt != null) {
			params.put("startdt", getSpecifiedDayBefore(startdt));
		}
		if (enddt != null) {
			params.put("enddt", getSpecifiedDayAfter(enddt));
		}
		if (bussisType != null) {
			params.put("parentcode", bussisType);
		}
		if (businessCount != null) {
			params.put("businessCount", businessCount);
		}
		if (selType != null) {
			params.put("selType", selType);
		}

		List funcList = analysisService.drillDownBusinessAnalystsType(params);
		return funcList;
	}

	// 初始化业务类型---区域类型
	@RequestMapping("/iniZzxsAnalystsArea")
	@ResponseBody
	public List iniZzxsAnalystsArea(OptimusRequest req, OptimusResponse res)
			throws OptimusException {
		String organizationmode = req.getParameter("organizationmode");
		List funcList;
		if (organizationmode != null) {
			funcList = analysisService.iniZzxsAnalystsType(organizationmode);
		} else {
			funcList = analysisService.iniZzxsAnalystsType(null);
		}
		return funcList;// funcList;
	}

	// 初始化业务类型---产业和组织形式类型
	@RequestMapping("/iniZzxsAndIndustrAnalysts")
	@ResponseBody
	public List iniZzxsAndIndustrAnalysts(OptimusRequest req,
			OptimusResponse res) throws OptimusException {
		String drillType = req.getParameter("drillType");
		List funcList;
		if (drillType != null) {
			funcList = analysisService.iniZzxsAndIndustrAnalystsType(drillType);
		} else {
			funcList = analysisService.iniZzxsAndIndustrAnalystsType(null);
		}
		return funcList;// funcList;
	}

	// 业务分析--按各级地市-or-按区域分析
	@RequestMapping("/zzxsAnalystsSum")
	@ResponseBody
	public List zzxsAnalystsSum(OptimusRequest req, OptimusResponse res)
			throws OptimusException {
		// selType --- 月份日期 startdt---开始时间 enddt --结束时间 ORGANIZATIONMODE
		// --一级组织形式
		// businessCount --期末 t_reg_dic_measure --- measure
		String selType = req.getParameter("selType");
		String startdt = req.getParameter("startdt");
		String enddt = req.getParameter("enddt");
		String organizationmode = req.getParameter("organizationmode");
		String businessCount = req.getParameter("businessCount");
		String level = req.getParameter("level");
		Map params = new HashMap();

		// 初始化和传参
		if (startdt != null) {
			params.put("startdt", startdt);
		}
		if (enddt != null) {
			params.put("enddt", enddt);
		}
		if (selType != null) {
			params.put("selType", selType);
		}
		if (organizationmode != null) {
			params.put("organizationmode", organizationmode);
		}
		if (businessCount != null) {
			params.put("businessCount", businessCount);
		}
		List funcList = null;
		if ("1".equals(level)) {
			funcList = analysisService.zzxsAnalystsSum(params);
		} else {
			funcList = analysisService.drillDownzzxsAnalystsSum(params);
		}

		return funcList;
	}

	// 下钻--业务分析--按业务量业务类型 --按区域分析
	@RequestMapping("/drillDownzzxsAnalystsSum")
	@ResponseBody
	public List drillDownzzxsAnalystsSum(OptimusRequest req, OptimusResponse res)
			throws OptimusException, ParseException {
		String selType = req.getParameter("selType");
		String startdt = req.getParameter("startdt");
		String enddt = req.getParameter("enddt");
		String organizationmode = req.getParameter("organizationmode");
		String businessCount = req.getParameter("businessCount");
		// 区域类型
		String drillType = req.getParameter("drillType");
		Map params = new HashMap();
		// 初始化和传参
		if (startdt != null) {
			params.put("startdt", getSpecifiedDayBefore(startdt));
		}
		if (enddt != null) {
			params.put("enddt", getSpecifiedDayAfter(enddt));
		}
		if (drillType != null) {
			params.put("regorgcode", drillType);
		}
		if (businessCount != null) {
			params.put("businessCount", businessCount);
		}
		if (selType != null) {
			params.put("selType", selType);
		}

		List funcList = analysisService.drillDownzzxsAnalystsSum(params);
		return funcList;
	}

	// 设立登记——按全省各市和组织形式分析
	@RequestMapping("/businessCityAnalystsType")
	@ResponseBody
	public List businessCityAnalystsType(OptimusRequest req, OptimusResponse res)
			throws OptimusException {
		// selArea,selType,startdt,enddt,organizationType
		String selType = req.getParameter("selType");
		String startdt = req.getParameter("startdt");
		String enddt = req.getParameter("enddt");
		String selArea = req.getParameter("selArea");
		String businessCount = req.getParameter("businessCount");
		String organizationmode = req.getParameter("organizationmode");

		Map params = new HashMap();

		if (selType != null) {
			params.put("selType", selType);
		}

		if (startdt != null) {
			params.put("startdt", startdt);
		}

		if (enddt != null) {
			params.put("enddt", enddt);
		}

		if (selArea != null) {
			params.put("selArea", selArea);
		}

		if (businessCount != null) {
			params.put("businessCount", businessCount);
		}

		if (organizationmode != null) {
			params.put("organizationmode", organizationmode);
		}
		List funcList = analysisService.businessAnalystsCityType(params);
		return funcList;
	}

	// 企业设立登记--按各级地市and组织形式
	@RequestMapping("/qysldjZzxsAnalystsSum")
	@ResponseBody
	public List qysldjZzxsAnalystsSum(OptimusRequest req, OptimusResponse res)
			throws OptimusException {
		// selType --- 月份日期 startdt---开始时间 enddt --结束时间 ORGANIZATIONMODE
		// --一级组织形式
		// businessCount --期末 t_reg_dic_measure --- measure
		String selType = req.getParameter("selType");
		String startdt = req.getParameter("startdt");
		// String enddt = req.getParameter("enddt");
		String organizationmode = req.getParameter("organizationmode");
		String businessCount = req.getParameter("businessCount");
		Map params = new HashMap();

		// 初始化和传参
		if (startdt != null) {
			params.put("startdt", startdt);
		}
		if (organizationmode != null) {
			params.put("organizationmode", organizationmode);
		}
		if (selType != null) {
			params.put("selType", selType);
		}
		if (businessCount != null) {
			params.put("businessCount", businessCount);
		}
		List funcList = null;
		funcList = analysisService.drillqyslZzxsAnalystsSum(params);
		return funcList;
	}

	// 设立登记——按组织形式和产业类型分析
	@RequestMapping("/zzxsAndIndustrycoAnalystsType")
	@ResponseBody
	public List zzxsAndIndustrycoAnalystsType(OptimusRequest req,
			OptimusResponse res) throws OptimusException {
		// selArea,selType,startdt,enddt,organizationType
		String selType = req.getParameter("selType");
		String startdt = req.getParameter("startdt");
		String enddt = req.getParameter("enddt");
		String businessCount = req.getParameter("businessCount");

		Map params = new HashMap();

		if (selType != null) {
			params.put("selType", selType);
		}

		if (startdt != null) {
			params.put("startdt", startdt);
		}

		if (enddt != null) {
			params.put("enddt", enddt);
		}

		if (businessCount != null) {
			params.put("businessCount", businessCount);
		}

		List funcList = analysisService.zzxsAndIndustrycoAnalystsType(params);
		return funcList;
	}

	// 企业设立登记--按组织形式and二级产业类型
	@RequestMapping("/zzxsAndIndustrycoSum")
	@ResponseBody
	public List zzxsAndIndustrycoSum(OptimusRequest req, OptimusResponse res)
			throws OptimusException {
		String selType = req.getParameter("selType");
		String startdt = req.getParameter("startdt");
		String enddt = req.getParameter("enddt");
		String industrType = req.getParameter("industrType");
		String businessCount = req.getParameter("businessCount");
		Map params = new HashMap();

		// 初始化和传参
		if (startdt != null) {
			params.put("startdt", startdt);
		}
		if (enddt != null) {
			params.put("enddt", enddt);
		}
		if (industrType != null) {
			params.put("industrType", industrType);
		}
		if (selType != null) {
			params.put("selType", selType);
		}
		if (businessCount != null) {
			params.put("businessCount", businessCount);
		}
		List funcList = null;
		funcList = analysisService.drillZzxsAndIndeustrAnalystsSum(params);
		return funcList;
	}

	/**
	 * 获得指定日期的前一天
	 * 
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static String getSpecifiedDayBefore(String specifiedDay) {// 可以用new
																		// Date().toLocalString()传递参数
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
				.getTime());
		return dayBefore;
	}

	/**
	 * 获得指定日期的后一天
	 * 
	 * @param specifiedDay
	 * @return
	 */
	public static String getSpecifiedDayAfter(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd")
				.format(c.getTime());
		return dayAfter;
	}
	
	@RequestMapping("/findByPripid")
	@ResponseBody
	public String findByPripid1(OptimusRequest req, OptimusResponse res)throws OptimusException {
		String priPid = req.getParameter("pripid");// 获取主体身份代码
		String sourceflag = req.getParameter("sourceflag");
		String entname = req.getParameter("entname");
		//查高管人
		List<Map> gg = analysisService.findTest(priPid,sourceflag,"0");
		StringBuffer sbf = new StringBuffer("{\"nodes\":");
		sbf.append("[");
		for(int i=0;i<gg.size();i++){
			Map m = gg.get(i);
			List<String> str = constr(analysisService.findTest((String)m.get("id"),"1"),(String)m.get("id"),1);
			sbf.append("{\"pripid\":\"").append(m.get("id")).append("\",\"name\":\"").append(m.get("name")).append("\",");
			if(str!=null&& str.size()>0){
				sbf.append("\"group\":\"").append(m.get("type")).append("\"},");
				
				sbf.append("{\"pripid\":\"").append("a5468299-0151-1000-e000-70910a0a0115").append("\",\"name\":\"").append("恒汇控股集团").append("\",");
				sbf.append("\"group\":\"").append("4").append("\"},");
				//--
				sbf.append("{\"pripid\":\"").append("1").append("\",\"name\":\"").append("恒汇控股集团股份有限公司").append("\",");
				sbf.append("\"group\":\"").append("2").append("\"},");
				sbf.append("{\"pripid\":\"").append("2").append("\",\"name\":\"").append("河源开关厂有限公司").append("\",");
				sbf.append("\"group\":\"").append("2").append("\"},");
				sbf.append("{\"pripid\":\"").append("3").append("\",\"name\":\"").append("广东恒创汇金资产管理有限公司").append("\",");
				sbf.append("\"group\":\"").append("2").append("\"},");
				
				sbf.append("{\"pripid\":\"").append("4").append("\",\"name\":\"").append("深圳恒汇全球基金管理有限公司").append("\",");
				sbf.append("\"group\":\"").append("2").append("\"},");
				sbf.append("{\"pripid\":\"").append("5").append("\",\"name\":\"").append("广东恒汇互联网金融服务有限公司").append("\",");
				sbf.append("\"group\":\"").append("2").append("\"},");
				

				//--
				sbf.append(str.get(0));
				sbf.append("],\"links\":[");
				
				sbf.append("{\"source\":\"").append(m.get("id")).append("\",");
				sbf.append("\"target\":\"").append("a5468299-0151-1000-e000-70910a0a0115").append("\"},");
				
				
				sbf.append("{\"source\":\"").append("1").append("\",");
				sbf.append("\"target\":\"").append("a5468299-0151-1000-e000-70910a0a0115").append("\"},");
				sbf.append("{\"source\":\"").append("2").append("\",");
				sbf.append("\"target\":\"").append("a5468299-0151-1000-e000-70910a0a0115").append("\"},");
				sbf.append("{\"source\":\"").append("3").append("\",");
				sbf.append("\"target\":\"").append("a5468299-0151-1000-e000-70910a0a0115").append("\"},");
				sbf.append("{\"source\":\"").append("4").append("\",");
				sbf.append("\"target\":\"").append("a5468299-0151-1000-e000-70910a0a0115").append("\"},");
				sbf.append("{\"source\":\"").append("5").append("\",");
				sbf.append("\"target\":\"").append("a5468299-0151-1000-e000-70910a0a0115").append("\"},");
				
				sbf.append(str.get(1));
			}else{
				sbf.append("\"group\":\"").append(m.get("type")).append("\"}");
			}
		}
		
		sbf.append("]");
		sbf.append("}");
		System.out.println(sbf.toString());
		return sbf.toString();
	}
	
	private int randomInt(int min,int max){
		/*int max=20;int min=10;*/
        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        return s;
	}

	private List<String> constr(List<Map> findTest,String id,int j) throws OptimusException {
		List<String> str = new ArrayList<String>();
		StringBuffer sbf1  = new StringBuffer();
		StringBuffer sbf2  = new StringBuffer();
		//int j = 1;
		j++;//"2"
		if(findTest!=null && findTest.size()>0){
			for(int i=0;i<findTest.size();i++){
				Map m = findTest.get(i);
				List<String> strs = constr(analysisService.findTest((String)m.get("id"),j+""),(String)m.get("id"),j);
				if(strs!=null && strs.size()>0){
					sbf1.append(strs.get(0)).append(",");
					sbf2.append(strs.get(1)).append(",");
				}
				if(i==findTest.size()-1){
					sbf1.append("{\"pripid\":\"").append(m.get("id")).append("\",");
					sbf1.append("\"name\":\"").append(m.get("name")).append("\",");
					sbf1.append("\"group\":\"").append(m.get("type")).append("\"}");
					
					sbf2.append("{\"source\":\"").append(m.get("id")).append("\",");
					sbf2.append("\"target\":\"").append(id).append("\"}");
				}else{
					sbf1.append("{\"pripid\":\"").append(m.get("id")).append("\",");
					sbf1.append("\"name\":\"").append(m.get("name")).append("\",");
					sbf1.append("\"group\":\"").append(m.get("type")).append("\"},");
					
					sbf2.append("{\"source\":\"").append(m.get("id")).append("\",");
					sbf2.append("\"target\":\"").append(id).append("\"},");
				}
			}
			str.add(sbf1.toString());
			str.add(sbf2.toString());
		}
		return str;
	}

	@RequestMapping("/findByPripid1")
	@ResponseBody
	public String findByPripid(OptimusRequest req, OptimusResponse res)throws OptimusException {
		/*String dd = "{ \"nodes\": "
				+ "[ {\"pripid\":\"440121121022016080300162\","
				+ "	 \"name\":\"广州花样餐饮有限公司\"," + "	 \"group\":\"2\"}, "
				+ "  {\"pripid\":\"500224198901280027\","
				+ "   \"name\":\"卢溪\"," + "   \"group\":\"3\"}, "
				+ "  {\"pripid\":\"50022419900722798X\","
				+ "   \"name\":\"易欢\"," + "   \"group\":\"3\"} " + "], "
				+ "\"links\": " + "[ {\"source\":\"50022419900722798X\","
				+ "   \"target\":\"440121121022016080300162\"}, "
				+ "  {\"source\":\"500224198901280027\","
				+ "   \"target\":\"440121121022016080300162\"} " + " ]" + "}";*/
		/*{ "nodes": 
			[ {"pripid":"440121121022016080300162",
				 "name":"广州花样餐饮有限公司",
				 "group":"2"}, 
			  {"pripid":"500224198901280027",
			   "name":"卢溪",
			   "group":"3"}, 
			  {"pripid":"50022419900722798X",
			   "name":"易欢",
			   "group":"3"} 
			], 451477f9-010d-1000-e000-03c00a010119 440000
		  "links": 
		  [ {"source":"50022419900722798X",
		     "target":"440121121022016080300162"}, 
		    {"source":"500224198901280027",
		     "target":"440121121022016080300162"} 
		  ]
		} */
		StringBuffer sbf = new StringBuffer("{\"nodes\":");
		String priPid = req.getParameter("pripid");// 获取主体身份代码
		String sourceflag = req.getParameter("sourceflag");
		String entname = req.getParameter("entname");
		//查高管人
		List<Map> gg = analysisService.findGG(priPid,sourceflag,"");
		
		StringBuffer sbf1 = new StringBuffer("");
		StringBuffer sbf2 = new StringBuffer("");
		String type="3";
		if(gg!=null && gg.size()>0){
			sbf1.append("[");
			sbf1.append("{\"pripid\":\"").append(priPid).append("\",\"name\":\"").append(entname).append("\",");
			sbf1.append("\"group\":\"").append("2").append("\"},");
			for(int i=0;i<gg.size();i++){
				Map m = gg.get(i);
				if(i==gg.size()-1){
					sbf1.append("{\"pripid\":\"").append(m.get("pripid")).append("\",");
					sbf1.append("\"name\":\"").append(m.get("name")).append("\",");
					sbf1.append("\"group\":\"").append(type).append("\"}");
					
					sbf2.append("{\"source\":\"").append(m.get("pripid")).append("\",");
					sbf2.append("\"target\":\"").append(priPid).append("\"}");
				}else{
					sbf1.append("{\"pripid\":\"").append(m.get("pripid")).append("\",");
					sbf1.append("\"name\":\"").append(m.get("name")).append("\",");
					sbf1.append("\"group\":\"").append(type).append("\"},");
					
					sbf2.append("{\"source\":\"").append(m.get("pripid")).append("\",");
					sbf2.append("\"target\":\"").append(priPid).append("\"},");
				}
			}
			sbf1.append("],");
		}
		sbf.append(sbf1);
		sbf.append("\"links\":[ ");
		System.out.println(sbf2.toString());
		sbf.append(sbf2);
		sbf.append("]");
		sbf.append("}");
		//查自然人
		//查法人
		sbf.append("");
		System.out.println(sbf.toString());
		return sbf.toString();
	}
	
	/**
	 * 全景分析
	 * @param req
	 * @param res
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/panoramicAnalysis")
	@ResponseBody
	public List findByEntPanoramicAnalysis(OptimusRequest req,OptimusResponse res) throws OptimusException {
		Map rootNode = new HashMap();
		String priPid = req.getParameter("priPid");// 获取主体身份代码
		String sourceflag = req.getParameter("sourceflag");
		String regno = req.getParameter("regno");
		String entname = req.getParameter("entname");
		rootNode.put("priPid", priPid);
		rootNode.put("sourceflag", sourceflag);
		rootNode.put("regno", regno);
		rootNode.put("entname", entname);
		List<Map> funcList = analysisService.findByEntPanoramicAnalysis(rootNode);
		List<Map> resultList = new ArrayList<Map>();
		if (funcList != null && funcList.size() > 0) {
			Random r = new Random();
			// 排序前的数据
			Map<String, Map> sortBeforeMap = new HashMap<String, Map>();
			String[] sortBeforeStr = new String[funcList.size()];// 排序前的数据key
			int i = 0;
			String str = "";
			List<Long> exist = new ArrayList<Long>();
			int j = 0;
			for (Map map : funcList) {
				map.put("value", r.nextInt(1000));
				long value = DateUtil.calToUTC(
						(String) map.get("operationtime"),
						DateUtil.YYYYMMDDHHMISS);
				// System.out.println("原始："+value);
				if (exist.contains(value)) {
					j++;
					value = value + (j);
				}
				// System.out.println("改变："+value);
				exist.add(value);
				map.put("operationtime", value);
				// System.out.println("--"+(String)map.get("pkidname")+"==");
				map.put("pkidname", ((String) map.get("pkidname")).trim());
				String typeFlag = (String) map.get("type");
				if ("199".equals(typeFlag)) {
					map.put("title",
							map.get("title") == null ? map.get("title")
									: "".equals(((String) map.get("title"))
											.trim()) ? "变更" : map.get("title"));
				}
				map.put("describe",
						map.get("describe") == null ? map.get("title")
								: "".equals(((String) map.get("describe"))
										.trim()) ? map.get("title")
										: ((String) map.get("describe"))
												.replaceAll("\n", ""));
				str = value + "-" + i;
				sortBeforeMap.put(str, map);
				sortBeforeStr[i] = str;
				i++;
			}
			String[] sortAfterStr = SortUtil.bubbleSort(sortBeforeStr);

			for (int z = sortAfterStr.length - 1; z >= 0; z--) {
				// 遍历map重新查询代码对应的中文
				Map paramsMap = sortBeforeMap.get(sortAfterStr[z]);// analysisService.findErgodicMap(sortBeforeMap.get(sortAfterStr[z]));
				if (paramsMap != null && paramsMap.size() > 0) {
					resultList.add(paramsMap);
				}
			}
			List<Map> zdxList = analysisService.findByZDXPanoramicAnalysis(rootNode);
			if (zdxList != null && zdxList.size() > 0) {
				for (Map zdx : zdxList) {
					zdx.put("pkidname", ((String) zdx.get("pkidname")).trim());
					zdx.put("value", r.nextInt(1000));
					String lastFlag = sortAfterStr[0].split("-")[0];
					zdx.put("operationtime",
							Long.parseLong(lastFlag) + 86400000L);
					// zdx.put("localtime", Long.parseLong(lastFlag)+1L);
					resultList.add(zdx);
					break;
				}
			}
			Map m = new HashMap();
			m.put("value", r.nextInt(1000));
			String lastFlag = sortAfterStr[0].split("-")[0];
			m.put("operationtime", Long.parseLong(lastFlag) + 86400000L + 100L);
			resultList.add(m);
		}
		return resultList;
	}

	/**
	 * 全景分析
	 * @param req
	 * @param res
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/panoramicAnalysiss")
	@ResponseBody
	public List findByEntPanoramicAnalysiss(OptimusRequest req,OptimusResponse res) throws OptimusException {
		Map rootNode = new HashMap();
		rootNode.put("priPid", req.getParameter("priPid"));
		rootNode.put("sourceflag", req.getParameter("sourceflag"));
		rootNode.put("regno", req.getParameter("regno"));
		rootNode.put("entname", req.getParameter("entname"));
		List<List<Map>> resultListMap = new ArrayList<List<Map>>();
		List<Map> funcList = analysisService.findByEntPanoramicAnalysis(rootNode,"0");//登记
		resultListMap.add(dealData(rootNode,funcList,"0"));
		
		funcList = analysisService.findByEntPanoramicAnalysis(rootNode,"1");//其他处罚、老赖
		resultListMap.add(dealData(rootNode,funcList,"1"));
		
		funcList = analysisService.findByEntPanoramicAnalysis(rootNode,"2");//广告
		resultListMap.add(dealData(rootNode,funcList,"2"));
		
		funcList = analysisService.findByEntPanoramicAnalysis(rootNode,"3");//商标
		resultListMap.add(dealData(rootNode,funcList,"3"));
		
		funcList = analysisService.findByEntPanoramicAnalysis(rootNode,"4");//监管
		resultListMap.add(dealData(rootNode,funcList,"4"));
		
		funcList = analysisService.findByEntPanoramicAnalysis(rootNode,"5");//其他许可
		resultListMap.add(dealData(rootNode,funcList,"5"));
		return resultListMap;
	}

	private List<Map> dealData(Map rootNode,List<Map> funcList, String type) throws OptimusException {
		List<Map> resultList = new ArrayList<Map>();
		if (funcList != null && funcList.size() > 0) {
			Map<String, Map> sortBeforeMap = new HashMap<String, Map>(); //排序前的数据
			String[] sortBeforeStr = new String[funcList.size()]; //排序前的数据key
			int i = 0;
			String str = "";
			List<Long> exist = new ArrayList<Long>();
			int j = 0;
			for (Map map : funcList) {
				if("0".equals(type)){
					map.put("value", 50);//randomInt(0,100)
				}else if("1".equals(type)){
					map.put("value", 450);//randomInt(401,500)
				}else if("2".equals(type)){
					map.put("value", 350);//randomInt(301,400)
				}else if("3".equals(type)){
					map.put("value", 250);//randomInt(201,300)
				}else if("4".equals(type)){
					map.put("value", 550);//randomInt(501,600)
				}else{
					map.put("value", 150);//randomInt(101,200)
				}
				long value = DateUtil.calToUTC((String) map.get("operationtime"),DateUtil.YYYYMMDDHHMISS);
				if (exist.contains(value)) {
					j++;
					value = value + (j);
				}
				exist.add(value);
				map.put("operationtime", value);
				map.put("pkidname", ((String) map.get("pkidname")).trim());
				String typeFlag = (String) map.get("type");
				if ("199".equals(typeFlag)) {
					map.put("title",map.get("title") == null ? map.get("title")
									: "".equals(((String) map.get("title")).trim()) ? "变更" : map.get("title"));
				}
				map.put("describe",map.get("describe") == null ? map.get("title")
								: "".equals(((String) map.get("describe")).trim()) ? map.get("title")
										: ((String) map.get("describe")).replaceAll("\n", ""));
				str = value + "-" + i;
				sortBeforeMap.put(str, map);
				sortBeforeStr[i] = str;
				i++;
			}
			String[] sortAfterStr = SortUtil.bubbleSort(sortBeforeStr);

			for (int z = sortAfterStr.length - 1; z >= 0; z--) {// 遍历map重新查询代码对应的中文
				Map paramsMap = sortBeforeMap.get(sortAfterStr[z]);
				if (paramsMap != null && paramsMap.size() > 0) {
					resultList.add(paramsMap);
				}
			}
			if("0".equals(type)){//注吊销(多个注吊销只保留最后一个)
				List<Map> zdxList = analysisService.findByEntPanoramicAnalysis(rootNode,"6");
				if (zdxList != null && zdxList.size() > 0) {
					List<Map> zdxsList = new ArrayList<Map>();
					for (Map zdx : zdxList) {
						if(StringUtils.isNotBlank((String)zdx.get("operationtime"))){
							zdxsList.add(zdx);
						}
					}
					if (zdxsList != null && zdxsList.size() > 0) {
						resultList.add(returnMapZdx(zdxsList));
					}else{
						Map zdx = zdxList.get(0);
						zdx.put("pkidname", ((String) zdx.get("pkidname")).trim());
						zdx.put("value", 50);
						String lastFlag = sortAfterStr[0].split("-")[0];
						zdx.put("operationtime",Long.parseLong(lastFlag) + 86400000L);
						resultList.add(zdx);
					}
				}
			}
			/*Map m = new HashMap();
			m.put("value", r.nextInt(1000));
			String lastFlag = sortAfterStr[0].split("-")[0];
			m.put("operationtime", Long.parseLong(lastFlag) + 86400000L + 100L);
			resultList.add(m);*/
		}
		return resultList;
	}
	
	private Map returnMapZdx(List<Map> zdxList){
		Map<String, Map> sortBeforeMap = new HashMap<String, Map>(); //排序前的数据
		String[] sortBeforeStr = new String[zdxList.size()]; //排序前的数据key
		int i = 0;
		String str = "";
		List<Long> exist = new ArrayList<Long>();
		int j = 0;
		for (Map map : zdxList) {
			map.put("value", 50);
			long value = DateUtil.calToUTC((String) map.get("operationtime"),DateUtil.YYYYMMDDHHMISS);
			if (exist.contains(value)) {
				j++;
				value = value + (j);
			}
			exist.add(value);
			map.put("operationtime", value);
			map.put("pkidname", ((String) map.get("pkidname")).trim());
			map.put("describe",map.get("describe") == null ? map.get("title"): "".equals(((String) map.get("describe")).trim()) ? map.get("title"): ((String) map.get("describe")).replaceAll("\n", ""));
			str = value + "-" + i;
			sortBeforeMap.put(str, map);
			sortBeforeStr[i] = str;
			i++;
		}
		Map paramsMap = new HashMap();
		if(zdxList.size()==1){//只有一条记录不执行排序
			paramsMap = sortBeforeMap;
		}else{//排序
			String[] sortAfterStr = SortUtil.bubbleSort(sortBeforeStr);
			paramsMap = sortBeforeMap.get(sortAfterStr[0]);
		}
		/*for (int z = sortAfterStr.length - 1; z >= 0; z--) {// 遍历map重新查询代码对应的中文
			Map paramsMap = sortBeforeMap.get(sortAfterStr[z]);
			if (paramsMap != null && paramsMap.size() > 0) {
				resultList.add(paramsMap);
			}
		}*/
		return paramsMap;
	}

	/**
	 * 全景分析详细
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws OptimusException
	 */
	@ResponseBody
	@RequestMapping("panoramicAnalysisDetail")
	public Map<String, Object> findByEntPanoramicAnalysisDetail(
			OptimusRequest req, OptimusResponse res) throws OptimusException {
		/** 原始基本信息参数 */
		int marketflag = Integer
				.parseInt(req.getParameter("marketflag") == null ? "999" : ""
						.equals(req.getParameter("marketflag").trim()) ? "999"
						: req.getParameter("marketflag").trim());
		String marketpriPid = req.getParameter("marketpriPid");// 获取主体身份代码
		String marketsourceflag = req.getParameter("marketsourceflag");
		String markettype = req.getParameter("markettype");
		String marketeconomicproperty = req
				.getParameter("marketeconomicproperty");
		/** 全景分析参数 */
		String id = req.getParameter("id");
		String pkidname = req.getParameter("pkidname");
		String pkidvalue = req.getParameter("pkidvalue");
		String pripid = req.getParameter("pripid");
		String sourceflag = req.getParameter("sourceflag");
		String type = req.getParameter("type");// 用来判断执行哪个详细字段
		String tablename = req.getParameter("tablename");
		String title = req.getParameter("title");
		String name = req.getParameter("name");
		String describe = req.getParameter("describe");
		String operationtime = req.getParameter("operationtime");
		String timestamp = req.getParameter("timestamp");
		String localtime = req.getParameter("localtime");
		String strIndex = "";

		Map<String, Object> dataMap = new HashMap<String, Object>();
		String runType = "";
		switch (marketflag) {
		case 0:
			if (markettype != null && markettype.trim() != "") {
				strIndex = markettype.substring(0, 1);
			}
			/*
			 * if (markettype != null && markettype.trim() != "") { strIndex =
			 * markettype.substring(0, 1); switch (strIndex) { case "0": runType
			 * = "1"; break; case "1": case "2": case "3": case "4": case "5":
			 * runType = "0"; break; case "6": case "7": runType = "2"; break;
			 * default: runType = "0"; break; } } else { runType = "0"; }
			 */
			if ("3".equals(marketeconomicproperty)) {// 外资信息
				runType = "2";
			} else {
				if ("0".equals(markettype)) {// 个体信息
					runType = "1";
				} else {// 内资信息
					runType = "0";
				}
			}
			dataMap = analysisService.findByEntPanoramicAnalysisDetail(
					tablename, pkidname, pkidvalue, runType);
		default:
			break;
		}

		Map<String, Object> returnMap = new HashMap<String, Object>();
		String returnString = "";
		if (dataMap != null && dataMap.size() > 0) {
			// returnMap.put("entname", dataMap.get("entname"));
			// returnMap.put("regno", dataMap.get("regno"));
			// returnMap.put("enttype", dataMap.get("enttype"));
			try {
				Map<String, Object> datasMap = new HashMap<String, Object>();
				Map<String, Object> sortMap = analysisService.findByTabName(
						tablename, type, strIndex);
				for (Map.Entry<String, Object> entry : sortMap.entrySet()) {
					sortMap.put(entry.getKey(), dataMap.get(entry.getValue()));
				}
				datasMap.put("weaponMap", sortMap);
				if ("t_aj_ajjbxx".equals(tablename.toLowerCase())) {// 如果是案件，执行案件
					returnString = FreemarkerUtil
							.returnString(ConfigManager
									.getProperty("freemarkerCaseFileName"),
									datasMap);
				} else {
					returnString = FreemarkerUtil.returnString(
							ConfigManager.getProperty("freemarkerQFileName"),
							datasMap);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		}
		returnMap.put("returnString", returnString);
		return returnMap;
	}
}