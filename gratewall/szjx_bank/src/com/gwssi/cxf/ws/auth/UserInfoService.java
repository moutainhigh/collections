package com.gwssi.cxf.ws.auth;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

public class UserInfoService {
	
	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	public List getUserInfo(String systemID, String userId, String password) {
		
		/*List<Map<String, Object>> queryForList;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select * from T_ENT_BANK_USERINFO t where t.id = ? and t.username = ? and t.password = ? ");
			List<String> paramList = new ArrayList<String>();
			paramList.add(systemID);
			paramList.add(userId);
			paramList.add(password);
			List dataList = SpringJdbcUtil.query("dc_dc", sql.toString(),
					paramList.toArray());
			if (dataList != null && dataList.size() > 0) {
				return true;
			} else {
				return false;
			}

		} catch (Throwable e) {
			return false;
		}*/
		
		
		if(PublicStringUtils.isEmpty(systemID)||PublicStringUtils.isEmpty(userId)||PublicStringUtils.isEmpty(password)){
			throw new RuntimeException("用户名或密码为空");
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from T_ENT_BANK_USERINFO t where t.id = ? and t.username = ? and t.password = ? ");
		
		
		List result = jdbcTemplate.queryForList(sql.toString(), new Object[]{systemID,userId,password});
		System.out.println(result);
		if(result!=null&&result.size()>0){
			return result;
		}else{
			return null;
		}
	}

}
