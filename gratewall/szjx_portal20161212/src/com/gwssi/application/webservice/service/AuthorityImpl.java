package com.gwssi.application.webservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.springframework.stereotype.Service;

import com.gwssi.application.common.AppConstants;
import com.gwssi.optimus.core.exception.OptimusException;

/**
 * 测试服务类
 * 
 * @author lizheng WebService注解中需要指定targetNamespace、serviceName、portName
 *         访问地址为/serviceName/portName?wsdl
 */
@Service(value = "authorityImpl")
@WebService(targetNamespace = "http://www.gwssi.com.cn", serviceName = "authority", portName = "webServices")
public class AuthorityImpl {

	public String hello(@WebParam(name = "name") String name) {
		Map param = XmlToMapUtil.dom2Map(name);
		System.out.println(param);
		return "Hello"+name+"!";
	}

	/**
	 * 自定义方法名、参数名、 返回值名
	 * 
	 * @param name
	 * @return ret
	 * 
	 */
	@WebMethod(exclude = false, operationName = "getFunc")
	@WebResult(name = "ret")
	public String getFuncByAppId(@WebParam(name = "xmlParam") String name) {
		
		Map param = XmlToMapUtil.dom2Map(name);
		List<Map> lit = new ArrayList();
		List<Map>  par= new ArrayList();
		AuthorityService authorityService = new AuthorityService();
		Map map = new HashMap();
		
		//获取appCode和postId
		String appCode = (String) param.get("APP_CODE");
		String post = (String) param.get("POST");
		String [] postId = post.split(",");
		for(int i=0;i<postId.length;i++){
		
			//System.out.println(appCode+"---------------"+postId[i]);
			List<Map> list = new ArrayList();
			try {
				list = authorityService.getRoleType(postId[i],appCode);
			} catch (OptimusException e) {
			
				e.printStackTrace();
			}
			// System.out.println(list.get(0).get("roleType")+"===============");
			
			//获取角色类型
			String type = (String) list.get(0).get("roleType");
			
			//获取系统集成表主键
			String pkSysIntegration = (String) list.get(0).get("pkSysIntegration");
			//System.out.println(pkSysIntegration+type);
		
			if(type.equals(AppConstants.ROLE_TYPE_SUPER)){
				//是超级管理员时
			
				try {
					lit = authorityService.getFunction(appCode);
				} catch (OptimusException e) {
					e.printStackTrace();
				}
			}
			
			if(type.equals(AppConstants.ROLE_TYPE_SYS)){
				//是本系统管理员
			
				try {
					lit = authorityService.getFunction(appCode);
				} catch (OptimusException e) {
					e.printStackTrace();
				}
				//System.out.println(lit);
			}
			
			if(type.equals(AppConstants.ROLE_TYPE_DEFAULT)){
				//是普通用户
				try {
					lit = authorityService.getDefaultFunction(appCode,pkSysIntegration);
				} catch (OptimusException e) {
					e.printStackTrace();
				}
			}

			par.addAll(lit);
		
		}
		
		map = XmlToMapUtil.xmlType(par);
		String b = XmlToMapUtil.map2Dom(map);
		XmlToMapUtil.xMLWriter(b);
		
		
		
		
		
			
		//System.out.println("sayHello called...");
	/*	1.获取并解析参数
		2.根据参数去数据库查功能权限
		a.如果登录人是超级管理员，本系统的管理员
		b.如果是普通用户*/
		return  XmlToMapUtil.xMLWriter(b);
	}

	/**
	 * 此方法本系统测试 不对外发布
	 * 
	 * @param name
	 * @return String
	 */
	// @WebMethod(exclude = true, operationName = "sayHello")
	// public String sayHello2(@WebParam(name = "name") String name) {
	// System.out.println("sayHello called...");
	// return "hello " + name;
	// }

}
