package com.gwssi.dw.runmgr.etl.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import com.gwssi.dw.runmgr.etl.vo.SysEtlDbsourceContext;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;

public class TxnSysEtlDbsource extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysEtlDbsource.class, SysEtlDbsourceContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "sys_etl_dbsource";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select sys_etl_dbsource list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one sys_etl_dbsource";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one sys_etl_dbsource";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one sys_etl_dbsource";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one sys_etl_dbsource";
	
	/**
	 * 构造函数
	 */
	public TxnSysEtlDbsource()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询数据来源管理列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn501020001( SysEtlDbsourceContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 修改数据来源管理信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn501020002( SysEtlDbsourceContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoSysEtlDbsource sys_etl_dbsource = context.getSysEtlDbsource( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加数据来源管理信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn501020003( SysEtlDbsourceContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoSysEtlDbsource sys_etl_dbsource = context.getSysEtlDbsource( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询数据来源管理用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn501020004( SysEtlDbsourceContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysEtlDbsourcePrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoSysEtlDbsource result = context.getSysEtlDbsource( outputNode );
	}
	
	/** 删除数据来源管理信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn501020005( SysEtlDbsourceContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoSysEtlDbsourcePrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
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
		SysEtlDbsourceContext appContext = new SysEtlDbsourceContext( context );
		invoke( method, appContext );
	}
}
