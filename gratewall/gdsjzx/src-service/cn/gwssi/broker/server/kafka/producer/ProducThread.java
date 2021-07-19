package cn.gwssi.broker.server.kafka.producer;

import cn.gwssi.broker.server.listener.ServiceExecuteListener;
import cn.gwssi.common.exception.BrokerException;
import cn.gwssi.common.resource.ServiceConstants;

/**
 * 生产线程
 * @author xue
 * @version 1.0
 * @since 2016/5/10
 */
public class ProducThread implements Runnable{
	private String topic;
	private Object obj;
	private String key;
	
	public ProducThread(String topic) {
		this.topic=topic;
	}
	
	public ProducThread(String topic,Object obj) {
		this.topic=topic;
		this.obj=obj;
	}
	
	public ProducThread(String key,String topic,Object obj) {
		this.topic=topic;
		this.obj=obj;
		this.key = key;
	}

	@Override
	public void run() {
		//Producer<String, Object> producer = null;
		try {
			//producer = new KafkaProducer<String, Object>(ServiceBrokerProperties.serviceProducerProp);
			switch (this.topic) {
			case ServiceConstants.CLIENT_CAHCHE_TOPIC:// 缓存数据中心才有
				ChcheTopic.createProducer(ServiceExecuteListener.producer,key,topic,obj);
				break;
			case ServiceConstants.SERVER_CAHCHE_TOPIC:// 缓存数据中心才有
				ChcheTopic.createProducer(ServiceExecuteListener.producer,key,topic,obj);
				break;
			case ServiceConstants.SYN_DATA_HAND_TOPIC:
				SynDataHandTopicProduc.createProducer(ServiceExecuteListener.producer,key,topic,obj);
				break;
			default:
				break;
			}
		} catch (BrokerException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			//producer.close();
		}
	}
}
