package cn.gwssi.broker.client.listener;

import java.util.*;
import java.util.concurrent.ExecutorService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.Logger;

import cn.gwssi.broker.client.auth.ClientAuth;
import cn.gwssi.broker.client.kafka.consumer.ConsumerThreadPool;
import cn.gwssi.broker.client.pool.ClientAsynDataThreadSizePool;
import cn.gwssi.broker.client.pool.ClientProducerSingleThreadPool;
import cn.gwssi.broker.client.pool.ClientTimeOutThreadSizePool;
import cn.gwssi.broker.client.task.SyncClientWithTimeout;
import cn.gwssi.common.exception.BrokerException;
import cn.gwssi.common.model.RequestContext;
import cn.gwssi.common.model.TPtFwrzjbxxBO;
import cn.gwssi.common.model.TPtFwrzxxxxBO;
import cn.gwssi.common.resource.BeanXmlUtil;
import cn.gwssi.common.resource.GetIPAddressUtil;
import cn.gwssi.common.resource.ServiceConstants;

/**
 * 应用启动监听
 * @author xue
 * @version 1.0
 * @since 2016/5/1
 */
public class ClientExecuteListener implements ServletContextListener {
	public static KafkaConsumer<String, Object> consumer =null;
	private static String clientCacheFileName = null;
	public static String ip = null;
	public static List<String> topics;
	private static Logger logi = Logger.getLogger(ClientExecuteListener.class);
	private static Logger log = Logger.getLogger("kafka");
	
	static{
		clientCacheFileName = ClientExecuteListener.class.getClassLoader().getResource(ServiceConstants.CLIENT_CAHCHE_FILE_NAME).getFile();
		ip= GetIPAddressUtil.getLocalIPForJava();
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		ExecutorService executor = ClientTimeOutThreadSizePool.getExecutorService(1);
		if(executor!=null){
			executor.shutdownNow();
			executor=null;
		}
		executor = ClientAsynDataThreadSizePool.getExecutorService(1);
		if(executor!=null){
			executor.shutdownNow();
			executor=null;
		}
		executor = ClientProducerSingleThreadPool.getExecutorService();
		if(executor!=null){
			executor.shutdownNow();
			executor=null;
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			//0、先从本地读取权限文件放入缓存
			List<Map<String,String>> cacheResult = (List<Map<String, String>>) BeanXmlUtil.xml2List(BeanXmlUtil.fileToString(clientCacheFileName, ServiceConstants.UTFCHARSET));
			ClientAuth.authMap.remove("cache");
			ClientAuth.authMap.clear();
			ClientAuth.authMap.put("cache", cacheResult);
			//1.获取当前应用服务权限，验证ip，注册对象名称 //服务器启动时主动获取一次所有服务权限；并对权限进行缓存
			String localName=(String) ClientBrokerProperties.clientBrokerProp.get("localname");//配置的
			//String ip=this.getLocalIPForJava();
			//System.out.println("localName:"+localName);//localName:GD-数据中心-01客户端IP：10.1.7.5
			RequestContext requestContext = new RequestContext();
			requestContext.setObjectName(localName);//注册对象
			requestContext.setParams(localName);
			requestContext.setServiceUrl(ServiceConstants.SERVICE_URL);
			//初始化线程池
			
			String clientTimeOutThread = (String)ClientBrokerProperties.clientBrokerProp.get("clientTimeOutThreadSize");
			String clientAsynDataThread = (String)ClientBrokerProperties.clientBrokerProp.get("clientAsynDataThreadSize");
			
			int clientTimeOutThreadSize=1,clientAsynDataThreadSize=1;
			if(StringUtils.isNotBlank(clientTimeOutThread)){
				clientTimeOutThreadSize = Integer.parseInt(clientTimeOutThread);
			}
			if(StringUtils.isNotBlank(clientAsynDataThread)){
				clientAsynDataThreadSize = Integer.parseInt(clientAsynDataThread);
			}
			
			ClientTimeOutThreadSizePool.getExecutorService(clientTimeOutThreadSize);
			ClientAsynDataThreadSizePool.getExecutorService(clientAsynDataThreadSize);
			
			if(!ClientAuth.checkAuth(requestContext,clientCacheFileName)) return;
			//2.启动kafka消费服务()
			if(this.consumer==null) {
				consumer = new KafkaConsumer<String, Object>(ClientBrokerProperties.clientConsumerProp);//每个top一个线程
				//从缓存中获取topic
				this.topics = obtainTopic(localName);
				System.out.println("启动的topic:"+topics);
				if (topics==null||topics.size()<=0) return ;
				this.consumer.subscribe(topics);//将所有topic启动
				ConsumerThreadPool.createConsumerTopic(topics);
			}
			//4、通知数据中心服务对象准备就绪，服务准备就绪
			noticeDataCerter(requestContext);
		}catch (BrokerException e) {
			logi.debug("【"+e.getCode()+"】【"+e.getMsg()+"】"+e.getMsgDes());
			e.printStackTrace();
		}
	}

	private List<String> obtainTopic(String localName) {
		List<Map<String,String>> list = ClientAuth.authMap.get("cache");
		List<String> result = new ArrayList<String>();
		if(list!=null && list.size()>0){
			for(Map<String,String> authMap :  list){
				if(authMap!=null && authMap.size()>0){
					for(Map.Entry<String, String> str : authMap.entrySet()){
						if(str.getKey().equals("themename")){
							String value= str.getValue();
							if(StringUtils.isNotBlank(value)){
								if(!result.contains(value)){
									result.add(value);
								}
							}
						}
					}
				}
			}
		}
		//加入默认的topic/缓存
		//result.add();
		result.add(ServiceConstants.CLIENT_CAHCHE_TOPIC);
		logi.info("启动的主题有："+result.toString());
		return result;
	}
	
	private void noticeDataCerter(RequestContext requestContext) throws BrokerException {
		String uid = UUID.randomUUID().toString();
		Date date= new Date();
		requestContext.setServiceId(uid);
		requestContext.setServiceName(ServiceConstants.DATA_CENTER_NOTICE);
		requestContext.setExcuteServicePath(ServiceConstants.DATA_CENTER_NOTICE_CLASS);
		requestContext.setServiceUrl(ServiceConstants.SERVICE_URL+"/SOAService/synUnifiedService/"+ServiceConstants.DATA_CENTER_NOTICE_CLASS);
		requestContext.setTimeOut("10000");
		//requestContext.setAsyClassPath("");
		
		TPtFwrzjbxxBO t = new TPtFwrzjbxxBO();
		t.setFwmc("客户端启动监听通知服务");
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
		t1.setObj("客户端启动监听通知服务");
		t1.setFwrzxxid(UUID.randomUUID().toString());
		t1.setDetail("服务名："+ServiceConstants.DATA_CENTER_NOTICE+" IP："+ClientExecuteListener.ip);
		try {
			t1.setDetail(requestContext.toString());
			SyncClientWithTimeout.callSyncWithTimout(requestContext);
			t1.setCode("0");
			t1.setExecutecontent("成功");
			t.setExecuteresult("成功");
		} catch (BrokerException e) {
			t.setExecuteresult("失败");
			t1.setCode("1");
			t1.setExecutecontent("失败");
			t.setExecuteresult(e.getMsg()+e.getMsgDes());
			//String code, String msg,String msgDes
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
		/*ReponseContextBase reponseContextBase = new ReponseContextBase();
		String s=DataHandler.Object2xml(requestContext);
		String xml=WebClient.create(ServiceConstants.SERVICE_URL).path("/SOAService/getAsynData").post(s,String.class);
		reponseContextBase = (ReponseContextBase) DataHandler.xml2Object(xml, reponseContextBase);*/
	}

}
