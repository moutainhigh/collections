package com.gwssi.comselect.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gwssi.application.common.AppConstants;
import com.gwssi.application.log.aspect.LogUtil;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;
import com.gwssi.optimus.plugin.auth.service.AuthService;
import com.gwssi.trs.controller.CaseController;
import com.gwssi.util.QueryCodeSql;

@Service(value = "portalAppSelectService")
public class PortalAppSelectService extends BaseService{
	private static  Logger log=Logger.getLogger(PortalAppSelectService.class);
	/**
	 * 中心库  数据源
	 */
	private static final String DATASOURS_DC_DC ="dc_dc";
	Properties properties = ConfigManager.getProperties("UserRolesGet");
	String databasename = properties.getProperty("yyjc.database.username");
	
	
	public List<Map> getPortalAppList(Map map, HttpServletRequest httpServletRequest) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);

		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		List<Map> pageQueryForList = null;
		String userId = "";
		String userName = "";
		if(user != null){
			userId = user.getUserId().toUpperCase();
			userName = user.getUserName();
		}
		String userIp = getIp(httpServletRequest);//获取ip
		log.info("登录用户ID:"+userId+"=====用户名："+userIp+"=====用户IP："+userIp);
		String[] ips = userIp.split("\\.");
		if(ips!=null && ips.length>=3){//获取待匹配的IP（IP地址一部分）
			userIp=ips[0]+"."+ips[1];
			log.info("处理后用户IP："+userIp);
		}else{
			StringBuffer sql=new StringBuffer();
			sql.append("select * from (select * from "+databasename+".portal_appall t where t.ip is null or ( t.ip is not null and t.ip = '10.1' )) where 1=1 ");
			List list=new ArrayList();
			commonMethod(map, sql, list);
			pageQueryForList = dao.pageQueryForList(sql.toString(), list);
			return pageQueryForList;
		}
		List list1=new ArrayList();
		list1.add(userIp.trim());
		String  sql2=" select count(1) from "+databasename+".SM_OLD_SYS  t  where t.ip = ? ";
		int queryForInt = dao.queryForInt(sql2, list1);
		
		if(queryForInt>0){
			StringBuffer sql=new StringBuffer();
			sql.append("select * from (select * from "+databasename+".portal_appall t where  t.ip is null or ( t.ip is not null and t.ip = ? )) where 1=1 ");
			List list=new ArrayList();
			list.add(userIp.trim());
			commonMethod(map, sql, list);
			pageQueryForList = dao.pageQueryForList(sql.toString(),list);
		}else{
			StringBuffer sql=new StringBuffer();
			sql.append("select * from (select * from "+databasename+".portal_appall t where  t.ip is null or ( t.ip is not null and t.ip = '10.1' )) where 1=1 ");
			List list=new ArrayList();
			commonMethod(map, sql, list);
			pageQueryForList = dao.pageQueryForList(sql.toString(),list);
		}
	
	//	LogUtil.insertLog("投资人查询", sql.toString(), list.toString(), httpServletRequest, dao);
		return pageQueryForList;
	}

	private void commonMethod(Map map, StringBuffer sql, List list) {
		if (map!=null) {
				Object object = map.get("systemName");
				if (object!=null) {
					if (object.toString().trim().length()>0) {
						sql.append(" and system_name like ?");
						list.add("%"+object.toString().trim()+"%");
					}
				}
				if (map.get("pid")!=null) {
					Object object2 = map.get("pid");
					if (object2.toString().trim().length()>0) {
						sql.append(" and pid = ?");
						list.add(object2.toString().trim());
					}
				}
				if (map.get("effectiveMmarker")!=null) {
					Object object2 = map.get("effectiveMmarker");
					if (object2.toString().trim().length()>0) {
						sql.append(" and effective_marker = ?");
						list.add(object2.toString().trim());
					}
				}
		}
	}
	
	/**
	 * 详情信息
	 * @param id
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getPortalAppDetail(String str) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		List<String> list = new ArrayList<String>();
		StringBuffer sql=new StringBuffer();
		sql.append("select * from "+databasename+".portal_appall  t  where t.PK_SYS_INTEGRATION= ? ");
		list.add(str);
	
		List<Map> pageQueryForList = dao.pageQueryForList(sql.toString(), list);
		if(pageQueryForList !=null && pageQueryForList.size()>0){
			return  pageQueryForList.get(0);
		}
		return null;
	}
	
	/**
	 * 判断是否存在该处于启用状态的系统名称
	 * @param id
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getPortalAppBySystemName(String str) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		List<String> list = new ArrayList<String>();
		StringBuffer sql=new StringBuffer();
		sql.append("select * from "+databasename+".portal_appall  t  where t.PK_SYS_INTEGRATION= ? and t.effectiveMmarker=? ");
		list.add(str);
		list.add(AppConstants.EFFECTIVE_Y);
	
		List<Map> pageQueryForList = dao.pageQueryForList(sql.toString(), list);
		if(pageQueryForList !=null && pageQueryForList.size()>0){
			return  (List<Map>) pageQueryForList.get(0);
		}
		return null;
	}

	/**
	 * 获取当前request的ip 如果是本地访问返回localhost
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request){
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getRemoteAddr();
	    }
	    return ip.equals("0:0:0:0:0:0:0:1")?"localhost":ip;
	}

}
