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
    * ע��SQL���
    */
   protected void register()
   {
	   registerSQLFunction("queryTableId", DaoFunction.SQL_ROWSET, "��ȡ���ݿ��ID" );
	   registerSQLFunction("deleteTableItem", DaoFunction.SQL_DELETE, "ɾ���������" );
   }

   /**
    * ִ��SQL���ǰ�Ĵ���
    */
   public void prepareExecuteStmt(DaoFunction func, TxnContext request, 
		   DataBus[] inputData, String outputNode) throws TxnException
   {
      
   }

   /**
    * ִ����SQL����Ĵ���
    */
   public void afterExecuteStmt(DaoFunction func, TxnContext request, 
		   DataBus[] inputData, String outputNode) throws TxnException
   {
      
   }
   /**
    * ��ѯ���ݱ���Ϣ
    * @param request
    * @param inputData
    * @return
    */
   
   public SqlStatement queryTableId(TxnContext request, DataBus inputData)
	{
		String collect_table_id = request.getRecord("primary-key").getValue("collect_table_id");//���ݱ�ID
		
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select * from res_collect_table t ");
		if(!"".equals(collect_table_id)){
			sqlBuffer.append(" where t.collect_table_id = '"+collect_table_id+"' ");
		}
		System.out.println("��ѯ���ݱ���Ϣsql============"+sqlBuffer.toString());
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		stmt.setCountStmt(sqlBuffer.toString());
		return stmt;
	}
   
   /**
	 * deleteTableItem 
	 * �������ݱ�IDɾ�����ݱ��Ӧ���������� 
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public SqlStatement deleteTableItem(TxnContext request, DataBus inputData)
	{
		String collect_dataitem_id = request.getRecord("primary-key").getValue("collect_dataitem_id");//���ݱ�ID
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
