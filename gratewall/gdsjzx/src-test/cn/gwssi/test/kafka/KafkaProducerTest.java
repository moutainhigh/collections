package cn.gwssi.test.kafka;


import java.util.Properties;  

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;

import cn.gwssi.blog.model.TPtSysLogBO;
import cn.gwssi.common.model.TPtFwrzjbxxBO;
import cn.gwssi.common.model.TPtFwrzxxxxBO;
import cn.gwssi.common.resource.DataHandler;
import cn.gwssi.common.resource.ServiceConstants;

  
  
public class KafkaProducerTest extends Thread{  
	private static Logger log = Logger.getLogger("kafka");
    private String topic;  
      
    public KafkaProducerTest(String topic){  
        super();  
        this.topic = topic;  
    }  

    public static void main(String[] args) { 
    	TPtSysLogBO logEntity = new TPtSysLogBO();
    	logEntity.setLogid("uuid");
    	log.info(logEntity);
    	TPtFwrzjbxxBO t =new TPtFwrzjbxxBO();
    	t.setCallerip("11");
    	log.info(t);
    	TPtFwrzxxxxBO y = new TPtFwrzxxxxBO();
    	y.setDetail("333");
    	log.info(y);
        //new KafkaProducerTest("test").start();// 使用kafka中创建好的主题 test   
    } 
    private Producer createProducer() { 
    	Properties props = new Properties();
    	 props.put("bootstrap.servers", "10.1.2.122:9092");
    	 props.put("acks", "all");
    	 props.put("retries", 0);
    	 props.put("batch.size", 16384);
    	 props.put("linger.ms", 1);
    	 props.put("buffer.memory", 33554432);
    	 props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    	 //props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");cn.gwssi.broker.serialization.ObjectSerializer
    	 props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
    	 Producer<String, Object> producer = new KafkaProducer<String, Object>(props);
        return producer; 
    } 
    @Override  
    public void run() {  
        Producer producer = null;
		try {
			producer = createProducer();  
			for(int i = 0; i < 5; i++){
				TPtSysLogBO t =new TPtSysLogBO();
				t.setContent("123");
				t.setLogid(i+"");
				//producer.send(new ProducerRecord<String, Object>(ServiceConstants.CAHCHE_TOPIC,0, Integer.toString(i), t));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
   	    producer.close();
    }  
       
}  