package com.gwssi.datacenter.dataSource;

import com.gwssi.application.common.AppConstants;
import com.gwssi.datacenter.dataSource.service.DataSourceManagerService;
import com.gwssi.datacenter.model.DcDataSourceBO;
import com.gwssi.optimus.core.common.Constants;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.persistence.datasource.storage.IDynamicSourceConfigureManager;
import com.gwssi.optimus.core.service.BaseService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public class DynamicSourceToDC extends BaseService implements
		IDynamicSourceConfigureManager {
   

    
	private static final Logger logger = LoggerFactory.getLogger(DynamicSourceToDC.class);

	public void addDataSourceConfigure(String paramString1,
			String paramString2, String paramString3, Properties paramProperties)
			throws Exception {
		//手动添加  只修改内存中的
/*		DcDataSourceBO dcbo=dataSourceManagerService.findDcDataSourceBOByPK(paramString1);
		if(dcbo==null){
			DcDataSourceBO addbo = new DcDataSourceBO();
			addbo.setPkDcDataSource(paramString1);
			addbo.setDbType(paramString3);
			String username = paramProperties.getProperty("username");
			addbo.setUserName(username);
			String password = paramProperties.getProperty("password");			
			addbo.setPwd(password);
			String url= paramProperties.getProperty("url");
			int  start=	url.indexOf("@");
			url=url.substring(start+1);
			String []urlIP =url.split(":");
			addbo.setDataSourceIp(urlIP[0]);
			addbo.setAccessPort(urlIP[1]);
			addbo.setDbInstance(urlIP[2]);
			addbo.setEffectiveMarker(AppConstants.EFFECTIVE_Y);
			dataSourceManagerService.insertDcDataSourceBO(addbo);
		}*/
		
		
	}

	public void updateDataSourceConfigure(String paramString1,
			String paramString2, String paramString3, Properties paramProperties)
			throws Exception {
		//手动添加  只修改内存中的
		
/*		DcDataSourceBO addbo=dataSourceManagerService.findDcDataSourceBOByPK(paramString1);
		if(addbo==null){
			addbo.setPkDcDataSource(paramString1);
			addbo.setDbType(paramString3);
			String username = paramProperties.getProperty("username");
			addbo.setUserName(username);
			String password = paramProperties.getProperty("password");			
			addbo.setPwd(password);
			String url= paramProperties.getProperty("url");
			int  start=	url.indexOf("@");
			url=url.substring(start+1);
			String []urlIP =url.split(":");
			addbo.setDataSourceIp(urlIP[0]);
			addbo.setAccessPort(urlIP[1]);
			addbo.setDbInstance(urlIP[2]);
			dataSourceManagerService.updateDcDataSourceBO(datasource);
		}*/
		
		
	}

	public List queryDataSourceConigurefALL() throws Exception {
		//查询数据源
		String str1 ="select * from DC_DATA_SOURCE  where EFFECTIVE_MARKER= ? ORDER BY ORDER_NO";
		IPersistenceDAO localIPersistenceDAO = getPersistenceDAO();
		List<String> str = new ArrayList<String>();
		str.add(AppConstants.EFFECTIVE_Y);
		List<Map> localList1 = localIPersistenceDAO.queryForList(str1, str);

		//判断有无数据源
		if ((localList1 == null) || (localList1.size() <= 0))
			return null;
		
		//处理数据源 写成框架所支持的
		ArrayList localArrayList = new ArrayList();
		for (int i = 0; i < localList1.size(); ++i) {
			Map<String,Object> localMap1 = (Map) localList1.get(i);
			String str2 = (String) localMap1.get("pkDcDataSource");
			localMap1.put(Constants.DATASOURCEKEY, str2);
			localMap1.remove("pkDcDataSource");
			
			localMap1.put(Constants.DATASOURCECLASS, "org.springframework.jdbc.datasource.DriverManagerDataSource");
			String username=(String) localMap1.get("userName");
			localMap1.put("username", username);
			localMap1.remove("userName");
			
			localMap1.put("dbtype", "oracle");
			
			String password=(String) localMap1.get("pwd");
			localMap1.put("password", password);			
			
			String  driverClass = "oracle.jdbc.OracleDriver"; 
			localMap1.put("driverClassName", driverClass);
			
			String url="jdbc:oracle:thin:@"+(String) localMap1.get("dataSourceIp")+":"+(String) localMap1.get("accessPort")+":"+(String) localMap1.get("dbInstance");
			localMap1.put("url", url);
			
			localMap1=this.doRemoveLocalMap(localMap1);
			
/*			for (String key : localMap1.keySet()) {
			System.out.println("KEY:"+key +"        value:"+localMap1.get(key));
			}
			System.out.println("----------------------------");*/
			localArrayList.add(localMap1);
		}

		return localArrayList;
	}

	public Map queryDataSourceConigureByKey(String paramString)
			throws Exception {

		String str1 ="select * from DC_DATA_SOURCE  where EFFECTIVE_MARKER= ? And PK_DC_DATA_SOURCE = ? ORDER BY ORDER_NO ";
		IPersistenceDAO localIPersistenceDAO = getPersistenceDAO();
		List<String> str = new ArrayList<String>(); // ？传值
		 str.add(AppConstants.EFFECTIVE_Y);
		 str.add(paramString);
		 List localList1 = localIPersistenceDAO.queryForList(str1, str);
		if ((localList1 == null) || (localList1.size() <= 0))
			return null;

		Map<String,Object> localMap1 = (Map) localList1.get(0);
		String str2 = (String) localMap1.get("pkDcDataSource");
		
		localMap1.put(Constants.DATASOURCEKEY, str2);
		localMap1.remove("pkDcDataSource");
		
		localMap1.put(Constants.DATASOURCECLASS, "org.springframework.jdbc.datasource.DriverManagerDataSource");
		String username=(String) localMap1.get("userName");
		localMap1.put("username", username);
		localMap1.remove("userName");
		
		
		localMap1.put("dbtype", "oracle");
		
		String password=(String) localMap1.get("pwd");
		localMap1.put("password", password);			
		

		
		String  driverClass = "oracle.jdbc.OracleDriver"; 
		localMap1.put("driverClassName", driverClass);
		
		String url="jdbc:oracle:thin:@"+(String) localMap1.get("dataSourceIp")+":"+(String) localMap1.get("accessPort")+":"+(String) localMap1.get("dbInstance");
		localMap1.put("url", url);
		
		localMap1=this.doRemoveLocalMap(localMap1);
		
		return localMap1;

	}

	public void deleteDataSourceConfigure(String paramString) throws Exception {
		//自己写代码删除 
/*		IPersistenceDAO localIPersistenceDAO = getPersistenceDAO();
		ArrayList localArrayList = new ArrayList();
		localArrayList.add(paramString);
		localIPersistenceDAO.execute(
				"delete from  T_PT_DATASOURCE_PROPERTIES where ID=?",
				localArrayList);
		localIPersistenceDAO.execute("delete  from T_PT_DATASOURCE where ID=?",
				localArrayList);*/
	}
	/*
	 * 删除数据中心 多余的项
	 */
	public Map<String,Object> doRemoveLocalMap(Map<String,Object> localMap1){
		localMap1.remove("createrTime");
		localMap1.remove("modifierTime");
		localMap1.remove("dataSourceIp");
		localMap1.remove("modifierName");		
		localMap1.remove("createrName");
		localMap1.remove("remarks");
		localMap1.remove("dataSourceStatus");
		localMap1.remove("firstLoadTime");				
		localMap1.remove("dbType");
		localMap1.remove("accessPort");	
		localMap1.remove("effectiveMarker");
		localMap1.remove("pkDcBusiObject");		
		localMap1.remove("firstLoaderName");
		localMap1.remove("dataSourceName");				
		localMap1.remove("lastModifierName");
		localMap1.remove("lastModifierId");	
		localMap1.remove("pwd");
		localMap1.remove("lastLoadTime");				
		localMap1.remove("orderNo");
		localMap1.remove("modifierId");	
		localMap1.remove("createrId");
		localMap1.remove("dbInstance");				
		localMap1.remove("dataSourceType");
		localMap1.remove("firstLoaderId");					
		localMap1.remove("busiSys");		
		return localMap1;
	}
}