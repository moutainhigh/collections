package com.gwssi.collect.database.txn;

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
import com.gwssi.collect.database.vo.CollectDatabaseTaskContext;
import com.gwssi.common.util.CalendarUtil;
import com.gwssi.common.util.UuidGenerator;

public class TxnCollectDatabaseTask extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnCollectDatabaseTask.class, CollectDatabaseTaskContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "collect_database_task";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select collect_database_task list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one collect_database_task";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one collect_database_task";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one collect_database_task";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one collect_database_task";
	
	/**
	 * 构造函数
	 */
	public TxnCollectDatabaseTask()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询采集数据库列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30501001( CollectDatabaseTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoCollectDatabaseTaskSelectKey selectKey = context.getSelectKey( inputNode );
		String taskId = context.getRecord("select-key").getValue("collect_task_id");
		System.out.println("数据库采集taskId is " + taskId);
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoCollectDatabaseTask result[] = context.getCollectDatabaseTasks( outputNode );
	}
	
	/** 修改采集数据库信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30501002( CollectDatabaseTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoCollectDatabaseTask collect_database_task = context.getCollectDatabaseTask( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加采集数据库信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30501003( CollectDatabaseTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoCollectDatabaseTask collect_database_task = context.getCollectDatabaseTask( inputNode );
		context.getRecord("record").setValue("created_time", CalendarUtil.getCurrentDateTime());
		context.getRecord("record").setValue("database_task_id", UuidGenerator.getUUID());
		System.out.println("增加context="+context);
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询采集数据库用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30501004( CollectDatabaseTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoCollectDatabaseTaskPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		String task_name = context.getRecord("primary-key").getValue("task_name");
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoCollectDatabaseTask result = context.getCollectDatabaseTask( outputNode );
		context.getRecord("record").setValue("task_name", task_name);
	}
	
	/** 删除采集数据库信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30501005( CollectDatabaseTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoCollectDatabaseTaskPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
		this.callService("30101043", context);
	}
	
	/** 校验是否任务重复
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30501006( CollectDatabaseTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoCollectDatabaseTaskPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		//table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
		//table.executeFunction("queryCollectTaskInfo", context, inputNode, "tableinfo");//数据表信息
		table.executeFunction("queryTasknum", context, inputNode, outputNode);//数据项列表
	}
	/** 查询采集数据库信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30501007( CollectDatabaseTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoCollectDatabaseTaskPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		//table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
		//table.executeFunction("queryCollectTaskInfo", context, inputNode, "tableinfo");//数据表信息
		table.executeFunction("queryCollectTaskInfo", context, inputNode, outputNode);//数据项列表
	}
	/** 查询采集数据库信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30501009( CollectDatabaseTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		//table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
		//table.executeFunction("queryCollectTaskInfo", context, inputNode, "tableinfo");//数据表信息
		table.executeFunction("queryCollectDataBaseTaskInfo", context, inputNode, outputNode);//数据项列表
	}
	
	public void txn30501010( CollectDatabaseTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoCollectDatabaseTaskSelectKey selectKey = context.getSelectKey( inputNode );
		String taskId = context.getRecord("select-key").getValue("collect_task_id");
		System.out.println("数据库采集taskId is " + taskId);
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction( "queryCollectDatabseTreeInfo", context, inputNode, outputNode );
		// 查询到的记录集 VoCollectDatabaseTask result[] = context.getCollectDatabaseTasks( outputNode );
	}
	
	public void txn30501011( CollectDatabaseTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		//table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
		//table.executeFunction("queryCollectTaskInfo", context, inputNode, "tableinfo");//数据表信息
		table.executeFunction("queryCollectDataBaseTaskInfoForStep", context, inputNode, outputNode);//数据项列表
	}
	
	/** 删除采集数据库信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30501013( CollectDatabaseTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoCollectDatabaseTaskPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
		this.callService("30101042", context);
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
		CollectDatabaseTaskContext appContext = new CollectDatabaseTaskContext( context );
		invoke( method, appContext );
	}
}
