package cn.gwssi.broker.server.kafka.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import cn.gwssi.broker.server.auth.ServiceAuth;
import cn.gwssi.broker.server.listener.ServiceBrokerProperties;
import cn.gwssi.common.resource.BeanXmlUtil;
import cn.gwssi.common.resource.ServiceConstants;

/**
 * 消费缓存权限
 * @author xue
 * @version 1.0
 * @since 2016/5/20
 */
public class ChcheTopicConsum {
	
	public static void dealData(ConsumerRecord<String, Object> record){
		if(ServiceBrokerProperties.serviceBrokerProp.get("localname").equals(record.key())){//是当前应用的数据才要
			List<String> result =(List<String>) record.value();
			List<Map<String, String>> cacheResult = new ArrayList<Map<String,String>>();
			if(result!=null && result.size()>0){
				for(String s:result){
					System.out.println("服务端获取到的推送过来的权限数据："+s);
					cacheResult.add(BeanXmlUtil.xml2Map(s));
				}
			}
			ServiceAuth.authMap.remove("cache");//只移除指定key的数据
			ServiceAuth.authMap.clear();//全部移除
			ServiceAuth.authMap.put("cache", cacheResult);
			BeanXmlUtil.StringTofile(BeanXmlUtil.list2xml(cacheResult), ChcheTopicConsum.class.getClassLoader().getResource(ServiceConstants.SERVIE_CAHCHE_FILE_NAME).getFile(), ServiceConstants.UTFCHARSET);
			//server只有两个topic，不需要重新加载
		}
	}
	
}
