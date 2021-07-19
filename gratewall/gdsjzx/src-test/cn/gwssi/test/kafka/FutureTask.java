package cn.gwssi.test.kafka;

import java.util.concurrent.*;

public class FutureTask {
	public static int i;
public static void test(){
	/*ExecutorService executor = ClientCacheThreadPool.getExecutorService();
    Future<Object> future = executor.submit(new TaskTest());
	try {
		Object o = future.get(1000, TimeUnit.MILLISECONDS);
		i = (int) o;
	} catch (InterruptedException e) {
		System.out.println("1");
		e.printStackTrace();
	} catch (ExecutionException e) {
		System.out.println("2");
		e.printStackTrace();
	} catch (TimeoutException e) {//超时异常
		System.out.println("3");
		e.printStackTrace();
	}finally {
    	future.cancel(true);//超时后取消任务
    	//return;
	}*/
}
}
class TaskTest implements Callable<Object> {
	@Override
	public Object call() throws Exception {
		int i =0,j=10,z=0;
		z = j/i;
		return z;
	}
	
}
