package cn.gwssi.test.kafka;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;

import cn.gwssi.blog.model.TPtSysLogBO;
import cn.gwssi.common.resource.ServiceConstants;


public class Too1 {
	private static Logger log = Logger.getLogger("kafka");
	public static Producer createProducer() { 
		Properties props = new Properties();
   	 props.put("bootstrap.servers", "10.1.2.122:9092");
   	 props.put("acks", "all");
   	 props.put("retries", 0);
   	 props.put("batch.size", 16384);
   	 props.put("linger.ms", 1);
   	 props.put("buffer.memory", 33554432);
   	 props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
   	 //props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");cn.gwssi.broker.serialization.ObjectSerializer
   	 props.put("value.serializer", "cn.gwssi.common.serialization.ObjectSerializer");
   	 Producer<String, Object> producer = new KafkaProducer<String, Object>(props);
       return producer; 
	}
	
	/*public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(2);//Executors.newFixedThreadPool(topics.size());
		executor.submit(new Tysss(ServiceConstants.CAHCHE_TOPIC,createProducer()));
		executor.submit(new Tysss(ServiceConstants.CAHCHE_TOPIC,createProducer()));
	}*/
	
}

class Tysss implements Runnable{
	private String topic;
	private Producer<String, Object> producer;


	public Tysss(String topic, Producer<String, Object> producer) {
		super();
		this.topic = topic;
		this.producer = producer;
	}


	@Override
	public void run() {
		try {
			for(int i = 0; i < 5; i++){
				TPtSysLogBO t =new TPtSysLogBO();
				t.setContent("123");
				t.setLogid(i+"");
				producer.send(new ProducerRecord<String, Object>(topic,Integer.toString(i), t));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			producer.close();
		}
	}
	
}