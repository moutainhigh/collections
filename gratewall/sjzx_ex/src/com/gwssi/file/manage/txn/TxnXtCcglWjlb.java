package com.gwssi.file.manage.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.file.manage.vo.XtCcglWjlbContext;

public class TxnXtCcglWjlb extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnXtCcglWjlb.class, XtCcglWjlbContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "xt_ccgl_wjlb";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select xt_ccgl_wjlb list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one xt_ccgl_wjlb";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one xt_ccgl_wjlb";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one xt_ccgl_wjlb";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one xt_ccgl_wjlb";
	
	/**
	 * 构造函数
	 */
	public TxnXtCcglWjlb()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询文件类别列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn604050101( XtCcglWjlbContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoXtCcglWjlbSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoXtCcglWjlb result[] = context.getXtCcglWjlbs( outputNode );
	}
	
	/** 修改文件类别信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn604050102( XtCcglWjlbContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoXtCcglWjlb xt_ccgl_wjlb = context.getXtCcglWjlb( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加文件类别信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn604050103( XtCcglWjlbContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoXtCcglWjlb xt_ccgl_wjlb = context.getXtCcglWjlb( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询文件类别用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn604050104( XtCcglWjlbContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoXtCcglWjlbPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoXtCcglWjlb result = context.getXtCcglWjlb( outputNode );
	}
	
	/** 删除文件类别信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn604050105( XtCcglWjlbContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoXtCcglWjlbPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
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
		XtCcglWjlbContext appContext = new XtCcglWjlbContext( context );
		invoke( method, appContext );
	}
}
