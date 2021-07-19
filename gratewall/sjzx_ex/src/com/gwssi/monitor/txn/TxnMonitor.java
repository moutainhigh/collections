package com.gwssi.monitor.txn;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.monitor.vo.MonitorContext;

public class TxnMonitor extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnMonitor.class, MonitorContext.class );
	

	
	/**
	 * 构造函数
	 */
	public TxnMonitor()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}

	/** 跳转到实时监控
	 * @param context 交易上下文
	 * @throws TxnException
	 * @throws IOException 
	 */
	public void txn5100000( MonitorContext context) throws TxnException, IOException
	{
		//http://172.30.7.179:8080/exmon/index.do?method=rtime
	}
	/** 跳转到监控指标管理
	 * @param context 交易上下文
	 * @throws TxnException
	 * @throws IOException 
	 */
	public void txn5200000( MonitorContext context) throws TxnException, IOException
	{
		//http://172.30.7.179:8080/exmon/index.do?method=manage
	}
	/** 跳转到警情发现与管理
	 * @param context 交易上下文
	 * @throws TxnException
	 * @throws IOException 
	 */
	public void txn5300000( MonitorContext context) throws TxnException, IOException
	{
		//http://172.30.7.179:8080/exmon/event.do
	}

		
	/**
	 * 重载父类的方法，用于替换交易接口的输入变量
	 * 调用函数
	 * @param funcName 方法名称
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void doService( String funcName,
			TxnContext context ) throws TxnException
	{
		Method method = (Method)txnMethods.get( funcName );
		if( method == null ){
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException( ErrorConstant.JAVA_METHOD_NOTFOUND,
					"没有找到交易码[" + txnCode + ":" + funcName + "]的处理函数"
			);
		}
		// 执行
		MonitorContext appContext = new MonitorContext( context );
		invoke( method, appContext );
	}
}
