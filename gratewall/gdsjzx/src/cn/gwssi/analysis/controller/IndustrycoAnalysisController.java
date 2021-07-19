package cn.gwssi.analysis.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gwssi.analysis.service.IndustrycoAnalysisService;

import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

/**
 * 
 * @author wuyincheng ,Jul 28, 2016
 */
//

@Controller
@RequestMapping("/analysis")
public class IndustrycoAnalysisController {

	@Autowired
	private IndustrycoAnalysisService service;
	
	
	
	//年报
	@SuppressWarnings("rawtypes")
	@RequestMapping("/yannualReportPageInfo")
	@ResponseBody
	public List<Map> getYannualReportPageInfo(OptimusRequest req, OptimusResponse res) {
		try {
			String startTime = req.getParameter("startTime");
			String endTime = req.getParameter("endTime");
			int dateType = Integer.parseInt(req.getParameter("dateType"));
			String measureCode = req.getParameter("measureCode");     //度量维度
			//广东省" _code=1
			//珠三角" _code=2
			//粤北" _code=3
			//粤东" _code=4
			//粤西" _code=5
			String sourceCode = req.getParameter("sourceCode");       //登记机关code
			String sourceParent = req.getParameter("sourceParent");   //登记机关是否选取的第一级
			String indusCode = req.getParameter("indusCode");         //行业类型code
			//1-个体（9999）；
			//2-农专（9100,9200）；
			//3-常驻代表机构（开头为7 ）;  4 - 其他  企业 ；
			String enterCode = req.getParameter("enterCode");	
			String ancheyear = req.getParameter("ancheyear");         //年报年份	
			return service.selectYannualReportPageInfo(startTime, endTime, dateType, formatMeasureCode(measureCode), 
					                   sourceCode, indusCode, enterCode, ancheyear, sourceParent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 行业-第一层数据获取
	@SuppressWarnings("rawtypes")
	@RequestMapping("/industryPageInfo")
	@ResponseBody
	public List<Map> getStartPageInfo(OptimusRequest req, OptimusResponse res) {
		try {
			String startTime = req.getParameter("startTime");
			String endTime = req.getParameter("endTime");
			int dateType = Integer.parseInt(req.getParameter("dateType"));
			String measureCode = req.getParameter("measureCode");
			String isBG = req.getParameter("isBG");
			dateType = (dateType > 1 || dateType < 0 ? 0 : dateType);
			return service
					.selectData(startTime, endTime, dateType, formatMeasureCode(measureCode), isBG);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 行业-第二层数据获取
	@SuppressWarnings("rawtypes")
	@RequestMapping("/industryPageInfoByType")
	@ResponseBody
	public List<Map> getStartPageInfoByType(OptimusRequest req,
			OptimusResponse res) {
		try {
			String startTime = req.getParameter("startTime");
			String endTime = req.getParameter("endTime");
			int dateType = Integer.parseInt(req.getParameter("dateType"));
			dateType = (dateType > 1 || dateType < 0 ? 0 : dateType);
			String measureCode = req.getParameter("measureCode");
			String indusCode = req.getParameter("indusCode");
			String isBG = req.getParameter("isBG");
			if (measureCode == null || "".equals(measureCode.trim()))
				return null;
			String t = req.getParameter("type");
			int type = -1;
			if (t != null && !"".equals(t.trim())) {
				type = Integer.parseInt(req.getParameter("type"));
			}
			type = (type > 2 || type < -1 ? -1 : type);
			return service.selectDataByType(startTime, endTime, formatMeasureCode(measureCode),
					dateType, type, indusCode, isBG);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//按企业与产业分析industryWithEnterPageInfo
	@SuppressWarnings("rawtypes")
	@RequestMapping("/industryWithEnterPageInfo")
	@ResponseBody
	public List<Map> getStartPageInfoByInduAndEnter(OptimusRequest req,
			OptimusResponse res) {
		try {
			String measureCode = req.getParameter("measureCode");
			String enterpriseCode = req.getParameter("enterpriseCode");
			String startTime = req.getParameter("startTime");
			String endTime = req.getParameter("endTime");
			String isBG = req.getParameter("isBG");
			if (measureCode == null || "".equals(measureCode.trim()))
				return null;
			return service.selectDataByMCodeAndECode(startTime, endTime, formatMeasureCode(measureCode), enterpriseCode, isBG);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//经济性质和产业分析 （第一层 & 第二层）
	@SuppressWarnings("rawtypes")
	@RequestMapping("/economicAndIndustycoPageInfo")
	@ResponseBody
	public List<Map> getEconomicAndIndustycoPageInfo(OptimusRequest req,
			OptimusResponse res) {
		try {
			String measureCode = req.getParameter("measureCode");
			String economicCode = req.getParameter("economicCode");
			String startTime = req.getParameter("startTime");
			//String endTime = req.getParameter("endTime");
			String industryCode = req.getParameter("industryCode");
			String parentIndustryCode = req.getParameter("parentIndustryCode");// 1 2 3 代表第一第二第三产业(一级)
			String isSecond = req.getParameter("isSecond");
			String isBG = req.getParameter("isBG");
			if (measureCode == null || "".equals(measureCode.trim()))
				return null;
			return service.selectEconomicAndIndustycoPageInfo(startTime, formatMeasureCode(measureCode), economicCode, industryCode, parentIndustryCode, isSecond, isBG);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//注册资金规模和产业分析 （第一层 & 第二层）
	@SuppressWarnings("rawtypes")
	@RequestMapping("/domScaleAndIndustycoPageInfo")
	@ResponseBody
	public List<Map> getDomScaleAndIndustycoPageInfo(OptimusRequest req,
			OptimusResponse res) {
		try {
			String measureCode = req.getParameter("measureCode");
			String economicCode = req.getParameter("economicCode");
			String startTime = req.getParameter("startTime");
			//String endTime = req.getParameter("endTime");
			String industryCode = req.getParameter("industryCode");
			String parentIndustryCode = req.getParameter("parentIndustryCode");// 1 2 3 代表第一第二第三产业(一级)
			String isSecond = req.getParameter("isSecond");
			String isBG = req.getParameter("isBG");
			if (measureCode == null || "".equals(measureCode.trim()))
				return null;
			return service.selectDomScaleAndIndustycoPageInfo(startTime, formatMeasureCode(measureCode), economicCode, industryCode, parentIndustryCode, isSecond, isBG);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//全省各市及经济性质分析
	@SuppressWarnings("rawtypes")
	@RequestMapping("/allProvinceAndEconomicPageInfo")
	@ResponseBody
	public List<Map> getAllProvinceAndEconomic(OptimusRequest req,
			OptimusResponse res) {
		try {
			String measureCode = req.getParameter("measureCode");
			String enterpriseCode = req.getParameter("enterpriseCode");//省份
			String economicCode = req.getParameter("economicCode");//经济性质
			String startTime = req.getParameter("startTime");
		//	String endTime = req.getParameter("endTime");
			String isBG = req.getParameter("isBG");
			if (measureCode == null || "".equals(measureCode.trim()))
				return null;
			return service.selectAllProvinceAndEconomic(startTime, formatMeasureCode(measureCode), enterpriseCode, economicCode, isBG);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//全省各市及资金规模
	@SuppressWarnings("rawtypes")
	@RequestMapping("/allProvinceAndDomScalePageInfo")
	@ResponseBody
	public List<Map> getAllProvinceAndDomScale(OptimusRequest req,
			OptimusResponse res) {
		try {
			String measureCode = req.getParameter("measureCode");
			String enterpriseCode = req.getParameter("enterpriseCode");//省份
			String economicCode = req.getParameter("economicCode");//经济规模
			String startTime = req.getParameter("startTime");
			//String endTime = req.getParameter("endTime");
			String isBG = req.getParameter("isBG");
			if (measureCode == null || "".equals(measureCode.trim()))
				return null;
			return service.selectAllProvinceAndDomScale(startTime, formatMeasureCode(measureCode), enterpriseCode, economicCode, isBG);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//按注册类型及注册资金规模
	//全省各市及资金规模
	@SuppressWarnings("rawtypes")
	@RequestMapping("/regtypeAndDomScalePageInfo")
	@ResponseBody
	public List<Map> getRegtypeAndDomScale(OptimusRequest req,
			OptimusResponse res) {
		try {
			String measureCode = req.getParameter("measureCode");
			//String enterpriseCode = req.getParameter("enterpriseCode");//注册类型
			String economicCode = req.getParameter("economicCode");//经济规模
			String startTime = req.getParameter("startTime");
			//String endTime = req.getParameter("endTime");
			String isBG = req.getParameter("isBG");
			if (measureCode == null || "".equals(measureCode.trim()))
				return null;
			return service.selectRegtypeAndDomScale(startTime, formatMeasureCode(measureCode), null, economicCode, isBG);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// 维度，，行业和产业分类 x轴 ==各级地市 -第一层数据获取
	@SuppressWarnings("rawtypes")
	@RequestMapping("/industryAndProvinceInfo")
	@ResponseBody
	public List<Map> getIndustryAndProvinceInfo(OptimusRequest req,
			OptimusResponse res) {
		try {
			String startTime = req.getParameter("startTime");
		//	String endTime = req.getParameter("endTime");
		//	int dateType = Integer.parseInt(req.getParameter("dateType"));
			String measureCode = req.getParameter("measureCode");
		//	dateType = (dateType > 1 || dateType < 0 ? 0 : dateType);
			String isBG = req.getParameter("isBG");
			return service.selectIndustryAndEnterprise(startTime, formatMeasureCode(measureCode), isBG);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 行业-第二层数据获取
	@SuppressWarnings("rawtypes")
	@RequestMapping("/valueByIndustry")
	@ResponseBody
	public List<Map> getValueByIndustry(OptimusRequest req, OptimusResponse res) {
		try {
			String startTime = req.getParameter("startTime");
			String endTime = req.getParameter("endTime");
			//int dateType = Integer.parseInt(req.getParameter("dateType"));
			//dateType = (dateType > 1 || dateType < 0 ? 0 : dateType);
			String measureCode = req.getParameter("measureCode");
			String indusCode = req.getParameter("indusCode");
			if (measureCode == null || "".equals(measureCode.trim()))
				return null;
			String t = req.getParameter("type");
			int type = -1;
			if (t != null && !"".equals(t.trim())) {
				type = Integer.parseInt(req.getParameter("type"));
			}
			type = (type > 2 || type < -1 ? -1 : type);
			String isBG = req.getParameter("isBG");
			return service.selectIndustryAndEnterprise(startTime, endTime,
					formatMeasureCode(measureCode), 0, type, indusCode, isBG);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 维度，，二级经济性质分类 x轴 ==区域 -一层数据获取
	@SuppressWarnings("rawtypes")
	@RequestMapping("/economicAndProvinceInfo")
	@ResponseBody
	public List<Map> getEconomicAndAreaInfo(OptimusRequest req,
			OptimusResponse res) {
		try {
			String startTime = req.getParameter("startTime");
		//	String endTime = req.getParameter("endTime");
		//	int dateType = Integer.parseInt(req.getParameter("dateType"));
		//	dateType = (dateType > 1 || dateType < 0 ? 0 : dateType);
			String measureCode = req.getParameter("measureCode");
			String isBG = req.getParameter("isBG");
			if (measureCode == null || "".equals(measureCode.trim()))
				return null;
			return service.selectEconomicAndAreaData(startTime,
					formatMeasureCode(measureCode), isBG);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	// 区域-第一层数据获取
	@SuppressWarnings("rawtypes")
	@RequestMapping("/areaInfo")
	@ResponseBody
	public List<List> getAreaInfo(OptimusRequest req, OptimusResponse res) {
		try {
			String startTime = req.getParameter("startTime");
			String endTime = req.getParameter("endTime");
			int dateType = Integer.parseInt(req.getParameter("dateType"));
			String measureCode = req.getParameter("measureCode");
			dateType = (dateType > 1 || dateType < 0 ? 0 : dateType);
			String isBG = req.getParameter("isBG");
			return service.selectAreaInfo(startTime, endTime, dateType,
					formatMeasureCode(measureCode), isBG);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 区域-第二层数据获取 industryPageInfoByType
	@SuppressWarnings("rawtypes")
	@RequestMapping("/provinceByArea")
	@ResponseBody
	public List<Map> getProvinceByArea(OptimusRequest req, OptimusResponse res) {
		try {
			String startTime = req.getParameter("startTime");
			String endTime = req.getParameter("endTime");
			int dateType = Integer.parseInt(req.getParameter("dateType"));
			dateType = (dateType > 1 || dateType < 0 ? 0 : dateType);
			String measureCode = req.getParameter("measureCode");
			String indusCode = req.getParameter("areaScope");
			if (measureCode == null || "".equals(measureCode.trim()))
				return null;
			String t = req.getParameter("type");
			int type = -1;
			if (t != null && !"".equals(t.trim())) {
				type = Integer.parseInt(req.getParameter("type"));
			}
			String isBG = req.getParameter("isBG");
			type = (type > 2 || type < -1 ? -1 : type);
			return service.selectProvinceByArea(startTime, endTime,
					formatMeasureCode(measureCode), dateType, type, indusCode, isBG);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 经济-第一&二层数据获取
	@SuppressWarnings("rawtypes")
	@RequestMapping("/economicPageInfo")
	@ResponseBody
	public List<Map> getEconomicPageInfo(OptimusRequest req, OptimusResponse res) {
		try {
			String startTime = req.getParameter("startTime");
			String endTime = req.getParameter("endTime");
			int dateType = Integer.parseInt(req.getParameter("dateType"));
			dateType = (dateType > 1 || dateType < 0 ? 0 : dateType);
			String measureCode = req.getParameter("measureCode");
			String economicCode = req.getParameter("economicCode");
			if (measureCode == null || "".equals(measureCode.trim()))
				return null;
			String t = req.getParameter("parentCode");
			int parentCode = -1;
			if (t != null && !"".equals(t.trim())) {
				parentCode = Integer.parseInt(req.getParameter("parentCode"));
			}
			String isSecondData = req.getParameter("isSecondData");
			parentCode = (parentCode > 3 || parentCode < -1 ? -1 : parentCode);
			String isBG = req.getParameter("isBG");
			return service.selectEconomicData(startTime, endTime, dateType,
					parentCode, formatMeasureCode(measureCode), economicCode, isSecondData, isBG);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 企业 selectEnterpriseData
	@SuppressWarnings("rawtypes")
	@RequestMapping("/enterprisePageInfo")
	@ResponseBody
	public List<Map> getEnterprisePageInfo(OptimusRequest req,
			OptimusResponse res) {
		try {
			String startTime = req.getParameter("startTime");
			String endTime = req.getParameter("endTime");
			int dateType = Integer.parseInt(req.getParameter("dateType"));
			dateType = (dateType > 1 || dateType < 0 ? 0 : dateType);
			String measureCode = req.getParameter("measureCode");
			if (measureCode == null || "".equals(measureCode.trim()))
				return null;
			String enterpriseCode = req.getParameter("enterpriseCode");
			String isBG = req.getParameter("isBG");
			return service.selectEnterpriseData(startTime, endTime, dateType,
					formatMeasureCode(measureCode), enterpriseCode, isBG);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String formatMeasureCode(String measure){
//		if(measure == null)
//			return "";
//		return formatMCode.get(measure.replaceAll(",", "").trim());
		return measure;
	}

	// 资金规模 domscale
	@SuppressWarnings("rawtypes")
	@RequestMapping("/domScalePageInfo")
	@ResponseBody
	public List<Map> getDomScalePageInfo(OptimusRequest req,
			OptimusResponse res) {
		try {
			String startTime = req.getParameter("startTime");
			String endTime = req.getParameter("endTime");
			int dateType = Integer.parseInt(req.getParameter("dateType"));
			dateType = (dateType > 1 || dateType < 0 ? 0 : dateType);
			String measureCode = req.getParameter("measureCode");
			if (measureCode == null || "".equals(measureCode.trim()))
				return null;
			String domCode = req.getParameter("domCode");
			String isSecond = req.getParameter("isSecond");
			String isBG = req.getParameter("isBG");
			return service.selectDomScaleData(startTime, endTime, dateType,
					formatMeasureCode(measureCode), domCode, isSecond, isBG);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
		// 获取Area+ 区域
		@SuppressWarnings("rawtypes")
		@RequestMapping("/sencondAreaInfo")
		@ResponseBody
		public List<Map> getSencondAreaInfo(OptimusRequest req, OptimusResponse res) {
			try {
				return service.selectSencondAreaInfo();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
	// 获取所有 区域
	@SuppressWarnings("rawtypes")
	@RequestMapping("/allAreaInfo")
	@ResponseBody
	public List<Map> getAllAreaInfo(OptimusRequest req, OptimusResponse res) {
		try {
			return service.selectAllAreaInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 获取indus+ 行业产业
	@SuppressWarnings("rawtypes")
	@RequestMapping("/sencondIndusInfo")
	@ResponseBody
	public List<Map> getSencondIndusInfo(OptimusRequest req, OptimusResponse res) {
		try {
			return (service.selectSencondIndusInfoInfo()).get(req
					.getParameter("type"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 获取enconomicInfo 经济配置信息
	@SuppressWarnings("rawtypes")
	@RequestMapping("/economicCodeInfo")
	@ResponseBody
	public List<Map> getEconomicCodeInfo(OptimusRequest req, OptimusResponse res) {
		try {
			return service.selectEconomicCodeInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 企业code
	@SuppressWarnings("rawtypes")
	@RequestMapping("/enterpriseCodeInfo")
	@ResponseBody
	public List<Map> getEnterpriseCodeInfo(OptimusRequest req,
			OptimusResponse res) {
		try {
			return service.selectEnterpriseCodeInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 经济性质domscale
	@SuppressWarnings("rawtypes")
	@RequestMapping("/domScaleCodeInfo")
	@ResponseBody
	public List<Map> getDomScaleCodeInfo(OptimusRequest req, OptimusResponse res) {
		try {
			return service.selectDomScaleCodeInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 *   获取measureInfo  
		 0--市场主体概况  '期末和期末资本金   包括注销吊销...'  
		 1--企业设立登记  // 没有固定指标 
		 2--市场准入和退出   '本期和本期资本金'     
		 3--业务办理情况
	 * */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/measureInfo")
	@ResponseBody
	public List<Map> getMeasureInfo(OptimusRequest req, OptimusResponse res) {
		String indexType=req.getParameter("indexType");
		
		try {
			return service.selectMeasureInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// private static SimpleDateFormat getSdf(){
	// return new SimpleDateFormat("yyyy-MM-dd");
	// }
	


}
