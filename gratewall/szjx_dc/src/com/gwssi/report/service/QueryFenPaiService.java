package com.gwssi.report.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.report.balance.api.ReportModel;
import com.gwssi.report.balance.api.ReportSource;
import com.gwssi.report.model.TCognosReportBO;
import com.gwssi.report.util.LogOperation;

/**
 * 分派数  service
 * @author lokn
 * 2018/02/09
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service
public class QueryFenPaiService extends BaseService implements ReportSource {
	
	IPersistenceDAO dao = this.getPersistenceDAO("dc_dc");
	LogOperation logop = new LogOperation();
	
	@Override
	public ReportModel getReport(TCognosReportBO queryInfo) {
		return null;
	}
	
	/**
	 * 查询登记部门信息
	 * @return
	 * @throws OptimusException 
	 */
	public List<Map> fingRegCode() throws OptimusException {
		String sql = "select b.regdepcode, c.name"
			+ "  from (select a.regdepcode regdepcode"
			+ "          from dc_dc.dc_cpr_infoware a"
			+ "         group by a.regdepcode) b"
			+ "  left join db_yyjc.jc_public_department c"
			+ "    on b.regdepcode = c.code"
			+ " where b.regdepcode <> '6100'"
			+ " order by c.parent_code";
		List<Map> queryForList = dao.queryForList(sql, null);
		return queryForList;
	}

	/**
	 * 查询
	 * 	  --登记部门分派数年度月份对比统计报表
	 * @param beginTime 开始日期
	 * @param endTime	截止日期
	 * @param inftype	信息件类型
	 * @param listForMonth 月份
	 * @param opreationtype 工作状态：1-登记，2-下载
	 * @param req
	 * @return list
	 * @throws OptimusException 
	 */
	public Map<String,List<Map>> findRegistDeptForList(String beginTime, String endTime, String inftype
			, int opreationtype, HttpServletRequest req) throws OptimusException {
		StringBuffer sql = new StringBuffer();
		List<String> params = new ArrayList<String>();
		List<String> params2 = new ArrayList<String>();
		Map result = new HashMap<String, List>();
		
		// 查询日期sql
		String sql2 = "select to_char(t.regtime, 'yyyyMM') as regtime"
			 + " from dc_cpr_infoware t"
			 + " where t.regtime >= to_date(?, 'yyyy-MM-dd hh24:mi:ss')"
			 + "  and t.regtime <= to_date(?, 'yyyy-MM-dd hh24:mi:ss')"
			 + "  group by to_char(t.regtime, 'yyyyMM')"
			 + "  order by to_char(t.regtime, 'yyyyMM')";
		params.add(beginTime + " 00:00:00");
		params.add(endTime + " 23:59:59");
		List<Map> monthForList = dao.queryForList(sql2, params);
		if (monthForList == null || monthForList.size() == 0) {
			return null;
		}
		
		sql.append("select  z.regdepname as name"); 
		for (Map map : monthForList) {
			sql.append(" , nvl(z.\""+ map.get("regtime") +"\", 0) \""+ map.get("regtime") + "\"");
		}
		sql.append(", sum(0");
		for (Map map : monthForList) {
			sql.append(" + nvl(z.\""+ map.get("regtime") +"\", 0)");
		}
		sql.append(") over (partition by z.regdepname ) num from ("
			+ " SELECT * FROM ("
			+ " SELECT D.PARENT_DEP_CODE,'　'||T.regdepname AS REGDEPNAME, TO_CHAR(T.REGTIME, 'YYYYMM') AS REGTIME, COUNT(1) AS CNT"
			+ "   FROM DC_CPR_INFOWARE T, DC_JG_SYS_RIGHT_DEPARTMENT D, DC_CPR_PROCESS_INSTANCE N"
			+ "  WHERE T.REGDEPCODE = D.SYS_RIGHT_DEPARTMENT_ID"
			+ "    AND T.PROINSID = N.PROINSID"
			+ "    AND N.STATENAME <> '预登记'");
		
		if (StringUtils.isNotEmpty(inftype)) {
			sql.append("    AND T.INFTYPE = ?");
		}
		
		sql.append("    AND T.REGTIME >= TO_DATE(?, 'YYYY-MM-DD hh24:mi:ss')"
			+ "    AND T.REGTIME <= TO_DATE(?, 'YYYY-MM-DD hh24:mi:ss')"
			+ "  GROUP BY D.PARENT_DEP_CODE,T.REGDEPNAME, TO_CHAR(T.REGTIME, 'YYYYMM')"
			+ " UNION ALL"
			+ " SELECT D.PARENT_DEP_CODE,D1.DEP_NAME AS REGDEPNAME, TO_CHAR(T.REGTIME, 'YYYYMM') AS REGTIME, COUNT(1) AS CNT"
			+ "   FROM DC_CPR_INFOWARE T, DC_JG_SYS_RIGHT_DEPARTMENT D,DC_JG_SYS_RIGHT_DEPARTMENT D1, DC_CPR_PROCESS_INSTANCE N"
			+ "  WHERE T.REGDEPCODE = D.SYS_RIGHT_DEPARTMENT_ID"
			+ "    AND D.PARENT_DEP_CODE = D1.SYS_RIGHT_DEPARTMENT_ID"
			+ "    AND T.PROINSID = N.PROINSID"
			+ "    AND N.STATENAME <> '预登记'");
		
		if (StringUtils.isNotEmpty(inftype)) {
			sql.append("    AND T.INFTYPE = ?");
		}
		
		sql.append("    AND T.REGTIME >= TO_DATE(?, 'YYYY-MM-DD hh24:mi:ss')"
			+ "    AND T.REGTIME <= TO_DATE(?, 'YYYY-MM-DD hh24:mi:ss')"
			+ "  GROUP BY D.PARENT_DEP_CODE,D1.DEP_NAME, TO_CHAR(T.REGTIME, 'YYYYMM')"
			+ "   ) T"
			+ "  PIVOT(SUM(T.CNT) FOR  REGTIME IN (");
		
		for (int i = 0; i < monthForList.size(); i++) {
			sql.append(monthForList.get(i).get("regtime"));
			if (i < monthForList.size() - 1) {
				sql.append(", ");
			}
		}
		//+ 201701,201702 "
		
		sql.append(")) )z ORDER BY PARENT_DEP_CODE, REGDEPNAME DESC");
		
		if (StringUtils.isNotEmpty(inftype)) {
			params2.add(inftype);
		}
		params2.add(beginTime + " 00:00:00");
		params2.add(endTime + " 23:59:59");
		
		if (StringUtils.isNotEmpty(inftype)) {
			params2.add(inftype);
		}
		params2.add(beginTime + " 00:00:00");
		params2.add(endTime + " 23:59:59");
		
		List<Map> queryForList = dao.queryForList(sql.toString(), params2);
		logop.logInfoYeWu("登记部门分派数年度月份对比统计报表", "WDY", opreationtype == 1 ? "查看报表" : "下载报表", sql.toString(),
				beginTime + "," + endTime + "," + inftype, req);
		if (queryForList == null || queryForList.size() == 0) {
			return null;
		}
		result.put("month", monthForList);
		result.put("query", queryForList);
		 
		return result;
	}

	/**
	 * 查询
	 * 	  --登记人分派数年度月份对比统计报表
	 * @param beginTime 开始日期
	 * @param endTime	截止日期
	 * @param inftype	信息件类型
	 * @param regCode	登记部门code值
	 * @param opreationtype 工作状态：1-登记，2-下载
	 * @param req
	 * @return
	 * @throws OptimusException 
	 */
	public Map<String, List<Map>> findRegistrantForMap(String beginTime, String endTime, String inftype, String regCode,
			int opreationtype, HttpServletRequest req) throws OptimusException {
		
		StringBuffer sql = new StringBuffer();
		List<String> params = new ArrayList<String>();
		List<String> queryMonthParams = new ArrayList<String>();
		Map<String, List<Map>> result = new HashMap<String, List<Map>>();
		String queryMonthSql = "SELECT TO_CHAR(T.REGTIME, 'YYYYMM') AS regtime"
			+ "  FROM DC_CPR_INFOWARE T"
			+ " WHERE T.REGTIME >= TO_DATE(?, 'YYYY-MM-DD hh24:mi:ss')"
			+ "   AND T.REGTIME <= TO_DATE(?, 'YYYY-MM-DD hh24:mi:ss')"
			+ " GROUP BY TO_CHAR(T.REGTIME, 'YYYYMM')"
			+ " ORDER BY  TO_CHAR(T.REGTIME, 'YYYYMM')";
		
		queryMonthParams.add(beginTime + " 00:00:00");
		queryMonthParams.add(endTime + " 23:59:59");
		// 月份查询
		List<Map> queryMonthForList = dao.queryForList(queryMonthSql, queryMonthParams);
		if (queryMonthForList == null || queryMonthForList.size() == 0) {
			return null;
		}
		// 登记人分派数查询
		sql.append("select  nvl(z.regdepname,'　') name"); // regdepname
		for (Map map : queryMonthForList) {
			sql.append(", nvl(z.\"" + map.get("regtime") + "\", 0) \"" + map.get("regtime") +"\"");
		}
		sql.append(", sum(0");
		for (Map map : queryMonthForList) {
			sql.append(" + nvl(z.\"" + map.get("regtime") + "\", 0)");
		}
		//nvl(z."201701",0) "201701" ,nvl(z."201702",0) "201702" ,sum(nvl(z."201701",0)+nvl(z."201702",0)) over (partition by z.regdepname ) num"
				
		sql.append(" ) over (partition by z.regdepname ) num"
				+ " from ("
				+ " SELECT * FROM ("
				+ " SELECT  t.REGPERSON REGDEPNAME, TO_CHAR(T.REGTIME, 'YYYYMM') AS REGTIME, COUNT(1) AS CNT"
				+ "  FROM DC_CPR_INFOWARE T, DC_CPR_PROCESS_INSTANCE D"
				+ " WHERE T.PROINSID = D.PROINSID"
				+ "   AND D.STATENAME <> '预登记'");
		if (StringUtils.isNotEmpty(regCode)) {
			sql.append("   AND T.regdepcode=? ");
		}
		if (StringUtils.isNotEmpty(inftype)) {
			sql.append("   AND T.Inftype = ? ");
		}
				
		sql.append("   AND  T.REGTIME >= TO_DATE(?,'YYYY-MM-DD hh24:mi:ss')"
				+ "   AND T.REGTIME <= TO_DATE(?,'YYYY-MM-DD hh24:mi:ss')"
				+ " GROUP BY T.REGPERSON, TO_CHAR(T.REGTIME, 'YYYYMM')"
				+ " ) T"
				+ " PIVOT (SUM(T.CNT) FOR REGTIME IN(");
		for (int i = 0; i < queryMonthForList.size(); i++) {
			sql.append(queryMonthForList.get(i).get("regtime"));
			if (i < queryMonthForList.size() - 1) {
				sql.append(", ");
			}
		}
		sql.append(" )))z");
		
		if (StringUtils.isNotEmpty(regCode)) {
			params.add(regCode);
		}
		if (StringUtils.isNotEmpty(inftype)) {
			params.add(inftype);
		}
		params.add(beginTime + " 00:00:00");
		params.add(endTime + " 23:59:59");
		List<Map> queryForList = dao.queryForList(sql.toString(), params);
		result.put("month", queryMonthForList);
		result.put("query", queryForList);
		logop.logInfoYeWu("登记人分派数年度月份对比统计报表", "WDY", opreationtype == 1 ? "查看报表" : "下载报表", sql.toString(),
				beginTime + "," + endTime + "," + inftype, req);
		return result;
	}

	/**
	 * 查询
	 * 	  --承办部门分派数年度月份对比统计报表
	 * @param beginTime
	 * @param endTime
	 * @param inftype
	 * @param opreationtype
	 * @param req
	 * @return
	 * @throws OptimusException 
	 */
	public Map<String, List<Map>> findHandleDeptForMap(String beginTime, String endTime, String inftype, int opreationtype,
			HttpServletRequest req) throws OptimusException {
		StringBuffer sql = new StringBuffer();
		List<String> params = new ArrayList<String>();
		List<String> queryMonthParams = new ArrayList<String>();
		Map<String, List<Map>> result = new HashMap<String, List<Map>>();
		
		String queryMonthSql = "SELECT TO_CHAR(T.REGTIME, 'YYYYMM') AS regtime"
				+ "  FROM DC_CPR_INFOWARE T"
				+ " WHERE T.REGTIME >= TO_DATE(?, 'YYYY-MM-DD hh24:mi:ss')"
				+ "   AND T.REGTIME <= TO_DATE(?, 'YYYY-MM-DD hh24:mi:ss')"
				+ " GROUP BY TO_CHAR(T.REGTIME, 'YYYYMM')"
				+ " ORDER BY  TO_CHAR(T.REGTIME, 'YYYYMM')";
		queryMonthParams.add(beginTime + " 00:00:00");
		queryMonthParams.add(endTime + " 23:59:59");
		List<Map> queryMonthForList = dao.queryForList(queryMonthSql, queryMonthParams);
		if (queryMonthForList == null || queryMonthForList.size() == 0) {
			return null;
		}
		
		sql.append("select z.handepname as name ");
		for (Map map : queryMonthForList) {
			sql.append(", nvl(z.\""+ map.get("regtime") +"\", 0) \""+ map.get("regtime") +"\"");
		}
		sql.append(",  sum(0");
		for (Map map : queryMonthForList) {
			sql.append("+ nvl(z.\"" + map.get("regtime") + "\", 0)");
		}
		sql.append(") over (partition by z.handepname ) num");
		sql.append(" from ("
				+ " select * from ("
				+ "         select d.parent_dep_code, '　' || t.handepname as handepname, to_char(t.regtime, 'yyyyMM') as regtime,  count(1) as cnt"
				+ "           from dc_cpr_infoware   t,"
				+ "                dc_jg_sys_right_department d,"
				+ "                dc_cpr_process_instance    f"
				+ "          where t.handepcode = d.sys_right_department_id"
				+ "            and t.proinsid = f.proinsid"
				+ "            and f.statename <> '预登记'");
		
		if (StringUtils.isNotEmpty(inftype)) {
			sql.append(" and t.inftype = ?");	
		}
		
		sql.append("  and t.regtime >= to_date(?, 'yyyy-MM-dd hh24:mi:ss')"
				+ "  and t.regtime <= to_date(?, 'yyyy-MM-dd hh24:mi:ss')"
				+ "   group by parent_dep_code, t.handepname, to_char(t.regtime, 'yyyyMM')"
				+ "    union all"
				+ "    select d.parent_dep_code,  d1.dep_name as handepname, to_char(t.regtime, 'yyyyMM') as regtime, count(1) as cnt"
				+ "           from dc_cpr_infoware t, dc_jg_sys_right_department d, dc_jg_sys_right_department d1, dc_cpr_process_instance  f"
				+ "          where t.handepcode = d.sys_right_department_id"
				+ "            and d.parent_dep_code = d1.sys_right_department_id"
				+ "            and t.proinsid = f.proinsid"
				+ "            and f.statename <> '预登记'");
		
		if (StringUtils.isNotEmpty(inftype)) {
			sql.append("  and t.inftype = ?");
		}
		
		sql.append("  and t.regtime >= to_date(?, 'yyyy-MM-dd hh24:mi:ss')"
				+ "  and t.regtime <= to_date(?, 'yyyy-MM-dd hh24:mi:ss')"
		        + "  group by d.parent_dep_code, d1.dep_name, to_char(t.regtime, 'yyyyMM'))g"
				+ "  pivot(sum(g.cnt) for regtime in(");
		for (int i = 0; i < queryMonthForList.size(); i++) {
			sql.append(queryMonthForList.get(i).get("regtime"));
			if (i < queryMonthForList.size() - 1) {
				sql.append(", ");
			}
		}
		sql.append(" )))z order by parent_dep_code, handepname desc");
		
		if (StringUtils.isNotEmpty(inftype)) {
			params.add(inftype);
		}
		params.add(beginTime + " 00:00:00");
		params.add(endTime + " 23:59:59");
		
		if (StringUtils.isNotEmpty(inftype)) {
			params.add(inftype);
		}
		params.add(beginTime + " 00:00:00");
		params.add(endTime + " 23:59:59");
		
		List<Map> queryForList = dao.queryForList(sql.toString(), params);
		
		result.put("month", queryMonthForList);
		result.put("query", queryForList);
		logop.logInfoYeWu("承办部门分派数年度月份对比统计报表", "WDY", opreationtype == 1 ? "查看报表" : "下载报表", sql.toString(),
				beginTime + "," + endTime + "," + inftype, req);
		return result;
	}

	/**
	 * 查询
	 * 	  --承办人分派数年度月份对比统计报表
	 * @param beginTime
	 * @param endTime
	 * @param inftype
	 * @param regCode
	 * @param opreationtype
	 * @param req
	 * @return
	 * @throws OptimusException 
	 */
	public Map<String, List<Map>> findHandlerForMap(String beginTime, String endTime, String inftype, String regCode,
			int opreationtype, HttpServletRequest req) throws OptimusException {
		StringBuffer sql = new StringBuffer();
		List<String> params = new ArrayList<String>();
		List<String> queryMonthParams = new ArrayList<String>();
		Map<String, List<Map>> result = new HashMap<String, List<Map>>();
		// 查询月份信息
		String queryMonthSql = "SELECT TO_CHAR(T.REGTIME, 'YYYYMM') AS regtime"
				+ "  FROM DC_CPR_INFOWARE T"
				+ " WHERE T.REGTIME >= TO_DATE(?, 'YYYY-MM-DD hh24:mi:ss')"
				+ "   AND T.REGTIME <= TO_DATE(?, 'YYYY-MM-DD hh24:mi:ss')"
				+ " GROUP BY TO_CHAR(T.REGTIME, 'YYYYMM')"
				+ " ORDER BY  TO_CHAR(T.REGTIME, 'YYYYMM')";
		queryMonthParams.add(beginTime + " 00:00:00");
		queryMonthParams.add(endTime + " 23:59:59");
		List<Map> queryMonthForList = dao.queryForList(queryMonthSql, queryMonthParams);
		if (queryMonthForList == null || queryMonthForList.size() == 0) {
			return null;
		}
		// 查询统计信息sql
		sql.append("select z.handle as name");
		for (Map map : queryMonthForList) {
			sql.append(", nvl(z.\"" + map.get("regtime") + "\", 0) \"" + map.get("regtime") + "\"");
		}
		
		sql.append(", sum(0");
		
		for (Map map : queryMonthForList) {
			sql.append(" + nvl(z.\"" + map.get("regtime") + "\", 0)");
		}
		
		sql.append(") over (partition by z.handle ) num"
				+ " from ("
				+ " select * from ("
				+ "         select t.handleid, t.handle, to_char(t.regtime, 'yyyyMM') as regtime,  count(1) as cnt"
				+ "           from dc_cpr_infoware  t, dc_cpr_process_instance   f"
				+ "          where  t.proinsid = f.proinsid"
				+ "            and f.statename <> '预登记'");
		if (StringUtils.isNotEmpty(inftype)) {
			sql.append(" and t.inftype = ?");
		}
		if (StringUtils.isNotEmpty(regCode)) {
			sql.append(" and t.handepcode = ?");
		}
				
		sql.append("  and t.regtime >= to_date(?, 'yyyy-MM-dd hh24:mi:ss')"
				+ "   and t.regtime <= to_date(?, 'yyyy-MM-dd hh24:mi:ss')"
				+ "   group by t.handleid, t.handle, to_char(t.regtime, 'yyyyMM')"
				+ "  )g"
				+ "   pivot(sum(g.cnt) for regtime in(");
		for (int i = 0; i < queryMonthForList.size(); i++) {
			sql.append(queryMonthForList.get(i).get("regtime"));
			if (i < queryMonthForList.size() - 1) {
				sql.append(", ");
			}
		}
		
		sql.append(" )))z order by handleid, handle desc");
		
		if (StringUtils.isNotEmpty(inftype)) {
			params.add(inftype);
		}
		if (StringUtils.isNotEmpty(regCode)) {
			params.add(regCode);
		}
		params.add(beginTime + " 00:00:00");
		params.add(endTime + " 23:59:59");
		
		List<Map> queryForList = dao.queryForList(sql.toString(), params);
		
		result.put("month", queryMonthForList);
		result.put("query", queryForList);
		logop.logInfoYeWu("承办人分派数年度月份对比统计报表", "WDY", opreationtype == 1 ? "查看报表" : "下载报表", sql.toString(),
				beginTime + "," + endTime + "," + inftype, req);
		return result;
	}

	/**
	 * 查询
	 * 	  --来源方式分派数年度月份对比统计报表
	 * @param beginTime
	 * @param endTime
	 * @param inftype
	 * @param opreationtype
	 * @param req
	 * @return
	 * @throws OptimusException 
	 */
	public Map<String, List<Map>> findSourceWayForMap(String beginTime, String endTime, String inftype
			, int opreationtype, HttpServletRequest req) throws OptimusException {
		StringBuffer sql = new StringBuffer();
		List<String> params = new ArrayList<String>();
		List<String> queryMonthParams = new ArrayList<String>();
		Map<String, List<Map>> result = new HashMap<String, List<Map>>();
		// 查询月份信息
		String queryMonthSql = "SELECT TO_CHAR(T.REGTIME, 'YYYYMM') AS regtime"
				+ "  FROM DC_CPR_INFOWARE T"
				+ " WHERE T.REGTIME >= TO_DATE(?, 'YYYY-MM-DD hh24:mi:ss')"
				+ "   AND T.REGTIME <= TO_DATE(?, 'YYYY-MM-DD hh24:mi:ss')"
				+ " GROUP BY TO_CHAR(T.REGTIME, 'YYYYMM')"
				+ " ORDER BY  TO_CHAR(T.REGTIME, 'YYYYMM')";
		queryMonthParams.add(beginTime + " 00:00:00");
		queryMonthParams.add(endTime + " 23:59:59");
		List<Map> queryMonthForList = dao.queryForList(queryMonthSql, queryMonthParams);
		if (queryMonthForList == null || queryMonthForList.size() == 0) {
			return null;
		}
		// 查询统计信息sql 
		sql.append(" select z.name as name");
		for (Map map : queryMonthForList) {
			sql.append(", nvl(z.\"" + map.get("regtime") + "\", 0) \"" + map.get("regtime") + "\"");
		}
		sql.append(", sum(0");
		for (Map map : queryMonthForList) {
			sql.append(" + nvl(z.\"" + map.get("regtime") + "\", 0)");
		}
		sql.append(") over(partition by z.name ) num"
				+ "  from ("
				+ " select * from ("
				+ " select d.name , to_char(t.regtime, 'yyyyMM') as regtime, count(1) as cnt"
				+ "   from dc_cpr_infoware t, dc_code.dc_12315_codedata d, dc_cpr_process_instance f"
				+ "  where t.infoori = d.code"
				+ "   and t.proinsid = f.proinsid"
				+ "   and f.statename <> '预登记'"
				+ "   and d.codetable = 'CH02'");
		if (StringUtils.isNotEmpty(inftype)) {
			sql.append(" and t.inftype = ?");
		}
		sql.append("   and t.regtime >= to_date(?, 'yyyy-MM-dd hh24:mi:ss') "
				+ "   and t.regtime <= to_date(?, 'yyyy-MM-dd hh24:mi:ss')"
				+ "   group by d.name, to_char(t.regtime, 'yyyyMM')"
				+ " ) g"
				+ " pivot (sum(g.cnt) for regtime in (");
		for (int i = 0; i < queryMonthForList.size(); i++) {
			sql.append(queryMonthForList.get(i).get("regtime"));
			if (i < queryMonthForList.size() - 1) {
				sql.append(", ");
			}
		}
		sql.append(")))z");
		
		if (StringUtils.isNotEmpty(inftype)) {
			params.add(inftype);
		}
		params.add(beginTime + " 00:00:00");
		params.add(endTime + " 23:59:59");
		
		List<Map> queryForList = dao.queryForList(sql.toString(), params);
		
		result.put("month", queryMonthForList);
		result.put("query", queryForList);
		logop.logInfoYeWu("来源方式分派数年度月份对比统计报表", "WDY", opreationtype == 1 ? "查看报表" : "下载报表", sql.toString(),
				beginTime + "," + endTime + "," + inftype, req);
		return result;
	}

	/**
	 * 查询
	 * 	  --接收方式分派数年度月份对比统计报表
	 * @param beginTime
	 * @param endTime
	 * @param inftype
	 * @param opreationtype
	 * @param req
	 * @return
	 * @throws OptimusException 
	 */
	public Map<String, List<Map>> findReceviceModeForMap(String beginTime, String endTime, String inftype, int opreationtype,
			HttpServletRequest req) throws OptimusException {
		StringBuffer sql = new StringBuffer();
		List<String> params = new ArrayList<String>();
		List<String> queryMonthParams = new ArrayList<String>();
		Map<String, List<Map>> result = new HashMap<String, List<Map>>();
		// 查询月份信息
		String queryMonthSql = "SELECT TO_CHAR(T.REGTIME, 'YYYYMM') AS regtime"
				+ "  FROM DC_CPR_INFOWARE T"
				+ " WHERE T.REGTIME >= TO_DATE(?, 'YYYY-MM-DD hh24:mi:ss')"
				+ "   AND T.REGTIME <= TO_DATE(?, 'YYYY-MM-DD hh24:mi:ss')"
				+ " GROUP BY TO_CHAR(T.REGTIME, 'YYYYMM')"
				+ " ORDER BY  TO_CHAR(T.REGTIME, 'YYYYMM')";
		queryMonthParams.add(beginTime + " 00:00:00");
		queryMonthParams.add(endTime + " 23:59:59");
		List<Map> queryMonthForList = dao.queryForList(queryMonthSql, queryMonthParams);
		if (queryMonthForList == null || queryMonthForList.size() == 0) {
			return null;
		}
		// 查询统计信息sql 
		sql.append("select z.name as name");
		for (Map map : queryMonthForList) {
			sql.append(", nvl(z.\"" + map.get("regtime") + "\", 0) \"" + map.get("regtime") + "\"");
		}
		sql.append(", sum(0");
		for (Map map : queryMonthForList) {
			sql.append(" + nvl(z.\"" + map.get("regtime") + "\", 0)");
		}
		sql.append(") over(partition by z.name ) num"
				+ "  from ("
				+ " select * from ("
				+ " select d.name , to_char(t.regtime, 'yyyyMM') as regtime, count(1) as cnt"
				+ "   from dc_cpr_infoware t, dc_code.dc_12315_codedata d, dc_cpr_process_instance f"
				+ "  where t.incform = d.code"
				+ "   and t.proinsid = f.proinsid"
				+ "   and f.statename <> '预登记'"
				+ "   and d.codetable = 'CH03'");
		if (StringUtils.isNotEmpty(inftype)) {
			sql.append("  and t.inftype = ?");
		}
		sql.append("   and t.regtime >= to_date(?, 'yyyy-MM-dd hh24:mi:ss') "
				+ "   and t.regtime <= to_date(?, 'yyyy-MM-dd hh24:mi:ss')"
				+ "   group by d.name, to_char(t.regtime, 'yyyyMM')"
				+ " ) g"
				+ " pivot (sum(g.cnt) for regtime in (");
		for (int i = 0; i < queryMonthForList.size(); i++) {
			sql.append(queryMonthForList.get(i).get("regtime"));
			if (i < queryMonthForList.size() - 1) {
				sql.append(",");
			}
		}
		sql.append(")))z");
		
		if (StringUtils.isNotEmpty(inftype)) {
			params.add(inftype);
		}
		params.add(beginTime + " 00:00:00");
		params.add(endTime + " 23:59:59");
		
		List<Map> queryForList = dao.queryForList(sql.toString(), params);
		
		result.put("month", queryMonthForList);
		result.put("query", queryForList);
		logop.logInfoYeWu("接受方式分派数年度月份对比统计报表", "WDY", opreationtype == 1 ? "查看报表" : "下载报表", sql.toString(),
				beginTime + "," + endTime + "," + inftype, req);
		return result;
	}

	/**
	 * 查询
	 * 	  --行业类型分派数年度月份对比统计报表 
	 * @param beginTime
	 * @param endTime
	 * @param inftype
	 * @param opreationtype
	 * @param req
	 * @return
	 * @throws OptimusException 
	 */
	public Map<String, List<Map>> findIndustryTypeForMap(String beginTime, String endTime, String inftype, int opreationtype,
			HttpServletRequest req) throws OptimusException {
		StringBuffer sql = new StringBuffer();
		List<String> params = new ArrayList<String>();
		List<String> queryMonthParams = new ArrayList<String>();
		Map<String, List<Map>> result = new HashMap<String, List<Map>>();
		// 查询月份信息sql
		String queryMonthSql = "SELECT TO_CHAR(T.REGTIME, 'YYYYMM') AS regtime"
				+ "  FROM DC_CPR_INFOWARE T"
				+ " WHERE T.REGTIME >= TO_DATE(?, 'YYYY-MM-DD hh24:mi:ss')"
				+ "   AND T.REGTIME <= TO_DATE(?, 'YYYY-MM-DD hh24:mi:ss')"
				+ " GROUP BY TO_CHAR(T.REGTIME, 'YYYYMM')"
				+ " ORDER BY  TO_CHAR(T.REGTIME, 'YYYYMM')";
		queryMonthParams.add(beginTime + " 00:00:00");
		queryMonthParams.add(endTime + " 23:59:59");
		List<Map> queryMonthForList = dao.queryForList(queryMonthSql, queryMonthParams);
		if (queryMonthForList == null || queryMonthForList.size() == 0) {
			return null;
		}
		// 查询统计信息sql 
		sql.append(" select  z.regdepname as name");
		for (Map map : queryMonthForList) {
			sql.append(",nvl(z.\"" + map.get("regtime") + "\",0) \"" + map.get("regtime") + "\"");
		}
		sql.append(",sum(0");
		for (Map map : queryMonthForList) {
			sql.append(" + nvl(z.\"" + map.get("regtime") + "\",0)");
		}
		sql.append(") over (partition by z.regdepname ) num"
				+ " from ("
				+ "     select * from ("
				+ "         select m.tradetype parent_dep_code,"
				+ "         (case when m.tradetype='100' or substr(m.tradetype,2,2)<>'00' then '　'||d.name else d.name end )as regdepname, "
				+ "         to_char(t.regtime, 'yyyymm') as regtime, count(1) as cnt"
				+ "           from dc_cpr_infoware t, dc_code.dc_12315_codedata d,dc_dc.dc_cpr_involved_main m, dc_cpr_process_instance f"
				+ "          where  t.invmaiid=m.invmaiid(+)"
				+ "            and  m.tradetype=d.code(+)"
				+ "            and d.codetable='CH15' "
				+ "            and t.proinsid = f.proinsid(+)"
				+ "            and f.statename <> '预登记'");
		if (StringUtils.isNotEmpty(inftype)) {
			sql.append("  and t.inftype = ?");
		}
				
		sql.append("            and t.regtime >= to_date(?, 'yyyy-MM-dd hh24:mi:ss')"
				+ "            and t.regtime <= to_date(?, 'yyyy-MM-dd hh24:mi:ss')"
				+ "          group by d.name,m.tradetype, to_char(t.regtime, 'yyyymm')"
				+ "         union all"
				+ "         select d.parentcode parent_dep_code,d1.name as regdepname, to_char(t.regtime, 'yyyymm') as regtime, count(1) as cnt"
				+ "           from dc_cpr_infoware t,"
				+ "           (select name,code, parentcode from dc_code.dc_12315_codedata  where codetable='CH15'"
				+ "                   union all "
				+ "             select '销售' as name,'100' as code, '100' as parentcode from dual) d,"
				+ "             dc_code.dc_12315_codedata  d1, dc_dc.dc_cpr_involved_main m, dc_cpr_process_instance f"
				+ "            where t.invmaiid=m.invmaiid(+)"
				+ "            and  m.tradetype=d.code(+)"
				+ "            and d.parentcode = d1.code"
				+ "            and d1.codetable='CH15'"
				+ "            and t.proinsid = f.proinsid(+)"
				+ "            and f.statename <> '预登记'");
		if (StringUtils.isNotEmpty(inftype)) {
			sql.append("  and t.inftype = ?");
		}
				
		sql.append("            and t.regtime >= to_date(?, 'yyyy-MM-dd hh24:mi:ss')"
				+ "            and t.regtime <= to_date(?, 'yyyy-MM-dd hh24:mi:ss')   "
				+ "          group by  d.parentcode,d1.name, to_char(t.regtime, 'yyyymm')"
				+ "      ) t"
				+ "      pivot(sum(t.cnt) for  regtime in (");
		for (int i = 0; i < queryMonthForList.size(); i++) {
			sql.append(queryMonthForList.get(i).get("regtime"));
			if (i < queryMonthForList.size() - 1) {
				sql.append(", ");
			}
		}
		sql.append(")))z order by parent_dep_code, regdepname desc");
		
		if (StringUtils.isNotEmpty(inftype)) {
			params.add(inftype);
		}
		params.add(beginTime + " 00:00:00");
		params.add(endTime + " 23:59:59");
		
		if (StringUtils.isNotEmpty(inftype)) {
			params.add(inftype);
		}
		params.add(beginTime + " 00:00:00");
		params.add(endTime + " 23:59:59");
		
		List<Map> queryForList = dao.queryForList(sql.toString(), params);
		
		result.put("month", queryMonthForList);
		result.put("query", queryForList);
		logop.logInfoYeWu("行业类型分派数年度月份对比统计报表", "WDY", opreationtype == 1 ? "查看报表" : "下载报表", sql.toString(),
				beginTime + "," + endTime + "," + inftype, req);
		return result;
	}

	/**
	 * 查询
	 * 	  --问题性质分派数年度月份对比统计报表  
	 * @param beginTime
	 * @param endTime
	 * @param inftype
	 * @param opreationtype
	 * @param req
	 * @return
	 * @throws OptimusException 
	 */
	public Map<String, List<Map>> findIssueNatureForMap(String beginTime, String endTime, String inftype, int opreationtype,
			HttpServletRequest req) throws OptimusException {
		StringBuffer sql = new StringBuffer();
		List<String> params = new ArrayList<String>();
		List<String> queryMonthParams = new ArrayList<String>();
		Map<String, List<Map>> result = new HashMap<String, List<Map>>();
		// 查询月份信息sql
		String queryMonthSql = "SELECT TO_CHAR(T.REGTIME, 'YYYYMM') AS regtime"
				+ "  FROM DC_CPR_INFOWARE T"
				+ " WHERE T.REGTIME >= TO_DATE(?, 'YYYY-MM-DD hh24:mi:ss')"
				+ "   AND T.REGTIME <= TO_DATE(?, 'YYYY-MM-DD hh24:mi:ss')"
				+ " GROUP BY TO_CHAR(T.REGTIME, 'YYYYMM')"
				+ " ORDER BY  TO_CHAR(T.REGTIME, 'YYYYMM')";
		queryMonthParams.add(beginTime + " 00:00:00");
		queryMonthParams.add(endTime + " 23:59:59");
		List<Map> queryMonthForList = dao.queryForList(queryMonthSql, queryMonthParams);
		if (queryMonthForList == null || queryMonthForList.size() == 0) {
			return null;
		}
		// 查询统计信息sql 
		sql.append("SELECT Z.REGDEPNAME as name");
		for (Map map : queryMonthForList) {
			sql.append(", NVL(z.\"" + map.get("regtime") + "\", 0) \"" + map.get("regtime") + "\"");
		}
		sql.append(", SUM(0");
		for (Map map : queryMonthForList) {
			sql.append(" + NVL(z.\""+ map.get("regtime") +"\", 0)");
		}
		sql.append(") OVER(PARTITION BY Z.PARENT_DEP_CODE,Z.REGDEPNAME) NUM"
				+ " FROM ("
				+ "      SELECT * FROM ("
				+ "           SELECT D.CODE PARENT_DEP_CODE, '　' || D.NAME AS REGDEPNAME, TO_CHAR(T.REGTIME, 'YYYYMM') AS REGTIME, COUNT(1) AS CNT"
				+ "           FROM DC_CPR_INFOWARE T, (SELECT * FROM DC_CODE.DC_12315_CODEDATA WHERE CODETABLE = 'CH27' ) D, "
				+ "                dc_cpr_process_instance f"
				+ "          WHERE NVL(T.APPLBASQUE,'9900') = D.CODE"
				+ "             AND T.proinsid = f.proinsid"
				+ "             AND f.statename <> '预登记'");
		if (StringUtils.isNotEmpty(inftype)) {
			sql.append(" AND T.inftype = ?");
		}
		sql.append("            AND T.REGTIME >= TO_DATE(?, 'YYYY-MM-DD hh24:mi:ss')"
				+ "             AND T.REGTIME <= TO_DATE(?, 'YYYY-MM-DD hh24:mi:ss')"
				+ "          GROUP BY D.CODE, D.NAME, TO_CHAR(T.REGTIME, 'YYYYMM')"
				+ "         UNION ALL"
				+ "         SELECT D1.CODE PARENT_DEP_CODE, D1.NAME  AS REGDEPNAME, TO_CHAR(T.REGTIME, 'YYYYMM') AS REGTIME,  COUNT(1) AS CNT"
				+ "           FROM DC_CPR_INFOWARE T,"
				+ "                (SELECT *  FROM DC_CODE.DC_12315_CODEDATA WHERE CODETABLE = 'CH27') D,"
				+ "                (SELECT *  FROM DC_CODE.DC_12315_CODEDATA WHERE CODETABLE = 'CH27' AND PARENTCODE IN ('0','3')) D1,"
				+ "                dc_cpr_process_instance f"
				+ "          WHERE NVL(T.APPLBASQUE,'9900') = D.CODE"
				+ "            AND DECODE(D.PARENTCODE,'0',D.CODE,'3',D.CODE,D.PARENTCODE) = D1.CODE"
				+ "            AND T.proinsid = f.proinsid"
				+ "            AND f.statename <> '预登记'");
		if (StringUtils.isNotEmpty(inftype)) {
			sql.append(" AND T.inftype = ?");
		}
		sql.append("  AND T.REGTIME >= TO_DATE(?, 'YYYY-MM-DD hh24:mi:ss')"
				+ "   AND T.REGTIME <= TO_DATE(?, 'YYYY-MM-DD hh24:mi:ss')"
				+ "   GROUP BY D1.CODE, D1.NAME,TO_CHAR(T.REGTIME, 'YYYYMM') ) T PIVOT(SUM(T.CNT) FOR REGTIME IN(");
		for (int i = 0; i < queryMonthForList.size(); i++) {
			sql.append(queryMonthForList.get(i).get("regtime"));
			if (i < queryMonthForList.size() - 1) {
				sql.append(",");
			}
		}
		sql.append("))) Z ORDER BY PARENT_DEP_CODE , REGDEPNAME DESC ");
		
		// 参数
		if (StringUtils.isNotEmpty(inftype)) {
			params.add(inftype);
		}
		params.add(beginTime + " 00:00:00");
		params.add(endTime + " 23:59:59");
		
		if (StringUtils.isNotEmpty(inftype)) {
			params.add(inftype);
		}
		params.add(beginTime + " 00:00:00");
		params.add(endTime + " 23:59:59");
		
		List<Map> queryForList = dao.queryForList(sql.toString(), params);
		
		result.put("month", queryMonthForList);
		result.put("query", queryForList);
		logop.logInfoYeWu("问题统计分派数年度月份对比统计报表", "WDY", opreationtype == 1 ? "查看报表" : "下载报表", sql.toString(),
				beginTime + "," + endTime + "," + inftype, req);
		return result;
	}

	public Map<String, List<Map>> suoShuBuMen(String beginTime, String endTime,
			String infotype, int i, HttpServletRequest req) {
		List list=new ArrayList();
		List query=new ArrayList();
		List<Map> res1=new ArrayList<Map>();
		List<Map> res2=new ArrayList<Map>();
		StringBuffer sql=new StringBuffer();
		Map<String ,List<Map>> result=new HashMap<String,List<Map>>();
		StringBuffer sqlQuery=new StringBuffer();
		sql.append(
				"SELECT TO_CHAR(T.REGTIME, 'YYYYMM') AS A\n" +
						"  FROM DC_CPR_INFOWARE T\n" + 
						" WHERE T.REGTIME >= TO_DATE(?, 'YYYY-MM-DD hh24:mi:ss')\n" + 
						"   AND T.REGTIME <= TO_DATE(?, 'YYYY-MM-DD hh24:mi:ss')\n" + 
						" GROUP BY TO_CHAR(T.REGTIME, 'YYYYMM')\n" + 
						"ORDER BY  TO_CHAR(T.REGTIME, 'YYYYMM')");
		list.add(beginTime+" 00:00:00");
		list.add(endTime+" 23:59:59");
		try {
			res1=dao.queryForList(sql.toString(),list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		if (res1==null||res1.size()==0) {
			return null;
		}
		sqlQuery.append("select  z.regdepname,");
		for (int j = 0; j < res1.size(); j++) {
			sqlQuery.append("nvl(z.\""+res1.get(j).get("a")+"\",0) \""+res1.get(j).get("a")+"\" ,");
		}
		sqlQuery.append("sum(");
		for (int j = 0,jj= res1.size(); j <jj; j++) {
			if (jj==1||j==jj-1) {
				sqlQuery.append("nvl(z.\""+res1.get(j).get("a")+"\",0)");
			}else{
				sqlQuery.append("nvl(z.\""+res1.get(j).get("a")+"\",0)+");
			}
		}
		sqlQuery.append(") over (partition by z.regdepname ) num from ( \n");
		sqlQuery.append(
					"SELECT * FROM (\n" +
					"SELECT  nvl(s.actpardepname,s.actdepname) REGDEPNAME, TO_CHAR(T.REGTIME, 'YYYYMM') AS REGTIME, COUNT(1) AS CNT\n" + 
					"  FROM dc_cpr_infoware T,dc_CPR_PROCESS_INSTANCE e,dc_cpr_process_step s\n" + 
					" WHERE  t.PROINSID=e.PROINSID  and e.PROINSID=s.PROINSID\n" + 
					" and t.regtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
					"      and t.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
					"      and s.action='分派'");
		query.add(beginTime+" 00:00:00");
		query.add(endTime+" 23:59:59");
		if (infotype!=null&&infotype.length()!=0) {
			sqlQuery.append("and t.inftype=? \n");
			query.add(infotype.trim());
		}
		sqlQuery.append(
				"GROUP BY  nvl(s.actpardepname,s.actdepname), TO_CHAR(T.REGTIME, 'YYYYMM')\n" +
						" ) T\n" + 
						" PIVOT(SUM(T.CNT) FOR  REGTIME IN (");
		for (int j = 0,k=res1.size(); j < k; j++) {
			if (k==1||j==k-1) {
				sqlQuery.append(res1.get(j).get("a"));
			}else{
				sqlQuery.append(res1.get(j).get("a")+",");
			}
		}
		sqlQuery.append("))\n" +")z\n" + "ORDER BY num desc");
		try {
			res2=dao.queryForList(sqlQuery.toString(), query);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		result.put("order",res1);
		result.put("result",res2);
		logop.logInfoYeWu("所属部门分派数年度月份对比表", "WDY", i == 1 ? "查看报表" : "下载报表", sqlQuery.toString(),
				beginTime + "," + endTime+","+infotype, req);
		return result;
	}

	public Map<String, List<Map>> yeWuFanWei(String beginTime, String endTime,
			String inftype, int i, HttpServletRequest req) {
		List list=new ArrayList();
		List query=new ArrayList();
		List<Map> res1=new ArrayList<Map>();
		List<Map> res2=new ArrayList<Map>();
		StringBuffer sql=new StringBuffer();
		Map<String ,List<Map>> result=new HashMap<String,List<Map>>();
		StringBuffer sqlQuery=new StringBuffer();
		sql.append(
				"SELECT TO_CHAR(T.REGTIME, 'YYYYMM') AS A\n" +
						"  FROM DC_CPR_INFOWARE T\n" + 
						" WHERE T.REGTIME >= TO_DATE(?, 'YYYY-MM-DD hh24:mi:ss')\n" + 
						"   AND T.REGTIME <= TO_DATE(?, 'YYYY-MM-DD hh24:mi:ss')\n" + 
						" GROUP BY TO_CHAR(T.REGTIME, 'YYYYMM')\n" + 
						"ORDER BY  TO_CHAR(T.REGTIME, 'YYYYMM')");
		list.add(beginTime+" 00:00:00");
		list.add(endTime+" 23:59:59");
		try {
			res1=dao.queryForList(sql.toString(),list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		if (res1==null||res1.size()==0) {
			return null;
		}
		sqlQuery.append("select  z.regdepname,");
		for (int j = 0; j < res1.size(); j++) {
			sqlQuery.append("nvl(z.\""+res1.get(j).get("a")+"\",0) \""+res1.get(j).get("a")+"\" ,");
		}
		sqlQuery.append("sum(");
		for (int j = 0,jj= res1.size(); j <jj; j++) {
			if (jj==1||j==jj-1) {
				sqlQuery.append("nvl(z.\""+res1.get(j).get("a")+"\",0)");
			}else{
				sqlQuery.append("nvl(z.\""+res1.get(j).get("a")+"\",0)+");
			}
		}
		sqlQuery.append(") over (partition by z.regdepname ) num from ( \n");
		sqlQuery.append(
				"\n" +
						"SELECT * FROM (\n" + 
						"SELECT  (case when o.BUSINESSTYPE ='CH20' THEN '工商'\n" + 
						"             when o.BUSINESSTYPE ='ZH18' then '消委会'\n" + 
						"             when  o.BUSINESSTYPE ='ZH19'  then '知识产权'\n" + 
						"             when o.BUSINESSTYPE ='ZH20' then '价格检查'\n" + 
						"             when o.BUSINESSTYPE ='ZH21'  then '质量监督'\n" + 
						"             else '其他' end) REGDEPNAME, TO_CHAR(T.REGTIME, 'YYYYMM') AS REGTIME, COUNT(1) AS CNT\n" + 
						"  FROM DC_CPR_INFOWARE T,dc_cpr_involved_object o,dc_CPR_PROCESS_INSTANCE e,dc_cpr_process_step s\n" + 
						" WHERE  t.INVOBJID=o.INVOBJID and t.PROINSID=e.PROINSID  and e.PROINSID=s.PROINSID\n" + 
						"     and t.regtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"     and t.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"     and s.action='分派' \n");
		query.add(beginTime+" 00:00:00");
		query.add(endTime+" 23:59:59");
		if (inftype!=null&&inftype.length()!=0) {
			sqlQuery.append("and t.inftype=? \n");
			query.add(inftype.trim());
		}
		sqlQuery.append(
				"GROUP BY o.BUSINESSTYPE, TO_CHAR(T.REGTIME, 'YYYYMM')\n" +
						" ) T\n" + 
						" PIVOT(SUM(T.CNT) FOR  REGTIME IN ( ");
		for (int j = 0,k=res1.size(); j < k; j++) {
			if (k==1||j==k-1) {
				sqlQuery.append(res1.get(j).get("a"));
			}else{
				sqlQuery.append(res1.get(j).get("a")+",");
			}
		}
		sqlQuery.append("))\n" +")z\n" + "ORDER BY num desc");
		try {
			res2=dao.queryForList(sqlQuery.toString(), query);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		result.put("order",res1);
		result.put("result",res2);
		logop.logInfoYeWu("业务范围分派数年度月份对比表", "WDY", i == 1 ? "查看报表" : "下载报表", sqlQuery.toString(),
				beginTime + "," + endTime+","+inftype, req);
		return result;
	}

	public Map<String, List<List<Map>>> sheJiKeTi(String beginTime,
			String endTime, String infotype, String shejiketi, int i,
			HttpServletRequest req) {
		List list=new ArrayList();
		List query=new ArrayList();
		List<Map> res1=new ArrayList<Map>();
		List<List<Map>> res2=new ArrayList<List<Map>>();//存放数据库查询到的数据
		List<List<Map>> results=new ArrayList<List<Map>>();//存放数据库查询到的数据
		StringBuffer sql=new StringBuffer();
		Map<String, List<List<Map>>> result=new HashMap<String,List<List<Map>>>();  //返回值
		sql.append(
				"SELECT TO_CHAR(T.REGTIME, 'YYYYMM') AS A\n" +
						"  FROM DC_CPR_INFOWARE T\n" + 
						" WHERE T.REGTIME >= TO_DATE(?, 'YYYY-MM-DD hh24:mi:ss')\n" + 
						"   AND T.REGTIME <= TO_DATE(?, 'YYYY-MM-DD hh24:mi:ss')\n" + 
						" GROUP BY TO_CHAR(T.REGTIME, 'YYYYMM')\n" + 
						"ORDER BY  TO_CHAR(T.REGTIME, 'YYYYMM')");
		list.add(beginTime+" 00:00:00");
		list.add(endTime+" 23:59:59");
		try {
			res1=dao.queryForList(sql.toString(),list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		if (res1==null||res1.size()==0) {
			return null;
		}
		query.add(beginTime+" 00:00:00");
		query.add(endTime+" 23:59:59");
		if (infotype!=null&&infotype.length()!=0) {
			query.add(infotype);
		}
		String [] str=this.getSqlByObj( infotype, shejiketi,res1);
		try {
			for (int j = 0; j < str.length; j++) {
				results.add(dao.queryForList(str[j], query));
			}
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		
		res2.add(res1);
		result.put("order",res2);
		result.put("result",results);
		return result;
	}
	
	
	private  String[] getSqlByObj(String infotype,String shejiketi, List<Map> res1 ){
		String[] str;
		if (shejiketi != null && "CH20".equals(shejiketi)) {
			str=new String[]{this.getGongShangSql(infotype,res1)/*sqlGongShang*/};
		} else if (shejiketi != null && "ZH18".equals(shejiketi)) {
			str=new String[]{this.getXiaoWeiHuiSql(infotype,res1)/*sqlXiaoWeiHui*/};
		} else if (shejiketi != null && "ZH19".equals(shejiketi)) {
			str=new String[]{this.getZhiShiChanQuanSql(infotype,res1)/*sqlZhiShiChanQuan*/};
		} else if (shejiketi != null && "ZH20".equals(shejiketi)) {
			str=new String[]{this.getJiaGeJianChaSql(infotype,res1)/*sqlJiaGeJianCha*/};
		} else if (shejiketi != null && "ZH21".equals(shejiketi)) {
			str=new String[]{this.getZhiLiangJianDuSql(infotype,res1)/*sqlZhiLianJianDu*/};
		} else {
			str=new String[]{this.getGongShangSql(infotype,res1),
					this.getXiaoWeiHuiSql(infotype,res1),
					this.getZhiShiChanQuanSql(infotype,res1),
					this.getJiaGeJianChaSql(infotype,res1),
					this.getZhiLiangJianDuSql(infotype,res1)};
		}
		return str;
	}
	
	
	private String getZhiLiangJianDuSql(String infotype, List<Map> res1) {
		StringBuffer sqlQuery=new StringBuffer();
		sqlQuery.append("select  nvl(z.name,'　') regdepname,");
		for (int j = 0; j < res1.size(); j++) {
			sqlQuery.append("nvl(z.\""+res1.get(j).get("a")+"\",0) \""+res1.get(j).get("a")+"\" ,");
		}
		sqlQuery.append("sum(");
		for (int j = 0,jj= res1.size(); j <jj; j++) {
			if (jj==1||j==jj-1) {
				sqlQuery.append("nvl(z.\""+res1.get(j).get("a")+"\",0)");
			}else{
				sqlQuery.append("nvl(z.\""+res1.get(j).get("a")+"\",0)+");
			}
		}
		sqlQuery.append(") over (partition by z.name ) num from ( \n");
		sqlQuery.append(
				"select * from (\n" +
						"select distinct y.INVOBJTYPE,z.name,y.regtime,y.num from (\n" + 
						"with x as\n" + 
						" (select o.INVOBJTYPE,t.regtime\n" + 
						"from DC_CPR_INFOWARE T,dc_cpr_involved_object o,dc_CPR_PROCESS_INSTANCE e,dc_cpr_process_step s\n" +
						"    WHERE  t.INVOBJID=o.INVOBJID and t.PROINSID=e.PROINSID  and e.PROINSID=s.PROINSID\n" + 
						"   --and o.INVOBJTYPE is not null\n" + 
						"   and s.action='分派'\n" + 
						"   and o.BUSINESSTYPE = 'ZH21'\n" + 
						"   AND LENGTH(O.INVOBJTYPE)=10 \n"+
						"     and t.regtime >=\n" + 
						"         to_date(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"     and t.regtime <=\n" + 
						"         to_date(?, 'yyyy-mm-dd hh24:mi:ss') \n");
		if (infotype!=null&&infotype.length()!=0) {
			sqlQuery.append("and t.inftype=? \n");
		}
		sqlQuery.append(
				")\n" +
						"select '00质量监督' as invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,count(1)num from x group by TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						"union all\n" + 
						"select x.invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,count(1) num from x  group by x.invobjtype ,TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,2)||'00000000' invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,count(1) num from x group by substr(x.invobjtype,0,2),TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						"UNION ALL\n" + 
						"select substr(x.invobjtype,0,4)||'000000' invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,count(1) num from x group by substr(x.invobjtype,0,4),TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						"UNION ALL\n" + 
						"select substr(x.invobjtype,0,6)||'0000' invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,count(1) num from x group by substr(x.invobjtype,0,6),TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						"UNION ALL\n" + 
						"select substr(x.invobjtype,0,8)||'00' invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,count(1) num from x group by substr(x.invobjtype,0,8),TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						"\n" + 
						") y\n" + 
						"left join\n" + 
						"(select code , (case when substr(code,9,2)<>'00'then '　　　　'||name\n" + 
						"      when substr(code,7,4)<>'0000'then '　　　'||name\n" + 
						"      when substr(code,5,6)<>'000000'then '　　'||name\n" + 
						"      when substr(code,3,8)<>'00000000'then '　'||name\n" + 
						"      else name end) name  from dc_code.dc_12315_codedata where codetable='ZH21'\n" + 
						"       union all\n" + 
						"      select '00质量监督' code ,'质量监督'name from dual) z\n" + 
						" on y.INVOBJTYPE=z.code\n" + 
						" order by y.invobjtype)yy\n" + 
						" PIVOT(SUM(nvl(yy.num,0)) FOR  REGTIME IN (");
		for (int j = 0,k=res1.size(); j < k; j++) {
			if (k==1||j==k-1) {
				sqlQuery.append(res1.get(j).get("a"));
			}else{
				sqlQuery.append(res1.get(j).get("a")+",");
			}
		}
		sqlQuery.append("))\n" +" )z\n" + " order by z.invobjtype");
		return sqlQuery.toString();
	}
	
	private String getJiaGeJianChaSql(String infotype, List<Map> res1) {
		StringBuffer sqlQuery=new StringBuffer();
		sqlQuery.append("select  nvl(z.name,'　') regdepname,");
		for (int j = 0; j < res1.size(); j++) {
			sqlQuery.append("nvl(z.\""+res1.get(j).get("a")+"\",0) \""+res1.get(j).get("a")+"\" ,");
		}
		sqlQuery.append("sum(");
		for (int j = 0,jj= res1.size(); j <jj; j++) {
			if (jj==1||j==jj-1) {
				sqlQuery.append("nvl(z.\""+res1.get(j).get("a")+"\",0)");
			}else{
				sqlQuery.append("nvl(z.\""+res1.get(j).get("a")+"\",0)+");
			}
		}
		sqlQuery.append(") over (partition by z.name ) num from ( \n");
		sqlQuery.append(
				"select * from (\n" +
						"select distinct y.INVOBJTYPE,z.name,y.regtime,y.num from (\n" + 
						"with x as\n" + 
						" (select o.INVOBJTYPE,t.regtime\n" + 
						"from DC_CPR_INFOWARE T,dc_cpr_involved_object o,dc_CPR_PROCESS_INSTANCE e,dc_cpr_process_step s\n" +
						"    WHERE  t.INVOBJID=o.INVOBJID and t.PROINSID=e.PROINSID  and e.PROINSID=s.PROINSID\n" + 
						"   --and o.INVOBJTYPE is not null\n" + 
						"   and s.action='分派'\n" + 
						"     and o.BUSINESSTYPE = 'ZH20'\n" + 
						"     AND LENGTH(O.INVOBJTYPE)=4 \n"+
						"     and t.regtime >=\n" + 
						"         to_date(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"     and t.regtime <=\n" + 
						"         to_date(?, 'yyyy-mm-dd hh24:mi:ss')");
		if (infotype!=null&&infotype.length()!=0) {
			sqlQuery.append("and t.inftype=? \n");
		}
		sqlQuery.append(" )\n" +
						"select '00价格检查' as invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,count(1)num from x group by TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						"union all\n" + 
						"select x.invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,count(1) num from x  group by x.invobjtype ,TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,2)||'00' invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,count(1) from x group by substr(x.invobjtype,0,2),TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						") y\n" + 
						"left join\n" + 
						"(select code , (case when substr(code,3,2)<>'00'then '　'||name\n" + 
						"      else name end) name  from dc_code.dc_12315_codedata where codetable='ZH20'\n" + 
						"      union all\n" + 
						"      select '00价格检查' code ,'价格检查'name from dual) z\n" + 
						" on y.INVOBJTYPE=z.code\n" + 
						" order by y.invobjtype)yy\n" + 
						" PIVOT(SUM(nvl(yy.num,0)) FOR  REGTIME IN (");
		for (int j = 0,k=res1.size(); j < k; j++) {
			if (k==1||j==k-1) {
				sqlQuery.append(res1.get(j).get("a"));
			}else{
				sqlQuery.append(res1.get(j).get("a")+",");
			}
		}
		sqlQuery.append("))\n" +" )z\n" + " order by z.invobjtype");
		return sqlQuery.toString();
	}
	
	private String getZhiShiChanQuanSql(String infotype, List<Map> res1) {
		StringBuffer sqlQuery=new StringBuffer();
		sqlQuery.append("select  nvl(z.name,'　') regdepname,");
		for (int j = 0; j < res1.size(); j++) {
			sqlQuery.append("nvl(z.\""+res1.get(j).get("a")+"\",0) \""+res1.get(j).get("a")+"\" ,");
		}
		sqlQuery.append("sum(");
		for (int j = 0,jj= res1.size(); j <jj; j++) {
			if (jj==1||j==jj-1) {
				sqlQuery.append("nvl(z.\""+res1.get(j).get("a")+"\",0)");
			}else{
				sqlQuery.append("nvl(z.\""+res1.get(j).get("a")+"\",0)+");
			}
		}
		sqlQuery.append(") over (partition by z.name ) num from ( \n");
		sqlQuery.append(
				"select * from (\n" +
						"select distinct y.INVOBJTYPE,z.name,y.num,y.regtime from (\n" + 
						"with x as\n" + 
						" (select o.INVOBJTYPE,t.regtime\n" + 
						"from  DC_CPR_INFOWARE T,dc_cpr_involved_object o,dc_CPR_PROCESS_INSTANCE e,dc_cpr_process_step s\n" +
						"    WHERE  t.INVOBJID=o.INVOBJID and t.PROINSID=e.PROINSID  and e.PROINSID=s.PROINSID\n" + 
						"   --and o.INVOBJTYPE is not null\n" + 
						"   and s.action='分派'\n" + 
						"   and o.BUSINESSTYPE = 'ZH19'\n" + 
						"   AND LENGTH(O.INVOBJTYPE)=3 \n"+
						"     and t.regtime >=\n" + 
						"         to_date(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"     and t.regtime <=\n" + 
						"         to_date(?, 'yyyy-mm-dd hh24:mi:ss') \n");
		if (infotype!=null&&infotype.length()!=0) {
			sqlQuery.append("and t.inftype=? \n");
		}
		sqlQuery.append(
				" )\n" +
						"select '00知识产权' as invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,count(1)num from x group by TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						"union all\n" + 
						"select x.invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,count(1) num from x  group by x.invobjtype ,TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,1)||'00' invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,count(1) num from x group by substr(x.invobjtype,0,1),TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						"\n" + 
						") y\n" + 
						"left join\n" + 
						"(select code , (case when substr(code,2,2)<>'00'then '　'||name\n" + 
						"      else name end) name  from dc_code.dc_12315_codedata where codetable='ZH19'\n" + 
						"      union all\n" + 
						"      select '00知识产权' code ,'知识产权'name from dual) z\n" + 
						" on y.INVOBJTYPE=z.code\n" + 
						" order by y.invobjtype)yy\n" + 
						" PIVOT(SUM(nvl(yy.num,0)) FOR  REGTIME IN (");
		for (int j = 0,k=res1.size(); j < k; j++) {
			if (k==1||j==k-1) {
				sqlQuery.append(res1.get(j).get("a"));
			}else{
				sqlQuery.append(res1.get(j).get("a")+",");
			}
		}
		sqlQuery.append("))\n" +" )z\n" +" order by z.invobjtype");
		return sqlQuery.toString();
	}

	
	private String getXiaoWeiHuiSql(String infotype, List<Map> res1) {
		StringBuffer sqlQuery=new StringBuffer();
		sqlQuery.append("select  nvl(z.name,'　') regdepname,");
		for (int j = 0; j < res1.size(); j++) {
			sqlQuery.append("nvl(z.\""+res1.get(j).get("a")+"\",0) \""+res1.get(j).get("a")+"\" ,");
		}
		sqlQuery.append("sum(");
		for (int j = 0,jj= res1.size(); j <jj; j++) {
			if (jj==1||j==jj-1) {
				sqlQuery.append("nvl(z.\""+res1.get(j).get("a")+"\",0)");
			}else{
				sqlQuery.append("nvl(z.\""+res1.get(j).get("a")+"\",0)+");
			}
		}
		sqlQuery.append(") over (partition by z.name ) num from ( \n");
		sqlQuery.append(
						"select * from (\n" +
						"select distinct y.INVOBJTYPE,z.name,y.regtime,y.num from (\n" + 
						"with x as\n" + 
						" (select o.INVOBJTYPE,t.regtime\n" + 
						"from  DC_CPR_INFOWARE T,dc_cpr_involved_object o,dc_CPR_PROCESS_INSTANCE e,dc_cpr_process_step s\n" +
						"    WHERE  t.INVOBJID=o.INVOBJID and t.PROINSID=e.PROINSID  and e.PROINSID=s.PROINSID\n" + 
						"   and o.BUSINESSTYPE = 'ZH18'\n" + 
						"   and s.action='分派'\n" + 
						"   AND LENGTH(O.INVOBJTYPE)=10 \n"+
						"     and t.regtime >=\n" + 
						"         to_date(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"     and t.regtime <=\n" + 
						"         to_date(?, 'yyyy-mm-dd hh24:mi:ss') \n");
		if (infotype!=null&&infotype.length()!=0) {
			sqlQuery.append("and t.inftype=? \n");
		}
		sqlQuery.append(
				")\n" +
						"select '00消委会' as invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,count(1)num from x group by TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						"union all\n" + 
						"select x.invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,count(1) num from x  group by TO_CHAR(x.REGTIME, 'YYYYMM'),x.invobjtype\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,2)||'00000000' invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,count(1) num from x group by TO_CHAR(x.REGTIME, 'YYYYMM') ,substr(x.invobjtype,0,2)\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,4)||'000000' invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,count(1) num from x  group by TO_CHAR(x.REGTIME, 'YYYYMM'),substr(x.invobjtype,0,4)\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,6)||'0000' invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,count(1) num  from x  group by TO_CHAR(x.REGTIME, 'YYYYMM') , substr(x.invobjtype,0,6)\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,8)||'00' invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,count(1) num  from x  group by TO_CHAR(x.REGTIME, 'YYYYMM'),substr(x.invobjtype,0,8)\n" + 
						"\n" + 
						") y\n" + 
						"left join\n" + 
						"(select code , (case when substr(code,9,2)<>'00'then '　　　　'||name\n" + 
						"      when substr(code,7,4)<>'0000'then '　　　'||name\n" + 
						"      when substr(code,5,6)<>'000000'then '　　'||name\n" + 
						"      when substr(code,3,8)<>'00000000'then '　'||name\n" + 
						"      else name end) name  from dc_code.dc_12315_codedata where codetable='ZH18'\n" + 
						"      union all\n" + 
						"      select '00消委会' code ,'消委会'name from dual) z\n" + 
						" on y.INVOBJTYPE=z.code\n" + 
						" order by y.invobjtype)yy\n" + 
						" PIVOT(SUM(nvl(yy.num,0)) FOR  REGTIME IN (");
		for (int j = 0,k=res1.size(); j < k; j++) {
			if (k==1||j==k-1) {
				sqlQuery.append(res1.get(j).get("a"));
			}else{
				sqlQuery.append(res1.get(j).get("a")+",");
			}
		}
		sqlQuery.append("))\n" +" )z\n" + " order by z.invobjtype");
		return sqlQuery.toString();
	}
	
	
	private String getGongShangSql(String infotype, List<Map> res1) {
		StringBuffer sqlQuery=new StringBuffer();
		sqlQuery.append("select  nvl(z.name,'　') regdepname,");
		for (int j = 0; j < res1.size(); j++) {
			sqlQuery.append("nvl(z.\""+res1.get(j).get("a")+"\",0) \""+res1.get(j).get("a")+"\" ,");
		}
		sqlQuery.append("sum(");
		for (int j = 0,jj= res1.size(); j <jj; j++) {
			if (jj==1||j==jj-1) {
				sqlQuery.append("nvl(z.\""+res1.get(j).get("a")+"\",0)");
			}else{
				sqlQuery.append("nvl(z.\""+res1.get(j).get("a")+"\",0)+");
			}
		}
		sqlQuery.append(") over (partition by z.name ) num from ( \n");
		sqlQuery.append("select * from (\n" +
						"select distinct y.INVOBJTYPE,z.name,y.REGTIME,y.num from (\n" + 
						"with x as\n" + 
						" (select o.INVOBJTYPE,t.regtime\n" + 
						" from DC_CPR_INFOWARE T,dc_cpr_involved_object o,dc_CPR_PROCESS_INSTANCE e,dc_cpr_process_step s\n" +
						"    WHERE  t.INVOBJID=o.INVOBJID and t.PROINSID=e.PROINSID  and e.PROINSID=s.PROINSID\n" + 
						"    and o.BUSINESSTYPE = 'CH20'\n" + 
						"    and s.action='分派'\n" + 
						"    AND LENGTH(O.INVOBJTYPE)=8 \n"+
						"     and t.regtime >=\n" + 
						"         to_date(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"     and t.regtime <=\n" + 
						"         to_date(?, 'yyyy-mm-dd hh24:mi:ss') \n");
		if (infotype!=null&&infotype.length()!=0) {
			sqlQuery.append("and t.inftype=? \n");
		}
		sqlQuery.append(" )\n" +
						"select '00工商' as invobjtype, TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,count(1)num from x group by TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						"union all\n" + 
						"select x.invobjtype, TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,count(1) num from x  group by x.invobjtype ,TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,3)||'00000' invobjtype, TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,count(1)num from x group by substr(x.invobjtype,0,3),TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,5)||'000' invobjtype, TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,count(1)num  from x  group by substr(x.invobjtype,0,5),TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						") y\n" + 
						"left join\n" + 
						"(select code , (\n" + 
						" case when substr(code,7,2)<>'00' then '　　　　'||name\n" + 
						"      when substr(code,6,3)<>'000'   then '　　　'||name\n" + 
						"      when substr(code,4,5)<>'00000' or substr(code,6,3)='900' then '　　'||name\n" + 
						"      when substr(code,2,7)<>'0000000'then '　'||name\n" + 
						"      else name end) name  from dc_code.dc_12315_codedata where codetable='CH20'\n" + 
						"      union all\n" + 
						"      select '00工商' code,'工商' name from dual) z\n" + 
						" on y.INVOBJTYPE=z.code)yy\n" + 
						" PIVOT(SUM(nvl(yy.num,0)) FOR  REGTIME IN (");
		for (int j = 0,k=res1.size(); j < k; j++) {
			if (k==1||j==k-1) {
				sqlQuery.append(res1.get(j).get("a"));
			}else{
				sqlQuery.append(res1.get(j).get("a")+",");
			}
		}
		sqlQuery.append("))\n" +")z\n" + "order by z.invobjtype");
		return sqlQuery.toString();
	}
}	