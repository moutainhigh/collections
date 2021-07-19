package cn.gwssi.broker.server.listener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.log4j.Logger;

import cn.gwssi.broker.server.auth.ServiceAuth;
import cn.gwssi.broker.server.auth.SyncServiceClientWithTimeout;
import cn.gwssi.broker.server.kafka.consumer.ConsumerThreadPool;
import cn.gwssi.broker.server.pool.ServiceThreadPool;
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
public class ServiceExecuteListener implements ServletContextListener {
	public static KafkaConsumer<String, Object> consumer =null;
	public static Producer<String, Object> producer =null;
	
	private static String serviceCacheFileName = null;
	public static String ip = null;
	private static Logger log = Logger.getLogger("kafka");
	private static Logger logi = Logger.getLogger(ServiceExecuteListener.class);
	
	static{
		//"E:\\data\\serviceCacheAuth.xml";
		serviceCacheFileName = ServiceExecuteListener.class.getClassLoader().getResource(ServiceConstants.SERVIE_CAHCHE_FILE_NAME).getFile();
		ip= GetIPAddressUtil.getLocalIPForJava();
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		ExecutorService executor = ServiceThreadPool.getExecutorService(1);
		if(executor!=null){
			executor.shutdownNow();
			executor=null;
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			List<Map<String,String>> cacheResult = (List<Map<String, String>>) BeanXmlUtil.xml2List(BeanXmlUtil.fileToString(serviceCacheFileName, ServiceConstants.UTFCHARSET));
			ServiceAuth.authMap.remove("cache");
			ServiceAuth.authMap.clear();
			ServiceAuth.authMap.put("cache", cacheResult);
			String localName=(String) ServiceBrokerProperties.serviceBrokerProp.get("localname");//配置的
			//System.out.println("localName:"+localName);//localName:GD-数据中心-01客户端IP：10.1.7.5
			RequestContext requestContext = new RequestContext();
			requestContext.setObjectName(localName);//注册对象
			requestContext.setServiceUrl(ServiceConstants.SERVICE_URL);
			requestContext.setParams(localName);
			//初始化线程池
			String serverTimeOutThread = (String) ServiceBrokerProperties.serviceBrokerProp.get("serverTimeOutThreadSize");
			int serverTimeOutThreadSize = 1;
			if(StringUtils.isNotBlank(serverTimeOutThread)){
				serverTimeOutThreadSize = Integer.parseInt(serverTimeOutThread);
			}
			ServiceThreadPool.getExecutorService(serverTimeOutThreadSize);
			
			if(producer==null) {
				producer = new KafkaProducer<String, Object>(ServiceBrokerProperties.serviceProducerProp);
			}
			//只需要启动一个缓存消费就可以了（kafka）、如果是数据中心就多加一个日志
			List<String> topics = new ArrayList<String>();
			//3、初始化cxf服务框架
			if(localName.equals(ServiceConstants.DATA_CENTER_NAME)){//如果是数据中心先启动cxf
				topics.add(ServiceConstants.LOG_HAND_TOPIC);
				if(!StartServer.start("",ip))return;
				if(!ServiceAuth.checkAuth(requestContext,serviceCacheFileName)) return;
			}else{
				if(!ServiceAuth.checkAuth(requestContext,serviceCacheFileName)) return;
				if(!StartServer.start(ServiceAuth.accessAddress(localName),ip))return;
			}
			//topics.add(ServiceConstants.CLIENT_CAHCHE_TOPIC);
			topics.add(ServiceConstants.SERVER_CAHCHE_TOPIC);
			System.out.println("启动的topic:"+topics);
			if(consumer==null) {
				consumer = new KafkaConsumer<String, Object>(ServiceBrokerProperties.serviceConsumerProp);//每个top一个线程
				if (topics==null||topics.size()<=0) return ;
				ServiceExecuteListener.consumer.subscribe(topics);//将所有topic启动
				ConsumerThreadPool.createConsumerTopic(topics);
			}
			//4、通知数据中心服务对象准备就绪，服务准备就绪
			noticeDataCerter(requestContext);
		}catch (BrokerException e) {
			logi.debug("【"+e.getCode()+"】【"+e.getMsg()+"】"+e.getMsgDes());
			e.printStackTrace();
		}
	}

	private void noticeDataCerter(RequestContext requestContext) throws BrokerException {
		Date date= new Date();
		String uid = UUID.randomUUID().toString();
		requestContext.setServiceId(uid);
		requestContext.setServiceName(ServiceConstants.SDATA_CENTER_NOTICE);
		requestContext.setExcuteServicePath(ServiceConstants.SDATA_CENTER_NOTICE_CLASS);
		requestContext.setServiceUrl(ServiceConstants.SERVICE_URL+"/SOAService/synUnifiedService/"+ServiceConstants.SDATA_CENTER_NOTICE_CLASS);
		requestContext.setTimeOut("100000");
		//requestContext.setAsyClassPath("");
		
		TPtFwrzjbxxBO t = new TPtFwrzjbxxBO();
		t.setFwrzjbid(uid);
		t.setFwmc("服务端启动通知服务");
		t.setCallertime(ServiceConstants.sdf.format(date));
		t.setCallername(requestContext.getObjectName());
		t.setCallerparameter(requestContext.getRequestParam() == null ? "{}" : requestContext.getRequestParam().toString());
		t.setCallerip(ServiceExecuteListener.ip);
		t.setCalleer(ServiceConstants.DATA_CENTER_NAME);
		
		TPtFwrzxxxxBO t1 = new TPtFwrzxxxxBO();
		t1.setFwrzjbid(uid);
		Date startTime = new Date();
		t1.setStartTime(ServiceConstants.sdf.format(startTime));
		t1.setObj("服务端启动监听通知服务");
		t1.setFwrzxxid(UUID.randomUUID().toString());
		t1.setDetail("服务名："+ServiceConstants.SDATA_CENTER_NOTICE+" IP："+ServiceExecuteListener.ip);
		try{
			t1.setDetail(requestContext.toString());
			SyncServiceClientWithTimeout.callSyncWithTimout(requestContext);
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
