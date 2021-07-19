package com.gwssi.dw.aic.download.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.dw.aic.download.vo.DownloadColumnContext;

public class TxnDownloadColumn extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnDownloadColumn.class, DownloadColumnContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "download_column";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select download_column list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one download_column";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one download_column";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one download_column";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one download_column";
	
	/**
	 * 构造函数
	 */
	public TxnDownloadColumn()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询字段列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60501001( DownloadColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoDownloadColumnSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoDownloadColumn result[] = context.getDownloadColumns( outputNode );
	}
	
	/** 修改字段信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60501002( DownloadColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoDownloadColumn download_column = context.getDownloadColumn( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加字段信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60501003( DownloadColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoDownloadColumn download_column = context.getDownloadColumn( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询字段用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60501004( DownloadColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoDownloadColumnPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoDownloadColumn result = context.getDownloadColumn( outputNode );
	}
	
	/** 删除字段信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60501005( DownloadColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoDownloadColumnPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
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
		DownloadColumnContext appContext = new DownloadColumnContext( context );
		invoke( method, appContext );
	}
}
