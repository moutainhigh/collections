package com.gwssi.dw.runmgr.db.txn;

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
import com.gwssi.dw.runmgr.services.vo.SysSvrServiceParamContext;

public class TxnSysDbViewParam extends TxnService
{

	// 数据表名称
	private static final String TABLE_NAME = "sys_db_view_param";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select sys_db_view_param list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one sys_db_view_param";
	
	// 修改记录
	// private static final String UPDATE_FUNCTION = "update one sys_db_view_param";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one sys_db_view_param";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete sys_db_view_param list";
	
	// 删除记录
	private static final String DELETE_ONE = "delete one sys_db_view_param";
	
	// 查询记录
	private static final String SELECT_ORDER_FUNCTION = "select next_order sys_db_view_param";
	
	/**
	 * 构造函数
	 */
	public TxnSysDbViewParam()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询数据视图参数列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn52102101( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, outputNode, -1);
		// 查询记录的条件 VoSysSvrServiceParamSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoSysSvrServiceParam result[] = context.getSysSvrServiceParams( outputNode );
	}
	
	/** 修改数据视图参数信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
//	public void txn50205002( TxnContext context ) throws TxnException
//	{
//		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
//		// 修改记录的内容 VoSysSvrServiceParam sys_db_view_param = context.getSysSvrServiceParam( inputNode );
//		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
//	}
	
	/** 增加数据视图参数信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn52102102( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoSysSvrServiceParam sys_db_view_param = context.getSysSvrServiceParam( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询数据视图参数用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn52102103( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysSvrServiceParamPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoSysSvrServiceParam result = context.getSysSvrServiceParam( outputNode );
	}
	
	/** 删除数据视图参数信息列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn52102104( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoSysSvrServiceParamPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 删除数据视图参数信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn52102105( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoSysSvrServiceParamPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_ONE, context, inputNode, outputNode );
	}
	
	/** 查询数据视图参数顺序
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn52102106( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoSysSvrServiceParamPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( SELECT_ORDER_FUNCTION, context, inputNode, outputNode );
	}
		
}
