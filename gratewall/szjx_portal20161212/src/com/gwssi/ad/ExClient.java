package com.gwssi.ad;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;

import com.gwssi.optimus.core.common.ConfigManager;

@org.springframework.stereotype.Service
@EnableAsync
public class ExClient {
	private static Logger logger = Logger.getLogger(ExClient.class);

	public static void main(String args[]) throws Exception {

		String identitystr = "chwtest.com/导入测试/E组/E1组/王志31";
		String aliasStr = "wangz31";
		ExClient ex = new ExClient();
		//ex.EnableEx(identitystr, aliasStr);
	}

	/*
	 * @Async public void EnableEx(String identitystr,String aliasStr ){
	 * Properties properties = ConfigManager.getProperties("lyncEx"); String
	 * load = properties.getProperty("ex.load"); // 用户名
	 * 
	 * if(!"true".equalsIgnoreCase(load)){ return; }
	 * 
	 * logger.info("开始启用Exchange邮箱： \n 传入参数为 ："+identitystr);
	 * logger.info("传入参数为："+aliasStr); //传入参数 String targetEendPoint =
	 * properties.getProperty("ex.wsdlurl"); String nameSpace =
	 * properties.getProperty("ex.namespace"); String identity = "identity";
	 * String alias ="alias"; String database="database"; String
	 * databaseStr=properties.getProperty("ex.databaseStr");
	 * 
	 * 
	 * String methodEnable="EnableExMail";
	 * 
	 * 
	 * // 定义服务 Service service = new Service();
	 * 
	 * 
	 * 
	 * try{ Call call2 = (Call) service.createCall();
	 * call2.setTargetEndpointAddress(new java.net.URL( targetEendPoint));
	 * call2.setUseSOAPAction(true); call2.setReturnType(new
	 * QName("http://www.w3.org/2001/XMLSchema", "string"));
	 * call2.setOperationName(new QName(nameSpace, methodEnable));
	 * call2.setSOAPActionURI(nameSpace+""+methodEnable); call2.addParameter(new
	 * QName(nameSpace,identity),XMLType.XSD_STRING,ParameterMode.IN);
	 * call2.addParameter(new
	 * QName(nameSpace,alias),XMLType.XSD_STRING,ParameterMode.IN);
	 * call2.addParameter(new
	 * QName(nameSpace,database),XMLType.XSD_STRING,ParameterMode.IN); String
	 * retVal2 = (String) call2 .invoke(new Object[] { identitystr,
	 * aliasStr,databaseStr}); System.out.println(retVal2);
	 * 
	 * logger.info("返回参数："+retVal2); }catch(Exception ex){
	 * logger.error("用户启用邮箱失败，用户信息："+identitystr); ex.printStackTrace(); } }
	 */
	@Async
	public synchronized void enbleEX(Map pMap, Map resMap) {
		HashMap<String, String> dataMap = new HashMap<String, String>();
		String name = (String) pMap.get("NAME");
		String ou = (String) pMap.get("OU");
		name = name.trim();
		ou = ou.trim();
		String ouStr[] = ou.split(",");
		String USER_OU = "/";
		for (int i = (ouStr.length - 1); i >= 0; i--) {
			USER_OU = USER_OU + ouStr[i] + "/";
		}

		USER_OU = USER_OU + name;
		dataMap.put("SRV_CODE", "EX0001");
		dataMap.put("USER_OU", USER_OU);
		dataMap.put("USER_NAME", resMap.get("USER_NAME").toString());

		Properties properties = ConfigManager.getProperties("lyncEx");
		String load = properties.getProperty("ex.load"); // 用户名

		if (!"true".equalsIgnoreCase(load)) {
			return;
		}
		String identitystr = properties.getProperty("ex.net") + USER_OU;
		String aliasStr = resMap.get("USER_NAME").toString();
		logger.info("开始启用Exchange邮箱： \n 传入参数为 ：" + identitystr);
		logger.info("传入参数为：" + aliasStr);
		// 传入参数
		String targetEendPoint = properties.getProperty("ex.wsdlurl");
		String nameSpace = properties.getProperty("ex.namespace");
		String identity = "identity";
		String alias = "alias";
		String database = "database";
		String databaseStr = properties.getProperty("ex.databaseStr");

		String methodEnable = "EnableExMail";

		// 定义服务
		Service service = new Service();

		try {
			Call call2 = (Call) service.createCall();
			call2.setTargetEndpointAddress(new java.net.URL(targetEendPoint));
			call2.setUseSOAPAction(true);
			call2.setReturnType(new QName("http://www.w3.org/2001/XMLSchema",
					"string"));
			call2.setOperationName(new QName(nameSpace, methodEnable));
			call2.setSOAPActionURI(nameSpace + "" + methodEnable);
			call2.addParameter(new QName(nameSpace, identity),
					XMLType.XSD_STRING, ParameterMode.IN);
			call2.addParameter(new QName(nameSpace, alias), XMLType.XSD_STRING,
					ParameterMode.IN);
			call2.addParameter(new QName(nameSpace, database),
					XMLType.XSD_STRING, ParameterMode.IN);
			System.out.println("exchange开始执行！！");
			String retVal2 = (String) call2.invoke(new Object[] { identitystr,
					aliasStr, databaseStr });
			System.out.println(retVal2);

			logger.info("exchange执行完成，返回参数：" + retVal2);
			this.enblelync(aliasStr);
		} catch (Exception ex) {
			logger.error("用户启用邮箱失败，用户信息：" + identitystr);
			ex.printStackTrace();
		}

	}
	

	public static  void enblelync(String identitystr) {
		
		Properties properties = ConfigManager.getProperties("lyncEx");
		String load = properties.getProperty("lync.load");
		 identitystr= identitystr+ properties.getProperty("lync.net");
		if (!"true".equalsIgnoreCase(load)) {
			return;
		}
		logger.info("开始启用lync：  传入参数为 ：" + identitystr);
		String targetEendPoint = properties.getProperty("lync.wsdlurl");
		String nameSpace = properties.getProperty("lync.namespace");
		
		String methodEnable = "EnableLync";
		
		//参数名
		String identity = "Identity";
		String hostname="hostname";
		String adminuser="adminuser";
		String adminpwd="adminpwd";
		String RegistrarPool="RegistrarPool";
		// 定义服务
		Service service = new Service();

		try {
			Call call2 = (Call) service.createCall();
			call2.setTargetEndpointAddress(new java.net.URL(targetEendPoint));
			call2.setUseSOAPAction(true);
			call2.setReturnType(new QName("http://www.w3.org/2001/XMLSchema",
					"string"));
			call2.setOperationName(new QName(nameSpace, methodEnable));
			call2.setSOAPActionURI(nameSpace + "" + methodEnable);
			call2.addParameter(new QName(nameSpace, identity),
					XMLType.XSD_STRING, ParameterMode.IN);
			call2.addParameter(new QName(nameSpace, hostname), XMLType.XSD_STRING,
					ParameterMode.IN);
			call2.addParameter(new QName(nameSpace, adminuser),
					XMLType.XSD_STRING, ParameterMode.IN);
			call2.addParameter(new QName(nameSpace, adminpwd),
					XMLType.XSD_STRING, ParameterMode.IN);
			call2.addParameter(new QName(nameSpace, RegistrarPool),
					XMLType.XSD_STRING, ParameterMode.IN);			
			String retVal2 = (String) call2.invoke(new Object[] { identitystr,
					properties.getProperty("lync.hostname"),properties.getProperty("lync.adminuser"),properties.getProperty("lync.adminpwd"), properties.getProperty("lync.pool") });
			System.out.println(retVal2);

			logger.info("lync 执行完成，返回参数为：" + retVal2);
		} catch (Exception ex) {
			logger.error("用户启用lync失败，用户信息：" + identitystr);
			ex.printStackTrace();
		}
	}
}
