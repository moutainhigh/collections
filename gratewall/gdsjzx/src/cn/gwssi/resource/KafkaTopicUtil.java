package cn.gwssi.resource;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import cn.gwssi.broker.server.listener.ServiceBrokerProperties;

public class KafkaTopicUtil {
	private volatile static KafkaConsumer<String, Object> consumer =null;
	
	public static KafkaConsumer getKafkaConsumer() {
		if (consumer==null) {
			synchronized (KafkaTopicUtil.class) {
				if (consumer==null) {
					consumer = new KafkaConsumer<String, Object>(ServiceBrokerProperties.serviceConsumerProp);
				}
			}
		}
		return consumer;
	}
	
	public static void getKafkaConnect(KafkaConsumer<String, Object> consumers,List<String> topics){
		if(consumers==null) {
			consumers = new KafkaConsumer<String, Object>(ServiceBrokerProperties.serviceConsumerProp);//每个top一个线程
		}
		consumers.subscribe(topics);
		ConsumerRecords<String, Object> records = consumers.poll(100);
		//consumer.close();
	}
	public static void main(String[] args) {
		List<String> s = new ArrayList<String>();
		s.add("test");
		KafkaTopicUtil.getKafkaConnect(KafkaTopicUtil.consumer, s);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		s.add("test1");
		KafkaTopicUtil.getKafkaConnect(KafkaTopicUtil.consumer, s);
	}
}
