package com.gwssi.share.service.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[share_service_condition]�Ĵ�����
 * @author Administrator
 *
 */
public class ShareServiceCondition extends BaseTable
{
	public ShareServiceCondition()
	{
		
	}
	
	/**
	 * ע���û��Զ����SQL���
	 */
	protected void register( )
	{
		// ������ע���û��Զ��庯���Ĺ���
		// ��������������SQL�������ƣ����ͣ�����
		// ҵ�������ͨ�����º�������:
		// table.executeFunction( "loadShareServiceConditionList", context, inputNode, outputNode );
		//XXX: registerSQLFunction( "loadShareServiceConditionList", DaoFunction.SQL_ROWSET, "��ȡ�����������б�" );
		registerSQLFunction("queryCondition", DaoFunction.SQL_ROWSET,"��ѯ�������");
		registerSQLFunction("deleteConditionsByShareServiceID", DaoFunction.SQL_DELETE,"��ѯ�������");
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
	 * XXX:�û��Զ����SQL���
	 * ���ڶ��¼�Ĳ�ѯ��䣬��Ҫ����������䣺��ѯ����ȡ��¼���������
	 * ������������䣬ֻ��Ҫ����һ�����
	 * @param request ���׵�������
	 * @param inputData ������������ڵ�
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
		
		/**
		 * 
		 * deleteConditionsByShareServiceID(���ݷ���IDɾ��)    
		 * TODO(����������������������� �C ��ѡ)    
		 * TODO(�����������������ִ������ �C ��ѡ)    
		 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
		 * TODO(�����������������ע������ �C ��ѡ)    
		 * @param request
		 * @param inputData
		 * @return        
		 * SqlStatement       
		 * @Exception �쳣����    
		 * @since  CodingExample��Ver(���뷶���鿴) 1.1
		 */
		public SqlStatement deleteConditionsByShareServiceID( TxnContext request, DataBus inputData )
		{
			SqlStatement stmt = new SqlStatement( );
			String service_id = request.getRecord("select-key").getValue("service_id");
			stmt.addSqlStmt( "delete from share_service_condition where service_id='"+service_id+"'" );
			return stmt;
		}
	
}

