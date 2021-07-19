package cn.gwssi.test.kafka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.Logger;

import cn.gwssi.blog.model.TPtSysLogBO;
import cn.gwssi.common.resource.DataHandler;
import cn.gwssi.common.resource.ServiceConstants;

public class Foo {
	private static Logger log = Logger.getLogger("kafka");
public static void main(String[] args) {
	/*FutureTask.test();
	int c = FutureTask.i;
	System.out.println(c);
	StringBuffer sbf = new StringBuffer();
	sbf.append("<").append("ddfdf").append(">").append("vvv").append("</").append("ddfdf").append(">");
	System.out.println(sbf.toString());*/
	/*String timeOut="10000";
	long t = Long.parseLong(timeOut);
	System.out.println(t);
	System.out.println("123");
	ExecutorService executor = Executors.newFixedThreadPool(1);
	executor.execute(new Tests());
	System.out.println("123");*/
	
	/*Map<String,Object> m = test();
	System.out.println(m.get("1"));
	System.out.println(m.get("list"));*/
	/*int i =11;
	tests(i);*/
	
	TPtSysLogBO logEntity = new TPtSysLogBO();
	logEntity.setLogid("uuid");
	log.info(logEntity);
	
	ce();
}

private static void ce() {
	Properties props = new Properties();
	props.put("bootstrap.servers", "10.1.2.122:9092");
	String str = "byt";
	System.out.println(str);
	props.put("group.id", str);
	//props.put("auto.offset.reset", "earliest"); //latest
	//props.put("client.id", "10");
	props.put("enable.auto.commit", "true");
	//props.put("enable.auto.commit", "false");
	props.put("auto.commit.interval.ms", "1000");
	props.put("session.timeout.ms", "30000");
	props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	//props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");cn.gwssi.common.serialization.ObjectDeserializer
	props.put("value.deserializer", "cn.gwssi.common.serialization.ObjectDeserializer");//org.apache.kafka.common.serialization.ByteArrayDeserializer
	
	//org.apache.kafka.common.serialization.ByteArraySerializer
	KafkaConsumer<String, Object> consumer = new KafkaConsumer<String, Object>(props);
	consumer.subscribe(Arrays.asList("nb"));
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
	        //DataHandler.deserialize(record.value());
	        System.out.println(o);
	        if(o instanceof TPtSysLogBO){
	        	System.out.println("nihao");
	        	TPtSysLogBO t = (TPtSysLogBO)o;
	        	System.out.println("TPtSysLogBO");
	        	System.out.println(t.getLogid());
	        }else if(o instanceof byte[]){
	        	System.out.println("wohao");
	        }else{
	        	System.out.println("56");
	        }
	    }
	}	
}

private static void tests(int size) {
	long totalPage = size/10; //共N页  
    if (size %10 != 0) {    
        totalPage += 1;    
    }  
    System.out.println("【总页数】"+totalPage);
    int sum = 0;
    List result = new ArrayList();
    List list = new ArrayList();
	for(int i=0;i<size;i++){
		String res = i+"【数据】";
		result.add(res);
		list.add(res);
		if(list.size()>=10){//大一等一为10 时，totalPage=1，sum++=1，list清空重新计算
			sum++;
			System.out.println("【最后一页的大小1】"+list.size());
			list.clear();
			if(i==size-1){//如果刚好是10，就结束，如果大于10 ，下一条就执行（1-9）totalPage=2，sum=1
				System.out.println("结束3");
			}
		}else{//(1-9)时，总数是1，而 sum 是0 ，所以会执行 (totalPage==sum+1)
			if(totalPage==sum+1){
				if(i==size-1){//(1-9)时，总数是1，而 sum 是0 ，所以会执行 (totalPage==sum+1) (i==size-1)表示是最后一条，执行
					sum++;
					System.out.println("【最后一页的大小3】"+list.size());
					System.out.println("结束1");
					list.clear();
				}
			}
		}
	}
	System.out.println("【总数】"+result.size());
	System.out.println("【总数】"+list.size());
}



private static Map<String,Object> test() {
	Map<String,Object> r = new HashMap<String,Object>();
	int size=12,num=10,count=0;
	
	long totalPage = size/num; //共N页  
    if (size % num != 0) {    
        totalPage += 1;    
    }
    int sum = 0;
	List result = new ArrayList();
	if(size>0){
		r.put("1", "有数库");
		for(int i=0;i<size;i++){
			result.add("shuju"+i);
			if(result.size()>=ServiceConstants.PACKET_NUMBER){
				sum++;
			}else{
				if(totalPage==sum){
					if(i==size-1){
						sum++;
					}
				}
			}
		}
	}else{
		r.put("1", "无数库");
		r.put("2", result);
	}
	return r;
}


}
class Tests implements Runnable{
public Tests(){
	
}
	@Override
	public void run() {
		System.out.println("57");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("578");
	}
	
}
