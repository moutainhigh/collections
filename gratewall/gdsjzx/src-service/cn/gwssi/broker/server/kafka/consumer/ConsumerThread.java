package cn.gwssi.broker.server.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.log4j.Logger;

import cn.gwssi.broker.server.listener.ServiceExecuteListener;
import cn.gwssi.common.resource.ServiceConstants;

/**
 * 消费线程
 * @author xue
 * @version 1.0
 * @since 2016/5/10
 */
public class ConsumerThread implements Runnable{
	private static  Logger log=Logger.getLogger(ConsumerThread.class);
	private String topic;

	public ConsumerThread(String topic) {
		this.topic=topic;
	}

	@Override
	public void run() {
		try {
			while (true) {
				ConsumerRecords<String, Object> records = ServiceExecuteListener.consumer.poll(100);
				for (ConsumerRecord<String, Object> record : records) {
					switch (record.topic()) {// 日志消费只有数据中心才有
					case ServiceConstants.LOG_HAND_TOPIC:
						LogHandTopicConsum.dealData(record);
						break;
					case ServiceConstants.SERVER_CAHCHE_TOPIC:// 缓存（都有）
						ChcheTopicConsum.dealData(record);
						break;
					default:
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
