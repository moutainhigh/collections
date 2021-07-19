package com.gwssi.sms.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;


@Service
public class SMSService extends BaseService {
	
	private static final String DATASOURS = "dc_dc";
	
	private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean savePhoneSMS(String phone,int code){
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS);
		String sql ="insert into  dc_house_phone_code(phone,isback,code,ISUSED) values(?,0,?,1)";
		List list1 = new ArrayList();
		list1.add(phone);
		list1.add(code);
		int i=0;
		try {
			i= dao.execute(sql, list1);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return i>0?true:false;
	}
	
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map isExitCode(String phone){
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS);
		String isHasSql = "select phone,code,to_char(dates,'YYYY-mm-dd HH24:mi:ss') as time1,to_char(starttime,'YYYY-mm-dd HH24:mi:ss') as time2 from v_dc_house_phone where phone =? and isused = '1'";
		List queryListParam = new ArrayList();
		queryListParam.add(phone);
		List<Map> resuts;
		Map resultMap =null;
		try {
			resuts = dao.queryForList(isHasSql, queryListParam);
			if(resuts!=null&&resuts.size()>0){
				resultMap = resuts.get(0);
			}
		} catch (OptimusException e1) {
			e1.printStackTrace();
			//return null;
		}
		return resultMap;
	}
	
	
	@SuppressWarnings({ "rawtypes",  "unchecked" })
	public Map  getValidateCode(String phone){

		IPersistenceDAO dao = getPersistenceDAO(DATASOURS);
		String sql = "select phone,code,to_char(dates,'YYYY-mm-dd HH24:mi:ss') as time1,to_char(starttime,'YYYY-mm-dd HH24:mi:ss') as time2 from v_dc_house_phone where phone =?";
		List list = new ArrayList();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Map map = new HashMap();
		list.add(phone);
		try {
			List<Map> i = dao.queryForList(sql, list);
			if(i!=null&&i.size()!=0){
				map = i.get(0);
				Date  sysdate = sf.parse((String)i.get(0).get("time1"));
				Date starttime = sf.parse((String)i.get(0).get("time2"));
				long logs = sysdate.getTime() - starttime.getTime();
				long secondes = logs/1000/60;
				map.put("isOften", secondes);
				return map;
			}else{
				return null;
			}
		} catch (OptimusException | ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	//是否频繁发送短信验证码
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String isOftenSendSMS(String phone){
		String sql = "select (case\n" +
		"         when ((sysdate - to_date(to_char(t.starttime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss')) * 24 * 60 * 60) < 60 then\n" + 
		"          '1'\n" + 
		"         else\n" + 
		"          '0'\n" + 
		"       end) isout\n" + 
		"  from v_dc_house_phone t\n" + 
		" where t.PHONE = ? \n" + 
		"   and t.isused = '1' \n"
		+ " order  by isout desc";
		
		
		List list = new ArrayList();
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS);
		list.add(phone);
		try {
			List<Map> i = dao.queryForList(sql, list);
			if(i!=null&&i.size()>0){
				return (String) i.get(0).get("isout");
			}else{
				return null;
			}
		} catch (OptimusException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean validateCode(String phoneNum,String code){
		List list = new ArrayList();
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS);
		list.add(phoneNum.trim());
		list.add(code.trim());
		String sql="select count(1) as count from v_dc_house_phone t where t.phone=? and t.code=?  and isused='1'  ";
		int i=0;
		boolean b;
		try {
			 i = dao.queryForInt(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
			i=0;
		}
		b= i>0?true:false;
		return b;
	}
}
