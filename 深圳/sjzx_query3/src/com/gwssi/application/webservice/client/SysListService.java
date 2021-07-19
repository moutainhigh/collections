package com.gwssi.application.webservice.client;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.application.model.SmSysIntegrationBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.util.StringUtil;

@Service(value = "sysListService")
public class SysListService extends BaseService{
	

	/**
	 * 根据代码查询获取已有的信息
	 * @param codeTableEnName code
	 * @return 代码信息集合
	 * @throws OptimusException 
	 */
	public List getDisplayValue(String codeTableEnName, String code) throws OptimusException{
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//编写查询sql
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ").append(codeTableEnName).append(" where CODE=?");
		listParam.add(code);
		
		//封装结果集
		List list = dao.queryForList(sql.toString(), listParam);
        return list;
        
	}

	public List<SmSysIntegrationBO> doquerySysList(Set<String> userPksysList) throws OptimusException {
		if(userPksysList.size()<1){
			return null;
		}
		List<SmSysIntegrationBO> t=  new ArrayList<SmSysIntegrationBO>();

		IPersistenceDAO dao = getPersistenceDAO();
		for(String s:userPksysList){

			SmSysIntegrationBO bo=	 dao.queryByKey(SmSysIntegrationBO.class, s);
			if(bo!=null){
				t.add(bo);
			}
		}
		
	
		return  t;
		
	}
	
	public  SmSysIntegrationBO doqueySysbyKey(String s) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO();
		SmSysIntegrationBO bo=	 dao.queryByKey(SmSysIntegrationBO.class, s);
		return bo;
	}
	
}