package cn.gwssi.broker.client;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.UUID;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import cn.gwssi.broker.client.auth.ClientAuth;
import cn.gwssi.broker.client.listener.ClientBrokerProperties;
import cn.gwssi.broker.client.listener.ClientExecuteListener;
import cn.gwssi.broker.client.task.SyncClientWithTimeout;
import cn.gwssi.common.exception.BrokerException;
import cn.gwssi.common.model.ReponseContextBase;
import cn.gwssi.common.model.RequestContext;
import cn.gwssi.common.model.SynReponseContext;
import cn.gwssi.common.model.TPtFwrzjbxxBO;
import cn.gwssi.common.model.TPtFwrzxxxxBO;
import cn.gwssi.common.resource.DataHandler;
import cn.gwssi.common.resource.ServiceConstants;

/**
 * Broker本地方法实现类
 * @author xue
 * @version 1.0
 * @since 2016/4/20
 */
public class BrokerImpl extends Broker implements BrokerInterface {
	private static Logger log = Logger.getLogger("kafka");
	
	/**
	 * 方法 发送webservice请求，根据参数标识同步请求数据
	 * @param requestParam
	 * @param time
	 * @return
	 * @throws BrokerException 
	 */
	public SynReponseContext synExecute(String serviceName,String params) throws BrokerException{
		Date date= new Date();
		SynReponseContext synReponseContext = new SynReponseContext();
		TPtFwrzjbxxBO t = new TPtFwrzjbxxBO();
		try {
			String uid = UUID.randomUUID().toString();
			String objectName = (String)ClientBrokerProperties.clientBrokerProp.get("localname");
			t.setFwrzjbid(uid);
			t.setCallername(objectName);
			t.setFwmc(serviceName);
			t.setCallertime(ServiceConstants.sdf.format(date));
			t.setCallerparameter("服务名："+serviceName+"调用参数："+params);
			t.setCallerip(ClientExecuteListener.ip);
			//log.info(t);
						
			TPtFwrzxxxxBO t1 = new TPtFwrzxxxxBO();
			t1.setFwrzjbid(uid);
			Date startTime = new Date();
			
			t1.setStartTime(ServiceConstants.sdf.format(startTime));
			t1.setObj("客户端本地鉴权");
			t1.setFwrzxxid(UUID.randomUUID().toString());
			t1.setDetail("服务名："+serviceName+" IP："+ClientExecuteListener.ip);
			try {//调用权限判断
				boolean flag = ClientAuth.clientPermissionCheck(serviceName,ClientExecuteListener.ip);
				t.setCalleer(ClientAuth.getAuth().get("serviceobjectnamef"));
				if(!flag){
					synReponseContext.setCode("1");
					synReponseContext.setDesc("权限不足，请查看是否注册了该服务！");
					t.setExecuteresult(synReponseContext.getDesc());
					t1.setExecutecontent("鉴权失败");
					t1.setCode("1");
					throw new BrokerException(synReponseContext.getDesc());
					//return synReponseContext;
				}
				t1.setCode("0");
				t1.setExecutecontent("鉴权成功");
			} catch (Exception e) {
				t1.setCode("1");
				t1.setExecutecontent("鉴权失败");
				e.printStackTrace();
				throw new BrokerException(e.getMessage());
			}finally{
				Date endTime =new Date(); 
				long time = endTime.getTime()-startTime.getTime();
				t1.setEndTime(ServiceConstants.sdf.format(endTime));
				t1.setTime(String.valueOf(time));
				log.info(t1);
			}

			t1 = new TPtFwrzxxxxBO();
			t1.setFwrzjbid(uid);
			startTime = new Date();
			
			t1.setStartTime(ServiceConstants.sdf.format(startTime));
			t1.setObj("客户端发送服务请求");
			t1.setFwrzxxid(UUID.randomUUID().toString());
			t1.setDetail(params);
			try {
				RequestContext requestContext = new RequestContext();
				requestContext.setServiceId(uid);
				requestContext.setServiceName(serviceName);
				requestContext.setObjectName(objectName);
				requestContext.setServiceUrl(ClientAuth.getAuth().get("serviceurl"));
				requestContext.setExcuteServicePath(ClientAuth.getAuth().get("invokeclass"));
				//requestContext.setTimeOut("");
				requestContext.setParams(params);
				t1.setDetail(requestContext.toString());
				SyncClientWithTimeout.callSyncWithTimout(requestContext);
				synReponseContext = SyncClientWithTimeout.synReponseContext;
				t1.setCode("0");
				t1.setExecutecontent("成功");
			} catch (Exception e) {
				t1.setCode("1");
				t1.setExecutecontent("失败");
				e.printStackTrace();
				throw new BrokerException(e.getMessage());
			}finally{
				Date endTime =new Date(); 
				long time = endTime.getTime()-startTime.getTime();
				t1.setEndTime(ServiceConstants.sdf.format(endTime));
				t1.setTime(String.valueOf(time));
				log.info(t1);
			}
			t.setExecuteresult("成功");
		} catch (Exception e) {
			t.setExecuteresult("失败");
			e.printStackTrace();
			throw new BrokerException(e.getMessage());
		}finally{
			Date callerenttime =new Date(); 
			long executetime = callerenttime.getTime()-date.getTime();
			t.setCallerenttime(ServiceConstants.sdf.format(callerenttime));
			t.setExecutetime(String.valueOf(executetime));
			log.info(t);
		}
		return synReponseContext;
	}
	
	public SynReponseContext synExecute(String timeOut,String serviceName,String params) throws BrokerException  {

		Date date= new Date();
		SynReponseContext synReponseContext = new SynReponseContext();
		TPtFwrzjbxxBO t = new TPtFwrzjbxxBO();
		try {
			String uid = UUID.randomUUID().toString();
			String objectName = (String)ClientBrokerProperties.clientBrokerProp.get("localname");
			t.setFwrzjbid(uid);
			t.setFwmc(serviceName);
			t.setCallername(objectName);
			t.setCallertime(ServiceConstants.sdf.format(date));
			t.setCallerparameter("服务名："+serviceName+"调用参数："+params);
			t.setCallerip(ClientExecuteListener.ip);
			//log.info(t);
						
			TPtFwrzxxxxBO t1 = new TPtFwrzxxxxBO();
			t1.setFwrzjbid(uid);
			Date startTime = new Date();
			
			t1.setStartTime(ServiceConstants.sdf.format(startTime));
			t1.setObj("客户端本地鉴权");
			t1.setFwrzxxid(UUID.randomUUID().toString());
			t1.setDetail("服务名："+serviceName+" IP："+ClientExecuteListener.ip);
			try {//调用权限判断
				boolean flag = ClientAuth.clientPermissionCheck(serviceName,ClientExecuteListener.ip);
				t.setCalleer(ClientAuth.getAuth().get("serviceobjectnamef"));
				if(!flag){
					synReponseContext.setCode("1");
					synReponseContext.setDesc("权限不足，请查看是否注册了该服务！");
					t.setExecuteresult(synReponseContext.getDesc());
					t1.setExecutecontent("鉴权失败");
					t1.setCode("1");
					throw new BrokerException(synReponseContext.getDesc());
					//return synReponseContext;
				}
				t1.setCode("0");
				t1.setExecutecontent("鉴权成功");
			} catch (Exception e) {
				t1.setCode("1");
				t1.setExecutecontent("鉴权失败");
				e.printStackTrace();
				throw new BrokerException(e.getMessage());
			}finally{
				Date endTime =new Date(); 
				long time = endTime.getTime()-startTime.getTime();
				t1.setEndTime(ServiceConstants.sdf.format(endTime));
				t1.setTime(String.valueOf(time));
				log.info(t1);
			}

			t1 = new TPtFwrzxxxxBO();
			t1.setFwrzjbid(uid);
			startTime = new Date();
			
			t1.setStartTime(ServiceConstants.sdf.format(startTime));
			t1.setObj("客户端发送服务请求");
			t1.setFwrzxxid(UUID.randomUUID().toString());
			t1.setDetail(params);
			try {
				RequestContext requestContext = new RequestContext();
				requestContext.setServiceId(uid);
				requestContext.setServiceName(serviceName);
				requestContext.setObjectName(objectName);
				requestContext.setServiceUrl(ClientAuth.getAuth().get("serviceurl"));
				requestContext.setExcuteServicePath(ClientAuth.getAuth().get("invokeclass"));
				requestContext.setTimeOut(timeOut);
				requestContext.setParams(params);
				t1.setDetail(requestContext.toString());
				SyncClientWithTimeout.callSyncWithTimout(requestContext);
				synReponseContext = SyncClientWithTimeout.synReponseContext;
				t1.setCode("0");
				t1.setExecutecontent("成功");
			} catch (Exception e) {
				t1.setCode("1");
				t1.setExecutecontent("失败");
				e.printStackTrace();
				throw new BrokerException(e.getMessage());
			}finally{
				Date endTime =new Date(); 
				long time = endTime.getTime()-startTime.getTime();
				t1.setEndTime(ServiceConstants.sdf.format(endTime));
				t1.setTime(String.valueOf(time));
				log.info(t1);
			}
			t.setExecuteresult("成功");
		} catch (Exception e) {
			t.setExecuteresult("失败");
			e.printStackTrace();
			throw new BrokerException(e.getMessage());
		}finally{
			Date callerenttime =new Date(); 
			long executetime = callerenttime.getTime()-date.getTime();
			t.setCallerenttime(ServiceConstants.sdf.format(callerenttime));
			t.setExecutetime(String.valueOf(executetime));
			log.info(t);
		}
		return synReponseContext;
	}

	/**
	 * 方法 发送webservice请求，根据参数标识异步请求数据
	 * @param requestParam
	 * @param time
	 * @return
	 * @throws BrokerException 
	 */
	public ReponseContextBase asynExecute(String serviceName,String params,String asyClassPath) throws BrokerException  {
		ReponseContextBase reponseContextBase = new ReponseContextBase();
		String objectName = (String)ClientBrokerProperties.clientBrokerProp.get("localname");
		Date date = new Date();
		TPtFwrzjbxxBO t = new TPtFwrzjbxxBO();
		String uid = UUID.randomUUID().toString();
		t.setFwrzjbid(uid);
		t.setFwmc(serviceName);
		t.setCallername(objectName);
		t.setCallertime(ServiceConstants.sdf.format(date));
		t.setExecutetime(ServiceConstants.sdf.format(date));
		t.setCallerparameter("客户端异步："+"服务名："+serviceName+"调用参数："+params);
		t.setExecuteway("异步");
		t.setExecutetype("异步");
		t.setCallerip(ClientExecuteListener.ip);
		
		TPtFwrzxxxxBO t1 = new TPtFwrzxxxxBO();
		t1.setFwrzxxid(UUID.randomUUID().toString());
		t1.setDetail("客户端请求时-鉴权开始");
		t1.setFwrzjbid(uid);
		t1.setExecutecontent(params);
		t1.setTime(ServiceConstants.sdf.format(date));
		log.info(t1);
		try {
			boolean flag = ClientAuth.clientPermissionCheck(serviceName,ClientExecuteListener.ip);
			t.setCalleer(ClientAuth.getAuth().get("serviceobjectnamef"));
			if(!flag){
				reponseContextBase.setCode("1");
				reponseContextBase.setDesc("权限不足，请查看是否注册了该服务！");
				t.setExecuteresult(reponseContextBase.getDesc());
				return reponseContextBase;
			}
			
			RequestContext requestContext = new RequestContext();
			requestContext.setServiceId(uid);
			requestContext.setServiceName(serviceName);
			requestContext.setObjectName(objectName);
			requestContext.setServiceUrl(ClientAuth.getAuth().get("serviceurl"));
			requestContext.setExcuteServicePath(ClientAuth.getAuth().get("invokeclass"));
			requestContext.setAsyClassPath(asyClassPath);
			requestContext.setParams(params);
			
			String s=DataHandler.Object2xml(requestContext);
			//String xml=WebClient.create(ServiceConstants.SERVICE_URL).path("/SOAService/getAsynData").post(s,String.class);
			String xml=WebClient.create(requestContext.getServiceUrl()).post(s,String.class);
			reponseContextBase = (ReponseContextBase) DataHandler.xml2Object(xml, reponseContextBase);
			t.setExecuteresult(reponseContextBase.getDesc());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			t.setExecuteresult("xml字符串转化为实体bean异常-NoSuchMethodException:"+e.getMessage());
			throw new BrokerException("xml字符串转化为实体bean异常-NoSuchMethodException:"+e.getMessage());
		} catch (SecurityException e) {
			e.printStackTrace();
			t.setExecuteresult("xml字符串转化为实体bean异常-SecurityException:"+e.getMessage());
			throw new BrokerException("xml字符串转化为实体bean异常-SecurityException:"+e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			t.setExecuteresult("xml字符串转化为实体bean异常-IllegalAccessException:"+e.getMessage());
			throw new BrokerException("xml字符串转化为实体bean异常-IllegalAccessException:"+e.getMessage());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			t.setExecuteresult("xml字符串转化为实体bean异常-IllegalArgumentException:"+e.getMessage());
			throw new BrokerException("xml字符串转化为实体bean异常-IllegalArgumentException:"+e.getMessage());
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			t.setExecuteresult("xml字符串转化为实体bean异常-InvocationTargetException:"+e.getMessage());
			throw new BrokerException("xml字符串转化为实体bean异常-InvocationTargetException:"+e.getMessage());
		} catch (DocumentException e) {
			e.printStackTrace();
			t.setExecuteresult("xml字符串转化为实体bean异常-DocumentException:"+e.getMessage());
			throw new BrokerException("xml字符串转化为实体bean异常-DocumentException:"+e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			t.setExecuteresult("服务内部组件连接异常:"+e.getMessage());
			throw new BrokerException("服务内部组件连接异常:"+e.getMessage());
		}finally{
			log.info(t);
		}
		return reponseContextBase;
	}
	
}
