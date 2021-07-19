package cn.gwssi.broker.server.kafka.producer;

import java.util.List;
import java.util.concurrent.ExecutorService;

import cn.gwssi.broker.server.listener.ServiceBrokerProperties;
import cn.gwssi.broker.server.pool.ServerProducerSingleThreadPool;
import cn.gwssi.broker.server.pool.ServiceThreadPool;

/**
 * 生产线程池
 * @author xue
 * @version 1.0
 * @since 2016/5/10
 */
public class ProducThreadPool {

	private void createProducTopic(List<String> topics) {
		if(topics!=null && topics.size()>0){
			ExecutorService executor = ServiceThreadPool.getExecutorService(Integer.parseInt((String) ServiceBrokerProperties.serviceBrokerProp.get("serviceThreadNum")));
	        for(String topic : topics){
	        	executor.execute(new ProducThread(topic));
	        }
		}
    }
	
	public static void createFixedProducTopic(String key,String topic,Object obj) {
		ExecutorService executor = ServiceThreadPool.getExecutorService(Integer.parseInt((String) ServiceBrokerProperties.serviceBrokerProp.get("serviceThreadNum")));
		executor.execute(new ProducThread(key,topic,obj));
    }
	
	public static void createSingleProducTopic(String key,String topic,Object obj) {
		ExecutorService executor = ServerProducerSingleThreadPool.getExecutorService();
		executor.execute(new ProducThread(key,topic,obj));
    }
}
