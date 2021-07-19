package com.gwssi.entSelect.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gwssi.entSelect.util.StringUtil;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.util.UuidGenerator;


@Service
public class CopyOfSafeVisitService  extends BaseService {

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveIPLog(String ip) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List pramas = new ArrayList();
		
		String uid = UuidGenerator.getUUID();
		//Timestamp time = new Timestamp(new Date().getTime());
		
		pramas.add(uid);
		pramas.add(ip);
		//pramas.add(Calendar.getInstance());
		pramas.add("1");
		pramas.add("0");
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		System.out.println(time  + "=====> " + ip);
		String sql = "insert into dc_t_outer_ip_log_t (ipuid, ipadd, visittime, isdele, isbackmenu) values (?,?,sysdate,?,?)";
		System.out.println("\n插入查询记录" + sql.toString()+"\n");
		dao.execute(sql, pramas);
	}
	
	
	
	public Integer count(String ip) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		System.out.println(time  + "=====> " + ip);
		StringBuffer sql = new StringBuffer("select count(1) as count from V_OUTER_IP_LOG t ");
		if(StringUtil.isNotEmpty(ip)){
			sql.append("  where t.ipadd = ?");
			params.add(ip);
			System.out.println("\n查询记录的sql语句" + sql.toString()+"\n");
			Integer count  = dao.queryForInt(sql.toString(), params);
			if(count!=null){
				return count;
			}else{
				return 999;
			}
			//return  0;
		}else{
			return 999;
		}
	}
}
