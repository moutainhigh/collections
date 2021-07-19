package com.gwssi.comselect.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.application.log.aspect.LogUtil;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

@Service(value = "caseShowService")
public class CaseShowService extends BaseService{
	
	/**
     * 案件查询 列表展示
     * @param map
	 * @param httpServletRequest 
     * @return
     * @throws OptimusException
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getCaseList(Map map, HttpServletRequest httpServletRequest) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql=new StringBuffer();
		sql.append("select * from DC_CASE_CASE t where 1=1 ");
		List list=new ArrayList();
		commonMethod(map, sql, list);
		String testsql =  "select * from ("+sql+")  e where rownum <= 100";
		List<Map> pageQueryForList = dao.pageQueryForList(testsql.toString(),list);
		LogUtil.insertLog("案件查询", testsql, list.toString(), httpServletRequest, dao);
		return pageQueryForList;
	}

	private void commonMethod(Map map, StringBuffer sql, List list) {
		if (map!=null) {
			Object object = map.get("caseno");
			if (object!=null) {
				if (object.toString().trim().length()>0) {
					sql.append(" and CASENO = ?");
					list.add(object.toString().trim());
				}
			}
			if (map.get("casestate")!=null) {
				Object object2 = map.get("casestate");
				if (object2.toString().trim().length()>0) {
					sql.append(" and casestate in ('51','52') ");
				}
			}
			if (map.get("casename")!=null) {
				Object object2 = map.get("casename");
				if (object2.toString().trim().length()>0) {
					sql.append(" and CASENAME = ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("litigantname")!=null) {
				Object object2 = map.get("litigantname");
				if (object2.toString().trim().length()>0) {
					sql.append(" and LITIGANTNAME = ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("litigantcerno")!=null) {
				Object object2 = map.get("litigantcerno");
				if (object2.toString().trim().length()>0) {
					sql.append(" and LITIGANTCERNO = ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("caseregistertime_begin")!=null) {
				Object object2 = map.get("caseregistertime_begin");
				if (object2.toString().trim().length()>0) {
					sql.append(" and t.CASEREGISTERTIME >= to_date(?,'yyyy-mm-dd')");
					list.add(object2.toString().trim());
				}
			}
			
			if (map.get("caseregistertime_and")!=null) {
				Object object2 = map.get("caseregistertime_end");
				if (object2.toString().trim().length()>0) {
					sql.append(" and t.CASEREGISTERTIME <= to_date(?,'yyyy-mm-dd')");
					list.add(object2.toString().trim());
				}
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getCaseListCount(Map map) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql=new StringBuffer();
		sql.append("select * from DC_CASE_CASE t where 1=1 ");
		List list=new ArrayList();
		commonMethod(map, sql, list);
		String testsql =  "select count(1) count from ("+sql+")";
		return dao.queryForList(testsql.toString(), list).get(0).get("count").toString();
	}

	/**
	 * 案件基本信息
	 * @param str
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryCaseBaseInfo(Map<String, String> str) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from v_dc_case_case t where t.id = ? ");
		params.add(str.get("id"));
		return typechage(dao.pageQueryForList(sql.toString(), params));
	}
	
	
	/**
	 * 违法行为及处罚信息信息
	 * @param str
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> casePenalizeInfoQuery(Map<String, String> str) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from v_dc_case_case_penalize t where t.caseid = ? ");
		params.add(str.get("id"));
		return typechage(dao.pageQueryForList(sql.toString(), params));
	}
	
	/**
	 * 当事人信息
	 * @param str
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryCaseLitigantInfo(Map<String, String> str) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		if (str.get("litiganttype").toString().trim().equals("2")) {
			sql.append("select * from  v_dc_case_sub_party t where t.FOREIGNKEY = ? ");
			params.add(str.get("id"));
		}
		else if (str.get("litiganttype").toString().trim().equals("1")) {
			sql.append("select * from  v_dc_case_sub_partybus t where t.FOREIGNKEY = ? ");
			params.add(str.get("id"));
		}else {
			sql.append("select * from  v_dc_case_sub_party t where t.FOREIGNKEY = ? ");
			params.add(str.get("id"));
		}
		return typechage(dao.pageQueryForList(sql.toString(), params));
	}
	
	/**
	 *案件源信息
	 * @param str
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryCaseSourceInfo(Map<String, String> str) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from v_dc_case_source t where t.casesourceno = ? ");
		params.add(str.get("id"));
		return typechage(dao.pageQueryForList(sql.toString(), params));
	}
	
	/**
	 *移送信息
	 * @param str
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryCaseMoveInfo(Map<String, String> str) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from v_dc_case_case_move t where t.caseid = ? ");
		params.add(str.get("id"));
		return typechage(dao.pageQueryForList(sql.toString(), params));
	}
	
	/**
	 * 类型转换(把GregorianCalendar 转换为String)
	 * @return
	 */
	public List<Map> typechage(List<Map> list){
		List<Map> changtype =new ArrayList<Map>();
		for(Map<String,Object> map1:list){
			
			Map<String,Object> newMap= new HashMap<String,Object>();
			for(String s :map1.keySet()){
				Object obj=map1.get(s);
				
				if (obj!=null&&(obj.getClass()==GregorianCalendar.class)){
					GregorianCalendar gcal =(GregorianCalendar)obj;
					String format = "yyyy-MM-dd HH:mm:ss";
					SimpleDateFormat formatter = new SimpleDateFormat(format);
					newMap.put(s,  formatter.format(gcal.getTime()).toString());
				}else{
					newMap.put(s, map1.get(s));
				}
			}
			changtype.add(newMap);
		}
		
		return  changtype;
	}
}
