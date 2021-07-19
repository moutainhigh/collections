package cn.gwssi.broker.server.auth;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.*;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import cn.gwssi.broker.server.pool.ServiceThreadPool;
import cn.gwssi.common.exception.BrokerException;
import cn.gwssi.common.model.RequestContext;
import cn.gwssi.common.model.SynReponseContext;
import cn.gwssi.common.model.TPtFwrzxxxxBO;
import cn.gwssi.common.resource.ServiceConstants;

/**
 * 同步时间超时处理
 * @author xue
 * @version 1.0
 * @since 2016/5/15
 */
public class SyncServiceClientWithTimeout {
	private static Logger log = Logger.getLogger("kafka");
	public static SynReponseContext synReponseContext;
	
	public static void callSyncWithTimout(RequestContext requestContext) throws BrokerException{
		String uuid=UUID.randomUUID().toString();
		System.out.println(uuid+"主线程 : " + Thread.currentThread().getName());
		/*TPtFwrzxxxxBO t = new TPtFwrzxxxxBO();
		t.setFwrzxxid(uuid);
		t.setDetail("服务端鉴权数据或通知发送请求前");
		t.setFwrzjbid(requestContext.getServiceId());
		t.setExecutecontent("客户端请求时：同步线程处理:"+requestContext);
		t.setTime(ServiceConstants.sdf.format(new Date()));
		log.info(t);*/
		
		ExecutorService executor = ServiceThreadPool.getExecutorService(1);
        SynServiceClientTaskWithResult task = new SynServiceClientTaskWithResult(requestContext);
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
