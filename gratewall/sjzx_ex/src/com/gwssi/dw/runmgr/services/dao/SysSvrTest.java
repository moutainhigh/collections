package com.gwssi.dw.runmgr.services.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

public class SysSvrTest extends BaseTable
{
	/**
	 * 注册用户自定义的SQL语句
	 */
	protected void register( )
	{
		registerSQLFunction("queryCondition", DaoFunction.SQL_ROWSET,"查询用户参数");
	}
	
	protected void afterExecuteStmt(DaoFunction arg0, TxnContext arg1,
			DataBus[] arg2, String arg3) throws TxnException
	{
	}

	protected void prepareExecuteStmt(DaoFunction arg0, TxnContext arg1,
			DataBus[] arg2, String arg3) throws TxnException
	{
	}
	
	/**
	   * 
	   * queryDataSource(查询用户参数)    
	   * TODO(这里描述这个方法适用条件 – 可选)    
	   * TODO(这里描述这个方法的执行流程 – 可选)    
	   * TODO(这里描述这个方法的使用方法 – 可选)    
	   * TODO(这里描述这个方法的注意事项 – 可选)    
	   * @param request
	   * @param inputData
	   * @return
	   * @throws TxnException        
	   * SqlStatement       
	   * @Exception 异常对象    
	   * @since  CodingExample　Ver(编码范例查看) 1.1
	   */
		public SqlStatement queryCondition(TxnContext request,
				DataBus inputData) throws TxnException
		{
			SqlStatement stmt = new SqlStatement();

			String service_id = request.getRecord("select-key").getValue("service_id");
			
			

			StringBuffer querySql = new StringBuffer(
					"select t.condition_id,t.service_id,t.frist_connector,t.left_paren,t.table_name_en,t.table_name_cn,t.column_name_en,t.column_name_cn,t.second_connector,t.param_value,t.param_type,t.right_paren from share_service_condition t "
							+ " where 1=1 ");
			if (service_id != null && !"".equals(service_id)) {
				querySql.append(" and service_id = '" + service_id + "'");
			}

			

			querySql.append(" and t.need_input='Y'");
			querySql.append(" order by t.show_order ");
			
			stmt.addSqlStmt(querySql.toString());
			stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
			return stmt;

		}
}
