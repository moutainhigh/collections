/**
 * EntInfoBankServiceSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * cf091607.02 v22116113222
 */

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

import org.apache.axis.MessageContext;
import org.apache.axis.transport.http.HTTPConstants;
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
				queryForList = getSingleEntInfo(sql.toString(),null,name);
				
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

    public java.lang.String getLogOffEntInfo(java.lang.String name) throws java.rmi.RemoteException {
       List<Map<String, Object>> queryForList;
		
		if(name != null && !"".equals(name)){
			StringBuffer sql = new StringBuffer("select distinct t.regno as  注册号,"
					+ "t.unifsocicrediden as 统一社会信用代码,"
					+ "t.entname as 商事主体名称,"
					+ " ' ' as 注销流程号,"
					+ "to_char(t.apprdate,'yyyy/MM/dd') as 注销时间,"
					+ "t.lerep as 法定代表人,"
					+ "a.CANREA as 注销原因,"
					+ "a.REMARK as 注销备注,"
					+ " ' ' as 打印时间  from DC_RA_MER_CANCEL a left join dc_ra_mer_base t on a.main_tb_id = t.id where t.entstatus = '4' and  ");
			
			sql.append(" (t.entname = '").append(name).append("' or t.regno = '").append(name).append("' or t.unifsocicrediden = '").append(name).append("')");
			
			try {
				queryForList = getSingleEntInfo(sql.toString(),null,name);
				
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
			StringBuffer sql = new StringBuffer("select distinct t.regno as  注册号,"
					+ "t.unifsocicrediden as 统一社会信用代码,"
					+ "t.entname as 商事主体名称,"
					+ "' ' as 注销流程号,"
					+ "to_char(t.apprdate,'yyyy/MM/dd') as 注销时间,"
					+ "t.lerep as 法定代表人,"
					+ "a.CANREA as 注销原因,"
					+ "a.REMARK as 注销备注,"
					+ "' ' as 打印时间  from DC_RA_MER_CANCEL a left join dc_ra_mer_base t on a.main_tb_id = t.id where t.entstatus = '4' and  t.apprdate > (to_date(to_char(sysdate,'yyyy/MM/dd'),'yyyy/MM/dd')-2) and t.apprdate <= (to_date(to_char(sysdate,'yyyy/MM/dd'),'yyyy/MM/dd'))");
			try {
				queryForList = getListEntInfo(sql.toString(),null);
				
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
	
	public List<Map<String, Object>> getSingleEntInfo(String sql,HttpServletRequest request,String param) {

		JdbcTemplate template = new JdbcTemplate(getDataSource());
		
		List<Map<String, Object>> queryForList = template.queryForList(sql);
		
		//log(sql,param,template);
		 
		return queryForList;
	}

	public List<Map<String, Object>> getListEntInfo(String sql, HttpServletRequest request) {
		
		JdbcTemplate template = new JdbcTemplate(getDataSource());
		
		List<Map<String, Object>> queryForList = template.queryForList(sql);
		
		//log(sql,"",template);
		
		return queryForList;
	}

}
