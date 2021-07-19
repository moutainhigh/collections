package com.gwssi.ip.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.ip.util.StringUtil;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.util.UuidGenerator;


@Service
public class SafeVisitService  extends BaseService {

	
	public void saveIPLog(String ip) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List pramas = new ArrayList();
		
		String uid = UuidGenerator.getUUID();
		//Timestamp time = new Timestamp(new Date().getTime());
		
		pramas.add(uid);
		
		if(StringUtil.isNotEmpty(ip)||ip==null) {
			ip ="";//如果IP为空，则先把IP 置为 空字符串
		}
		pramas.add(ip);
		
		//pramas.add(Calendar.getInstance());
		pramas.add("0");
		pramas.add("2");
		String sql = "insert into DC_HOUSE_IP_LOG (ipuid, ipadd,vtime, isblack, action) values (?,?,sysdate,?,?)";
		dao.execute(sql, pramas);
	}
	
	
	
	public Integer count(String ip) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql = new StringBuffer("select count(1) as count from v_dc_house_ip_log t ");
		if(StringUtil.isNotEmpty(ip)){
			sql.append("where t.ipadd = ?");
			params.add(ip);
			int count  = dao.queryForInt(sql.toString(), params);
			return count;
			//return  0;
		}else{
			return 999;
		}
		
	}
}
