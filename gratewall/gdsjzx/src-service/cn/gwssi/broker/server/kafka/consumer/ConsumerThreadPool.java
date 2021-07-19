package cn.gwssi.broker.server.kafka.consumer;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

/**
 * 消费线程池
 * @author xue
 * @version 1.0
 * @since 2016/5/10
 */
public class ConsumerThreadPool {
	private static  Logger log=Logger.getLogger(ConsumerThreadPool.class);
	public static void createConsumerTopic(List<String> topics) {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		ConsumerThread task = new ConsumerThread(topics.toString());
        executor.execute(task);
    }

}
