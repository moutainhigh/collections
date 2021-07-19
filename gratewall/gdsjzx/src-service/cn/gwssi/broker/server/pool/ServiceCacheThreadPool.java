package cn.gwssi.broker.server.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.gwssi.common.resource.MsgSendThreadFactory;

public class ServiceCacheThreadPool {
	private volatile static ExecutorService executor;
	
	private ServiceCacheThreadPool() {
	}

	public static ExecutorService getExecutorService() {
		if (executor==null) {
			synchronized (ServiceCacheThreadPool.class) {
				if (executor==null) {
					executor = new ThreadPoolExecutor(0, //core pool size  
                            Integer.MAX_VALUE, //maximum pool size  
                            60L,               //keep alive time  
                            TimeUnit.SECONDS,  
                            new SynchronousQueue<Runnable>(),new MsgSendThreadFactory());  
				}
			}
		}
		return executor;
	}
}