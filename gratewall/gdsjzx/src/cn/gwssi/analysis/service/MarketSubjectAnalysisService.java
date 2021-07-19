package cn.gwssi.analysis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.service.BaseService;

/**
 * 产业分析
 * 
 * @author wuyincheng ,Jul 28, 2016
 */
@Service
public class MarketSubjectAnalysisService extends BaseService {

	private static final String[] EMPTY = new String[] {};
	// measure_code配置信息缓存
	@SuppressWarnings("rawtypes")
	private SimpleCache<List<Map>> measureCache = new SimpleCache<List<Map>>(
			600000, null); // 10分钟生效
	// industry_code行业信息缓存
	@SuppressWarnings("rawtypes")
	private SimpleCache<Map<String, List<Map>>> indusCache = new SimpleCache<Map<String, List<Map>>>(
			600000, null); // 10分钟生效
	// economic_code配置信息缓存（经济性质）
	@SuppressWarnings("rawtypes")
	private SimpleCache<List<Map>> economicCache = new SimpleCache<List<Map>>(
			600000, null); // 10分钟生效
	// Enterprise_code 企业代码
	@SuppressWarnings("rawtypes")
	private SimpleCache<List<Map>> enterpriseCache = new SimpleCache<List<Map>>(
			600000, null); // 10分钟生效
	// domscale_code 资金规模/类型
	@SuppressWarnings("rawtypes")
	private SimpleCache<List<Map>> domScaleCache = new SimpleCache<List<Map>>(
			600000, null); // 10分钟生效
	// domscale_code 资金规模/类型
	@SuppressWarnings("rawtypes")
	private SimpleCache<List<Map>> areaCache = new SimpleCache<List<Map>>(
			600000, null); // 10分钟生效
	// T_ANALYSIS_BG_ORGANIZATIONMODE 组织形式
	@SuppressWarnings("rawtypes")
	private SimpleCache<List<Map>> enterpriseOrgCache = new SimpleCache<List<Map>>(
			600000, null); // 10分钟生效
	/**
	 * 行业产业-第一层
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param dateType
	 *            0-日 1-月
	 * @param mark
	 *            0-期末 1-本期
	 * @param measureCode
	 *            参考表 t_reg_dic_measure
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> selectData(String startTime, String endTime, int dateType,
			String measureCode) {
		List<String> cons = new ArrayList<String>();
		final String[] mc = splitMeasureCode(measureCode);
		cons.add(startTime);
		cons.add(endTime);
		for (String mCode : mc) {
			cons.add(mCode);
		}
		List<Map> v1 = null;
		try {
			v1 = this
					.getPersistenceDAO("iqDataSource")
					.queryForList(
							"select sum(a.value) as s,substring(convert(char,transdt,120),0,"
									+ (dateType == 0 ? 10 : 7)
									+ ") as t, code2 as c"
									+ " from (t_analysis_industryco a left join t_dm_hymldm b on a.industryco=b.code1) where transdt>=? and transdt<=? "
									+ (mc.length > 0 ? "and measure in "
											+ getInStru(mc) : "")
									+ " group by t,c order by t ", cons);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return v1;
	}

	/**
	 * 行业产业-第二层 这个函数可以代替第一层
	 * 
	 * @param time
	 *            时间 yyyy-MM-dd || yyyy-MM
	 * @param measureCode
	 *            具体维度
	 * @param dateType
	 *            日期类型
	 * @param type
	 *            0-第一产业 1-第二产业 2-第三产业 -1-代表所有
	 * @Param indusCode 具体行业(code1)
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> selectDataByType(String startTime, String endTime,
			String measureCode, int dateType, int type, String indusCode) {
		List<String> cons = new ArrayList<String>();
		final String[] mc = splitMeasureCode(measureCode);
		final String[] ic = splitMeasureCode(indusCode);
		cons.add(startTime);
		cons.add(endTime);
		for (String mCode : mc) {
			cons.add(mCode);
		}
		for (String iCode : ic) {
			cons.add(iCode);
		}
		List<Map> v1 = null;
		try {
			v1 = this
					.getPersistenceDAO("iqDataSource")
					.queryForList(
							"select sum(a.value) as s,substring(convert(char,transdt,120),0,"
									+ (dateType == 0 ? 10 : 7)
									+ ") as t, value1 n from "
									+ "(t_analysis_industryco a left join t_dm_hymldm b on a.industryco=b.code1) "
									+ "where transdt>=? and transdt<=? "
									+ (mc.length > 0 ? "and measure in "
											+ getInStru(mc) : "")
									+ (type == -1 ? ""
											: "  and code2"
													+ (type == 0 ? "='A'"
															: (type == 1 ? ">'A' and code2 <'F' "
																	: ">'E'")))
									+ (ic.length > 0 ? "and code1 in "
											+ getInStru(ic) : "")
									+ " group by t,n order by t", cons);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return v1;
	}

	/**
	 * 经济性质-第一层 & 第二层
	 * 
	 * @param startTime
	 * @param endTime
	 * @param dateType
	 * @param measureCode
	 * @param economicCode
	 *            经济性质代码
	 * @param parentCode
	 *            0-第一经济 1-第二经济 3-第三经济
	 * @Param isSecondData 是否为第二层数据
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> selectAreaData(String startTime, String endTime,
			int dateType, int parentCode, String measureCode,
			String economicCode, String isSecondData) {
		List<String> cons = new ArrayList<String>();
		final String[] mc = splitMeasureCode(measureCode);
		final String[] ec = splitMeasureCode(economicCode);
		cons.add(startTime);
		cons.add(endTime);
		for (String mCode : mc) {
			cons.add(mCode);
		}
		for (String eCode : ec) {
			cons.add(eCode);
		}
		List<Map> v1 = null;
		try {
			v1 = this
					.getPersistenceDAO("iqDataSource")
					.queryForList(
							"select sum(a.value) as s,substring(convert(char,transdt,120),0,"
									+ (dateType == 0 ? 10 : 7)
									+ ") as t, "
									+ (isSecondData == null ? "parent_code as c from"
											: "b.value as n from ")
									+ "(t_analysis_economicproperty a left join t_dm_jjxzdm b on a.economicproperty=b.code) "
									+ "where transdt>=? and transdt<=? "
									+ (mc.length > 0 ? "and measure in "
											+ getInStru(mc) : "")
									+ (ec.length > 0 ? "and code in "
											+ getInStru(ec) : "")
									+ (parentCode == -1 ? ""
											: "  and parent_code="
													+ (parentCode == 1 ? "'1'"
															: (parentCode == 2 ? "'2'"
																	: "'3'")))
									+ " and parent_code<>'' group by t,"
									+ (isSecondData == null ? "c" : "n")
									+ " order by t", cons);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return v1;
	}

	/**
	 * 经济性质-第一层 & 第二层
	 * 
	 * @param startTime
	 * @param endTime
	 * @param dateType
	 * @param measureCode
	 * @param economicCode
	 *            经济性质代码
	 * @param parentCode
	 *            0-第一经济 1-第二经济 3-第三经济
	 * @Param isSecondData 是否为第二层数据
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> selectEconomicData(String startTime, String endTime,
			int dateType, int parentCode, String measureCode,
			String economicCode, String isSecondData) {
		List<String> cons = new ArrayList<String>();
		final String[] mc = splitMeasureCode(measureCode);
		final String[] ec = splitMeasureCode(economicCode);
		cons.add(startTime);
		cons.add(endTime);
		for (String mCode : mc) {
			cons.add(mCode);
		}
		for (String eCode : ec) {
			cons.add(eCode);
		}
		List<Map> v1 = null;
		try {
			v1 = this
					.getPersistenceDAO("iqDataSource")
					.queryForList(
							"select sum(a.value) as s,substring(convert(char,transdt,120),0,"
									+ (dateType == 0 ? 10 : 7)
									+ ") as t, "
									+ (isSecondData == null ? "parent_code as c from"
											: "b.value as n from ")
									+ "(t_analysis_economicproperty a left join t_dm_jjxzdm b on a.economicproperty=b.code) "
									+ "where transdt>=? and transdt<=? "
									+ (mc.length > 0 ? "and measure in "
											+ getInStru(mc) : "")
									+ (ec.length > 0 ? "and code in "
											+ getInStru(ec) : "")
									+ (parentCode == -1 ? ""
											: "  and parent_code="
													+ (parentCode == 1 ? "'1'"
															: (parentCode == 2 ? "'2'"
																	: "'3'")))
									+ " and parent_code<>'' group by t,"
									+ (isSecondData == null ? "c" : "n")
									+ " order by t", cons);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return v1;
	}
	
	
	/**
	 * 市场主体
	 * 经济性质-第一层 & 第二层
	 * 
	 * @param startTime
	 * @param endTime
	 * @param dateType
	 * @param measureCode
	 * @param economicCode
	 *            经济性质代码
	 * @param parentCode
	 *            0-第一经济 1-第二经济 3-第三经济
	 * @Param isSecondData 是否为第二层数据
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> selectEconomicMarketSubjectData(String startTime, String endTime,
			int dateType, int parentCode, String measureCode,
			String economicCode, String isSecondData) {
		List<String> cons = new ArrayList<String>();
		final String[] mc = splitMeasureCode(measureCode);
		final String[] ec = splitMeasureCode(economicCode);
		cons.add(startTime);
		cons.add(endTime);
		for (String mCode : mc) {
			cons.add(mCode);
		}
		for (String eCode : ec) {
			cons.add(eCode);
		}
		List<Map> v1 = null;
		try {
			v1 = this
					.getPersistenceDAO("iqDataSource")
					.queryForList(
							"select sum(a.value) as s,substring(convert(char,transdt,120),0,"
									+ (dateType == 0 ? 10 : 7)
									+ ") as t, "
									+ (isSecondData == null ? "parent_code as c from"
											: "b.value as n from ")
									+ "(t_analysis_bg_economicproperty a left join t_dm_jjxzdm b on a.economicproperty=b.code) "
									+ "where transdt>=? and transdt<=? "
									+ (mc.length > 0 ? "and measure in "
											+ getInStru(mc) : "")
									+ (ec.length > 0 ? "and code in "
											+ getInStru(ec) : "")
									+ (parentCode == -1 ? ""
											: "  and parent_code="
													+ (parentCode == 1 ? "'1'"
															: (parentCode == 2 ? "'2'"
																	: "'3'")))
									+ " and parent_code<>'' group by t,"
									+ (isSecondData == null ? "c" : "n")
									+ " order by t", cons);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return v1;
	}

	
	
	/**
	 * 企业类型 - 只有一层
	 * 
	 * @param startTime
	 * @param endTime
	 * @param dateType
	 * @param measureCode
	 * @param enterpriseCode
	 *            企业类型代码
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> selectEnterpriseData(String startTime, String endTime,
			int dateType, String measureCode, String enterpriseCode) {
		List<String> cons = new ArrayList<String>();
		final String[] mc = splitMeasureCode(measureCode);
		final String[] ec = splitMeasureCode(enterpriseCode);
		cons.add(startTime);
		cons.add(endTime);
		for (String mCode : mc) {
			cons.add(mCode);
		}
		for (String eCode : ec) {
			cons.add(eCode);
		}
		List<Map> v1 = null;
		try {
			v1 = this
					.getPersistenceDAO("iqDataSource")
					.queryForList(
							"select sum(a.value) as s,substring(convert(char,transdt,120),0,"
									+ (dateType == 0 ? 10 : 7)
									+ ") as t, "
									+ ("b.value as n from ")
									+ "(t_analysis_enttype a left join t_dm_qylxdm b on a.ENTTYPE=b.code) "
									+ "where transdt>=? and transdt<=? "
									+ (mc.length > 0 ? "and measure in "
											+ getInStru(mc) : "")
									+ (ec.length > 0 ? "and code in "
											+ getInStru(ec) : "")
									+ " and code<>'' group by t,n"
									+ " order by t", cons);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return v1;
	}

	// 获取所有measure信息
	@SuppressWarnings("rawtypes")
	public List<Map> selectMeasureInfo() {
		List<Map> v1 = null;
		if (measureCache.cache != null && measureCache.isUseful()) {
			v1 = (List<Map>) measureCache.cache;
		} else {
			synchronized (measureCache) {
				try {
					v1 = this
							.getPersistenceDAO("iqDataSource")
							.queryForList(
									"select measurecde as mc,measure as mn, mark as mk from t_reg_dic_measure where levell = '2' "
											+ " and (mc <= '001008' or mc in ('002001', '002005', '002009'))",
									null);
					measureCache.setCache(v1);
				} catch (OptimusException e) {
					e.printStackTrace();
				}
			}
		}
		return v1;
	}

	/**
	 * 市场主体变更
	 * 区域-第一层
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param dateType
	 *            0-日 1-月
	 * @param mark
	 *            0-期末 1-本期
	 * @param measureCode
	 *            参考表 t_reg_dic_measure
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> selectMarketSubjectInfo(String startTime, String endTime,
			int dateType, String measureCode) {
		List<String> cons = new ArrayList<String>();
		final String[] mc = splitMeasureCode(measureCode);
		cons.add(startTime);
		cons.add(endTime);
		for (String mCode : mc) {
			cons.add(mCode);
		}
		List<Map> v1 = null;
		try {
			v1 = this
					.getPersistenceDAO("iqDataSource")
					.queryForList(
							"select sum(a.value) as s,substring(convert(char,transdt,120),0,"
									+ (dateType == 0 ? 10 : 7)
									+ ") as t, QY_VALUE as c"
									+ " from (t_analysis_bg_regorg a inner join t_cognos_xzqhdm b on  b.xz_code=(case when  a.regorg='440003'  then '440003' when a.regorg='440606' then '440606' else substring(a.regorg,1,4)||'00' end)) where transdt>=? and transdt<=? "
									+ (mc.length > 0 ? "and measure in "
											+ getInStru(mc) : "")
									+ " group by t,c order by t ", cons);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return v1;
	}

	/**
	 * 市场主体变更
	 * 全省各市-第二层 这个函数可以代替第一层
	 * 
	 * @param time
	 *            时间 yyyy-MM-dd || yyyy-MM
	 * @param measureCode
	 *            具体维度
	 * @param dateType
	 *            日期类型
	 * @param type
	 *            珠海 梅州 汕尾
	 * @Param indusCode 具体行业(code1)
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> selectProvinceByAreaByMarketSubject(String startTime, String endTime,
			String measureCode, int dateType, int type, String areaCode) {
		List<String> cons = new ArrayList<String>();
		final String[] mc = splitMeasureCode(measureCode);
		final String[] ac = splitMeasureCode(areaCode);
		cons.add(startTime);
		cons.add(endTime);
		for (String mCode : mc) {
			cons.add(mCode);
		}
		for (String aCode : ac) {
			cons.add(aCode);
		}
		List<Map> v1 = null;
		try {
			v1 = this
					.getPersistenceDAO("iqDataSource")
					.queryForList(
							"select sum(a.value) as s,substring(convert(char,transdt,120),0,"
									+ (dateType == 0 ? 10 : 7)
									+ ") as t, XZ_VALUE n from "
									+ "(t_analysis_bg_regorg a inner join t_cognos_xzqhdm b on  b.xz_code=(case when  a.regorg='440003'  then '440003' when a.regorg='440606' then '440606' else substring(a.regorg,1,4)||'00' end)) "
									+ "where transdt>=? and transdt<=? "
									+ (mc.length > 0 ? "and measure in "
											+ getInStru(mc) : "")
									+ (ac.length > 0 ? "and QY_CODE in "
											+ getInStru(ac) : "")
									+ " group by t,n order by t", cons);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return v1;
	}
	
	

	/**
	 * 市场主体变更
	 * 组织形式-第一层
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param dateType
	 *            0-日 1-月
	 * @param mark
	 *            0-期末 1-本期
	 * @param measureCode
	 *            参考表 t_dm_zzxsdm
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> selectEnterpriseMarketSubjectInfo(String startTime, String endTime,
			int dateType, String measureCode) {
		List<String> cons = new ArrayList<String>();
		final String[] mc = splitMeasureCode(measureCode);
		cons.add(startTime);
		cons.add(endTime);
		for (String mCode : mc) {
			cons.add(mCode);
		}
		List<Map> v1 = null;
		try {
			v1 = this
					.getPersistenceDAO("iqDataSource")
					.queryForList(
							"select sum(a.value) as s,substring(convert(char,transdt,120),0,"
									+ (dateType == 0 ? 10 : 7)
									+ ") as t, Parent_value as c"
									+ " from (T_ANALYSIS_BG_ORGANIZATIONMODE a inner join t_dm_zzxsdm b on a.ORGANIZATIONMODE=b.code) where transdt>=? and transdt<=? "
									+ (mc.length > 0 ? "and measure in "
											+ getInStru(mc) : "")
									+ " group by t,c order by t ", cons);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return v1;
	}

	/**
	 * 市场主体变更
	 * 组织形式-第二层 这个函数可以代替第一层
	 * @param measureCode2 
	 * 
	 * @param time
	 *            时间 yyyy-MM-dd || yyyy-MM
	 * @param dateType
	 *            日期类型
	 * @param type 
	 * @Param indusCode 具体行业(code1)
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> selectByEnterpriseByMarketSubject(String startTime, String endTime,
			String measureCode, int dateType, int type, String organizationScope) {
		List<String> cons = new ArrayList<String>();
		final String[] mc = splitMeasureCode(measureCode);
		final String[] oc = splitMeasureCode(organizationScope);
		cons.add(startTime);
		cons.add(endTime);
		for (String mCode : mc) {
			cons.add(mCode);
		}
		for (String aCode : oc) {
			cons.add(aCode);
		}
		List<Map> v1 = null;
		try {
			v1 = this
					.getPersistenceDAO("iqDataSource")
					.queryForList(
							"select sum(a.value) as s,substring(convert(char,transdt,120),0,"
									+ (dateType == 0 ? 10 : 7)
									+ ") as t, b.value n from "
									+ "(T_ANALYSIS_BG_ORGANIZATIONMODE a inner join t_dm_zzxsdm b on a.ORGANIZATIONMODE=b.code) "
									+ "where transdt>=? and transdt<=? "
									+ (mc.length > 0 ? "and measure in "
											+ getInStru(mc) : "")
									+ (oc.length > 0 ? "and parentcode in "
											+ getInStru(oc) : "")
									+ " group by t,n order by t", cons);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return v1;
	}
	
	
	
	// 获取所有economic信息
	@SuppressWarnings("rawtypes")
	public List<Map> selectEconomicCodeInfo() {
		List<Map> v1 = null;
		if (economicCache.cache != null && economicCache.isUseful()) {
			v1 = (List<Map>) economicCache.cache;
		} else {
			synchronized (economicCache) {
				try {
					v1 = this
							.getPersistenceDAO("iqDataSource")
							.queryForList(
									"select code c,parent_code pc,value v, parent_value pv from t_dm_jjxzdm",
									null);
					economicCache.setCache(v1);
				} catch (OptimusException e) {
					e.printStackTrace();
				}
			}
		}
		return v1;
	}

	// 获取所有enterprise(企业)信息
	@SuppressWarnings("rawtypes")
	public List<Map> selectEnterpriseCodeInfo() {
		List<Map> v1 = null;
		if (enterpriseCache.cache != null && enterpriseCache.isUseful()) {
			v1 = (List<Map>) enterpriseCache.cache;
		} else {
			synchronized (enterpriseCache) {
				try {
					v1 = this.getPersistenceDAO("iqDataSource").queryForList(
							"select code c,value v from t_dm_qylxdm", null);
					enterpriseCache.setCache(v1);
				} catch (OptimusException e) {
					e.printStackTrace();
				}
			}
		}
		return v1;
	}

	// 获取所有domscale(资金规模/类型)信息
	@SuppressWarnings("rawtypes")
	public List<Map> selectDomScaleCodeInfo() {
		List<Map> v1 = null;
		if (domScaleCache.cache != null && domScaleCache.isUseful()) {
			v1 = (List<Map>) domScaleCache.cache;
		} else {
			synchronized (domScaleCache) {
				try {
					v1 = this
							.getPersistenceDAO("iqDataSource")
							.queryForList(
									"select scalecode c,scale v from t_reg_dic_domscale order by c",
									null);
					domScaleCache.setCache(v1);
				} catch (OptimusException e) {
					e.printStackTrace();
				}
			}
		}
		return v1;
	}

	// 获取所有indus产业信息
	@SuppressWarnings("rawtypes")
	public Map<String, List<Map>> selectSencondIndusInfoInfo() {
		List<Map> v1 = null;
		Map<String, List<Map>> caches = null;
		if (indusCache.cache != null && indusCache.isUseful()) {
			caches = indusCache.cache;
		} else {
			synchronized (indusCache) {
				try {
					caches = new HashMap<String, List<Map>>(8);
					v1 = this
							.getPersistenceDAO("iqDataSource")
							.queryForList(
									"select code1 c1,value1 v1 from t_dm_hymldm where code2='A'",
									null);
					caches.put("0", v1);
					v1 = this
							.getPersistenceDAO("iqDataSource")
							.queryForList(
									"select code1 c1,value1 v1 from t_dm_hymldm where code2>'A' and code2<'F'",
									null);
					caches.put("1", v1);
					v1 = this
							.getPersistenceDAO("iqDataSource")
							.queryForList(
									"select code1 c1,value1 v1 from t_dm_hymldm where code2>'E'",
									null);
					caches.put("2", v1);
					indusCache.setCache(caches);
				} catch (OptimusException e) {
					e.printStackTrace();
				}
			}
		}
		return caches;
	}
	

	
	// 获取所有area区域信息
	@SuppressWarnings("rawtypes")
	public List<Map> selectSencondAreaInfo() {
		List<Map> v1 = null;
	    List<Map> caches = null;
		if (areaCache.cache != null && areaCache.isUseful()) {
			caches = areaCache.cache;
		} else {
			synchronized (areaCache) {
				try {
					v1 = this
							.getPersistenceDAO("iqDataSource")
							.queryForList(
									"select distinct qy_CODE as c1,qy_VALUE as v1 from t_cognos_xzqhdm ",
									null);
					areaCache.setCache(caches);
				} catch (OptimusException e) {
					e.printStackTrace();
				}
			}
		}
		return v1;
	}
	
	// 获取所有组织形式信息
	@SuppressWarnings("rawtypes")
	public List<Map> selectSencondEnterpriseInfo() {
		List<Map> v1 = null;
	    List<Map> caches = null;
		if (enterpriseOrgCache.cache != null && enterpriseOrgCache.isUseful()) {
			caches = enterpriseOrgCache.cache;
		} else {
			synchronized (enterpriseOrgCache) {
				try {
					v1 = this
							.getPersistenceDAO("iqDataSource")
							.queryForList(
									"select distinct parentcode as c1,Parent_value as v1 from t_dm_zzxsdm ",
									null);
					enterpriseOrgCache.setCache(caches);
				} catch (OptimusException e) {
					e.printStackTrace();
				}
			}
		}
		return v1;
	}
	
	private static String[] splitMeasureCode(String mc) {
		if (mc == null || "".equals(mc.trim()))
			return EMPTY;
		final String s[] = mc.split(",");
		return s;
	}

	private static String getInStru(String[] mc) {
		final StringBuilder s = new StringBuilder();
		if (mc.length > 0) {
			s.append('(').append('?');
		}
		for (int i = 1; i < mc.length; i++) {
			s.append(',').append('?');
		}
		if (mc.length > 0) {
			s.append(')');
		}
		return s.length() == 0 ? null : s.toString();
	}

	// 简单缓存对象
	private static class SimpleCache<T> {
		private long cacheTime;
		private long time; // 生效时间
		T cache;

		public SimpleCache(long time, T cache) {// 生效时间,
			this.time = time;
			this.cache = cache;
			this.cacheTime = System.currentTimeMillis();
		}

		public void setCache(T cache) {
			this.cacheTime = System.currentTimeMillis();
			this.cache = cache;
		}

		public boolean isUseful() {
			return System.currentTimeMillis() - cacheTime < time;
		}
	}

}
