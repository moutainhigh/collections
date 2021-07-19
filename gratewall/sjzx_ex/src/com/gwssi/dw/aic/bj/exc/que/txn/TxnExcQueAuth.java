package com.gwssi.dw.aic.bj.exc.que.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.dw.aic.bj.exc.que.vo.ExcQueAuthContext;

public class TxnExcQueAuth extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnExcQueAuth.class, ExcQueAuthContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "exc_que_auth";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "${mod.function.rowset}";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one exc_que_auth";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "${mod.function.update}";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "${mod.function.insert}";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "${mod.function.delete}";
	
	/**
	 * 构造函数
	 */
	public TxnExcQueAuth()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询编办列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60114011( ExcQueAuthContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoExcQueAuthSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoExcQueAuth result[] = context.getExcQueAuths( outputNode );
	}
	
	/** 修改编办信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60114012( ExcQueAuthContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoExcQueAuth exc_que_auth = context.getExcQueAuth( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加编办信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60114013( ExcQueAuthContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoExcQueAuth exc_que_auth = context.getExcQueAuth( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询编办用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60114014( ExcQueAuthContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoExcQueAuthPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoExcQueAuth result = context.getExcQueAuth( outputNode );
	}
	
	/** 删除编办信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60114015( ExcQueAuthContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoExcQueAuthPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
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
		ExcQueAuthContext appContext = new ExcQueAuthContext( context );
		invoke( method, appContext );
	}
}
