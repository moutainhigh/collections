package cn.gwssi.analysis.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gwssi.analysis.service.IndustrycoAnalysisService;
import cn.gwssi.analysis.service.MarketSubjectAnalysisService;

import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

/**
 * 
 * @author wuyincheng ,Jul 28, 2016
 */

@Controller
@RequestMapping("/analysis")
public class MarketSubjectAnalysisController {
	//marketSubject
	@Autowired
	private MarketSubjectAnalysisService service;

	// 行业-第一层数据获取
	@SuppressWarnings("rawtypes")
	@RequestMapping("/marketSubjectPageInfo")
	@ResponseBody
	public List<Map> getStartPageInfo(OptimusRequest req, OptimusResponse res) {
		try {
			String startTime = req.getParameter("startTime");
			String endTime = req.getParameter("endTime");
			int dateType = Integer.parseInt(req.getParameter("dateType"));
			String measureCode = req.getParameter("measureCode");
			dateType = (dateType > 1 || dateType < 0 ? 0 : dateType);
			return service
					.selectData(startTime, endTime, dateType, measureCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 行业-第二层数据获取
	@SuppressWarnings("rawtypes")
	@RequestMapping("/marketSubjectPageInfoByType")
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
			if (measureCode == null || "".equals(measureCode.trim()))
				return null;
			String t = req.getParameter("type");
			int type = -1;
			if (t != null && !"".equals(t.trim())) {
				type = Integer.parseInt(req.getParameter("type"));
			}
			type = (type > 2 || type < -1 ? -1 : type);
			return service.selectDataByType(startTime, endTime, measureCode,
					dateType, type, indusCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// 市场主体-区域-第一层数据获取
		@SuppressWarnings("rawtypes")
		@RequestMapping("/areaMarketSubjectInfo")
		@ResponseBody
		public List<Map> getMarketSubjectAreaInfo(OptimusRequest req, OptimusResponse res) {
			try {
				String startTime = req.getParameter("startTime");
				String endTime = req.getParameter("endTime");
				int dateType = Integer.parseInt(req.getParameter("dateType"));
				String measureCode = req.getParameter("measureCode");
				dateType = (dateType > 1 || dateType < 0 ? 0 : dateType);
				return service
						.selectMarketSubjectInfo(startTime, endTime, dateType, measureCode);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		// 区域-第二层数据获取  industryPageInfoByType
		@SuppressWarnings("rawtypes")
		@RequestMapping("/provinceByAreaByMarketSubject")
		@ResponseBody
		public List<Map> getProvinceByArea(OptimusRequest req,
				OptimusResponse res) {
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
				type = (type > 2 || type < -1 ? -1 : type);
				return service.selectProvinceByAreaByMarketSubject(startTime, endTime, measureCode,
						dateType, type, indusCode);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		
		// 市场主体-组织形式-第一层数据获取
			@SuppressWarnings("rawtypes")
			@RequestMapping("/enterpriseMarketSubjectInfo")
			@ResponseBody
			public List<Map> getEnterpriseMarketSubjectInfo(OptimusRequest req, OptimusResponse res) {
				try {
					String startTime = req.getParameter("startTime");
					String endTime = req.getParameter("endTime");
					int dateType = Integer.parseInt(req.getParameter("dateType"));
					String measureCode = req.getParameter("measureCode");
					dateType = (dateType > 1 || dateType < 0 ? 0 : dateType);
					return service
							.selectEnterpriseMarketSubjectInfo(startTime, endTime, dateType, measureCode);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			// 组织形式-第二层数据获取  industryPageInfoByType
			@SuppressWarnings("rawtypes")
			@RequestMapping("/secondByEnterpriseByMarketSubject")
			@ResponseBody
			public List<Map> getsecondByEnterprise(OptimusRequest req,
					OptimusResponse res) {
				try {
					String startTime = req.getParameter("startTime");
					String endTime = req.getParameter("endTime");
					int dateType = Integer.parseInt(req.getParameter("dateType"));
					dateType = (dateType > 1 || dateType < 0 ? 0 : dateType);
					String measureCode = req.getParameter("measureCode");
					String organizationScope = req.getParameter("organizationScope");
					if (measureCode == null || "".equals(measureCode.trim()))
						return null;
					String t = req.getParameter("type");
					int type = -1;
					if (t != null && !"".equals(t.trim())) {
						type = Integer.parseInt(req.getParameter("type"));
					}
					type = (type > 2 || type < -1 ? -1 : type);
					return service.selectByEnterpriseByMarketSubject(startTime, endTime, measureCode,
							dateType, type, organizationScope);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		
	// 经济-第一&二层数据获取
	@SuppressWarnings("rawtypes")
	@RequestMapping("/economicPageMarketSubjectInfo")
	@ResponseBody
	public List<Map> getEconomicPageMarketSubjectInfo(OptimusRequest req, OptimusResponse res) {
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
			return service.selectEconomicMarketSubjectData(startTime, endTime, dateType,
					parentCode, measureCode, economicCode, isSecondData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 企业 selectEnterpriseData
	@SuppressWarnings("rawtypes")
	@RequestMapping("/enterprisePageMarketSubjectInfo")
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
			return service.selectEnterpriseData(startTime, endTime, dateType,
					measureCode, enterpriseCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
		// 获取Area+ 区域
		@SuppressWarnings("rawtypes")
		@RequestMapping("/sencondAreaMarketSubjectInfo")
		@ResponseBody
		public List<Map> getSencondAreaInfo(OptimusRequest req, OptimusResponse res) {
			try {
				return service.selectSencondAreaInfo();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		// 获取Area+ 区域
				@SuppressWarnings("rawtypes")
				@RequestMapping("/sencondEnterpriseMarketSubjectInfo")
				@ResponseBody
				public List<Map> getSencondEnterpriseInfo(OptimusRequest req, OptimusResponse res) {
					try {
						return service.selectSencondEnterpriseInfo();
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}

	// 获取indus+ 行业产业
	@SuppressWarnings("rawtypes")
	@RequestMapping("/sencondIndusMarketSubjectInfo")
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
	@RequestMapping("/economicCodeMarketSubjectInfo")
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
	@RequestMapping("/enterpriseCodeMarketSubjectInfo")
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
	@RequestMapping("/domScaleCodeMarketSubjectInfo")
	@ResponseBody
	public List<Map> getDomScaleCodeInfo(OptimusRequest req, OptimusResponse res) {
		try {
			return service.selectDomScaleCodeInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 获取measureInfo
	@SuppressWarnings("rawtypes")
	@RequestMapping("/measureMarketSubjectInfo")
	@ResponseBody
	public List<Map> getMeasureInfo(OptimusRequest req, OptimusResponse res) {
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

	public static void main(String[] args) {
		String s = "1990-08-01";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			sdf.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
