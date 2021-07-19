package cn.gwssi.broker.client.task;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import cn.gwssi.broker.client.listener.ClientBrokerProperties;
import cn.gwssi.broker.client.pool.ClientTimeOutThreadSizePool;
import cn.gwssi.common.exception.BrokerException;
import cn.gwssi.common.model.RequestContext;
import cn.gwssi.common.model.SynReponseContext;

/**
 * 同步时间超时处理
 * @author xue
 * @version 1.0
 * @since 2016/5/15
 */
public class SyncClientWithTimeout {
	public static SynReponseContext synReponseContext;
	
	private static Logger log = Logger.getLogger("kafka");
	
	
	public static void callSyncWithTimout(RequestContext requestContext) throws BrokerException{
		System.out.println("主线程 : " + Thread.currentThread().getName());
		
		/*TPtFwrzxxxxBO t = new TPtFwrzxxxxBO();
		String uuid = UUID.randomUUID().toString();
		t.setFwrzxxid(uuid);
		t.setDetail("客户端请求时：同步线程处理");
		t.setFwrzjbid(requestContext.getServiceId());
		t.setTime(ServiceConstants.sdf.format(new Date()));
		t.setExecutecontent(requestContext.getParams());
		log.info(t);*/
		
		ExecutorService executor = ClientTimeOutThreadSizePool.getExecutorService(Integer.parseInt((String)ClientBrokerProperties.clientBrokerProp.get("clientTimeOutThreadSize")));
        SynClientTaskWithResult task = new SynClientTaskWithResult(requestContext);
        Future<Object> future = executor.submit(task);
		String timeOut = requestContext.getTimeOut();
        if(StringUtils.isNotBlank(timeOut)){//有超时
        	timeoutTask(executor,future,timeOut);
		}else{//无超时
			immediateTask(executor,future,timeOut);
		}
    }

	private static void immediateTask(ExecutorService executor,Future<Object> future,String timeOut) throws BrokerException {
		try {
			Object o = future.get();//future.get(Long.parseLong(requestContext.getTimeOut()), TimeUnit);
			synReponseContext = (SynReponseContext) o; //Properties 
		} catch (InterruptedException e) {//get为一个等待过程，异常中止get会抛出异常 
			e.printStackTrace();
			throw new BrokerException("002","get为一个等待过程，异常中止get会抛出异常:InterruptedException",e.getMessage());
		} catch (ExecutionException e) {//submit计算出现异常
			e.printStackTrace();
			throw new BrokerException("003","submit计算出现异常:ExecutionException",e.getMessage());
		}
	}

	private static void timeoutTask(ExecutorService executor,Future<Object> future,String timeOut) throws BrokerException {
		try {
			Object o = future.get(Long.parseLong(timeOut), TimeUnit.MILLISECONDS);
			synReponseContext = (SynReponseContext) o;
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new BrokerException("002","get为一个等待过程，异常中止get会抛出异常:InterruptedException",e.getMessage());
		} catch (ExecutionException e) {
			e.printStackTrace();
			throw new BrokerException("003","submit计算出现异常:ExecutionException",e.getMessage());
		} catch (TimeoutException e) {//超时异常
			e.printStackTrace();
			throw new BrokerException("004","超时异常:TimeoutException",e.getMessage());
		}finally {
	    	future.cancel(true);//超时后取消任务
	    	//return;
		}
	}
    
}
