package com.gwssi.diZhi.serivce;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.GregorianCalendar;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.application.log.aspect.LogUtil;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;
import com.gwssi.optimus.plugin.auth.service.AuthService;
import com.gwssi.yaoxie.service.YaoXieQyService;

@Service
public class DiZhiQuerySerivce  extends BaseService{
	private static  Logger log=Logger.getLogger(YaoXieQyService.class);
	
	
	/**
	 * 中心库  数据源
	 */
	private static final String DATASOURS_DC_DC ="dc_dc";
	
	

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
	
	
	
	public List<Map<String, Object>> queryCode_value() throws OptimusException {
		String sql = "SELECT T.OLD_CODE_NAME  as text ,T.OLD_CODE_VALUE as value FROM CODEDATA T WHERE CODE_INDEX = 'CD71'";
		IPersistenceDAO dao_dc=getPersistenceDAO("dc_dc");
		List list = null;
		
		list = dao_dc.queryForList(sql.toString(),null);
		 
		return list;
		
	}



	public String getListCount(Map<String, String> form) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		List list=new ArrayList();
		commonMethod(form, sql, list);
		String testsql =  "select count(1) count from ("+sql+")";
		return dao.queryForList(testsql.toString(), list).get(0).get("count").toString();
	}



	public List<Map> getList(Map<String, String> map, HttpServletRequest httpRequest) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql = new StringBuffer();
		List list=new ArrayList();
		commonMethod(map, sql, list);
		String testsql =  "select * from ("+sql+")  e where rownum <= 100";
		List<Map> pageQueryForList = dao.pageQueryForList(testsql.toString(),list);
		LogUtil.insertLog("地址变更企业查询", testsql, list.toString(), httpRequest, dao);
		return pageQueryForList;
	}
	
	
	
	private void commonMethod(Map map, StringBuffer sql, List list) {
		if (map!=null) {
			
			
			String bgQianSelect  = (String) map.get("bgQianSelect");
			String bgHouSelect  = (String) map.get("bgHouSelect");
			
			String  SpQymark = (String) map.get("isZaiRuYiChang");//--是否在异常名录FLAG, 0：否，1：是
			//下面是分三种情况进行判断查询：-1是全部，0：否，1：是
			if ("-1".equals(SpQymark)) {//全部
				sql.append("select * from DC_QY_BG_VIEW t where 1=1");
			}else if("否".equals(SpQymark)){//0是否
				sql.append("select * from DC_QY_BG_VIEW t where t.MS_TYPE = '否' ");
			}else if("是".equals(SpQymark)){//1是
				sql.append("select * from DC_QY_BG_VIEW t where t.MS_TYPE =  '是' ");
			}
		
			
			
			
			if (map.get("entname")!=null) {
				Object object2 = map.get("entname");
				if (object2.toString().trim().length()>0) {
					//sql.append(" and instr(t.entname,?,1,1)<>0 ");
					sql.append(" and instr (T.entname,?)>0");
					list.add(object2.toString().trim());
				}	
			}
			if (map.get("unif")!=null) {
				Object object2 = map.get("unif");
				if (object2.toString().trim().length()>0) {
					sql.append(" and t.UNIFSOCICREDIDEN = ?");
					list.add(object2.toString().trim());
				}
			} 
			
			
			
			if (map.get("estdate_begin")!=null) {
				Object object2 = map.get("estdate_begin");
				if (object2.toString().trim().length()>0) {
					sql.append(" and t.estdate >= to_date(?,'yyyy-mm-dd')");
					list.add(object2.toString().trim());
				}
			}
			
			if (map.get("estdate_end")!=null) {
				Object object2 = map.get("estdate_end");
				if (object2.toString().trim().length()>0) {
					sql.append(" and t.estdate <= to_date(?,'yyyy-mm-dd')");
					list.add(object2.toString().trim());
				}
			}
			
			
			if (map.get("bgdate_begin")!=null) {
				Object object2 = map.get("bgdate_begin");
				if (object2.toString().trim().length()>0) {
					sql.append(" and t.ALTDATE >= to_date(?,'yyyy-mm-dd')");
					list.add(object2.toString().trim());
				}
			}
			
			if (map.get("bgdate_end")!=null) {
				Object object2 = map.get("bgdate_end");
				if (object2.toString().trim().length()>0) {
					sql.append(" and t.ALTDATE <= to_date(?,'yyyy-mm-dd')");
					list.add(object2.toString().trim());
				}
			}
			
			/*if (map.get("altBefore")!=null&&map.get("altAfter")!=null) {
				Object object2 = map.get("altBefore");
				Object object3 = map.get("altAfter");
				if (object2.toString().trim().length()>0) {
					sql.append(" and ( instr (T.ALTAF,?)>0  AND instr (T.ALTBE,?)>0 )");//instr(title,'手册')>0 
					list.add(object2.toString().trim());
					list.add(object3.toString().trim());
				}
			}*/
			
			
			
			
			//0等于 1 包含 
			
			if (map.get("altBefore")!=null&&bgQianSelect.equals("0")) {
				Object object2 = map.get("altBefore");
				if (object2.toString().trim().length()>0) {
					sql.append(" and  instr (T.ALTBE,?)=0 ");//instr(title,'手册')>0 
					list.add(object2.toString().trim());
				}
			}
			
			if (map.get("altBefore")!=null&&bgQianSelect.equals("1")) {
				Object object2 = map.get("altBefore");
				if (object2.toString().trim().length()>0) {
					sql.append(" and  instr (T.ALTBE,?)>0 ");//instr(title,'手册')>0 
					list.add(object2.toString().trim());
				}
			}
			
		
			if (map.get("altAfter")!=null&&bgHouSelect.equals("0")) {
				Object object2 = map.get("altAfter");
				if (object2.toString().trim().length()>0) {
					sql.append(" and  instr (T.ALTAF,?)=0 ");//instr(title,'手册')>0 
					list.add(object2.toString().trim());
				}
			}
			
			if (map.get("altAfter")!=null&&bgHouSelect.equals("1")) {
				Object object2 = map.get("altAfter");
				if (object2.toString().trim().length()>0) {
					sql.append(" and  instr (T.ALTAF,?)>0 ");//instr(title,'手册')>0 
					list.add(object2.toString().trim());
				}
			}
			
			
		
			if(!"否".equals(SpQymark)){
				//temps
				if (map.get("yiChangType")!=null) {
					Object object2 = map.get("yiChangType");
					if (object2.toString().trim().length()>0) {
						sql.append(" and t.ABNORMALTYPE  in ( ");
						String temp = object2.toString().trim();
						if(temp.contains(",")) {
							String [] arr = temp.split(",");  
							for (int i = 0; i < arr.length; i++) {
								sql.append("?");
								list.add(arr[i]);
								if (i < arr.length-1) {
									sql.append(",");
								}
							}
						}else {
							sql.append("?");
							sql.append(",");
							list.add(temp);
						}
						
					
						sql.append(" ) ");
					}
				}
			}
		
		}
	}


	
	
	
	public String exportExcelCount(Map map, HttpServletRequest httpServletRequest) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		List list=new ArrayList();
		commonMethod(map, sql, list);
		//String testsql =  "select * from ("+sql+")  e where rownum <= 30000";
		String testsql =  "select count(1) count from ("+sql+")";
		return dao.queryForList(testsql.toString(), list).get(0).get("count").toString();
	}
	




	public List<Map> exportExcel(Map map, HttpServletRequest httpServletRequest) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		List list=new ArrayList();
		commonMethod(map, sql, list);
		//String testsql =  "select * from ("+sql+")  e where rownum <= 30000";
		String testsql =  "select * from ("+sql+")  e where rownum <= 3000"; //修改为8万条数据
		List<Map> pageQueryForList = dao.queryForList(testsql.toString(),list);
		LogUtil.insertLog("地址变更企业查询导出", testsql.toString(), list.toString(), httpServletRequest, dao);
		return typechage(pageQueryForList);
			
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
