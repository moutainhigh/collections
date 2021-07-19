package com.gwssi.dw.aic.download.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.dw.aic.download.vo.DownloadCheckPurvContext;

public class TxnDownloadCheckPurv extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnDownloadCheckPurv.class, DownloadCheckPurvContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "download_check_purv";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select download_check_purv list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one download_check_purv";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one download_check_purv";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one download_check_purv";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one download_check_purv";
	
	/**
	 * 构造函数
	 */
	public TxnDownloadCheckPurv()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询审批权限列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60600001( DownloadCheckPurvContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoDownloadCheckPurvSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoDownloadCheckPurv result[] = context.getDownloadCheckPurvs( outputNode );
	}
	
	/** 修改审批权限信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60600002( DownloadCheckPurvContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoDownloadCheckPurv download_check_purv = context.getDownloadCheckPurv( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加审批权限信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60600003( DownloadCheckPurvContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoDownloadCheckPurv download_check_purv = context.getDownloadCheckPurv( inputNode );
		System.out.println("context:" + context);
		DataBus db = context.getRecord(inputNode);
		String roleid = db.getValue("roleid");
		String[] rolesArray = null;
		if (roleid != null){
			rolesArray = roleid.split(",");
		}
		String min_size = db.getValue("min_size");
		String max_size = db.getValue("max_size");
		String download_purv_id = db.getValue("download_purv_id");
		String multi_download_check = db.getValue("multi_download_check");
		
		for (int i = 0; i < rolesArray.length; i++){
			if (rolesArray[i] != null && !rolesArray[i].trim().equals("")){
				DataBus insertDb = new DataBus();
				insertDb.setValue("download_check_purv_id", com.gwssi.common.util.UuidGenerator.getUUID());
				insertDb.setValue("roleid", rolesArray[i].trim());
				insertDb.setValue("max_size", max_size);
				insertDb.setValue("min_size", min_size);
				insertDb.setValue("download_purv_id", download_purv_id);
				insertDb.setValue("multi_download_check", multi_download_check);
				context.addRecord("insert", insertDb);
			}
		}
		table.executeFunction( INSERT_FUNCTION, context, "insert", outputNode );
	}
	
	/** 查询审批权限用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60600004( DownloadCheckPurvContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoDownloadCheckPurvPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoDownloadCheckPurv result = context.getDownloadCheckPurv( outputNode );
	}
	
	/** 删除审批权限信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60600005( DownloadCheckPurvContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoDownloadCheckPurvPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
		
	/**
	 * 获取角色的下载审批权限信息
	 * @param context
	 * @throws TxnException
	 */
	public void txn60600006( DownloadCheckPurvContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String role_list = context.getRecord("oper-data").getValue("role-list");
		role_list = role_list.replaceAll(";", "','");
		role_list = "'" + role_list + "'";
		DataBus db = new DataBus();
		db.setValue("role-list", role_list);
		context.addRecord("roleInfo", db);
		Attribute.setPageRow(context, "downloadCheckPurv", -1);
		table.executeFunction( "getDownloadCheckPurvInfo", context, "roleInfo", "downloadCheckPurv" );
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
		DownloadCheckPurvContext appContext = new DownloadCheckPurvContext( context );
		invoke( method, appContext );
	}
}
