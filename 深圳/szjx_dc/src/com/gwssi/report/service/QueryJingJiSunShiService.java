package com.gwssi.report.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.report.balance.api.ReportModel;
import com.gwssi.report.balance.api.ReportSource;
import com.gwssi.report.model.TCognosReportBO;
import com.gwssi.report.util.LogOperation;
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service
public class QueryJingJiSunShiService extends BaseService implements ReportSource{

	IPersistenceDAO dao = this.getPersistenceDAO("dc_dc");
	LogOperation logop = new LogOperation();
	static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public ReportModel getReport(TCognosReportBO queryInfo) {
		return null;
	}
	
	private List<Map> getMonth(String beginTime, String endTime, String infotype) {
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer();
		List<Map> res1 = new ArrayList<Map>();
		sql.append("SELECT TO_CHAR(T.REGTIME, 'YYYYMM') AS A\n"
				+ "  FROM DC_CPR_INFOWARE T\n"
				+ " WHERE T.REGTIME >= TO_DATE(?, 'YYYY-MM-DD hh24:mi:ss')\n"
				+ "   AND T.REGTIME <= TO_DATE(?, 'YYYY-MM-DD hh24:mi:ss')\n");
		list.add(beginTime + " 00:00:00");
		list.add(endTime + " 23:59:59");
		if (infotype != null && infotype.length() != 0) {
			sql.append(" and t.Inftype=? \n");
			list.add(infotype);
		}
		sql.append(" GROUP BY TO_CHAR(T.REGTIME, 'YYYYMM')\n"
				+ "ORDER BY  TO_CHAR(T.REGTIME, 'YYYYMM')");
		try {
			res1 = dao.queryForList(sql.toString(), list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return res1;
	}
	
	private StringBuffer bufferUtil(List<Map> res1, StringBuffer sqlQuery) {
		for (int j = 0, k = res1.size(); j < k; j++) {
			if (k == 1 || j == k - 1) {
				sqlQuery.append(res1.get(j).get("a"));
			} else {
				sqlQuery.append(res1.get(j).get("a") + ",");
			}
		}
		return sqlQuery;
	}

	private StringBuffer bufferUtilB(List<Map> res1,StringBuffer sqlQuery){
		sqlQuery.append("select  /*+ parallel(8)*/  nvl(z.regdepname,'???') regdepname,");
		for (int j = 0; j < res1.size(); j++) {
			sqlQuery.append("nvl(z.\"" + res1.get(j).get("a") + "\",0) \""
					+ res1.get(j).get("a") + "\" ,");
		}
		sqlQuery.append("sum(");
		for (int j = 0, jj = res1.size(); j < jj; j++) {
			if (jj == 1 || j == jj - 1) {
				sqlQuery.append("nvl(z.\"" + res1.get(j).get("a") + "\",0)");
			} else {
				sqlQuery.append("nvl(z.\"" + res1.get(j).get("a") + "\",0)+");
			}
		}
		return sqlQuery;
	}
	
	private StringBuffer bufferUtilC(List<Map> res1,StringBuffer sqlQuery){
		sqlQuery.append(
				"select /*+ parallel(8)*/  nvl(z.name,Z.INVOBJTYPE) regdepname,");
		for (int j = 0; j < res1.size(); j++) {
			sqlQuery.append("nvl(z.\"" + res1.get(j).get("a") + "\",0) \""
					+ res1.get(j).get("a") + "\" ,");
		}
		sqlQuery.append("sum(");
		for (int j = 0, jj = res1.size(); j < jj; j++) {
			if (jj == 1 || j == jj - 1) {
				sqlQuery.append("nvl(z.\"" + res1.get(j).get("a") + "\",0)");
			} else {
				sqlQuery.append("nvl(z.\"" + res1.get(j).get("a") + "\",0)+");
			}
		}
		return sqlQuery;
	}
	

	public Map<String, List<Map>> dengJiBuMen(String beginTime, String endTime,
			String infotype, int i, HttpServletRequest req) {
		List<Map> res1 = this.getMonth(beginTime, endTime, infotype);
		List query = new ArrayList();
		List<Map> res2 = new ArrayList<Map>();
		Map<String, List<Map>> result = new HashMap<String, List<Map>>();
		StringBuffer sqlQuery = new StringBuffer();
		if (res1 == null || res1.size() == 0) {
			return null;
		}
		this.bufferUtilB(res1, sqlQuery);
		sqlQuery.append(
				") over (partition by z.regdepname,z.PARENT_DEP_CODE ) num\n" +
						" from (\n" + 
						"SELECT * FROM (\n" + 
						"SELECT D.PARENT_DEP_CODE,'???'||T.regdepname AS REGDEPNAME, TO_CHAR(T.REGTIME, 'YYYYMM') AS REGTIME,sum(nvl(cc.ECOLOVAL,0)) AS CNT\n" + 
						"  FROM DC_CPR_INFOWARE T, DC_JG_SYS_RIGHT_DEPARTMENT D,dc_dc.dc_cpr_feedback ff,dc_dc.dc_cpr_case_info cc\n" + 
						" WHERE T.REGDEPCODE = D.SYS_RIGHT_DEPARTMENT_ID\n" + 
						"  -- and t.feedbackid=f.feedbackid and f.caseinfoid=c.caseinfoid\n" + 
						"     and t.feedbackid=ff.feedbackid\n" + 
						"and ff.caseinfoid=cc.caseinfoid\n" + 
						"   AND T.REGTIME >= TO_DATE(?,'YYYY-MM-DD')\n" + 
						"   AND T.REGTIME <= TO_DATE(?,'YYYY-MM-DD hh24:mi:ss') \n");
		query.add(beginTime);
		query.add(endTime + " 23:59:59");
		if (infotype != null && infotype.length() != 0) {
			sqlQuery.append("and t.inftype=? \n");
			query.add(infotype.trim());
		}
		sqlQuery.append(
				" GROUP BY D.PARENT_DEP_CODE,T.REGDEPNAME, TO_CHAR(T.REGTIME, 'YYYYMM')\n" +
						"UNION ALL\n" + 
						"SELECT D.PARENT_DEP_CODE,D1.DEP_NAME AS REGDEPNAME, TO_CHAR(T.REGTIME, 'YYYYMM') AS REGTIME,sum(nvl(cc.ECOLOVAL,0))  AS CNT\n" + 
						"  FROM DC_CPR_INFOWARE T, DC_JG_SYS_RIGHT_DEPARTMENT D,DC_JG_SYS_RIGHT_DEPARTMENT D1,dc_dc.dc_cpr_feedback ff,dc_dc.dc_cpr_case_info cc\n" + 
						" WHERE T.REGDEPCODE = D.SYS_RIGHT_DEPARTMENT_ID\n" + 
						" and  t.feedbackid=ff.feedbackid\n" + 
						"and ff.caseinfoid=cc.caseinfoid\n" + 
						"   AND D.PARENT_DEP_CODE = D1.SYS_RIGHT_DEPARTMENT_ID\n" + 
						"   AND T.REGTIME >= TO_DATE(?,'YYYY-MM-DD')\n" + 
						"   AND T.REGTIME <= TO_DATE(?,'YYYY-MM-DD hh24:mi:ss') \n");
		query.add(beginTime);
		query.add(endTime + " 23:59:59");
		if (infotype != null && infotype.length() != 0) {
			sqlQuery.append("and t.inftype=? \n");
			query.add(infotype.trim());
		}
		sqlQuery.append(
				"GROUP BY D.PARENT_DEP_CODE,D1.DEP_NAME, TO_CHAR(T.REGTIME, 'YYYYMM')\n" +
						" ) T\n" + 
						" PIVOT(SUM(T.CNT) FOR  REGTIME IN (");
		this.bufferUtil(res1, sqlQuery);
		sqlQuery.append("))\n" +")z\n" + "ORDER BY PARENT_DEP_CODE/*,T.REGTIME*/ ,REGDEPNAME DESC");
		try {
			res2 = dao.queryForList(sqlQuery.toString(), query);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		result.put("order", res1);
		result.put("result", res2);
		logop.logInfoYeWu("?????????????????????????????????????????????????????????", "WDY", i == 1 ? "????????????" : "????????????",
				sqlQuery.toString(), beginTime + "," + endTime + "," + infotype, req);
		return result;
	}

	public Map<String, List<Map>> dengJiRen(String beginTime, String endTime,
			String infotype, String regcode, int i, HttpServletRequest req) {
		List<Map> res1 = this.getMonth(beginTime, endTime, infotype);
		List query = new ArrayList();
		List<Map> res2 = new ArrayList<Map>();
		Map<String, List<Map>> result = new HashMap<String, List<Map>>();
		StringBuffer sqlQuery = new StringBuffer();
		if (res1 == null || res1.size() == 0) {
			return null;
		}
		this.bufferUtilB(res1, sqlQuery);
		sqlQuery.append(
				") over (partition by z.regdepname ) num\n" +
						" from (\n" + 
						"SELECT * FROM (\n" + 
						"SELECT  t.REGPERSON REGDEPNAME, TO_CHAR(T.REGTIME, 'YYYYMM') AS REGTIME, sum(nvl(cc.ECOLOVAL,0)) AS CNT\n" + 
						"  FROM DC_CPR_INFOWARE T,dc_dc.dc_cpr_feedback ff,dc_dc.dc_cpr_case_info cc\n" + 
						" WHERE t.feedbackid=ff.feedbackid\n" + 
						"and ff.caseinfoid=cc.caseinfoid\n" + 
						"   and T.REGTIME >= TO_DATE(?,'YYYY-MM-DD')\n" + 
						"   AND T.REGTIME <= TO_DATE(?,'YYYY-MM-DD hh24:mi:ss') \n");
		query.add(beginTime);
		query.add(endTime + " 23:59:59");
		if (infotype != null && infotype.length() != 0) {
			sqlQuery.append("and t.inftype=? \n");
			query.add(infotype.trim());
		}
		if (regcode != null && regcode.length() != 0) {
			sqlQuery.append("and t.regdepcode= ? \n");
			query.add(regcode.trim());
		}
		sqlQuery.append(
				"GROUP BY T.REGPERSON, TO_CHAR(T.REGTIME, 'YYYYMM')\n" +
						" ) t PIVOT(SUM(T.CNT) FOR  REGTIME IN (");
		this.bufferUtil(res1, sqlQuery);
		sqlQuery.append("))\n" +"\n" + ")z\n" + "ORDER BY num desc");
		try {
			res2 = dao.queryForList(sqlQuery.toString(), query);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		result.put("order", res1);
		result.put("result", res2);
		logop.logInfoYeWu("??????????????????????????????????????????????????????", "WDY", i == 1 ? "????????????" : "????????????",
				sqlQuery.toString(), beginTime + "," + endTime + "," + infotype, req);
		return result;
	}

	public Map<String, List<Map>> chengBanBuMen(String beginTime,
			String endTime, String infotype, int i, HttpServletRequest req) {
		List<Map> res1 = this.getMonth(beginTime, endTime, infotype);
		List query = new ArrayList();
		List<Map> res2 = new ArrayList<Map>();
		Map<String, List<Map>> result = new HashMap<String, List<Map>>();
		StringBuffer sqlQuery = new StringBuffer();
		if (res1 == null || res1.size() == 0) {
			return null;
		}
		this.bufferUtilB(res1, sqlQuery);
		sqlQuery.append(
				") over (partition by z.PARENT_DEP_CODE???z.regdepname ) num\n" +
						" from (\n" + 
						"SELECT * FROM (\n" + 
						"SELECT D.PARENT_DEP_CODE,'???'||T.HANDEPNAME AS REGDEPNAME, TO_CHAR(T.REGTIME, 'YYYYMM') AS REGTIME, sum(nvl(cc.ECOLOVAL,0)) AS CNT\n" + 
						"  FROM DC_CPR_INFOWARE T, DC_JG_SYS_RIGHT_DEPARTMENT D,dc_dc.dc_cpr_feedback ff,dc_dc.dc_cpr_case_info cc\n" + 
						" WHERE t.feedbackid=ff.feedbackid\n" + 
						"   and ff.caseinfoid=cc.caseinfoid\n" + 
						"   and T.HANDEPCODE = D.SYS_RIGHT_DEPARTMENT_ID\n" + 
						"   AND T.REGTIME >= TO_DATE(?,'YYYY-MM-DD')\n" + 
						"   AND T.REGTIME <= TO_DATE(?,'YYYY-MM-DD hh24:mi:ss') \n");
		query.add(beginTime);
		query.add(endTime + " 23:59:59");
		if (infotype != null && infotype.length() != 0) {
			sqlQuery.append("and t.inftype=? \n");
			query.add(infotype.trim());
		}
		sqlQuery.append(
				"GROUP BY D.PARENT_DEP_CODE,T.HANDEPNAME, TO_CHAR(T.REGTIME, 'YYYYMM')\n" +
						"UNION ALL\n" + 
						"SELECT D.PARENT_DEP_CODE,D1.DEP_NAME AS REGDEPNAME, TO_CHAR(T.REGTIME, 'YYYYMM') AS REGTIME,sum(nvl(cc.ECOLOVAL,0)) AS CNT\n" + 
						"  FROM DC_CPR_INFOWARE T, DC_JG_SYS_RIGHT_DEPARTMENT D,DC_JG_SYS_RIGHT_DEPARTMENT D1,dc_dc.dc_cpr_feedback ff,dc_dc.dc_cpr_case_info cc\n" + 
						" WHERE t.feedbackid=ff.feedbackid\n" + 
						"   and ff.caseinfoid=cc.caseinfoid\n" + 
						"   and T.HANDEPCODE = D.SYS_RIGHT_DEPARTMENT_ID\n" + 
						"   AND D.PARENT_DEP_CODE = D1.SYS_RIGHT_DEPARTMENT_ID\n" + 
						"   AND T.REGTIME >= TO_DATE(?,'YYYY-MM-DD')\n" + 
						"   AND T.REGTIME <= TO_DATE(?,'YYYY-MM-DD hh24:mi:ss') \n");
		query.add(beginTime);
		query.add(endTime + " 23:59:59");
		if (infotype != null && infotype.length() != 0) {
			sqlQuery.append("and t.inftype=? \n");
			query.add(infotype.trim());
		}
		sqlQuery.append(
				"GROUP BY D.PARENT_DEP_CODE,D1.DEP_NAME, TO_CHAR(T.REGTIME, 'YYYYMM')\n" +
						" ) T\n" + 
						" PIVOT(SUM(T.CNT) FOR  REGTIME IN (");
		this.bufferUtil(res1, sqlQuery);
		sqlQuery.append("))\n" +")z\n" + "ORDER BY PARENT_DEP_CODE/*,T.REGTIME*/ ,REGDEPNAME DESC");
		try {
			res2 = dao.queryForList(sqlQuery.toString(), query);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		result.put("order", res1);
		result.put("result", res2);
		logop.logInfoYeWu("?????????????????????????????????????????????????????????", "WDY", i == 1 ? "????????????" : "????????????",
				sqlQuery.toString(), beginTime + "," + endTime + "," + infotype, req);
		return result;
	}

	public Map<String, List<Map>> chengBanRen(String beginTime, String endTime,
			String infotype, String regcode, int i, HttpServletRequest req) {
		List<Map> res1 = this.getMonth(beginTime, endTime, infotype);
		List query = new ArrayList();
		List<Map> res2 = new ArrayList<Map>();
		Map<String, List<Map>> result = new HashMap<String, List<Map>>();
		StringBuffer sqlQuery = new StringBuffer();
		if (res1 == null || res1.size() == 0) {
			return null;
		}
		this.bufferUtilB(res1, sqlQuery);
		sqlQuery.append(
				") over (partition by z.regdepname) num\n" +
						" from (\n" + 
						"SELECT * FROM (\n" + 
						"SELECT  t.HANDLE REGDEPNAME, TO_CHAR(T.REGTIME, 'YYYYMM') AS REGTIME, sum(nvl(cc.ECOLOVAL,0)) AS CNT\n" + 
						"  FROM DC_CPR_INFOWARE T,dc_dc.dc_cpr_feedback ff,dc_dc.dc_cpr_case_info cc\n" + 
						" WHERE t.feedbackid=ff.feedbackid\n" + 
						"and ff.caseinfoid=cc.caseinfoid\n" + 
						"   and   T.REGTIME >= TO_DATE(?,'YYYY-MM-DD')\n" + 
						"   AND T.REGTIME <= TO_DATE(?,'YYYY-MM-DD hh24:mi:ss') \n");
		query.add(beginTime);
		query.add(endTime + " 23:59:59");
		if (infotype != null && infotype.length() != 0) {
			sqlQuery.append("and t.inftype=? \n");
			query.add(infotype.trim());
		}
		if (regcode != null && regcode.length() != 0) {
			sqlQuery.append("and t.HANDEPCODE= ? \n");
			query.add(regcode.trim());
		}
		sqlQuery.append(
				"GROUP BY T.HANDLE, TO_CHAR(T.REGTIME, 'YYYYMM')\n" +
						") T\n" + 
						"PIVOT(SUM(T.CNT) FOR  REGTIME IN (");
		this.bufferUtil(res1, sqlQuery);
		sqlQuery.append("))\n" +"\n" + ")z\n" + "ORDER BY num desc");
		try {
			res2 = dao.queryForList(sqlQuery.toString(), query);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		result.put("order", res1);
		result.put("result", res2);
		logop.logInfoYeWu("??????????????????????????????????????????????????????", "WDY", i == 1 ? "????????????" : "????????????",
				sqlQuery.toString(), beginTime + "," + endTime + "," + infotype, req);
		return result;
	}

	public Map<String, List<Map>> laiYuanFangShi(String beginTime,
			String endTime, String infotype, int i, HttpServletRequest req) {
		List<Map> res1 = this.getMonth(beginTime, endTime, infotype);
		List query = new ArrayList();
		List<Map> res2 = new ArrayList<Map>();
		Map<String, List<Map>> result = new HashMap<String, List<Map>>();
		StringBuffer sqlQuery = new StringBuffer();
		if (res1 == null || res1.size() == 0) {
			return null;
		}
		this.bufferUtilB(res1, sqlQuery);
		sqlQuery.append(
				") over (partition by z.regdepname  ) num\n" +
						" from (\n" + 
						"SELECT * FROM (\n" + 
						"SELECT  /*+ parallel(8)*/ c.name REGDEPNAME, TO_CHAR(T.REGTIME, 'YYYYMM') AS REGTIME, sum(nvl(cc.ECOLOVAL,0)) AS CNT\n" + 
						"  FROM DC_CPR_INFOWARE T,dc_code.dc_12315_codedata c,dc_dc.dc_cpr_feedback ff,dc_dc.dc_cpr_case_info cc\n" + 
						" WHERE t.feedbackid=ff.feedbackid\n" + 
						"and ff.caseinfoid=cc.caseinfoid\n" + 
						"   and  t.INFOORI=c.code\n" + 
						"    and c.codetable='CH02'\n" + 
						"    and  T.REGTIME >= TO_DATE(?,'YYYY-MM-DD')\n" + 
						"   AND T.REGTIME <= TO_DATE(?,'YYYY-MM-DD hh24:mi:Ss') \n");
		query.add(beginTime);
		query.add(endTime + " 23:59:59");
		if (infotype != null && infotype.length() != 0) {
			sqlQuery.append("and t.inftype=? \n");
			query.add(infotype.trim());
		}
		sqlQuery.append(
				"GROUP BY c.name, TO_CHAR(T.REGTIME, 'YYYYMM')\n" +
						") T\n" + 
						"PIVOT(SUM(T.CNT) FOR  REGTIME IN (");
		this.bufferUtil(res1, sqlQuery);
		sqlQuery.append(" ))\n" +"\n" + ")z\n" + 
						"ORDER BY num desc");
		try {
			res2 = dao.queryForList(sqlQuery.toString(), query);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		result.put("order", res1);
		result.put("result", res2);
		logop.logInfoYeWu("?????????????????????????????????????????????????????????", "WDY", i == 1 ? "????????????" : "????????????",
				sqlQuery.toString(), beginTime + "," + endTime + "," + infotype, req);
		return result;
	}

	public Map<String, List<Map>> jieShouFangShi(String beginTime,
			String endTime, String infotype, int i, HttpServletRequest req) {
		List<Map> res1 = this.getMonth(beginTime, endTime, infotype);
		List query = new ArrayList();
		List<Map> res2 = new ArrayList<Map>();
		Map<String, List<Map>> result = new HashMap<String, List<Map>>();
		StringBuffer sqlQuery = new StringBuffer();
		if (res1 == null || res1.size() == 0) {
			return null;
		}
		this.bufferUtilB(res1, sqlQuery);
		sqlQuery.append(
				") over (partition by z.regdepname,z.REGDEPNAME  ) num\n" +
						" from (\n" + 
						"SELECT * FROM (\n" + 
						"SELECT  c.name REGDEPNAME, TO_CHAR(T.REGTIME, 'YYYYMM') AS REGTIME, sum(nvl(cc.ECOLOVAL,0)) AS CNT\n" + 
						"  FROM DC_CPR_INFOWARE T,dc_code.dc_12315_codedata c,dc_dc.dc_cpr_feedback ff,dc_dc.dc_cpr_case_info cc\n" + 
						" WHERE t.feedbackid=ff.feedbackid\n" + 
						"and ff.caseinfoid=cc.caseinfoid\n" + 
						"   and   t.INCFORM=c.code\n" + 
						"    and c.codetable='CH03'\n" + 
						"    and  T.REGTIME >= TO_DATE(?,'YYYY-MM-DD')\n" + 
						"   AND T.REGTIME <= TO_DATE(?,'YYYY-MM-DD hh24:mi:ss') \n");
		query.add(beginTime);
		query.add(endTime + " 23:59:59");
		if (infotype != null && infotype.length() != 0) {
			sqlQuery.append("and t.inftype=? \n");
			query.add(infotype.trim());
		}
		sqlQuery.append(
				"GROUP BY c.name, TO_CHAR(T.REGTIME, 'YYYYMM')\n" +
						" ) T\n" + 
						" PIVOT(SUM(T.CNT) FOR  REGTIME IN (");
		this.bufferUtil(res1, sqlQuery);
		sqlQuery.append("))\n" +"\n" + ")z\n" + "ORDER BY num desc");
		try {
			res2 = dao.queryForList(sqlQuery.toString(), query);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		result.put("order", res1);
		result.put("result", res2);
		logop.logInfoYeWu("?????????????????????????????????????????????????????????", "WDY", i == 1 ? "????????????" : "????????????",
				sqlQuery.toString(), beginTime + "," + endTime + "," + infotype, req);
		return result;
	}

	public Map<String, List<Map>> hangYeLeiXing(String beginTime,
			String endTime, String infotype, int i, HttpServletRequest req) {
		List<Map> res1 = this.getMonth(beginTime, endTime, infotype);
		List query = new ArrayList();
		List<Map> res2 = new ArrayList<Map>();
		Map<String, List<Map>> result = new HashMap<String, List<Map>>();
		StringBuffer sqlQuery = new StringBuffer();
		if (res1 == null || res1.size() == 0) {
			return null;
		}
		this.bufferUtilB(res1, sqlQuery);
		sqlQuery.append(
				") over (partition by z.regdepname,z.PARENT_DEP_CODE ) num\n" +
						" from (\n" + 
						"SELECT * FROM (\n" + 
						"SELECT  /*+ parallel(8)*/ m.TRADETYPE PARENT_DEP_CODE,(case when m.TRADETYPE='100' or substr(m.TRADETYPE,2,2)<>'00' then '???'||d.name else d.name end )AS REGDEPNAME, TO_CHAR(T.REGTIME, 'YYYYMM') AS REGTIME, sum(nvl(cc.ECOLOVAL,0))  AS CNT\n" + 
						"  FROM DC_CPR_INFOWARE T, dc_code.dc_12315_codedata d,dc_dc.dc_CPR_INVOLVED_MAIN m,dc_dc.dc_cpr_feedback ff,dc_dc.dc_cpr_case_info cc\n" + 
						" WHERE t.feedbackid=ff.feedbackid\n" + 
						"and ff.caseinfoid=cc.caseinfoid\n" + 
						"   and t.INVMAIID=m.INVMAIID(+)\n" + 
						"   and  m.TRADETYPE=d.code\n" + 
						"   and d.codetable='CH15'\n" + 
						"   and (d.PARENTCODE <>'0' OR D.CODE='100')\n" + 
						"   AND T.REGTIME >= TO_DATE(?,'YYYY-MM-DD')\n" + 
						"   AND T.REGTIME <= TO_DATE(?,'YYYY-MM-DD hh24:mi:ss')\n");
		query.add(beginTime);
		query.add(endTime + " 23:59:59");
		if (infotype != null && infotype.length() != 0) {
			sqlQuery.append("and t.inftype=? \n");
			query.add(infotype.trim());
		}
		sqlQuery.append(
				"GROUP BY D.name,m.TRADETYPE, TO_CHAR(T.REGTIME, 'YYYYMM')\n" +
						"UNION ALL\n" + 
						"SELECT   /*+ parallel(8)*/ D.PARENTCODE PARENT_DEP_CODE,D1.NAME AS REGDEPNAME, TO_CHAR(T.REGTIME, 'YYYYMM') AS REGTIME, sum(nvl(cc.ECOLOVAL,0))  AS CNT\n" + 
						"  FROM DC_CPR_INFOWARE T,-- dc_code.dc_12315_codedata d,\n" + 
						"  (select name,code, decode(PARENTCODE,'0',code,PARENTCODE) PARENTCODE from dc_code.dc_12315_codedata  where codetable='CH15'\n" + 
						"          /*union all\n" + 
						"select '??????' as name,'100' as code, '100' as PARENTCODE from dual*/) d,\n" + 
						"    dc_code.dc_12315_codedata  D1,dc_dc.dc_CPR_INVOLVED_MAIN m,dc_dc.dc_cpr_feedback ff,dc_dc.dc_cpr_case_info cc\n" + 
						" WHERE  t.feedbackid=ff.feedbackid\n" + 
						"and ff.caseinfoid=cc.caseinfoid\n" + 
						"   and t.INVMAIID=m.INVMAIID(+)\n" + 
						"   and  m.TRADETYPE=d.code\n" + 
						"   AND D.PARENTCODE = D1.code\n" + 
						"   and d1.codetable='CH15'\n" + 
						"   AND T.REGTIME >= TO_DATE(?,'YYYY-MM-DD')\n" + 
						"   AND T.REGTIME <= TO_DATE(?,'YYYY-MM-DD hh24:mi:ss') \n");
		query.add(beginTime);
		query.add(endTime + " 23:59:59");
		if (infotype != null && infotype.length() != 0) {
			sqlQuery.append("and t.inftype=? \n");
			query.add(infotype.trim());
		}
		sqlQuery.append(
				"GROUP BY  D.PARENTCODE,D1.NAME, TO_CHAR(T.REGTIME, 'YYYYMM')\n" +
						") T\n" + 
						"PIVOT(SUM(T.CNT) FOR  REGTIME IN (");
		this.bufferUtil(res1, sqlQuery);
		sqlQuery.append(" ))\n" +")z\n" + 
						"ORDER BY PARENT_DEP_CODE/*,T.REGTIME*/ ,REGDEPNAME DESC");
		try {
			res2 = dao.queryForList(sqlQuery.toString(), query);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		result.put("order", res1);
		result.put("result", res2);
		logop.logInfoYeWu("?????????????????????????????????????????????????????????", "WDY", i == 1 ? "????????????" : "????????????",
				sqlQuery.toString(), beginTime + "," + endTime + "," + infotype, req);
		return result;
	}

	public Map<String, List<Map>> wenTiXingZhi(String beginTime,
			String endTime, String infotype, int i, HttpServletRequest req) {
		List<Map> res1 = this.getMonth(beginTime, endTime, infotype);
		List query = new ArrayList();
		List<Map> res2 = new ArrayList<Map>();
		Map<String, List<Map>> result = new HashMap<String, List<Map>>();
		StringBuffer sqlQuery = new StringBuffer();
		if (res1 == null || res1.size() == 0) {
			return null;
		}
		this.bufferUtilB(res1, sqlQuery);
		sqlQuery.append(
				") OVER(PARTITION BY Z.PARENT_DEP_CODE,Z.REGDEPNAME) NUM\n" +
						"  FROM (SELECT *\n" + 
						"          FROM (SELECT D.CODE PARENT_DEP_CODE,\n" + 
						"                       '???' || D.NAME AS REGDEPNAME,\n" + 
						"                       TO_CHAR(T.REGTIME, 'YYYYMM') AS REGTIME,\n" + 
						"                      sum(nvl(cc.ECOLOVAL,0)) AS CNT\n" + 
						"                  FROM DC_CPR_INFOWARE T,\n" + 
						"                       (SELECT *\n" + 
						"                          FROM DC_CODE.DC_12315_CODEDATA\n" + 
						"                         WHERE CODETABLE = 'CH27'\n" + 
						"                           /*AND PARENTCODE <> '0'*/) D,\n" + 
						"                           dc_dc.dc_cpr_feedback ff,dc_dc.dc_cpr_case_info cc\n" + 
						"                 WHERE  t.feedbackid=ff.feedbackid\n" + 
						"                   and ff.caseinfoid=cc.caseinfoid\n" + 
						"                   and NVL(T.APPLBASQUE,'9900') = D.CODE\n" + 
						"                   AND T.REGTIME >= TO_DATE(?, 'YYYY-MM-DD')\n" + 
						"                   AND T.REGTIME <= TO_DATE(?, 'YYYY-MM-DD hh24:mi:ss') \n");
		query.add(beginTime);
		query.add(endTime + " 23:59:59");
		if (infotype != null && infotype.length() != 0) {
			sqlQuery.append("and t.inftype=? \n");
			query.add(infotype.trim());
		}
		sqlQuery.append(
				"GROUP BY D.CODE, D.NAME, TO_CHAR(T.REGTIME, 'YYYYMM')\n" +
						"                UNION ALL\n" + 
						"                SELECT /*+ parallel(8)*/\n" + 
						"       D1.CODE PARENT_DEP_CODE,\n" + 
						"       D1.NAME  AS REGDEPNAME,\n" + 
						"       TO_CHAR(T.REGTIME, 'YYYYMM') AS REGTIME,\n" + 
						"      sum(nvl(cc.ECOLOVAL,0)) AS CNT\n" + 
						"  FROM DC_CPR_INFOWARE T,\n" + 
						"       (SELECT * FROM DC_CODE.DC_12315_CODEDATA WHERE CODETABLE = 'CH27') D,\n" + 
						"       (SELECT *\n" + 
						"          FROM DC_CODE.DC_12315_CODEDATA\n" + 
						"         WHERE CODETABLE = 'CH27'\n" + 
						"           AND PARENTCODE IN ('0', '3')) D1,\n" + 
						"           dc_dc.dc_cpr_feedback ff,dc_dc.dc_cpr_case_info cc\n" + 
						" WHERE t.feedbackid=ff.feedbackid\n" + 
						"            and ff.caseinfoid=cc.caseinfoid and NVL(T.APPLBASQUE, '9900') = D.CODE\n" + 
						"   AND DECODE(D.PARENTCODE, '0', D.CODE, '3', D.CODE, D.PARENTCODE) =\n" + 
						"       D1.CODE\n" + 
						"   AND T.REGTIME >= TO_DATE(?, 'YYYY-MM-DD')\n" + 
						"   AND T.REGTIME <= TO_DATE(?, 'YYYY-MM-DD hh24:mi:ss') \n");
		query.add(beginTime);
		query.add(endTime + " 23:59:59");
		if (infotype != null && infotype.length() != 0) {
			sqlQuery.append("and t.inftype=? \n");
			query.add(infotype.trim());
		}
		sqlQuery.append(
				"GROUP BY D1.CODE,\n" +
						"          D1.NAME ,\n" + 
						"          TO_CHAR(T.REGTIME, 'YYYYMM')\n" + 
						" ) T PIVOT(SUM(T.CNT) FOR REGTIME IN(");
		this.bufferUtil(res1, sqlQuery);
		sqlQuery.append("))\n" +"        ) Z\n" + 
						" ORDER BY PARENT_DEP_CODE /*,T.REGTIME*/, REGDEPNAME DESC");
		try {
			res2 = dao.queryForList(sqlQuery.toString(), query);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		result.put("order", res1);
		result.put("result", res2);
		logop.logInfoYeWu("?????????????????????????????????????????????????????????", "WDY", i == 1 ? "????????????" : "????????????",
				sqlQuery.toString(), beginTime + "," + endTime + "," + infotype, req);
		return result;
	}

	public Map<String, List<Map>> yeWuFanWei(String beginTime, String endTime,
			String infotype, int i, HttpServletRequest req) {
		List<Map> res1 = this.getMonth(beginTime, endTime, infotype);
		List query = new ArrayList();
		List<Map> res2 = new ArrayList<Map>();
		Map<String, List<Map>> result = new HashMap<String, List<Map>>();
		StringBuffer sqlQuery = new StringBuffer();
		if (res1 == null || res1.size() == 0) {
			return null;
		}
		this.bufferUtilB(res1, sqlQuery);
		sqlQuery.append(
				") over (partition by z.regdepname ) num\n" +
						" from (\n" + 
						"SELECT * FROM (\n" + 
						"SELECT  (case when o.BUSINESSTYPE ='CH20' THEN '??????'\n" + 
						"             when o.BUSINESSTYPE ='ZH18' then '?????????'\n" + 
						"             when  o.BUSINESSTYPE ='ZH19'  then '????????????'\n" + 
						"             when o.BUSINESSTYPE ='ZH20' then '????????????'\n" + 
						"             when o.BUSINESSTYPE ='ZH21'  then '????????????'\n" + 
						"             else '??????' end) REGDEPNAME, TO_CHAR(T.REGTIME, 'YYYYMM') AS REGTIME,  sum(nvl(cc.ECOLOVAL,0))  AS CNT\n" + 
						"  FROM DC_CPR_INFOWARE T,dc_cpr_involved_object o,dc_dc.dc_cpr_feedback ff,dc_dc.dc_cpr_case_info cc\n" + 
						" WHERE  t.feedbackid=ff.feedbackid\n" + 
						"     and ff.caseinfoid=cc.caseinfoid\n" + 
						"     and t.INVOBJID=o.INVOBJID\n" + 
						"     and t.regtime>=to_date(?,'yyyy-mm-dd')\n" + 
						"     and t.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')\n");
		query.add(beginTime);
		query.add(endTime + " 23:59:59");
		if (infotype != null && infotype.length() != 0) {
			sqlQuery.append("and t.inftype=? \n");
			query.add(infotype.trim());
		}
		sqlQuery.append(
				"GROUP BY o.BUSINESSTYPE, TO_CHAR(T.REGTIME, 'YYYYMM')\n" +
						" ) T\n" + 
						" PIVOT(SUM(T.CNT) FOR  REGTIME IN (");
		this.bufferUtil(res1, sqlQuery);
		sqlQuery.append("))\n" +")z\n" + "ORDER BY num desc");
		try {
			res2 = dao.queryForList(sqlQuery.toString(), query);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		result.put("order", res1);
		result.put("result", res2);
		logop.logInfoYeWu("?????????????????????????????????????????????????????????", "WDY", i == 1 ? "????????????" : "????????????",
				sqlQuery.toString(), beginTime + "," + endTime + "," + infotype, req);
		return result;
	}

	public Map<String, List<Map>> suoShuBuMen(String beginTime, String endTime,
			String infotype, int i, HttpServletRequest req) {
		List<Map> res1 = this.getMonth(beginTime, endTime, infotype);
		List query = new ArrayList();
		List<Map> res2 = new ArrayList<Map>();
		Map<String, List<Map>> result = new HashMap<String, List<Map>>();
		StringBuffer sqlQuery = new StringBuffer();
		if (res1 == null || res1.size() == 0) {
			return null;
		}
		this.bufferUtilB(res1, sqlQuery);
		sqlQuery.append(
				") over (partition by z.regdepname ) num\n" +
						" from (\n" + 
						"SELECT * FROM (\n" + 
						"SELECT  nvl(t.HANPARDEPNAME,t.HANDEPNAME) REGDEPNAME, TO_CHAR(T.REGTIME, 'YYYYMM') AS REGTIME, sum(nvl(cc.ECOLOVAL,0)) AS CNT\n" + 
						"  FROM DC_CPR_INFOWARE T,dc_dc.dc_cpr_feedback ff,dc_dc.dc_cpr_case_info cc\n" + 
						" WHERE  t.feedbackid=ff.feedbackid\n" + 
						" and ff.caseinfoid=cc.caseinfoid\n" + 
						"  and t.regtime>=to_date(?,'yyyy-mm-dd')\n" + 
						"      and t.regtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') \n");
		query.add(beginTime);
		query.add(endTime + " 23:59:59");
		if (infotype != null && infotype.length() != 0) {
			sqlQuery.append("and t.inftype=? \n");
			query.add(infotype.trim());
		}
		sqlQuery.append(
				"GROUP BY  nvl(t.HANPARDEPNAME,t.HANDEPNAME), TO_CHAR(T.REGTIME, 'YYYYMM')\n" +
						") T\n" + 
						"PIVOT(SUM(T.CNT) FOR  REGTIME IN (");
		this.bufferUtil(res1, sqlQuery);
		sqlQuery.append(" ))\n" +")z\n" + "ORDER BY num desc");
		try {
			res2 = dao.queryForList(sqlQuery.toString(), query);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		result.put("order", res1);
		result.put("result", res2);
		logop.logInfoYeWu("?????????????????????????????????????????????????????????", "WDY", i == 1 ? "????????????" : "????????????",
				sqlQuery.toString(), beginTime + "," + endTime + "," + infotype, req);
		return result;
	}

	public Map<String, List<List<Map>>> sheJiKeTi(String beginTime,
			String endTime, String infotype, String shejiketi, int i,
			HttpServletRequest req) {
		List<Map> res1 = this.getMonth(beginTime, endTime, infotype);
		List query = new ArrayList();
		List<List<Map>> res2=new ArrayList<List<Map>>();//?????????????????????????????????
		List<List<Map>> results=new ArrayList<List<Map>>();//?????????????????????????????????
		Map<String, List<List<Map>>> result=new HashMap<String,List<List<Map>>>();  //?????????
		if (res1 == null || res1.size() == 0) {
			return null;
		}
		query.add(beginTime);
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
		logop.logInfoYeWu("?????????????????????????????????????????????????????????", "WDY", i == 1 ? "????????????" : "????????????", str[0],
				beginTime + "," + endTime+","+infotype, req);
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
		this.bufferUtilC(res1, sqlQuery);
		sqlQuery.append(
				") over (partition by z.name ,z.INVOBJTYPE) num\n" +
						" from (\n" + 
						"select * from (\n" + 
						"select distinct y.INVOBJTYPE,z.name,y.regtime,y.num from (\n" + 
						"with x as\n" + 
						" (select o.INVOBJTYPE,t.regtime,nvl(cc.ECOLOVAL,0) numbers\n" + 
						"    from dc_dc.dc_cpr_infoware t, dc_cpr_involved_object o,dc_dc.dc_cpr_feedback ff,dc_dc.dc_cpr_case_info cc\n" + 
						"   where  t.feedbackid=ff.feedbackid\n" + 
						"and ff.caseinfoid=cc.caseinfoid\n" + 
						"  and o.invobjid(+) = t.invobjid\n" + 
						"   --and o.INVOBJTYPE is not null\n" + 
						"   and o.BUSINESSTYPE = 'ZH21'\n" + 
						"   AND LENGTH(O.INVOBJTYPE)=10\n" + 
						"     and t.regtime >=\n" + 
						"         to_date(?, 'yyyy-mm-dd')\n" + 
						"     and t.regtime <=\n" + 
						"         to_date(?, 'yyyy-mm-dd hh24:mi:ss') \n");
		if (infotype!=null&&infotype.length()!=0) {
			sqlQuery.append("and t.inftype=? \n");
		}
		sqlQuery.append(
				"  )\n" +
						"select '00????????????' as invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,sum(x.numbers)num from x group by TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						"union all\n" + 
						"select x.invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,sum(x.numbers) num from x  group by x.invobjtype ,TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,2)||'00000000' invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,sum(x.numbers) num from x group by substr(x.invobjtype,0,2),TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						"UNION ALL\n" + 
						"select substr(x.invobjtype,0,4)||'000000' invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,sum(x.numbers)  num from x group by substr(x.invobjtype,0,4),TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						"UNION ALL\n" + 
						"select substr(x.invobjtype,0,6)||'0000' invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,sum(x.numbers) num from x group by substr(x.invobjtype,0,6),TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						"UNION ALL\n" + 
						"select substr(x.invobjtype,0,8)||'00' invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,sum(x.numbers) num from x group by substr(x.invobjtype,0,8),TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						"\n" + 
						") y\n" + 
						"left join\n" + 
						"(select code , (case when substr(code,9,2)<>'00'then '????????????'||name\n" + 
						"      when substr(code,7,4)<>'0000'then '?????????'||name\n" + 
						"      when substr(code,5,6)<>'000000'then '??????'||name\n" + 
						"      when substr(code,3,8)<>'00000000'then '???'||name\n" + 
						"      else name end) name  from dc_code.dc_12315_codedata where codetable='ZH21'\n" + 
						"       union all\n" + 
						"      select '00????????????' code ,'????????????'name from dual) z\n" + 
						" on y.INVOBJTYPE=z.code\n" + 
						" order by y.invobjtype)yy\n" + 
						" PIVOT(SUM(nvl(yy.num,0)) FOR  REGTIME IN (");
		this.bufferUtil(res1, sqlQuery);
		sqlQuery.append("))\n" +" )z\n" + " order by z.invobjtype");
		return sqlQuery.toString();
	}

	private String getJiaGeJianChaSql(String infotype, List<Map> res1) {
		StringBuffer sqlQuery=new StringBuffer();
		this.bufferUtilC(res1, sqlQuery);
		sqlQuery.append(
				") over (partition by z.name ,z.INVOBJTYPE) num\n" +
						" from (\n" + 
						"select * from (\n" + 
						"select distinct y.INVOBJTYPE,z.name,y.regtime,y.num from (\n" + 
						"with x as\n" + 
						" (select o.INVOBJTYPE,t.regtime,nvl(cc.ECOLOVAL,0) numbers\n" + 
						"    from dc_dc.dc_cpr_infoware t, dc_cpr_involved_object o,dc_dc.dc_cpr_feedback ff,dc_dc.dc_cpr_case_info cc\n" + 
						"   where  t.feedbackid=ff.feedbackid\n" + 
						"and ff.caseinfoid=cc.caseinfoid\n" + 
						" and o.invobjid(+) = t.invobjid\n" + 
						"   and o.INVOBJTYPE is not null\n" + 
						"     and o.BUSINESSTYPE = 'ZH20'\n" + 
						"     AND LENGTH(O.INVOBJTYPE)=4\n" + 
						"     and t.regtime >=\n" + 
						"         to_date(?, 'yyyy-mm-dd')\n" + 
						"     and t.regtime <=\n" + 
						"         to_date(?, 'yyyy-mm-dd hh24:mi:ss') \n");
		if (infotype!=null&&infotype.length()!=0) {
			sqlQuery.append("and t.inftype=? \n");
		}
		sqlQuery.append(
				"  )\n" +
						"select '00????????????' as invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,sum(x.numbers)num from x group by TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						"union all\n" + 
						"select x.invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,sum(x.numbers) num from x  group by x.invobjtype ,TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,2)||'00' invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,sum(x.numbers) from x group by substr(x.invobjtype,0,2),TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						") y\n" + 
						"left join\n" + 
						"(select code , (case when substr(code,3,2)<>'00'then '???'||name\n" + 
						"      else name end) name  from dc_code.dc_12315_codedata where codetable='ZH20'\n" + 
						"      union all\n" + 
						"      select '00????????????' code ,'????????????'name from dual) z\n" + 
						" on y.INVOBJTYPE=z.code\n" + 
						" order by y.invobjtype)yy\n" + 
						" PIVOT(SUM(nvl(yy.num,0)) FOR  REGTIME IN (");
		this.bufferUtil(res1, sqlQuery);
		sqlQuery.append("))\n" +" )z\n" + " order by z.invobjtype");
		return sqlQuery.toString();
	}

	private String getZhiShiChanQuanSql(String infotype, List<Map> res1) {
		StringBuffer sqlQuery=new StringBuffer();
		this.bufferUtilC(res1, sqlQuery);
		sqlQuery.append(
				") over (partition by z.name,z.INVOBJTYPE ) num\n" +
						" from (\n" + 
						"select * from (\n" + 
						"select distinct y.INVOBJTYPE,z.name,y.num,y.regtime from (\n" + 
						"with x as\n" + 
						" (select o.INVOBJTYPE,t.regtime,nvl(cc.ECOLOVAL,0) numbers\n" + 
						"    from dc_dc.dc_cpr_infoware t, dc_cpr_involved_object o,dc_dc.dc_cpr_feedback ff,dc_dc.dc_cpr_case_info cc\n" + 
						"   where t.feedbackid=ff.feedbackid\n" + 
						"and ff.caseinfoid=cc.caseinfoid\n" + 
						"and o.invobjid(+) = t.invobjid\n" + 
						"   and o.INVOBJTYPE is not null\n" + 
						"   and o.BUSINESSTYPE = 'ZH19'\n" + 
						"   AND LENGTH(O.INVOBJTYPE)=3\n" + 
						"     and t.regtime >=\n" + 
						"         to_date(?, 'yyyy-mm-dd')\n" + 
						"     and t.regtime <=\n" + 
						"         to_date(?, 'yyyy-mm-dd hh24:mi:ss') \n");
		if (infotype!=null&&infotype.length()!=0) {
			sqlQuery.append("and t.inftype=? \n");
		}
		sqlQuery.append(
				" )\n" +
						"select '00????????????' as invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,sum(x.numbers)num from x group by TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						"union all\n" + 
						"select x.invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,sum(x.numbers) num from x  group by x.invobjtype ,TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,1)||'00' invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,sum(x.numbers) num from x group by substr(x.invobjtype,0,1),TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						"\n" + 
						") y\n" + 
						"left join\n" + 
						"(select code , (case when substr(code,2,2)<>'00'then '???'||name\n" + 
						"      else name end) name  from dc_code.dc_12315_codedata where codetable='ZH19'\n" + 
						"      union all\n" + 
						"      select '00????????????' code ,'????????????'name from dual) z\n" + 
						" on y.INVOBJTYPE=z.code\n" + 
						" order by y.invobjtype)yy\n" + 
						" PIVOT(SUM(nvl(yy.num,0)) FOR  REGTIME IN (");
		this.bufferUtil(res1, sqlQuery);
		sqlQuery.append("))\n" +" )z\n" + " order by z.invobjtype");
		return sqlQuery.toString();
	}

	private String getXiaoWeiHuiSql(String infotype, List<Map> res1) {
		StringBuffer sqlQuery=new StringBuffer();
		this.bufferUtilC(res1, sqlQuery);
		sqlQuery.append(
				") over (partition by z.name ,z.INVOBJTYPE) num\n" +
						" from (\n" + 
						"select * from (\n" + 
						"select distinct y.INVOBJTYPE,z.name,y.regtime,y.num from (\n" + 
						"with x as\n" + 
						" (select o.INVOBJTYPE,t.regtime,nvl(cc.ECOLOVAL,0) numbers\n" + 
						"    from dc_dc.dc_cpr_infoware t, dc_cpr_involved_object o,dc_dc.dc_cpr_feedback ff,dc_dc.dc_cpr_case_info cc\n" + 
						"   where  t.feedbackid=ff.feedbackid\n" + 
						"and ff.caseinfoid=cc.caseinfoid\n" + 
						"and  o.invobjid(+) = t.invobjid\n" + 
						"  -- and o.INVOBJTYPE is not null\n" + 
						"   and o.BUSINESSTYPE = 'ZH18'\n" + 
						"   AND LENGTH(O.INVOBJTYPE)=10\n" + 
						"     and t.regtime >=\n" + 
						"         to_date(?, 'yyyy-mm-dd')\n" + 
						"     and t.regtime <=\n" + 
						"         to_date(?, 'yyyy-mm-dd hh24:mi:ss') \n");
		if (infotype!=null&&infotype.length()!=0) {
			sqlQuery.append("and t.inftype=? \n");
		}
		sqlQuery.append(
				" )\n" +
						"select '00?????????' as invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,sum(x.numbers)num from x group by TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						"union all\n" + 
						"select x.invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,sum(x.numbers) num from x  group by TO_CHAR(x.REGTIME, 'YYYYMM'),x.invobjtype\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,2)||'00000000' invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,sum(x.numbers) num from x group by TO_CHAR(x.REGTIME, 'YYYYMM') ,substr(x.invobjtype,0,2)\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,4)||'000000' invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,sum(x.numbers) num from x  group by TO_CHAR(x.REGTIME, 'YYYYMM'),substr(x.invobjtype,0,4)\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,6)||'0000' invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,sum(x.numbers) num  from x  group by TO_CHAR(x.REGTIME, 'YYYYMM') , substr(x.invobjtype,0,6)\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,8)||'00' invobjtype,TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,sum(x.numbers) num  from x  group by TO_CHAR(x.REGTIME, 'YYYYMM'),substr(x.invobjtype,0,8)\n" + 
						"\n" + 
						") y\n" + 
						"left join\n" + 
						"(select code , (case when substr(code,9,2)<>'00'then '????????????'||name\n" + 
						"      when substr(code,7,4)<>'0000'then '?????????'||name\n" + 
						"      when substr(code,5,6)<>'000000'then '??????'||name\n" + 
						"      when substr(code,3,8)<>'00000000'then '???'||name\n" + 
						"      else name end) name  from dc_code.dc_12315_codedata where codetable='ZH18'\n" + 
						"      union all\n" + 
						"      select '00?????????' code ,'?????????'name from dual) z\n" + 
						" on y.INVOBJTYPE=z.code\n" + 
						" order by y.invobjtype)yy\n" + 
						" PIVOT(SUM(nvl(yy.num,0)) FOR  REGTIME IN (");
		this.bufferUtil(res1, sqlQuery);
		sqlQuery.append("))\n" +" )z\n" + " order by z.invobjtype");
		return sqlQuery.toString();
	}

	private String getGongShangSql(String infotype, List<Map> res1) {
		StringBuffer sqlQuery=new StringBuffer();
		this.bufferUtilC(res1, sqlQuery);
		sqlQuery.append(
				") over (partition by Z.INVOBJTYPE, z.name ) num\n" +
						" from (\n" + 
						"select * from (\n" + 
						"select distinct y.INVOBJTYPE,z.name,y.REGTIME,y.num from (\n" + 
						"with x as\n" + 
						" (select o.INVOBJTYPE,t.regtime,nvl(cc.ECOLOVAL,0) numbers\n" + 
						"    from dc_dc.dc_cpr_infoware t, dc_cpr_involved_object o,dc_dc.dc_cpr_feedback ff,dc_dc.dc_cpr_case_info cc\n" + 
						"   where t.feedbackid=ff.feedbackid\n" + 
						"and ff.caseinfoid=cc.caseinfoid\n" + 
						"   and  o.invobjid(+) = t.invobjid\n" + 
						"    and o.BUSINESSTYPE = 'CH20'\n" + 
						"    AND LENGTH(O.INVOBJTYPE)=8\n" + 
						"     and t.regtime >=\n" + 
						"         to_date(?, 'yyyy-mm-dd')\n" + 
						"     and t.regtime <=\n" + 
						"         to_date(?, 'yyyy-mm-dd hh24:mi:ss') \n");
		if (infotype!=null&&infotype.length()!=0) {
			sqlQuery.append("and t.inftype=? \n");
		}
		sqlQuery.append(
				" )\n" +
						"select '00??????' as invobjtype, TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,sum(x.numbers)num from x group by TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						"union all\n" + 
						"select x.invobjtype, TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,sum(x.numbers) num from x  group by x.invobjtype ,TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,3)||'00000' invobjtype, TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,sum(x.numbers)num from x group by substr(x.invobjtype,0,3),TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						"union all\n" + 
						"select substr(x.invobjtype,0,5)||'000' invobjtype, TO_CHAR(x.REGTIME, 'YYYYMM') AS REGTIME,sum(x.numbers)num  from x  group by substr(x.invobjtype,0,5),TO_CHAR(x.REGTIME, 'YYYYMM')\n" + 
						") y\n" + 
						"left join\n" + 
						"(select code , (\n" + 
						" case when substr(code,7,2)<>'00' then '????????????'||name\n" + 
						"      when substr(code,6,3)<>'000'   then '?????????'||name\n" + 
						"      when substr(code,4,5)<>'00000' or substr(code,6,3)='900' then '??????'||name\n" + 
						"      when substr(code,2,7)<>'0000000'then '???'||name\n" + 
						"      else name end) name  from dc_code.dc_12315_codedata where codetable='CH20'\n" + 
						"      union all\n" + 
						"      select '00??????' code,'??????' name from dual) z\n" + 
						" on y.INVOBJTYPE=z.code)yy\n" + 
						" PIVOT(SUM(nvl(yy.num,0)) FOR  REGTIME IN (");
		this.bufferUtil(res1, sqlQuery);
		sqlQuery.append("))\n" +" )z\n" + " order by z.invobjtype\n" + "");
		return sqlQuery.toString();
	}
}
