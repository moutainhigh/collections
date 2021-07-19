package cn.gwssi.broker.client.kafka.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import cn.gwssi.broker.client.auth.ClientAuth;
import cn.gwssi.broker.client.listener.ClientBrokerProperties;
import cn.gwssi.broker.client.listener.ClientExecuteListener;
import cn.gwssi.broker.client.pool.ClientProducerSingleThreadPool;
import cn.gwssi.common.resource.BeanXmlUtil;
import cn.gwssi.common.resource.ServiceConstants;

/**
 * 消费缓存权限
 * @author xue
 * @version 1.0
 * @since 2016/5/20
 */
public class ChcheTopicConsum {
	public static void dealData(ConsumerRecord<String, Object> record){
		try {
			if(ClientBrokerProperties.clientBrokerProp.get("localname").equals(record.key())){//是当前应用的数据才要
				List<String> result =(List<String>) record.value();
				List<Map<String, String>> cacheResult = new ArrayList<Map<String,String>>();
				if(result!=null && result.size()>0){
					for(String s:result){
						System.out.println("客户端获取到的推送过来的权限数据："+s);
						cacheResult.add(BeanXmlUtil.xml2Map(s));
					}
				}
				ClientAuth.authMap.remove("cache");
				ClientAuth.authMap.clear();
				ClientAuth.authMap.put("cache", cacheResult);
				BeanXmlUtil.StringTofile(BeanXmlUtil.list2xml(cacheResult), ChcheTopicConsum.class.getClassLoader().getResource(ServiceConstants.CLIENT_CAHCHE_FILE_NAME).getFile(), ServiceConstants.UTFCHARSET);
				//获取topic
				List<String> topics = obtainTopic((String)ClientBrokerProperties.clientBrokerProp.get("localname"),cacheResult);
				boolean flag = false;
				if(ClientExecuteListener.topics.size()==topics.size()){
					for(String topic : topics){//abc
						if(!ClientExecuteListener.topics.contains(topic)){//只要有不存在的
							flag = true;
							ClientExecuteListener.topics = topics;
							break;
						}
					}
				}else{
					ClientExecuteListener.topics = topics;
					flag = true;
				}
				if(flag){
					ClientExecuteListener.consumer.close();
					ClientExecuteListener.consumer = null;
					ExecutorService executor = ClientProducerSingleThreadPool.getExecutorService();
					executor.shutdownNow();
					
					if(ClientExecuteListener.consumer==null) {
						ClientExecuteListener.consumer = new KafkaConsumer<String, Object>(ClientBrokerProperties.clientConsumerProp);//每个top一个线程
						System.out.println("启动的topic:"+topics);
						if (topics==null||topics.size()<=0) return ;
						ClientExecuteListener.consumer.subscribe(topics);//将所有topic启动
						ConsumerThreadPool.createConsumerTopic(topics);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static List<String> obtainTopic(String localName,List<Map<String,String>> list) {
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
		return result;
	}
}