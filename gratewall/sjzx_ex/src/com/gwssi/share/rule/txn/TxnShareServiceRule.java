package com.gwssi.share.rule.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.share.rule.vo.ShareServiceRuleContext;

public class TxnShareServiceRule extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnShareServiceRule.class, ShareServiceRuleContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "share_service_rule";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select share_service_rule list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one share_service_rule";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one share_service_rule";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one share_service_rule";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one share_service_rule";
	
	/**
	 * 构造函数
	 */
	public TxnShareServiceRule()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询访问规则列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn403001( ShareServiceRuleContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoShareServiceRuleSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoShareServiceRule result[] = context.getShareServiceRules( outputNode );
	}
	
	/** 修改访问规则信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn403002( ShareServiceRuleContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoShareServiceRule share_service_rule = context.getShareServiceRule( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加访问规则信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn403003( ShareServiceRuleContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoShareServiceRule share_service_rule = context.getShareServiceRule( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询访问规则用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn403004( ShareServiceRuleContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoShareServiceRulePrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoShareServiceRule result = context.getShareServiceRule( outputNode );
	}
	
	/** 删除访问规则信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn403005( ShareServiceRuleContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoShareServiceRulePrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/**
	 * 
	 * txn403007(根据服务ID删除对应记录)    
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
	public void txn403007( ShareServiceRuleContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoShareServiceRulePrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( "delByService_ID", context, inputNode, outputNode );
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
		ShareServiceRuleContext appContext = new ShareServiceRuleContext( context );
		invoke( method, appContext );
	}
}
