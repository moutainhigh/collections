package com.gwssi.sysmgr.download.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.sysmgr.download.vo.DownloadPurvContext;

public class TxnDownloadPurv extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnDownloadPurv.class, DownloadPurvContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "download_purv";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select download_purv list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one download_purv";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one download_purv";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one download_purv";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one download_purv";
	
	/**
	 * 构造函数
	 */
	public TxnDownloadPurv()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询下载设置列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn105001( DownloadPurvContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		//Attribute.setPageRow(context, "record", -1);
		table.executeFunction( ROWSET_FUNCTION, context, "select-key", "record" );
		Recordset rs = context.getRecordset("record");
		for(int i=0; i < rs.size(); i++){
			DataBus db = rs.get(i);
			db.setValue("jgmc", db.getValue("sjjgname") + db.getValue("jgmc"));
			db.setValue("expand", "true");
		}
	}
	
	/** 查询下载设置列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn1050011( DownloadPurvContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, "record", -1);
		table.executeFunction( ROWSET_FUNCTION, context, "select-key", "record" );
		Recordset rs = context.getRecordset("record");
		for(int i=0; i < rs.size(); i++){
			DataBus db = rs.get(i);
			db.setValue("jgmc", db.getValue("sjjgname") + db.getValue("jgmc"));
			db.setValue("expand", "true");
		}
	}
	
	/** 修改下载设置信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn105002( DownloadPurvContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoDownloadPurv download_purv = context.getDownloadPurv( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
		String agency_id = context.getRecord(inputNode).getValue("agency_id");
		TxnContext txnContext = new TxnContext();
		txnContext.getRecord("select-key").setValue("jgid_pk", agency_id);
		table.executeFunction("getOrgName", txnContext, "select-key", "record");
		context.getRecord("biz_log").setValue("desc", "修改 " + txnContext.getRecord("record").getValue("jgmc") + " 的下载权限");
	}
	
	/** 增加下载设置信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn105003( DownloadPurvContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoDownloadPurv download_purv = context.getDownloadPurv( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询下载设置用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn105004( DownloadPurvContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoDownloadPurvPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoDownloadPurv result = context.getDownloadPurv( outputNode );
	}
	
	/** 删除下载设置信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn105005( DownloadPurvContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoDownloadPurvPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	
	public void txn105006( DownloadPurvContext context ) throws TxnException
	{
		// 取得用户信息
		String agencyId = context.getRecord("oper-data").getValue("org-code");
		context.getRecord("select-key").setValue("agency_id", agencyId);
		this.callService("com.gwssi.sysmgr.download.txn.TxnDownloadPurv", "txn105001", context);
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
		DownloadPurvContext appContext = new DownloadPurvContext( context );
		invoke( method, appContext );
	}
}
