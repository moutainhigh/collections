package com.gwssi.dw.runmgr.services.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[sys_svr_user]�Ĵ�����
 * 
 * @author Administrator
 * 
 */
public class SysSvrUser extends BaseTable
{
	public SysSvrUser()
	{

	}

	/**
	 * ע���û��Զ����SQL���
	 */
	protected void register()
	{
		// ������ע���û��Զ��庯���Ĺ���
		// ��������������SQL�������ƣ����ͣ�����
		// ҵ�������ͨ�����º�������:
		// table.executeFunction( "loadSysSvrUserList", context, inputNode,
		// outputNode );
		// XXX: registerSQLFunction( "loadSysSvrUserList",
		// DaoFunction.SQL_ROWSET, "��ȡ�����������б�" );
		registerSQLFunction("updateState", DaoFunction.SQL_UPDATE, "���·������״̬");
		registerSQLFunction("checkConfig", DaoFunction.SQL_ROWSET, "���·������״̬");
		registerSQLFunction("resetPassword", DaoFunction.SQL_UPDATE,
				"��ʼ�������������");
		registerSQLFunction("checkExistUser", DaoFunction.SQL_ROWSET, "��ѯ�ִ��û�");
		// DC2-jufeng-2012-07-10
		registerSQLFunction("queryUser", DaoFunction.SQL_ROWSET, "��ѯ�û��б�");
		// DC2-jufeng-2012-07-11
		registerSQLFunction("queryUserServices", DaoFunction.SQL_ROWSET,
				"��ѯ�û������б�");
		// DC2-jufeng-2012-07-12
		registerSQLFunction("queryOneService", DaoFunction.SQL_SELECT,
				"��ѯ�û������б�");

		registerSQLFunction("queryCanUsedServicesAndViews",
				DaoFunction.SQL_ROWSET, "��ѯ�û����Է���ķ������ͼ�б�");

		registerSQLFunction("queryUserCanCopy", DaoFunction.SQL_ROWSET,
				"��ѯ�û����Կ������û��б�");

		registerSQLFunction("queryUserSvrCanCopy", DaoFunction.SQL_ROWSET,
				"��ѯ�û����Կ������û��ķ����б�");
		registerSQLFunction("queryUserConfigId", DaoFunction.SQL_ROWSET,
				"�����û�ID�ͷ���ID��ȡ����ID");
		registerSQLFunction("deleteUserConfig", DaoFunction.SQL_DELETE,
				"ɾ���û���ĳ������");
		registerSQLFunction("updateUserConfigPause", DaoFunction.SQL_UPDATE,
				"ͣ�û������û���ĳ������");
		registerSQLFunction("selectSvrUserById", DaoFunction.SQL_SELECT,"��ѯ�û�����ͨ��ID");
		registerSQLFunction("selectServiceConfig", DaoFunction.SQL_SELECT,"��ѯ�û������ȡ��������");
		registerSQLFunction("selectServiceColumn", DaoFunction.SQL_ROWSET,"��ѯ�û������ȡ�������õı���ֶ�");
		registerSQLFunction("selectServiceColumnConfig", DaoFunction.SQL_ROWSET,"��ѯ�û������ȡ�������õı���ֶ�");
		
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

	public SqlStatement updateState(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String state = request.getRecord("record").getValue("state");
		String ids = request.getRecord("record").getValue("sys_svr_user_id");
		StringBuffer idStr = new StringBuffer();
		if (ids != null && !ids.trim().equals("")) {
			String[] idStrs = ids.split(",");
			for (int i = 0; i < idStrs.length; i++) {
				idStr.append("'").append(idStrs[i]).append("'");
				if (i != (idStrs.length - 1)) {
					idStr.append(", ");
				}
			}
		}
		stmt.addSqlStmt("update SYS_SVR_USER set state='" + state
				+ "' WHERE sys_svr_user_id IN (" + idStr.toString() + ")");
		// stmt.setCountStmt( "select count(sys_svr_user_id) from
		// SYS_SVR_USER");
		return stmt;
	}

	public SqlStatement resetPassword(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String pwd = request.getRecord("record").getValue("password");
		String ids = request.getRecord("record").getValue("sys_svr_user_id");
		StringBuffer idStr = new StringBuffer();
		if (ids != null && !ids.trim().equals("")) {
			String[] idStrs = ids.split(",");
			for (int i = 0; i < idStrs.length; i++) {
				idStr.append("'").append(idStrs[i]).append("'");
				if (i != (idStrs.length - 1)) {
					idStr.append(", ");
				}
			}
		}
		stmt.addSqlStmt("update SYS_SVR_USER set password='" + pwd
				+ "' WHERE sys_svr_user_id IN (" + idStr.toString() + ")");
		// stmt.setCountStmt( "select count(sys_svr_user_id) from
		// SYS_SVR_USER");
		return stmt;
	}

	public SqlStatement checkConfig(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String uId = request.getRecord("record").getValue("sys_svr_user_id");
		if (uId.indexOf(",") >= 0) {
			uId = uId.replaceAll(",", "','");
		}
		stmt
				.addSqlStmt("SELECT sys_svr_config_id FROM sys_svr_config WHERE sys_svr_user_id IN ('"
						+ uId + "')");
		stmt
				.setCountStmt("select count(sys_svr_config_id) from sys_svr_config WHERE sys_svr_user_id='"
						+ uId + "'");
		return stmt;
	}

	public SqlStatement checkExistUser(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String uName = request.getRecord("select-key").getValue("user_name");
		String loginName = request.getRecord("select-key").getValue(
				"login_name");
		StringBuffer sql = new StringBuffer(
				"SELECT sys_svr_user_id FROM sys_svr_user WHERE 1=1 AND ");
		if (uName != null && !uName.trim().equals("")) {
			sql.append(" user_name='").append(uName).append("'");
			if (loginName != null && !loginName.trim().equals("")) {
				sql.append(" OR login_name='").append(loginName).append("'");
			}
		} else {
			sql.append(" login_name='").append(loginName).append("'");
		}

		stmt.addSqlStmt(sql.toString());
		stmt.setCountStmt("select count(1) from (" + sql.toString() + ")");
		return stmt;
	}

	public SqlStatement queryUser(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String sys_svr_user_id = request.getRecord("select-key").getValue(
				"sys_svr_user_id");

		String loginName = request.getRecord("select-key").getValue(
				"login_name");
		String user_type = request.getRecord("select-key")
				.getValue("user_type");
		String state = request.getRecord("select-key").getValue("state");

		StringBuffer sql = new StringBuffer(
				" select * from view_sys_svr_user  where 1=1 ");
		if (sys_svr_user_id != null && !sys_svr_user_id.equals("")) {
			sql.append(" and sys_svr_user_id = '").append(sys_svr_user_id)
					.append("' ");
		}
		if (loginName != null && !loginName.equals("")) {
			sql.append(" and  login_name like '%").append(loginName).append(
					"%' ");
		}
		if (user_type != null && !user_type.equals("")) {
			sql.append(" and  user_type='").append(user_type).append("' ");
		}
		if (state != null && !state.equals("")) {
			sql.append(" and state ='").append(state).append("' ");
		}
		//sql.append(" order by user_order asc ");
		System.out.println("��ѯ�û��б����� \n" + sql);
		stmt.addSqlStmt(sql.toString());
		stmt.setCountStmt("select count(1) from (" + sql.toString() + ")");
		return stmt;
	}

	public SqlStatement queryUserServices(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();

		String sys_svr_user_id = request.getRecord("select-key").getValue(
				"sys_svr_user_id");
		StringBuffer sql = new StringBuffer(
				" select * from ( "
						+ " select distinct t1.sys_svr_service_id, t1.sys_svr_user_id, t1.sys_svr_config_id, t2.svr_name,  t3.user_name,   t2.max_records,  'W' as svr_type, "
						+ " case when exists(select * from sys_svr_limit tt where tt.sys_svr_user_id=t1.sys_svr_user_id and tt.sys_svr_service_id=t1.sys_svr_service_id) then 1  else 0 end as  is_limit, "
						+ " t2.svr_order, 0 as view_order , 1 as cc,t1.is_pause "
						+ " from sys_svr_config t1, sys_svr_service t2, view_sys_svr_user t3 "
						+ " where t1.sys_svr_service_id = t2.sys_svr_service_id(+)  "
						+ " and t1.sys_svr_user_id = t3.sys_svr_user_id(+) "
						+ " and t1.sys_svr_service_id is not null "
						+ " and t3.sys_svr_user_id is not null ");
		sql.append(" and t1.sys_svr_user_id ='").append(sys_svr_user_id)
				.append("' ");
		sql
				.append(" union all ")
				.append(
						" select distinct t1.sys_db_view_id as sys_svr_service_id, t1.sys_db_user_id as sys_svr_user_id, 'V' as sys_svr_config_id,  t2.view_name as svr_name, t3.user_name, t2.max_records,'V' as svr_type, 0 as is_limit,"
								+ "  0 as svr_order, t2.view_order, 2 as cc,'0'  "
								+ " from sys_db_config t1, sys_db_view t2, view_sys_svr_user t3 "
								+ " where t1.sys_db_view_id = t2.sys_db_view_id(+) "
								+ " and t1.sys_db_user_id = t3.sys_svr_user_id(+) "
								+ " and t1.sys_db_view_id is not null "
								+ " and t3.sys_svr_user_id is not null");
		sql.append(" and t1.sys_db_user_id ='").append(sys_svr_user_id).append(
				"' ");
		sql.append(" union all ")
				.append("  select distinct t.sys_db_config_id as sys_svr_service_id,t.sys_db_user_id as sys_svr_user_id, 'CV' as sys_svr_config_id, t.config_name as svr_name, t2.user_name, null as max_records,  'CV' as svr_type, 0 as limit,"
								+ "  99999999 as svr_order, t.config_order as view_order, 3 as cc,'0' "
								+ "  from sys_db_config t, view_sys_svr_user t2 "
								+ "  where t.sys_db_user_id=t2.sys_svr_user_id(+) "
								+ "  and  t.config_type='02' ");
		sql.append(" and t.sys_db_user_id ='").append(sys_svr_user_id).append(
				"' ");
		sql.append(" ) s  order by s.cc, s.view_order , s.svr_order  ");
		System.out.println("sql -----queryUserServices-------->>>>>>>>>>>   \n"+ sql);
		stmt.addSqlStmt(sql.toString());
		stmt.setCountStmt("select count(1) from (" + sql.toString() + ")");
		return stmt;
	}

	public SqlStatement queryCanUsedServicesAndViews(TxnContext request,
			DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();

		String sys_svr_user_id = request.getRecord("select-key").getValue(
				"sys_svr_user_id");
		StringBuffer sql = new StringBuffer(
				" select * from ( "
						+ " SELECT s.sys_svr_service_id, s.svr_name, s.svr_code, s.svr_order, 1 as cc,'W' as stype "
						+ " FROM sys_svr_service s "
						+ "  WHERE s.sys_svr_service_id NOT IN   (SELECT c.sys_svr_service_id   FROM sys_svr_config c WHERE ");

		sql.append(" c.sys_svr_user_id ='").append(sys_svr_user_id).append(
				"' ) ");
		sql
				.append(" union all ")
				.append(
						" select s.sys_db_view_id as sys_svr_service_id,  s.view_name as svr_name,  s.view_code as svr_code, s.view_order as svr_order,  2 as cc, 'V' as stype "
								+ " from  sys_db_view s   where s.sys_db_view_id not in   (select c.sys_db_view_id from  sys_db_config c   where ");
		sql.append(" c.sys_db_user_id ='").append(sys_svr_user_id).append(
				"' ) ");
		sql.append(" ) ss order by ss.cc , ss.svr_order ");

		System.out
				.println("sql ------queryCanUsedServicesAndViews------->>>>>>>>>>>   \n"
						+ sql);
		stmt.addSqlStmt(sql.toString());
		stmt.setCountStmt("select count(1) from (" + sql.toString() + ")");
		return stmt;
	}

	public SqlStatement queryUserCanCopy(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();

		String sys_svr_user_id = request.getRecord("select-key").getValue(
				"sys_svr_user_id");
		StringBuffer sql = new StringBuffer(
				" select distinct  t.sys_svr_user_id , t2.login_name, t2.user_name, t2.user_order  from sys_svr_config t , sys_svr_user t2 "
						+ " where t.sys_svr_user_id=t2.sys_svr_user_id");
		sql.append(" and t2.sys_svr_user_id <>'").append(sys_svr_user_id)
				.append("' order by t2.user_order  ");
		System.out.println("sql ------queryUserCanCopy------->>>>>>>>>>>   \n"
				+ sql);
		stmt.addSqlStmt(sql.toString());
		stmt.setCountStmt("select count(1) from (" + sql.toString() + ")");
		return stmt;
	}

	public SqlStatement queryUserSvrCanCopy(TxnContext request,
			DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();

		String sys_svr_user_id = request.getRecord("select-key").getValue(
				"sys_svr_user_id");
		// Ҫ����������û�idΪsys_svr_user_id2
		String sys_svr_user_id2 = request.getRecord("select-key").getValue(
				"sys_svr_user_id2");
		StringBuffer sql = new StringBuffer(
				" select  t1.sys_svr_service_id, t2.svr_name , t1.sys_svr_config_id from sys_svr_config t1, sys_svr_service t2  "
						+ " where t1.sys_svr_service_id=t2.sys_svr_service_id ");
		sql
				.append(" and t1.sys_svr_user_id='")
				.append(sys_svr_user_id2)
				.append("' ")
				.append(
						" and t1.sys_svr_service_id not in ( select t.sys_svr_service_id from sys_svr_config t where t.sys_svr_user_id='")
				.append(sys_svr_user_id).append("') ");

		System.out
				.println("sql ------queryUserSvrCanCopy------->>>>>>>>>>>   \n"
						+ sql);
		stmt.addSqlStmt(sql.toString());
		stmt.setCountStmt("select count(1) from (" + sql.toString() + ")");
		return stmt;
	}

	public SqlStatement queryOneService(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();

		String sys_svr_service_id = request.getRecord("select-key").getValue(
				"sys_svr_service_id");
		StringBuffer sql = new StringBuffer(
				" select * from ( "
						+ " select distinct t1.sys_svr_service_id, t1.sys_svr_user_id, t2.svr_name,  t3.user_name,   t2.max_records,  'W' as svr_type, "
						+ " case when exists(select * from sys_svr_limit tt where tt.sys_svr_user_id=t1.sys_svr_user_id and tt.sys_svr_service_id=t1.sys_svr_service_id) then 1  else 0 end as  is_limit, "
						+ " t2.svr_order,  0 as view_order "
						+ " from sys_svr_config t1, sys_svr_service t2, sys_svr_user t3 "
						+ " where t1.sys_svr_service_id = t2.sys_svr_service_id(+)  "
						+ " and t1.sys_svr_user_id = t3.sys_svr_user_id(+) "
						+ " and t1.sys_svr_service_id is not null "
						+ " and t3.sys_svr_user_id is not null ");

		// System.out.println("sql ------------->>>>>>>>>>> \n"+sql);
		stmt.addSqlStmt(sql.toString());
		stmt.setCountStmt("select count(1) from (" + sql.toString() + ")");
		return stmt;
	}

	public SqlStatement queryUserConfigId(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();

		String sys_svr_service_id = request.getRecord("select-key").getValue(
				"sys_svr_service_id");
		String sys_svr_user_id = request.getRecord("select-key").getValue(
				"sys_svr_user_id");
		StringBuffer sql = new StringBuffer(
				" select s.sys_svr_config_id from sys_svr_config s "
						+ "where s.sys_svr_service_id='" + sys_svr_service_id
						+ "' " + "and s.sys_svr_user_id='" + sys_svr_user_id
						+ "' ");

		stmt.addSqlStmt(sql.toString());
		return stmt;
	}

	public SqlStatement deleteUserConfig(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();

		String sys_svr_config_id = request.getRecord("select-key").getValue(
				"sys_svr_config_id");
		StringBuffer sql = new StringBuffer(
				"delete from sys_svr_config s where s.sys_svr_config_id='"
						+ sys_svr_config_id + "'");
		System.out.println("-----deleteUserConfig------" + sql);

		stmt.addSqlStmt(sql.toString());
		return stmt;
	}

	public SqlStatement updateUserConfigPause(TxnContext request,
			DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();

		String sys_svr_service_id = request.getRecord("select-key").getValue(
				"sys_svr_service_id");
		String sys_svr_user_id = request.getRecord("select-key").getValue(
				"sys_svr_user_id");
		String is_pause = request.getRecord("select-key").getValue("is_pause");
		StringBuffer sql = new StringBuffer(
				"update sys_svr_config s set s.is_pause='").append(is_pause)
				.append("' where s.sys_svr_service_id='").append(
						sys_svr_service_id).append("' and s.sys_svr_user_id='")
				.append(sys_svr_user_id).append("' ");

		stmt.addSqlStmt(sql.toString());
		return stmt;
	}
	
	public SqlStatement selectSvrUserById(TxnContext request,
			DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String sys_svr_user_id = request.getRecord("select-key").getValue("sys_svr_user_id");
		StringBuffer sql = new StringBuffer(
				"select * from view_sys_svr_user where sys_svr_user_id='"+sys_svr_user_id+"'");
		stmt.addSqlStmt(sql.toString());
		return stmt;
	}
	
	public SqlStatement selectServiceConfig(TxnContext request,
			DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String sys_svr_user_id = request.getRecord("select-key").getValue("sys_svr_user_id");
		String sys_svr_service_id = request.getRecord("select-key").getValue("sys_svr_service_id");
		StringBuffer sql = new StringBuffer("select s.sys_svr_config_id,s.permit_column," +
				"s.permit_column_en_array,s.permit_column_cn_array, s.query_sql,sv.svr_code," +
				"sv.create_by,sv.create_date from sys_svr_config s,sys_svr_service sv " +
				"where s.sys_svr_service_id=sv.sys_svr_service_id and s.sys_svr_user_id ='");
		sql.append(sys_svr_user_id).append("' and s.sys_svr_service_id='")
		.append(sys_svr_service_id).append("' ");
		stmt.addSqlStmt(sql.toString());
		return stmt;
	}
	
	public SqlStatement selectServiceColumn(TxnContext request,
			DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String column_nos = request.getRecord("select-key").getValue("column_nos");
		StringBuffer sql = new StringBuffer("select table_name,wm_concat (column_name) column_names from(" +
				"select rt.table_name||'('||r.table_code||')��' table_name,r.column_name||'('||r.column_code||')' " +
				"column_name from sys_rd_column r,sys_rd_table rt where r.table_code=rt.table_code " +
				" and r.column_no in (").append(column_nos).append(")")
				.append(" order by r.column_no)group by table_name");
		stmt.addSqlStmt(sql.toString());
		return stmt;
	}
	
	public SqlStatement selectServiceColumnConfig(TxnContext request,
			DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String sys_svr_config_id = request.getRecord("select-key").getValue("sys_svr_config_id");
		StringBuffer sql = new StringBuffer("select sys_svr_config_id, param_text from sys_svr_config_param "+
				"where sys_svr_config_id = '"+sys_svr_config_id+"' order by sys_svr_config_param.param_order " );
		stmt.addSqlStmt(sql.toString());
		return stmt;
	}
	
	
	/**
	 * XXX:�û��Զ����SQL��� ���ڶ��¼�Ĳ�ѯ��䣬��Ҫ����������䣺��ѯ����ȡ��¼��������� ������������䣬ֻ��Ҫ����һ�����
	 * 
	 * @param request
	 *            ���׵�������
	 * @param inputData
	 *            ������������ڵ�
	 * @return public SqlStatement loadSysSvrUserList( TxnContext request,
	 *         DataBus inputData ) { SqlStatement stmt = new SqlStatement( );
	 *         stmt.addSqlStmt( "select * from sys_svr_user" );
	 *         stmt.setCountStmt( "select count(*) from sys_svr_user" ); return
	 *         stmt; }
	 */

}
