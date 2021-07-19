package cn.gwssi.broker.client;

import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import cn.gwssi.common.exception.BrokerException;
import cn.gwssi.common.model.RequestContext;

/**
 * 通用的功能都放在抽象类Broker，然后继承
 * @author xue
 * @version 1.0
 * @since 2016/4/20
 */
public abstract class Broker {
	
	/**
	 * 缓存，并写入文件
	 * @param requestParam
	 * @throws JAXBException 
	 * @throws BrokerException 
	 */
	protected void cacheState(RequestContext requestParam) throws JAXBException, BrokerException{
		//ExecuteListener.cacheRequestParam = requestParam;
		//BeanXmlUtil.StringTofile(BeanXmlUtil.beanToXml(ExecuteListener.cacheRequestParam), ServiceConstants.PATH, ServiceConstants.UTFCHARSET);
	}
	
	protected void writeKafka(List<Map<String,String>> responseResults){
		
	}
	
}