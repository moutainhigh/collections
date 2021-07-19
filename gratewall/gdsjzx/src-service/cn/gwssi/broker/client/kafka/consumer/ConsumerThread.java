package cn.gwssi.broker.client.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import cn.gwssi.broker.client.listener.ClientExecuteListener;
import cn.gwssi.common.resource.ServiceConstants;

/**
 * 消费线程
 * @author xue
 * @version 1.0
 * @since 2016/5/10
 */
public class ConsumerThread implements Runnable{
	private String topic;

	public ConsumerThread(String topic) {
		this.topic=topic;
	}

	@Override
	public void run() {
		while (true) {
			ConsumerRecords<String, Object> records = ClientExecuteListener.consumer.poll(100);
			for (ConsumerRecord<String, Object> record : records) {
				if(record.topic().equals(ServiceConstants.CLIENT_CAHCHE_TOPIC)){// 缓存（都有）
					ChcheTopicConsum.dealData(record);
				}else{// 数据消费（基本都有）
					DataHandTopicConsum.dealData(record);
				}
			}
		}
			/*ClientExecuteListener.consumer.subscribe(Arrays.asList(this.topic));
			switch (this.topic) {
			case ServiceConstants.DATA_HAND_TOPIC:// 数据消费（基本都有）
				DataHandTopicConsum.createConsumer(ClientExecuteListener.consumer);
				break;
			case ServiceConstants.CAHCHE_TOPIC:
				ChcheTopicConsum.createConsumer(ClientExecuteListener.consumer);
				break;
			default:
				break;
			}*/
	}

}
