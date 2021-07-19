package cn.gwssi.datachange.msg_push.component;

import cn.gwssi.datachange.msg_push.api.MsgSender;

/**
 * 带重传与线程池构造
 * @author wuyincheng
 * @date Jul 12, 2016
 */
public abstract class AbstractSenderFactory {
	
	protected boolean isFixedPool = true;//固定线程数量
	protected int poolSize = 5;   //线程池大小
	protected int timeout = 30; //超时时间 s
	protected int retryTimes = 3; //非配置出错(空指针) 重试次数
	protected int retryIntervals = 3;//重试间隔时间 （单位：s）

	protected abstract MsgSender getMsgSender();
	
	public MsgSender createMsgSender() {
		MsgSender sender = new ThreadPoolSender(isFixedPool, poolSize, timeout,
				                new RetrySender(getMsgSender(), retryTimes, retryIntervals));
		return sender;
	}

	public boolean isFixedPool() {
		return isFixedPool;
	}

	public void setFixedPool(boolean isFixedPool) {
		this.isFixedPool = isFixedPool;
	}

	public int getPoolSize() {
		return poolSize;
	}

	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getRetryTimes() {
		return retryTimes;
	}

	public void setRetryTimes(int retryTimes) {
		this.retryTimes = retryTimes;
	}

	public int getRetryIntervals() {
		return retryIntervals;
	}

	public void setRetryIntervals(int retryIntervals) {
		this.retryIntervals = retryIntervals;
	}
	
	
	
}
