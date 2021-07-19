package com.gwssi.comselect.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.gwssi.application.log.aspect.LogUtil;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;
import com.gwssi.optimus.plugin.auth.service.AuthService;


@Service
public class BatchPersonService extends BaseService {

	@SuppressWarnings("rawtypes")
	public List getPersonListMap(List params,HttpServletRequest httpServletRequest) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");

		Map map = null;
		StringBuffer sql = null;
		List list =  null;
		List result = null;
		List total = new ArrayList(); 
		for (int i = 0; i < params.size(); i++) {
			sql =  new StringBuffer();
			sql.append("select a.entname,a.unifsocicrediden,d.old_code_name as enttype,a.regcap,to_char(a.estdate,'yyyy-MM-dd') as estdate,(case when a.entstatus='2' or a.entstatus='4' then to_char(a.apprdate,'yyyy-MM-dd') end)as apprdate,e.old_code_name as entstatus,b.persname,b.cerno,b.perflag,(case when b.perflag='股东' then '出资额'||c.old_code_name|| TO_CHAR(b.SUBCONAM)||'万元，出资比例'||TO_CHAR(b.CONPROP)||'%' when b.perflag='高管人员' then b.post end) as memo ");
			sql.append("from dc_ra_mer_base a,(select * from v_persons_query where 1=1 and ");
			map = (Map) params.get(i);
			list = new ArrayList();
			if (map != null) {
					if (map.get("lineTxt") != null) {
						if(((String) map.get("lineTxt")).trim().length() > 0 && (((String) map.get("lineTxt")).trim().length() == 18 || ((String) map.get("lineTxt")).trim().length() == 15 )){
							String cerno = (String) map.get("lineTxt");
							if (cerno.trim().length() > 0&& cerno.trim().length() == 18) {
								sql.append("  cerno = ? or cerno=FUN_IDCARD_18TO15('"+ cerno.trim() + "') ");
								list.add(cerno.trim());
								sql.append(" ) b,(select * from codedata where code_index='CA04') c,(select * from codedata where code_index='CA16') d,(select * from codedata where code_index='CA19') e where a.id=b.main_tb_id and a.currency=c.old_code_value(+) and a.enttype=d.old_code_value(+) and a.entstatus=e.old_code_value(+)  order by a.entname ");
								result  =  dao.queryForList(sql.toString(),list);
								// list.add(cerno.trim());
							} else if (cerno.trim().length() > 0&& cerno.trim().length() == 15) {
								sql.append("  cerno = ? or cerno=FUN_IDCARD_15TO18('"+ cerno.trim() + "')");
								list.add(cerno.trim());
								sql.append(" ) b,(select * from codedata where code_index='CA04') c,(select * from codedata where code_index='CA16') d,(select * from codedata where code_index='CA19') e where a.id=b.main_tb_id and a.currency=c.old_code_value(+) and a.enttype=d.old_code_value(+) and a.entstatus=e.old_code_value(+)  order by a.entname");
								result  =  dao.queryForList(sql.toString(),list);
								// list.add(cerno.trim());
							}
							if(result!=null&&result.size()>0){
								for(int j = 0; j < result.size(); j++) {
									Map mapResult = (Map) result.get(j);
									total.add(mapResult);
								}
							}
						/*	else{
								Map newMap = new HashMap();
								newMap.put("cerno", (String) map.get("lineTxt"));
								newMap.put("memo", "未查到记录！");
								total.add(newMap);
							}*/
						}
						/*else{
							Map newMap1 = new HashMap();
							newMap1.put("cerno", (String) map.get("lineTxt"));
							newMap1.put("memo", "身份证号位数不对！");
							total.add(newMap1);
						}*/
					}
			}else{
				return null;
			}
			
		}
		LogUtil.insertLog("人员查询", "", "", httpServletRequest, dao);
		return total;
	}
	
	
	
	
	 /**
     * 人员查询列表展示
     * @param map
     * @param httpServletRequest 
     * @return
     * @throws OptimusException
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getPersonList(Map map, HttpServletRequest httpServletRequest) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql=new StringBuffer();
		List list=new ArrayList();
		if (map!=null) {
			if(map.get("select")!=null){
				String select = (String)map.get("select");
				if("0".equals(select)){//历史记录
					sql.append("select  func_getcode('C00017',a.regorg) as regorg,a.dom, b.type_flag,a.id as pripid,a.pripid as entid,a.opetype,a.regno,a.entstatus as status,a.enttype as type,a.entname,a.unifsocicrediden,d.old_code_name as enttype,a.regcap,to_char(a.estdate,'yyyy-MM-dd') as estdate,(case when a.entstatus='2' or a.entstatus='4' then to_char(a.apprdate,'yyyy-MM-dd') end)as apprdate,e.old_code_name as entstatus,b.persname,b.cerno,b.perflag,(case when b.perflag='股东' then '出资额'||c.old_code_name|| TO_CHAR(b.SUBCONAM)||'万元，出资比例'||TO_CHAR(b.CONPROP)||'%' when b.perflag='高管人员' then b.post end) as memo ");
					sql.append("from dc_ra_mer_base a,(select * from v_persons_query_old where 1=1  ");
				}else{// 当前记录
					sql.append("select  func_getcode('C00017',a.regorg) as regorg,a.dom, a.id as pripid,a.pripid as entid,a.opetype,a.regno,a.entstatus as status,a.enttype as type,a.entname,a.unifsocicrediden,d.old_code_name as enttype,a.regcap,to_char(a.estdate,'yyyy-MM-dd') as estdate,(case when a.entstatus='2' or a.entstatus='4' then to_char(a.apprdate,'yyyy-MM-dd') end)as apprdate,e.old_code_name as entstatus,b.persname,b.cerno,b.perflag,(case when b.perflag='股东' then '出资额'||c.old_code_name|| TO_CHAR(b.SUBCONAM)||'万元，出资比例'||TO_CHAR(b.CONPROP)||'%' when b.perflag='高管人员' then b.post end) as memo ");
					sql.append("from dc_ra_mer_base a,(select * from v_persons_query where 1=1  ");
				}
			}
			if (map.get("cerno")!=null) {
				String cerno = (String)map.get("cerno");
				if (cerno.trim().length()>0 && cerno.trim().length()==18) {
					sql.append(" and cerno = ? or cerno=FUN_IDCARD_18TO15('"+cerno.trim()+"')");
					list.add(cerno.trim());
				//	list.add(cerno.trim());
				}else if(cerno.trim().length()>0 && cerno.trim().length()==15){
					sql.append(" and cerno = ? or cerno=FUN_IDCARD_15TO18('"+cerno.trim()+"')");
					list.add(cerno.trim());
					//list.add(cerno.trim());
				}
			}
			if (map.get("persname")!=null) {
				Object object2 = map.get("persname");
				if (object2.toString().trim().length()>0) {
					sql.append(" and persname = ?");
					list.add(object2.toString().trim());
				}
			}
			sql.append(" ) b,(select * from codedata where code_index='CA04') c,(select * from codedata where code_index='CA16') d,(select * from codedata where code_index='CA19') e where a.id=b.main_tb_id and a.currency=c.old_code_value(+) and a.enttype=d.old_code_value(+) and a.entstatus=e.old_code_value(+)  order by a.entname ");
			
		}
		String testsql =  "select * from ("+sql+")  e where rownum <= 100";
		List<Map> pageQueryForList = dao.queryForList(sql.toString(),list);
		LogUtil.insertLog("人员查询", sql.toString(), list.toString(), httpServletRequest, dao);
		return pageQueryForList;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getPersonListCount(Map map) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql=new StringBuffer();
		sql.append("select a.entname,a.unifsocicrediden,b.persname,b.cerno,b.perflag,(case when b.perflag='股东' then '出资额'||c.old_code_name|| TO_CHAR(b.SUBCONAM)||'万元，出资比例'||TO_CHAR(b.CONPROP)||'%'  when  b.perflag = '高管人员' then b.post end) as memo ");
		sql.append("from dc_ra_mer_base a,(select * from v_persons_query where 1=1  ");
		List list=new ArrayList();
		if (map!=null) {
			if(map.get("select")!=null){
				String select = (String)map.get("select");
				if("0".equals(select)){//历史记录
					sql.append("select b.type_flag,a.id as pripid,a.pripid as entid,a.opetype,a.regno,a.entstatus as status,a.enttype as type,a.entname,a.unifsocicrediden,d.old_code_name as enttype,a.regcap,to_char(a.estdate,'yyyy-MM-dd') as estdate,(case when a.entstatus='2' or a.entstatus='4' then to_char(a.apprdate,'yyyy-MM-dd') end)as apprdate,e.old_code_name as entstatus,b.persname,b.cerno,b.perflag,(case when b.perflag='股东' then '出资额'||c.old_code_name|| TO_CHAR(b.SUBCONAM)||'万元，出资比例'||TO_CHAR(b.CONPROP)||'%' when b.perflag='高管人员' then b.post end) as memo ");
					sql.append("from dc_ra_mer_base a,(select * from v_persons_query_old where 1=1  ");
				}else{// 当前记录
					sql.append("select a.id as pripid,a.pripid as entid,a.opetype,a.regno,a.entstatus as status,a.enttype as type,a.entname,a.unifsocicrediden,d.old_code_name as enttype,a.regcap,to_char(a.estdate,'yyyy-MM-dd') as estdate,(case when a.entstatus='2' or a.entstatus='4' then to_char(a.apprdate,'yyyy-MM-dd') end)as apprdate,e.old_code_name as entstatus,b.persname,b.cerno,b.perflag,(case when b.perflag='股东' then '出资额'||c.old_code_name|| TO_CHAR(b.SUBCONAM)||'万元，出资比例'||TO_CHAR(b.CONPROP)||'%' when b.perflag='高管人员' then b.post end) as memo ");
					sql.append("from dc_ra_mer_base a,(select * from v_persons_query where 1=1  ");
				}
			}
			if (map.get("cerno")!=null) {
				String cerno = (String)map.get("cerno");
				if (cerno.trim().length()>0 && cerno.trim().length()==18) {
					sql.append(" and cerno = ? or cerno=FUN_IDCARD_18TO15('"+cerno.trim()+"')");
					list.add(cerno.trim());
				//	list.add(cerno.trim());
				}else if(cerno.trim().length()>0 && cerno.trim().length()==15){
					sql.append(" and cerno = ? or cerno=FUN_IDCARD_15TO18('"+cerno.trim()+"')");
					list.add(cerno.trim());
					//list.add(cerno.trim());
				}
			}
			if (map.get("persname")!=null) {
				Object object2 = map.get("persname");
				if (object2.toString().trim().length()>0) {
					sql.append(" and persname = ?");
					list.add(object2.toString().trim());
				}
			}
			sql.append(" ) b,(select * from codedata where code_index='CA04') c,(select * from codedata where code_index='CA16') d,(select * from codedata where code_index='CA19') e where a.id=b.main_tb_id and a.currency=c.old_code_value(+) and a.enttype=d.old_code_value(+) and a.entstatus=e.old_code_value(+)  order by a.entname ");
			
		}
		String testsql =  "select count(1) count from ("+sql+")";
		return dao.queryForList(testsql.toString(), list).get(0).get("count").toString();
	}
	
	
	/**
	 * 人员批量查询角色
	 * @param type
	 * @param parm
	 * @return
	 * @throws OptimusException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<String> queryListUserRolesByUserId() throws OptimusException{
		//获取当前登陆人dejuese
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		if(user != null){
			String userId = user.getUserId();	
			List<Map> roleIdListByLoginName = new AuthService().getRoleIdByUsersRole(userId);
			
			ArrayList<String> roleCodes = new ArrayList<String>();
			for(Map roleMap : roleIdListByLoginName){
				String role_code = (String)roleMap.get("roleCode");
				roleCodes.add(role_code);
			}
			session.setAttribute("roleIdList", roleCodes);//将该用户角色List放入session
		
		}
		List<String> roleCodes = (List<String>)session.getAttribute("roleIdList");
		return roleCodes;
		
	}
	
	public List<Map> getBGDetall(String id, String cerno, String flag, String bgtype) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql=new StringBuffer();
		List params = new ArrayList();
		if("3".equals(flag)){//法定代表人
			sql.append("select * from V_PERSONS_FDDBRBG b where 1=1");
		}else if("1".equals(flag)){ //高管人员
			sql.append("select b.id,b.main_tb_id,b.persname,b.cerno,func_getcode('C00001',b.certype) as certype,func_getcode('C00005',a.POST) as post,a.bgtype from dc_ra_mer_member_ext a,dc_ra_mer_persons_ext b where a.id= b.id");
		}else if("2".equals(flag)){ //股东
			sql.append("select b.id, b.main_tb_id, a.inv, b.cerno, func_getcode('C00001',a.certype) as certype from dc_ra_mer_invest_ext a, dc_ra_mer_persons_ext b where a.id = b.id");
		}
		if(id != null){
			sql.append(" and b.main_tb_id = ?");
			params.add(id);
		}
		if (cerno.trim().length()>0 && cerno.trim().length()==18) {
			sql.append(" and (b.cerno = ? or b.cerno=FUN_IDCARD_18TO15('"+cerno.trim()+"'))");
			params.add(cerno.trim());
		}else if(cerno.trim().length()>0 && cerno.trim().length()==15){
			sql.append(" and b.cerno = ? or b.cerno=FUN_IDCARD_15TO18('"+cerno.trim()+"')");
			params.add(cerno.trim());
		}
		if(bgtype != null){
			sql.append(" and b.bgtype= ?");
			params.add(bgtype);
		}
		return dao.queryForList(sql.toString(), params);
		
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
					String format = "yyyy-MM-dd";
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