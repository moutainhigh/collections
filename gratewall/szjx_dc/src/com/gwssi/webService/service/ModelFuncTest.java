package com.gwssi.webService.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.alibaba.fastjson.JSON;
import com.gwssi.application.common.AppConstants;
import com.gwssi.application.webservice.service.AuthorityService;
import com.gwssi.application.webservice.service.XmlToMapUtil;
import com.gwssi.comselect.model.EntSelectQueryBo;
import com.gwssi.comselect.service.ComSelectService;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.util.BeanUtilTest;

/**
 * 获取企业类型类
 * @author chaihw
 */
@Service(value = "modelFuncTest")
@WebService(targetNamespace = "http://www.gwssi.com.cn", serviceName = "services", portName = "modelreturn")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class ModelFuncTest extends SpringBeanAutowiringSupport {

	private static Logger logger = Logger.getLogger(ModelFuncTest.class);

	@Autowired
	private ComSelectService comSelectService;
	/**
	 * 测试连接
	 * 
	 * @param name
	 * @return String
	 */
	@WebMethod(exclude = false, operationName = "con")
	@WebResult(name = "ret", partName = "ret")
	public String con() {
		return "webService 被调用 success";
	}
	
	/**
	 * 通过bo参数 
	 * @param name
	 * @return
	 */
	@WebMethod(exclude = false, operationName = "getEntList")
	@WebResult(name = "ret")
	public String getEntList(@WebParam(name = "xmlParam") String name) {
		System.out.println("接受到数据为："+name);
		Map<String,Object> param = XmlToMapUtil.dom2Map(name);
		for(String s:param.keySet()){
			System.out.println(s+":"+param.get(s));
		}
		EntSelectQueryBo bo = JSON.parseObject(param.get("bo").toString(), EntSelectQueryBo.class);
		System.out.println("to_test-------"+bo);
	/*	EntSelectQueryBo bo  = new EntSelectQueryBo();
		 BeanUtilTest.transMap2Bean(param, bo);*/
	
		
		 List<Map> list= null;
		 try {
			 list=	comSelectService.queryNoPageQuery(bo);
		} catch (OptimusException e) {

			e.printStackTrace();
		}
		 System.out.println(bo);
			Map map = XmlToMapUtil.xmlType(list);
			String b = XmlToMapUtil.map2Dom(map);
			XmlToMapUtil.xMLWriter(b);
		return XmlToMapUtil.xMLWriter(b);
	}
	
	/**
	 * 注册号 获取企业
	 */
	@WebMethod(exclude = false, operationName = "getRegNo")
	@WebResult(name = "ret")
	public String getRegNo(@WebParam(name = "xmlParam") String name) {
		System.out.println("接收到数据为："+name);
		Map<String,Object> param = XmlToMapUtil.dom2Map(name);
		String entName=null;
		for(String s:param.keySet()){
			System.out.println(s+":"+param.get(s));
			entName=(String) param.get(s);
		}
		 List<Map> list= null;
		 try {
			 list=	comSelectService.queryEntByRegNo(entName);
		} catch (OptimusException e) {

			e.printStackTrace();
		}
	
			Map map = XmlToMapUtil.xmlType(list);
			String b = XmlToMapUtil.map2Dom(map);
			XmlToMapUtil.xMLWriter(b);
		return XmlToMapUtil.xMLWriter(b);
	}	

	/**
	 * XML企业名称 like%
	 */
	@WebMethod(exclude = false, operationName = "getREG")
	@WebResult(name = "ret")
	public String getEntListbyEntNameLike(@WebParam(name = "xmlParam") String name) {
		System.out.println("接受到数据为："+name);
		Map<String,Object> param = XmlToMapUtil.dom2Map(name);
		String entName=null;
		for(String s:param.keySet()){
			System.out.println(s+":"+param.get(s));
			entName=(String) param.get(s);
		}
		String[] ent=entName.split(",");
		EntSelectQueryBo bo  = new EntSelectQueryBo();
		if(ent!=null){
			String[] ent_Item= new String[ent.length];
			for(int i=0 ;i<ent.length;i++){
				ent_Item[i]="include";
			}
			bo.setEntname_term(ent_Item);
			bo.setEntname(ent);
		}
		 List<Map> list= null;
		 try {
			 list=	comSelectService.queryNoPageQuery(bo);
		} catch (OptimusException e) {

			e.printStackTrace();
		}
		 System.out.println(bo);
			Map map = XmlToMapUtil.xmlType(list);
			String b = XmlToMapUtil.map2Dom(map);
			XmlToMapUtil.xMLWriter(b);
		return XmlToMapUtil.xMLWriter(b);
	}	
}
