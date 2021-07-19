package cn.gwssi.broker.client.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.gwssi.common.resource.MsgSendThreadFactory;

public class ClientTimeOutThreadSizePool {
	private volatile static ExecutorService executor;
	
	private ClientTimeOutThreadSizePool() {
	}

	public static ExecutorService getExecutorService(int clientThreadNum) {
		if (executor==null) {
			synchronized (ClientTimeOutThreadSizePool.class) {
				if (executor==null) {
					executor = new ThreadPoolExecutor(clientThreadNum, //core pool size  
							clientThreadNum, //maximum pool size  
                            30L,       //keep alive time  
                            TimeUnit.SECONDS,  
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