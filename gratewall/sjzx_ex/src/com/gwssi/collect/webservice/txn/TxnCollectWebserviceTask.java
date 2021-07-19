package com.gwssi.collect.webservice.txn;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.collect.webservice.param.vo.CollectWsParamValueContext;
import com.gwssi.collect.webservice.vo.CollectWebserviceTaskContext;
import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.util.UuidGenerator;
import com.gwssi.webservice.client.TaskInfo;
import com.gwssi.webservice.server.ServiceDAO;
import com.gwssi.webservice.server.ServiceDAOImpl;

public class TxnCollectWebserviceTask extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap		txnMethods		= getAllMethod(
														TxnCollectWebserviceTask.class,
														CollectWebserviceTaskContext.class);

	// ���ݱ�����
	private static final String	TABLE_NAME		= "collect_webservice_task";

	// ��ѯ�б�
	private static final String	ROWSET_FUNCTION	= "select collect_webservice_task list";

	// ��ѯ��¼
	private static final String	SELECT_FUNCTION	= "select one collect_webservice_task";

	// �޸ļ�¼
	private static final String	UPDATE_FUNCTION	= "update one collect_webservice_task";

	// ���Ӽ�¼
	private static final String	INSERT_FUNCTION	= "insert one collect_webservice_task";

	// ɾ����¼
	private static final String	DELETE_FUNCTION	= "delete one collect_webservice_task";

	/**
	 * ���캯��
	 */
	public TxnCollectWebserviceTask()
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

	public void txn30102111(CollectWebserviceTaskContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		//System.out.println("11 ="+context);
		String wsTaskId = context.getRecord("select-key").getValue(
				"webservice_task_id");
		table
				.executeFunction("getWsMethodInfo", context, inputNode,
						outputNode);
		
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction("getParamByFuncID", context, inputNode,
				"dataItem");// �����б�
		
		
	}

	public void txn30102112(CollectWebserviceTaskContext context)
			throws TxnException, DBException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String taskId = context.getRecord("select-key").getValue(
				"collect_task_id");
		System.out.println("taskId is " + taskId);
		table.executeFunction("getTaskInfo", context, inputNode, outputNode);

		int count = -1;
		ServiceDAO daoTable = new ServiceDAOImpl(); // �������ݱ�Dao
		count = this.getNum(CollectConstants.TYPE_FTP_TABLE, daoTable);
		count = count + 1;
		String service_no = "";
		System.out.println("count===" + count);
		if (count < 10) {
			service_no = "SERVICE_00" + count;
		} else if (count >= 10 && count <= 99) {
			service_no = "SERVICE_0" + count;
		} else {
			service_no = "SERVICE_" + count;
		}
		context.getRecord("record").setValue("service_no", service_no);// ������
		context.getRecord("record").setValue("collect_task_id", taskId);// ������
	}

	public void txn30102113(CollectWebserviceTaskContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String id = context.getRecord("record").getValue("webservice_task_id");
		String is_code = context.getRecord("record").getValue("encrypt_mode");
		if (is_code != null && is_code.equals("01")) {
			context.getRecord("record").setValue("is_encryption", "0");// �޼���
		} else {
			context.getRecord("record").setValue("is_encryption", "1");// ��Ҫ����
		}

		if (id == null || "".equals(id)) {
			id = UuidGenerator.getUUID();
			context.getRecord("record").setValue("webservice_task_id", id);// ���ݱ�ID
			context.getRecord("record").setValue("method_status",
					CollectConstants.TYPE_QY);// ���ݱ�ID
			context.getRecord("primary-key").setValue("webservice_task_id", id);// ���ݱ�ID

			table.executeFunction(INSERT_FUNCTION, context, inputNode,
					outputNode);
			//System.out.println("���ӷ�����"+context);
		} else {
			table.executeFunction(UPDATE_FUNCTION, context, inputNode,
					outputNode);
			//System.out.println("�༭������"+context);
		}
	}

	public void txn30102114(CollectWebserviceTaskContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String wsTaskId = context.getRecord("select-key").getValue(
				"webservice_task_id");
		System.out.println("wsTaskId is " + wsTaskId);
		table
				.executeFunction("getWsMethodInfo", context, inputNode,
						outputNode);
	}

	/**
	 * ��ѯwebservice�����б�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30102001(CollectWebserviceTaskContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String taskId = context.getRecord("select-key").getValue(
				"collect_task_id");
		System.out.println("taskId is " + taskId);
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction(ROWSET_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * �޸�webservice������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30102002(CollectWebserviceTaskContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		String is_code = context.getRecord("record").getValue("encrypt_mode");
		if (is_code != null && is_code.equals("01")) {
			context.getRecord("record").setValue("is_encryption", "0");// �޼���
		} else {
			context.getRecord("record").setValue("is_encryption", "1");// ��Ҫ����
		}

		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
		// DataBus db=context.getRecord("primary-key");
		//
		// System.out.println("/������======"+db);
		// context.addRecord("primary-key", db);
		// XtjgCode xtjgCode = new XtjgCode();
		// xtjgCode.getCjbList(context);
		this.callService("30102004", context);

	}

	/**
	 * ����socket������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 * @throws DBException
	 */
	public void txn30102003(CollectWebserviceTaskContext context)
			throws TxnException, DBException
	{

		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String id = context.getRecord("record").getValue("webservice_task_id");
		String is_code = context.getRecord("record").getValue("encrypt_mode");
		if (is_code != null && is_code.equals("01")) {
			context.getRecord("record").setValue("is_encryption", "0");// �޼���
		} else {
			context.getRecord("record").setValue("is_encryption", "1");// ��Ҫ����
		}
		if (id == null || "".equals(id)) {
			id = UuidGenerator.getUUID();
			context.getRecord("record").setValue("webservice_task_id", id);// ���ݱ�ID
			context.getRecord("record").setValue("web_name_space", "");// ��ռ�
			context.getRecord("record").setValue("method_status",
					CollectConstants.TYPE_QY);// ���ݱ�ID
			context.getRecord("primary-key").setValue("webservice_task_id", id);// ���ݱ�ID

			int count = -1;
			ServiceDAO daoTable = new ServiceDAOImpl(); // �������ݱ�Dao
			count = this.getNum(CollectConstants.TYPE_FTP_TABLE, daoTable);
			count = count + 1;
			String service_no = "";
			System.out.println("count===" + count);
			if (count < 10) {
				service_no = "SERVICE_00" + count;
			} else if (count >= 10 && count <= 99) {
				service_no = "SERVICE_0" + count;
			} else {
				service_no = "SERVICE_" + count;
			}
			context.getRecord("record").setValue("service_no", service_no);// ������
			table.executeFunction(INSERT_FUNCTION, context, inputNode,
					outputNode);

			String paraId = UuidGenerator.getUUID();
			// ���Ӳ�����Ϣ
			String sql = "insert into collect_webservice_patameter(webservice_patameter_id,webservice_task_id,patameter_type) values('"
					+ paraId
					+ "','"
					+ id
					+ "','"
					+ CollectConstants.PARAM_STYLE_00 + "')";
			System.out.println("sql========" + sql);
			table.executeUpdate(sql);

		} else {
			table.executeFunction(UPDATE_FUNCTION, context, inputNode,
					outputNode);
		}

		this.callService("30102008", context);
	}

	/**
	 * ��ȡ������
	 * 
	 * @param tableName
	 * @param daoTable
	 * @return
	 * @throws DBException
	 */
	public int getNum(String tableName, ServiceDAO daoTable) throws DBException
	{
		int num = 0;
//		String sql = "select service_no from collect_webservice_task a,collect_task b where a.collect_task_id = b.collect_task_id "
//				+ " and b.collect_type = '05' order by service_no desc";
		
		
		String sql = "select service_no from collect_webservice_task a,collect_task b where a.collect_task_id = b.collect_task_id "
			+ " order by service_no desc";
		List list = daoTable.query(sql);
		Map map = new HashMap();
		if (list != null && list.size() > 0) {
			map = (HashMap) list.get(0);
			System.out.println(map.toString());
			String service_no = (String) map.get("SERVICE_NO");// ���ֵ
			if (service_no != null && !"".equals(service_no)) {
				service_no = service_no.substring(service_no.indexOf("_") + 1);
				num = Integer.parseInt(service_no);
			}
		}
		System.out.println("num===" + num);
		return num;
	}

	/**
	 * ��ѯwebservice���������޸�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 * @throws DBException
	 */
	public void txn30102004(CollectWebserviceTaskContext context)
			throws TxnException, DBException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		String service_targets_id = context.getRecord("primary-key").getValue(
				"service_targets_id");// �������ID

		if (service_targets_id != null && !"".equals(service_targets_id)) {
			context.getRecord("record").setValue("service_targets_id",
					service_targets_id);
		}

		table.executeFunction("getParamByFuncID", context, inputNode,
				"dataItem");// �����б�
		context = getParamValueList(context, table);
		// System.out.println(context);
	}

	public CollectWebserviceTaskContext getParamValueList(
			CollectWebserviceTaskContext context1, BaseTable table)
			throws TxnException
	{
		CollectWebserviceTaskContext context = context1;
		Recordset rs = context.getRecordset("dataItem");
		TaskInfo ti = new TaskInfo();
		String tmpStr = "";
		for (int ii = 0; ii < rs.size(); ii++) {
			DataBus db = rs.get(ii);
			String paramid = db.getValue("webservice_patameter_id");
			// String paramid = db.getValue("webservice_task_id");
			List paramValueList = ti.queyrParamValue(paramid);
			for (int jj = 0; jj < paramValueList.size(); jj++) {
				Map tmpMap = (Map) paramValueList.get(jj);
				// if ("01".equals(db.get("patameter_style").toString())) {
				tmpStr += tmpMap.get("PATAMETER_NAME").toString().toUpperCase()
						+ "="
						+ tmpMap.get("PATAMETER_VALUE").toString()
								.toUpperCase() + ",";
				// + tmpMap.get("CONNECTOR");
				// }
			}
			// tmpStr = ti.getParamValue(paramid);
			System.out.println("tmpStr = " + tmpStr);

			if (StringUtils.isNotBlank(tmpStr)) {
				// ���²���ֵ
				String sqlUpdate = " update collect_webservice_patameter set patameter_value = '"
						+ tmpStr
						+ "' where webservice_patameter_id = '"
						+ paramid + "'";
				System.out.println("���²���ֵsql = " + sqlUpdate);
				table.executeUpdate(sqlUpdate);

				tmpStr = tmpStr.substring(0, tmpStr.length() - 1);
				rs.get(ii).setProperty("patameter_value", tmpStr);
			}

		}
		return context;
	}

	/**
	 * ɾ��webservice������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30102005(CollectWebserviceTaskContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("deleteFunc", context, inputNode, outputNode);// ɾ��������Ӧ����

		this.callService("30101004", context);
	}

	/**
	 * ɾ��webservice������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30102025(CollectWebserviceTaskContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("deleteFunc", context, inputNode, outputNode);// ɾ��������Ӧ����
	}

	/**
	 * ��ѯwebservice�������ڲ鿴
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 * @throws DBException
	 */
	public void txn30102006(CollectWebserviceTaskContext context)
			throws TxnException, DBException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		//System.out.println("begin:"+context);
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		String webservice_task_id=context.getPrimaryKey().getValue("webservice_task_id");
		context.getSelectKey().setValue("webservice_task_id", webservice_task_id);
		Attribute.setPageRow(context, "dataItem", -1);
		table.executeFunction("getParamByFuncID", context, inputNode,
				"dataItem");// �����б�
		//System.out.println("end:"+context);
	}

	/**
	 * ��ѯsocket�������ڲ鿴
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 * @throws DBException
	 */
	public void txn30102016(CollectWebserviceTaskContext context)
			throws TxnException, DBException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		table.executeFunction("getParamByFuncID", context, inputNode,
				"dataItem");// �����б�
	}

	/**
	 * ��ѯsocket������������
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 * @throws DBException
	 */
	public void txn30102007(CollectWebserviceTaskContext context)
			throws TxnException, DBException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String collect_task_id = context.getRecord("primary-key").getValue(
				"collect_task_id");// �ɼ�����ID
		String service_targets_id = context.getRecord("primary-key").getValue(
				"service_targets_id");// �ɼ�����ID
		context.getRecord(outputNode).setValue("collect_task_id",
				collect_task_id);// �ɼ�����ID
		context.getRecord(outputNode).setValue("service_targets_id",
				service_targets_id);// �������ID
		table.executeFunction("getParamByFuncID", context, inputNode,
				"dataItem");// �����б�
	}

	/**
	 * ��ѯsocket���������޸�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 * @throws DBException
	 */
	public void txn30102008(CollectWebserviceTaskContext context)
			throws TxnException, DBException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String webservice_task_id = context.getRecord("record").getValue(
				"webservice_task_id");// �������ID
		if (webservice_task_id != null && !"".equals(webservice_task_id)) {
			context.getRecord("primary-key").setValue("webservice_task_id",
					webservice_task_id);
		}

		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		String service_targets_id = context.getRecord("primary-key").getValue(
				"service_targets_id");// �������ID

		if (service_targets_id != null && !"".equals(service_targets_id)) {
			context.getRecord("record").setValue("service_targets_id",
					service_targets_id);
		}

		table.executeFunction("getParamByFuncID", context, inputNode,
				"dataItem");// �����б�
		Recordset rs = context.getRecordset("dataItem");
		String jsonData = "";
		String patameter_value = "";
		String paramid = "";
		for (int ii = 0; ii < rs.size(); ii++) {
			DataBus db = new DataBus();
			db = rs.get(ii);
			paramid = db.getValue("webservice_patameter_id");
			patameter_value = getParamValue(paramid);

			jsonData += "{\"webservice_patameter_id\": \"" + paramid
					+ "\", \"webservice_task_id\": \""
					+ db.getValue("webservice_task_id")
					+ "\", \"patameter_type\": \""
					+ db.getValue("patameter_type")
					+ "\", \"patameter_name\": \""
					+ db.getValue("patameter_name")
					+ "\", \"patameter_value\": \"" + patameter_value
					+ "\", \"patameter_style\": \""
					+ db.getValue("patameter_style") + "\"}";
			jsonData += ", ";

			// ���²���ֵ
			String sqlUpdate = " update collect_webservice_patameter set patameter_value = '"
					+ patameter_value
					+ "' where webservice_patameter_id = '"
					+ paramid + "'";
			System.out.println("���²���ֵsql = " + sqlUpdate);
			table.executeUpdate(sqlUpdate);
		}
		if (StringUtils.isNotBlank(jsonData)) {
			jsonData = jsonData.substring(0, jsonData.lastIndexOf(","));
			jsonData = "[" + jsonData + "]";
		}
		context.setProperty("jsondata", jsonData);
		// System.out.println(context);
	}

	public String getParamValue(String webservice_patameter_id)
	{

		TaskInfo ti = new TaskInfo();
		List paramValueList = ti.queyrParamValue(webservice_patameter_id);
		String tmpStr = "";
		for (int jj = 0; jj < paramValueList.size(); jj++) {
			Map tmpMap = (Map) paramValueList.get(jj);
			// if ("01".equals(db.get("patameter_style").toString())) {
			tmpStr += tmpMap.get("PATAMETER_NAME").toString().toUpperCase()
					+ "="
					+ tmpMap.get("PATAMETER_VALUE").toString().toUpperCase()
					+ ",";
		}
		System.out.println("tmpStr = " + tmpStr);
		if (StringUtils.isNotBlank(tmpStr)) {
			tmpStr = tmpStr.substring(0, tmpStr.length() - 1);
		}
		return tmpStr;
	}

	/**
	 * �޸�socket������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30102009(CollectWebserviceTaskContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		String is_code = context.getRecord("record").getValue("encrypt_mode");
		if (is_code != null && is_code.equals("01")) {
			context.getRecord("record").setValue("is_encryption", "0");// �޼���
		} else {
			context.getRecord("record").setValue("is_encryption", "1");// ��Ҫ����
		}

		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
		// DataBus db=context.getRecord("primary-key");
		//
		// System.out.println("/������======"+db);
		// context.addRecord("primary-key", db);
		// XtjgCode xtjgCode = new XtjgCode();
		// xtjgCode.getCjbList(context);
		this.callService("30102008", context);

	}

	/**
	 * ��ѯsocket�������ڲ鿴
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 * @throws DBException
	 */
	public void txn30102010(CollectWebserviceTaskContext context)
			throws TxnException, DBException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		table.executeFunction("getParamByFuncID", context, inputNode,
				"dataItem");// �����б�
	}

	/**
	 * ɾ��socket������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30102011(CollectWebserviceTaskContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("deleteFunc", context, inputNode, outputNode);// ɾ��������Ӧ����

		this.callService("30101052", context);
	}

	/**
	 * ����jms������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 * @throws DBException
	 */
	public void txn30102013(CollectWebserviceTaskContext context)
			throws TxnException, DBException
	{

		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String id = context.getRecord("record").getValue("webservice_task_id");
		String is_code = context.getRecord("record").getValue("encrypt_mode");
		if (is_code != null && is_code.equals("01")) {
			context.getRecord("record").setValue("is_encryption", "0");// �޼���
		} else {
			context.getRecord("record").setValue("is_encryption", "1");// ��Ҫ����
		}
		if (id == null || "".equals(id)) {
			id = UuidGenerator.getUUID();
			context.getRecord("record").setValue("webservice_task_id", id);// ���ݱ�ID
			context.getRecord("record").setValue("web_name_space", "");// ��ռ�
			context.getRecord("record").setValue("method_status",
					CollectConstants.TYPE_QY);// ���ݱ�ID
			context.getRecord("primary-key").setValue("webservice_task_id", id);// ���ݱ�ID

			int count = -1;
			ServiceDAO daoTable = new ServiceDAOImpl(); // �������ݱ�Dao
			count = this.getNum(CollectConstants.TYPE_FTP_TABLE, daoTable);
			count = count + 1;
			String service_no = "";
			System.out.println("count===" + count);
			if (count < 10) {
				service_no = "SERVICE_00" + count;
			} else if (count >= 10 && count <= 99) {
				service_no = "SERVICE_0" + count;
			} else {
				service_no = "SERVICE_" + count;
			}
			context.getRecord("record").setValue("service_no", service_no);// ������
			table.executeFunction(INSERT_FUNCTION, context, inputNode,
					outputNode);

			String paraId = UuidGenerator.getUUID();
			// ���Ӳ�����Ϣ
			String sql = "insert into collect_webservice_patameter(webservice_patameter_id,webservice_task_id,patameter_type) values('"
					+ paraId
					+ "','"
					+ id
					+ "','"
					+ CollectConstants.PARAM_STYLE_00 + "')";
			System.out.println("sql========" + sql);
			table.executeUpdate(sql);

		} else {
			table.executeFunction(UPDATE_FUNCTION, context, inputNode,
					outputNode);
		}

		this.callService("30102018", context);
	}

	/**
	 * ��ѯjms������������
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 * @throws DBException
	 */
	public void txn30102017(CollectWebserviceTaskContext context)
			throws TxnException, DBException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String collect_task_id = context.getRecord("primary-key").getValue(
				"collect_task_id");// �ɼ�����ID
		String service_targets_id = context.getRecord("primary-key").getValue(
				"service_targets_id");// �ɼ�����ID
		context.getRecord(outputNode).setValue("collect_task_id",
				collect_task_id);// �ɼ�����ID
		context.getRecord(outputNode).setValue("service_targets_id",
				service_targets_id);// �������ID
		table.executeFunction("getParamByFuncID", context, inputNode,
				"dataItem");// �����б�
	}

	/**
	 * ��ѯjms���������޸�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 * @throws DBException
	 */
	public void txn30102018(CollectWebserviceTaskContext context)
			throws TxnException, DBException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String webservice_task_id = context.getRecord("record").getValue(
				"webservice_task_id");// �������ID
		if (webservice_task_id != null && !"".equals(webservice_task_id)) {
			context.getRecord("primary-key").setValue("webservice_task_id",
					webservice_task_id);
		}

		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		String service_targets_id = context.getRecord("primary-key").getValue(
				"service_targets_id");// �������ID

		if (service_targets_id != null && !"".equals(service_targets_id)) {
			context.getRecord("record").setValue("service_targets_id",
					service_targets_id);
		}

		table.executeFunction("getParamByFuncID", context, inputNode,
				"dataItem");// �����б�
		Recordset rs = context.getRecordset("dataItem");
		String jsonData = "";
		String patameter_value = "";
		String paramid = "";
		for (int ii = 0; ii < rs.size(); ii++) {
			DataBus db = new DataBus();
			db = rs.get(ii);
			paramid = db.getValue("webservice_patameter_id");
			patameter_value = getParamValue(paramid);

			jsonData += "{\"webservice_patameter_id\": \"" + paramid
					+ "\", \"webservice_task_id\": \""
					+ db.getValue("webservice_task_id")
					+ "\", \"patameter_type\": \""
					+ db.getValue("patameter_type")
					+ "\", \"patameter_name\": \""
					+ db.getValue("patameter_name")
					+ "\", \"patameter_value\": \"" + patameter_value
					+ "\", \"patameter_style\": \""
					+ db.getValue("patameter_style") + "\"}";
			jsonData += ", ";

			// ���²���ֵ
			String sqlUpdate = " update collect_webservice_patameter set patameter_value = '"
					+ patameter_value
					+ "' where webservice_patameter_id = '"
					+ paramid + "'";
			System.out.println("���²���ֵsql = " + sqlUpdate);
			table.executeUpdate(sqlUpdate);
		}
		if (StringUtils.isNotBlank(jsonData)) {
			jsonData = jsonData.substring(0, jsonData.lastIndexOf(","));
			jsonData = "[" + jsonData + "]";
		}
		context.setProperty("jsondata", jsonData);
		// System.out.println(context);
	}

	/**
	 * �޸�jms������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30102019(CollectWebserviceTaskContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		String is_code = context.getRecord("record").getValue("encrypt_mode");
		if (is_code != null && is_code.equals("01")) {
			context.getRecord("record").setValue("is_encryption", "0");// �޼���
		} else {
			context.getRecord("record").setValue("is_encryption", "1");// ��Ҫ����
		}

		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
		// DataBus db=context.getRecord("primary-key");
		//
		// System.out.println("/������======"+db);
		// context.addRecord("primary-key", db);
		// XtjgCode xtjgCode = new XtjgCode();
		// xtjgCode.getCjbList(context);
		this.callService("30102018", context);

	}

	/**
	 * ��ѯjms�������ڲ鿴
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 * @throws DBException
	 */
	public void txn30102020(CollectWebserviceTaskContext context)
			throws TxnException, DBException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		table.executeFunction("getParamByFuncID", context, inputNode,
				"dataItem");// �����б�
	}

	/**
	 * ɾ��jms������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30102021(CollectWebserviceTaskContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("deleteFunc", context, inputNode, outputNode);// ɾ��������Ӧ����

		this.callService("30101062", context);
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
		CollectWebserviceTaskContext appContext = new CollectWebserviceTaskContext(
				context);
		invoke(method, appContext);
	}
}
