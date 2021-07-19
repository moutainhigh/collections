package com.gwssi.report.service;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
/*import com.gwssi.optimus.core.persistence.datasource.DataSourceManager;*/
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.report.auth.SimpleReportInfo;
import com.gwssi.report.balance.api.ReportModel;
import com.gwssi.report.balance.api.ReportSource;
import com.gwssi.report.balance.htmlParser.HTMLTableParser;
import com.gwssi.report.balance.htmlParser.ParserFactory;
import com.gwssi.report.balance.htmlParser.ReportLimitRowsModel;
import com.gwssi.report.model.TCognosReportBO;

@Service
public class CognosService extends BaseService implements ReportSource{
	
	private static  Logger log=Logger.getLogger(CognosService.class);
	
	//report cache for check balance
	private WeakHashMap<TCognosReportBO, ReportModel> reportCache =
			new WeakHashMap<TCognosReportBO, ReportModel>(128);

	// 将字符串转换成二进制字符串，以空格相隔
	public synchronized String StrToBinstr(String str) {
		char[] strChar = str.toCharArray();
		String result = "";
		for (int i = 0; i < strChar.length; i++) {
			result += Integer.toBinaryString(strChar[i]) + " ";
		}
		return result;
	}


	/**
	 * 将生产的报表存入数据库
	 * 
	 * @throws OptimusException
	 *             sdk_m_visit
	 * @throws UnsupportedEncodingException
	 */
	public void saveReport(TCognosReportBO bo, String result)
			throws OptimusException, UnsupportedEncodingException {
		IPersistenceDAO dao = this.getPersistenceDAO("dbCsdb");
		result = this.StrToBinstr(result);
		String sql = " INSERT INTO DB_CSDB.T_COGNOS_Report(id, regcode, reportContext, reportType, reportTime, reportParamters, ReportName, mouth, year)  "
				+ "values ('"
				+ UUID.randomUUID()
				+ "', '"
				+ bo.getRegcode()
				+ "', '"
				+ result
				+ "', '"
				+ bo.getReporttype()
				+ "', '"
				+ bo.getReporttime()
				+ "', '"
				+ bo.getReportparamters()
				+ "', '"
				+ bo.getReportname()
				+ "', '"
				+ bo.getMouth()
				+ "', '"
				+ bo.getYear() + "')";
		System.out.println(sql);
		dao.execute(sql, null);
	}

	public List<Map> getAllMouthReports(String year, String reportParamters)
			throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO("dbCsdb");
		String sql = "  select a.code reporttype ,a.value reportname ,b.code regcode,c.code reportparamters from DB_CSDB.T_COGNOS_reporttype a, T_DM_BBQH b,T_DM_BBLB c"
				+ "   where c.code='03' and a.value='个体1表'    and  	a.code||b.code||'"
				+ reportParamters
				+ "'   not in (select reportType||regcode||reportParamters from DB_CSDB.T_COGNOS_report where year='"
				+ year
				+ "'        and reportParamters='"
				+ reportParamters
				+ "') ";
		System.out.println("执行sql===" + sql);
		List<Map> list = dao.queryForList(sql, null);

		return list;
	};

	//未使用到？
	public void deleteALlByDate(String year, String reportParamters)
			throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO("dbCsdb");
		String sql = "delete from DB_CSDB.T_COGNOS_REPORT where year='" + year
				+ "' and reportParamters='" + reportParamters + "'";
		System.out.println("执行sql===" + sql);
		dao.execute(sql, null);
	}
	
	@SuppressWarnings("rawtypes")
	public Map queryReportInfo(String id) throws SQLException,
			OptimusException {
		Map map = null;
		List<String> params = new ArrayList<String>(1);
		params.add(id);
		String sql = "select reporttype,reportname from DB_CSDB.T_COGNOS_report  where id= ? ";
		List<Map> list = getPersistenceDAO("dbCsdb").queryForList(sql, params);
		if (list.size() > 0 && list != null) {
			map = list.get(0);
		}
		return map;
	}

	@SuppressWarnings("rawtypes")
	public Map queryCognosReport(String id, String region) throws SQLException,
			OptimusException {
		Map map = null;
		List<String> params = new ArrayList<String>(2);
		params.add(id);
		String sql = "select regcode,id,year,mouth,reporttype,reportname,reportcontext,isvalid from DB_CSDB.T_COGNOS_report  where id= ? ";
//		if(!"001".equals(region)) {
//			sql += " and regcode=?";
//			params.add(region);
//		}
		//03533446de864ff599237def074e2624-440304
		List<Map> list = getPersistenceDAO("dbCsdb").queryForList(sql, params);
		if (list.size() > 0 && list != null) {
			map = list.get(0);
		}
		return map;
	}

	public List queryCognosReports(String id, String regcode, String reportName,
			String params, String month, String year) throws SQLException,
			OptimusException {
		StringBuilder sql = new StringBuilder(
				"select id,regcode,reporttype,reporttime,REPORTPARAMTERS,REPORTNAME,mouth,year from DB_CSDB.T_COGNOS_report  where 1=1 ");
		List<String> p = new ArrayList<String>(7);
		if (StringUtils.isNotBlank(id)) {
			sql.append(" and id=? ");
			p.add(id);
		}
		if (StringUtils.isNotBlank(regcode)) {
			sql.append(" and regcode like ? ");
			p.add("%" +regcode + "%");
			//sql.append(" and charindex('");
			//sql.append("',regcode)>0");
		}
		if (StringUtils.isNotBlank(reportName)) {
			sql.append(" and reportName like ?");
			p.add("%" +reportName + "%");
		}
		if (StringUtils.isNotBlank(params)) {
			sql.append(" and REPORTPARAMTERS like ? ");
			p.add("%" + params + "%");
		}
		if (StringUtils.isNotBlank(month)) {
			sql.append(" and mouth=? ");
			p.add(month);
		}
		if (StringUtils.isNotBlank(year)) {
			sql.append(" and year=? ");
			p.add(year);
		}
		List<Map> list = getPersistenceDAO("dbCsdb").queryForList(sql.toString(), p);
		return list;
	}

	public Map queryCognosReport1() throws SQLException, OptimusException {
		Map map = null;
		String sql = "select  top 1 b.value as regcode,id,year,mouth,reportname,reportContext from DB_CSDB.T_COGNOS_report a left join T_DM_BBQH b on a.regcode=b.code ";
		List<Map> list = getPersistenceDAO("dbCsdb").queryForList(sql, null);
		if (list.size() > 0 && list != null) {
			map = list.get(0);
		}
		return map;
	}

	public List queryReport(String reportname, String regcode, String year,
			String bgq, String reporttype, Set<SimpleReportInfo> filters) throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO("dbCsdb");
		StringBuffer sql = new StringBuffer(
"select a.id,(case when substr(reportparamters,4,2)='03' then substr(reportparamters,1,2)||'月报' when substr(reportparamters,4,2)='02' then substr(reportparamters,1,2)||'季报' when  (substr(reportparamters,4,2)='01' and substr(reportparamters,1,2)='06') then substr(reportparamters,1,2)||'半年报'  when (substr(reportparamters,4,2)='01' and substr(reportparamters,1,2)='12') then substr(reportparamters,1,2)||'年报' end) as bgq,a.REGCODE as regcode,a.reportname,a.year,a.reporttype,a.isvalid from DB_CSDB.T_COGNOS_report a  where 1=1");
		List<String> params = new ArrayList<String>(6);
		if (reportname != null && !reportname.isEmpty()) {//报表名称不为空
			sql.append(" and  a.reportname = ?  ");
			params.add(reportname );
		}
		
		if (regcode != null && !regcode.isEmpty()) {//-1综合岗查全部
			//HttpSession session = WebContext.getHttpSession();
			if ("1".equals(WebContext.getHttpSession().getAttribute("fenjuzonghe"))
					&&"1".equals(WebContext.getHttpSession().getAttribute("USER_DOOR_ROLE_CODE"))) {
				sql.append("and  a.regcode != ? ");
				params.add("001");
				WebContext.getHttpSession().removeAttribute("fenjuzonghe");
			}else if("1".equals(WebContext.getHttpSession().getAttribute("fenjuzonghe"))
					&&"2".equals(WebContext.getHttpSession().getAttribute("USER_DOOR_ROLE_CODE"))){
				sql.append(" and  a.regcode = ? ");
				params.add((String)WebContext.getHttpSession().getAttribute("USER_REGION_CODE"));
				WebContext.getHttpSession().removeAttribute("fenjuzonghe");
			}else if("1".equals(WebContext.getHttpSession().getAttribute("fenjuzonghe"))
					&&"3".equals(WebContext.getHttpSession().getAttribute("USER_DOOR_ROLE_CODE"))){
				sql.append("and a.regcode != ?");
				params.add("001");
				WebContext.getHttpSession().removeAttribute("fenjuzonghe");
			}else{
				sql.append(" and  a.regcode = ? ");
				params.add(regcode);
			}
		}
		if (year != null && !year.isEmpty()) {
			sql.append(" and  a.year = ? ");
			params.add(year);
		}
		// 需要对数据进行处理
		if (bgq != null && !bgq.isEmpty()) {
			sql.append(" and  a.reportParamters = ? ");
			params.add(bgq);
		}
		if(!StringUtils.isBlank(reporttype)){
			sql.append(" and  a.reporttype = ? ");
			params.add(reporttype);
		}
//		int index = 0;
//		if(filters != null && filters.size() > 0) {
//			sql.append(" and (");
//			for(SimpleReportInfo s : filters) {
//				sql.append(index ++ == 0 ? "" : " or ");
//				sql.append("(reportname='").append(s.reportName).append("' and reporttype='")
//				   .append(s.reportType).append('\'').append(')');
//			}
//			sql.append(')');
//		}
		System.out.println(sql.toString());
		List<Map> list = dao.pageQueryForList(sql.toString(), params);
		return list;
	}

	public void updateReport(String id, String report) throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO("dbCsdb");
		String sql = "update DB_CSDB.T_COGNOS_REPORT set reportContext=? where id='"
				+ id + "'";
		System.out.println(sql);
		List list = new ArrayList();
		list.add(report);
		dao.execute(sql, list);
		//delete cache
		synchronized(reportCache){
			TCognosReportBO bo = new TCognosReportBO();
			bo.setId(id);
			ReportModel model = reportCache.remove(bo);
			if(model != null)
				log.info("Delete Cache: [id=" + id + "]");
		}
	}
	
	//停用/启用报表
	public int updateValid(String id, String isValid)  {
		IPersistenceDAO dao = this.getPersistenceDAO("dbCsdb");
		String sql = "update DB_CSDB.T_COGNOS_REPORT set isValid=? where id=?";
		List list = new ArrayList();
		list.add(isValid);
		list.add(id);
		int result = 0;
		try{
			result = dao.execute(sql, list);
		}catch(OptimusException e){
			e.printStackTrace();
		}
		return result;
		//delete cache
		
	}

	//查询报表平衡关系
	@SuppressWarnings("rawtypes")
	public List<Map> queryBalanceInfo(final String reportName, final String reportType) {
		IPersistenceDAO dao = this.getPersistenceDAO("dbCsdb");
		String sql = "select * from DB_CSDB.T_REPORT_BALANCEINFO where reportname=? and reporttype=?";
		List<String> params = new ArrayList<String>(){
			private static final long serialVersionUID = 1L;
			{add(reportName);
			 add(reportType);}
		};
		try {
			return dao.queryForList(sql, params);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//for check balance
	@Override
	public ReportModel getReport(TCognosReportBO queryInfo) {
		if(queryInfo == null)
			return null;
		//check cache
		synchronized(reportCache){
			if(reportCache.get(queryInfo) != null){
				log.info("Cache Hit: [" + queryInfo + "]");
				return reportCache.get(queryInfo);
			}
		}
		String sql = null;
		List<String> params = new ArrayList<String>(6);
		if(queryInfo.getId() != null){
			params.add(queryInfo.getId());
			sql = "select regcode,reporttype,reportparamters,reportname,mouth,year,reportcontext from DB_CSDB.T_COGNOS_report  where  id=?"; 
		}else{
			params.add(queryInfo.getRegcode());
			params.add(queryInfo.getReporttype());
			params.add(queryInfo.getReportparamters());
			params.add(queryInfo.getReportname());
			params.add(queryInfo.getMouth());
			params.add(queryInfo.getYear());
			sql = "select id,reportcontext from DB_CSDB.T_COGNOS_report  where regcode=? and reporttype=? and " + 
		             "reportparamters=? and reportname=? and mouth=? and year=?";
		}
		@SuppressWarnings("rawtypes") List<Map> list;
		ReportModel model = null;
		try {
			list = this.getPersistenceDAO("dbCsdb").queryForList(sql, params);
			@SuppressWarnings("rawtypes") Map result = null;
			if (list.size() > 0 && list != null) {
				result = list.get(0);
			}
			if(result != null){
				if(queryInfo.getId() != null){
					queryInfo.setRegcode((String) result.get("regcode"));
					queryInfo.setReporttype((String) result.get("reporttype"));
					queryInfo.setReportparamters((String) result.get("reportparamters"));
					queryInfo.setReportname((String) result.get("reportname"));
					queryInfo.setMouth((String) result.get("mouth"));
					queryInfo.setYear((String) result.get("year"));
				}else{
					queryInfo.setId((String) result.get("id"));
				}
				final HTMLTableParser parser = ParserFactory.createParser(queryInfo);
				model =  parser.parse((String)(result.get("reportcontext")));
				model.setReportInfo(queryInfo);
				synchronized(reportCache) {//cache :add or update
					reportCache.put(queryInfo, new ReportLimitRowsModel(model));
				}
			}
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return model;
	}
	
	public static void main(String[] args) {
		CognosService service = new CognosService();
		System.out.println(service.getPersistenceDAO());
	}
	
	public Map<String,List<Map>> getReportNames(String upType){
		IPersistenceDAO dao = this.getPersistenceDAO("dbCsdb");
		String	sql="select reportName,cognospath,reporttype from report_from_cognos_info where uptype like ? and status='1' and issystem='1'";
		String sql1="select reportName,cognospath,reporttype from report_from_cognos_info where uptype like ? and status='1' and issystem='1' and (reporttype='1' or reportname like '%省局自定义%' or reportname like '%本期登记%') and instr(reportname,'前海蛇口')<1";
		//String sql="select reportName,cognospath,reporttype from report_from_cognos_info where uptype like ? and reportname like '%消保%' or reportname='综合2表' or reportname='综合3表'";
		//String sql="select reportName,cognospath,reporttype from report_from_cognos_info where uptype like ? and reportname='综合3表'";
		//String sql="select reportName,cognospath,reporttype from report_from_cognos_info where uptype like ?  and reportname='市场和质量监督管理统计'";
		System.out.println("upType="+upType);
		List<String> params=new ArrayList<String>(1);
		params.add(upType);
		Map<String,List<Map>> map=new HashMap();
		try {
			map.put("jibao", dao.queryForList(sql1, params));
			map.put("yuebao", dao.queryForList(sql, params));
			if (map==null||map.size()==0) 
				return null;
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public List<Map> getReportNamesJ(String upType){
		IPersistenceDAO dao = this.getPersistenceDAO("dbCsdb");
		//String sql="select reportName,cognospath,reporttype from report_from_cognos_info where uptype like ? and status='1' and issystem='1' and (reporttype='1' or reportname like '%省局自定义%' or reportname like '%本期登记%')";
		//String sql="select reportName,cognospath,reporttype from report_from_cognos_info where uptype like ? and  reportname='市场和质量监督管理统计'";
		//String sql="select reportName,cognospath,reporttype from report_from_cognos_info where uptype like ? and  reportname='市场和质量监督管理统计' and reporttype='4'";
		String sql="select reportName,cognospath,reporttype from report_from_cognos_info where uptype like ? and  reportname like '%前海蛇口自贸片区%' and reporttype='1'";
		/*String sql=
				"select reportName,cognospath,reporttype from report_from_cognos_info where   (\n" +
				" reportname='深圳市企业统计表（按行业、行政区域分组）(本期登记)'\n" + 
				"or reportname='深圳市市场主体管辖区域统计表(按企业类型分组)(本期登记)'\n" + 
				"or reportname='深圳市法人企业统计表（按行业、行政区域分组）(本期登记)'\n" + 
				")\n" + 
				"and '4' like ?";*/
		System.out.println("upType="+upType);
		List<String> params=new ArrayList<String>(1);
		params.add(upType);
		List<Map> res=null;
		try {
			res= dao.queryForList(sql, params);
			if (res==null||res.size()==0) 
				return null;
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return res;
	}
	//获取12315报表基本信息
		public Map<String,List<Map>> getReportNamesFor12315(String upType){
			IPersistenceDAO dao = this.getPersistenceDAO("dbCsdb");
			//String sql="select reportName,cognospath,reporttype from report_from_cognos_info where uptype like ? and status=1";
			//String sql1="select reportName,cognospath,reporttype from report_from_cognos_info where uptype like ? and  reporttype='2' or reporttype='3'";
			String sql=
					"select reportName,cognospath,reporttype from report_from_cognos_info where( reportname like '%消保%' or reportname='综合2表' or  reportname='综合3表' or reportname='动产抵押登记情况统计表' )and reporttype ='1' and uptype like (case  when (substr(to_char(sysdate, 'yyyymmdd'), 5, 2) = '03' or\n" +
							"                              substr(to_char(sysdate, 'yyyymmdd'), 5, 2) = '09') then  '%2%'\n" + 
							"                         when substr(to_char(sysdate, 'yyyymmdd'), 5, 2) = '06' then   '%3%'\n" + 
							"                         when substr(to_char(sysdate, 'yyyymmdd'), 5, 2) = '12' then   '%4%'\n" + 
							"                         else   '%1%'  end)";
			String sql1="select reportName,cognospath,reporttype from report_from_cognos_info where( reportname like '%消保%' or reportname='综合2表' or  reportname='综合3表' or reportname='动产抵押登记情况统计表')and reporttype ='1' and uptype like '%1%'";
			Map<String,List<Map>> res=new HashMap<String, List<Map>>();
			try {
				res.put("jibao", dao.queryForList(sql, null));
				res.put("yuebao", dao.queryForList(sql1, null));
				if (res==null||res.size()==0) 
					return null;
			} catch (OptimusException e) {
				e.printStackTrace();
			}
			return res;
		}

	public String[] getRootPath(String upType, String reportType) {
		// TODO Auto-generated method stub
		return null;
	}


	public List<Map> getReportNamesFor12315g(String string) {
		// TODO Auto-generated method stub
		IPersistenceDAO dao = this.getPersistenceDAO("dbCsdb");
		//String sql="select reportName,cognospath,reporttype from report_from_cognos_info where uptype like ? and status=1";
		//String sql1="select reportName,cognospath,reporttype from report_from_cognos_info where uptype like ? and  reporttype='2' or reporttype='3'";
		String sql=
				"select reportName,cognospath,reporttype from report_from_cognos_info where（ reportname like '%消保%' or reportname='综合2表' or  reportname='综合3表'）and reporttype ='4' and uptype like (case  when (substr(to_char(sysdate, 'yyyymmdd'), 5, 2) = '03' or\n" +
						"                              substr(to_char(sysdate, 'yyyymmdd'), 5, 2) = '09') then  '%2%'\n" + 
						"                         when substr(to_char(sysdate, 'yyyymmdd'), 5, 2) = '06' then   '%3%'\n" + 
						"                         when substr(to_char(sysdate, 'yyyymmdd'), 5, 2) = '12' then   '%4%'\n" + 
						"                         else   '%1%'  end)";

		List<Map> res=null;
		try {
			res= dao.queryForList(sql, null);
			if (res==null||res.size()==0) 
				return null;
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return res;
	}


	public List<Map> getHomePage() {
		// TODO Auto-generated method stub
		IPersistenceDAO dao = this.getPersistenceDAO("dc_dc");
		List list1=new ArrayList();
		list1.add("1");
		String sql=
				"select b.dep_name as name,'pin' as symbol,count(*) as value from dc_ra_mer_base_query a,DC_JG_sys_right_department b where a.regorg=sys_right_department_id and regstate='1' and regorg like'4%' and 1=? group by b.dep_name";
		List<Map> list=new ArrayList<Map>();
		try {
			list=dao.queryForList(sql, list1);
		} catch (OptimusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Map> getHomePage1() {
		// TODO Auto-generated method stub
		IPersistenceDAO dao = this.getPersistenceDAO("dc_dc");
		List list1=new ArrayList();
		list1.add("1");
		String sql=
"select (CASE WHEN A.opetype='GT' THEN '个体工商户' else '企业' end) as t,count(1) sl\n" +
"from dc_ra_mer_base_query a,DC_JG_sys_right_department b\n" + 
"where a.regorg=sys_right_department_id and regstate='1' and regorg like'4%' and 1=? \n" + 
"group by (CASE WHEN A.OPetype='GT' THEN '个体工商户' else '企业' end)\n" + 
"order by t desc";
		List<Map> list=new ArrayList<Map>();
		try {
			list=dao.queryForList(sql, list1);
		} catch (OptimusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}


	
}
