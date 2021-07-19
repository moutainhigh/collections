package com.gwssi.dw.runmgr.services.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.common.util.UuidGenerator;
import com.gwssi.dw.runmgr.services.vo.SysSvrLimitContext;

public class TxnSysSvrLimit extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap		txnMethods		= getAllMethod(
														TxnSysSvrLimit.class,
														SysSvrLimitContext.class);

	// ���ݱ�����
	private static final String	TABLE_NAME		= "sys_svr_limit";

	// ��ѯ�б�
	private static final String	ROWSET_FUNCTION	= "select sys_svr_limit list";

	// ��ѯ��¼
	private static final String	SELECT_FUNCTION	= "select one sys_svr_limit";

	// �޸ļ�¼
	private static final String	UPDATE_FUNCTION	= "update one sys_svr_limit";

	// ���Ӽ�¼
	private static final String	INSERT_FUNCTION	= "insert one sys_svr_limit";

	// ɾ����¼
	private static final String	DELETE_FUNCTION	= "delete one sys_svr_limit";

	/**
	 * ���캯��
	 */
	public TxnSysSvrLimit()
	{

	}

	/**
	 * ��ʼ������
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{

	}

	/**
	 * ��ѯ�û����������б�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50011701(SysSvrLimitContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoSysSvrLimitSelectKey selectKey = context.getSelectKey(
		// inputNode );
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction(ROWSET_FUNCTION, context, inputNode, outputNode);
		// ��ѯ���ļ�¼�� VoSysSvrLimit result[] = context.getSysSvrLimits( outputNode
		// );
	}

	/**
	 * �޸��û�����������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50011702(SysSvrLimitContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// �޸ļ�¼������ VoSysSvrLimit sys_svr_limit = context.getSysSvrLimit(
		// inputNode );
		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * �����û�����������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50011703(SysSvrLimitContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ���Ӽ�¼������ VoSysSvrLimit sys_svr_limit = context.getSysSvrLimit(
		// inputNode );
		table.executeFunction(INSERT_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * ��ѯ�û��������������޸�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50011704(SysSvrLimitContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoSysSvrLimitPrimaryKey primaryKey = context.getPrimaryKey(
		// inputNode );
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		// ��ѯ���ļ�¼���� VoSysSvrLimit result = context.getSysSvrLimit( outputNode );
	}

	/**
	 * ɾ���û�����������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50011705(SysSvrLimitContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ɾ����¼�������б� VoSysSvrLimitPrimaryKey primaryKey[] =
		// context.getPrimaryKeys( inputNode );
		table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * �����û�����������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50011706(SysSvrLimitContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// System.out.println("in txn50011706 ---------------------- >>>> \n"+
		// context);
		DataBus db = context.getRecord("record");
		TxnContext temp = new TxnContext();
		DataBus d1 = new DataBus();
		String sys_svr_service_id = db.getValue("sys_svr_service_id");
		String sys_svr_user_id = db.getValue("sys_svr_user_id");
		String visit_weeks = db.getValue("visit_weeks");
		d1.setValue("sys_svr_service_id", sys_svr_service_id);
		d1.setValue("sys_svr_user_id", sys_svr_user_id);
		temp.addRecord("select-key", d1);
		this.callService("50011701", temp);
		// System.out.println("in 50011701 ---------------------- >>>> \n"+
		// temp);
		Recordset rs = temp.getRecordset("record");
		if (rs != null && rs.size() > 0) {
			// ɾ������
			StringBuffer sb = new StringBuffer(" delete from sys_svr_limit ");
			sb.append(" where sys_svr_service_id='").append(sys_svr_service_id)
					.append("' and sys_svr_user_id='").append(sys_svr_user_id)
					.append("' ");
			int j = table.executeUpdate(sb.toString());
			// System.out.println("ɾ�������Ľ���� "+j);
		}
		String[] s = new String[8];
		for (int j = 1; j < 8; j++) {
			StringBuffer sb1 = new StringBuffer(
					" insert into  sys_svr_limit (sys_svr_limit_id, sys_svr_user_id, sys_svr_service_id, is_limit_week,"
							+ "is_limit_time,is_limit_number,is_limit_total,limit_week,limit_time,limit_start_time, limit_end_time, limit_number, limit_total) values ");
			sb1.append("( '").append(UuidGenerator.getUUID() + "',")
					.append("'").append(sys_svr_user_id).append("'").append(
							",'").append(sys_svr_service_id).append("'");
			String is_limit_week = "0";
			if (visit_weeks != null && !visit_weeks.equals("")) {
				String weeks = ";" + visit_weeks + ";";
				String e = String.valueOf(j);
				if (weeks.indexOf(e) >= 0) {
					// �ܷ��ʵ�
				} else {
					// ���ܷ��ʵ�
					is_limit_week = "1";
				}
			}
			sb1.append(",'").append(is_limit_week).append("'").append(",'")
					.append(db.getValue("is_limit_time")).append("'").append(
							",'").append(db.getValue("is_limit_number"))
					.append("'").append(",'").append(
							db.getValue("is_limit_total")).append("'").append(
							",'").append("" + j + "").append("'").append(",'")
					.append(
							db.getValue("limit_start_time") + "-"
									+ db.getValue("limit_end_time"))
					.append("'").append(",'").append(
							db.getValue("limit_start_time")).append("'")
					.append(",'").append(db.getValue("limit_end_time")).append(
							"'").append(",'").append(
							db.getValue("limit_number")).append("'").append(
							",'").append(db.getValue("limit_total")).append(
							"' ) ");
			// System.out.println("��"+j+"��SQL�ǣ�" +sb1);
			s[j] = sb1.toString();
		}

		SqlStatement[] sqlStmt = new SqlStatement[7];
		for (int j = 1; j < 8; j++) {
			SqlStatement ss = new SqlStatement();
			ss.addSqlStmt(s[j]);
			sqlStmt[j - 1] = ss;
		}

		table.executeBatch(sqlStmt);
		
		StringBuffer delete_lock = new StringBuffer(" delete from sys_svr_lock ");
		delete_lock.append(" where sys_svr_service_id='").append(
				sys_svr_service_id).append("' and sys_svr_user_id='").append(
				sys_svr_user_id).append("' ");
		int j = table.executeUpdate(delete_lock.toString());

		// ���Ӽ�¼������ VoSysSvrLimit sys_svr_limit = context.getSysSvrLimit(
		// inputNode );
	}

	/**
	 * ���ظ���ķ����������滻���׽ӿڵ�������� ���ú���
	 * 
	 * @param funcName
	 *            ��������
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	protected void doService(String funcName, TxnContext context)
			throws TxnException
	{
		Method method = (Method) txnMethods.get(funcName);
		if (method == null) {
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException(ErrorConstant.JAVA_METHOD_NOTFOUND,
					"û���ҵ�������[" + txnCode + ":" + funcName + "]�Ĵ�����");
		}

		// ִ��
		SysSvrLimitContext appContext = new SysSvrLimitContext(context);
		invoke(method, appContext);
	}
}
