package cn.gwssi.broker.client.listener;

import java.io.IOException;
import java.util.Properties;

import cn.gwssi.common.resource.KeysUtil;

/**
 * 配置参数初始化
 * @author xue
 * @version 1.0
 * @since 2016/4/20
 */
public class ClientBrokerProperties {
	public static Properties clientBrokerProp = new Properties();
	public static Properties clientConsumerProp = new Properties();
	public static Properties clientProducerProp = new Properties();
	static {
        try {
        	clientBrokerProp.load(ClientBrokerProperties.class.getClassLoader().getResourceAsStream("client-broker.properties"));
        	clientConsumerProp.load(ClientBrokerProperties.class.getClassLoader().getResourceAsStream("client-kafka-consumer.properties"));
        	clientProducerProp.load(ClientBrokerProperties.class.getClassLoader().getResourceAsStream("client-kafka-producer.properties"));
        	clientConsumerProp.setProperty("group.id", KeysUtil.md5Encrypt(clientBrokerProp.getProperty("localname")));//group.id=kafkarun
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
