package com.gwssi.dw.aic.bj.homepage.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.dw.aic.bj.homepage.vo.WorkStatusContext;

public class TxnWorkStatus extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnWorkStatus.class, WorkStatusContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "work_status";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select work_status list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one work_status";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one work_status";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one work_status";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one work_status";
	
	/**
	 * 构造函数
	 */
	public TxnWorkStatus()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询待办任务列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn54000001( WorkStatusContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		pubDestroy(table, context);
		this.callService("com.gwssi.dw.aic.download.txn.TxnDownloadCheckPurv", "txn60600006", context);
		Attribute.setPageRow(context, "ws-record", 20);
		table.executeFunction( "queryTotalData", context, "ws-query", "ws-record" );
		//System.out.println("----"+context.getRecordset(""));
	}
	
	/** 修改待办任务信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn54000002( WorkStatusContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoWorkStatus work_status = context.getWorkStatus( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, "ws-record", "ws-record" );
	}
	
	/** 增加待办任务信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn54000003( WorkStatusContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoWorkStatus work_status = context.getWorkStatus( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, "record", "record" );
	}
	
	/** 查询待办任务用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn54000004( WorkStatusContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoWorkStatusPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoWorkStatus result = context.getWorkStatus( outputNode );
	}
	
	/** 删除待办任务信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn54000005( WorkStatusContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoWorkStatusPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
		
	
	/**
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn54000006( WorkStatusContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );	
		pubDestroy(table, context);
		this.callService("com.gwssi.dw.aic.download.txn.TxnDownloadCheckPurv", "txn60600006", context);
		Attribute.setPageRow(context, "ws-record", 10);
		table.executeFunction( "queryTotalData", context, "ws-query", "ws-record" );
	}
	
	/**
	 * 获取待办的个数
	 * @param context
	 * @throws TxnException
	 */
	public void txn54000007( WorkStatusContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		pubDestroy(table, context);
		this.callService("com.gwssi.dw.aic.download.txn.TxnDownloadCheckPurv", "txn60600006", context);
		Attribute.setPageRow(context, "ws-record", 20);
		table.executeFunction( "queryTotalDataCount", context, "ws-query", "record" );
		context.getRecordset("downloadCheckPurv").clear();
		context.getRecordset("ws-query").clear();
		//System.out.println("----"+context);
	}
	
	/**
	 * 
	 * @param table
	 * @param context
	 * @throws TxnException
	 */
	public void pubDestroy( BaseTable table, WorkStatusContext context ) throws TxnException{
		String roleList = context.getRecord("oper-data").getValue("role-list");
//		System.out.println("转换前:" + roleList);
		roleList = "'" + roleList.replaceAll(";", "','") + "'";
//		System.out.println("转换后:" + roleList);
		TxnContext txnContext = new TxnContext();
		txnContext.getRecord("select-key").setValue("roleList", roleList);
		table.executeFunction("getRoleList", txnContext, "select-key", "record");
		Recordset rs = txnContext.getRecordset("record");
		String totalList = "";
		for (int i=0; i < rs.size(); i++){
			String temp = rs.get(i).getValue("funclist").trim();
			if(temp != null && !temp.equals("")){
				temp = temp.substring(temp.length()-1).equals(";") ? temp : temp+";";
			}
			totalList += temp;
		}
//		System.out.println("全部功能权限：" + totalList);
		String inStr = totalList.replaceAll(";", "','");
		inStr = "'" + inStr.substring(0, inStr.length() - 2);
//		System.out.println("全部功能权限：" + inStr);
		
		// 设置只读取有效标识为1的数据
		context.getRecord("ws-query").setValue("funclist", inStr);
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
		WorkStatusContext appContext = new WorkStatusContext( context );
		invoke( method, appContext );
	}
}
