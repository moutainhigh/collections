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

@Service(value = "CPRShowService")
public class CPRShowService extends BaseService{
	
	/**
     * 12315查询 列表展示
     * @param map
	 * @param req 
     * @return
     * @throws OptimusException
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getCPRList(Map map, HttpServletRequest req) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql=new StringBuffer();
		sql.append("select * from dc_cpr_infoware t where 1=1 ");
		List list=new ArrayList();
		if (map!=null) {
			Object object = map.get("regino");
			if (object!=null) {
				if (object.toString().trim().length()>0) {
					sql.append(" and t.regino = ?");
					list.add(object.toString().trim());
				}
			}
			if (map.get("incform")!=null) {
				Object object2 = map.get("incform");
				if (object2.toString().trim().length()>0) {
					sql.append(" and t.incform = ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("inftype")!=null) {
				Object object2 = map.get("inftype");
				if (object2.toString().trim().length()>0) {
					sql.append(" and t.inftype = ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("infoori")!=null) {
				Object object2 = map.get("infoori");
				if (object2.toString().trim().length()>0) {
					sql.append(" and t.infoori = ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("regdepname")!=null) {
				Object object2 = map.get("regdepname");
				if (object2.toString().trim().length()>0) {
					sql.append(" and t.regdepname = ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("regtime_begin")!=null) {
				Object object2 = map.get("regtime_begin");
				if (object2.toString().trim().length()>0) {
					sql.append(" and t.regtime >= to_date(?,'yyyy-mm-dd')");
					list.add(object2.toString().trim());
				}
			}
			
			if (map.get("regtime_end")!=null) {
				Object object2 = map.get("regtime_end");
				if (object2.toString().trim().length()>0) {
					sql.append(" and t.regtime <= to_date(?,'yyyy-mm-dd')");
					list.add(object2.toString().trim());
				}
			}
		}
		String testsql =  "select * from ("+sql+")  e where rownum <= 100";
		List<Map> res = dao.pageQueryForList(testsql.toString(),list);
		LogUtil.insertLog("12315查询", testsql, list.toString(), req, dao);
		return res;
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getCPRListCount(Map map) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql=new StringBuffer();
		sql.append("select * from dc_cpr_infoware t where 1=1 ");
		List list=new ArrayList();
		if (map!=null) {
			Object object = map.get("regino");
			if (object!=null) {
				if (object.toString().trim().length()>0) {
					sql.append(" and t.regino = ?");
					list.add(object.toString().trim());
				}
			}
			if (map.get("incform")!=null) {
				Object object2 = map.get("incform");
				if (object2.toString().trim().length()>0) {
					sql.append(" and t.incform = ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("inftype")!=null) {
				Object object2 = map.get("inftype");
				if (object2.toString().trim().length()>0) {
					sql.append(" and t.inftype = ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("infoori")!=null) {
				Object object2 = map.get("infoori");
				if (object2.toString().trim().length()>0) {
					sql.append(" and t.infoori = ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("regdepname")!=null) {
				Object object2 = map.get("regdepname");
				if (object2.toString().trim().length()>0) {
					sql.append(" and t.regdepname = ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("regtime_begin")!=null) {
				Object object2 = map.get("regtime_begin");
				if (object2.toString().trim().length()>0) {
					sql.append(" and t.regtime >= to_date(?,'yyyy-mm-dd')");
					list.add(object2.toString().trim());
				}
			}
			
			if (map.get("regtime_end")!=null) {
				Object object2 = map.get("regtime_end");
				if (object2.toString().trim().length()>0) {
					sql.append(" and t.regtime <= to_date(?,'yyyy-mm-dd')");
					list.add(object2.toString().trim());
				}
			}
		}
		String testsql =  "select count(1) count from ("+sql+")";
		return dao.queryForList(testsql.toString(), list).get(0).get("count").toString();
		
	}

	/**
	 * 信息件信息
	 * @param str
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryCPRInfoWare(Map<String, String> str) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from v_dc_cpr_infoware t where t.infowareid = ? ");
		Object object = str.get("infowareid");
		params.add(object.toString().trim());
		return typechage(dao.pageQueryForList(sql.toString(), params));
	}
	
	
	/**
	 * 涉及主体信息
	 * @param str
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> CPRInvolvedMainQuery(Map<String, String> str) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from v_dc_cpr_involved_main t where t.invmaiid = ? ");
		Object object = str.get("invmaiid");
		params.add(object.toString().trim());
     	return typechage(dao.pageQueryForList(sql.toString(), params));
	}
	
	/**
	 * 涉及客体信息
	 * @param str
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> CPRInvolvedObjectQuery(Map<String, String> str) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from v_dc_cpr_involved_object t where t.invobjid = ? ");
		Object object = str.get("invobjid");
		params.add(object.toString().trim());
     	return typechage(dao.pageQueryForList(sql.toString(), params));
	}
	/**
	 * 信息提供方信息
	 * @param str
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> CPRInfoProviderQuery(Map<String, String> str) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from v_dc_cpr_info_provider t where t.infproid = ? ");
		Object object = str.get("infproid");
		params.add(object.toString().trim());
     	return typechage(dao.pageQueryForList(sql.toString(), params));
	}
	
	/**
	 * 分流信息
	 * @param str
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> CPRDispatchQuery(Map<String, String> str) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from v_dc_cpr_dispatch t where t.infowareid = ? ");
		Object object = str.get("infowareid");
		params.add(object.toString().trim());
     	return typechage(dao.pageQueryForList(sql.toString(), params));
	}
	
	/**
	 * 调查信息列表
	 * @param str
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> CPRInvestigationQueryList(Map<String, String> str) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from v_dc_cpr_investigation t where t.infowareid = ? ");
		params.add(str.get("infowareid"));
		List<Map> res = typechage(dao.pageQueryForList(sql.toString(), params));
		return res;
	}
	
	
	/**
	 * 调查信息列表
	 * @param str
	 * @return
	 * @throws OptimusException
	 */
	public Map CPRInvestigationDetailQuery(String str) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from v_dc_cpr_investigation t where t.investigationid = ? ");
		params.add(str);
	//	List<Map> res = dao.pageQueryForList(sql.toString(), params);
		
     	return dao.queryForList(sql.toString(), params).get(0);
	}
	
	/**
	 * 调解信息
	 * @param str
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> CPRMediationQuery(Map<String, String> str) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from v_dc_cpr_mediation t where t.mediationid = ? ");
		Object object = str.get("mediationid");
		params.add(object.toString().trim());
     	return typechage(dao.pageQueryForList(sql.toString(), params));
	}
	
	/**
	 * 反馈信息
	 * @param str
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> CPRFeedbackQuery(Map<String, String> str) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from v_dc_cpr_feedback t where t.feedbackid = ? ");
		Object object = str.get("feedbackid");
		params.add(object.toString().trim());
     	return typechage(dao.pageQueryForList(sql.toString(), params));
	}
	
	
	/**
	 * 当事人信息
	 * @param str
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryCPRLitigantInfo(Map<String, String> str) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		if (str.get("litiganttype").toString().trim().equals("2")) {
			sql.append("select * from  v_dc_CPR_sub_party t where t.FOREIGNKEY = ? ");
			params.add(str.get("id"));
		}
		else if (str.get("litiganttype").toString().trim().equals("1")) {
			sql.append("select * from  v_dc_CPR_sub_partybus t where t.FOREIGNKEY = ? ");
			params.add(str.get("id"));
		}else {
			sql.append("select * from  v_dc_CPR_sub_party t where t.FOREIGNKEY = ? ");
			params.add(str.get("id"));
		}
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
