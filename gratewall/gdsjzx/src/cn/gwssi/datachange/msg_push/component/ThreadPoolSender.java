package cn.gwssi.datachange.msg_push.component;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import cn.gwssi.datachange.msg_push.api.Msg;
import cn.gwssi.datachange.msg_push.api.MsgSender;
import cn.gwssi.datachange.msg_push.api.Result;
import cn.gwssi.datachange.msg_push.api.receiver.Receiver;

/**
 * 
 * @author wuyincheng
 * @date Jul 12, 2016
 */
public class ThreadPoolSender implements MsgSender{
	
	private MsgSender sender = null;
	
	private boolean isFixedPool;
	private int poolSize;
	private int timeout;
	
	private ExecutorService pool = null;
	
	public ThreadPoolSender(MsgSender sender) {
		this.sender = sender;
		initPool();
	}
	
	public ThreadPoolSender(boolean isFixedPool, int poolSize, int timeout, MsgSender sender) {
		this.isFixedPool = isFixedPool;
		this.poolSize = poolSize;
		this.timeout = timeout;
		this.sender = sender;
		initPool();
	}
	
	private void initPool(){
		if(pool == null) {
			pool = isFixedPool ? 
				new ThreadPoolExecutor(poolSize, poolSize,
						30, TimeUnit.SECONDS,
                        new LinkedBlockingQueue<Runnable>(), new MsgSendThreadFactory()) :
            	new ThreadPoolExecutor(poolSize, Integer.MAX_VALUE,
                        30, TimeUnit.SECONDS,
                        new SynchronousQueue<Runnable>(), new MsgSendThreadFactory());
		}
	}

	@Override
	public Result sendMsg(Msg msg, Receiver receiver) throws Exception {
		Future<Result> future = pool.submit(new MsgSendTask(msg, receiver, sender));
		return future.get(timeout, TimeUnit.SECONDS);
	}
	
	
	private static class MsgSendThreadFactory  implements ThreadFactory{
		private AtomicInteger count = new AtomicInteger(1);
		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setDaemon(true);
			t.setName("Msg-Sender-[" + count.incrementAndGet() + "]");
			return t;
		}
		
	}
	
	private static class MsgSendTask implements Callable<Result> {

		private Msg msg ;
		private Receiver receiver;
		private MsgSender sender;
		
		public MsgSendTask(Msg msg, Receiver receiver, MsgSender sender) {
			this.msg = msg;
			this.receiver = receiver;
			this.sender = sender;
		}
		
		@Override
		public Result call() throws Exception {
			return sender.sendMsg(msg, receiver);
		}
		
	}

}
