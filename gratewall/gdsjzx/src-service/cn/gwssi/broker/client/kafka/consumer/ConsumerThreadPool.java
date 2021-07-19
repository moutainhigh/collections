package cn.gwssi.broker.client.kafka.consumer;

import java.util.List;
import java.util.concurrent.ExecutorService;

import cn.gwssi.broker.client.pool.ClientProducerSingleThreadPool;

/**
 * 消费线程池
 * @author xue
 * @version 1.0
 * @since 2016/5/10
 */
public class ConsumerThreadPool {

	public static void createConsumerTopic(List<String> topics) {
		if(topics!=null && topics.size()>0){
			ExecutorService executor = ClientProducerSingleThreadPool.getExecutorService();
			ConsumerThread task=new ConsumerThread(topics.toString());
			executor.execute(task);
	        /*for(String topic : topics){
	        	executor.submit(new ConsumerThread(topic));
	        }*/
		}
    }

}
