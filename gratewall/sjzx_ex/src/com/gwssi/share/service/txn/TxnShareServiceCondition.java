package com.gwssi.share.service.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.share.service.vo.ShareServiceConditionContext;

public class TxnShareServiceCondition extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnShareServiceCondition.class, ShareServiceConditionContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "share_service_condition";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select share_service_condition list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one share_service_condition";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one share_service_condition";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one share_service_condition";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one share_service_condition";
	
	/**
	 * 构造函数
	 */
	public TxnShareServiceCondition()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询服务查询条件列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn40210001( ShareServiceConditionContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoShareServiceConditionSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoShareServiceCondition result[] = context.getShareServiceConditions( outputNode );
	}
	
	/** 修改服务查询条件信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn40210002( ShareServiceConditionContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoShareServiceCondition share_service_condition = context.getShareServiceCondition( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加服务查询条件信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn40210003( ShareServiceConditionContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoShareServiceCondition share_service_condition = context.getShareServiceCondition( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询服务查询条件用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn40210004( ShareServiceConditionContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoShareServiceConditionPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoShareServiceCondition result = context.getShareServiceCondition( outputNode );
	}
	
	/** 删除服务查询条件信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn40210005( ShareServiceConditionContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoShareServiceConditionPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	/**
	 * 
	 * txn40210006(根据服务ID删除条件)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param context
	 * @throws TxnException        
	 * void       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public void txn40210006( ShareServiceConditionContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoShareServiceConditionPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( "deleteConditionsByShareServiceID", context, inputNode, outputNode );
	}
	
	/** 查询服务参数信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn40210101( ShareServiceConditionContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoShareServiceConditionSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( "queryCondition", context, inputNode, outputNode );
		// 查询到的记录集 VoShareServiceCondition result[] = context.getShareServiceConditions( outputNode );
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
		ShareServiceConditionContext appContext = new ShareServiceConditionContext( context );
		invoke( method, appContext );
	}
}
