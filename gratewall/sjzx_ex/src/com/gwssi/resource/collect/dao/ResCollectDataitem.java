package com.gwssi.resource.collect.dao;

import com.gwssi.common.constant.ExConstant;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class ResCollectDataitem extends BaseTable
{
   public ResCollectDataitem()
   {
      
   }

   /**
    * 注册SQL语句
    */
   protected void register()
   {
	   registerSQLFunction("queryTableId", DaoFunction.SQL_ROWSET, "获取数据库表ID" );
	   registerSQLFunction("deleteTableItem", DaoFunction.SQL_DELETE, "删除数据项表" );
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
    * 查询数据表信息
    * @param request
    * @param inputData
    * @return
    */
   
   public SqlStatement queryTableId(TxnContext request, DataBus inputData)
	{
		String collect_table_id = request.getRecord("primary-key").getValue("collect_table_id");//数据表ID
		
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select * from res_collect_table t ");
		if(!"".equals(collect_table_id)){
			sqlBuffer.append(" where t.collect_table_id = '"+collect_table_id+"' ");
		}
		System.out.println("查询数据表信息sql============"+sqlBuffer.toString());
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		stmt.setCountStmt(sqlBuffer.toString());
		return stmt;
	}
   
   /**
	 * deleteTableItem 
	 * 根据数据表ID删除数据表对应所有数据项 
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public SqlStatement deleteTableItem(TxnContext request, DataBus inputData)
	{
		String collect_dataitem_id = request.getRecord("primary-key").getValue("collect_dataitem_id");//数据表ID
		StringBuffer sqlBuffer = new StringBuffer();
		SqlStatement stmt = new SqlStatement();
		if(collect_dataitem_id!=null){
			sqlBuffer.append("update  res_collect_dataitem t set t.is_markup = '"+ExConstant.IS_MARKUP_N);
			sqlBuffer.append("' where t.collect_dataitem_id = '"+collect_dataitem_id+"'");
			stmt.addSqlStmt(sqlBuffer.toString());
		}
		return stmt;
	}

}
