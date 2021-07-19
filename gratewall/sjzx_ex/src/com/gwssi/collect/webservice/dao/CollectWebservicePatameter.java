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
	 * ע��SQL���
	 */
	protected void register()
	{
		registerSQLFunction("update_style", DaoFunction.SQL_UPDATE, "�޸Ĳ�����ʽ");//
		registerSQLFunction("queryParamValueById", DaoFunction.SQL_ROWSET, "��ѯ����ֵ�б�");
		registerSQLFunction("update_param", DaoFunction.SQL_UPDATE, "�޸Ĳ�����ʽ");//
		registerSQLFunction("delete_param", DaoFunction.SQL_DELETE, "�޸Ĳ�����ʽ");//
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
	 * ��ѯ����ֵ�б�
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
		System.out.println("��ѯ����ֵ�б�sql�� " + sql);
		stmt.addSqlStmt(sql);
		// }
		return stmt;
	}
	/**
	 * ����webservice������Ϣ
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException
	 */
	public SqlStatement update_style(TxnContext request,
 			DataBus inputData) throws TxnException
 	{
 		SqlStatement stmt = new SqlStatement();

 		String webservice_patameter_id=request.getRecord("primary-key").getValue("webservice_patameter_id");//�������ID
 		String patameter_style=request.getRecord("record").getValue("patameter_style");//�������ID
 		
 		StringBuffer sql = new StringBuffer();
 		sql.append("update collect_webservice_patameter t set t.patameter_style =  " );
 		sql.append("'");
 		sql.append(patameter_style);
 		sql.append("' ");
 		sql.append("where t.webservice_patameter_id = ");
 		sql.append("'");
 		sql.append(webservice_patameter_id);
 		sql.append("' ");
 		
 		System.out.println("�޸Ĳɼ�������Ϣ�� sql======"+sql.toString());
 		stmt.addSqlStmt(sql.toString());
 		return stmt;
 	}
	/**
	 * ����socket������Ϣ
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException
	 */
	public SqlStatement update_param(TxnContext request,
 			DataBus inputData) throws TxnException
 	{
 		SqlStatement stmt = new SqlStatement();

 		String webservice_patameter_id=request.getRecord("primary-key").getValue("webservice_patameter_id");//�������ID
 		String patameter_style=request.getRecord("record").getValue("patameter_style");//������ʽ
 		String patameter_type=request.getRecord("record").getValue("patameter_type");//��������
 		String patameter_name=request.getRecord("record").getValue("patameter_name");//��������
 		String patameter_value=request.getRecord("record").getValue("patameter_value");//����ֵ
 		
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
 		
 		System.out.println("�޸Ĳɼ�������Ϣ�� sql======"+sql.toString());
 		stmt.addSqlStmt(sql.toString());
 		return stmt;
 	}
	
	
	
	public SqlStatement delete_param(TxnContext request, DataBus inputData)
	{
		String webservice_patameter_id = request.getRecord("primary-key").getValue(
				"webservice_patameter_id");// ����ID

		StringBuffer sqlBuffer = new StringBuffer();
		SqlStatement stmt = new SqlStatement();
		if (webservice_patameter_id != null && !"".equals(webservice_patameter_id)) {
			sqlBuffer.append("delete from collect_webservice_patameter ");
			sqlBuffer.append(" where webservice_patameter_id  = '"
					+ webservice_patameter_id + "'");// ɾ������״̬Ϊͣ��
			stmt.addSqlStmt(sqlBuffer.toString());
		}
		return stmt;
	}
	

}
