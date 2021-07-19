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
public class IndustrycoAnalysisService extends BaseService {
	
	private static final String NULL_REPLACE = "[未注明类型]";

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
	// domscale_code 所有区域信息
	@SuppressWarnings("rawtypes")
	private SimpleCache<List<Map>> allAreaCache = new SimpleCache<List<Map>>(
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
			String measureCode, String isBG) {
		List<String> cons = new ArrayList<String>();
		final String[] mc = splitMeasureCode(measureCode);
		cons.add(startTime);
		cons.add(endTime);
		for (String mCode : mc) {
			cons.add(mCode);
		}
		
		List<Map> v1 = null;
		try {
			final boolean flag = (measureCode != null && dateType == 1 && (measureCode.startsWith("001001") || measureCode.startsWith("002001")));
			if(flag){
				cons.add(startTime);
				cons.add(endTime);
			}
			final String tableName = (isBG == null ? "t_analysis" : "t_analysis_bg") +"_industryco";
			v1 = this
					.getPersistenceDAO("iqDataSource")
					.queryForList(
							"select sum(a.value) as s,substring(convert(char,transdt,120),0,"
									+ (dateType == 0 ? 10 : 7)
									+ ") as t, isnull(code2,'" + NULL_REPLACE + "') as c"
									+ " from (" + tableName + " a left join t_dm_hymldm b on a.industryco=b.code1) where transdt>=? and transdt<=? "
									+ (mc.length > 0 ? "and measure in "
											+ getInStru(mc) : "")
									+ " group by transdt,c " +(flag ? " having transdt in (select max(transdt) from "+tableName+" where transdt>=? and transdt<=? group by substring(convert(char,transdt,120),0,7))" : "")+ " order by t " 
										, cons);
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
			String measureCode, int dateType, int type, String indusCode, String isBG) {
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
			boolean flag = (measureCode != null && dateType == 1 && (measureCode.startsWith("001001") || measureCode.startsWith("002001")));
			if(flag){
				cons.add(startTime);
				cons.add(endTime);
			}
			final String tableName = (isBG == null ? "t_analysis" : "t_analysis_bg") +"_industryco";
			v1 = this
					.getPersistenceDAO("iqDataSource")
					.queryForList(
							"select sum(a.value) as s,substring(convert(char,transdt,120),0,"
									+ (dateType == 0 ? 10 : 7)
									+ ") as t, isnull(value1,'" + NULL_REPLACE + "')  n from "
									+ "(" + tableName + " a left join t_dm_hymldm b on a.industryco=b.code1) "
									+ "where transdt>=? and transdt<=? "
									+ (mc.length > 0 ? "and measure in "
											+ getInStru(mc) : "")
									+ (type == -1 ? ""
											: "  and code2"
													+ (type == 0 ? "='A'"
															: (type == 1 ? " in ('B','C','D','E') "
																	: " not in ('A','B','C','D','E') ")))
									+ (ic.length > 0 ? "and code1 in "
											+ getInStru(ic) : "")
									+ " group by transdt,n " + (flag ? " having transdt in (select max(transdt) from "+tableName+" where transdt>=? and transdt<=? group by substring(convert(char,transdt,120),0,7))" : "") + " order by t"
									, cons);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return v1;
	}
	
	
	/**
	 * 行业产业和各级地市-第一层
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
	public List<Map> selectIndustryAndEnterprise(String startTime,
			String measureCode, String isBG) {
		List<String> cons = new ArrayList<String>();
		final String[] mc = splitMeasureCode(measureCode);
		cons.add(startTime);
		//cons.add(endTime);
		for (String mCode : mc) {
			cons.add(mCode);
		}
		List<Map> v1 = null;
		try {
			v1 = this
					.getPersistenceDAO("iqDataSource")
					.queryForList(
									"select sum(a.value) s,isnull(b.xz_value,'" + NULL_REPLACE + "') t, a.MEASURE m,"+
								    "isnull(c.code2,'" + NULL_REPLACE + "') c "+
								    "from "+(isBG == null ? "T_REG_ENTRY_EXIT" :  "t_reg_entry_alter")+" a,t_cognos_xzqhdm b,t_dm_hymldm c "+
								    "where b.xz_code=(case when  a.regorg='440003'  then '440003' when a.regorg='440606' then '440606' else substring(a.regorg,1,4)||'00' end) and a.INDUSTRYCO = c.code1 "+
								    "and a.transdt=?  " +
								    (mc.length > 0 ? "and a.measure in "
											+ getInStru(mc) : "")+
								    "group by c,m,t order by t", cons);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return v1;
	}


	/**
	 * 行业产业和各级地市-第二层 这个函数可以代替第一层
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
	public List<Map> selectIndustryAndEnterprise(String startTime, String endTime,
			String measureCode, int dateType, int type,String indusCode, String isBG) {
		List<String> cons = new ArrayList<String>();
		final String[] mc = splitMeasureCode(measureCode);
		final String[] ic = splitMeasureCode(indusCode);
		cons.add(startTime);
		//cons.add(endTime);
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
							"select sum(a.value) as s,isnull(b.xz_value,'" + NULL_REPLACE + "') t, a.MEASURE m, isnull(c.value1,'" + NULL_REPLACE + "') c from "
									+ ""+(isBG == null ? "T_REG_ENTRY_EXIT" :  "t_reg_entry_alter")+" a,t_cognos_xzqhdm b,t_dm_hymldm c  "
									+ "where b.xz_code=(case when  a.regorg='440003'  then '440003' when a.regorg='440606' then '440606' else substring(a.regorg,1,4)||'00' end) and a.INDUSTRYCO = c.code1  and a.transdt=? "
									+ (mc.length > 0 ? "and a.measure in "
											+ getInStru(mc) : "")
									+ (type == -1 ? ""
									: "  and c.code2"
											+ (type == 0 ? "='A'"
													: (type == 1 ? " in('B','C','D','E')  "
															: " not in('A','B','C','D','E') ")))
									+ (ic.length > 0 ? "and c.code1 in "
											+ getInStru(ic) : "")
									+ "  group by c,m,t  order by t", cons);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return v1;
	}


	/**
	 * 区域和二级经济性质 ---第一层
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
	public List<Map> selectEconomicAndAreaData(String startTime, String measureCode, String isBG) {
		List<String> cons = new ArrayList<String>();
		final String[] mc = splitMeasureCode(measureCode);
		cons.add(startTime);
	//	cons.add(endTime);
		for (String mCode : mc) {
			cons.add(mCode);
		}
		List<Map> v1 = null;
		try {
			v1 = this
					.getPersistenceDAO("iqDataSource")
					.queryForList(
							"select sum(a.value) s,isnull(b.qy_value,'" + NULL_REPLACE + "') t,isnull(c.value,'" + NULL_REPLACE + "') c from "+(isBG == null ? "T_REG_ENTRY_EXIT" :  "t_reg_entry_alter")+" a left join t_cognos_xzqhdm b on b.xz_code=(case when  a.regorg='440003'  then '440003' when a.regorg='440606' then '440606' else substring(a.regorg,1,4)||'00' end) left join t_dm_jjxzdm c on a.ECONOMICPROPERTY=c.code where " +
							" a.transdt=? "
									+ (mc.length > 0 ? "and a.measure in "
											+ getInStru(mc) : "")
									+ " group by t,c order by t", cons);
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
			String economicCode, String isSecondData, String isBG) {
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
			boolean flag = (measureCode != null && dateType == 1 && (measureCode.startsWith("001001") || measureCode.startsWith("002001")));
			if(flag){
				cons.add(startTime);
				cons.add(endTime);
			}
			final String tableName = (isBG == null ? "t_analysis" : "t_analysis_bg") +"_economicproperty";
			v1 = this
					.getPersistenceDAO("iqDataSource")
					.queryForList(
							"select sum(a.value) as s,substring(convert(char,transdt,120),0,"
									+ (dateType == 0 ? 10 : 7)
									+ ") as t, "
									+ (isSecondData == null ? "isnull(parent_code,'" + NULL_REPLACE + "') as c from"
											: "isnull(b.value,'" + NULL_REPLACE + "') as n from ")
									+ "("+tableName+" a left join t_dm_jjxzdm b on a.economicproperty=b.code) "
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
									+ " group by transdt,"
									+ (isSecondData == null ? "c" : "n") + (flag ? " having transdt in (select max(transdt) from "+tableName+" where transdt>=? and transdt<=? group by substring(convert(char,transdt,120),0,7))" : "")
									+ " order by t"  
									, cons);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return v1;
	}
	
	
	/**
	 * 资金规模-第一层 & 第二层
	 * @param startTime
	 * @param endTime
	 * @param dateType
	 * @param measureCode
	 * @param domCode 资金规模代码
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> selectDomScaleData(String startTime, String endTime,
			int dateType, String measureCode,String domCode, String isSecond, String isBG) {
		List<String> cons = new ArrayList<String>();
		final String[] mc = splitMeasureCode(measureCode);
		final String[] dc = splitMeasureCode(domCode);
		cons.add(startTime);
		cons.add(endTime);
		for (String mCode : mc) {
			cons.add(mCode);
		}
		for (String dCode : dc) {
			cons.add(dCode);
		}
		
		List<Map> v1 = null;
		try {
			boolean flag = (measureCode != null && dateType == 1 && (measureCode.startsWith("001001") || measureCode.startsWith("002001")));
			if(flag){
				cons.add(startTime);
				cons.add(endTime);
			}
			final String tableName = (isBG == null ? "t_analysis" : "t_analysis_bg") +"_domscale";
			v1 = this
					.getPersistenceDAO("iqDataSource")
					.queryForList(
							"select sum(a.value) as s,substring(convert(char,transdt,120),0,"
									+ (dateType == 0 ? 10 : 7)
									+ ") as t,  "+ (isSecond == null ? "isnull(domscale,'" + NULL_REPLACE + "') as c from " : "isnull(scale,'" + NULL_REPLACE + "') as n from")
									+ "("+tableName+" a left join t_reg_dic_domscale b on a.domscale=b.SCALECODE) "
									+ "where transdt>=? and transdt<=? "
									+ (mc.length > 0 ? "and measure in " + getInStru(mc) : "")
									+ (dc.length > 0 ? "and scalecode in "
											+ getInStru(dc) : "")
									 + " group by transdt,"+(isSecond == null ? "c" : "n")+ (flag ? " having transdt in (select max(transdt) from "+tableName+" where transdt>=? and transdt<=? group by substring(convert(char,transdt,120),0,7))" : "")+" order by t" 
									 , cons);
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
			String economicCode, String isSecondData, String isBG) {
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
			boolean flag = (measureCode != null && dateType == 1 && (measureCode.startsWith("001001") || measureCode.startsWith("002001")));
			if(flag){
				cons.add(startTime);
				cons.add(endTime);
			}
			final String tableName = (isBG == null ? "t_analysis" : "t_analysis_bg") +"_economicproperty";
			v1 = this
					.getPersistenceDAO("iqDataSource")
					.queryForList(
							"select sum(a.value) as s,substring(convert(char,transdt,120),0,"
									+ (dateType == 0 ? 10 : 7)
									+ ") as t, "
									+ (isSecondData == null ? "isnull(parent_code,'" + NULL_REPLACE + "') as c from"
											: "isnull(b.value,'" + NULL_REPLACE + "') as n from ")
									+ "("+tableName+" a left join t_dm_jjxzdm b on a.economicproperty=b.code) "
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
									+ " group by transdt,"
									+ (isSecondData == null ? "c" : "n") + (flag ? " having transdt in (select max(transdt) from "+tableName+" where transdt>=? and transdt<=? group by substring(convert(char,transdt,120),0,7))" : "")
									+ " order by t" 
									, cons);
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
			int dateType, String measureCode, String enterpriseCode, String isBG) {
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
			boolean flag = (measureCode != null && dateType == 1 && (measureCode.startsWith("001001") || measureCode.startsWith("002001")));
			if(flag){
				cons.add(startTime);
				cons.add(endTime);
			}
			final String tableName = (isBG == null ? "t_analysis" : "t_analysis_bg") +"_enttype";
			v1 = this
					.getPersistenceDAO("iqDataSource")
					.queryForList(
							"select " + (flag ? "max" : "sum") + "(a.value) as s,substring(convert(char,transdt,120),0,"
									+ (dateType == 0 ? 10 : 7)
									+ ") as t, "
									+ ("isnull(b.value,'" + NULL_REPLACE + "') as n from ")
									+ "(" + tableName + " a left join t_dm_qylxdm b on a.ENTTYPE=b.code) "
									+ "where transdt>=? and transdt<=? "
									+ (mc.length > 0 ? "and measure in "
											+ getInStru(mc) : "")
									+ (ec.length > 0 ? "and code in "
											+ getInStru(ec) : "")
									+ "  group by transdt,n " + (flag ? " having transdt in (select max(transdt) from "+tableName+" where transdt>=? and transdt<=? group by substring(convert(char,transdt,120),0,7))" : "")
									+ " order by "+(flag ? "transdt" : "t")+"", cons);
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
									"select measurecde as mc,measure as mn, mark as mk from t_reg_dic_measure where  1=1 "
									//measureStr	//+ " and mc <= '001008' ", //or mc in ('002001', '002005', '002009'))
									,null);
					measureCache.setCache(v1);
				} catch (OptimusException e) {
					e.printStackTrace();
				}
			}
		}
		return v1;
	}
	
	
	// 获取distinct 的所有measure信息
		@SuppressWarnings("rawtypes")
		public String selectMeasureTableInfo(String tableName) {
			List<Map> v1 = null;
			StringBuffer measure=new StringBuffer();
			String sql="select distinct measure from "+tableName;
			try {
				v1 = this
						.getPersistenceDAO("iqDataSource")
						.queryForList(
								sql
								,null);
			} catch (OptimusException e) {
				e.printStackTrace();
			}
		if(v1!=null && v1.size()>0){
					measure.append(" and mc in (");
			for(int i=0;i<v1.size();i++){
				String measureStr=(String) v1.get(i).get("measure");
				if(i==0){
					measure.append("'"+measureStr+"'");
				}else{
					measure.append(",'"+measureStr+"'");
				}
			}
			measure.append(")");
		}
		System.out.println("===="+measure.toString());
			return measure.toString();
		}
	/**
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
	public List<List> selectAreaInfo(String startTime, String endTime,
			int dateType, String measureCode, String isBG) {
		List<List> l1=new ArrayList<List>();
		List<String> cons = new ArrayList<String>();
		final String[] mc = splitMeasureCode(measureCode);
		cons.add(startTime);
		cons.add(endTime);
		for (String mCode : mc) {
			cons.add(mCode);
		}
		
		List<Map> v1 = null;
		try {
			boolean flag = (measureCode != null && dateType == 1 && (measureCode.startsWith("001001") || measureCode.startsWith("002001")));
			if(flag){
				cons.add(startTime);
				cons.add(endTime);
			}
			final String tableName = (isBG == null ? "t_analysis" : "t_analysis_bg") +"_regorg";
			v1 = this
					.getPersistenceDAO("iqDataSource")
					.queryForList(
							"select sum(a.value) as s,substring(convert(char,transdt,120),0,"
									+ (dateType == 0 ? 10 : 7)
									+ ") as t, isnull(QY_VALUE,'" + NULL_REPLACE + "') as c"
									+ " from ("+tableName+" a left join t_cognos_xzqhdm b on b.xz_code=(case when  a.regorg='440003'  then '440003' when a.regorg='440606' then '440606' else substring(a.regorg,1,4)||'00' end) ) where transdt>=? and transdt<=? "
									+ (mc.length > 0 ? "and measure in "
											+ getInStru(mc) : "")
									+ " group by transdt,c " +(flag ? " having transdt in (select max(transdt) from "+tableName+" where transdt>=? and transdt<=? group by substring(convert(char,transdt,120),0,7))" : "")+ " order by t ", cons);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		List<Map> v2 = null;
		/*if(mc.length>0 && mc!=null ){
			if("001001".equals(mc[0]) || "001002".equals(mc[0]) || "001003".equals(mc[0])){
				try {
					cons.remove(cons.size()-1);
					v2 = this
							.getPersistenceDAO("iqDataSource")
							.queryForList(
									"select " + (measureCode != null && dateType == 1 && (measureCode.startsWith("001001") || measureCode.startsWith("002001")) 
							             ? "max" : "sum") + "(a.value) as s,substring(convert(char,transdt,120),0,"
											+ (dateType == 0 ? 10 : 7)
											+ ") as t, QY_VALUE as c"
											+ " from ("+(isBG ==  null ? "t_analysis_" : "t_analysis_bg")+"_regorg a inner join t_cognos_xzqhdm b on b.xz_code=(case when  a.regorg='440003'  then '440003' when a.regorg='440606' then '440606' else substring(a.regorg,1,4)||'00' end) ) where transdt>=? and transdt<=? "
											+ " and measure = '002"+mc[0].substring(3)+"'"
											+ " group by t,c order by t ",cons);
				} catch (OptimusException e) {
					e.printStackTrace();
				}
			}
		}*/
	
	
		
		l1.add(v1);
		l1.add(v2);
		return l1;
	}

	/**
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
	public List<Map> selectProvinceByArea(String startTime, String endTime,
			String measureCode, int dateType, int type, String areaCode, String isBG) {
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
			boolean flag = (measureCode != null && dateType == 1 && (measureCode.startsWith("001001") || measureCode.startsWith("002001")));
			if(flag){
				cons.add(startTime);
				cons.add(endTime);
			}
			final String tableName = (isBG == null ? "t_analysis" : "t_analysis_bg") +"_regorg";
			v1 = this
					.getPersistenceDAO("iqDataSource")
					.queryForList(
							"select sum(a.value) as s,substring(convert(char,transdt,120),0,"
									+ (dateType == 0 ? 10 : 7)
									+ ") as t, isnull(XZ_VALUE,'" + NULL_REPLACE + "') n from "
									+ "("+tableName+" a left join t_cognos_xzqhdm b on b.xz_code=(case when  a.regorg='440003'  then '440003' when a.regorg='440606' then '440606' else substring(a.regorg,1,4)||'00' end) ) "
									+ "where transdt>=? and transdt<=? "
									+ (mc.length > 0 ? "and measure in "
											+ getInStru(mc) : "")
									+ (ac.length > 0 ? "and QY_CODE in "
											+ getInStru(ac) : "")
									+ " group by transdt,n " + (flag ? " having transdt in (select max(transdt) from "+tableName+" where transdt>=? and transdt<=? group by substring(convert(char,transdt,120),0,7))" : "") + " order by t", cons);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return v1;
	}
	/**
	 * 按二级产业和企业类型分析
	 * @param measureCode
	 * @param enterpriseCode
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> selectDataByMCodeAndECode(String startTime, String endTime, String measureCode, String enterpriseCode, String isBG) {
		List<String> cons = new ArrayList<String>();
		cons.add(startTime);
//		cons.add(endTime);
//		cons.add(measureCode);
		final String[] ec = splitMeasureCode(enterpriseCode);
		final String[] mc = splitMeasureCode(measureCode);
		for(String mCode : mc) {
			cons.add(mCode);
		}
		for (String eCode : ec) {
			cons.add(eCode);
		}
		List<Map> v1 = null;
		try {
			v1 = this
					.getPersistenceDAO("iqDataSource")
					.queryForList("select " + (measureCode != null && (measureCode.startsWith("001001") || measureCode.startsWith("002001")) 
							             ? "max" : "sum") + "(a.value) s,isnull(c.value1,'" + NULL_REPLACE + "') i,isnull(b.value,'" + NULL_REPLACE + "') c from " +  
								    ""+(isBG == null ? "T_REG_ENTRY_EXIT" :  "t_reg_entry_alter")+" a left join t_dm_qylxdm b on a.enttype=b.code left join t_dm_hymldm  c on a.industryco = c.CODE1 " +
								     "where transdt=?   "+(mc.length > 0 ? " and a.measure in " + getInStru(mc) : "")+(ec.length > 0 ? " and code in " + getInStru(ec) : "") +
								    " group by i,c", cons);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return v1;
	}
	
	
	/**
	 * 全省各市及经济性质分析
	 * @param measureCode
	 * @param enterpriseCode 全省各市
	 * @param economicCode  经济性质
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> selectAllProvinceAndEconomic(String startTime, String measureCode,
			String enterpriseCode, String economicCode, String isBG) {
		List<String> cons = new ArrayList<String>();
		cons.add(startTime);
		//cons.add(endTime);
		//cons.add(measureCode);
		final String [] mc = splitMeasureCode(measureCode);
		for(String mCode : mc){
			cons.add(mCode);
		}
		if(!(economicCode == null || "".equals(economicCode.trim()))){
			cons.add(economicCode);
		}
		final String[] ec = splitMeasureCode(enterpriseCode);
		for (String eCode : ec) {
			cons.add(eCode);
		}
		List<Map> v1 = null;
		try {
			v1 = this
					.getPersistenceDAO("iqDataSource")
					.queryForList("select sum(a.value) s,isnull(c.xz_value,'" + NULL_REPLACE + "') i,isnull(b.value,'" + NULL_REPLACE + "') c from "+
    ""+(isBG == null ? "T_REG_ENTRY_EXIT" :  "t_reg_entry_alter")+" a left join t_dm_jjxzdm b on a.economicproperty=b.code left join t_cognos_xzqhdm  c on c.xz_code=(case when  a.regorg='440003'  then '440003' when a.regorg='440606' then '440606' else substring(a.regorg,1,4)||'00' end) " + 
   " where   transdt=?  "+ (mc.length > 0 ? " and a.measure in " + getInStru(mc) : "") +(economicCode == null || "".equals(economicCode.trim()) ? "" : " and b.code=? ") + 
    (ec.length > 0 ? "and regorg in " + getInStru(ec) : "") +
   " group by i,c", cons);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return v1;
	}
	/**
	 * 全省各市及注册资金规模分析
	 * @param measureCode
	 * @param enterpriseCode 全省各市
	 * @param economicCode  资金规模
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> selectAllProvinceAndDomScale(String startTime, String measureCode,
			String enterpriseCode, String economicCode, String isBG) {
		List<String> cons = new ArrayList<String>();
		cons.add(startTime);
		//cons.add(endTime);
		//cons.add(measureCode);
		final String [] mc = splitMeasureCode(measureCode);
		for(String mCode : mc){
			cons.add(mCode);
		}
		final String[] ec = splitMeasureCode(enterpriseCode);
		for (String eCode : ec) {
			cons.add(eCode);
		}
		final String [] cc = splitMeasureCode(economicCode);
		for (String eCode : cc) {
			cons.add(eCode);
		}
		List<Map> v1 = null;
		try {
			v1 = this
					.getPersistenceDAO("iqDataSource")
					.queryForList("select sum(a.value) s,isnull(c.xz_value,'" + NULL_REPLACE + "') i,isnull(b.scale,'" + NULL_REPLACE + "') c from "+
    ""+(isBG == null ? "T_REG_ENTRY_EXIT" :  "t_reg_entry_alter")+" a left join t_reg_dic_domscale b on a.domscale=b.scalecode left join t_cognos_xzqhdm  c on c.xz_code=(case when  a.regorg='440003'  then '440003' when a.regorg='440606' then '440606' else substring(a.regorg,1,4)||'00' end) " +
    " where  transdt=?  " + (mc.length > 0 ? " and a.measure in " + getInStru(mc) : "")+(ec.length > 0 ? "and regorg in " + getInStru(ec) : "") +(cc.length > 0 ? "and scalecode in " + getInStru(cc) : "") +
    " group by i,c", cons);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return v1;
	}
	
	
	/**
	 * 注册类型及注册资金规模分析
	 * @param measureCode
	 * @param enterpriseCode 注册类型
	 * @param economicCode  资金规模
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> selectRegtypeAndDomScale(String startTime, String measureCode,
			String enterpriseCode, String economicCode, String isBG) {
		List<String> cons = new ArrayList<String>();
		cons.add(startTime);
		//cons.add(measureCode);
		final String [] mc = splitMeasureCode(measureCode);
		for(String mCode : mc){
			cons.add(mCode);
		}

		final String[] ec = splitMeasureCode(enterpriseCode);
		for (String eCode : ec) {
			cons.add(eCode);
		}
		final String [] cc = splitMeasureCode(economicCode);
		for (String eCode : cc) {
			cons.add(eCode);
		}
		List<Map> v1 = null;
		try {
			v1 = this
					.getPersistenceDAO("iqDataSource")
					.queryForList("select sum(a.value) s,isnull(b.scale,'" + NULL_REPLACE + "') c,(case  when  substring(b.scalecode,0,3)='001' then '内资'  when  substring(b.scalecode,0,3)='002' then '私营' else '农合' end) as i from "+
    ""+(isBG == null ? "T_REG_ENTRY_EXIT" :  "t_reg_entry_alter")+" a left join  t_reg_dic_domscale b on a.domscale=b.scalecode" +
    " where   transdt=? "+  (mc.length > 0 ? " and a.measure in " + getInStru(mc) : "")+(ec.length > 0 ? "and regorg in " + getInStru(ec) : "") +(cc.length > 0 ? "and scalecode in " + getInStru(cc) : "") +
    " group by i,c", cons);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return v1;
	}
	
	
	/**
	 * 按经济性质和产业分析
	 * @param startTime
	 * @param endTime
	 * @param measureCode
	 * @param economicCode 经济性质代码
	 * @param industryCode 产业代码
	 * @param parentIndustryCode 第一层经济性质 123-第一第二第三产业
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> selectEconomicAndIndustycoPageInfo(String startTime,
			 String measureCode, String economicCode,
			String industryCode, String parentIndustryCode, String isSecond, String isBG) {
		List<String> cons = new ArrayList<String>();
		cons.add(startTime);
//		cons.add(measureCode);
		final String [] mc = splitMeasureCode(measureCode);
		for(String mCode : mc) {
			cons.add(mCode);
		}
		final String[] ec = splitMeasureCode(economicCode);
		for (String eCode : ec) {
			cons.add(eCode);
		}
		final String[] ic = splitMeasureCode(industryCode);
		for (String iCode : ic) {
			cons.add(iCode);
		}
		List<Map> v1 = null;
		try {
			//(ec.length > 0 ? "and regorg in " + getInStru(ec) : "")
			v1 = this.getPersistenceDAO("iqDataSource")
					.queryForList("select sum(a.value) s,"+(isSecond == null ? "(case  when  b.code2='A' then '第一产业'  when  b.code2 in ('B','C','D','E') then '第二产业' else '第三产业' end) as c" : "b.value1 as c")+",isnull(c.value,'" + NULL_REPLACE + "') i from " +
					    ""+(isBG == null ? "T_REG_ENTRY_EXIT" :  "t_reg_entry_alter")+" a left join t_dm_hymldm b on a.industryco=b.code1 left join t_dm_jjxzdm c on a.economicproperty=c.code " +
					    "where  transdt=?  " + (mc.length > 0 ? " and a.measure in " + getInStru(mc) : "") + (ec.length > 0 ? "and c.code in " + getInStru(ec) : "") + 
					    (ic.length > 0  ? "and b.code1 in " + getInStru(ic) : "") + 
					    (isSecond == null ? "" : "  and b.code2 "
													+ (parentIndustryCode.equals("0") ? "='A'"
															: (parentIndustryCode.equals("1") ? "in('B','C','D','E') "
																	: "not in('A','B','C','D','E')"))) +
					    " group by i,c", cons);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return v1;
	}
	
	/**
	 * 注册资金规模和产业分析
	 * @param startTime
	 * @param endTime
	 * @param measureCode
	 * @param economicCode
	 * @param industryCode
	 * @param parentIndustryCode
	 * @param isSecond
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> selectDomScaleAndIndustycoPageInfo(String startTime,
			String measureCode, String economicCode,
			String industryCode, String parentIndustryCode, String isSecond, String isBG) {
		List<String> cons = new ArrayList<String>();
		cons.add(startTime);
//		cons.add(measureCode);
		final String[] mc = splitMeasureCode(measureCode);
		for (String mCode : mc) {
			cons.add(mCode);
		}
		final String[] ec = splitMeasureCode(economicCode);
		for (String eCode : ec) {
			cons.add(eCode);
		}
		final String[] ic = splitMeasureCode(industryCode);
		for (String iCode : ic) {
			cons.add(iCode);
		}
		List<Map> v1 = null;
		try {
			//(ec.length > 0 ? "and regorg in " + getInStru(ec) : "")
			v1 = this.getPersistenceDAO("iqDataSource")
					.queryForList("select sum(a.value) s,"+(isSecond == null ? "(case  when  b.code2='A' then '第一产业'  when  b.code2 in ('B','C','D','E') then '第二产业' else '第三产业' end) as c" : "isnull(b.value1,'" + NULL_REPLACE + "') as c")+",isnull(c.scale,'" + NULL_REPLACE + "') i from " +
					    ""+(isBG == null ? "T_REG_ENTRY_EXIT" :  "t_reg_entry_alter")+" a left join t_dm_hymldm b on a.industryco=b.code1 left join  t_reg_dic_domscale c on a.domscale=c.scalecode " +
					    "where  transdt=?  " +(mc.length > 0 ? " and a.measure in " + getInStru(mc) : "") 
					    + (ec.length > 0 ? "and c.scalecode in " + getInStru(ec) : "") + 
					    (ic.length > 0  ? "and b.code1 in " + getInStru(ic) : "") + 
					    (isSecond == null ? "" : "  and b.code2 "
													+ (parentIndustryCode.equals("0") ? "='A'"
															: (parentIndustryCode.equals("1") ? "in('B','C','D','E') "
																	: "not in('A','B','C','D','E')"))) +
					    " group by i,c", cons);
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
									"select code1 c1,value1 v1 from t_dm_hymldm where code2 in ('B','C','D','E')",
									null);
					caches.put("1", v1);
					v1 = this
							.getPersistenceDAO("iqDataSource")
							.queryForList(
									"select code1 c1,value1 v1 from t_dm_hymldm where code2 not in ('A','B','C','D','E')",
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
	/**
	 * 年报数据获取
	 * @param startTime  开始时间
	 * @param endTime    结束时间 
 	 * @param dateType   日期类型
	 * @param measureCode   度量维度  002-年报率  003-补报率
	 * @param sourceCode   登记机关
	 * @param indusCode    行业类型 -1 -2 -3 代表父类第一第二第三产业
	 * @param enterCode    企业类型  1，2，3，4
	 * @param ancheyear    年报年份
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> selectYannualReportPageInfo(String startTime, String endTime,
			int dateType, String measureCode, String sourceCode,
			String indusCode, String enterCode, String ancheyear, String sourceParent) {
		List<String> cons = new ArrayList<String>();
		final String[] mc = splitMeasureCode(measureCode);
		final String[] sc = splitMeasureCode(sourceCode);
		final String[] ic = splitMeasureCode(indusCode);
		cons.add(startTime);
		//cons.add(endTime);
		cons.add(ancheyear);
		if(!"002".equals(measureCode)) { //为年报率不需要添加
			for (String mCode : mc) { cons.add(mCode); }
		}
		for (String sCode : sc) { cons.add(sCode); }
		if(!("-1".equals(indusCode) || "-2".equals(indusCode) || "-3".equals(indusCode))){
			for (String iCode : ic) { cons.add(iCode); }
		}
		List<Map> v1 = null;
		try {
			//(case  when  substring(b.scalecode,0,3)='001' then '内资'  when  substring(b.scalecode,0,3)='002' then '私营' else '农合' end)
			v1 = this.getPersistenceDAO("iqDataSource").
//			queryForList("select " + (measureCode != null && (measureCode.startsWith("001001") || measureCode.startsWith("002001")) 
			//				             ? "max" : "sum") + "(a.VALUE) s, "+(sourceParent == null ? "b.xz_value" : "b.qy_value")+" i,d.value c from T_REG_ANNREPORT_REALANALYSIS a " + 
			queryForList("select "+(sourceParent == null ? "isnull(b.xz_value,'" + NULL_REPLACE + "')" : "isnull(b.qy_value,'" + NULL_REPLACE + "')")+" i,"+
					"(sum(case a.measure when '001001' then a.value end )) s0, " +
							"(sum(case a.measure when '001002' then a.value end )) s1," +
							"(sum(case a.measure when '001003' then a.value end )) s2," + 
							"round(s1/s0,4)*100 s3,round(s2/s0,4)*100 s4 " +
					"  from T_REG_ANNREPORT_REALANALYSIS a " + 
					 "left join T_COGNOS_XZQHDM b on a.SOURCEFLAG=b.XZ_CODE " + 
					 "left join t_dm_hymldm c on a.INDUSTRYCO=c.code1 " + 
					 "left join t_dm_qylxdm d on a.ENTTYPE=d.code " + 
					 "left join T_REG_ANNREPORT_MEASURE e on a.MEASURE=e.code where " + 
					 "a.transdt=? and a.ANCHEYEAR=? " + 
					 ("002".equals(measureCode) ?"" : (mc.length > 0 ? " and a.MEASURE in " + getInStru(mc) : "")) +
					 (sourceParent == null || "006".compareTo(sourceCode) < 0  ?
							 (sc.length > 0 ? " and b.xz_code in " + getInStru(sc) : "") :
							 (sc.length > 0 ? " and b.qy_code in " + getInStru(sc) : "")) +
					 ("-1".equals(indusCode) ? " and c.code2 = 'A' " :
						("-2".equals(indusCode) ? " and c.code2 in ('B','C','D','E') " : 
							("-3".equals(indusCode) ? " and c.code2 not in ('A','B','C','D','E') " : 
								(ic.length > 0 ? " and a.INDUSTRYCO in " + getInStru(ic) : ""))))  +
					 (enterCode == null || "".equals(enterCode.trim()) ? "" : ("1".equals(enterCode) ? " and a.enttype = '9999' " :
					  ("2".equals(enterCode) ? " and (a.enttype = '9100' or a.enttype = '9200') " : 
						("3".equals(enterCode) ? " and substring(a.ENTTYPE,0,1) = '7' " : 
							"and (a.enttype != '9100' and a.enttype != '9200' and a.enttype != '9999' and substring(a.ENTTYPE,0,1)!='7')")))) +
						" group by i", cons);
		/*v1 = this.getPersistenceDAO("iqDataSource").
				queryForList("select sum(a.VALUE) s, "+(sourceParent == null ? "b.xz_value" : "b.qy_value")+" i,d.value c from T_REG_ANNREPORT_REALANALYSIS a " + 
							 "inner join T_COGNOS_XZQHDM b on a.SOURCEFLAG=b.XZ_CODE " + 
							 "inner join t_dm_hymldm c on a.INDUSTRYCO=c.code1  " + 
							 "inner join t_dm_qylxdm d on a.ENTTYPE=d.code " + 
							 "inner join T_REG_ANNREPORT_MEASURE e on a.MEASURE=e.code where " + 
							 "a.transdt>=?  and a.TRANSDT<=? and a.ANCHEYEAR=?" + 
							 (mc.length > 0 ? " and a.MEASURE in " + getInStru(mc) : "") +
							 (sourceParent == null  ?
									 (sc.length > 0 ? " and b.xz_code in " + getInStru(sc) : "") :
									 (sc.length > 0 ? " and b.qy_code in " + getInStru(sc) : "")) +
							 ("-1".equals(indusCode) ? " and c.code2 = 'A' " :
								("-2".equals(indusCode) ? " and c.code2 in ('B','C','D','E') " : 
									("-3".equals(indusCode) ? " and c.code2 not in ('A','B','C','D','E') " : 
										(ic.length > 0 ? " and a.INDUSTRYCO in " + getInStru(ic) : ""))))  +
							 ("1".equals(enterCode) ? " and a.enttype = '9999' " :
							  ("2".equals(enterCode) ? " and (a.enttype = '9100' or a.enttype = '9200') " : 
								("3".equals(enterCode) ? " and substring(a.ENTTYPE,0,1) = '7' " : 
									"and (a.enttype != '9100' and a.enttype != '9200' and a.enttype != '9999' and substring(a.ENTTYPE,0,1)!='7')"))) +
								" group by i,c", cons);*/
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return v1;
	}

	// 获取所有area区域信息
	@SuppressWarnings("rawtypes")
	public List<Map> selectAllAreaInfo() {
		List<Map> v1 = null;
	    List<Map> caches = null;
		if (allAreaCache.cache != null && allAreaCache.isUseful()) {
			caches = allAreaCache.cache;
		} else {
			synchronized (allAreaCache) {
				try {
					v1 = this
							.getPersistenceDAO("iqDataSource")
							.queryForList(
									"select xz_code xc,xz_value xv, qy_value qv,qy_code qc from t_cognos_xzqhdm ",
									null);
					allAreaCache.setCache(caches);
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
