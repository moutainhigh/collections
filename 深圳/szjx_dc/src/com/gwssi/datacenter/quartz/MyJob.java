package com.gwssi.datacenter.quartz;



import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;

import com.gwssi.application.common.AppConstants;
import com.gwssi.datacenter.dataSource.service.DataSourceManagerService;
import com.gwssi.datacenter.model.DcDataSourceBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.DAOManager;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

public class MyJob extends BaseService{
	@Autowired
	private DataStructLoadBySqlServiceQuartz dataStructLoadbySqlServiceQuartz;
	
	@Autowired
	private DAOManager daomanager;
	
	@SuppressWarnings("unused")
	public void work() throws OptimusException, IOException{
		System.out.println("work开始工作");
		Properties prop = new Properties();
		String flag = null;
		InputStream in = MyJob.class.getClassLoader().getResourceAsStream("/common.properties");
		try {
			prop.load(in);
			flag = prop.getProperty("applicationContext_quartz.xml");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			in.close();
		}
		//System.out.println(flag);
	    if(flag.equals("true")){	
		
		DataSourceManagerService dataSourceManagerService = new DataSourceManagerService();
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "select * from dc_data_source t where t.effective_marker = ?";
		List<String> paramList = new ArrayList<String>();
		paramList.add(AppConstants.EFFECTIVE_Y);
		List<DcDataSourceBO> list = dao.queryForList(DcDataSourceBO.class, sql, paramList);
		for(int i=0; i<list.size(); i++){
			String pkDcDataSource = list.get(i).getPkDcDataSource();
			System.out.println(pkDcDataSource);
			DcDataSourceBO smser =null;
			smser = dataSourceManagerService.findDcDataSourceBOByPK(pkDcDataSource); //获取数据源表信息
			Map<String, Boolean> t1= new HashMap<String,Boolean>();
			t1 = dataStructLoadbySqlServiceQuartz.addAll(smser);
		}
		
//     	String pkDcDataSource = "acf6930e5eb0442f80e222113b1c5cfd";
//     	DcDataSourceBO smser =null;
//		smser= dataSourceManagerService.findDcDataSourceBOByPK(pkDcDataSource); //获取数据源表信息
//		Map<String, Boolean> t1= new HashMap<String,Boolean>();
//		t1 = dataStructLoadbySqlServiceQuartz.addAll(smser);
			
	}
	}
	
	
}
