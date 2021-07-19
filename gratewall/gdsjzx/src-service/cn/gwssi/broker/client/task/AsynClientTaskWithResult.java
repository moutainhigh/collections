package cn.gwssi.broker.client.task;

import cn.gwssi.common.model.RequestContext;

/**
 * 异步线程处理
 * @author xue
 * @version 1.0
 * @since 2016/5/15
 */
public class AsynClientTaskWithResult implements Runnable{

	private RequestContext requestParam;
	
	public AsynClientTaskWithResult(RequestContext requestContext){
		this.requestParam=requestContext;
	}
	
	@Override
	public void run() {
	}

	public RequestContext getRequestParam() {
		return requestParam;
	}

	public void setRequestParam(RequestContext requestParam) {
		this.requestParam = requestParam;
	}

}
