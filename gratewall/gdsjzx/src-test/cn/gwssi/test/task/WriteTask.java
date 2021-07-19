package cn.gwssi.test.task;

/**
 * 模拟产生数据的线程类
 * @author xue
 *
 */
public class WriteTask implements Runnable {

	@Override
	public void run() {
		for(int i=0;i<20;i++){
//			try {
//				sleep(100);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			WriterQueue.getQueue().put("for:"+i+" thread:"+Thread.currentThread().getName());
		}
	}
	
	private void sleep(int millis) throws InterruptedException {
		Thread.sleep(millis);
	}

}
