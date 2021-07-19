package com.gwssi.dw.runmgr.etl.txn;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.common.util.CSDBConfig;
import com.gwssi.dw.runmgr.etl.vo.OpbSchedulerContext;

public class TxnOpbScheduler extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnOpbScheduler.class, OpbSchedulerContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "opb_scheduler";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select opb_scheduler list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one opb_scheduler";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one opb_scheduler";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one opb_scheduler";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one opb_scheduler";
	
	/**
	 * 构造函数
	 */
	public TxnOpbScheduler()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询调度列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn50106001( OpbSchedulerContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoOpbSchedulerSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoOpbScheduler result[] = context.getOpbSchedulers( outputNode );
	}
	
	/** 修改调度信息
	 * @param context 交易上下文
	 * @throws TxnException
	 * @throws IOException 
	 */
	public void txn50106002( OpbSchedulerContext context ) throws TxnException, IOException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoOpbScheduler opb_scheduler = context.getOpbScheduler( inputNode );
		table.executeFunction( "updateSchedulerInfoByDbuser", context, inputNode, outputNode );
	}
	
	/** 增加调度信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn50106003( OpbSchedulerContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoOpbScheduler opb_scheduler = context.getOpbScheduler( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询调度用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn50106004( OpbSchedulerContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoOpbSchedulerPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoOpbScheduler result = context.getOpbScheduler( outputNode );
	}
	
	/** 删除调度信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn50106005( OpbSchedulerContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoOpbSchedulerPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/**
	 * 根据WorkFlow查询Scheduler信息
	 * @param context
	 * @throws TxnException
	 */
	public void txn50106006( OpbSchedulerContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoOpbSchedulerPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( "getSchedulerByWorkFlowId", context, inputNode, outputNode );
	}
	
	public void txn50106007( OpbSchedulerContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoOpbSchedulerPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		// 设置只取一条记录
		Attribute.setPageRow(context, "record", 1);
		table.executeFunction( "getWorkFlowExecInfo", context, "select-key", "record" );
	}
	
	public void txn50106008( OpbSchedulerContext context ) throws TxnException, IOException, InterruptedException
	{
		DataBus dataBus = context.getRecord(inputNode);
		String wf_name = dataBus.getValue("wf_name");
		String rep_foldername = dataBus.getValue("rep_foldername");
		String domain_name = dataBus.getValue("domain_name");
		String server_name = dataBus.getValue("server_name");
		String rootPath = CSDBConfig.get("pmcmdFilePath");
		String pcSrvBinPath = CSDBConfig.get("pcSrvBinPath");
		String command = "\""+rootPath+"\\pmcmdScheduler.bat\" " + 
			rep_foldername + " " + wf_name + " " + domain_name + " " + server_name+ 
			" " + pcSrvBinPath + " " + pcSrvBinPath.substring(0, 2); 
		System.out.println("执行命令："+ command);
		Process p = Runtime.getRuntime().exec(command);
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
		OpbSchedulerContext appContext = new OpbSchedulerContext( context );
		invoke( method, appContext );
	}
}
