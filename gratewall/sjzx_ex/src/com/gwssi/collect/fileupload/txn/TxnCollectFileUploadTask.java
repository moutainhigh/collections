package com.gwssi.collect.fileupload.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.collect.fileupload.vo.CollectFileUploadTaskContext;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.util.CalendarUtil;
import com.gwssi.common.util.UuidGenerator;

public class TxnCollectFileUploadTask extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnCollectFileUploadTask.class, CollectFileUploadTaskContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "collect_file_upload_task";
	// 数据表名称
	private static final String TABLE_NAME_TASK = "collect_task";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select collect_file_upload_task list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one collect_file_upload_task";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one collect_file_upload_task";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one collect_file_upload_task";
	// 增加记录
	private static final String INSERT_FUNCTION_TASK = "insert one collect_task";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one collect_file_upload_task";
	
	/**
	 * 构造函数
	 */
	public TxnCollectFileUploadTask()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询文件上传采集表列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30301001( CollectFileUploadTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoCollectFileUploadTaskSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoCollectFileUploadTask result[] = context.getCollectFileUploadTasks( outputNode );
	}
	
	/** 修改文件上传采集表信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30301002( CollectFileUploadTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoCollectFileUploadTask collect_file_upload_task = context.getCollectFileUploadTask( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加文件上传采集表信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30301003( CollectFileUploadTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		BaseTable table_task = TableFactory.getInstance().getTableObject( this, TABLE_NAME_TASK );
		// 增加记录的内容 VoCollectFileUploadTask collect_file_upload_task = context.getCollectFileUploadTask( inputNode );
		
		String COLLECT_TASK_ID = UuidGenerator.getUUID();//采集任务id
		String FILE_UPLOAD_TASK_ID = UuidGenerator.getUUID();//文件上传任务ID
		context.getRecord("record").setValue("collect_task_id", COLLECT_TASK_ID);
		context.getRecord("record").setValue("file_upload_task_id", FILE_UPLOAD_TASK_ID);
		
		context.getRecord("record").setValue("created_time", CalendarUtil.getCurrentDateTime());
		context.getRecord("record").setValue("is_markup", ExConstant.IS_MARKUP_Y);//引入常量
		context.getRecord("record").setValue("creator_id", context.getRecord("oper-data").getValue("userID"));
		context.getRecord("record").setValue("task_status",ExConstant.SERVICE_STATE_Y);// 引入常量  启用
		
		table_task.executeFunction(INSERT_FUNCTION_TASK, context, inputNode, outputNode);
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询文件上传采集表用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30301004( CollectFileUploadTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoCollectFileUploadTaskPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoCollectFileUploadTask result = context.getCollectFileUploadTask( outputNode );
	}
	
	/** 删除文件上传采集表信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30301005( CollectFileUploadTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoCollectFileUploadTaskPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
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
		CollectFileUploadTaskContext appContext = new CollectFileUploadTaskContext( context );
		invoke( method, appContext );
	}
}
