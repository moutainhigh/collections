package cn.gwssi.broker.server.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.Logger;

import cn.gwssi.broker.server.listener.ServiceBrokerProperties;
import cn.gwssi.common.exception.BrokerException;
import cn.gwssi.common.log.LogOperation;
import cn.gwssi.common.log.LogOperationFactory;
import cn.gwssi.common.resource.ServiceConstants;

/**
 * 消费日志，写入数据库
 * @author xue
 * @version 1.0
 * @since 2016/5/10
 */
public class LogHandTopicConsum {
	private static  Logger log=Logger.getLogger(LogHandTopicConsum.class);
	public static void createConsumer(KafkaConsumer<String, Object> consumer){
		while (true) {
			System.out.println("你好测试写入kafka池子"+consumer);
			ConsumerRecords<String, Object> records = consumer.poll(100);
			for (ConsumerRecord<String, Object> record : records) {
				if(ServiceBrokerProperties.serviceBrokerProp.get("localname").equals(ServiceConstants.DATA_CENTER_NAME)){//是数据中心才读日志
					//System.out.printf("offset = %d, key = %s, value = %s",record.offset(), record.key(), record.value());
					try {
						Object obj=record.value();
						LogOperation logOperation = LogOperationFactory.getLogOperationInstance(ServiceConstants.SERVER_LOG_PATH);
						logOperation.execute(obj);
					} catch (BrokerException e) {
						log.info("日志出入数据库错误："+e.getMessage());
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static void dealData(ConsumerRecord<String, Object> record){
		if(ServiceBrokerProperties.serviceBrokerProp.get("localname").equals(ServiceConstants.DATA_CENTER_NAME)){//是数据中心才读日志
			try {
				Object obj=record.value();
				LogOperation logOperation = LogOperationFactory.getLogOperationInstance(ServiceConstants.SERVER_LOG_PATH);
				logOperation.execute(obj);
			} catch (BrokerException e) {
				log.info("日志出入数据库错误："+e.getMessage());
				e.printStackTrace();
			}
		}
	}
}