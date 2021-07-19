package com.gwssi.collect.ftp.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.collect.ftp.vo.CollectFtpTaskContext;

public class TxnCollectFtpTask extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnCollectFtpTask.class, CollectFtpTaskContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "collect_ftp_task";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select collect_ftp_task list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one collect_ftp_task";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one collect_ftp_task";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one collect_ftp_task";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one collect_ftp_task";
	
	/**
	 * 构造函数
	 */
	public TxnCollectFtpTask()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询ftp任务列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30201001( CollectFtpTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoCollectFtpTaskSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoCollectFtpTask result[] = context.getCollectFtpTasks( outputNode );
	}
	
	/** 修改ftp任务信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30201002( CollectFtpTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoCollectFtpTask collect_ftp_task = context.getCollectFtpTask( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加ftp任务信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30201003( CollectFtpTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoCollectFtpTask collect_ftp_task = context.getCollectFtpTask( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询ftp任务用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30201004( CollectFtpTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoCollectFtpTaskPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoCollectFtpTask result = context.getCollectFtpTask( outputNode );
		
		String service_targets_id=context.getRecord("primary-key").getValue("service_targets_id");//服务对象ID
		if(service_targets_id!=null&&!"".equals(service_targets_id)){
			context.getRecord("record").setValue("service_targets_id", service_targets_id);
		}
	}
	
	/** 删除ftp任务信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30201005( CollectFtpTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoCollectFtpTaskPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( "deleteFile", context, inputNode, outputNode );
		
		this.callService("30101013", context);
	}
	
	/** 查询ftp任务用于查看
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30201006( CollectFtpTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoCollectFtpTaskPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoCollectFtpTask result = context.getCollectFtpTask( outputNode );
	
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
		CollectFtpTaskContext appContext = new CollectFtpTaskContext( context );
		invoke( method, appContext );
	}
}
