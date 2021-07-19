package com.gwssi.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gwssi.AppConstants;
import com.gwssi.util.ErrorUtil;
import com.gwssi.util.PropertiesUtil;
import com.gwssi.util.SpringJdbcUtil;

/**
 * <h3>查询待办</h3>
 * <ol>
 * 	<li>财务待办。</li>
 * 	<li>短信待办。</li>
 * 	<li>OA待办。</li>
 * 	<li>案件（执法办案）待办。</li>
 * 	<li>市场监管（消保、12315）待办。</li>
 * 	<li>浪潮平台（含食品、人事、市场监管等）待办。</li>
 * 	<li>市场监管委合同管理系统待办 联系方== 中科保泰公司-黄勇  。</li>
 * </ol>
 * 
 * TODO 统一返回值格式
 */
public class PortalTodoService {
	
	public static void main(String[] args) throws Exception{
		PortalTodoService t = new PortalTodoService();
		String userId = "CHANGRUAN@SZAIC";
		//userId = "WANGYANG@SZAIC";//林 没有 0
		userId = "LINGQY1@SZAIC";//林 没有 0
		userId = "ZHANGSAI@SZAIC";//林 没有 0
		userId = "DMH@SZAIC";//林 没有 0
		String ret = "";
		// 财务，连不上库
	    /// ret = t.callCaiWuService(userId);
		//System.out.println("财务："+ret);
		

		
		 /*  ret = t.callWcmService(userId);
			System.out.println("wcm："+ret);*/
		
		
		//OA，返回：[{"typeName":"待分发","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000556","count":0},{"typeName":"待合并","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000559","count":0},{"typeName":"待核稿","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000561","count":0},{"typeName":"待办理","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000569","count":11},{"typeName":"领导新审批","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000567","count":5},{"typeName":"主任拟办","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000566","count":0},{"typeName":"已合并","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000565","count":0},{"typeName":"待盖章","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000560","count":0},{"typeName":"待排版","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000562","count":0},{"typeName":"待校对","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000563","count":0},{"typeName":"已校对","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000564","count":0},{"typeName":"待拟办","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000588","count":0},{"typeName":"待审批","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000589","count":0},{"typeName":"待签收","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC","count":0},{"typeName":"待阅读","url":"http://wsjgj/oa/jsp/edoc_daiyue_list.jsp?userId=LINQY1@SZAIC","count":17}]
		/*ret = t.callOaService(userId);
		System.out.println("OA："+ret);
		System.out.println(ret);*/
		
		// 短信，返回：{"code":"0","message":"3"}
		//ret = t.callSmsService(userId);
	  // System.out.println("短信："+ret);
		
		// 案件，返回：{"count":0}
		/*ret = t.callCaseService(userId);
		System.out.println("案件："+ret);*/
		
		//浪潮平台，{"count":"0"}
	  //ret = t.callGiapService(userId);
	 //、System.out.println("GIAP："+ret);///==========连接不上
		//userId = "WANGYANG@SZAIC"; //有1
		
		//userId="XIAKS@SZAIC";
		//userId = "CHANGRUAN@SZAIC";
		
		
		ret = t.callGiapService(userId);
		System.out.println("登记许可" +ret);
		
		// ret = t.callGiapService(userId);
		System.out.println("GIAP："+ret);
		
		
		
		//ret = t.callCBMService(userId);
		//System.out.println("合同管理系统===" + ret);
		
		
		//WCM，返回：{"count":"0"}
		//ret = t.callWcmService(userId);
		//System.out.println("WCM："+ret);
		
		// 市场监管，返回：{"count":"0","szjxjgTaskNum":"0","xbwqNum":"0"}
		//ret = t.callMsService(userId);
	    //System.out.println("市场监管："+ret);
	}
	
	private static final Logger logger = LoggerFactory.getLogger(PortalTodoService.class);
	
	private static PropertiesUtil TodoPropertiesUtil = PropertiesUtil.getInstance("GwssiPortalTodo");

	/**
	 * 财务待办。
	 * 直接调用财务系统存储过程。
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
/*	public String callCaiWuService(String userId)throws Exception {
		String retJsonString = "";
		try{
			// 1、处理UserId格式
			String userIdWithoutDomainName =  StringUtils.replace(StringUtils.upperCase(userId), "@SZAIC", "");
			// 2、调用财务系统存储过程
			List<Map<String, Object>> cwTodoList = SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_CW, 
					"{call toAuditFromAllDatabase(?)}", userIdWithoutDomainName);
			// 3、封装返回值
			retJsonString = JSON.toJSONString(cwTodoList);
		}catch(Throwable e){
			e.printStackTrace();
			retJsonString = ErrorUtil.getErrorResponse(e.getMessage());
		}
		return retJsonString;
	}*/
	
	
	
	
	public String callCaiWuService(String userId)throws Exception {
		List cwTodoList  =  new ArrayList();
		HashMap returnMap = new HashMap();
		logger.debug("getSCJGWaitByWebService start>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String returnStr  = null;
		try {
			// 1、准备 Web Service 调用对象
			String endpointAddress = TodoPropertiesUtil.getProperty("CW_ADD"); // 地址
			String targetNamespace = TodoPropertiesUtil.getProperty("CW_TARGET_NAME_SPACE_TODO"); // targetNamespace
			String operName = TodoPropertiesUtil.getProperty("CW_OPER_NAME"); // operName
			String paramName = TodoPropertiesUtil.getProperty("CW_PARA_NAME"); // 参数名称
			
			//直接引用远程的wsdl文件
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(endpointAddress);
			call.setOperationName(new QName(targetNamespace, operName));
			call.addParameter(paramName, XMLType.XSD_STRING, ParameterMode.IN);//接口的参数
			logger.debug("call webService start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+userId);
			call.setTimeout(60000); //设置超时间间
			
			// 2、发起调用
			String tempStr =  (String) call.invoke(new Object[]{userId});
			if(tempStr==null || tempStr.isEmpty()){
				throw new RuntimeException("财务待办返回值异常，返回值为空。");
			}
			
			returnMap.put("myAudit", tempStr);
			cwTodoList.add(returnMap);
			
			
			// 3、封装返回值
			returnStr =JSON.toJSONString(cwTodoList);
			//System.out.println(returnStr);
			logger.info("财务待办 result is>>>>>>>>>>>>>>>>>>>>>>>>> "+returnStr + "   ===> 该域账号为===> "  + userId);
		} catch (Exception e) {
			logger.debug("财务待办返回异常" + e.getMessage());
			//System.out.println("财务待办异常" + e.getMessage());
		}
		return returnStr;
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 短信待办。
	 * 调用陈孝华提供的Web Service，通过OSB中转。
	 * 
	 * FIXME 考虑缓存 Call 对象。
	 * 
	 * @param userId
	 * @return
	 * @throws ServiceException 
	 * @throws RemoteException 
	 */
	public String callSmsService(String userId) throws ServiceException, RemoteException {
		logger.debug("getMessageWaitByWebService start>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
		
		// 2、准备 Web Service 调用对象
		String endpointAddress = TodoPropertiesUtil.getProperty("DX_ADDR"); // 地址
		String targetNamespace = TodoPropertiesUtil.getProperty("DX_NAME_SPACE"); // targetNamespace
		String operName = TodoPropertiesUtil.getProperty("DX_OPER_NAME"); // operName
		String paramName = TodoPropertiesUtil.getProperty("DX_PARAM_NAME"); // 参数名称
		
		// 直接引用远程的wsdl文件
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(endpointAddress);
		call.setSOAPActionURI("http://tempuri.org/getSmsCount");
		call.setOperationName(new QName(targetNamespace, operName));
		call.addParameter(new QName(targetNamespace, paramName),XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.setTimeout(60000); //设置超时间间
		
		logger.debug("call webService start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ userIdWithoutDomainName);
		
		// 3、发起调用
		result = (String) call.invoke(new Object[] { userIdWithoutDomainName });
		if(StringUtils.isBlank(result)){
			throw new RuntimeException("短信待办返回值异常，返回值为空。");
		}
		JSONObject jo = JSON.parseObject(result);
		String code = jo.getString("code");
		if("0".equals(code)){
			jo.remove("code");
			String count = jo.getString("message");
			try{
				int cnt = Integer.parseInt(count);
				if(cnt<0){
					throw new RuntimeException("短信待办返回值错误："+count);
				}
			}catch(Throwable e){
				throw new RuntimeException("短信待办返回值错误："+count);
			}
			jo.remove("message");
			jo.put("count", count);
			jo.put("appId", "8b80a6d286b14a5f8a2f344d66666po2");
			jo.put("pId", "AM");
			result = jo.toJSONString();

			logger.debug("result is>>>>>>>>>>>>>>>>>>>>>>>>> " + result);
			return result ;
		}
		if("1".equals(code)){
			// 当前用户无权限处理待办
			jo.put("count", "0");
			jo.remove("code");
			result = jo.toJSONString();
			
			logger.debug("result is>>>>>>>>>>>>>>>>>>>>>>>>> " + result);
			return result ;
		}
		if("2".equals(code)){
			String message = jo.getString("message");
			throw new RuntimeException("获取短信待办出错："+message);
		}
		// 4、返回结果
		jo.put("count", "0");
		return jo.toJSONString();
	}
	/**
	 * 执法办案待办。（华宇案件系统）
	 * 
	 * 通过 Web Service。
	 * 
	 * FIXME 考虑缓存 Call 对象。
	 * 
	 * @param userId
	 * @return
	 * @throws ServiceException 
	 * @throws RemoteException 
	 */
	public String callCaseService(String userId) throws ServiceException, RemoteException {
		logger.debug("getZFBAWaitByWebService start>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String result = null;
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("appId","12b1d7435d6e41b385da165991e4c8fb");
		resultMap.put("pId","LE");
		try {
			// 1、准备 Web Service 调用对象
			String endpointAddress = TodoPropertiesUtil.getProperty("ZFBA_ADDR"); // 地址
			String targetNamespace = TodoPropertiesUtil.getProperty("ZFBA_NAME_SPACE"); // targetNamespace
			String operName = TodoPropertiesUtil.getProperty("ZFBA_OPER_NAME"); // operName
			String paramName = TodoPropertiesUtil.getProperty("ZFBA_PARAM_NAME"); // 参数名称
			
			//直接引用远程的wsdl文件
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(endpointAddress);
			call.setOperationName(new QName(targetNamespace, operName));
			call.addParameter(paramName, XMLType.XSD_STRING, ParameterMode.IN);//接口的参数
			logger.debug("call webService start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+userId);
			call.setTimeout(60000); //设置超时间间
			
			// 2、发起调用
			Integer response =(Integer) call.invoke(new Object[]{userId});
			if(response==null || response<0){
				throw new RuntimeException("案件待办返回值异常，返回值：\""+response+"\"。");
			}
			
			// 3、封装返回值
			// FIXME 如果返回格式这么简单，用JSON效率太低，改为字符串处理。
			
			resultMap.put("count", response);
		
			
			logger.debug("result is>>>>>>>>>>>>>>>>>>>>>>>>> "+result);
		} catch (Exception e) {
			logger.debug("案件待办返回值异常" + e.getMessage());
			System.out.println("案件待办返回值异常" + e.getMessage());
			resultMap.put("count", 0);
			
		}
		
		result = JSON.toJSONString(resultMap);
		
		return result;
	}
	
	/**
	 * 市场监管待办。
	 * 
	 * 通过 Web Service。
	 * 
	 * FIXME 考虑缓存 Call 对象。
	 * 
	 * @param userId
	 * @return
	 * @throws ServiceException 
	 * @throws RemoteException 
	 */
	@SuppressWarnings("unchecked")
	public String callMsService(String userId) throws ServiceException, RemoteException {
		logger.debug("getSCJGWaitByWebService start>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String result =null;
		try {
			// 1、准备 Web Service 调用对象
			String endpointAddress = TodoPropertiesUtil.getProperty("SCJG_ADDR"); // 地址
			String targetNamespace = TodoPropertiesUtil.getProperty("SCJG_NAME_SPACE"); // targetNamespace
			String operName = TodoPropertiesUtil.getProperty("SCJG_OPER_NAME"); // operName
			String paramName = TodoPropertiesUtil.getProperty("SCJG_PARAM_NAME"); // 参数名称
			
			//直接引用远程的wsdl文件
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(endpointAddress);
			call.setOperationName(new QName(targetNamespace, operName));
			call.addParameter(paramName, XMLType.XSD_STRING, ParameterMode.IN);//接口的参数
			logger.debug("call webService start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+userId);
			call.setTimeout(60000); //设置超时间间
			
			// 2、发起调用
			Map<String,Object> resultMap = (Map<String, Object>) call.invoke(new Object[]{userId});
			if(resultMap==null || resultMap.isEmpty()){
				throw new RuntimeException("市场监管待办返回值异常，返回值为空。");
			}
			
			// 3、封装返回值
			resultMap.put("appId","8b80a6d286b14a5f8a2f344d64446cf7");
			resultMap.put("pId","MS");
			result =JSON.toJSONString(resultMap);
			logger.debug("result is>>>>>>>>>>>>>>>>>>>>>>>>> "+resultMap);
		} catch (Exception e) {
			logger.debug("市场监管待办返回值待办返回值异常" + e.getMessage());
			System.out.println("市场监管待办返回值待办返回值异常" + e.getMessage());
		}
		return result;
	}
	/**
	 * OA待办。
	 * 
	 * @param userId
	 * @return
	 * @throws ServiceException 
	 * @throws RemoteException 
	 */
	public String callOaService(String userId) throws ServiceException, RemoteException {
		logger.debug("getOaWaitByWebService start>>>>>>>>>>>>>>>>>>>>>>>>>>");
		// 1、准备 Web Service 调用对象
		String result = null;
		
		try {
			String endpointAddress = TodoPropertiesUtil.getProperty("OA_UPCOMING_ADDR"); // 地址
			String targetNamespace = TodoPropertiesUtil.getProperty("OA_TARGET_NAME_SPACE"); // targetNamespace
			String operName = TodoPropertiesUtil.getProperty("OA_OPER_NAME"); // operName
			String paramName = TodoPropertiesUtil.getProperty("OA_PARAM_NAME"); // 参数名称
			
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(endpointAddress);
			call.setOperationName(new QName(targetNamespace, operName));
			call.addParameter(paramName, XMLType.XSD_STRING, ParameterMode.IN);//接口的参数
			call.setReturnType(XMLType.XSD_STRING);//设置返回类型 
			logger.debug("call webService start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+userId);
			call.setTimeout(60000); //设置超时间间
			// 2、发起调用
			result = (String)call.invoke(new Object[]{userId});
			if(StringUtils.isBlank(result) || "null".equalsIgnoreCase(result)){
				throw new RuntimeException("OA待办返回值异常，返回值：\""+result+"\"。");
			}
			logger.debug("result is>>>>>>>>>>>>>>>>>>>>>>>>> "+result);
			
		} catch (Exception e) {
			logger.debug("OA公文待办返回值异常" + e.getMessage());
			System.out.println("OA公文待办返回值异常" + e.getMessage());
		}
		
		// 3、返回结果
		return result;
	}
	
	
	/***
	 * 登记许可待办。
	 * 
	 * 直接查浪潮的库。
	 * 
	 * 共有3个待办，累加求和。
	 * 
	 * @param userId
	 * @return count 待办数
	 */
	public String callGiapService(String userId) throws Exception{
		// 1、定义临时变量
		int totalCnt = 0;
		int singleTodoCnt = 0;
		
		/*// 2、查询待办数量
		// 2.1 第1个
		StringBuffer sql = new StringBuffer();
		sql .append("SELECT ")
			.append("	count(1) ")
			.append("	FROM GCLOUD_GIAP_aicmer.GIAP_APPLY_BASE BASE")
			.append("	LEFT JOIN  GCLOUD_GIAP_aicmer.GIAP_APPLY_WORKFLOW WF    ")
			.append("	ON BASE.SERIAL_NO = WF.SERIAL_NO ")
			.append("	WHERE EXISTS ")
			.append("	(SELECT 1  FROM  GCLOUD_GIAP_aicmer.GIAP_APPLY_DAI_BAN DB WHERE BASE.SERIAL_NO = DB.SERIAL_NO AND DB.ORGAN_ID = ?)")
			.append("	AND BASE.IS_FINISH = \'0\'")
			.append("	AND WF.IS_FINISH = \'0\'")
			.append("	AND BASE.TASK_STATE = \'TODO\'")
			.append("	ORDER BY WF.TASK_CREATE_TIME DESC");
		singleTodoCnt = SpringJdbcUtil.queryForInt(AppConstants.DATASOURCE_KEY_DJXK,sql.toString(), userId);
		logger.debug("Single Todo Cnt 1 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+singleTodoCnt);
		totalCnt += singleTodoCnt;*/

/*		// 2.2 第2个
		sql = new StringBuffer();
		sql.append(" SELECT count(1) FROM GCLOUD_NAME.NAME_ENTERPRISE_INFO EI JOIN GCLOUD_NAME.NAME_TASK_ALLOCATION TA ON EI.ID=TA.INFO_ID WHERE 1=1 AND ( EI.APPROVAL_STATE = \'10\' OR EI.APPROVAL_STATE = \'12\' OR EI.APPROVAL_STATE = \'13\' ) AND TA.ORGAN_ID = ? ORDER BY EI.APPLY_DATE DESC");
		singleTodoCnt = SpringJdbcUtil.queryForInt(AppConstants.DATASOURCE_KEY_DJXK,sql.toString(), userId);
		logger.debug("Single Todo Cnt 2 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+singleTodoCnt);
		totalCnt += singleTodoCnt;

		// 2.3  第3个
		sql = new StringBuffer();
		sql.append("SELECT count(1) FROM LS_BPM.WF_DAI_BAN_TASK PROCTABLE WHERE PROCTABLE.IS_VISIBLE = \'1\'   AND PROCTABLE.PROCESS_TYPE = \'HR\' AND ORGAN_ID =? ORDER BY PROCTABLE.CREATE_TIME");
		singleTodoCnt = SpringJdbcUtil.queryForInt(AppConstants.DATASOURCE_KEY_DJXK,sql.toString(), userId);
		logger.debug("Single Todo Cnt 3 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+singleTodoCnt);
		totalCnt += singleTodoCnt;
		
		logger.debug("Total Todo Cnt >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+totalCnt);
		
		// 3、 封装返回值
		Map<String,String> map = new HashMap<String,String>();
		map.put("count", String.valueOf(totalCnt));
		map.put("appId", "8b80a6d286b14a5f8a2f344d64445gh9");
		map.put("pId", "RP");
		String ret =JSON.toJSONString(map);
		
		return ret;*/
		
		
		logger.debug("登记许可待办  start>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
			
			
			try {
				// 1、准备 Web Service 调用对象
				String endpointAddress = TodoPropertiesUtil.getProperty("OA_LANG_CHAO_ADD"); // 地址
				String targetNamespace = TodoPropertiesUtil.getProperty("OA_TARGET_NAME_SPACE_APP"); // targetNamespace
				String operName = TodoPropertiesUtil.getProperty("OA_OPER_NAME_APP"); // operName
				String paramName = TodoPropertiesUtil.getProperty("OAPARA_NAME_APP"); // 参数名称
				
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

				if(StringUtils.isBlank(response) || "null".equalsIgnoreCase(response)){
					throw new RuntimeException("登记许可待办返回值异常，返回值：\""+response+"\"。");
				}
				
				
				// 3、封装返回值
				// FIXME 如果返回格式这么简单，用JSON效率太低，改为字符串处理。
				Map<String,String> map = new HashMap<String,String>();
				map.put("count", String.valueOf(response));
				map.put("appId", "8b80a6d286b14a5f8a2f344d64445gh9");
				map.put("pId", "RP");
				result =JSON.toJSONString(map);
				
				logger.debug("result is>>>>>>>>>>>>>>>>>>>>>>>>> "+result);
				logger.info("=================================登记许可待办 业务返回数据===================================" + result);
				logger.info("=================================登记许可待办 待办业务结束===================================");
			} catch (Exception e) {
				logger.debug("登记许可待办 业务webserice 出现异常了，以下是异常内容。\n");
				logger.debug(e.getMessage());
				System.out.println("登记许可待办 业务 " + e.getMessage());
			}
			return result;
		
		
	}
	
	
	
	/**
	 * 返回代办数据通过oracle数据库获取代办sql
	 * 
	 *   todoConfig['HR'] = todoPath + "GetWaitNo&pkid=dabf14ddda2e4bf9b2f639f686350b88";
	 * @param userId
	 * @param request
	 * @return
	 */
	public String CallNoByDataBase(String userId, HttpServletRequest request){
		/*String ret="";
		String sql	="select * from V_TODO_SQL t where t.pk_sys_integration = ?";
		String pkid= request.getParameter("pkid");
		
		if(StringUtils.isEmpty(pkid)){
			return null;
		}
		List<Map<String,Object>> list= SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_YYJC,sql.toString(), pkid);
		if(list!=null&&list.size()>0){
			Map<String,Object> map1 =list.get(0);
			
			String sql2=(String) map1.get("WAIT_SQL");
			String datasourekey =(String) map1.get("DATASOURCEKEY");
			String pid =(String) map1.get("PID");
			int singleTodoCnt = SpringJdbcUtil.queryForInt(datasourekey,sql2.toString(), userId);
			
			Map<String,String> map = new HashMap<String,String>();
			map.put("count", String.valueOf(singleTodoCnt));
			map.put("appId", pkid);
			map.put("pId", pid);
			 ret =JSON.toJSONString(map);
		}
		return ret;*/
		
		Map<String,String> map = new HashMap<String,String>();
		
		String pkid= request.getParameter("pkid");
		map.put("appId", pkid);
		map.put("pId", "AM");
		logger.debug("CallNoByDataBase待办  start>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
			
			
			try {
				// 1、准备 Web Service 调用对象
				String endpointAddress = TodoPropertiesUtil.getProperty("OA_LANG_CHAO_ADD"); // 地址
				String targetNamespace = TodoPropertiesUtil.getProperty("OA_TARGET_NAME_SPACE_TODO"); // targetNamespace
				String operName = TodoPropertiesUtil.getProperty("OA_OPER_NAME_TODO"); // operName
				String paramName = TodoPropertiesUtil.getProperty("OAPARA_NAME_TODO"); // 参数名称
				
				//直接引用远程的wsdl文件
				Service service = new Service();
				Call call = (Call) service.createCall();
				call.setTargetEndpointAddress(endpointAddress);
				call.setOperationName(new QName(targetNamespace, operName));
				call.addParameter(paramName, XMLType.XSD_STRING, ParameterMode.IN);//接口的参数
				call.setTimeout(60000); //设置超时间间
				logger.debug("call webService start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+userId);
				
				String response =(String) call.invoke(new Object[]{userId});

				if(StringUtils.isBlank(response) || "null".equalsIgnoreCase(response)){
					throw new RuntimeException("CallNoByDataBase异常，返回值：\""+response+"\"。");
				}
				
				map.put("count", String.valueOf(response));
				//JSONObject jo = JSON.parseObject(response);
				
				// 3、封装返回值
			
				//map.put("appId", "8b80a6d286b14a5f8a2f344d64445gc9");
				
				
				System.out.println("=================================CallNoByDataBase 业务返回数据===================================" + result);
				logger.debug("result is>>>>>>>>>>>>>>>>>>>>>>>>> "+result);
				logger.info("=================================CallNoByDataBase 业务返回数据===================================" + result);
				logger.info("=================================CallNoByDataBase 待办业务结束===================================");
			} catch (Exception e) {
				logger.debug("CallNoByDataBase ===>人事系统 业务webserice 出现异常了，以下是异常内容。\n");
				logger.debug(e.getMessage());
				System.out.println("CallNoByDataBase 人事系统 业务webserice 出现异常了" +e.getMessage());
				map.put("count", "0");
			}
			
			
			result =JSON.toJSONString(map);
			return result;
	}
	
	
	
	
	public String callWcmService(String userId) throws Exception {
		/*//userId = "huangxd2_szaic";
		
		// 0、定义变量
		int count = 0;
		
		// 1、根据UserId（形如：LINQY1_ZAIC）查询WCM系统中 WcmUserId (数字格式，形如：9527)
		//userId =StringUtils.replace(StringUtils.lowerCase(userId), "@", "_");
		
		String sql = "select w.userid from WCMUSER w where w.USERNAME= ?";
		List<Map<String, Object>> wcmUserId =SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_WCM, sql, userId);
		String  ids =  "";
		if(wcmUserId!=null&&wcmUserId.size()>0) {
			ids = (String) wcmUserId.get(0).get("USERID");
		}
		
		logger.debug("WCM User Id>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>query  "+wcmUserId);
		logger.debug("WCM User Id>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>wrap===> "+ids);
		
		// 2、查询待办数量
	//	sql = "select count(1) from  WCMFLOWDOC w , wcmdocument du where w.worked = 0 and du.docid =w.objid and du.docstatus > 0 and w.worktime is null  and w.tousers is not null and( w.tousers =? or  w.tousers like concat(concat(?,\',\'),\'%\') or  w.tousers like concat(concat(\'%\',\',\'),?)  or   w.tousers like concat(concat(\'%\',\',\'),concat(?,concat(\',\',\'%\'))) )";
		
		sql ="select count(1) as count from  WCMFLOWDOC w , wcmdocument du where w.worked = 0 and du.docid =w.objid and du.docstatus > 0 and w.worktime is null  and w.tousers is not null  and w.tousers = ? ";
		
		
		System.out.println("查询参数========>   " +ids);
		List<Map<String, Object>> counts = SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_WCM,sql.toString(), ids);
		BigDecimal tempCount = new BigDecimal(0);
		if(counts!=null&&counts.size()>0) {
			tempCount = (BigDecimal) counts.get(0).get("COUNT");
		}
		
		
		count = tempCount.intValue();
		logger.debug("wcm wait>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+count);*/
		
		
		
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("appId", "23b1d7435d6e41b385da165991e4c8kE");
		map.put("pId", "AM");

		 logger.debug("wcm新闻待办  start>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
			userId =StringUtils.replace(StringUtils.lowerCase(userId), "@", "_");
			
			try {
				// 1、准备 Web Service 调用对象
				String endpointAddress = TodoPropertiesUtil.getProperty("WCM_ADD"); // 地址
				String targetNamespace = TodoPropertiesUtil.getProperty("WCM_TARGET_NAME_SPACE_TODO"); // targetNamespace
				String operName = TodoPropertiesUtil.getProperty("WCM_OPER_NAME"); // operName,入口函数的
				String paramName = TodoPropertiesUtil.getProperty("WCM_PARA_NAME"); // 入口函数的参数名称
				
				//直接引用远程的wsdl文件
				Service service = new Service();
				Call call = (Call) service.createCall();
				call.setTargetEndpointAddress(endpointAddress);
				call.setOperationName(new QName(targetNamespace, operName));
				call.addParameter(paramName, XMLType.XSD_STRING, ParameterMode.IN);//接口的参数
				call.setTimeout(60000); //设置超时间间
				logger.debug("call webService start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+userId);
				
				String response =(String) call.invoke(new Object[]{userId});

				if(StringUtils.isBlank(response) || "null".equalsIgnoreCase(response)){
					throw new RuntimeException("wcm新闻待办异常，返回值：\""+response+"\"。");
				}
		
				
				String count = response;
				
				// 3、封装返回值
				
				map.put("count", String.valueOf(count));
				
			} catch (Exception e) {
				logger.debug("wcm新闻待办统 业务webserice 出现异常了，以下是异常内容。\n");
				logger.debug(e.getMessage());
				System.out.println("wcm新闻待办业务webserice 出现异常了" +e.getMessage());
				map.put("count", String.valueOf("0"));
			}
			result =JSON.toJSONString(map);
			return result;
			
		}
	
	
/* 20190220	*//**
	 * 新闻投稿待办（审稿）。
	 * 
	 * 直接查库。
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	
	/*public String callWcmService(String userId) throws Exception {
		// 0、定义变量
		int count = 0;
		
		// 1、根据UserId（形如：LINQY1_ZAIC）查询WCM系统中 WcmUserId (数字格式，形如：9527)
		userId =StringUtils.replace(StringUtils.lowerCase(userId), "@", "_");
		
		String sql = "select w.USERID from WCMUSER w where w.USERNAME= ?";
		int wcmUserId =SpringJdbcUtil.queryForInt(AppConstants.DATASOURCE_KEY_WCM, sql, userId);
		logger.debug("WCM User Id>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+wcmUserId);
		
		// 2、查询待办数量
		sql = "select count(1) from  WCMFLOWDOC w , wcmdocument du where w.worked = 0 and du.docid =w.objid and du.docstatus > 0 and w.worktime is null  and w.tousers is not null and( w.tousers =? or  w.tousers like concat(concat(?,\',\'),\'%\') or  w.tousers like concat(concat(\'%\',\',\'),?)  or   w.tousers like concat(concat(\'%\',\',\'),concat(?,concat(\',\',\'%\'))) )";
		count = SpringJdbcUtil.queryForInt(AppConstants.DATASOURCE_KEY_WCM,sql.toString(), userId,userId,userId,userId);
		logger.debug("wcm wait>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+count);
		
		// 3、封装返回值
		Map<String,String> map = new HashMap<String,String>();
		map.put("appId", "23b1d7435d6e41b385da165991e4c8kE");
		map.put("pId", "AM");
		map.put("count", String.valueOf(count));
		String result =JSON.toJSONString(map);
		
		return result;
	}*/
	
	
	
	 /* public String callWcmService(String userId)
			    throws Exception
			  {
			    int count = 0;

			    userId = StringUtils.replace(StringUtils.lowerCase(userId), "@", "_");

			    String sql = "select w.USERID from WCMUSER w where w.USERNAME= ?";
			    int wcmUserId = SpringJdbcUtil.queryForInt("wcm_db", sql, new String[] { userId });
			    logger.debug("WCM User Id>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + wcmUserId);

			    StringBuffer strsql = new StringBuffer();
			    strsql.append("select w.tousers from WCMFLOWDOC w, wcmdocument du  where w.worked = 0 and du.docid = w.objid and du.docstatus > 0  and w.worktime is null  and w.tousers is not null");
			    strsql.append("   and w.tousers like '%" + wcmUserId + "%'");

			    List list = SpringJdbcUtil.query("wcm_db", strsql.toString());
			    if ((list != null) && (list.size() > 0)) {
			      for (Map map : list) {
			        String userids = (String)map.get("TOUSERS");
			        List userList = new ArrayList();
			        Collections.addAll(userList, userids.split(","));

			        if (userList.contains(String.valueOf(wcmUserId))) {
			          count++;
			        }

			      }

			    }

			    logger.debug("wcm wait>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + count);

			    Map map = new HashMap();
			    map.put("appId", "23b1d7435d6e41b385da165991e4c8kE");
			    map.put("pId", "AM");
			    map.put("count", String.valueOf(count));
			    String result = JSON.toJSONString(map);

			    return result;
			  }*/
	
	
	/**
	 * 新闻投稿待办（审稿）。 
	 * @autor chaihw 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	/*public String callWcmService(String userId) throws Exception {
		// 0、定义变量
		int count = 0;
		
		// 1、根据UserId（形如：LINQY1_ZAIC）查询WCM系统中 WcmUserId (数字格式，形如：9527)
		userId =StringUtils.replace(StringUtils.lowerCase(userId), "@", "_");
		
		String sql = "select w.USERID from WCMUSER w where w.USERNAME= ?";
		int wcmUserId =SpringJdbcUtil.queryForInt(AppConstants.DATASOURCE_KEY_WCM, sql, userId);
		System.out.println("=======> WCM callWcmService门户投稿代办用户id===>  " + wcmUserId);
		wcmUserId=4115;
		logger.debug("WCM User Id>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+wcmUserId);
		
		
		StringBuffer strsql = new StringBuffer();
		strsql.append("select w.tousers from WCMFLOWDOC w, wcmdocument du  where w.worked = 0 and du.docid = w.objid and du.docstatus > 0  and w.worktime is null  and w.tousers is not null");
		strsql.append("   and w.tousers like '%"+wcmUserId+"%'");
		
		List<Map<String,Object>> list= SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_WCM,strsql.toString());
		if(list!=null &&list.size()>0){
			for(Map<String,Object> map:list){
				String userids = (String) map.get("TOUSERS");
				List<String> userList = new ArrayList<String>();
				Collections.addAll(userList, userids.split(","));
				
				if(userList.contains(String.valueOf(wcmUserId))){
					count=count+1;
				}else{
					
				}
				
				
			}
			
		}
		
		
		System.out.println("当前用户的门户投稿代办数量为 : " + count);
		logger.debug("wcm wait>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+count);
		

		
		// 3、封装返回值
		Map<String,String> map = new HashMap<String,String>();
		map.put("appId", "23b1d7435d6e41b385da165991e4c8kE");
		map.put("pId", "AM");
		map.put("count", String.valueOf(count));
		String result =JSON.toJSONString(map);
		
		return result;
	}	*/
	
	//合同管理系统
	public String callCBMService(String userId) throws ServiceException, RemoteException {
		logger.debug("getCBMWaitByWebService start>>>>>>>>>>>>>>>>>>>>>>>>>>");
		// 1、准备 Web Service 调用对象
		String result = null;
		
		try {
			String endpointAddress = TodoPropertiesUtil.getProperty("OA_CBM"); // 地址
			String targetNamespace = TodoPropertiesUtil.getProperty("OA_CBM_TARGET_NAME_SPACE"); // targetNamespace
			String operName = TodoPropertiesUtil.getProperty("OA_CBM_OPER_NAME"); // operName
			String paramName = TodoPropertiesUtil.getProperty("OA_CBM_PARAM_NAME"); // 参数名称
			
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(endpointAddress);
			call.setOperationName(new QName(targetNamespace, operName));
			call.addParameter(paramName, XMLType.XSD_STRING, ParameterMode.IN);//接口的参数
			call.setReturnType(XMLType.XSD_STRING);//设置返回类型 
			call.setTimeout(60000); //设置超时间间
			logger.debug("call webService start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+userId);
			
			// 2、发起调用
			result = (String)call.invoke(new Object[]{userId});
			if(StringUtils.isBlank(result) || "null".equalsIgnoreCase(result)){
				throw new RuntimeException("CBM待办返回值异常，返回值：\""+result+"\"。");
			}
			logger.debug("result is>>>>>>>>>>>>>>>>>>>>>>>>> "+result);
		} catch (Exception e) {
			logger.debug("合同管理系统待办返回>>>>>>>>>>>>>>>>>>>>>>>>> "+e.getMessage());
			//System.out.println("合同管理系统待办返回>>>>>>>>>>>>>>>>>>>>>>>>> "+e.getMessage());
		}
		
		
		// 3、返回结果
		return result;
	}
	
	
public String getEmailCounts(String userId) {
		
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
		userIdWithoutDomainName = userIdWithoutDomainName.toLowerCase();
		//System.out.println("用户域账号处理之后的结果 ====> " + userIdWithoutDomainName);
		logger.info("用户域账号处理之后的结果 ====> " + userIdWithoutDomainName);
		//System.out.println("========> 用户域账号处理之后的结果==>统一改为 @szaic.gov.cn形式" + userIdWithoutDomainName+"@szaic.gov.cn");
		logger.info("========> 用户域账号处理之后的结果==>统一改为 @szaic.gov.cn形式  *==》  " + userIdWithoutDomainName);
		userIdWithoutDomainName = userIdWithoutDomainName+"@szaic.gov.cn";
	//	System.out.println("=========> " +userIdWithoutDomainName);
		String counts = "";// 结果数
		URL wsUrl = null;
		HttpURLConnection conn = null;
		OutputStream os = null;
		InputStream is = null;
		try {
			// 服务的地址
			wsUrl = new URL("http://10.0.4.128/unreadmail/unreadmail.asmx?wsdl");

			conn = (HttpURLConnection) wsUrl.openConnection();

			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
			//
			conn.setConnectTimeout(60000);
			conn.setReadTimeout(60000);

			os = conn.getOutputStream();

		    StringBuffer bf = new StringBuffer();
	        bf.append("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:tem=\"http://tempuri.org/\">\r\n" + 
	        		"   <soap:Header/>\r\n" + 
	        		"   <soap:Body>\r\n" + 
	        		"      <tem:GetUnReadMailCountByUserPrincipalName>\r\n" + 
	        		"         <!--Optional:-->\r\n" + 
	        		"         <tem:UserPrincipalName>"+userIdWithoutDomainName+"</tem:UserPrincipalName>\r\n" + 
	        		"      </tem:GetUnReadMailCountByUserPrincipalName>\r\n" + 
	        		"   </soap:Body>\r\n" + 
	        		"</soap:Envelope>");
	        //请求体
	       /* String soap = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:tem=\"http://tempuri.org/\">\r\n" + 
	        		"   <soap:Header/>\r\n" + 
	        		"   <soap:Body>\r\n" + 
	        		"      <tem:GetUnReadMailCountByUserPrincipalName>\r\n" + 
	        		"         <!--Optional:-->\r\n" + 
	        		"         <tem:UserPrincipalName>sysadmin@szaic.gov.cn</tem:UserPrincipalName>\r\n" + 
	        		"      </tem:GetUnReadMailCountByUserPrincipalName>\r\n" + 
	        		"   </soap:Body>\r\n" + 
	        		"</soap:Envelope>";*/
			
	        //os.write(soap.getBytes());
	        os.write(bf.toString().getBytes());

			is = conn.getInputStream();

			byte[] b = new byte[1024];
			int len = 0;
			String s = "";
			while ((len = is.read(b)) != -1) {
				String ss = new String(b, 0, len, "UTF-8");
				s += ss;
			}
			//System.out.println(s);
			//System.out.println(s.substring(s.indexOf("<GetUnReadMailCountByUserPrincipalNameResult>")+45,s.indexOf("</GetUnReadMailCountByUserPrincipalNameResult>")));
			logger.info("请求exchange未读代办的结果XML内容 ===" + s);
			counts = s.substring(s.indexOf("<GetUnReadMailCountByUserPrincipalNameResult>")+45,s.indexOf("</GetUnReadMailCountByUserPrincipalNameResult>"));
			logger.info("当前用户exchange未读待办====数量===》  " +counts);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			logger.debug("邮件未读代办出现异常====\t" +e.getMessage());
		} catch (IOException e) {
			logger.debug("邮件未读代办出现异常====\t" +e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				is.close();
				os.close();
				conn.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return counts;
	}
}
