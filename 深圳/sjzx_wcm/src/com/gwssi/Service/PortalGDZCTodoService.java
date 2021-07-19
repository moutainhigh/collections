package com.gwssi.Service;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.gwssi.util.PropertiesUtil;

/**
 * <h3>查询待办</h3>
 * <ol>
 * 	<li>财务待办。</li>
 * 	<li>短信待办。</li>
 * 	<li>OA待办。</li>
 * 	<li>案件（执法办案）待办。</li>
 * 	<li>市场监管（消保、12315）待办。</li>
 * 	<li>浪潮平台（含食品、人事、市场监管等）待办。</li>
 * </ol>
 * 
 * TODO 统一返回值格式
 */
public class PortalGDZCTodoService {
	private static PropertiesUtil TodoPropertiesUtil = PropertiesUtil.getInstance("GwssiPortalTodo");
	
	
	public static void main(String[] args) throws Exception{
		PortalGDZCTodoService t = new PortalGDZCTodoService();
		//String userId = "LINQY1@SZAIC";
		String userId = "LINAN@SZAIC";//@szaic.gov.cn
		//String name = "林其友";
		String name = "李楠";
		String ret = "";
		
		//浪潮平台，{"count":"0"}
		ret = t.callPermanentAssetsService(userId,name);
		System.out.println("GIAP："+ret);
		
	}
	
	private static final Logger logger = LoggerFactory.getLogger(PortalGDZCTodoService.class);
	
	/***
	 * 登记许可待办。
	 * 直接查浪查询固定资产的库。
	 * @return count 待办数
	 */
/*	public String callPermanentAssetsService(String userId,String name) throws Exception{
		// 1、定义临时变量
		int totalCnt = 0;
		int singleTodoCnt = 0;
		
		// 2、查询待办数量
		// 2.1 第1个
		StringBuffer sql = new StringBuffer();
		//sql .append("select count(1) from gcloud_assetman.assetman_apply where current_user_id = ? and current_dispose_name = ? ");
		sql .append("select count(1) from gcloud_assetman.assetman_apply where current_user_id = ? and current_dispose_name = ? ");
		//sql .append("select count(1) from assetman_apply where current_user_id = ? and current_dispose_name = ?");
		
		//singleTodoCnt = SpringJdbcUtil.queryForInt(AppConstants.DATASOURCE_KEY_DJXK,sql.toString(), userId,name);
		//singleTodoCnt = SpringJdbcUtil.queryForInt(AppConstants.DATASOURCE_KEY_DJXK,sql.toString(), userId);
		logger.debug("Single Todo Cnt 1 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+singleTodoCnt);
		System.out.println("参数： userId ===>  " + userId + "  name======> " + name);
		totalCnt += singleTodoCnt;

		// 3、 封装返回值
		Map<String,String> map = new HashMap<String,String>();
		map.put("count", String.valueOf(totalCnt));
		map.put("appId", "pgmId");
		map.put("pId", "pgman");
		String ret =JSON.toJSONString(map);
		
		return ret;
	}*/
	
	
	public String callPermanentAssetsService(String userId,String name) throws Exception{
		logger.debug("固定资产 代办 start>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String result = null;
			// 1、处理UserId
			String[] userArray = userId.split("@");
			String userIdWithoutDomainName = "";
			if(userArray!=null && userArray.length>0){
				userIdWithoutDomainName = userArray[0];
			}else{
				throw new RuntimeException("UserId格式不正确，UserId："+userId);
			}
			if(StringUtils.isBlank(userIdWithoutDomainName)){
				throw new RuntimeException("UserId格式不正确，UserId："+userId);
			}
			
			String paramName = TodoPropertiesUtil.getProperty("OAPARA_NAME_GD"); // 参数名称
			
			try {
				// 1、准备 Web Service 调用对象
				String endpointAddress = TodoPropertiesUtil.getProperty("OA_LANG_CHAO_ADD"); // 地址
				String targetNamespace = TodoPropertiesUtil.getProperty("OA_TARGET_NAME_SPACE_GD"); // targetNamespace
				String operName = TodoPropertiesUtil.getProperty("OA_OPER_NAME_GD"); // operName
				
				//直接引用远程的wsdl文件
				Service service = new Service();
				Call call = (Call) service.createCall();
				call.setTargetEndpointAddress(endpointAddress);
				call.setOperationName(new QName(targetNamespace, operName));
				call.addParameter(paramName, XMLType.XSD_STRING, ParameterMode.IN);//接口的参数
				call.setTimeout(60000); //设置超时间间
				logger.debug("call webService start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+userId);
				
				// 2、发起调用
				/*Integer response =(Integer) call.invoke(new Object[]{userId});
				if(response==null || response<0){
					throw new RuntimeException("固定资产 代办返回值异常，返回值：\""+response+"\"。");
				}*/
				
				
				String response =(String) call.invoke(new Object[]{userId});
				System.out.println(response);
				
				if(StringUtils.isBlank(response) || "null".equalsIgnoreCase(response)){
					throw new RuntimeException("固定资产待办返回值异常，返回值：\""+response+"\"。");
				}

				if(result==null) {
					result = "0";
				}
				// 3、封装返回值
				// FIXME 如果返回格式这么简单，用JSON效率太低，改为字符串处理。
				Map<String,Object> resultMap = new HashMap<String,Object>();
				resultMap.put("count", response);
				resultMap.put("appId","12b1d7435d6e41b385da165991e4c8fb");
				resultMap.put("pId","LE");
				result = JSON.toJSONString(resultMap);
				
				logger.debug("result is>>>>>>>>>>>>>>>>>>>>>>>>> "+result);
				logger.info("=================================固定资产 代办待办业务返回数据===================================" + result);
				logger.info("=================================固定资产 代办待办业务结束===================================");
				return result;
			} catch (Exception e) {
				logger.debug("固定资产 代办待办 业务webserice 出现异常了，以下是异常内容。\n");
				logger.debug(e.getMessage());
				System.out.println(e.getMessage());
				return result;
			}
		
	}
	
	
	
}
