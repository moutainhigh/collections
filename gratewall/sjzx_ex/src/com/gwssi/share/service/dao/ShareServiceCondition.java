package com.gwssi.share.service.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * 数据表[share_service_condition]的处理类
 * @author Administrator
 *
 */
public class ShareServiceCondition extends BaseTable
{
	public ShareServiceCondition()
	{
		
	}
	
	/**
	 * 注册用户自定义的SQL语句
	 */
	protected void register( )
	{
		// 以下是注册用户自定义函数的过程
		// 包括三个参数：SQL语句的名称，类型，描述
		// 业务类可以通过以下函数调用:
		// table.executeFunction( "loadShareServiceConditionList", context, inputNode, outputNode );
		//XXX: registerSQLFunction( "loadShareServiceConditionList", DaoFunction.SQL_ROWSET, "获取服务条件表列表" );
		registerSQLFunction("queryCondition", DaoFunction.SQL_ROWSET,"查询服务参数");
		registerSQLFunction("deleteConditionsByShareServiceID", DaoFunction.SQL_DELETE,"查询服务参数");
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
	 * XXX:用户自定义的SQL语句
	 * 对于多记录的查询语句，需要生成两个语句：查询语句和取记录数量的语句
	 * 对于其它的语句，只需要生成一个语句
	 * @param request 交易的上下文
	 * @param inputData 生成语句的输入节点
	 * @return
	public SqlStatement loadShareServiceConditionList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from share_service_condition" );
		stmt.setCountStmt( "select count(*) from share_service_condition" );
		return stmt;
	}
	 */
	
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
		
		/**
		 * 
		 * deleteConditionsByShareServiceID(根据服务ID删除)    
		 * TODO(这里描述这个方法适用条件 – 可选)    
		 * TODO(这里描述这个方法的执行流程 – 可选)    
		 * TODO(这里描述这个方法的使用方法 – 可选)    
		 * TODO(这里描述这个方法的注意事项 – 可选)    
		 * @param request
		 * @param inputData
		 * @return        
		 * SqlStatement       
		 * @Exception 异常对象    
		 * @since  CodingExample　Ver(编码范例查看) 1.1
		 */
		public SqlStatement deleteConditionsByShareServiceID( TxnContext request, DataBus inputData )
		{
			SqlStatement stmt = new SqlStatement( );
			String service_id = request.getRecord("select-key").getValue("service_id");
			stmt.addSqlStmt( "delete from share_service_condition where service_id='"+service_id+"'" );
			return stmt;
		}
	
}

