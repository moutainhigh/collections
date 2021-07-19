package cn.gwssi.analysis.service;

import java.util.*;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gwssi.blog.controller.BlogController;
import cn.gwssi.resource.Conts;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service
public class AnalysisService extends BaseService {

	private static Logger log = Logger.getLogger(BlogController.class);
	
	@Autowired
	private IndustrycoAnalysisService service;
	
	public List<Map> findByEntEstbReg(Map params) throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO();
		StringBuffer sql = new StringBuffer(
				"select distinct TRANSDT,TESTDATA1,TESTDATA2,TESTDATA3,CONVERT(VARCHAR(18),VALUESUM) VALUESUM　from T_Test_MEASURE　")
				.append(" where 1=1 ");

		return dao.queryForList(sql.toString(), null);
	}

	// 初始化业务类型---区域
	public List<Map> iniBusinessAnalystsArea(Map params) throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO("iqDataSource");
		 StringBuffer sqlxz=new StringBuffer("select  XZ_CODE as code,XZ_VALUE as value from t_cognos_xzqhdm  where 1=1 ");
		//StringBuffer sqlxz = new StringBuffer(
		//		"select distinct qy_CODE as code,qy_VALUE as value from t_cognos_xzqhdm  where 1=1 ");
		// StringBuffer sqlyw=new
		// StringBuffer("select code,value from T_DM_YWLXDM");
		
		 if(params!=null && params.size()>0){
		 if(params.get("drillType")!=null){ 
		 sqlxz.append("and qy_code = '");
		 sqlxz.append(params.get("drillType"));
		 sqlxz.append("'"); } }
		 
		List<Map> sqlxzMap = dao.queryForList(sqlxz.toString(), null);
		return sqlxzMap;
		// qy_code ='002'
		// List<Map> sqlywMap=dao.queryForList(sqlyw.toString(), null);
		// return sqlywMap;
	}

	// 初始化业务类型---区域类型
	public List<Map> iniBusinessAnalystsType(Map params)
			throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO("iqDataSource");
		StringBuffer sql = new StringBuffer(
				"select  CODE,VALUE from t_cognos_ywlx  where  1=1 ");
		/*if(params!=null && params.size()>0){
			sql.append("");
			sql.append(b);
			sql.append("");
		}*/
		return dao.queryForList(sql.toString(), null);
	}

	// 初始化业务类型---业务类型
	public List<List> iniBusinessAnalystsBusiness(String drillType) throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO("iqDataSource");
		List<List> list = new ArrayList<List>();
		String sqlxz = "select  XZ_CODE as code ,XZ_VALUE as value from t_cognos_xzqhdm ";
		String sqlqy = "select distinct QY_VALUE as code ,QY_CODE as value from t_cognos_xzqhdm ";
		String ywlx=" select code,value from t_cognos_ywlx";
		String ywlxdm=" select code,value,parentcode from T_DM_YWLXdm ";
		
		list.add(dao.queryForList(sqlxz, null));
		list.add(dao.queryForList(sqlqy, null));
		//一级业务类型
		list.add(dao.queryForList(ywlx, null));
		if(drillType!=null){
			ywlxdm = ywlxdm+"where parentcode = '"+drillType+"'";
			//二级业务类型
			list.add(dao.queryForList(ywlxdm, null));
		}
		
		return list;
	}

	// 初始化组织形式---组织形式类型
	public List<List> iniZzxsAndIndustrAnalystsType(String param)
			throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO("iqDataSource");
		List<List> list = new ArrayList<List>();
		list.add(service.selectMeasureInfo());
		if (param == null) {
			String sqlStr = "select distinct (case when code2='A' then 'A' when code2>'A' and code2<'F' then 'B,C,D,E' else 'F' end) c,(case when code2='A' then '第一产业' when code2>'A' and code2<'F' then '第二产业' else '第三产业' end) v from t_dm_hymldm ";
			list.add(dao.queryForList(sqlStr.toString(), null));
		} else {
			String sqlStr = "select code1 c,value1 v from t_dm_hymldm where code2 in ('"
					+ param + "')";
			list.add(dao.queryForList(sqlStr.toString(), null));
		}
		return list;
	}

	// 初始化组织形式---指标类型
	public List<Map> iniZzxsAnalystsType(String param) throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO("iqDataSource");
		List<Map> list = new ArrayList<Map>();
		if (param == null) {
			String sqlStr = "select distinct parentcode as code,Parent_value as value  from t_dm_zzxsdm";
			list=dao.queryForList(sqlStr.toString(), null);
		} else {
			String sqlStr = "select code, value  from t_dm_zzxsdm where parentcode='"
					+ param + "'";
			list=dao.queryForList(sqlStr.toString(), null);
		}
		return list;
	}

	// 初始化组织形式---企业设立登记--全省各市
	public List iniBusinessAnalystsQsgsfx() throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO("iqDataSource");
		List<List> list = new ArrayList<List>();
		StringBuffer sql = new StringBuffer(
				"select  XZ_CODE as code ,XZ_VALUE as value from t_cognos_xzqhdm");
		list.add(dao.queryForList(sql.toString(), null));
		String sqlStr = "select distinct parentcode as code,Parent_value as value  from t_dm_zzxsdm";
		list.add(dao.queryForList(sqlStr.toString(), null));
		list.add(service.selectMeasureInfo());
		return list;
	}

	// 按照业务类型 ，区域 ，时间，指标
	public List<Map> businessAnalystsType(Map params) throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO("iqDataSource");
		StringBuffer sql = new StringBuffer();
		// 区域 月 or日
		if ("selArea2".equals(params.get("regType"))) {
			sql.append("select sum(a.VALUE) as measureValue,");
			if("2".equals(params.get("level"))){
				sql.append("b.code as ywCode,b.value as ywValue,");
			}else{
				sql.append("b.parentcode as ywCode,c.value as ywValue,");
			}
			sql.append("d.qy_value as qyvalue,d.qy_code as qycode "
					+ "from  t_task_measure a,T_DM_YWLXDM b,t_cognos_ywlx c,t_cognos_xzqhdm d "
					+ "where trim(a.ACCEPTTYPE) = trim(b.code) AND trim(b.parentcode) = trim(C.code) AND trim(a.regorg) = trim(d.xz_code) ");
		} else {
			sql.append("select sum(a.VALUE) as measureValue,");
			if("2".equals(params.get("level"))){
				sql.append("b.code as ywCode,b.value as ywValue,");
			}else{
				sql.append("b.parentcode as ywCode,c.value as ywValue,");
			}
			sql.append("d.xz_value as qyvalue,d.xz_code as qycode "
					+ "from  t_task_measure a,T_DM_YWLXDM b,t_cognos_ywlx c,t_cognos_xzqhdm d "
					+ "where trim(a.ACCEPTTYPE) = trim(b.code) AND trim(b.parentcode) = trim(C.code) AND trim(a.regorg) = trim(d.xz_code)   ");

		}
		// 多选框
		if (params.get("selArea") != null) {
			String[] selArea = (String[]) ((String) params.get("selArea"))
					.split(",");
			sql.append(" and qycode in ('");
			for (int k = 0; k < selArea.length; k++) {
				if (k != selArea.length - 1) {
					sql.append(selArea[k] + "','");
				} else {
					sql.append(selArea[k] + "') ");
				}
			}
		}
		// 时间
		if (params != null && params.size() > 0) {
			if (params.get("startdt") != null ) {
				sql.append(" and a.transdt ='");
				sql.append(params.get("startdt"));
				sql.append("' ");
			}
			// 业务类型
			if (params.get("accepttype") != null) {
				String[] accepttype = (String[]) ((String) params.get("accepttype"))
						.split(",");
				if("2".equals(params.get("level"))){
					sql.append(" and b.code in ('");
				}else{
					sql.append(" and b.parentcode in ('");
				}
				for (int k = 0; k < accepttype.length; k++) {
					if (k != accepttype.length - 1) {
						sql.append(accepttype[k] + "','");
					} else {
						sql.append(accepttype[k] + "') ");
					}
				}
			}
		}
		// 指标
		if (params.get("businessCount") != null) {
			sql.append(" and a.MEASURE ='");
			sql.append(params.get("businessCount"));
			sql.append("'");
		}
			sql.append(" group by TRANSDT,qycode,qyvalue,ywValue,ywCode  order by qyvalue ");
		return dao.queryForList(sql.toString(), null);
	}

	// --按照某个业务类型的业务量总数 x轴是业务类型 y轴是业务数总量 搜索条件是时间 ， 业务类型 ， 指标，区域或者地市
	public List<Map> drillDownBusinessAnalystsType(Map params)
			throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO("iqDataSource");
		StringBuffer sql = new StringBuffer();
		// 区域 月 or日
		if ("area".equals(params.get("regType"))) {
			/*
			 * if("1".equals(params.get("selType"))){
			 * sql.append("select  a.TRANSDT,"); }else{ sql.append(
			 * "select  substring(convert(varchar(20),a.TRANSDT),0,7) TRANSDT,"
			 * ); }
			 */
			sql.append("select max(a.VALUE) as measureValue,b.value as ywValue,d.qy_value as qyvalue,d.qy_code as qycode from "
					+ "t_task_measure a,T_DM_YWLXDM b,t_cognos_xzqhdm d where a.ACCEPTTYPE = b.code  and a.regorg = d.xz_code  ");
		} else {
			/*
			 * if("1".equals(params.get("selType"))){
			 * sql.append("select  max(a.TRANSDT),"); }else{ sql.append(
			 * "select  substring(convert(varchar(20),a.TRANSDT),0,7) TRANSDT,"
			 * ); }
			 */
			sql.append("select max(a.VALUE) as measureValue,b.value as ywValue,d.xz_value as qyvalue,d.xz_code as qycode from "
					+ "t_task_measure a,T_DM_YWLXDM b,t_cognos_xzqhdm d where a.ACCEPTTYPE = b.code  and a.regorg = d.xz_code  ");
		}
		// 时间
		if (params != null && params.size() > 0) {
			if (params.get("startdt") != null && params.get("enddt") != null) {
				sql.append(" and a.transdt >'");
				sql.append(params.get("startdt"));
				sql.append("' and a.transdt <'");
				sql.append(params.get("enddt"));
				sql.append("' ");
			}
			// 业务类型
			if (params.get("parentcode") != null) {
				sql.append(" and b.parentcode ='");
				sql.append(params.get("parentcode"));
				sql.append("' ");
			}
		}
		// 指标
		if (params.get("businessCount") != null) {
			sql.append(" and a.MEASURE ='");
			sql.append(params.get("businessCount"));
			sql.append("'");
		}
		if ("area".equals(params.get("regType"))) {
			sql.append(" group by d.qy_code,d.qy_value,b.value  order by qyvalue ");
		} else {
			sql.append(" group by d.xz_code,d.xz_value,b.value  order by qyvalue ");
		}
		return dao.queryForList(sql.toString(), null);
	}

	// 按照某个时间段的业务总量显示 x轴 是地区 y轴是度量值
	// 修改第一版 ---按照某个时间段的区域的业务总量显示 x轴 是时间 y轴是度量值 图例是区域
	// select sum(a.value), a.regorg regorg from t_task_measure a left join
	// t_cognos_xzqhdm b on b.xz_code = a.regorg where a.transdt between
	// '2010-03-12' and '2016-07-25' group by a.regorg
	public List<Map> businessAnalystsSum(Map params) throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO("iqDataSource");
		StringBuffer sql = new StringBuffer();
		if ("0".equals(params.get("selType"))) {
			sql.append("select sum(a.VALUE) as measureValue,");
			if(params.get("drillType") != null){
				sql.append(" d.xz_value as qyvalue,d.xz_code as qycode,");
			}else{
				sql.append(" d.qy_value as qyvalue,d.qy_code as qycode,");
			}
			sql.append("convert(varchar(20),a.transdt)  transdt from  t_task_measure a,t_cognos_xzqhdm d where trim(a.regorg) = trim(d.xz_code)   ");
		} else {
			sql.append("select max(a.VALUE) as measureValue,");
			if(params.get("drillType") != null){
				sql.append(" d.xz_value as qyvalue,d.xz_code as qycode,");
			}else{
				sql.append(" d.qy_value as qyvalue,d.qy_code as qycode,");
			}
			sql.append("substring(convert(varchar(20),a.transdt),0,7) transdt from  t_task_measure a,t_cognos_xzqhdm d where trim(a.regorg) = trim(d.xz_code)   ");
		}
		// 本期 previousDate 期末terminal
		if (params != null && params.size() > 0) {
			if (params.get("startdt") != null || params.get("enddt") != null) {
				sql.append(" and a.transdt >= '");
				sql.append(params.get("startdt"));
				sql.append("' and a.transdt <= '");
				sql.append(params.get("enddt"));
				sql.append("' ");
			}
		}
		if (params.get("businessCount") != null) {
			sql.append(" and a.measure = '");
			sql.append(params.get("businessCount"));
			sql.append("'");
		}
		if (params.get("drillType") != null) {
			sql.append(" and d.qy_code = '");
			sql.append(params.get("drillType"));
			sql.append("'");
		}
		sql.append(" group by TRANSDT,qycode,qyvalue  order by qyvalue");
		return dao.queryForList(sql.toString(), null);
	}

	// --按照某个地区的业务量总数 x轴是一周 y轴是业务数总量 搜索条件是时间 ， 业务类型 ， 指标
	public List<Map> drillDownBusinessAnalystsSum(Map params)
			throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO("iqDataSource");
		StringBuffer sql = new StringBuffer();
		if ("0".equals(params.get("selType"))) {
			sql.append("select max(t.value) value,t.transdt,t.regorg,(select xz_value from t_cognos_xzqhdm where xz_code= t.regorg) regorgzxcode,"
					+ "(select qy_code from t_cognos_xzqhdm where xz_code= t.regorg) regorgcode,"
					+ "(select qy_VALUE from t_cognos_xzqhdm where xz_code= t.regorg) regorgvalue from "
					+ "(select a.value,convert(varchar(20),a.transdt) transdt,a.regorg  from t_task_measure a  where 1=1 ");
		} else {
			sql.append("select max(t.value) value,t.transdt,t.regorg,(select xz_value from t_cognos_xzqhdm where xz_code= t.regorg) regorgzxcode,"
					+ "(select qy_code from t_cognos_xzqhdm where xz_code= t.regorg) regorgcode,"
					+ "(select qy_VALUE from t_cognos_xzqhdm where xz_code= t.regorg) regorgvalue from "
					+ "(select a.value,substring(convert(varchar(20),a.transdt),0,7) transdt,a.regorg  from t_task_measure a  where 1=1 ");
		}
		if (params != null && params.size() > 0) {
			if (params.get("startdt") != null && params.get("enddt") != null) {
				sql.append(" and a.transdt > '");
				sql.append(params.get("startdt"));
				sql.append("' and  a.transdt < '");
				sql.append(params.get("enddt"));
				sql.append("' ");
			}
		}

		if (!"allCity".equals(params.get("regorgcode"))) {
			sql.append(" and a.regorg in ( select xz_code from t_cognos_xzqhdm where qy_code='");
			sql.append(params.get("regorgcode"));
			sql.append("')");
		}

		if (params.get("businessCount") != null) {
			sql.append(" and a.MEASURE ='");
			sql.append(params.get("businessCount"));
			sql.append("'");
		}
		sql.append(") t group by  t.regorg,t.transdt  order by t.transdt ");
		return dao.queryForList(sql.toString(), null);
	}

	// 按照某个时间段的企业设立登记总量显示 x轴 是时间段 y轴是度量值 图例 是 按一级组织形式
	public List<Map> zzxsAnalystsSum(Map params) throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO("iqDataSource");
		StringBuffer sql = new StringBuffer();
		if ("0".equals(params.get("selType"))) {
			sql.append("select max(a.VALUE) as measureValue,convert(varchar(20),a.transdt) transdt,a.organizationmode,b.MEASURECDE,b.MEASURE,c.parentcode,c.Parent_value "
					+ "from  T_ANALYSIS_ORGANIZATIONMODE a,t_reg_dic_measure b,t_dm_zzxsdm c  "
					+ "where a.ORGANIZATIONMODE = c.parentcode and b.MEASURECDE = a.MEASURE ");
		} else {
			// select a.value,substring(convert(varchar(20),a.transdt),0,7)
			// transdt
			sql.append("select max(a.VALUE) as measureValue,substring(convert(varchar(20),a.transdt),0,7) transdt,a.organizationmode,b.MEASURECDE,b.MEASURE,c.parentcode,c.Parent_value "
					+ "from  T_ANALYSIS_ORGANIZATIONMODE a,t_reg_dic_measure b,t_dm_zzxsdm c  "
					+ "where a.ORGANIZATIONMODE = c.parentcode and b.MEASURECDE = a.MEASURE ");
		}
		// 本期 previousDate 期末terminal
		if (params != null && params.size() > 0) {
			if (params.get("startdt") != null && params.get("enddt") != null) {
				sql.append(" and a.transdt >= '");
				sql.append(params.get("startdt"));
				sql.append("' and a.transdt <= '");
				sql.append(params.get("enddt"));
				sql.append("' ");
			}
		}
		if (params.get("organizationmode") != null) {
			// 多选框
			if (params.get("organizationmode") != null) {
				String[] organizationmode = (String[]) ((String) params
						.get("organizationmode")).split(",");
				sql.append(" and a.organizationmode in ('");
				for (int k = 0; k < organizationmode.length; k++) {
					if (k != organizationmode.length - 1) {
						sql.append(organizationmode[k] + "','");
					} else {
						sql.append(organizationmode[k] + "') ");
					}
				}
			}
		}
		if (params.get("businessCount") != null) {
			String[] businessCount = (String[]) ((String) params
					.get("businessCount")).split(",");
			sql.append(" and a.measure in  ('");
			for (int k = 0; k < businessCount.length; k++) {
				if (k != businessCount.length - 1) {
					sql.append(businessCount[k] + "','");
				} else {
					sql.append(businessCount[k] + "') ");
				}
			}
		}
		sql.append(" group by transdt,a.organizationmode,b.MEASURECDE,b.MEASURE,c.parentcode,c.Parent_value order by transdt ");
		return dao.queryForList(sql.toString(), null);
	}

	// --按照某个地区的业务量总数 x轴是一周 y轴是业务数总量 搜索条件是时间 ， 业务类型 ， 指标
	public List<Map> drillDownzzxsAnalystsSum(Map params)
			throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO("iqDataSource");
		StringBuffer sql = new StringBuffer();
		if ("0".equals(params.get("selType"))) {
			sql.append("select max(a.VALUE) as measureValue,convert(varchar(20),a.transdt) transdt,a.organizationmode,b.MEASURECDE,b.MEASURE,c.code,c.value "
					+ "from  T_ANALYSIS_ORGANIZATIONMODE a,t_reg_dic_measure b,t_dm_zzxsdm c  "
					+ "where a.ORGANIZATIONMODE = c.parentcode and b.MEASURECDE = a.MEASURE ");
		} else {
			sql.append("select max(a.VALUE) as measureValue,substring(convert(varchar(20),a.transdt),0,7) transdt,a.organizationmode,b.MEASURECDE,b.MEASURE,c.code,c.value "
					+ "from  T_ANALYSIS_ORGANIZATIONMODE a,t_reg_dic_measure b,t_dm_zzxsdm c  "
					+ "where a.ORGANIZATIONMODE = c.parentcode and b.MEASURECDE = a.MEASURE ");
		}
		// 本期 previousDate 期末terminal
		if (params != null && params.size() > 0) {
			if (params.get("startdt") != null && params.get("enddt") != null) {
				sql.append(" and a.transdt >= '");
				sql.append(params.get("startdt"));
				sql.append("' and a.transdt <= '");
				sql.append(params.get("enddt"));
				sql.append("' ");
			}
		}
		if (params.get("organizationmode") != null) {
			// 多选框
			if (params.get("organizationmode") != null) {
				String[] organizationmode = (String[]) ((String) params
						.get("organizationmode")).split(",");
				sql.append(" and c.code in ('");
				for (int k = 0; k < organizationmode.length; k++) {
					if (k != organizationmode.length - 1) {
						sql.append(organizationmode[k] + "','");
					} else {
						sql.append(organizationmode[k] + "') ");
					}
				}
			}
		}
		if (params.get("businessCount") != null) {
			String[] businessCount = (String[]) ((String) params
					.get("businessCount")).split(",");
			sql.append(" and a.measure in  ('");
			for (int k = 0; k < businessCount.length; k++) {
				if (k != businessCount.length - 1) {
					sql.append(businessCount[k] + "','");
				} else {
					sql.append(businessCount[k] + "') ");
				}
			}
		}
		sql.append(" group by transdt,a.organizationmode,b.MEASURECDE,b.MEASURE,c.code,c.value order by transdt ");
		return dao.queryForList(sql.toString(), null);
	}

	// 按照城市，组织形式 设立登记——按组织形式和产业分析 ----
	public List<Map> zzxsAndIndustrycoAnalystsType(Map params)
			throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO("iqDataSource");
		StringBuffer sql = new StringBuffer();
		sql.append("select (case when b.code2='A' then 'A' when b.code2>'A' and b.code2<'F' then 'B,C,D,E' else 'F' end) d,sum(a.value) s,c.parent_value t,(case when b.code2='A' then '第一产业' when code2>'A' and code2<'F' then '第二产业' else '第三产业' end) c "
				+ "from T_REG_ENTRY_EXIT a,t_dm_hymldm b,t_dm_zzxsdm c  "
				+ "where a.ORGANIZATIONMODE=c.code and a.INDUSTRYCO=b.code1   ");

		// 多选框
		/*
		 * if (params.get("selArea") != null) { String[] selArea = (String[])
		 * ((String) params.get("selArea")) .split(",");
		 * sql.append(" and b.xz_code in ('"); for (int k = 0; k <
		 * selArea.length; k++) { if (k != selArea.length - 1) {
		 * sql.append(selArea[k] + "','"); } else { sql.append(selArea[k] +
		 * "') "); } } }
		 */
		// 时间
		if (params.get("startdt") != null && params.get("enddt") != null) {
			sql.append(" and a.transdt >='");
			sql.append(params.get("startdt"));
			sql.append("' and a.transdt <='");
			sql.append(params.get("enddt"));
			sql.append("' ");
		}

		// 指标
		if (params.get("businessCount") != null) {
			String[] businessCount = (String[]) ((String) params
					.get("businessCount")).split(",");
			sql.append(" and a.measure in  ('");
			for (int k = 0; k < businessCount.length; k++) {
				if (k != businessCount.length - 1) {
					sql.append(businessCount[k] + "','");
				} else {
					sql.append(businessCount[k] + "') ");
				}
			}
		}
		sql.append(" group by t,c,d");
		return dao.queryForList(sql.toString(), null);
	}

	// 按照城市，组织形式 设立登记——按全省各市和组织形式分析 ----
	public List<Map> businessAnalystsCityType(Map params)
			throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO("iqDataSource");
		StringBuffer sql = new StringBuffer();
		sql.append("select  sum(a.value) s,b.XZ_VALUE t,c.parent_value c ,c.parentcode d from T_REG_ENTRY_EXIT a,t_cognos_xzqhdm b,t_dm_zzxsdm c  "
				+ "where a.ORGANIZATIONMODE=c.code and  b.xz_code=(case when  a.regorg='440003'  then '440003' when a.regorg='440606' then '440606' else substring(a.regorg,1,4)||'00' end) ");

		// 多选框
		if (params.get("selArea") != null) {
			String[] selArea = (String[]) ((String) params.get("selArea"))
					.split(",");
			sql.append(" and b.xz_code in ('");
			for (int k = 0; k < selArea.length; k++) {
				if (k != selArea.length - 1) {
					sql.append(selArea[k] + "','");
				} else {
					sql.append(selArea[k] + "') ");
				}
			}
		}
		
		// 组织形式字段
				if (params.get("organizationmode") != null) {

					String[] organizationmode = (String[]) ((String) params
							.get("organizationmode")).split(",");
					sql.append(" and a.organizationmode in ('");
					for (int k = 0; k < organizationmode.length; k++) {
						if (k != organizationmode.length - 1) {
							sql.append(organizationmode[k] + "','");
						} else {
							sql.append(organizationmode[k] + "') ");
						}
					}

				}
				
				
		// 时间
		if (params.get("startdt") != null ) {
			sql.append(" and a.transdt ='");
			sql.append(params.get("startdt"));
			sql.append("' ");
		}

		// 指标
		if (params.get("businessCount") != null) {
			String[] businessCount = (String[]) ((String) params
					.get("businessCount")).split(",");
			sql.append(" and a.measure in  ('");
			for (int k = 0; k < businessCount.length; k++) {
				if (k != businessCount.length - 1) {
					sql.append(businessCount[k] + "','");
				} else {
					sql.append(businessCount[k] + "') ");
				}
			}
		}
		sql.append(" group by t,c,d");
		return dao.queryForList(sql.toString(), null);
	}

	// --按照各个地市的业务量总数 x轴是各个组织形式 y轴是产业 搜索条件是时间 ， 度数维度 ，选择一级产业范围
	public List<Map> drillZzxsAndIndeustrAnalystsSum(Map params)
			throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO("iqDataSource");
		StringBuffer sql = new StringBuffer();
		sql.append("select  sum(a.value) s,c.parent_value t,b.VALUE1 c "+
			"from T_REG_ENTRY_EXIT a,t_dm_hymldm b,t_dm_zzxsdm c  "+
			"where a.ORGANIZATIONMODE=c.code and a.INDUSTRYCO=b.code1 ");

		// 多选框
		if (params.get("industrType") != null) {
			String[] industrType = (String[]) ((String) params.get("industrType"))
					.split(",");
			sql.append(" and b.code1 in ('");
			for (int k = 0; k < industrType.length; k++) {
				if (k != industrType.length - 1) {
					sql.append(industrType[k] + "','");
				} else {
					sql.append(industrType[k] + "') ");
				}
			}
		}
		// 时间
		if (params.get("startdt") != null && params.get("enddt") != null) {
			sql.append(" and a.transdt >='");
			sql.append(params.get("startdt"));
			sql.append("' and a.transdt <='");
			sql.append(params.get("enddt"));
			sql.append("' ");
		}


		// 指标
		if (params.get("businessCount") != null) {
			String[] businessCount = (String[]) ((String) params
					.get("businessCount")).split(",");
			sql.append(" and a.measure in  ('");
			for (int k = 0; k < businessCount.length; k++) {
				if (k != businessCount.length - 1) {
					sql.append(businessCount[k] + "','");
				} else {
					sql.append(businessCount[k] + "') ");
				}
			}
		}
		sql.append(" group by t,c");
		return dao.queryForList(sql.toString(), null);
	}

	// --按照各个地市的业务量总数 x轴是各个地市 y轴是业务数总量 搜索条件是时间 ， 度数维度 ，选择范围
	public List<Map> drillqyslZzxsAnalystsSum(Map params)
			throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO("iqDataSource");
		StringBuffer sql = new StringBuffer();
		sql.append("select  sum(a.value) s,b.XZ_VALUE t,c.value c ,c.code d "
				+ "from T_REG_ENTRY_EXIT a left join t_cognos_xzqhdm b on b.xz_code=(case when  a.regorg='440003'  then '440003' when a.regorg='440606' then '440606' else substring(a.regorg,1,4)||'00' end) left join t_dm_zzxsdm c on a.ORGANIZATIONMODE=c.code "
				+ "where 1=1 ");

		// 多选框
		if (params.get("selArea") != null) {
			String[] selArea = (String[]) ((String) params.get("selArea"))
					.split(",");
			sql.append(" and b.xz_code in ('");
			for (int k = 0; k < selArea.length; k++) {
				if (k != selArea.length - 1) {
					sql.append(selArea[k] + "','");
				} else {
					sql.append(selArea[k] + "') ");
				}
			}
		}
		// 时间
		if (params.get("startdt") != null && params.get("enddt") != null) {
			sql.append(" and a.transdt >='");
			sql.append(params.get("startdt"));
			sql.append("' and a.transdt <='");
			sql.append(params.get("enddt"));
			sql.append("' ");
		}

		// 组织形式字段
		if (params.get("organizationmode") != null) {

			String[] organizationmode = (String[]) ((String) params
					.get("organizationmode")).split(",");
			sql.append(" and a.organizationmode in ('");
			for (int k = 0; k < organizationmode.length; k++) {
				if (k != organizationmode.length - 1) {
					sql.append(organizationmode[k] + "','");
				} else {
					sql.append(organizationmode[k] + "') ");
				}
			}
		}

		// 指标
		if (params.get("businessCount") != null) {
			String[] businessCount = (String[]) ((String) params
					.get("businessCount")).split(",");
			sql.append(" and a.measure in  ('");
			for (int k = 0; k < businessCount.length; k++) {
				if (k != businessCount.length - 1) {
					sql.append(businessCount[k] + "','");
				} else {
					sql.append(businessCount[k] + "') ");
				}
			}
		}
		sql.append(" group by t,c,d");
		return dao.queryForList(sql.toString(), null);
	}

	// --按照某个地区的业务量总数 x轴是一周 y轴是业务数总量 搜索条件是时间 ， 业务类型 ， 指标
	public List<Map> drillzzxsAnalystsSum(Map params) throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO("iqDataSource");
		StringBuffer sql = new StringBuffer();
		sql.append("select max(a.VALUE) as measureValue,substring(convert(varchar(20),a.transdt),0,7) transdt,a.organizationmode,b.MEASURECDE,b.MEASURE,c.code,c.value "
				+ "from  T_ANALYSIS_ORGANIZATIONMODE a,t_reg_dic_measure b,t_dm_zzxsdm c  "
				+ "where a.ORGANIZATIONMODE = c.parentcode and b.MEASURECDE = a.MEASURE ");
		// 本期 previousDate 期末terminal
		if (params != null && params.size() > 0) {
			if (params.get("startdt") != null && params.get("enddt") != null) {
				sql.append(" and a.transdt > '");
				sql.append(params.get("startdt"));
				sql.append("' and a.transdt < '");
				sql.append(params.get("enddt"));
				sql.append("' ");
			}
		}
		if (params.get("organizationmode") != null) {
			sql.append(" and a.organizationmode = '");
			sql.append(params.get("organizationmode"));
			sql.append("'");
		}
		if (params.get("businessCount") != null) {
			sql.append(" and a.measure = '");
			sql.append(params.get("businessCount"));
			sql.append("'");
		}
		sql.append(" group by transdt,a.organizationmode,b.MEASURECDE,b.MEASURE,c.code,c.value order by transdt ");
		return dao.queryForList(sql.toString(), null);
	}

	/*// 地市转区域
	public List<Map> returnMap(String sql) throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO("iqDataSource");
		List<Map> list = dao.queryForList(sql.toString(), null);
		List<Map> data = new ArrayList();
		float value1 = 0;
		float value2 = 0;
		float value3 = 0;
		float value4 = 0;
		float value5 = 0;
		for (int i = 0; i < list.size(); i++) {
			Map areaMap = list.get(i);
			String areaName = (String) areaMap.get("regorg");
			if (i > 0
					&& areaMap.get("transdt").equals(
							list.get(i - 1).get("transdt"))) {
				if ("广东省".equals(areaName)) {
					value1 = value1
							+ Float.parseFloat((String) areaMap.get("value"));
				} else {
					if ("珠三角".equals(areaName)) {
						value2 = value2
								+ Float.parseFloat((String) areaMap
										.get("value"));
					} else {
						if ("粤东".equals(areaName)) {
							value3 = value3
									+ Float.parseFloat((String) areaMap
											.get("value"));
						} else {
							if ("粤西".equals(areaName)) {
								value4 = value4
										+ Float.parseFloat((String) areaMap
												.get("value"));
							} else {
								if ("粤北".equals(areaName)) {
									value5 = value5
											+ Float.parseFloat((String) areaMap
													.get("value"));
								}

							}

						}

					}

				}
			} else {
				if (i > 0) {
					Map area1 = new HashMap();
					area1.put("regorg", "广东省");
					area1.put("value", value1);
					area1.put("code", "001");
					area1.put("transdt", list.get(i - 1).get("transdt"));
					data.add(area1);
					Map area2 = new HashMap();
					area2.put("regorg", "珠三角");
					area2.put("value", value2);
					area2.put("code", "002");
					area2.put("transdt", list.get(i - 1).get("transdt"));
					data.add(area2);
					Map area3 = new HashMap();
					area3.put("regorg", "粤东");
					area3.put("value", value3);
					area3.put("code", "003");
					area3.put("transdt", list.get(i - 1).get("transdt"));
					data.add(area3);
					Map area4 = new HashMap();
					area4.put("regorg", "粤西");
					area4.put("value", value1);
					area4.put("code", "004");
					area4.put("transdt", list.get(i - 1).get("transdt"));
					data.add(area4);
					Map area5 = new HashMap();
					area5.put("regorg", "粤北");
					area5.put("value", value1);
					area5.put("code", "005");
					area5.put("transdt", list.get(i - 1).get("transdt"));
					data.add(area5);
				}
				if ("广东省".equals(areaName)) {
					value1 = value1
							+ Float.parseFloat((String) areaMap.get("value"));
				} else {
					if ("珠三角".equals(areaName)) {
						value2 = value2
								+ Float.parseFloat((String) areaMap
										.get("value"));
					} else {
						if ("粤东".equals(areaName)) {
							value3 = value3
									+ Float.parseFloat((String) areaMap
											.get("value"));
						} else {
							if ("粤西".equals(areaName)) {
								value4 = value4
										+ Float.parseFloat((String) areaMap
												.get("value"));
							} else {
								if ("粤北".equals(areaName)) {
									value5 = value5
											+ Float.parseFloat((String) areaMap
													.get("value"));
								}

							}

						}

					}

				}
			}

			if (i == (list.size() - 1)) {

				Map area1 = new HashMap();
				area1.put("regorg", "广东省");
				area1.put("value", value1);
				area1.put("code", "001");
				area1.put("transdt", list.get(i - 1).get("transdt"));
				data.add(area1);
				Map area2 = new HashMap();
				area2.put("regorg", "珠三角");
				area2.put("value", value2);
				area2.put("code", "002");
				area2.put("transdt", list.get(i - 1).get("transdt"));
				data.add(area2);
				Map area3 = new HashMap();
				area3.put("regorg", "粤东");
				area3.put("value", value3);
				area3.put("code", "003");
				area3.put("transdt", list.get(i - 1).get("transdt"));
				data.add(area3);
				Map area4 = new HashMap();
				area4.put("regorg", "粤西");
				area4.put("value", value4);
				area4.put("code", "004");
				area4.put("transdt", list.get(i - 1).get("transdt"));
				data.add(area4);
				Map area5 = new HashMap();
				area5.put("regorg", "粤北");
				area5.put("value", value5);
				area5.put("code", "005");
				area5.put("transdt", list.get(i - 1).get("transdt"));
				data.add(area5);
			}
		}
		// [{regorg=广东省, value=3437.0, transdt=2016-06, code=001}, {regorg=珠三角,
		// value=177960.0, transdt=2016-06, code=002}, {regorg=粤东,
		// value=46836.0, transdt=2016-06, code=003}, {regorg=粤西, value=31901.0,
		// transdt=2016-06, code=004}, {regorg=粤北, value=19363.0,
		// transdt=2016-06, code=005}]
		return data;
	}
*/
	/**
	 * 冒泡排序 sortMap === List<Map>
	 * */
	public List sort(List sortMap) {
		List args = new ArrayList();
		List yearList = new ArrayList();
		for (int m = 0; m < sortMap.size(); m++) {
			Map map = (Map) sortMap.get(m);
			args.add(map.get("ancheyear"));
		}

		int time1 = 0, time2 = 0;
		for (int i = 0; i < args.size(); i++) {
			++time1;
			for (int j = i + 1; j < args.size(); j++) {
				++time2;
				int temp;
				Integer argsi = Integer.parseInt((String) args.get(i));
				Integer argsj = Integer.parseInt((String) args.get(j));
				if (argsi < argsj) {
					args.set(i, argsj.toString());
					args.set(j, argsi.toString());
				}
			}
		}

		for (int n = 0; n < args.size(); n++) {
			Map yearMap = new HashMap();
			yearMap.put("ancheyear", args.get(n));
			yearList.add(yearMap);
		}
		return yearList;
	}

	 /**
	 * 全景分析
	 * @param params
	 * CREATE TABLE dba.T_PANORAMIC_ANALYSIS
		(
		     id   numeric(12, 0) identity,
		    pkidname   varchar(50)  NULL,--所在表的主键名
		    pkidvalue  varchar(36)  NULL,--所在表的主键值
		    pripid     varchar(36)  NULL,--
		    sourceflag varchar(6)   NULL,--来源平台
		    type       varchar(6)   NULL,--类型
		    tablename  varchar(100)   NULL,--来源表
		    title      varchar(100) NULL,--标题
		    name       varchar(100) NULL,--名称
		    describe   varchar(1000)NULL,--描述
		    operationtime       varchar(20)  NULL,--时间
		    timestamp  varchar(20)  NULL,--增量时间
		    CONSTRAINT PK_T_PANORAMIC_ANALYSIS
		    PRIMARY KEY CLUSTERED  (id)
		)
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findByEntPanoramicAnalysis(Map params) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO("iqDataSource");
		//StringBuffer sql1=new StringBuffer("select distinct id,pkidname,pkidvalue,pripid,sourceflag,type,tablename,title,name,describe,operationtime,timestamp,operationtime localtime from T_PANORAMIC_ANALYSIS where tablename='T_SCZT_SCZTJBXX'");
		//StringBuffer sql=new StringBuffer("select id,pkidname,pkidvalue,pripid,sourceflag,type,tablename,title,name,describe,operationtime,timestamp,operationtime localtime from T_PANORAMIC_ANALYSIS where tablename<>'T_SCZT_ZDXXX' and tablename<>'T_SCZT_SCZTJBXX' and tablename<>'T_SB_SBJBXX' and tablename<>'T_SCJG_PMXX'");//and operationtime is not null and operationtime <>''
		StringBuffer sql=new StringBuffer("select id,pkidname,pkidvalue,pripid,sourceflag,type,tablename,title,name,describe,operationtime,timestamp,operationtime localtime from T_PANORAMIC_ANALYSIS where tablename<>'T_SCZT_ZDXXX' and tablename<>'T_SCZT_LS_QRXX' and tablename<>'T_SCZT_LS_QCXX' and tablename<>'T_SB_SBJBXX' and tablename<>'T_SCJG_PMXX'");//and operationtime is not null and operationtime <>''
		List list = new ArrayList();
		if(params!=null&&params.size()>0){
			String pripid = (String) params.get("priPid");
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and pripid = ? ");
				list.add(pripid);
			}
		}else{
			return null;
		}
		StringBuffer sql1=new StringBuffer("select id,pkidname,pkidvalue,pripid,sourceflag,type,tablename,title,name,describe,operationtime,timestamp,operationtime localtime from T_PANORAMIC_ANALYSIS where (tablename='T_SCZT_LS_QRXX' or tablename='T_SCZT_LS_QCXX')");
		if(params!=null&&params.size()>0){
			String pripid = (String) params.get("priPid");
			String sourceflag=(String)params.get("sourceflag");
			if(StringUtils.isNotBlank(sourceflag)){
				sql1.append(" and sourceflag = ?");
				list.add(sourceflag);
			}
			if(StringUtils.isNotBlank(pripid)){
				sql1.append(" and pripid = ? ");
				list.add(pripid);
			}
		}else{
			return null;
		}
		sql.append("  union all ").append(sql1);
		//商標
		StringBuffer sql2=new StringBuffer("");
		if(params!=null&&params.size()>0){
			String regno = (String) params.get("regno");
			String entname = (String) params.get("entname");
			System.out.println(entname);
			sql2.append("select distinct id,pkidname,pkidvalue,pripid,sourceflag,type,tablename,title,name,describe,operationtime,timestamp,operationtime localtime from T_PANORAMIC_ANALYSIS where (tablename='T_SB_SBJBXX' and name ='"+entname+"' ) or (tablename='T_SCJG_PMXX' and pripid='"+regno+"')");
		}else{
			return null;
		}
		if(StringUtils.isNotBlank(sql2.toString())){
			sql.append(" union all ").append(sql2);	
		}
		System.out.println(params.get("priPid")+"sql语句："+sql);
		return dao.queryForList(sql.toString(), list);
	}
	
	public List<Map> findByEntPanoramicAnalysis(Map params,String type) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO("iqDataSource");
		StringBuffer sql=new StringBuffer("select id,pkidname,pkidvalue,pripid,sourceflag,type,tablename,title,name,describe,operationtime,timestamp,operationtime localtime from ");
		List list = new ArrayList();
		switch (type) {
		case "1"://其他处罚、老赖
			sql=new StringBuffer("select distinct pkidname,pkidvalue,pripid,sourceflag,type,tablename,title,name,describe,operationtime,timestamp,operationtime localtime from ");
			sql.append(" T_PANORAMIC_ANALYSIS_QTCF where");
			sql.append(" type in('256','259')");
			if(params!=null&&params.size()>0){
				String pripid = (String) params.get("priPid");
				if(StringUtils.isNotBlank(pripid)){
					sql.append(" and pripid = ? ");
					list.add(pripid);
				}
			}else{
				return null;
			}
			break;
		case "2"://广告(广告)
			sql.append(" T_PANORAMIC_ANALYSIS_GG where");
			sql.append(" type ='212'");	
			if(params!=null&&params.size()>0){
				String pripid = (String) params.get("priPid");
				if(StringUtils.isNotBlank(pripid)){
					sql.append(" and pripid = ? ");
					list.add(pripid);
				}
			}else{
				return null;
			}
			break;
		case "3"://商标(商标)T_SB_SBJBXX
			sql.append(" T_PANORAMIC_ANALYSIS_SB where");
			sql.append(" type = '257'");
			if(params!=null&&params.size()>0){
				String entname = (String) params.get("entname");
				System.out.println(entname);
				sql.append("  and name ='").append(entname).append("'");
			}else{
				return null;
			}
			break;
		case "4"://监管(年报/异常名录/案件/守重/动产抵押/行政处罚/拍卖)
			sql.append(" T_PANORAMIC_ANALYSIS_JG where");
			sql.append(" (type in('207','209','210','211','213','214')");
			if(params!=null&&params.size()>0){
				String pripid = (String) params.get("priPid");
				if(StringUtils.isNotBlank(pripid)){
					sql.append(" and pripid = ?)");
					list.add(pripid);
				}else{
					return null;
				}
				String regno = (String) params.get("regno");
				if(StringUtils.isNotBlank(regno)){
					sql.append(" or (type='258' and pripid=?)");
					list.add(regno);
				}else{
					return null;
				}
			}else{
				return null;
			}
			break;
		case "5"://其他许可
			sql.append(" T_PANORAMIC_ANALYSIS_QTXK where");
			sql.append(" type='255'");
			if(params!=null&&params.size()>0){
				String pripid = (String) params.get("priPid");
				if(StringUtils.isNotBlank(pripid)){
					sql.append(" and pripid = ? ");
					list.add(pripid);
				}
			}else{
				return null;
			}
			break;
		case "6"://注吊销204
			sql.append(" T_PANORAMIC_ANALYSIS_REG where");
			sql.append(" type='204'");
			if(params!=null&&params.size()>0){
				String pripid = (String) params.get("priPid");
				if(StringUtils.isNotBlank(pripid)){
					sql.append(" and pripid = ? ");
					list.add(pripid);
				}
			}else{
				return null;
			}
			break;
		default://登记(变更/迁入/迁出/清算/股权冻结/股权出质/注册)--/注吊销204
			sql.append(" T_PANORAMIC_ANALYSIS_REG where ");
			sql.append(" type in('199','201','202','203','205','206','215')");
			if(params!=null&&params.size()>0){
				String pripid = (String) params.get("priPid");
				if(StringUtils.isNotBlank(pripid)){
					sql.append(" and pripid = ? ");
					list.add(pripid);
				}
			}else{
				return null;
			}
			break;
		}
		System.out.println(params.get("priPid")+"sql语句："+sql);
		return dao.queryForList(sql.toString(), list);
	}
	
	public List<Map> findByZDXPanoramicAnalysis(Map params) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO("iqDataSource");
		StringBuffer sql=new StringBuffer("select id,pkidname,pkidvalue,pripid,sourceflag,type,tablename,title,name,describe,operationtime,timestamp,operationtime localtime from T_PANORAMIC_ANALYSIS_REG where tablename='T_SCZT_ZDXXX'");
		List list = new ArrayList();
		if(params!=null&&params.size()>0){
			String pripid = (String) params.get("priPid");
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and pripid = ? ");
				list.add(pripid);
			}
		}else{
			return null;
		}
		return dao.queryForList(sql.toString(), list);
	}
	
	/**
	 * 全景分析详细
	 * @param tableCode
	 * @param pkIdName
	 * @param pkIdValue
	 * @param type 用来判断具体主体执行个体，外资还是内资
	 * @return
	 * @throws OptimusException
	 */
	public Map<String,Object> findByEntPanoramicAnalysisDetail(String tableCode,String pkIdName,String pkIdValue,String type) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer();
		//外资（广东溢达包装有限公司） 个体（四会市东城区百莲美容美体会所） 内资（广东众望通科技股份有限公司）
		//众望通有（基本信息、隶属信息、出资信息、人员信息、变更信息、证照信息；年报基本信息、对外出资-投资人信息、党建信息）
		//东莞市厚街多元饮水店(注吊销信息)
		//广东公泰物资有限公司(股权信息-股权冻结)
		//广东金穗丰实业有限公司(股权信息-股权出质信息)
		//广东国润酒业有限公司(迁移信息)清算信息
		switch (tableCode.toLowerCase()) {
		case "t_sczt_scztjbxx"://执行主体
			if("1".equals(type)){
				sql.append(Conts.SCZTJBXXSQLGT);
			}else if("2".equals(type)){
				sql.append(Conts.SCZTJBXXSQLWZ);
			}else{//("0".equals(type))
				sql.append(Conts.SCZTJBXXSQL);
			}
			break;
		case "t_ndbg_qybsjbxx"://年报 众望通
			sql.append(Conts.NDBGJBXX);
			break;
		case "t_sczt_bgxx"://变更（完成）众望通
			sql.append("select a.bgxxid,(select value from T_DM_BGBASX where code = a.altitem) altitem,a.altbe,a.altaf,convert(varchar,a.altdate,111) altdate,a.alttime,a.content,a.oldhistoryinfoid,a.newhistoryinfoid,a.entityno,convert(varchar,a.operatedate,111) operatedate,a.operator,(case a.modifystatus when '1' then '有效' else '无效' end) modifystatus,a.timestamp from t_sczt_bgxx a ");
			break;
		case "t_sczt_ls_qrxx"://迁入 广东万源创宝再生资源股份有限公司
			sql.append("select a.qrxxid,a.minletnum,(select value from T_DM_GSGLJGDM where code=a.moutareregorg) moutareregorg,a.minrea,a.moutarea,substring(a.mindate,1,char_length(a.mindate)-9) mindate,a.archremovemode,a.pripid,a.historyinfoid,a.memo,a.minstatus,a.timestamp,a.sourceflag from t_sczt_ls_qrxx a");
			break;
		case "t_sczt_ls_qcxx"://迁出 广东万源创宝再生资源股份有限公司
			sql.append("select a.qcxxid,a.moutletnum,a.minarea,a.moutrea,(select value from T_DM_GSGLJGDM where code=a.minareregorg) minareregorg,substring(a.moutdate,1,char_length(a.moutdate)-9) moutdate,a.archremovemode,a.pripid,a.historyinfoid,a.memo,a.moutstatus,a.timestamp,a.sourceflag from t_sczt_ls_qcxx a");
			break;
		case "t_sczt_qsxx"://清算
			//sql.append("select a.qsxxid,a.ligprincipal,a.liqmem,a.addr,a.tel,a.ligst,a.ligenddate,a.debttranee,a.claimtranee,a.pripid,a.timestamp,a.sourceflag from t_sczt_qsxx a");
			sql.append(Conts.T_SCZT_QSXX);
			break;
		case "t_sczt_zdxxx"://注吊销(完成)龙川县新田供销社综合四门市
			//sql.append("select zdxxxid,pripid,canrea,sanauth,sandocno,sandate,cancelbrsign,declebrsign,affwritno,blicrevorinum,blicrevorino,blicrevdupconum,carevst,pubnewsname,pubdate,candate,devicecancel,devicechange,handinsigndate,debtunit,bizsequence,handinlicenceperson,registercert,reopendate,repcarcannum,repcert,sealdestorydesc,shutoutbegindate,shutoutenddate,takelicenceperson,timestamp,sourceflag from t_sczt_zdxxx a");
			sql.append("select a.zdxxxid,a.pripid,(select value from T_DM_ZXYYDM where code=a.canrea) canrea,a.sanauth,a.sandocno,substring(a.sandate,1,char_length(a.sandate)-9)  sandate,a.cancelbrsign,a.declebrsign,a.affwritno,a.blicrevorinum,a.blicrevorino,a.blicrevdupconum,a.carevst,a.pubnewsname,substring(a.pubdate,1,char_length(a.pubdate)-9) pubdate,a.candate,(case a.devicecancel when '' then '否' when null then '否' else '是' end) devicecancel,(case a.devicechange when '' then '否' when null then '否' else '是' end) devicechange,substring(a.handinsigndate,1,char_length(a.handinsigndate)-9)  handinsigndate,a.debtunit,a.bizsequence,a.handinlicenceperson,a.registercert,substring(a.reopendate,1,char_length(a.reopendate)-9)  reopendate,a.repcarcannum,a.repcert,a.sealdestorydesc,substring(a.shutoutbegindate,1,char_length(a.shutoutbegindate)-9)  shutoutbegindate,substring(a.shutoutenddate,1,char_length(a.shutoutenddate)-9)  shutoutenddate,a.takelicenceperson,a.timestamp from t_sczt_zdxxx a");
			break;
		case "t_sczt_gqdjxx"://股权冻结(完成)广东公泰物资有限公司/惠州市第六污水处理有限公司
			//sql.append("select gqdjxxid,froam,sharfroprop,froauth,frofrom,froto,frodocno,exestate,corentname,thawauth,thawdocno,thawdate,frozsign,historyinfoid,pripid,freezeitem,investinfo,thawgist,timestamp,invest,sourceflag from t_sczt_gqdjxx a");
			sql.append("select a.GQDJXXID,a.FroAm,a.SharFroProp,a.FroAuth,a.FroFrom,a.FroTo,a.FroDocNO,a.ExeState,a.CorEntName,a.ThawAuth,a.ThawDocNO,substring(a.ThawDate,1,char_length(a.ThawDate)-9) ThawDate,(select value from T_DM_DJZTDM where code=a.FrozSign) frozsign,a.historyInfoID,a.PriPID,a.freezeItem,a.investInfo,a.thawGist,a.TIMESTAMP,a.invest from T_SCZT_GQDJXX a");
			break;
		case "t_sczt_gqczxx"://股权出质
			sql.append("select a.gqczxxid,a.pledgor,a.blictype,a.blicno,a.certype,a.cerno,a.pledamunit,a.impam,a.imporg,a.imporgtel,a.entname,a.regno,substring(a.applydate,1,char_length(a.applydate)-9) applydate,substring(a.approveDate,1,char_length(a.approvedate)-9) approvedate,(select value from T_DM_OLD_JYZTDM where code=a.regstatus) regstatus,a.pripid,(select value from T_DM_djgXjGdm where code=a.regorg) regorg,a.stockregisterno,a.timestamp,a.historyinfoid,a.rescindopinion,a.stockpledgehostexclusiveid,a.zqr_blictype,a.zqr_blicno,a.zqr_certype,a.zqr_cerno,a.sourceflag,a.secureclaimnum,a.regcapcur from t_sczt_gqczxx a");
			//sql.append(Conts.T_SCZT_GQDJXX);
			break;
		case "t_aj_ajjbxx"://案件 东莞市厚街多元饮水店
			sql.append(Conts.CASEINFO);
			break;
		case "t_gg_ggjyjbxx"://广告
			sql.append("select a.adid,a.pripid,a.adproprietor,a.adbuenttype,a.adbuentchar,a.adope,a.tax,a.opetype,a.adbuent,a.adopeorg,a.oploc,a.postalcode,convert(varchar,a.adopfrom,111) adopfrom,convert(varchar,a.adopto,111) adopto,a.adlicbu,a.regcap,a.maglicno,a.remark,a.adopeinc,a.forbusiadinc,a.netinc,a.deficit,a.sourceflag,a.timestamp from t_gg_ggjyjbxx a");
			break;
		case "t_scjg_szjbxxzsb"://守重(完成)众望通
			sql.append("select a.curcompactcreditid,a.bizsequence,a.bizcompactcreditid,a.comcrelevelid,convert(varchar,a.comcreyear) comcreyear,a.corpname,a.registerno,a.entitytypeid,a.principal,a.address,a.postcode,a.phone,a.mobilephone,a.chargedep,(select value from T_DM_JJXZDM where code=a.economicproperty) economicproperty,convert(varchar,a.regcapital)||'(万元)' regcapital,(select value from t_dm_bzdm where code = a.regcapitalcoin) regcapitalcoin,a.employeenum,a.approvetypeid,a.entityurl,a.corporgcode,a.countrytaxno,a.regiontaxno,a.businessscope,(select value from T_DM_djgXjGdm where code=a.issuedeptid) issuedeptid,a.aicid,convert(varchar,a.firstcomcreyear) firstcomcreyear,a.continuecomcrenum,a.continuecomcreyear,(select value from T_DM_djgXjGdm where code=a.approvedeptid) approvedeptid,(select value from T_DM_djgXjGdm where code=a.acceptdeptid) acceptdeptid,a.entityno,(select value from T_DM_SZZT where code=a.statusid) statusid,(case a.islocation when '1' then '是' else '否' end) islocation,convert(varchar,a.approvedate,111) approvedate,convert(varchar,a.applydate,111) applydate,a.othercredit,a.recommendunit,a.submitname,convert(varchar,a.submitdate,111) submitdate,(case a.iswebsubmit when 'y' then '是' else '否' end) iswebsubmit,(case a.isDoMedal when '1' then '是' else '否' end) isdomedal,(case a.isRunDoMedal when '1' then '是' else '否' end) isrundomedal,a.sourceflag,a.timestamp from t_scjg_szjbxxzsb a");
			break;
		case "t_jyycml_qygtnz"://异常名录(完成)惠州市名门制衣有限公司
			sql.append("select a.abnormalqiyeid,a.pripid,a.regno,a.uniscid,a.entname,a.lerep,a.abnormalid,(case a.biaoji when '9100' then (select value from T_DM_LRYCMLYY where code=a.SpeCause) when '9200' then (select value from T_DM_NZLRYCMLYY where code=a.SpeCause) when '999' then (select value from T_DM_GTLRYCMLYY where code=a.SpeCause) else a.specause end) specause,convert(varchar,a.abntime,111) abntime,(case a.biaoji when '9100' then (select value from T_DM_YCYCMLYY where code=a.RemExcpRes) when '9200' then (select value from T_DM_NZYCYCMLYY where code=a.RemExcpRes) when '999' then (select value from T_DM_GTYCYCMLYY where code=a.RemExcpRes) else a.RemExcpRes end) remexcpres,convert(varchar,a.remdate,111) remdate,(select value from T_DM_djgXjGdm where code=a.decorg) decorg,a.auditingfileno,a.reconsiderationorg,a.litigationorg,a.governmentorg,a.area_code,(case a.biaoji when '9100' then '企业' when '9200' then '农专' when '999' then '个体' else a.biaoji end) biaoji,a.sourceflag,a.timestamp from t_jyycml_qygtnz a");
			break;
		case "t_scjg_dydjywzs"://动产抵押
			sql.append("select a.pledgeid,a.historyinfoid,a.bizsequence,a.morregcno,convert(varchar,a.approvedate,111) approvedate,a.status,a.isforeignplace,a.guaname,a.reason,a.totalvalue,a.owner,a.guades,a.priclaseckind,a.pefperto,a.scope,convert(varchar,a.applydate,111) applydate,a.foreignregdep,a.supervisedeptid,a.contractno,a.totalnum,a.approvecontent,a.issuedeptid,a.aicid,a.suretymoney,a.bargaintype,a.unit,a.contractnotype,a.totalvaluecointype,a.totalvalueinrmb,a.suretymoneycointype,a.suretymoneyinrmb,a.mortgagor,a.contractname,a.bargainname,a.islimited,a.bargainno,a.expireshowmode,convert(varchar,a.pefperform,111) pefperform,a.otherbargaintypename,a.pmoney,a.creditbargaintype,a.remark,a.sourceflag,a.timestamp from t_scjg_dydjywzs a");
			break;
		case "t_aj_xzcfjds"://行政处罚（） 珠海市斗门县万顺发展有限公司
			sql.append("select a.fijudgmentid,a.pendecno,a.caseno,a.fspartyname,a.lerep,a.postcode,a.tel,a.oploc,a.opscope,a.sex,a.age,a.occupation,a.cerno,a.litedeg,a.nation,a.polstand,a.fsnativeplace,a.casereason,a.illegfact,a.quabasis,a.penbasis,a.fsalarm,a.fschargestoplaw,a.fschargestopintrusion,a.ffseizureillegal,a.ffseizurenonlaw,a.ffseizureprofit,a.ffseizuresale,a.ffseizuredealbrow,a.penam,a.fskeeplicence,a.fsrevokelicence,a.fskeepshopcard,a.fsrevokeshopcard,a.fsstopproduction,a.fsaviso,a.fspricefixing,a.fspreemption,a.fsotherpunish,a.fstellright,convert(varchar,a.fdrecorddate,111) fdrecorddate,a.fsrecorder,convert(varchar,a.fdcreateupdatedate,111) fdcreateupdatedate,a.pigsign,a.archno,a.fsdeductproperty,a.fsopencorrect,a.fsstopissue,a.fsseizureillegaltype,a.fsseizuresaletype,a.regno,a.area_code,a.timestamp,a.sourceflag from t_aj_xzcfjds a ");
			break;
		case "t_qtbm_xzxk"://其他行政许可
			sql.append(Conts.T_QTBM_XZXK);
			break;
		case "t_qtbm_xzcf"://其他行政处罚
			sql.append(Conts.T_QTBM_XZCF);
			break;
		case "t_sb_sbjbxx"://商票
			sql.append(Conts.T_SB_SBJBXX);
			break;
		case "t_scjg_pmxx"://拍卖
			sql.append(Conts.T_SCJG_PMXX);
			break;
		case "laolai"://拍卖
			sql.append(Conts.LAOLAI);
			break;
		default:
			sql.append("select * from ").append(tableCode).append(" a");
			break;
		}
		if("t_qtbm_xzxk".equals(tableCode.toLowerCase())||"t_qtbm_xzcf".equals(tableCode.toLowerCase())){
			sql.append(" where a.").append(pkIdName).append("=").append(pkIdValue);
		}else{
			sql.append(" where a.").append(pkIdName).append("='").append(pkIdValue).append("'");
		}
		System.out.println("全景分析详细sql语句:"+sql);
		List<Map> listData = dao.queryForList(sql.toString(), null);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		if(listData!=null && listData.size()>0){
			dataMap = listData.get(0);
		}
		return dataMap;
	}
	
	/**
	 * 遍历map重新查询代码对应的中文
	 * @param params
	 * @return
	 * @throws OptimusException
	 */
	public Map<String,Object> findErgodicMap(Map params) throws OptimusException {
		//id,pkidname,pkidvalue,pripid,sourceflag,type,tablename,title,name,describe,operationtime,timestamp,operationtime localtime
		String type=(String) params.get("type");//类型
		String tableCode=(String) params.get("tablename");//表名
		String pkIdName=(String) params.get("pkidname");//主键名
		String pkIdValue=(String) params.get("pkidvalue");//主键值
		Map<String,Object> dataMap = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(tableCode)){
			StringBuffer sql=new StringBuffer();
			switch (tableCode.toLowerCase()) {//执行主体
			case "t_sczt_scztjbxx":
				sql.append("select (case entname when '' then regno else entname end) title,(case entname when '' then regno else entname end) name,((case entname when '' then '注册号为'||regno||'的主体' else entname end)+'注册成立于'||estdate) describe from t_sczt_scztjbxx ");
				sql.append(" where ").append(pkIdName).append("='").append(pkIdValue).append("'");
				break;
			case "t_ndbg_qybsjbxx"://年报
				sql.append("select '企业年报' title,'企业年报' name,(entname||'报送于'||ancheyear) describe,anchedate from t_ndbg_qybsjbxx ");
				sql.append(" where ").append(pkIdName).append("='").append(pkIdValue).append("'");
				break;
			case "t_sczt_bgxx"://变更
				sql.append("select c.altitem title,c.altitem name,(c.altitem||'：由'||c.altbe||'变更为'||c.altaf) describe from (select (select value from t_dm_bgbasx where code=a.altitem) altitem,a.altbe,a.altaf,a.altdate from dbo.t_sczt_bgxx a ");
				sql.append(" where a.").append(pkIdName).append("='").append(pkIdValue).append("') c");
				sql.append(" where c.altitem is not null and c.altitem<>''");
				break;
			case "t_sczt_qrxx"://迁入
				sql.append("select '市场主体迁入' title,'市场主体迁入' name,('市场主体于'||mindate||'从'||moutarea||'迁入') describe from t_sczt_qrxx ");
				sql.append(" where ").append(pkIdName).append("='").append(pkIdValue).append("'");
				break;
			case "t_sczt_qcxx"://迁出
				sql.append("select '市场主体迁出' title,'市场主体迁出' name,('市场主体于'||moutdate||'从'||minarea||'迁出') describe from t_sczt_qcxx ");
				sql.append(" where ").append(pkIdName).append("='").append(pkIdValue).append("'");
				break;
			case "t_sczt_qsxx"://清算
				sql.append("select '清算' title,'清算' name,(case when ligst='' then '市场主体于'||ligenddate||'进行了清算' when ligst is null then '市场主体于'||ligenddate||'进行了清算' else ligst end) describe from t_sczt_qsxx ");
				sql.append(" where ").append(pkIdName).append("='").append(pkIdValue).append("'");
				break;
			case "t_sczt_zdxxx"://注吊销
				sql.append("select '注吊销' title,'注吊销' name,canrea describe from t_sczt_zdxxx ");
				sql.append(" where ").append(pkIdName).append("='").append(pkIdValue).append("'");
				break;
			case "t_sczt_gqdjxx"://股权冻结
				sql.append("select '股权冻结' title,freezeItem name,investInfo describe FROM T_SCZT_GQDJXX ");
				sql.append(" where ").append(pkIdName).append("='").append(pkIdValue).append("'");
				break;
			case "t_sczt_gqczxx"://股权出质
				sql.append("select '股权出质' title,'股权出质' name,(entname+'于'||applydate||'股权出质') describe FROM T_SCZT_GQCZXX ");
				sql.append(" where ").append(pkIdName).append("='").append(pkIdValue).append("'");
				break;
			default:
				//sql.append("select * from ").append(tableCode);
				break;
			}
			List<Map> listData = null;
			if(StringUtils.isNotBlank(sql.toString())){
				IPersistenceDAO dao= this.getPersistenceDAO();
				listData = dao.queryForList(sql.toString(), null);
			}else{
				listData = new ArrayList<Map>();
			}
			System.out.println(tableCode+"全景分析中文代码sql语句:"+sql);
			if(listData!=null && listData.size()>0){
				dataMap = listData.get(0);
				params.put("title", dataMap.get("title"));
				params.put("name", dataMap.get("name"));
				params.put("describe", dataMap.get("describe"));
			}else{
				params = new HashMap();
			}
		}else{
			params = new HashMap();
		}
		return params;
	}
	
	/**
	 * 详细信息字段信息
	 * @param tableCode
	 * @param type 8及以上的数字
	 * @return
	 * @throws OptimusException
	 */
	public Map<String,Object> findByTabName(String tableCode,String type,String strIndex) throws OptimusException {
		Map map=new LinkedHashMap();
		IPersistenceDAO dao= this.getPersistenceDAO();
		String condition="";
		if("t_sczt_scztjbxx".equalsIgnoreCase(tableCode)){//执行主体
			switch (strIndex) {
			/*case "1":case "4":case "5":condition = "1";break;*/
			case "0":
				condition = "2";
				break;
			case "2":
				condition = "3";
				break;
			case "3":
				condition = "5";
				break;
			case "6":
				condition = "6";
				break;
			case "7":
				condition = "7";
				break;
			case "9":
				condition = "4";
				break;
			default:
				condition = "1";
				break;
			}
		}
		/*else if("t_ndbg_qybsjbxx".equalsIgnoreCase(tableCode)){//年报
			condition = type;
		}else if("t_aj_ajjbxx".equalsIgnoreCase(tableCode)){//案件
			condition = "10";
		}*/
		else{
			condition = type;
		}
		StringBuffer sbf = new StringBuffer("select * from T_SCZT_JBXXINHTML where state=? and type =? order by sort");
		List<String> params = new ArrayList<String>();
		params.add("1");
		params.add(condition);
		List<Map> datalist=dao.queryForList(sbf.toString(), params);
		if(datalist!=null && datalist.size()>0){
			for(Map m : datalist){
				map.put(m.get("fieldcn"), m.get("fieldeng"));
			}
		}
		return map;
	}
	
	public Map findRegJbxx(String pripid,String sourceflag,String type) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		//StringBuffer sql=new StringBuffer("select a.*,b.*,c.*,d.* from T_SCZT_SCZTJBXX a,T_SCZT_SCZTBCXX b,T_SCZT_SCZTLSXX c,T_SCZT_WGQYSCJYHDJBXX d where a.pripid=b.pripid and a.pripid=c.pripid and a.pripid=d.WGQYSCJYHDJBXXID ");
		StringBuffer sql=null;
		if("0".equals(type)){
			sql=new StringBuffer(Conts.SCZTJBXXSQL);
		}else if("1".equals(type)){
			sql=new StringBuffer(Conts.SCZTJBXXSQLGT);
		}else if("2".equals(type)){
			sql=new StringBuffer(Conts.SCZTJBXXSQLWZ);
		}
		List<String> list = new ArrayList<String>();
		sql.append(" where 1=1 ");
		if(StringUtils.isNotBlank(sourceflag)){
			sql.append(" and a.sourceflag = ?");
			list.add(sourceflag);
		}
		if(StringUtils.isNotBlank(pripid)){
			sql.append(" and a.pripid = ? ");
			list.add(pripid);
		}
		List<Map> datalist=dao.queryForList(sql.toString(), list);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		if(datalist!=null && datalist.size()>0){
			dataMap = datalist.get(0);
		}
		return dataMap;
	}
	
	public List<Map> findGG(String pripid,String sourceflag,String type) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sbf = new StringBuffer("select a.gjcyxxid pripid,a.sourceflag,b.name from T_SCZT_GJCYXX a, t_sczt_ryjbxx b where a.personid = b.ryjbxxid and a.SOURCEFLAG=b.SOURCEFLAG");
		sbf.append(" and a.pripid='").append(pripid).append("'");
		sbf.append(" and a.sourceflag='").append(sourceflag).append("'");
		List<Map> datalist=dao.queryForList(sbf.toString(),null);
		return datalist;
	}
	public List<Map> findFR(String pripid,String sourceflag,String type) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sbf = new StringBuffer("select a.pripid,a.sourceflag,a.inv from t_sczt_frtzrjczxx a where 1=1");
		sbf.append(" and a.pripid='").append(pripid).append("'");
		sbf.append(" and a.sourceflag='").append(sourceflag).append("'");
		List<Map> datalist=dao.queryForList(sbf.toString(),null);
		return datalist;
	}
	public List<Map> findZR(String pripid,String sourceflag,String type) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sbf = new StringBuffer("select a.pripid,a.sourceflag,a.inv from t_sczt_zrrtzrjczxx a where 1=1");
		sbf.append(" and a.pripid='").append(pripid).append("'");
		sbf.append(" and a.sourceflag='").append(sourceflag).append("'");
		List<Map> datalist=dao.queryForList(sbf.toString(),null);
		return datalist;
	}
	
	public List<Map> findTest(String pripid,String sourceflag,String type) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sbf = new StringBuffer("select pripid,SOURCEFLAG,id,pid,levels,name,type from t_qiyezupu where 1=1");
		sbf.append(" and pripid='").append(pripid).append("'");
		sbf.append(" and sourceflag='").append(sourceflag).append("'");
		sbf.append(" and levels='").append(type).append("'");
		List<Map> datalist=dao.queryForList(sbf.toString(),null);
		return datalist;
	}
	
	public List<Map> findTest(String id,String type) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sbf = new StringBuffer("select pripid,SOURCEFLAG,id,pid,levels,name,type from t_qiyezupu where 1=1");
		sbf.append(" and pid='").append(id).append("'");
		sbf.append(" and levels='").append(type).append("'");
		List<Map> datalist=dao.queryForList(sbf.toString(),null);
		return datalist;
	}
}
