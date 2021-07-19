package cn.gwssi.broker.client.kafka.consumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import cn.gwssi.broker.client.callback.CallBack;
import cn.gwssi.broker.client.callback.CallBackFactory;
import cn.gwssi.broker.client.listener.ClientBrokerProperties;
import cn.gwssi.broker.client.pool.ClientAsynDataThreadSizePool;
import cn.gwssi.common.model.AsynReponseContext;
import cn.gwssi.common.model.DataCarrier;

/**
 * 消费数据
 * @author xue
 * @version 1.0
 * @since 2016/5/20
 */
public class DataHandTopicConsum {
	public static void createConsumer(KafkaConsumer<String, Object> consumer) {
		while (true) {
			ConsumerRecords<String, Object> records = consumer.poll(100);
			for (ConsumerRecord<String, Object> record : records) {
				//record.key(),用来判断谁消费（原则是谁调用，谁消费）
				if(ClientBrokerProperties.clientBrokerProp.get("localname").equals(record.key())){//是当前应用的数据才要
					DataCarrier dataCarrier=(DataCarrier)record.value();
					AsynReponseContext reponseContext=new AsynReponseContext();
					List result = new ArrayList();
					result.add(dataCarrier.getResponseResults());
					reponseContext.setResponseResult(result);
					if(dataCarrier.getFlag().equals("true")){
						reponseContext.setCode(dataCarrier.getCode());
						reponseContext.setDesc(dataCarrier.getDesc());
						reponseContext.setErrorCode(dataCarrier.getErrorCode());
						reponseContext.setErrorMsg(dataCarrier.getErrorMsg());
						CallBack callback = CallBackFactory.getServiceInstance(dataCarrier.getAsyClassPath());//(CallBack) Class.forName().newInstance();
						callback.execute(reponseContext);
					}
				}
				ClientAsynDataThreadSizePool.getExecutorService(Integer.parseInt((String)ClientBrokerProperties.clientBrokerProp.get("clientAsynDataThreadSize")));
			}
		}
	}

	//分包缓存(SingelThread）
	private static HashMap<String, List> packetCache = new HashMap<String, List>();
	
	public static void dealData(ConsumerRecord<String, Object> record) {
				//record.key(),用来判断谁消费（原则是谁调用，谁消费）
		//if(ClientBrokerProperties.clientBrokerProp.get("localname").equals(record.key())){//是当前应用的数据才要
			DataCarrier dataCarrier=(DataCarrier)record.value();
			List cache = packetCache.get(record.key());
			cache = (cache == null ? new ArrayList() : cache);
			cache.add(dataCarrier.getResponseResults());
			if("true".equals(dataCarrier.getFlag())){//KAFKA消费顺序与生产顺序相同的情况下，不相同 @TODO
				AsynReponseContext reponseContext=new AsynReponseContext();
				reponseContext.setCode(dataCarrier.getCode());
				reponseContext.setDesc(dataCarrier.getDesc());
				reponseContext.setErrorCode(dataCarrier.getErrorCode());
				reponseContext.setErrorMsg(dataCarrier.getErrorMsg());
				reponseContext.setResponseResult(cache);
				CallBack callback = CallBackFactory.getServiceInstance(dataCarrier.getAsyClassPath());//(CallBack) Class.forName().newInstance();
				callback.execute(reponseContext);
				//执行完移除cache
				packetCache.remove(record.key());
			}else{
				packetCache.put(record.key(), cache);
			}
			
//			AsynReponseContext reponseContext=new AsynReponseContext();
//			List result = new ArrayList();
//			result.add(dataCarrier.getResponseResults());
//			reponseContext.setResponseResult(result);
//			if(dataCarrier.getFlag().equals("true")){
//				reponseContext.setCode(dataCarrier.getCode());
//				reponseContext.setDesc(dataCarrier.getDesc());
//				reponseContext.setErrorCode(dataCarrier.getErrorCode());
//				reponseContext.setErrorMsg(dataCarrier.getErrorMsg());
//				CallBack callback = CallBackFactory.getServiceInstance(dataCarrier.getAsyClassPath());//(CallBack) Class.forName().newInstance();
//				callback.execute(reponseContext);
//			}
		//}
	}
}
/*while(true) {
ConsumerRecords<String, Object> records = consumer.poll(Long.MAX_VALUE);
for (TopicPartition partition : records.partitions()) {
    List<ConsumerRecord<String, Object>> partitionRecords = records.records(partition);
    for (ConsumerRecord<String, Object> record : partitionRecords) {
        System.out.println(record.offset() + ": " + record.value());
    }
    //System.out.printf("offset = %d, key = %s, value = %s",record.offset(), record.key(), record.value());
    long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
    consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastOffset + 1)));
}
} */