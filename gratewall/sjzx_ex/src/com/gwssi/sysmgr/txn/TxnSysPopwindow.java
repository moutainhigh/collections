package com.gwssi.sysmgr.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.sysmgr.vo.SysPopwindowContext;

public class TxnSysPopwindow extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysPopwindow.class, SysPopwindowContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "sys_popwindow";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select sys_popwindow list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one sys_popwindow";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one sys_popwindow";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one sys_popwindow";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one sys_popwindow";
	
	/**
	 * 构造函数
	 */
	public TxnSysPopwindow()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询弹窗任务列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60800001( SysPopwindowContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoSysPopwindowSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoSysPopwindow result[] = context.getSysPopwindows( outputNode );
	}
	/** 用於主頁展示列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60800008( SysPopwindowContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysPopwindowPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		Attribute.setPageRow(context, "tz-record", 8);
		table.executeFunction( ROWSET_FUNCTION, context, "tz-query", "tz-record" );
		//table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoSysPopwindow result = context.getSysPopwindow( outputNode );
	}
	/** 修改弹窗任务信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60800002( SysPopwindowContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoSysPopwindow sys_popwindow = context.getSysPopwindow( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加弹窗任务信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60800003( SysPopwindowContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String uuid = com.gwssi.common.util.UuidGenerator.getUUID();
		context.getRecord(inputNode).setValue("sys_popwindow_id", uuid);
		// 增加记录的内容 VoSysPopwindow sys_popwindow = context.getSysPopwindow( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询弹窗任务用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60800004( SysPopwindowContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysPopwindowPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoSysPopwindow result = context.getSysPopwindow( outputNode );
	}
	
	/** 删除弹窗任务信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60800005( SysPopwindowContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoSysPopwindowPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/**
	 * 查询当前用户的弹窗信息列表
	 * @param context
	 * @throws TxnException
	 */
	public void txn60800006( SysPopwindowContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoSysPopwindowPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );

		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		//table.executeFunction( "getPopWindowContentByRoles", context, inputNode, outputNode );
	}
	
	/**
	 * 查询当前用户的弹窗信息个数
	 * @param context
	 * @throws TxnException
	 */
	public void txn60800007( SysPopwindowContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, outputNode, -1);
		// 删除记录的主键列表 VoSysPopwindowPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( "getPopWindowCountByRoles", context, inputNode, outputNode );
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
		SysPopwindowContext appContext = new SysPopwindowContext( context );
		invoke( method, appContext );
	}
}
