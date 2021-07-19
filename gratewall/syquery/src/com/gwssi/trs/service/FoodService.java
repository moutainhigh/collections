package com.gwssi.trs.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service
public class FoodService extends BaseService{
	
	public List<Map> queryJbxx(Map map,String type) throws OptimusException{
		IPersistenceDAO dao= getPersistenceDAO("dc_dc");;
		StringBuffer sql=null;
		if("1".equals(type) || "3".equals(type)){//经营许可证或生产许可证主体信息
			sql=new StringBuffer("select * from v_food_ent");
		}else if("11".equals(type) || "31".equals(type)){//经营许可证信息
			sql=new StringBuffer("select * from v_FOOD_LICENSE_INFO");
		}else if("4".equals(type)){//生产许可证（旧）信息
			sql=new StringBuffer("select * from DC_F1_SPSC_ENT_BASIC_INFO");
		}else if("5".equals(type)){//食品流通信息
			sql=new StringBuffer("select * from v_F1_SPLT_ENT_BASIC_INFO");
		}else if("51".equals(type)){//食品流通许可证信息
			sql=new StringBuffer("select * from v_f1_SPLT_LICENSE_INFO");
		}else if("6".equals(type)){//餐饮服务信息
			sql=new StringBuffer("select * from dc_f1_CYFW_ENT_BASIC_INFO");
		}
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and id = ? ");
				list.add(pripid);
			}
		}else{
			return null;
		}
		return typechage(dao.queryForList(sql.toString(), list));
	}
	
	
	/*
	 * 查询列表基本信息
	 */
	public List<Map> queryList(Map map,String type) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");;
		StringBuffer sql = null;
		if("12".equals(type)){//经营许可证安全管理人员信息
			sql = new StringBuffer("select * from V_FOOD_HR_AQGL_INFO");
		}else if("13".equals(type)){//经营许可证技术人员信息
			sql = new StringBuffer("select * from v_FOOD_HR_ZYJS_INFO");
		}else if("14".equals(type)){//经营许可证安全设施设备信息
			sql = new StringBuffer("select * from v_FOOD_DEVICE_INFO");
		}else if("15".equals(type)){//经营许可证产品模型项信息
			sql = new StringBuffer("select * from V_FOOD_PRODUCT_INFO");
		}else if("16".equals(type)){//经营许可证场所模型项信息
			sql = new StringBuffer("select * from V_FOOD_PLACE_INFO");
		}else if("17".equals(type)){//经营许可证仪器模型项信息
			sql = new StringBuffer("select * from V_FOOD_INSTRUMENT_INFO");
		}else if("41".equals(type)){//检验产品信息
			sql = new StringBuffer("select * from v_f1_SPSC_PRODUCTION_INSPECT");
		}else if("42".equals(type)){//检验抽样详情信息
			sql = new StringBuffer("select * from v_f1_SPSC_SAMPLE_DETAIL");
		}else if("43".equals(type)){//现场核查记录信息
			sql = new StringBuffer("select * from V_f1_SCENE_INVESTIGATE_ANNAL");
		}else if("44".equals(type)){//食品安全员信息
			sql = new StringBuffer("select * from v_f1_FOOD_SECURITY");
		}else if("52".equals(type)){//经营种类信息
			sql = new StringBuffer("select * from v_f1_SPLT_OPERATION_TYPE");
		}else if("53".equals(type)){//特殊经营种类信息
			sql = new StringBuffer("select * from v_f1_SPLT_SPECIAL_OPERATION");
		}else if("54".equals(type)){//食品类别信息
			sql = new StringBuffer("select * from V_F1_SPLT_CATEGORY_TYPE_INFO");
		}else if("55".equals(type)){//变更项目信息
			sql = new StringBuffer("select * from v_f1_SPLT_CHANGE_INFO");
		}else if("56".equals(type)){//食品安全员信息
			sql = new StringBuffer("select * from v_f1_SPLT_FOOD_SECURITY");
		}else if("57".equals(type)){//专业技术人员信息
			sql = new StringBuffer("select * from v_f1_SPLT_RESPONSIBLE");
		}else if("61".equals(type)){//食品安全员信息
			sql = new StringBuffer("select * from v_f1_cyfw_food_security");
		}else{
			return null;
		}
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and main_tb_id = ? ");
				list.add(pripid);
			}
		}else{
			return null;
		}
		return typechage(dao.queryForList(sql.toString(), list));
	}
	
	/*
	 * 查询列表详细信息
	 */
	public List<Map> queryListDetail(Map map,String type) throws OptimusException{
		IPersistenceDAO dao= getPersistenceDAO("dc_dc");;
		StringBuffer sql=null;
		if("12".equals(type)){//经营许可证安全管理人员信息
			sql=new StringBuffer("select * from V_FOOD_HR_AQGL_INFO");
		}else if("13".equals(type)){//经营许可证技术人员信息
			sql=new StringBuffer("select * from v_FOOD_HR_ZYJS_INFO");
		}else{
			return null;
		}
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String id = (String) map.get("id");
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(id)){
				sql.append(" and id = ? ");
				list.add(id);
			}
		}else{
			return null;
		}
		return typechage(dao.queryForList(sql.toString(), list));
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
