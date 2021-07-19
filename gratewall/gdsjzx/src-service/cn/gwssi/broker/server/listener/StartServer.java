package cn.gwssi.broker.server.listener;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.log4j.Logger;

import cn.gwssi.broker.server.cxf.restful.ResponseService;
import cn.gwssi.common.exception.BrokerException;

/**
 * cxf内部服务启动
 * @author xue
 * @version 1.0
 * @since 2016/5/1
 */
public class StartServer {
	private static Logger log = Logger.getLogger(StartServer.class);
	public static boolean start(String accessAddress,String ip) throws BrokerException{
		log.info("本地获取的accessAddress："+accessAddress);
		log.info("本地获取的ip："+ip);
		boolean flag = false;
		try {
			if(StringUtils.isNotBlank(accessAddress)){
				JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
				factoryBean.setResourceClasses(ResponseService.class);
				factoryBean.setAddress(accessAddress);//ServiceConstants.SERVICE_URL
				factoryBean.create();
				flag = true;
			}else{
				if(StringUtils.isNotBlank(ip)){
					String[] strIp = ip.split("\\n");
					JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
					factoryBean.setResourceClasses(ResponseService.class);
					String address = "http://"+strIp[0]+":9000";
					factoryBean.setAddress(address);//ServiceConstants.SERVICE_URL
					factoryBean.create();
					flag = true;
				}
			}
			
		} catch (Exception e) {
			e.getStackTrace();
			throw new BrokerException("007","启动服务框架内部服务异常",e.getMessage());
		}
		return flag;
	}
	
}
