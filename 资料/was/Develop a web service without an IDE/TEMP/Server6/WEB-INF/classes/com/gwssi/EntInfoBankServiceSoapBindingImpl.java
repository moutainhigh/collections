package com.gwssi;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import com.alibaba.fastjson.JSON;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.fastjson.JSONArray;

public class EntInfoBankServiceSoapBindingImpl implements com.gwssi.BankService{
   
	private static DataSource dataSource;

    public static DataSource getDataSource(){
	    if (dataSource == null) {
	      Resource resource = new ClassPathResource("bean.xml");
	      BeanFactory factory = new XmlBeanFactory(resource);
	      dataSource = (DataSource)factory.getBean("dataSource");
	    }
	    return dataSource;
	  }
	
	public java.lang.String getEntInfo(java.lang.String name) throws java.rmi.RemoteException {
		
		List<Map<String, Object>> queryForList;
		
		
		if(name != null && !"".equals(name)){
			StringBuffer sql = new StringBuffer("select distinct t.regno as  注册号,"
					+ "t.unifsocicrediden as 统一社会信用代码,"
					+ "t.entname as 企业名称,"
					+ "t.dom as 住所,"
					+ "t.lerep as 法定代表人,"
					+ "t.regcap as 注册资本,"
					+ "t.enttype as 企业类型,"
					+ "t.opscope as 经营范围 ,"
					+ "to_char(t.opfrom,'yyyy/MM/dd') as 经营期限自,"
					+ "to_char(t.opto,'yyyy/MM/dd') as 经营期限至," 
					+ "to_char(t.estdate,'yyyy/MM/dd') as 成立日期,"
					+ "to_char(t.apprdate,'yyyy/MM/dd') as 核准日期    from dc_ra_mer_base t ");
			
			sql.append(" where t.entname = '").append(name).append("' or t.regno = '").append(name).append("' or t.unifsocicrediden = '").append(name).append("'");
			
			try {
				queryForList = getSingleEntInfo(sql.toString(),null);
				
				if(queryForList!=null && queryForList.size()>0){
					String json = JSONArray.toJSON(queryForList).toString();
					return json;
				}else{
					return "无查询结果";
				}
			} catch (Exception e) {
				e.printStackTrace();
				return e.getMessage();//返回错误信息
			}
		}else{
			String sql = "select distinct t.regno as  注册号,"
					+ "t.unifsocicrediden as 统一社会信用代码,"
					+ "t.entname as 企业名称,"
					+ "t.dom as 住所,"
					+ "t.lerep as 法定代表人,"
					+ "t.regcap as 注册资本,"
					+ "t.enttype as 企业类型,"
					+ "t.opscope as 经营范围 ,"
					+ "to_char(t.opfrom,'yyyy/MM/dd') as 经营期限自,"
					+ "to_char(t.opto,'yyyy/MM/dd') as 经营期限至," 
					+ "to_char(t.estdate,'yyyy/MM/dd') as 成立日期,"
					+ "to_char(t.apprdate,'yyyy/MM/dd') as 核准日期    from dc_ra_mer_base t "
					+ " where t.apprdate > (to_date(to_char(sysdate,'yyyy/MM/dd'),'yyyy/MM/dd')-2) and t.apprdate <= (to_date(to_char(sysdate,'yyyy/MM/dd'),'yyyy/MM/dd')) ";
			try {
				queryForList = getListEntInfo(sql,null);
				
				if(queryForList!=null && queryForList.size()>0){
					String json = JSONArray.toJSON(queryForList).toString();
					return json;
				}else{
					return "无查询结果";
				}
			} catch (Exception e) {
				e.printStackTrace();
				return e.getMessage();//返回错误信息
			}
		}
	}
	
	public List<Map<String, Object>> getSingleEntInfo(String sql,
			HttpServletRequest request,String param) {

		JdbcTemplate template = new JdbcTemplate(getDataSource());
		
		List<Map<String, Object>> queryForList = template.queryForList(sql);
		
		log(sql,param,template);
		 
		return queryForList;
	}

	public List<Map<String, Object>> getListEntInfo(String sql, HttpServletRequest request) {
		
		JdbcTemplate template = new JdbcTemplate(getDataSource());
		
		List<Map<String, Object>> queryForList = template.queryForList(sql);
		
		log(sql,"",template);
		
		return queryForList;
	}
	
	public String getIp() {
		MessageContext mc =MessageContext.getCurrentContext();
		HttpServletRequest request =  
		(HttpServletRequest) mc.getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
		String ip = request.getHeader("x-forwarded-for");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public void log(String sql,String param,JdbcTemplate template){
		
		String ip = getIp();
		
		StringBuffer insertSql = new StringBuffer("insert into LOG_OPERATION select sys_guid(),'BANKSER','','"
				).append(ip).append("','银行接口调用,'").append(sql).append("','").append(param).append("',sysdate from dual");
		
		template.execute(insertSql.toString());
	}
}


