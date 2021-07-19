package com.gwssi.help.txn;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

import com.gwssi.help.vo.HelpContext;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;

public class TxnHelp extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnHelp.class, HelpContext.class );
	

	
	/**
	 * 构造函数
	 */
	public TxnHelp()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}

	/** 
	 * 平台介绍
	 */
	public void txn7000001( HelpContext context) throws TxnException, IOException
	{

	}
	/** 使用说明
	 */
	public void txn7000002( HelpContext context) throws TxnException, IOException
	{

	}
	
	/** 服务对象的职能、角色介绍
	 */
	public void txn7000004( HelpContext context) throws TxnException, IOException
	{

	}
	/** 采集服务流程
	 */
	public void txn7000005( HelpContext context) throws TxnException, IOException
	{

	}
	/** 共享服务流程
	 */
	public void txn7000006( HelpContext context) throws TxnException, IOException
	{

	}
	/** 交换平台部署
	 */
	public void txn7000007( HelpContext context) throws TxnException, IOException
	{

	}
	/** 交换流程
	 */
	public void txn7000008( HelpContext context) throws TxnException, IOException
	{

	}
	/** 系统概览
	 */
	public void txn9000001( HelpContext context) throws TxnException, IOException
	{

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
		HelpContext appContext = new HelpContext( context );
		invoke( method, appContext );
	}
}
