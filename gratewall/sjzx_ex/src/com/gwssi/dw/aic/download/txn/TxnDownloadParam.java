package com.gwssi.dw.aic.download.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.dw.aic.download.vo.DownloadParamContext;

public class TxnDownloadParam extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnDownloadParam.class, DownloadParamContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "download_param";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select download_param list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one download_param";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one download_param";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one download_param";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one download_param";
	
	/**
	 * 构造函数
	 */
	public TxnDownloadParam()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询下载参数列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60400001( DownloadParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoDownloadParamSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoDownloadParam result[] = context.getDownloadParams( outputNode );
	}
	
	/** 修改下载参数信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60400002( DownloadParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoDownloadParam download_param = context.getDownloadParam( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加下载参数信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60400003( DownloadParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoDownloadParam download_param = context.getDownloadParam( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, "param", "param" );
	}
	
	/** 查询下载参数用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60400004( DownloadParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoDownloadParamPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoDownloadParam result = context.getDownloadParam( outputNode );
	}
	
	/** 删除下载参数信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60400005( DownloadParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoDownloadParamPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/**
	 * 获取字段全部信息
	 * @param context
	 * @throws TxnException
	 */
	public void txn60400006( DownloadParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoDownloadParamPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( "getColumnInfo", context, inputNode, outputNode );
		//System.out.println(context);
		TxnContext txnContext = new TxnContext();
		txnContext.getRecord(inputNode).setValue("download_status_id", 
				context.getRecord(inputNode).getValue("download_status_id"));
		this.callService("com.gwssi.dw.aic.download.txn.TxnDownloadStatus", "txn60300004", txnContext);
		//System.out.println(txnContext);
		context.addRecord("download", txnContext.getRecord(outputNode));
		// table.executeFunction("getDownloadInfo", context, inputNode, "download");
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
		DownloadParamContext appContext = new DownloadParamContext( context );
		invoke( method, appContext );
	}
}
