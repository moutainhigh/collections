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
	 * ע���û��Զ����SQL���
	 */
	protected void register( )
	{
		registerSQLFunction("queryCondition", DaoFunction.SQL_ROWSET,"��ѯ�û�����");
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
	   * queryDataSource(��ѯ�û�����)    
	   * TODO(����������������������� �C ��ѡ)    
	   * TODO(�����������������ִ������ �C ��ѡ)    
	   * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	   * TODO(�����������������ע������ �C ��ѡ)    
	   * @param request
	   * @param inputData
	   * @return
	   * @throws TxnException        
	   * SqlStatement       
	   * @Exception �쳣����    
	   * @since  CodingExample��Ver(���뷶���鿴) 1.1
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
