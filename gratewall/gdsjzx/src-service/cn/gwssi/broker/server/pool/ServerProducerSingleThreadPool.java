package cn.gwssi.broker.server.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.gwssi.common.resource.MsgSendThreadFactory;

public class ServerProducerSingleThreadPool {
	private volatile static ExecutorService executor;
	
	private ServerProducerSingleThreadPool() {
	}

	public static ExecutorService getExecutorService() {
		if (executor==null) {
			synchronized (ServerProducerSingleThreadPool.class) {
				if (executor==null) {
					executor = new ThreadPoolExecutor(1,  //core pool size  
                            1,  //maximum pool size  
                            3000L, //keep alive time  
                            TimeUnit.MILLISECONDS,  
                            new LinkedBlockingQueue<Runnable>(),new MsgSendThreadFactory());
				}
			}
		}
		return executor;
	}

}
/*java -cp KafkaOffsetMonitor-assembly-0.2.0.jar com.quantifind.kafka.offsetapp.OffsetGetterWeb --zk 10.1.2.122:2181 \
--port 8089 --refresh 10.seconds --retain 1.days
https://github.com/quantifind/KafkaOffsetMonitor/releases/download/v0.2.0/KafkaOffsetMonitor-assembly-0.2.0.jar
*/