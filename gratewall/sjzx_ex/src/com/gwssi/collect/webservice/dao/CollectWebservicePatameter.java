package com.gwssi.collect.webservice.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

public class CollectWebservicePatameter extends BaseTable
{
	public CollectWebservicePatameter()
	{

	}

	/**
	 * 注册SQL语句
	 */
	protected void register()
	{
		registerSQLFunction("update_style", DaoFunction.SQL_UPDATE, "修改参数格式");//
		registerSQLFunction("queryParamValueById", DaoFunction.SQL_ROWSET, "查询参数值列表");
		registerSQLFunction("update_param", DaoFunction.SQL_UPDATE, "修改参数格式");//
		registerSQLFunction("delete_param", DaoFunction.SQL_DELETE, "修改参数格式");//
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
	/**
	 * 查询参数值列表
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException
	 */
	public SqlStatement queryParamValueById(TxnContext request,
			DataBus inputData) throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		// if(null !=
		// request.getRecord("select-key").getValue("webservice_patameter_id")){
		String sql = "select * from collect_ws_param_value where 1=1 ";
		sql += "and webservice_patameter_id = '" + request.getRecord("primary-key").getValue(
				"webservice_patameter_id") + "' order by cast(showorder   as   int) ";
		System.out.println("查询参数值列表sql： " + sql);
		stmt.addSqlStmt(sql);
		// }
		return stmt;
	}
	/**
	 * 更新webservice参数信息
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException
	 */
	public SqlStatement update_style(TxnContext request,
 			DataBus inputData) throws TxnException
 	{
 		SqlStatement stmt = new SqlStatement();

 		String webservice_patameter_id=request.getRecord("primary-key").getValue("webservice_patameter_id");//服务对象ID
 		String patameter_style=request.getRecord("record").getValue("patameter_style");//服务对象ID
 		
 		StringBuffer sql = new StringBuffer();
 		sql.append("update collect_webservice_patameter t set t.patameter_style =  " );
 		sql.append("'");
 		sql.append(patameter_style);
 		sql.append("' ");
 		sql.append("where t.webservice_patameter_id = ");
 		sql.append("'");
 		sql.append(webservice_patameter_id);
 		sql.append("' ");
 		
 		System.out.println("修改采集参数信息表 sql======"+sql.toString());
 		stmt.addSqlStmt(sql.toString());
 		return stmt;
 	}
	/**
	 * 更新socket参数信息
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException
	 */
	public SqlStatement update_param(TxnContext request,
 			DataBus inputData) throws TxnException
 	{
 		SqlStatement stmt = new SqlStatement();

 		String webservice_patameter_id=request.getRecord("primary-key").getValue("webservice_patameter_id");//服务对象ID
 		String patameter_style=request.getRecord("record").getValue("patameter_style");//参数格式
 		String patameter_type=request.getRecord("record").getValue("patameter_type");//参数类型
 		String patameter_name=request.getRecord("record").getValue("patameter_name");//参数名称
 		String patameter_value=request.getRecord("record").getValue("patameter_value");//参数值
 		
 		StringBuffer sql = new StringBuffer();
 		sql.append("update collect_webservice_patameter t set t.patameter_style =  " );
 		sql.append("'");
 		sql.append(patameter_style);
 		sql.append("',t.patameter_type = '");
 		sql.append(patameter_type);
 		sql.append("',t.patameter_name = '");
 		sql.append(patameter_name);
 		sql.append("',t.patameter_value = '");
 		sql.append(patameter_value);
 		sql.append("' where t.webservice_patameter_id = ");
 		sql.append("'");
 		sql.append(webservice_patameter_id);
 		sql.append("' ");
 		
 		System.out.println("修改采集参数信息表 sql======"+sql.toString());
 		stmt.addSqlStmt(sql.toString());
 		return stmt;
 	}
	
	
	
	public SqlStatement delete_param(TxnContext request, DataBus inputData)
	{
		String webservice_patameter_id = request.getRecord("primary-key").getValue(
				"webservice_patameter_id");// 方法ID

		StringBuffer sqlBuffer = new StringBuffer();
		SqlStatement stmt = new SqlStatement();
		if (webservice_patameter_id != null && !"".equals(webservice_patameter_id)) {
			sqlBuffer.append("delete from collect_webservice_patameter ");
			sqlBuffer.append(" where webservice_patameter_id  = '"
					+ webservice_patameter_id + "'");// 删除方法状态为停用
			stmt.addSqlStmt(sqlBuffer.toString());
		}
		return stmt;
	}
	

}
