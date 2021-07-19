package cn.gwssi.broker.server.listener;

import java.io.IOException;
import java.util.Properties;

import cn.gwssi.common.resource.KeysUtil;

/**
 * 配置参数初始化
 * @author xue
 * @version 1.0
 * @since 2016/4/20
 */
public class ServiceBrokerProperties {
	public static Properties serviceBrokerProp = new Properties();
	public static Properties serviceConsumerProp = new Properties();
	public static Properties serviceProducerProp = new Properties();
	static {
        try {
        	serviceBrokerProp.load(ServiceBrokerProperties.class.getClassLoader().getResourceAsStream("server-broker.properties"));
        	serviceConsumerProp.load(ServiceBrokerProperties.class.getClassLoader().getResourceAsStream("server-kafka-consumer.properties"));
        	serviceProducerProp.load(ServiceBrokerProperties.class.getClassLoader().getResourceAsStream("server-kafka-producer.properties"));
        	serviceConsumerProp.setProperty("group.id", KeysUtil.md5Encrypt(serviceBrokerProp.getProperty("localname")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
