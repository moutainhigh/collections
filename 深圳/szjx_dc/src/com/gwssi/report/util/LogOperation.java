package com.gwssi.report.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.plugin.auth.model.User;

public class LogOperation extends BaseService{
	private Map<String,String> map=new HashMap<String,String>(){
		{
			put("440303", "深圳市罗湖区");
			put("440304", "深圳市福田区");
			put("440305", "深圳市南山区");
			put("440306", "深圳市宝安区");
			put("440307", "深圳市龙岗区");
			put("440308", "深圳市盐田区");
			put("440309", "深圳市光明新区");
			put("440310", "深圳市坪山新区");
			put("440342", "深圳市龙华新区");
			put("440343", "深圳市大鹏新区");
			put("001", "深圳市");
			put("440399","深圳市其他地区");
		}
	};
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void logInfo(String id,String operationType,HttpServletRequest req){
		IPersistenceDAO dao = this.getPersistenceDAO("dbCsdb");
		String userName=req.getSession().getAttribute("dlm").toString();
		String userNameCn=((User)req.getSession().getAttribute("user")).getUserName();
		String ip=((User)req.getSession().getAttribute("user")).getIp();
		String reportName = null;
		String tableYear = null;
		String tableDate = null;
		String tableReg = null;
		List list=new ArrayList();
		List<Map> result=new ArrayList<Map>();
		String sql="select regcode,reportname,mouth,year from t_cognos_report where id=?";
		list.add(id);
		try {
			result = dao.queryForList(sql, list);
			for (Map map1 : result) {
				reportName=(String) map1.get("reportname");
				tableYear=(String) map1.get("year");
				tableDate=(String) map1.get("mouth");
				tableReg=map.get(map1.get("regcode"));
			}
		} catch (OptimusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.addLog(userName, userNameCn, ip, operationType, reportName, tableYear, tableDate, tableReg);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void addLog(String userName,String userNameCn,String ip,String operationType,
			String reportName,String tableYear,String tableDate,String reportReg){
		IPersistenceDAO dao = this.getPersistenceDAO("dbCsdb");
		List list=new ArrayList();
		list.add(userName);
		list.add(userNameCn);
		list.add(ip);
		list.add(operationType);
		list.add(reportName);
		list.add(tableYear);
		list.add(tableDate);
		list.add(reportReg);
		String sql="insert into t_log_operation (id,sys_flag,user_id,user_name,user_ip,operation_type,"+
					"operation_table,table_year,table_date,table_reg,operation_time) values("+
					"sys_guid(),'WDY',?,?,?,?,?,?,?,?,sysdate)";
		try {
			dao.execute(sql, list);
		} catch (OptimusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void logInfoYeWu(String reportName,String sysFlag,String operationType,String sql, String sqlP,  HttpServletRequest req){
		IPersistenceDAO dao = this.getPersistenceDAO("dbCsdb");
		String userName=req.getSession().getAttribute("dlm").toString();
		String userNameCn=((User)req.getSession().getAttribute("user")).getUserName();
		String ip=((User)req.getSession().getAttribute("user")).getIp();
		//String reportName = null;
		this.addYeWuLog(userName, userNameCn, ip, operationType, reportName, sql, sqlP);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void addYeWuLog(String userName,String userNameCn,String ip,String operationType,
			String reportName,String sql,String sqlP){
		IPersistenceDAO dao = this.getPersistenceDAO("dbCsdb");
		List list=new ArrayList();
		list.add(userName);//
		list.add(userNameCn);//
		list.add(ip);//
		list.add(operationType);//
		list.add(reportName);//
		list.add(sql);//
		list.add(sqlP);//
		String sql1="insert into t_log_operation (id,sys_flag,user_id,user_name,user_ip,operation_type,"+
					"operation_table,operation_sql,operation_sql_paratemer,operation_time) values("+
					"sys_guid(),'WDY',?,?,?,?,?,?,?,sysdate)";
		try {
			dao.execute(sql1, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
	}
}
