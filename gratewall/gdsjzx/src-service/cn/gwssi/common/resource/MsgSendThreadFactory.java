package cn.gwssi.common.resource;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class MsgSendThreadFactory implements ThreadFactory{

	private AtomicInteger count = new AtomicInteger(1);
	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r);
		t.setDaemon(true);
		t.setName("Msg-Sender-[" + count.incrementAndGet() + "]");
		return t;
	}

}
