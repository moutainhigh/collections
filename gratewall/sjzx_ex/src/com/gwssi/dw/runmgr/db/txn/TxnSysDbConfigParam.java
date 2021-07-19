package com.gwssi.dw.runmgr.db.txn;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

public class TxnSysDbConfigParam extends TxnService
{
	// 数据表名称
	private static final String TABLE_NAME = "sys_db_config_param";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select sys_db_config_param list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one sys_db_config_param";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "${mod.function.update}";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one sys_db_config_param";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one sys_db_config_param";
	
	// 删除记录
	private static final String DELETE_LIST_FUNCTION = "delete config sys_db_config_param";
	
	/**
	 * 构造函数
	 */
	public TxnSysDbConfigParam()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询视图配置参数列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn52103101( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, outputNode, -1);
		// 查询记录的条件 VoSysSvrConfigParamSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoSysSvrConfigParam result[] = context.getSysSvrConfigParams( outputNode );
	}
	
	/** 修改视图配置参数信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn52103102( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoSysSvrConfigParam sys_svr_config_param = context.getSysSvrConfigParam( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加视图配置参数信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn52103103( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoSysSvrConfigParam sys_svr_config_param = context.getSysSvrConfigParam( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询视图配置参数用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn52103104( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysSvrConfigParamPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoSysSvrConfigParam result = context.getSysSvrConfigParam( outputNode );
	}
	
	/** 删除视图配置参数信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn52103105( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoSysSvrConfigParamPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 删除视图配置所有参数信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn52103106( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoSysSvrConfigParamPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_LIST_FUNCTION, context, inputNode, outputNode );
	}

}
