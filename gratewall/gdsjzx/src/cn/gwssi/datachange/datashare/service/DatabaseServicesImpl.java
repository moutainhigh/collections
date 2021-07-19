package cn.gwssi.datachange.datashare.service;

import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebService;

import cn.gwssi.datachange.datashare.inter.DataBaseServiceInterface;

/**		对不同服务，则需要进行不同处理；处理方式如下：
			a、其他应用程序调用服务时，clint jar 通过其他应用传入serviceid调用服务内容查询服务，查询真实服务地址
 * cn.gwssi.datachange.datashare.service
 * GDGSWebServices.java
 * 下午2:26:39
 * @author wuminghua
 */
@WebService
public class DatabaseServicesImpl implements DataBaseServiceInterface{

	
	
	
	/**
	 * @return 1、数据库实例名  2、验证用户密码及ip地址 3、返回数据库连接用户密码 4、返回数据库服务可查询表及字段信息
	 * 
	 */
	@WebMethod
	public Map callDatabaseWebserice(String service,String ip ,String username,String password){
		
		
		
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
}
