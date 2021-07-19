package com.gwssi.comselect.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

@Service(value = "whiteEntSelectService")
public class WhiteEntSelectService extends BaseService{
	
	public List<Map> getList(Map<String,String> map) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer();
		sql.append("select * from V_NS_ENTERPRISE_WHITE_LIST t where 1=1");
		List<String> params = new ArrayList<String>();
		if(map.get("address") != null && !"".equals(map.get("address"))){
			String address = map.get("address");
			sql.append(" and t.address like ? ");
			params.add("%" + address + "%");
		}
		if(map.get("houseCode") != null && !"".equals(map.get("houseCode"))){
			String houseCode = map.get("houseCode");
			sql.append(" and t.house_code = ? ");
			params.add(houseCode);
		}
		if(map.get("addrFlag") != null && !"".equals(map.get("addrFlag"))){
			String addrFlag = map.get("addrFlag");
			sql.append(" and t.addr_flag = ? ");
			params.add(addrFlag);
		}
		//sql.append(" order by LAST_UPDATE desc");
		List<Map> res = dao.pageQueryForList(sql.toString(), params);
		return res;
	}
	
	public Map getListDetail(String id) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer();
		sql.append("select * from V_NS_ENTERPRISE_WHITE_LIST t where id = ? order by LAST_UPDATE desc");
		List<String> params = new ArrayList<String>();
		params.add(id);
		List<Map> res = dao.pageQueryForList(sql.toString(), params);
		if(res!=null || res.size()>0){
			return res.get(0);
		}
		else{
			return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void updateListDetail(Map<String, String> form, String id) throws OptimusException{
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		String userId = "";
		String userName = "";
		if(user != null){
			userId = user.getUserId().toUpperCase();
			userName = user.getUserName();
		}
		
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer();
		sql.append("update DC_NS_ENTERPRISE_WHITE_LIST t set ADDRESS = ?,ADDR_FLAG = ?,HOUSE_CODE = ?,REMARK = ?,LAST_UPDATE = ?,UPDATE_USER=?,UPDATE_NAME=? where id = ?");
		List params = new ArrayList<String>();
		params.add(form.get("address"));
		params.add(form.get("addrFlag"));
		params.add(form.get("houseCode"));
		params.add(form.get("remark"));
		params.add(Calendar.getInstance());
		params.add(userId);
		params.add(userName);
		params.add(id);
		dao.execute(sql.toString(), params);		
	}
	
	@SuppressWarnings("unchecked")
	public void updateListInsert(Map<String, String> form) throws OptimusException{
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		String userId = "";
		String userName = "";
		if(user != null){
			userId = user.getUserId().toUpperCase();
			userName = user.getUserName();
		}
		
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer();
		sql.append("insert into DC_NS_ENTERPRISE_WHITE_LIST(ID, ADDRESS, ADDR_FLAG, HOUSE_CODE, REMARK, CREATE_DATE, LAST_UPDATE, CREATE_USER, CREATE_NAME, UPDATE_USER,UPDATE_NAME ) VALUES(dc_ns_enterprise_white_list_s.nextval,?,?,?,?,?,?,?,?,?,?)");
		List params = new ArrayList<String>();
		params.add(form.get("address"));
		params.add(form.get("addrFlag"));
		params.add(form.get("houseCode"));
		params.add(form.get("remark"));
		params.add(Calendar.getInstance());
		params.add(Calendar.getInstance());
		params.add(userId);
		params.add(userName);
		params.add(userId);
		params.add(userName);
		dao.execute(sql.toString(), params);		
	}
	
	@SuppressWarnings("unchecked")
	public void updateListDelete(String id) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer();
		sql.append("delete from DC_NS_ENTERPRISE_WHITE_LIST t where t.id = ?");
		List params = new ArrayList<String>();
		params.add(id);
		dao.execute(sql.toString(), params);		
	}
	
	public List<Map> exportExcel(String address,String houseCode,String addrFlag) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer();
		sql.append("select *  from V_NS_ENTERPRISE_WHITE_LIST t where 1=1 ");
		List<String> params = new ArrayList<String>();
		if(!"".equals(address)){
			sql.append(" and t.address = ? ");
			params.add(address);
		}
		if(!"".equals(houseCode)){
			sql.append(" and t.house_code = ? ");
			params.add(houseCode);
		}
		if(!"".equals(addrFlag)){
			sql.append(" and t.addr_flag = ? ");
			params.add(addrFlag);
		}
		List<Map> res = dao.queryForList(sql.toString(), params);
		return res;
			
	}

}
