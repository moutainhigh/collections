package cn.gwssi.broker.server.task;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import org.apache.log4j.Logger;

import cn.gwssi.broker.server.listener.ServiceBrokerProperties;
import cn.gwssi.broker.server.pool.ServiceThreadPool;
import cn.gwssi.common.model.RequestContext;

/**
 * 异步线程池处理
 * @author xue
 * @version 1.0
 * @since 2016/5/15
 */
public class AsyncServiceWithTimeout {
	private static  Logger log=Logger.getLogger(AsyncServiceWithTimeout.class);
	public static List<Map<String,String>> rsresult;
	
    public static void callAsyncWithTimout(RequestContext requestContext) {
    	log.info("主线程 : " + Thread.currentThread().getName());
    	ExecutorService executor = ServiceThreadPool.getExecutorService(Integer.parseInt((String) ServiceBrokerProperties.serviceBrokerProp.get("serverTimeOutThreadSize")));
        AsynServiceTaskWithResult task = new AsynServiceTaskWithResult(requestContext);
        executor.execute(task);
    }
    
}
