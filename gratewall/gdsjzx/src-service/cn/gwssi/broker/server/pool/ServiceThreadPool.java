package cn.gwssi.broker.server.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.gwssi.common.resource.MsgSendThreadFactory;

public class ServiceThreadPool {
	private volatile static ExecutorService executor;
	
	private ServiceThreadPool() {
	}

	public static ExecutorService getExecutorService(int serviceThreadNum) {
		if (executor==null) {
			synchronized (ServiceThreadPool.class) {
				if (executor==null) {
					executor = new ThreadPoolExecutor(serviceThreadNum, //core pool size  
							serviceThreadNum, //maximum pool size  
                            30L,       //keep alive time  
                            TimeUnit.SECONDS,  
                            new LinkedBlockingQueue<Runnable>(),new MsgSendThreadFactory());  
				}
			}
		}
		return executor;
	}
}