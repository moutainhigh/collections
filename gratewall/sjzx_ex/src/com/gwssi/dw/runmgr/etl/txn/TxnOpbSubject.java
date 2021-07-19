package com.gwssi.dw.runmgr.etl.txn;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.dw.runmgr.etl.vo.OpbSubjectContext;

public class TxnOpbSubject extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnOpbSubject.class, OpbSubjectContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "opb_subject";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select opb_subject list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one opb_subject";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one opb_subject";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one opb_subject";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one opb_subject";
	
	/**
	 * 构造函数
	 */
	public TxnOpbSubject()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询测试列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn50105001( OpbSubjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoOpbSubjectSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoOpbSubject result[] = context.getOpbSubjects( outputNode );
	}
	
	/** 修改测试信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn50105002( OpbSubjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoOpbSubject opb_subject = context.getOpbSubject( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加测试信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn50105003( OpbSubjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoOpbSubject opb_subject = context.getOpbSubject( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询测试用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn50105004( OpbSubjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoOpbSubjectPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoOpbSubject result = context.getOpbSubject( outputNode );
	}
	
	/** 删除测试信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn50105005( OpbSubjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoOpbSubjectPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/**
	 * 抽取服务管理查询列表
	 * @param context
	 * @throws TxnException
	 */
	public void txn50105006( OpbSubjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoOpbSubjectSelectKey selectKey = context.getSelectKey( inputNode );
		// TxnContext txnContext = new TxnContext();
		long before = java.util.Calendar.getInstance().getTimeInMillis();
		table.executeFunction( "loadOpbSubjectList", context, inputNode, outputNode );
		// 查询到的记录集 VoOpbSubject result[] = context.getOpbSubjects( outputNode );
		Recordset rs = context.getRecordset(outputNode);
		for (int i=0; i < rs.size(); i++){
			DataBus db = rs.get(i);
			String strWorkflowId = db.getValue("workflow_id");
			String dbuser = db.getValue("dbuser");
			TxnContext txnContextForSourceAndTarget = new TxnContext();
			txnContextForSourceAndTarget.getRecord("select-key").setValue("workflow_id", strWorkflowId);
			txnContextForSourceAndTarget.getRecord("select-key").setValue("dbuser", dbuser);
			table.executeFunction( "getSourceAndTarget", txnContextForSourceAndTarget, "select-key", "record" );
			String[] sourceAndTarget = generateSourceAndTarget(txnContextForSourceAndTarget);
			db.setValue("wf_db_source", sourceAndTarget[0]);
			db.setValue("wf_db_target", sourceAndTarget[1]);
		}
		long after = java.util.Calendar.getInstance().getTimeInMillis();
		System.out.println("系统共执行：" + (after-before) + "毫秒");
	}
	
	/**
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn50105007( OpbSubjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoOpbSubjectSelectKey selectKey = context.getSelectKey( inputNode );
		// TxnContext txnContext = new TxnContext();
		long before = java.util.Calendar.getInstance().getTimeInMillis();
		table.executeFunction( "loadWorkflowRunList", context, inputNode, outputNode );
		// System.out.println(context);
		Recordset rs  = context.getRecordset(outputNode);
		for (int i = 0; i < rs.size(); i++){
			DataBus db = rs.get(i);
			if (db.getValue("first_error_code").equals("0")){
				db.setValue("status", "成功");
			}else{
				db.setValue("status", "失败");
			}
		}
		long after = java.util.Calendar.getInstance().getTimeInMillis();
		System.out.println("系统共执行：" + (after-before) + "毫秒");
	}
	
	/**
	 * 查询日志详细信息
	 * @param context
	 * @throws TxnException
	 */
	public void txn50105008( OpbSubjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoOpbSubjectSelectKey selectKey = context.getSelectKey( inputNode );
		// TxnContext txnContext = new TxnContext();
		long before = java.util.Calendar.getInstance().getTimeInMillis();
		table.executeFunction( "loadWorkflowRunLog", context, inputNode, outputNode );
//		Recordset rs  = context.getRecordset(outputNode);
//		for (int i = 0; i < rs.size(); i++){
//			DataBus db = rs.get(i);
//			if (db.getValue("run_err_code").equals("0")){
//				db.setValue("status", "成功");
//			}else{
//				db.setValue("status", "失败");
//			}
//		}
		long after = java.util.Calendar.getInstance().getTimeInMillis();
		System.out.println("系统共执行：" + (after-before) + "毫秒");
	}
	
	/**
	 * 
	 * @param context
	 * @return
	 * @throws TxnException
	 */
	public String[] generateSourceAndTarget(TxnContext context) throws TxnException{
		String[] sourceAndTarget = new String[2];
		sourceAndTarget[0] = "";
		sourceAndTarget[1] = "";
		List sourceList = new ArrayList();
		List targetList = new ArrayList();
		Recordset rs = context.getRecordset("record");
		for (int i=0; i < rs.size(); i++)
		{
			DataBus db = rs.get(i);
			String isTarget = db.getString("is_target");
			if (isTarget.equals("1"))
			{
				String connection_name = db.getString("connection_name");
				boolean isExist = false;
				for (int n = 0; n < targetList.size() && isExist==false; n++){
					String temp = (String) targetList.get(n);
					if (connection_name.equals(temp)){
						isExist = true;
					}
				}
				if (isExist == false)
				{
					targetList.add(connection_name);
				}				
			}else if(isTarget.equals("0"))
			{
				String connection_name = db.getString("connection_name");
				boolean isExist = false;
				for (int n = 0; n < sourceList.size() && isExist==false; n++){
					String temp = (String) sourceList.get(n);
					if (connection_name.equals(temp)){
						isExist = true;
					}
				}
				if (isExist == false)
				{
					sourceList.add(connection_name);
				}
			}
		}
		sourceAndTarget[0] = sourceList.toString();
		sourceAndTarget[1] = targetList.toString();
		return sourceAndTarget;
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
		OpbSubjectContext appContext = new OpbSubjectContext( context );
		invoke( method, appContext );
	}
}
