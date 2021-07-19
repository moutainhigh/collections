package com.gwssi.sysmgr.priv.datapriv.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.sysmgr.priv.datapriv.vo.FuncdataobjectContext;

public class TxnFuncdataobject extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnFuncdataobject.class, FuncdataobjectContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "funcdataobject";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select funcdataobject list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "${mod.function.select}";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "${mod.function.update}";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one funcdataobject";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one funcdataobject";
	
	/**
	 * 构造函数
	 */
	public TxnFuncdataobject()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询配置功能关系列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn103021( FuncdataobjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoFuncdataobjectSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoFuncdataobject result[] = context.getFuncdataobjects( outputNode );
		System.out.println("");
	}
	
	/** 修改配置功能关系信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn103022( FuncdataobjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoFuncdataobject funcdataobject = context.getFuncdataobject( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加配置功能关系信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn103023( FuncdataobjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoFuncdataobject funcdataobject = context.getFuncdataobject( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询配置功能关系用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn103024( FuncdataobjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoFuncdataobjectPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoFuncdataobject result = context.getFuncdataobject( outputNode );
	}
	
	/** 删除配置功能关系信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn103025( FuncdataobjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoFuncdataobjectPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, "record", outputNode );
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
		FuncdataobjectContext appContext = new FuncdataobjectContext( context );
		invoke( method, appContext );
	}
}
