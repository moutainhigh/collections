package com.gwssi.resource.share.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.resource.share.vo.ResShareTableContext;

public class TxnResShareTable extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnResShareTable.class, ResShareTableContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "res_share_table";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select res_share_table list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one res_share_table";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one res_share_table";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one res_share_table";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one res_share_table";
	
	/**
	 * 构造函数
	 */
	public TxnResShareTable()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询共享表列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn20301001( ResShareTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoResShareTableSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoResShareTable result[] = context.getResShareTables( outputNode );
	}
	
	/** 修改共享表信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn20301002( ResShareTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoResShareTable res_share_table = context.getResShareTable( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加共享表信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn20301003( ResShareTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoResShareTable res_share_table = context.getResShareTable( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询共享表用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn20301004( ResShareTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoResShareTablePrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoResShareTable result = context.getResShareTable( outputNode );
	}
	
	/** 删除共享表信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn20301005( ResShareTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoResShareTablePrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
		
	/**
	 * 
	 * txn20301006(根据主题查主题下面的表)    
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
	public void txn20301006( ResShareTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		
		table.executeFunction( "quertShareTableByTopic", context, inputNode, outputNode );
		//System.out.println(context);
		
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
		ResShareTableContext appContext = new ResShareTableContext( context );
		invoke( method, appContext );
	}
}
