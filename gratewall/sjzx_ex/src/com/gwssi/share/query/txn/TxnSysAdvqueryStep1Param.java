package com.gwssi.share.query.txn;

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

import com.gwssi.share.query.vo.SysAdvqueryStep1ParamContext;

public class TxnSysAdvqueryStep1Param extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysAdvqueryStep1Param.class, SysAdvqueryStep1ParamContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "sys_advquery_step1_param";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select sys_advquery_step1_param list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one sys_advquery_step1_param";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "${mod.function.update}";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one sys_advquery_step1_param";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one sys_advquery_step1_param";
	
	// 删除记录
	private static final String DELETE_ROWSET_FUNCTION = "delete sys_advquery_step1_param list";
	
	/**
	 * 构造函数
	 */
	public TxnSysAdvqueryStep1Param()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询自定义查询步骤一列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6022201( SysAdvqueryStep1ParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow( context, outputNode, -1 );
		// 查询记录的条件 VoSysAdvqueryStep1ParamSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoSysAdvqueryStep1Param result[] = context.getSysAdvqueryStep1Params( outputNode );
	}
	
	/** 修改自定义查询步骤一信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6022202( SysAdvqueryStep1ParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoSysAdvqueryStep1Param sys_advquery_step1_param = context.getSysAdvqueryStep1Param( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加自定义查询步骤一信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6022203( SysAdvqueryStep1ParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoSysAdvqueryStep1Param sys_advquery_step1_param = context.getSysAdvqueryStep1Param( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询自定义查询步骤一用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6022204( SysAdvqueryStep1ParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysAdvqueryStep1ParamPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoSysAdvqueryStep1Param result = context.getSysAdvqueryStep1Param( outputNode );
	}
	
	/** 删除自定义查询步骤一信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6022205( SysAdvqueryStep1ParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoSysAdvqueryStep1ParamPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 删除某自定义查询下所有的步骤一参数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6022206( SysAdvqueryStep1ParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoSysAdvqueryStep1ParamPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_ROWSET_FUNCTION, context, inputNode, outputNode );
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
		SysAdvqueryStep1ParamContext appContext = new SysAdvqueryStep1ParamContext( context );
		invoke( method, appContext );
	}
}
