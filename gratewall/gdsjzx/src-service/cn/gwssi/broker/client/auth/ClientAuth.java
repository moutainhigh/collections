package cn.gwssi.broker.client.auth;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import cn.gwssi.broker.client.listener.ClientExecuteListener;
import cn.gwssi.broker.client.task.SyncClientWithTimeout;
import cn.gwssi.common.exception.BrokerException;
import cn.gwssi.common.model.RequestContext;
import cn.gwssi.common.model.SynReponseContext;
import cn.gwssi.common.model.TPtFwrzjbxxBO;
import cn.gwssi.common.model.TPtFwrzxxxxBO;
import cn.gwssi.common.resource.BeanXmlUtil;
import cn.gwssi.common.resource.ServiceConstants;

public class ClientAuth {
	//缓存应用可访问服务情况<服务名称，服务地址>
	public static ConcurrentHashMap<String, List<Map<String,String>>> authMap=new ConcurrentHashMap<String, List<Map<String,String>>>();
	private static ThreadLocal<Map<String, String>> local = new ThreadLocal<Map<String, String>>();
	private static Logger log = Logger.getLogger("kafka");

	public static void setAuth(Map<String, String> auth) {
		local.set(auth);
	}

	public static Map<String, String> getAuth() {
		return local.get();
	}

	/**
	 * 验证该服务对象是否在数据中心注册
	 * @param requestContext
	 * @param clientCacheFileName 
	 * @return
	 * @throws BrokerException
	 */
	public static boolean checkAuth(RequestContext requestContext, String clientCacheFileName) throws BrokerException {
		boolean flag = false;
		Date date= new Date();
		String uid = UUID.randomUUID().toString();
		requestContext.setServiceName(ServiceConstants.DATA_CENTER_AUTH_CHECK);//验证该服务对象是否在数据中心注册 服务
		requestContext.setServiceId(uid);
		requestContext.setExcuteServicePath(ServiceConstants.DATA_CENTER_AUTH_CLASS);//数据中心权限验证和缓存类
		requestContext.setServiceUrl(ServiceConstants.SERVICE_URL+"/SOAService/synUnifiedService/"+ServiceConstants.DATA_CENTER_AUTH_CLASS);
		requestContext.setTimeOut("10000");
		
		TPtFwrzjbxxBO t = new TPtFwrzjbxxBO();
		t.setFwmc("客户端权限验证");
		t.setFwrzjbid(uid);
		t.setCallertime(ServiceConstants.sdf.format(date));
		t.setCallername(requestContext.getObjectName());
		t.setCallerparameter(requestContext.getRequestParam() == null ? "{}" : requestContext.getRequestParam().toString());
		t.setCallerip(ClientExecuteListener.ip);
		t.setCalleer(ServiceConstants.DATA_CENTER_NAME);
		
		TPtFwrzxxxxBO t1 = new TPtFwrzxxxxBO();
		t1.setFwrzjbid(uid);
		Date startTime = new Date();
		t1.setStartTime(ServiceConstants.sdf.format(startTime));
		t1.setObj("客户端获取本地权限数据服务");
		t1.setFwrzxxid(UUID.randomUUID().toString());
		t1.setDetail("服务名："+ServiceConstants.DATA_CENTER_AUTH_CHECK+" IP："+ClientExecuteListener.ip);
		
		SynReponseContext reponseContext = null;
		try {
			t1.setDetail(requestContext.toString());
			SyncClientWithTimeout.callSyncWithTimout(requestContext);
			reponseContext = SyncClientWithTimeout.synReponseContext;
			t.setExecuteresult(reponseContext.getDesc());
			t1.setCode("0");
			t1.setExecutecontent("成功");
			t.setExecuteresult("成功");
		} catch (BrokerException e) {
			t.setExecuteresult("失败");
			t1.setCode("1");
			t1.setExecutecontent("失败");
			t.setExecuteresult(e.getMsg()+e.getMsgDes());
			e.printStackTrace();
			throw new BrokerException(e.getCode(),e.getMsg(),e.getMsgDes());
		}finally{
			Date callerenttime =new Date(); 
			long executetime = callerenttime.getTime()-date.getTime();
			t.setCallerenttime(ServiceConstants.sdf.format(callerenttime));
			t.setExecutetime(String.valueOf(executetime));
			log.info(t);
			Date endTime =new Date(); 
			long time = endTime.getTime()-startTime.getTime();
			t1.setEndTime(ServiceConstants.sdf.format(endTime));
			t1.setTime(String.valueOf(time));
			log.info(t1);
		}
		if(reponseContext!=null){
			if(reponseContext.getCode()!=null && "0".equals(reponseContext.getCode())){
				List<String> result = reponseContext.getResponseResult();
				List<Map<String, String>> cacheResult = new ArrayList<Map<String,String>>();
				if(result!=null && result.size()>0){
					for(String s:result){
						System.out.println(s);
						cacheResult.add(BeanXmlUtil.xml2Map(s));
					}
				}
				authMap.remove("cache");
				authMap.clear();
				authMap.put("cache", cacheResult);
				BeanXmlUtil.StringTofile(BeanXmlUtil.list2xml(cacheResult), clientCacheFileName, ServiceConstants.UTFCHARSET);
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * 传入参数，与缓存对比，鉴权
	 * @param map
	 * @return
	 */
	public static boolean clientPermissionCheck(String serviceName,String ip){
		RequestContext r=null;
		boolean flag = false;//无权限
		if(StringUtils.isNotBlank(serviceName)&&StringUtils.isNotBlank(ip)){
			String[] strIp = ip.split("\\n");
			if(ClientAuth.authMap!=null && ClientAuth.authMap.size()>0){
				List<Map<String,String>> cacheList = ClientAuth.authMap.get("cache");
				if(cacheList!=null && cacheList.size()>0){
					for(Map<String,String> cacheMap:cacheList){
						if(cacheMap!=null && cacheMap.size()>0){
							if("0".equals(cacheMap.get("type"))&&cacheMap.get("servicename").equals(serviceName)&&cacheMap.get("serviceobjectip").equals(strIp[0])&&StringUtils.isNotBlank(cacheMap.get("invokeclass"))&&StringUtils.isNotBlank(cacheMap.get("alias"))&&StringUtils.isNotBlank(cacheMap.get("defaulttime"))&&StringUtils.isNotBlank(cacheMap.get("serviceobjectnamef"))){
								/*cacheOneMap.clear();
								cacheOneMap.putAll(cacheMap);*/
								local.set(cacheMap);
								flag = true;
								break;
							}
						}
					}
				}
			}
		}
		return flag;
	}
	
}
