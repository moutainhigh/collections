package com.gwssi.collect.webservice.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

import com.gwssi.common.constant.CollectConstants;

public class CollectWebserviceTask extends BaseTable
{
	public CollectWebserviceTask()
	{

	}

	/**
	 * 注册SQL语句
	 */
	protected void register()
	{
		registerSQLFunction("getTaskInfo", DaoFunction.SQL_SELECT, "获取任务详细信息");
		registerSQLFunction("getWsMethodInfo", DaoFunction.SQL_SELECT,
				"获取方法详细信息");
		registerSQLFunction("getParamByFuncID", DaoFunction.SQL_ROWSET,
				"获取方法对应方参数");
		registerSQLFunction("deleteFunc", DaoFunction.SQL_DELETE, "删除方法对应参数");
//		registerSQLFunction("deleteParam", DaoFunction.SQL_DELETE, "删除方法对应参数");
	}

	/**
	 * 执行SQL语句前的处理
	 */
	public void prepareExecuteStmt(DaoFunction func, TxnContext request,
			DataBus[] inputData, String outputNode) throws TxnException
	{

	}

	/**
	 * 执行完SQL语句后的处理
	 */
	public void afterExecuteStmt(DaoFunction func, TxnContext request,
			DataBus[] inputData, String outputNode) throws TxnException
	{

	}

	public SqlStatement getTaskInfo(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String task_id = request.getRecord("select-key").getValue(
				"collect_task_id");// 方法ID
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer
				.append("select * from collect_task t where t.collect_task_id = '"
						+ task_id + "'");
		stmt.addSqlStmt(sqlBuffer.toString());
		return stmt;
	}

	/**
	 * 
	 * getWsMethodInfo 获取方法详细信息
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public SqlStatement getWsMethodInfo(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String webservice_task_id = request.getRecord("select-key").getValue(
				"webservice_task_id");// 方法ID

		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.*, c.service_targets_id");
		sqlBuffer.append(" from collect_webservice_task t, collect_task c");
		sqlBuffer.append(" where t.collect_task_id = c.collect_task_id");
		sqlBuffer.append("  and t.webservice_task_id = ");
		sqlBuffer.append("'");
		sqlBuffer.append(webservice_task_id);
		sqlBuffer.append("'");
//		sqlBuffer.append("  and t.method_status = ");
//		sqlBuffer.append("'");
//		sqlBuffer.append(CollectConstants.TYPE_QY);
//		sqlBuffer.append("'");
		System.out.println("getWsMethodInfo sqlBuffer is" + sqlBuffer);
		stmt.addSqlStmt(sqlBuffer.toString());
		stmt.setCountStmt("select count(1) from ("+sqlBuffer.toString()+")");
		return stmt;
	}

	/**
	 * getParamByFuncID 根据方法查询对应所有参数
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public SqlStatement getParamByFuncID(TxnContext request, DataBus inputData)
	{
		String webservice_task_id = request.getRecord("select-key").getValue(
				"webservice_task_id");// 方法ID
		System.out.println("webservice_task_id" + webservice_task_id);
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select * from collect_webservice_patameter t ");
		if (webservice_task_id != null) {
			sqlBuffer.append(" where t.webservice_task_id= '"
					+ webservice_task_id + "'");
		}
		System.out.println("根据采集方法ID查询对应所有参数       sql==="
				+ sqlBuffer.toString());
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		stmt
				.setCountStmt("select count(1) from (" + sqlBuffer.toString()
						+ ")");
		return stmt;
	}

	/**
	 * deleteParamItem 删除方法对应参数
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @since CodingExample Ver(编码范例查看) 1.1
	 * 
	 * public SqlStatement deleteParamItem(TxnContext request, DataBus
	 * inputData) { String webservice_task_id =
	 * request.getRecord("primary-key").getValue("webservice_task_id");//方法ID
	 * 
	 * StringBuffer sqlBuffer = new StringBuffer(); SqlStatement stmt = new
	 * SqlStatement();
	 * if(webservice_task_id!=null&&!"".equals(webservice_task_id)){
	 * sqlBuffer.append("delete from collect_webservice_patameter where
	 * webservice_task_id = '"+webservice_task_id+"'");//删除参数表
	 * stmt.addSqlStmt(sqlBuffer.toString()); } return stmt; }
	 */

	/**
	 * deleteFunc 删除方法
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public SqlStatement deleteFunc(TxnContext request, DataBus inputData)
	{
		String webservice_task_id = request.getRecord("primary-key").getValue(
				"webservice_task_id");// 方法ID

		StringBuffer sqlBuffer = new StringBuffer();
		SqlStatement stmt = new SqlStatement();
		if (webservice_task_id != null && !"".equals(webservice_task_id)) {
//			sqlBuffer
//					.append("update collect_webservice_task set method_status ='"
//							+ CollectConstants.TYPE_TY + "'");
			sqlBuffer.append("delete from collect_webservice_task ");
			sqlBuffer.append(" where webservice_task_id  = '"
					+ webservice_task_id + "'");// 删除方法状态为停用
			stmt.addSqlStmt(sqlBuffer.toString());
		}
		return stmt;
	}
	
}
