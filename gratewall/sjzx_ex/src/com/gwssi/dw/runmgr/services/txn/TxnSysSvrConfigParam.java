package com.gwssi.dw.runmgr.services.txn;

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
import com.gwssi.dw.runmgr.services.vo.SysSvrConfigParamContext;

public class TxnSysSvrConfigParam extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysSvrConfigParam.class, SysSvrConfigParamContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "sys_svr_config_param";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select sys_svr_config_param list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one sys_svr_config_param";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "${mod.function.update}";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one sys_svr_config_param";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one sys_svr_config_param";
	
	// 删除记录
	private static final String DELETE_LIST_FUNCTION = "delete config sys_svr_config_param";
	
	/**
	 * 构造函数
	 */
	public TxnSysSvrConfigParam()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询服务配置参数列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn50206001( SysSvrConfigParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, outputNode, -1);
		// 查询记录的条件 VoSysSvrConfigParamSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoSysSvrConfigParam result[] = context.getSysSvrConfigParams( outputNode );
	}
	
	/** 修改服务配置参数信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn50206002( SysSvrConfigParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoSysSvrConfigParam sys_svr_config_param = context.getSysSvrConfigParam( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加服务配置参数信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn50206003( SysSvrConfigParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoSysSvrConfigParam sys_svr_config_param = context.getSysSvrConfigParam( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询服务配置参数用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn50206004( SysSvrConfigParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysSvrConfigParamPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoSysSvrConfigParam result = context.getSysSvrConfigParam( outputNode );
	}
	
	/** 删除服务配置所有参数信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn50206005( SysSvrConfigParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoSysSvrConfigParamPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
		
	}
	
	/** 删除服务配置所有参数信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn50206006( SysSvrConfigParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoSysSvrConfigParamPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( "deleteConfigParam", context, inputNode, outputNode );
		
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
		SysSvrConfigParamContext appContext = new SysSvrConfigParamContext( context );
		invoke( method, appContext );
	}
}
