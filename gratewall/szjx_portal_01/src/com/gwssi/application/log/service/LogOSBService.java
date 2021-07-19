package com.gwssi.application.log.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.application.common.AppConstants;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.util.StringUtil;

@Service(value = "logOsbService")
@EnableAsync
public class LogOSBService extends BaseService{
	

	/**
	 * 多数据源
	 * 获取日志库的数据源的key 该数据源在初始化的时候会自动加载
	 * @return 
	 */
	private static String getlog_datasourcekey(){
		Properties properties = ConfigManager.getProperties("optimus");
		
		String key= properties.getProperty("logDataSource");


		return key;
	}

	public List queryOsbLog(Map params) throws OptimusException {
	
		IPersistenceDAO dao = getPersistenceDAO(getlog_datasourcekey());
		List listParam = new ArrayList();
		
		//获取请求系统和目标系统的主键
		String serviceCode = StringUtil.getMapStr(params, "serviceCode").trim();
		String requestUrl = StringUtil.getMapStr(params, "requestUrl").trim();
		String requestMeth= StringUtil.getMapStr(params, "requestMeth").trim();
		//编写sql
		String sql = "select * from OSBLOG  where 1=1";
		

		if(StringUtils.isNotEmpty(serviceCode)){
			sql+=" and upper(service_code) like ?";
			listParam.add("%"+serviceCode.toUpperCase()+"%");
		}	
		

		if(StringUtils.isNotEmpty(requestUrl)){
			sql+=" and upper(request_url) like ?";
			listParam.add("%"+requestUrl.toUpperCase()+"%");
		}
		
		if(StringUtils.isNotEmpty(requestMeth)){
			sql+=" and upper(request_meth) like ?";
			listParam.add("%"+requestMeth.toUpperCase()+"%");
		}
		
		//封装结果集
		return dao.pageQueryForList(sql.toString(), listParam);
		
	
	}

	public List querygsbServiceCode() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		
		//编写sql语句
        String sql = "select t.service_no as value,t.service_name as name from sm_services t ";
       
        //封装结果集
        List systemList = dao.queryForList(sql, null);
        
        return systemList;
	}

	public Map queryOSBLogByPK(String pkOsbLog) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getlog_datasourcekey());	
		List listParam = new ArrayList();
		String sql = "select * from OSBLOG  where 1=1 and  pk_osb_log  =?";
		
		listParam.add(pkOsbLog);
		List<Map> list =dao.pageQueryForList(sql.toString(), listParam);
		if(list!=null &&list.get(0)!=null){
			return list.get(0);
		}else{
			return null;
		}
		


	
		

	}
	
	


	

}
