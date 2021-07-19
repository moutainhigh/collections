package cn.gwssi.broker.server.task;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;

import cn.gwssi.broker.server.listener.ServiceBrokerProperties;
import cn.gwssi.broker.server.pool.ServiceCacheThreadPool;
import cn.gwssi.broker.server.pool.ServiceThreadPool;
import cn.gwssi.common.exception.BrokerException;
import cn.gwssi.common.model.RequestContext;

/**
 * 同步时间超时处理
 * @author xue
 * @version 1.0
 * @since 2016/5/15
 */
public class SyncServiceWithTimeout {
	private static  Logger log=Logger.getLogger(SyncServiceWithTimeout.class);
	public static List rsresult;
	
	//private static Logger logK = Logger.getLogger("kafka");
	
	public static void callSyncWithTimout(RequestContext requestContext) throws BrokerException{
		String uuid = UUID.randomUUID().toString();
		log.info(uuid+"主线程 : " + Thread.currentThread().getName());
		log.info("SyncServiceWithTimeout参数"+requestContext.toString());
		/*TPtFwrzxxxxBO t = new TPtFwrzxxxxBO();
		t.setFwrzxxid(uuid);
		t.setDetail("執行服務是否降級");
		t.setFwrzjbid(requestContext.getServiceId());
		t.setExecutecontent("服务端线程处理"+requestContext.getParams());
		t.setTime(ServiceConstants.sdf.format(new Date()));
		logK.info(t);*/
		SynServiceTaskWithResult task = new SynServiceTaskWithResult(requestContext);
		
        if(requestContext!=null && requestContext.getServerModel()!=null && "1".equals(requestContext.getServerModel())){//降级
        	ExecutorService executor = ServiceThreadPool.getExecutorService(Integer.parseInt((String) ServiceBrokerProperties.serviceBrokerProp.get("serverTimeOutThreadSize")));
            Future<Object> future = executor.submit(task);
            timeoutFuture(executor,future,requestContext.getTimeOut());
        }else{//正常
        	ExecutorService executor = ServiceCacheThreadPool.getExecutorService();
        	Future<Object> future = executor.submit(task);
        	timeoutFuture(executor,future,requestContext.getTimeOut());
        }
    }
	
	private static void timeoutFuture(ExecutorService executor,
		Future<Object> future, String timeOut) throws BrokerException {
		try {
			long timeout = Long.parseLong(timeOut);
			log.info("超时时间秒："+timeout);
			Object o = future.get(timeout, TimeUnit.MILLISECONDS);
			rsresult = (List) o;
		} catch (InterruptedException e) {//get为一个等待过程，异常中止get会抛出异常 
			e.printStackTrace();
			throw new BrokerException("002","get为一个等待过程，异常中止get会抛出异常:InterruptedException",e.getMessage());
		} catch (ExecutionException e) {//submit计算出现异常
			e.printStackTrace();
			throw new BrokerException("003","submit计算出现异常:ExecutionException",e.getMessage());
		} 
        catch (TimeoutException e) {//超时异常
			e.printStackTrace();
			throw new BrokerException("004","超时异常:TimeoutException",e.getMessage());
		}
        finally {
	    	future.cancel(true);//超时后取消任务
	    	//return;
		}
		
	}
    
	private static void timeoutFutureTask(Thread thread,
			FutureTask<Object> futureTask, String timeOut) throws BrokerException {
		try {
			// 设置为后台线程
			/*thread.setDaemon(true);
			thread.start();*/
			long timeout = Long.parseLong(timeOut);
			log.info("超时时间秒：" + timeout);
			Object o = futureTask.get(timeout, TimeUnit.MILLISECONDS);
			rsresult = (List) o;
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new BrokerException("002","get为一个等待过程，异常中止get会抛出异常:InterruptedException",e.getMessage());
		} catch (ExecutionException e) {
			e.printStackTrace();
			throw new BrokerException("003","submit计算出现异常:ExecutionException",e.getMessage());
		} catch (TimeoutException e) {
			e.printStackTrace();
			throw new BrokerException("004","超时异常:TimeoutException",e.getMessage());
		}finally {
			//thread.interrupt();
		}

	}
}
