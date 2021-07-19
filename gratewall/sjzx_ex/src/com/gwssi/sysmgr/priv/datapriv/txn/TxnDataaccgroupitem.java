package com.gwssi.sysmgr.priv.datapriv.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.sysmgr.priv.datapriv.vo.DataaccgroupitemContext;

public class TxnDataaccgroupitem extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnDataaccgroupitem.class, DataaccgroupitemContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "dataaccgroupitem";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select dataaccgroupitem list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "${mod.function.select}";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "${mod.function.update}";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one dataaccgroupitem";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one dataaccgroupitem";
	
	/**
	 * 构造函数
	 */
	public TxnDataaccgroupitem()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询数据权限分组项列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn103061( DataaccgroupitemContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoDataaccgroupitemSelectKey selectKey = context.getSelectKey( inputNode );
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoDataaccgroupitem result[] = context.getDataaccgroupitems( outputNode );
	}
	
	/** 修改数据权限分组项信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn103062( DataaccgroupitemContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoDataaccgroupitem dataaccgroupitem = context.getDataaccgroupitem( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加数据权限分组项信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn103063( DataaccgroupitemContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoDataaccgroupitem dataaccgroupitem = context.getDataaccgroupitem( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询数据权限分组项用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn103064( DataaccgroupitemContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoDataaccgroupitemPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoDataaccgroupitem result = context.getDataaccgroupitem( outputNode );
	}
	
	/** 删除数据权限分组项信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn103065( DataaccgroupitemContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoDataaccgroupitemPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	public void txn103066( DataaccgroupitemContext context ) throws TxnException{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoDataaccgroupitemPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( "deleteItemByAll", context, inputNode, outputNode );
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
		DataaccgroupitemContext appContext = new DataaccgroupitemContext( context );
		invoke( method, appContext );
	}
}
