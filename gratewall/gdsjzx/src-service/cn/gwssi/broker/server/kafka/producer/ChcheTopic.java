package cn.gwssi.broker.server.kafka.producer;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import cn.gwssi.common.exception.BrokerException;

/**
 * 生产日志，写入kafka
 * @author xue
 * @version 1.0
 * @since 2016/5/10
 */
public class ChcheTopic {
	
	public static void createProducer(Producer<String, Object> producer,String key,String topic,Object o) throws BrokerException{
		try {
			producer.send(new ProducerRecord<String, Object>(topic, key,o));//写数据
		} catch (Exception e) {
			throw new BrokerException(e.getMessage());
		}
	}
}
