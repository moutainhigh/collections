package com.gwssi.log.systemlog.txn;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.common.util.JsonDataUtil;
import com.gwssi.log.sharelog.vo.ShareLogContext;
import com.gwssi.log.systemlog.vo.FirstPageQuerySystemlogContext;

public class TxnFirstPageQuery extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnFirstPageQuery.class, FirstPageQuerySystemlogContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "first_page_query_sl";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select first_page_query_sl list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one first_page_query_sl";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one first_page_query_sl";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one first_page_query_sl";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one first_page_query_sl";
	
	/**
	 * 构造函数
	 */
	public TxnFirstPageQuery()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	/** 查询系统日志列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn601011( FirstPageQuerySystemlogContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		System.out.println("txn601011");
		String opera_time = context.getRecord("select-key").getValue("created_time");
		
		FirstPageQuerySystemlogContext usercontext = new FirstPageQuerySystemlogContext();
		Attribute.setPageRow(usercontext, outputNode, -1);
		table.executeFunction("getUserInfo", usercontext, inputNode,
				outputNode);
		Recordset userRs = usercontext.getRecordset("record");
		context.setValue("username", JsonDataUtil.getJsonByRecordSet(userRs));
		
		
		// 查询记录的条件 VoFirstPageQuerySystemlogSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( "querySystemLogList", context, inputNode, outputNode );
		// 查询到的记录集 VoFirstPageQuerySystemlog result[] = context.getFirstPageQuerySystemlogs( outputNode );
	}
	
	
	
	
	/** 查询系统日志列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn601091( FirstPageQuerySystemlogContext context ) throws TxnException
	{
		
		
	}
	
	
	
	public void txn601092( FirstPageQuerySystemlogContext context ) throws TxnException
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
	
		table.executeFunction( "querySystemLogList", context, inputNode, outputNode );
		
	}
	/** 修改系统日志信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn601012( FirstPageQuerySystemlogContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoFirstPageQuerySystemlog first_page_query = context.getFirstPageQuerySystemlog( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加系统日志信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn601013( FirstPageQuerySystemlogContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoFirstPageQuerySystemlog first_page_query = context.getFirstPageQuerySystemlog( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询系统日志用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn601014( FirstPageQuerySystemlogContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoFirstPageQuerySystemlogPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoFirstPageQuerySystemlog result = context.getFirstPageQuerySystemlog( outputNode );
	}
	
	/** 删除系统日志信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn601015( FirstPageQuerySystemlogContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoFirstPageQuerySystemlogPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	/** 跳转系统日志信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn601016( FirstPageQuerySystemlogContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoFirstPageQuerySystemlogPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( "querySystemLog", context, inputNode, outputNode );
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
		FirstPageQuerySystemlogContext appContext = new FirstPageQuerySystemlogContext( context );
		invoke( method, appContext );
	}
}
