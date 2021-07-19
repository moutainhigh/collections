package com.gwssi.otherselect.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.comselect.model.EntSelectQueryBo;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.otherselect.model.BlackEntBO;

@Service(value = "blackEntService")
public class BlackEntService extends BaseService{
	
	public List<Map> queryList(Map<String, String> str) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from V_BLACKENT t where t.pripid = ? ");
		params.add(str.get("priPid"));
		return typechage(dao.pageQueryForList(sql.toString(), params));
	}
	
	public List<Map> queryYR(Map<String, String> str) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from V_YRENT t where t.pripid = ? ");
		params.add(str.get("priPid"));
		return typechage(dao.pageQueryForList(sql.toString(), params));
	}
	
	public List<Map> queryRenyuanxx(Map<String, String> str) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from DC_BL_ENTER_BLACK_PERSON t where t.pripid = ? ");
		params.add(str.get("priPid"));
		List<Map> res = typechage(dao.pageQueryForList(sql.toString(), params));
		return res;
	}
	
	public List<Map> queryYRRenyuanxx(Map<String, String> str) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from DC_BL_ENTER_ONEPERSON_PERSON t where t.pripid = ? ");
		params.add(str.get("priPid"));
		List<Map> res = typechage(dao.pageQueryForList(sql.toString(), params));
		return res;
	}
	
	public List<Map> queryTouzirenxx(Map<String, String> str) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from V_BLACKENT_TOUZIREN t where t.pripid = ? ");
		params.add(str.get("priPid"));
		List<Map> res = typechage(dao.pageQueryForList(sql.toString(), params));
		return res;
	}
	
	public List<Map> queryYRTouzirenxx(Map<String, String> str) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from V_YR_TOUZIREN t where t.pripid = ? ");
		params.add(str.get("priPid"));
		List<Map> res = typechage(dao.pageQueryForList(sql.toString(), params));
		return res;
	}
	
	public List<Map> queryDiaoxiao(Map<String, String> str) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from V_BLACKENT_DIAOXIAO t where t.pripid = ? ");
		params.add(str.get("priPid"));
		return typechage(dao.pageQueryForList(sql.toString(), params));
	}
	
	/*
	 * 黑牌企业主要人员信息
	 */
	public Object queryrenyuanxx(String id) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer();
		sql.append("select * from V_BLACKENT_RENYUAN t where t.id = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(id);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	}
	
	/*
	 * 黑牌企业投资人信息
	 */
	public Object querytouzirenxx(String id) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer();
		sql.append("select * from V_BLACKENT_TOUZIREN t where t.id = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(id);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	} 
	/*
	 * 一人企业主要人员信息
	 */
	public Object queryyrrenyuanxx(String id) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer();
		sql.append("select * from V_YR_RENYUAN t where t.id = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(id);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	}
	
	/*
	 * 一人企业投资人信息
	 */
	public Object queryyrtouzirenxx(String id) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer();
		sql.append("select * from V_YR_TOUZIREN t where t.id = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(id);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

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
