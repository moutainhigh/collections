package cn.gwssi.broker.client.task;

import java.util.concurrent.*;
import org.apache.log4j.Logger;

import cn.gwssi.broker.client.listener.ClientBrokerProperties;
import cn.gwssi.broker.client.pool.ClientTimeOutThreadSizePool;
import cn.gwssi.common.model.RequestContext;

/**
 * 异步线程池处理
 * @author xue
 * @version 1.0
 * @since 2016/5/15
 */
public class AsyncClientWithTimeout {
	private static  Logger log=Logger.getLogger(AsyncClientWithTimeout.class);
	
    public static void callAsyncWithTimout(long timeout,RequestContext requestContext) {
    	log.info("主线程 : " + Thread.currentThread().getName());
        ExecutorService executor = ClientTimeOutThreadSizePool.getExecutorService(Integer.parseInt((String) ClientBrokerProperties.clientBrokerProp.get("clientTimeOutThreadSize")));
        AsynClientTaskWithResult task = new AsynClientTaskWithResult(requestContext);
        executor.execute(task);
    }
    
}
