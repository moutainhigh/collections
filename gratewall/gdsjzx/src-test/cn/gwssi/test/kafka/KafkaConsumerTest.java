package cn.gwssi.test.kafka;

import java.util.Arrays;
import java.util.Properties;  

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import cn.gwssi.blog.model.TPtSysLogBO;
import cn.gwssi.common.model.TPtFwrzjbxxBO;
import cn.gwssi.common.model.TPtFwrzxxxxBO;
import cn.gwssi.common.resource.DataHandler;
import cn.gwssi.common.resource.ServiceConstants;

/** 
 * @author zm 
 * 
 */  
public class KafkaConsumerTest extends Thread{  
	//private static Logger log = Logger.getLogger("kafka");
    private String topic;  
      
    public KafkaConsumerTest(String topic){  
        super();  
        this.topic = topic;  
    }  
    public static void main(String[] args) {
    	new KafkaConsumerTest("").start();
	}
   
    @Override  
    public void run() {  
        try {
			Properties props = new Properties();
			props.put("bootstrap.servers", "10.1.2.122:9092");
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
			KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<String, byte[]>(props);
			consumer.subscribe(Arrays.asList("test"));
			//log.info(logEntity);
			
			while (true) {
			    ConsumerRecords<String, byte[]> records = consumer.poll(100);
			    for (ConsumerRecord<String, byte[]> record : records){
			    	/*System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
			    	System.out.println("我是消费者1");
			        System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
			        System.out.println("<<<<<");
			        System.out.println(record.partition()+"=====================");*/
			        System.out.println(record.topic()+"-----");
			        Object o =record.value();
			        System.out.println(o);
			        if(o instanceof TPtSysLogBO){
			        	System.out.println("1");
			        	TPtSysLogBO t = (TPtSysLogBO)o;
			        	System.out.println(t.getLogid());
			        	System.out.println(t.getContent());
			        }else if(o instanceof TPtFwrzjbxxBO){//11
			        	TPtFwrzjbxxBO t = (TPtFwrzjbxxBO)o;
			        	System.out.println("2");
			        	System.out.println(t.getCallerip());
			        } else if(o instanceof TPtFwrzxxxxBO){//333
			        	TPtFwrzxxxxBO t = (TPtFwrzxxxxBO)o;
			        	System.out.println("3");
			        	System.out.println(t.getDetail());
			        }else{
			        	System.out.println("4");
			        }
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
       /* ConsumerConnector consumer = createConsumer();  
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();  
        topicCountMap.put(topic, 1); // 一次从主题中获取一个数据  
         Map<String, List<KafkaStream<byte[], byte[]>>>  messageStreams = consumer.createMessageStreams(topicCountMap);  
         KafkaStream<byte[], byte[]> stream = messageStreams.get(topic).get(0);// 获取每次接收到的这个数据  
         ConsumerIterator<byte[], byte[]> iterator =  stream.iterator();  
         while(iterator.hasNext()){  
             String message = new String(iterator.next().message());  
             System.out.println("接收到: " + message);  
         }  */
    }  
       
}  

/*while(true) {
ConsumerRecords<String, Object> records = consumer.poll(Long.MAX_VALUE);
for (TopicPartition partition : records.partitions()) {
    List<ConsumerRecord<String, Object>> partitionRecords = records.records(partition);
    for (ConsumerRecord<String, Object> record : partitionRecords) {
    	System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
        System.out.println("<<<<<");
        System.out.println(record.partition()+"=====================");
        System.out.println(record.topic()+"-----");
        ReponseContext r = (ReponseContext) record.value();
        System.out.println(r.getCode());
        System.out.println(r.getDesc());
        System.out.println(r.getErrorCode());
        System.out.println(r.getErrorMsg());
		List<Map<String,String>> list = r.getResponseResult();
		for(Map<String,String> map : list){
			for(Map.Entry<String,String> m:map.entrySet()){
				System.out.println("key:"+m.getKey()+"====value:"+m.getValue());
			}
		}
		System.out.println("=====================");
    }
    long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
    System.out.println(lastOffset+"nihao");
    consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastOffset + 1)));
}
}*/
 