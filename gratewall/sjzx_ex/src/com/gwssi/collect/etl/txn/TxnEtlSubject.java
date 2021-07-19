package com.gwssi.collect.etl.txn;

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
import com.gwssi.collect.etl.vo.EtlSubjectContext;
import com.gwssi.collect.webservice.vo.CollectTaskContext;
import com.runqian.report4.model.expression.function.Count;

public class TxnEtlSubject extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnEtlSubject.class, EtlSubjectContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "etl_subject";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select etl_subject list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one etl_subject";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one etl_subject";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one etl_subject";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one etl_subject";
	
	/**
	 * 构造函数
	 */
	public TxnEtlSubject()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询etl任务列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30300001( EtlSubjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 修改etl任务信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30300002( EtlSubjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoEtlSubject etl_subject = context.getEtlSubject( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加etl任务信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30300003( EtlSubjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoEtlSubject etl_subject = context.getEtlSubject( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询etl任务用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30300004( EtlSubjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoEtlSubjectPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoEtlSubject result = context.getEtlSubject( outputNode );
	}
	
	/** 删除etl任务信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30300005( EtlSubjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoEtlSubjectPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/**
	 * 
	 * 查询ETL任务详细
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception 异常对象
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public void txn30300006(EtlSubjectContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("getEtlMinWorkRunId", context, inputNode, "minId");
		System.out.println(context.getRecord("minId"));
		table.executeFunction("getEtlWorkletTree", context, inputNode,
				outputNode);
		context.getRecord(inputNode).setValue("workflow_run_id",
				context.getRecord("minId").getValue("workflow_run_id"));
	}

	/**
	 * 
	 * 查询ETL子任务详细
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception 异常对象
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public void txn30300007(EtlSubjectContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction("getEtlTaskDetail", context, inputNode,outputNode);
	}
	
	/**
	 * 
	 * txn30300008(这里用一句话描述这个方法的作用)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param context
	 * @throws TxnException        
	 * void       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public void txn30300008( EtlSubjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoEtlSubjectPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		//table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		table.executeFunction( "getEtlInfo", context, inputNode, outputNode );
		Attribute.setPageRow(context, "subject", -1);
		table.executeFunction("getCollectTableDetail", context, inputNode,"subject");
		Attribute.setPageRow(context, "subjectNum", -1);
		table.executeFunction("getSubjectNum", context, inputNode,"subjectNum");
		//System.out.println("size=="+context.getRecordset("subject").size());
		//System.out.println("size=="+context.getRecordset("subjectNum").size());
		// 查询到的记录内容 VoEtlSubject result = context.getEtlSubject( outputNode );
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
		EtlSubjectContext appContext = new EtlSubjectContext( context );
		invoke( method, appContext );
	}
}
