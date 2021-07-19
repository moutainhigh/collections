package cn.gwssi.broker.server.auth;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import cn.gwssi.broker.server.listener.ServiceExecuteListener;
import cn.gwssi.common.exception.BrokerException;
import cn.gwssi.common.model.RequestContext;
import cn.gwssi.common.model.SynReponseContext;
import cn.gwssi.common.model.TPtFwrzjbxxBO;
import cn.gwssi.common.model.TPtFwrzxxxxBO;
import cn.gwssi.common.resource.BeanXmlUtil;
import cn.gwssi.common.resource.ServiceConstants;

public class ServiceAuth {
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
	 * @param serviceCacheFileName 
	 * @return
	 * @throws BrokerException
	 */
	public static boolean checkAuth(RequestContext requestContext, String serviceCacheFileName) throws BrokerException {
		boolean flag = false;
		Date date= new Date();
		String uid = UUID.randomUUID().toString();
		requestContext.setServiceName(ServiceConstants.SDATA_CENTER_AUTH_CHECK);//验证该服务对象是否在数据中心注册 服务
		requestContext.setServiceId(uid);
		requestContext.setExcuteServicePath(ServiceConstants.SDATA_CENTER_AUTH_CLASS);//数据中心权限验证和缓存类
		requestContext.setServiceUrl(ServiceConstants.SERVICE_URL+"/SOAService/synUnifiedService/"+ServiceConstants.SDATA_CENTER_AUTH_CLASS);
		requestContext.setTimeOut("100000");
		SynReponseContext reponseContext = null;
		
		TPtFwrzjbxxBO t = new TPtFwrzjbxxBO();
		t.setFwrzjbid(uid);
		t.setFwmc("服务端权限验证");
		t.setCallertime(ServiceConstants.sdf.format(date));
		t.setCallername(requestContext.getObjectName());
		t.setCallerparameter(requestContext.getRequestParam() == null ? "{}" : requestContext.getRequestParam().toString());
		t.setCallerip(ServiceExecuteListener.ip);
		t.setCalleer(ServiceConstants.DATA_CENTER_NAME);
		
		TPtFwrzxxxxBO t1 = new TPtFwrzxxxxBO();
		t1.setFwrzjbid(uid);
		Date startTime = new Date();
		t1.setStartTime(ServiceConstants.sdf.format(startTime));
		t1.setObj("服务端获取本地权限数据服务");
		t1.setFwrzxxid(UUID.randomUUID().toString());
		t1.setDetail("服务名："+ServiceConstants.SDATA_CENTER_AUTH_CHECK+" IP："+ServiceExecuteListener.ip);
		
		try {
			t1.setDetail(requestContext.toString());
			SyncServiceClientWithTimeout.callSyncWithTimout(requestContext);
			reponseContext = SyncServiceClientWithTimeout.synReponseContext;
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
			//String code, String msg,String msgDes
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
						//s= s.replace("&lt;", '<' + "");s= s.replace("&gt;", '>' + "");
						cacheResult.add(BeanXmlUtil.xml2Map(s));
					}
				}
				authMap.remove("cache");
				authMap.clear();
				authMap.put("cache", cacheResult);
				BeanXmlUtil.StringTofile(BeanXmlUtil.list2xml(cacheResult), serviceCacheFileName, ServiceConstants.UTFCHARSET);
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * 传入参数，与缓存对比，鉴权(通用鉴权需要对比服务名，服务对象ip)
	 * @param serviceCode
	 * @param objectIP
	 * @param objectPort
	 * @return
	 */
	public static boolean serviceNamePermissionCheck(String serviceCode,String objectIP){
		boolean flag = false;//无权限
		if(StringUtils.isNotBlank(serviceCode)&&StringUtils.isNotBlank(objectIP)){
			if(ServiceAuth.authMap!=null && ServiceAuth.authMap.size()>0){
				List<Map<String,String>> cacheList = ServiceAuth.authMap.get("cache");
				if(cacheList!=null && cacheList.size()>0){
					for(Map<String,String> cacheMap:cacheList){
						if(cacheMap!=null && cacheMap.size()>0){
							if("0".equals(cacheMap.get("type")) && cacheMap.get("servicename").equals(serviceCode) && cacheMap.get("serviceobjectip").equals(objectIP)&&StringUtils.isNotBlank(cacheMap.get("invokeclass"))&&StringUtils.isNotBlank(cacheMap.get("alias"))&&StringUtils.isNotBlank(cacheMap.get("defaulttime"))){
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
	
	/**
	 * 传入参数，与缓存对比，鉴权(特殊鉴权只需要服务url和对象ip)
	 * @param map
	 * @return
	 */
	public static boolean servicePermissionCheck(String url,String objectIP){
		boolean flag = false;//无权限
		if(StringUtils.isNotBlank(url)&&StringUtils.isNotBlank(objectIP)){
			if(ServiceAuth.authMap!=null && ServiceAuth.authMap.size()>0){
				List<Map<String,String>> cacheList = ServiceAuth.authMap.get("cache");
				if(cacheList!=null && cacheList.size()>0){
					for(Map<String,String> cacheMap:cacheList){
						if(cacheMap!=null && cacheMap.size()>0){
							System.out.println("缓存数据："+cacheMap.toString());
							if ("0".equals(cacheMap.get("type"))
									&& cacheMap.get("serviceurl").equals(url)
									&& cacheMap.get("serviceobjectip").equals(
											objectIP)
									&& StringUtils.isNotBlank(cacheMap
											.get("invokeclass"))
									&& StringUtils.isNotBlank(cacheMap
											.get("alias"))
									&& StringUtils.isNotBlank(cacheMap
											.get("defaulttime"))) {
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
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	public static String accessAddress(String localname){
		String reslut = "";
		if(ServiceAuth.authMap!=null && ServiceAuth.authMap.size()>0){
			List<Map<String,String>> cacheList = ServiceAuth.authMap.get("cache");
			if(cacheList!=null && cacheList.size()>0){
				for(Map<String,String> cacheMap:cacheList){
					if(cacheMap!=null && cacheMap.size()>0){
						if("1".equals(cacheMap.get("type")) && cacheMap.get("serviceobjectname").equals(localname)){
							reslut = "http://"+cacheMap.get("serviceobjectip")+":"+cacheMap.get("serviceobjectport");
							break;
						}
					}
				}
			}
		}
		return reslut;
	}
}
