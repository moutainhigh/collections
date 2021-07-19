package com.gwssi.log.report.dao;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.context.vo.VoUser;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

import com.gwssi.common.util.CalendarUtil;
import com.gwssi.common.util.DateUtil;

public class LogReportCreate extends BaseTable
{
	public LogReportCreate()
	{

	}

	/**
	 * ע��SQL���
	 */
	protected void register()
	{
		registerSQLFunction("queryLogReportCreate", DaoFunction.SQL_ROWSET,
				"��ѯϵͳʹ���������");
		registerSQLFunction("addLogReport", DaoFunction.SQL_INSERT, "����ϵͳ����");
		registerSQLFunction("upLoadLogReport", DaoFunction.SQL_INSERT,
				"�ϴ���־ϵͳ����");
		registerSQLFunction("publishReport", DaoFunction.SQL_UPDATE, "��������");
		registerSQLFunction("returnReport", DaoFunction.SQL_UPDATE, "�˻ر���");
		registerSQLFunction("modifyReport", DaoFunction.SQL_UPDATE, "�޸ı���");
		registerSQLFunction("addReportUse", DaoFunction.SQL_INSERT,"������������ʹ�ü�¼");
		registerSQLFunction("checkReportName", DaoFunction.SQL_ROWSET,"��֤�Ƿ���ڱ�������");
		registerSQLFunction("updateReportState", DaoFunction.SQL_UPDATE,"�޸�ʹ�ñ���״̬");
		registerSQLFunction("queryreport", DaoFunction.SQL_ROWSET,"��ѯϵͳʹ�ñ�����־��");
		registerSQLFunction("getStateSet", DaoFunction.SQL_ROWSET,"��ȡ����״̬���ڲ�ѯ����");

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

	public SqlStatement queryreport(TxnContext request,
			DataBus inputData) throws TxnException{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sqlBuffer = new StringBuffer();
		String codeType = inputData.getValue("codetype");
		String column = inputData.getValue("column");
		if (StringUtils.isNotBlank(codeType) && StringUtils.isNotBlank(column)) {
			sqlBuffer
					.append("select cd.codename as title, cd.codevalue as key ")
					.append("from codedata cd, ")
					.append("(select svr.")
					.append(column)
					
					.append(" from LOG_REPORT_CREATE svr group by svr.")
					.append(column).append(") t where cd.codetype = '")
					.append(codeType).append("' and cd.codevalue = t.")
					.append(column).append("(+)");
			stmt.addSqlStmt(sqlBuffer.toString());
			System.out.println("sql����"+sqlBuffer);
		}
		return stmt;
		
		
	}
	
	
	
	
	/**
	 * ��ѯϵͳʹ���������
	 */
	public SqlStatement queryLogReportCreate(TxnContext request,
			DataBus inputData) throws TxnException
	{
		SqlStatement stmt = new SqlStatement();

		
		String state = request.getRecord("select-key").getValue("state");
		String create_date = request.getRecord("select-key").getValue("created_time");
		

		StringBuffer querySql = new StringBuffer(
				"select t.log_report_create_id,t.report_name,t.create_date,t.last_mender,t.publish_date,t.publish_person,t.state,t.operate,t.filename,t.path,t.timestamp,t.creator,t.report_type from LOG_REPORT_CREATE t"
						+ " where t.state<2 ");
		
		if (StringUtils.isNotBlank(create_date)) {
			String[] times = DateUtil.getDateRegionByDatePicker(create_date,
					false);
			if (StringUtils.isNotBlank(times[0])) {
				querySql.append(" and t.create_date >= '" + times[0] + "'");
				}
			if (StringUtils.isNotBlank(times[1])) {
				querySql.append(" and t.create_date <= '" + times[1] + "'");
			}
		}
		


		if (state != null && !"".equals(state)) {
			querySql.append(" and t.state = '" + state + "'");
		}

		//querySql.append(" order by t.report_name desc");
		querySql.append(" order by t.timestamp desc");
		System.out.println(querySql.toString());
		stmt.addSqlStmt(querySql.toString());
		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
		return stmt;

	}
	
	/**
	 * ��ѯϵͳʹ���������
	 */
	public SqlStatement getStateSet(TxnContext request,
			DataBus inputData) throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		String sql="select codevalue key,codename title from codedata c " +
				"where c.codetype='����״̬' and c.status='1'";
		stmt.addSqlStmt(sql);
		return stmt;

	}
	
	/**
	 * ������־����
	 */
	public SqlStatement addLogReport(TxnContext request, DataBus inputData)
			throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		DataBus data = request.getRecord("record");
		// ��ȡ��־����ID
		String log_report_create_id = data.getValue("log_report_create_id");
		String report_name = data.getValue("report_name");
		// ��ȡ��������
		String report_type = data.getValue("report_type");
		// ��ȡ��ǰ���ں�ʱ��
		String create_date = CalendarUtil.getCurrentDate();
		String timestamp = CalendarUtil.getCurrentDateTime();

		String filename = data.getValue("filename");
		String path = data.getValue("path");

		VoUser user = request.getOperData();
		String creator = user.getOperName();
		StringBuffer insertSql = new StringBuffer(
				"insert into log_report_create(log_report_create_id,report_name,creator,"
						+ "create_date,last_mender,publish_date,"
						+ "publish_person,state,operate,"
						+ "filename,path,report_type," + "timestamp)"
						+ "values ('" + log_report_create_id + "','"
						+ report_name + "','" + creator + "'," + "'"
						+ create_date + "','"+creator+"',''," + "'','0','����'," + "'"
						+ filename + "','" + path + "','" + report_type + "','"
						+ timestamp + "')");
		//System.out.println("������־sql:"+insertSql.toString());
		stmt.addSqlStmt(insertSql.toString());
		return stmt;
	}

	/**
	 * ������־����
	 */
	public SqlStatement upLoadLogReport(TxnContext request, DataBus inputData)
			throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		DataBus data = request.getRecord("record");
		// ��ȡ��־����ID
		String log_report_create_id = data.getValue("log_report_create_id");
		String report_name = data.getValue("report_name");
		// ��ȡ��������
		String report_type = data.getValue("report_type");
		// ��ȡ��ǰ���ں�ʱ��
		String create_date = CalendarUtil.getCurrentDate();
		String timestamp = CalendarUtil.getCurrentDateTime();

		String filename = data.getValue("filename");
		String path = data.getValue("path");

		VoUser user = request.getOperData();
		String creator = user.getOperName();
		StringBuffer insertSql = new StringBuffer(
				"insert into log_report_create(log_report_create_id,report_name,creator,"
						+ "create_date,last_mender,publish_date,"
						+ "publish_person,state,operate,"
						+ "filename,path,report_type," + "timestamp)"
						+ "values ('" + log_report_create_id + "','"
						+ report_name + "','" + creator + "'," + "'"
						+ create_date + "','',''," + "'','0','����'," + "'"
						+ filename + "','" + path + "','" + report_type + "','"
						+ timestamp + "')");
		// System.out.println("�ϴ���־sql:"+insertSql.toString());
		stmt.addSqlStmt(insertSql.toString());
		return stmt;
	}

	/**
	 * ������־����
	 */
	public SqlStatement publishReport(TxnContext request, DataBus inputData)
			throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		DataBus data = request.getRecord("select-key");
		// ��ȡ��־����ID
		String log_report_create_id = data.getValue("log_report_create_id");

		// ��ȡ��ǰ���ں�ʱ��
		String create_date = CalendarUtil.getCurrentDate();
		// String timestamp = CalendarUtil.getCurrentDateTime();

		VoUser user = request.getOperData();
		String creator = user.getOperName();
		StringBuffer insertSql = new StringBuffer(
				"update log_report_create set publish_date='" + create_date
						+ "',publish_person='" + creator
						+ "',state='1' where log_report_create_id='"
						+ log_report_create_id + "'");

		// System.out.println("��������sql:"+insertSql.toString());
		stmt.addSqlStmt(insertSql.toString());
		return stmt;
	}

	/**
	 * �˻���־����
	 */
	public SqlStatement returnReport(TxnContext request, DataBus inputData)
			throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		DataBus data = request.getRecord("select-key");
		// ��ȡ��־����ID
		String log_report_create_id = data.getValue("log_report_create_id");

		// ��ȡ��ǰ���ں�ʱ��
		String create_date = CalendarUtil.getCurrentDate();
		// String timestamp = CalendarUtil.getCurrentDateTime();

		VoUser user = request.getOperData();
		String creator = user.getOperName();
		StringBuffer insertSql = new StringBuffer(
				"update log_report_create set publish_date='',publish_person='',state='0' where log_report_create_id='"
						+ log_report_create_id + "'");

		// System.out.println("�˻ر���sql:"+insertSql.toString());
		stmt.addSqlStmt(insertSql.toString());
		return stmt;
	}
	
	/**
	 * �޸�ʹ�ñ���״̬
	 */
	public SqlStatement updateReportState(TxnContext request, DataBus inputData)
			throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		DataBus data = request.getRecord("primary-key");
		// ��ȡ��־����ID
		String log_report_create_id = data.getValue("log_report_create_id");
		StringBuffer insertSql = new StringBuffer(
				"update log_report_create set state='2' where log_report_create_id='"
						+ log_report_create_id + "'");
		stmt.addSqlStmt(insertSql.toString());
		return stmt;
	}

	/**
	 * �޸���־����
	 */
	public SqlStatement modifyReport(TxnContext request, DataBus inputData)
			throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		DataBus data = request.getRecord("record");
		// ��ȡ��־����ID
		String log_report_create_id = data.getValue("log_report_create_id");

		// ��ȡ��ǰ���ں�ʱ��
		// String create_date = CalendarUtil.getCurrentDate();
		String timestamp = CalendarUtil.getCurrentDateTime();

		VoUser user = request.getOperData();
		String creator = user.getOperName();
		StringBuffer updateSql = new StringBuffer(
				"update log_report_create set timestamp='" + timestamp
						+ "',last_mender='" + creator
						+ "' where log_report_create_id='"
						+ log_report_create_id + "'");

		// System.out.println("�޸���־����sql:"+updateSql.toString());
		stmt.addSqlStmt(updateSql.toString());
		return stmt;
	}

	/**
	 * ������־����
	 */
	public SqlStatement addReportUse(TxnContext request, DataBus inputData)
			throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		DataBus data = request.getRecord("select-key");
		// ��ȡ��־����ID
		String log_report_create_id = data.getValue("log_report_create_id");
		// ��ȡ��ǰ���ں�ʱ��
		String create_date = CalendarUtil.getCurrentDate();
		String timestamp = CalendarUtil.getCurrentDateTime();

		VoUser user = request.getOperData();
		String browser_person = user.getOperName();
		StringBuffer insertSql = new StringBuffer(
				"insert into log_report_use(log_report_use_id,log_report_create_id,browser_person,browser_date,"
						+ "operate,filename,path,"
						+ "timestamp)"
						+ "select sys_guid(),'"
						+ log_report_create_id
						+ "','"
						+ browser_person
						+ "','"
						+ create_date
						+ "',"
						+ "'����',filename,path,'"
						+ timestamp
						+ "' from log_report_create where log_report_create_id='"
						+ log_report_create_id + "'");
		// System.out.println("�ϴ���־sql:"+insertSql.toString());
		stmt.addSqlStmt(insertSql.toString());
		return stmt;
	}

	/**
	 * ��ѯ��������
	 */
	public SqlStatement checkReportName(TxnContext request, DataBus inputData)
			throws TxnException
	{

		SqlStatement stmt = new SqlStatement();
		String report_name = request.getRecord("select-key").getValue(
				"report_name");

		StringBuffer querySql = new StringBuffer(
				"select t.* from log_report_create t where state<2");

		if (report_name != null && !"".equals(report_name)) {
			querySql.append(" and t.report_name = '" + report_name + "'");
		}

		// System.out.println("��ѯ��������:"+querySql.toString());
		stmt.addSqlStmt(querySql.toString());
		//stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");

		return stmt;
	}

}
