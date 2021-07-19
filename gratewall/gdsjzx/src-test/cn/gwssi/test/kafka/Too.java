package cn.gwssi.test.kafka;

import java.util.Arrays;
import java.util.Properties;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import cn.gwssi.blog.model.TPtSysLogBO;


public class Too {
	public static KafkaConsumer rt(){
		Properties props = new Properties();
		props.put("bootstrap.servers", "10.1.2.122:9092");
		String str = "po";
		System.out.println(str);
		props.put("group.id", str);
		//props.put("client.id", "10");
		props.put("enable.auto.commit", "true");
		//props.put("enable.auto.commit", "false");
		props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		//props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");cn.gwssi.common.serialization.ObjectDeserializer
		props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
		
		//org.apache.kafka.common.serialization.ByteArraySerializer
		KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<String, byte[]>(props);
		//consumer.subscribe(Arrays.asList("tt"));
		return consumer;
	}
	
	public static void main(String[] args) {
		/*Too.consumer = rt();
		List<String> topics = new ArrayList<String>();
		topics.add(ServiceConstants.LOG_HAND_TOPIC);
		topics.add(ServiceConstants.CAHCHE_TOPIC);
		//ConsumerThreadPool.createConsumerTopic(0, topics);
		Too.consumer.subscribe(topics);
		ExecutorService executor = Executors.newFixedThreadPool(2);//Executors.newFixedThreadPool(topics.size());
		executor.submit(new Tyss(ServiceConstants.LOG_HAND_TOPIC,rt()));
		executor.submit(new Tyss(ServiceConstants.CAHCHE_TOPIC,rt()));
		//new Tyss(ServiceConstants.LOG_HAND_TOPIC);
		//new Tyss(ServiceConstants.CAHCHE_TOPIC);
*/		
		
		testCUS();
	}

	private static void testCUS() {
		TPtSysLogBO logEntity = new TPtSysLogBO();
    	logEntity.setLogid("uuid");
    	//log.info(logEntity);
		Properties props = new Properties();
		props.put("bootstrap.servers", "10.1.6.124:9092");
		String str = "bv";
		System.out.println(str);
		props.put("group.id", str);
		props.put("auto.offset.reset", "earliest"); //latest
		//props.put("client.id", "10");
		props.put("enable.auto.commit", "true");
		//props.put("enable.auto.commit", "false");
		props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		//props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");cn.gwssi.common.serialization.ObjectDeserializer
		props.put("value.deserializer", "cn.gwssi.common.serialization.ObjectDeserializer");
		
		//org.apache.kafka.common.serialization.ByteArraySerializer
		KafkaConsumer<String, Object> consumer = new KafkaConsumer<String, Object>(props);
		consumer.subscribe(Arrays.asList("logHandTopic"));
		//log.info(logEntity);
		
		while (true) {
		    ConsumerRecords<String, Object> records = consumer.poll(100);
		    for (ConsumerRecord<String, Object> record : records){
		    	/*System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
		    	System.out.println("我是消费者1");
		        System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
		        System.out.println("<<<<<");
		        System.out.println(record.partition()+"=====================");*/
		        System.out.println(record.topic()+"-----");
		       
		        Object o =record.value();
		        System.out.println(o);
		        if(o instanceof TPtSysLogBO){
		        	System.out.println("nihao");
		        	TPtSysLogBO t = (TPtSysLogBO)o;
		        	System.out.println("TPtSysLogBO");
		        	System.out.println(t.getLogid());
		        }else{
		        	System.out.println("wohao");
		        }
		    }
		}
	}
}

class Tyss implements Runnable{
	private String topic;
	private KafkaConsumer<String, byte[]> consumer;

	public Tyss(String topic,KafkaConsumer<String, byte[]> consumer) {
		this.topic=topic;
		this.consumer=consumer;
	}
	@Override
	public void run() {
		int i =0;
		while(true){
			i++;
			System.out.println("name"+i+":"+topic);
			ConsumerRecords<String, byte[]> records = consumer.poll(100);
			for (ConsumerRecord<String, byte[]> record : records) {
				System.out.println("name:"+topic);
				System.out.printf("offset = %d, key = %s, value = %s",record.offset(), record.key(), record.value());
			}
			if(i>20){
				break;
			}
		}
	}
	
}