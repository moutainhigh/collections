package com.gwssi.log.report.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import cn.gwssi.dw.rd.log.vo.LogReportUseContext;

public class TxnLogReportUse extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnLogReportUse.class, LogReportUseContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "log_report_use";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select log_report_use list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one log_report_use";
	
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one log_report_use";
	
	
	/**
	 * 构造函数
	 */
	public TxnLogReportUse()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	

	/** 查询报告操作情况列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn620200201( LogReportUseContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoLogReportUseSelectKey selectKey = context.getSelectKey( inputNode );
		//table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		table.executeFunction( "queryLogReportUse", context, inputNode, outputNode );
		// 查询到的记录集 VoLogReportUse result[] = context.getLogReportUses( outputNode );
	}
	
	/** 查询报告操作情况列
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn620200291( LogReportUseContext context ) throws TxnException
	{
		
	}
	/** 查询报告操作情况列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn620200292( LogReportUseContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoShareServiceSelectKey selectKey = context.getSelectKey( inputNode );
		String create_time = context.getRecord("select-key").getValue("created_time");
		if(StringUtils.isNotBlank(create_time)){
			String [] ctime = com.gwssi.common.util.DateUtil.getDateRegionByDatePicker(create_time, true);
			context.getRecord("select-key").setValue("created_time_start", ctime[0]);
			context.getRecord("select-key").setValue("created_time_end", ctime[1]);
			context.getRecord("select-key").remove("created_time");
		}
	
		table.executeFunction( "queryLogReportUse", context, inputNode, outputNode );
		// 查询到的记录集 VoLogReportUse result[] = context.getLogReportUses( outputNode );
	}
	
	/** 增加报告操作情况信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn620200203( LogReportUseContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoLogReportUse log_report_use = context.getLogReportUse( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
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
		LogReportUseContext appContext = new LogReportUseContext( context );
		invoke( method, appContext );
	}
}
